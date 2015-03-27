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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;
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

    //for items that damage enemies
    int getItemDamage(String item) {return MyCombatInv.get(item).getEnemyDamage();}

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
        //Set<String> description = new HashSet();
        ArrayList<String> description=new ArrayList();


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

    void loadInventory(Player player, boolean load)
    {
        int tsquareQ = 0, scarfQ = 0, macQ = 0, bookQ = 0, coffeeQ = 0, energyQ = 0, noodlesQ = 0, sandwichQ = 0;
        String equippedWeapon = "None", equippedArmour = "None";
        if(load && Gdx.files.local("items.xml").exists())
        {
            try {
                XmlReader reader = new XmlReader();
                FileHandle handle1 = Gdx.files.local("items.xml");
                XmlReader.Element root = reader.parse(handle1.readString());
                XmlReader.Element equipment = root.getChildByName("equipment");
                XmlReader.Element combatItems = root.getChildByName("combatItem");
                XmlReader.Element equipped = root.getChildByName("equipped");
                tsquareQ = equipment.getInt("Tsquare");
                scarfQ = equipment.getInt("Scarf");
                macQ = equipment.getInt("macShield");
                bookQ = equipment.getInt("bookShield");
                coffeeQ = combatItems.getInt("Coffee");
                energyQ = combatItems.getInt("EnergyDrink");
                noodlesQ = combatItems.getInt("Noodles");
                sandwichQ = combatItems.getInt("Sandwich");
                equippedWeapon = equipped.getChildByName("Weapon").getText();
                equippedArmour = equipped.getChildByName("Armour").getText();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            tsquareQ = 1;
            scarfQ = 1;
            macQ = 1;
            bookQ = 1;
            coffeeQ = 10;
            energyQ = 10;
            noodlesQ = 10;
            sandwichQ = 10;
            equippedWeapon = "None";
            equippedArmour = "None";
        }
        /**
         * Constructor for combat items is (Type, Text, Health, Energy, Enemy Damage, Quantity, Image)
         */
        CombatItem Coffee, EnergyDrink, Noodles, Sandwich, IED;

        Coffee = new CombatItem("Energy", "Energy Item. Mmm that's good coffee!", 0, 10, 0, 10, "Coffee_cupSmall.png");
        EnergyDrink = new CombatItem("Energy", "Energy Item. Buzzing!", 0, 10, 0, 10, "energy60.png");
        Noodles = new CombatItem("Health", "Health Item. Instant Goodness", 10, 0, 0, 10, "Noodles.png");
        Sandwich = new CombatItem("Health", "Health Item. Needs more mayo!", 10, 0, 0, 10, "sandwichIcon.png");
        IED = new CombatItem("AOE Damage", "Deals damage to all enemies.", 0, 0, 10, 10, "ied.png");

        MyCombatInv.put("Coffee", Coffee);
        MyCombatInv.put("Energy Drink", EnergyDrink);
        MyCombatInv.put("Noodles", Noodles);
        MyCombatInv.put("Sandwich", Sandwich);
        MyCombatInv.put("Improvised Explosive Device", IED);

        /**
         * Constructor for equipment is (Type, Text, Attack, Energy, Defence, Health, Quantity)
         */
        Equipment Tsquare, Scarf, Macshield, Bookshield;

        Tsquare = new Equipment("Weapon", "Weapon. +10 Attack. The sign of a true engineer.", 10, 0, 0, 0, tsquareQ, "tsquareSmall.png");
        Scarf = new Equipment("Weapon", "Weapon. +5 Attack. McDonalds Manager, here I come!", 5, 0, 0, 0, scarfQ, "Scarf.png");
        Macshield = new Equipment("Armour", "Armour. +10 Defence. Overpriced shield!", 0, 0, 10, 0, macQ, "macShieldIcon.png");
        Bookshield = new Equipment("Armour", "Armour. +5 Defence. Is there a fine if it's damaged on return?", 0, 0, 5, 0, bookQ, "bookShieldIcon.png");

        MyEquipment.put("Tsquare", Tsquare);
        MyEquipment.put("Scarf", Scarf);
        MyEquipment.put("Macshield", Macshield);
        MyEquipment.put("Bookshield", Bookshield);

        if(!equippedWeapon.equals("None"))
        {
            player.equipItem(equippedWeapon);
        }
        if(!equippedArmour.equals("None"))
        {
            player.equipItem(equippedArmour);
        }
    }
}
