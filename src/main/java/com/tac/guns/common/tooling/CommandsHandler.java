package com.tac.guns.common.tooling;

import java.util.HashMap;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class CommandsHandler
{
    private static CommandsHandler instance;

    public static CommandsHandler get()
    {
        if(instance == null)
        {
            instance = new CommandsHandler();
        }
        // ---MANUAL, CURRENT SYSTEM FOR IMPLEMENTING DEFAULT HANDLERS--- ///

        // WEAPON EDITING TRACKER
        instance.catGlobals.put(1, true);
        // WEAPON EDITING TRACKER   |   END

        instance.catGlobals.put(3, true); // GUI EDITOR

        instance.catGlobals.put(2, true); // SCOPE EDITOR

        instance.catGlobals.put(4, true); // GUI EDITOR
        // ---MANUAL, CURRENT SYSTEM FOR IMPLEMENTING DEFAULT HANDLERS   |   END--- ///
        return instance;
    }

    private CommandsHandler() {}

    private HashMap<Integer, Boolean> catGlobals = new HashMap<>(); // NEED A BETTER SOLUTION THEN JUST OBJECT, DIRTY!
    private int catCurrentIndex = 0;

    public void setCatCurrentIndex(int catCurrentIndex) {this.catCurrentIndex = catCurrentIndex;}
    public int getCatCurrentIndex() {return this.catCurrentIndex;}
    public boolean catInGlobal(int index) {return catGlobals.containsKey(Integer.valueOf(index));}
    //public HashMap<String, Object> getCatGlobal(int i) {return catGlobals.get(i);}
    /*public boolean putCatGlobals(int index, HashMap<String, Object> data)
    {
        if(this.catGlobals.containsKey(index))
            return false;
        this.catGlobals.put(index, data);
        return true;
    }*/
}