package com.tac.guns.client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;

import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Helps to fix key conflict issue. Adapted solution from FMUM.
 * 
 * @see InputHandler
 * @author Giant_Salted_Fish
 */
@OnlyIn( Dist.CLIENT )
public final class KeyBind
{
	public static final HashMap< String, KeyBind > REGISTRY = new HashMap<>();
	
	private static final long HANDLE = Minecraft.getInstance().getWindow().getWindow();
	
	private static final Function< Integer, Boolean >
		UPDATER_KEYBOARD = key -> GLFW.glfwGetKey( HANDLE, key ) == GLFW.GLFW_PRESS;
	
	private static final Function< Integer, Boolean >
		UPDATER_MOUSE = key -> GLFW.glfwGetMouseButton( HANDLE, key ) == GLFW.GLFW_PRESS;
	
	private static final Function< Integer, Boolean > UPDATER_NONE = key -> false;
	
	/**
	 * Whether this key is continuously down or not
	 */
	public boolean down = false;
	
	/**
	 * Current bounden key
	 */
	private Key keyCode;
	
	/**
	 * Invoked in {@link #update()} to check whether the bounden key is down or not. This helps to
	 * avoid the if branch that checks whether the bounden key is from keyboard or the mouse.
	 */
	private Function< Integer, Boolean > updater;
	
	/**
	 * The corresponding vanilla key bind object. Its key bind will be set to
	 * {@link InputMappings#INPUT_INVALID} to avoid key conflict in game.
	 */
	private KeyMapping keyBind;
	
	private final LinkedList< Runnable > pressCallBacks = new LinkedList<>();
	
	/**
	 * Use {@link #KeyBind(String, String, int, Type...)} if you want to specify key category
	 * 
	 * @param inputType Will be {@link Type#KEYSYM} if not present
	 */
	KeyBind( String name, int keyCode, Type... inputType ) {
		this( name, "key.categories.tac", keyCode, inputType );
	}
	
	/**
	 * @see #KeyBind(String, int, Type...)
	 */
	KeyBind( String name, String category, int keyCode, Type... inputType )
	{
		this.$keyCode(
			keyCode >= 0
			? ( inputType.length > 0 ? inputType[ 0 ] : Type.KEYSYM ).getOrCreate( keyCode )
			: InputConstants.UNKNOWN
		);
		this.keyBind = new KeyMapping(
			name,
			GunConflictContext.IN_GAME_HOLDING_WEAPON,
			this.keyCode,
			category
		);
		
		// Bind to none to avoid conflict
		this.keyBind.setKey( InputConstants.UNKNOWN );
		
		REGISTRY.put( this.name(), this );
	}
	
	public String name() { return this.keyBind.getName(); }
	
	public Key keyCode() { return this.keyCode; }
	
	public void $keyCode( Key code )
	{
		this.keyCode = code;
		if( code == InputConstants.UNKNOWN )
			this.updater = UPDATER_NONE;
		else switch( code.getType() )
		{
		case KEYSYM:
			this.updater = UPDATER_KEYBOARD;
			break;
		case MOUSE:
			this.updater = UPDATER_MOUSE;
			break;
		case SCANCODE:
			// TODO: what this means?
		}
	}
	
	/**
	 * Add a callback that will be invoked on the press of this key. It will only be invoked once
	 * until the key is released and pressed again.
	 */
	public void addPressCallBack( Runnable callback ) { this.pressCallBacks.add( callback ); }
	
	void update()
	{
		if( !this.updater.apply( this.keyCode.getValue() ) )
			this.down = false;
		else if( !this.down )
		{
			// Previously not pressed, update #down and fire callbacks
			this.down = true;
			this.pressCallBacks.forEach( Runnable::run );
		}
	}
	
	void reset() { this.down = false; }
	
	void restoreKeyBind() { this.keyBind.setKey( this.keyCode ); }
	
	boolean clearKeyBind()
	{
		final Key code = this.keyBind.getKey();
		if( code == this.keyCode ) return false;
		
		// Key bind has been changed, update it
		this.$keyCode( code );
		this.keyBind.setKey( InputConstants.UNKNOWN );
		return true;
	}
	
	void regis() { ClientRegistry.registerKeyBinding( this.keyBind ); }
}
