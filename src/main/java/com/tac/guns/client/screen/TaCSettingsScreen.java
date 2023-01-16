package com.tac.guns.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.settings.GunOptions;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.stream.Stream;


@OnlyIn(Dist.CLIENT)
public class TaCSettingsScreen extends OptionsSubScreen {
    private OptionsList optionsList;
    private static final Option[] OPTIONS = new Option[]{};

    public TaCSettingsScreen(Screen p_i225929_1_, Options p_i225929_2_) {
        super(p_i225929_1_, p_i225929_2_, new TranslatableComponent("tac.options.screenFormat"));
    }

    protected void init() {
        this.optionsList = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        if (InputConstants.isRawMouseInputSupported()) {
            this.optionsList.addSmall(Stream.concat(Arrays.stream(OPTIONS), Stream.of()).toArray(Option[]::new));
            /*this.optionsList.addBig(GunOptions.DOUBLE_RENDER_EXIST*//*, GunOptions.REDDOT_SQUISH_EXIST*//*);
            this.optionsList.addBig(GunOptions.SIZE_FIREMODE_POS);
            this.optionsList.addBig(GunOptions.FIREMODE_EXIST);
            this.optionsList.addBig(GunOptions.X_FIREMODE_POS);
            this.optionsList.addBig(GunOptions.Y_FIREMODE_POS);

            this.optionsList.addBig(GunOptions.AMMOCOUNTER_EXIST);
            this.optionsList.addBig(GunOptions.SIZE_AMMOCOUNTER_POS);
            this.optionsList.addBig(GunOptions.X_AMMOCOUNTER_POS);
            this.optionsList.addBig(GunOptions.Y_AMMOCOUNTER_POS);

            this.optionsList.addBig(GunOptions.WeaponIcon_EXIST);
            this.optionsList.addBig(GunOptions.SIZE_Icon_POS);
            this.optionsList.addBig(GunOptions.X_Icon_POS);
            this.optionsList.addBig(GunOptions.Y_Icon_POS);

            this.optionsList.addBig(GunOptions.ReloadBar_EXIST);
            this.optionsList.addBig(GunOptions.SIZE_ReloadBar_POS);
            this.optionsList.addBig(GunOptions.X_ReloadBar_POS);
            this.optionsList.addBig(GunOptions.Y_ReloadBar_POS);*/
        } else {
            this.optionsList.addSmall(OPTIONS);
        }

        /*this.children.add(this.optionsList);
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (p_223703_1_) -> {
            this.options.save();
            this.minecraft.setScreen(this.lastScreen);
        }));*/
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.optionsList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 5, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}

