package com.collidge;

/**
 * Created by Kris on 24-Jan-15. Last modified 24-Feb-15.
 *
 * So far all code in this class was written by Kris, with three edits by Dan marked as "Dan's edit"
 *
 * Functions in this class:
 * - void useItem(String item)      -> uses a Combat item to restore either the Player's Health or Energy
 * - String[] getList()             -> returns a String array of items of which we have a quantity greater than zero
 * - void loadInventory()           -> sets up the inventory in the first place, initialising all of the items
 *
 * Notes:
 * - TODO: May either move useItem into player, or equipItem into inventory. Will see.
 * - TODO: Rebalance items
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Inventory
{
    /**
     * Dan's edit
     * Added = new HashMap();
     */
    private HashMap<String, CombatItem> MyCombatInv = new HashMap();
    private HashMap<String, Equipment> MyEquipment = new HashMap();

    /**
     * Dan's edit
     * Changed to have Player player passed in.
     */
    void useItem(Player player, String item)
    {
        player.changeHealth(MyCombatInv.get(item).getHealthRestore());
        player.changeEnergy(MyCombatInv.get(item).getEnergyRestore());
        MyCombatInv.get(item).changeItemQuantity(-1);
    }

    /**
     * This is currently implemented within player rather than in inventory
     */
//    void equipItem(String item)
//    {
//        player.setattack(MyEquipment.get(item).getAttackbonus());
//        player.setenergy(MyEquipment.get(item).getEnergybonus());
//        player.sethealth(MyEquipment.get(item).getHealthbonus());
//        player.setdefence(MyEquipment.get(item).getDefencebonus());
//    }

    int getAttackBonus(String item){ return MyEquipment.get(item).getAttackbonus();}
    int getEnergyBonus(String item){ return MyEquipment.get(item).getEnergybonus();}
    int getDefenceBonus(String item){ return MyEquipment.get(item).getDefencebonus();}
    int getHealthBonus(String item){ return MyEquipment.get(item).getHealthbonus();}

    String[] getList()
    {
        /**
         * Dan's edit
         * Commented out the first line and replaced with the next one
         */
        //Set<String> names = Collections.emptySet();
        Set<String> names = new HashSet();

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

    String[] getDesc()
    {
        Set<String> description = new HashSet();

        for(Map.Entry<String, CombatItem> entry: MyCombatInv.entrySet())
        {
            if(entry.getValue().getItemQuantity() > 0)
            {
                description.add(entry.getValue().getBattleText());
            }
        }


        return description.toArray(new String[description.size()]);

    }

    String[] getEquipmentList()
    {
        /**
         * Dan's edit
         * Commented out the first line and replaced with the next one
         */
        //Set<String> names = Collections.emptySet();
        Set<String> names = new HashSet();

        for(Map.Entry<String, Equipment> entry: MyEquipment.entrySet())
        {
            if(entry.getValue().getItemQuantity() > 0)
            {
                names.add(entry.getKey());
            }
        }

        String[] Names = names.toArray(new String[names.size()]);
        return Names;
    }

    String getItemImage(String item)
    {
        if(MyCombatInv.containsKey(item))
        {
            return MyCombatInv.get(item).getItemImage();
        }
        else
        {
            return MyEquipment.get(item).getItemImage();
        }
    }

    String getItemText(String item)
    {
        if(MyCombatInv.containsKey(item))
        {
            return MyCombatInv.get(item).getItemText();
        }
        else
        {
            return MyEquipment.get(item).getItemText();
        }
    }

    String getItemType(String item)
    {
        if(MyCombatInv.containsKey(item))
        {
            return MyCombatInv.get(item).getItemType();
        }
        else
        {
            return MyEquipment.get(item).getItemType();
        }
    }

    int getItemQuantity(String item)
    {
        if(MyCombatInv.containsKey(item))
        {
            return MyCombatInv.get(item).getItemQuantity();
        }
        else
        {
            return MyEquipment.get(item).getItemQuantity();
        }
    }

    void loadInventory()
    {
        /**
         * Constructor for combat items is (Type, Text, Health, Energy, Quantity, Image)
         */
        CombatItem Coffee, EnergyDrink, Noodles, Sandwich;

        Coffee = new CombatItem("Energy", "Energy Item. Restores 10 energy. Mmm that's good coffee!", 0, 10, 10, "Coffee_cupSmall.png", "Restores 10 En");
        EnergyDrink = new CombatItem("Energy", "Energy Item. Restores 10 energy. Buzzing!", 0, 10, 10, "energy60.png", "Restores 10 En");
        Noodles = new CombatItem("Health", "Health Item. Restores 10 HP. Instant Goodness", 10, 0, 10, "Noodles.png", "Restores 10 Health");
        Sandwich = new CombatItem("Health", "Health Item. Restores 10 HP. Needs more mayo!", 10, 0, 10, "sandwichIcon.png", "Restores 10 Health");

        MyCombatInv.put("Coffee", Coffee);
        MyCombatInv.put("Energy Drink", EnergyDrink);
        MyCombatInv.put("Noodles", Noodles);
        MyCombatInv.put("Sandwich", Sandwich);

        /**
         * Constructor for equipment is (Type, Text, Attack, Energy, Defence, Health, Quantity)
         */
        Equipment Tsquare, Scarf, Macshield, Bookshield;
        Tsquare = new Equipment("Weapon", "Weapon. +10 Attack. The sign of a true engineer.", 10, 0, 0, 0, 1, "tsquareSmall.png");
        Scarf = new Equipment("Weapon", "Weapon. +5 Attack. McDonalds Manager, here I come!", 5, 0, 0, 0, 1, "Scarf.png");
        Macshield = new Equipment("Armour", "Armour. +10 Defence. Overpriced shield!", 0, 0, 10, 0, 1, "macShieldIcon.png");
        Bookshield = new Equipment("Armour", "Armour. +5 Defence. Is there a fine if it's damaged on return?", 0, 0, 5, 0, 1, "bookShieldIcon.png");

        MyEquipment.put("Tsquare", Tsquare);
        MyEquipment.put("Scarf", Scarf);
        MyEquipment.put("Macshield", Macshield);
        MyEquipment.put("Bookshield", Bookshield);
    }
}
