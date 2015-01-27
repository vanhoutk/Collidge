package com.example.kris.menu;

/**
 * Created by Kris on 24-Jan-15.
 *
 * Functions in this class:
 *
 * void useItem(String item) - uses a Combat item to restore either the Player's Health or Energy
 * void equipItem(String item) - equips an item which increases a number of Attack, Defence, Energy, Health
 * String[] getList() - returns a String array of items of which we have a quantity greater than zero
 * void loadInventory() - sets up the inventory in the first place, initialising all of the items
 *
 * loadInventory should be called when the inventory is initialised originally
 * Combat will call the useItem and getList functions
 * The InventoryMenu (working title, may change to InventoryGameState) will call the equipItem function
 */

import com.example.kris.menu.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory
{
    private HashMap<String, CombatItem> MyCombatInv;
    private HashMap<String, Equipment> MyEquipment;
    //private Map<String, CombatItem> MyCombatInv;

    /**
     * Depending on whether or not I can get getList to return only items whose quantity is > 0, I may
     * change this to a bool, and return true if the item could be used, or false if none are left.
     */
    void useItem(String item)
    {
//        Player.changeHealth(MyCombatInv.get(item).getHealthRestore());
//        Player.changeEnergy(MyCombatInv.get(item).getEnergyRestore());
        MyCombatInv.get(item).setItemQuantity(-1);
    }

    /**
     * Depending on whether or not I can get getList to return only items whose quantity is > 0, I may
     * change this to a bool, and return true if the item could be used, or false if none are left.
     */
    void equipItem(String item)
    {
//        Player.setattack(MyEquipment.get(item).getAttackbonus());
//        Player.setenergy(MyEquipment.get(item).getEnergybonus());
//        Player.sethealth(MyEquipment.get(item).getHealthbonus());
//        Player.setdefence(MyEquipment.get(item).getDefencebonus());
    }

    /**
     * Have a feeling this function will need some work. Ideally should be a case of combat being able to use
     * Inventory.getList() and being returned an array of strings which they can then display.
     */
    String[] getList()
    {
        Set<String> names = Collections.emptySet();

        for(Map.Entry<String, CombatItem> entry: MyCombatInv.entrySet())
        {
            if(entry.getValue().getItemQuantity() > 0)
            {
                names.add(entry.getKey());
            }
        }

        String[] Names = names.toArray(new String[names.size()]);
        return Names;
    }
    
    /**
     * Once the images have been included, they'll need to be added to the constructors of all of the
     * items below. (of type String, e.g. "R.Drawable.Coffee" (I think))
     */
    void loadInventory()
    {
        CombatItem Coffee, EnergyDrink, Noodles, ChickenRoll;
        Coffee = new CombatItem("Energy", "Mmm that's good coffee!", 0, 10, 0);
        EnergyDrink = new CombatItem("Energy", "Buzzing!", 0, 10, 0);
        Noodles = new CombatItem("Health", "Instant Goodness", 10, 0, 0);
        ChickenRoll = new CombatItem("Health", "Needs more mayo!", 10, 0, 0);

        MyCombatInv.put("Coffee", Coffee);
        MyCombatInv.put("EnergyDrink", EnergyDrink);
        MyCombatInv.put("Noodles", Noodles);
        MyCombatInv.put("ChickenRoll", ChickenRoll);

        Equipment Tsquare, Scarf, Macshield, Bookshield;
        Tsquare = new Equipment("Weapon", "The sign of a true engineer.", 10, 5, 0, 0);
        Scarf = new Equipment("Weapon", "McDonalds Manager, here I come!", 5, 10, 0, 0);
        Macshield = new Equipment("Armour", "Overpriced shield!", 0, 0, 10, 5);
        Bookshield = new Equipment("Armour", "Is there a fine if it's damaged on return?", 0, 0, 5, 10);

        MyEquipment.put("Tsquare", Tsquare);
        MyEquipment.put("Scarf", Scarf);
        MyEquipment.put("Macshield", Macshield);
        MyEquipment.put("Bookshield", Bookshield);
    }

}
