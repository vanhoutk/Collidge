package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kris on 24-Jan-15. Last modified 27-Mar-15.
 *
 * Comments have been added by Kris to indicate additions made by Toni, Dan & Michael
 *
 * Functions in this class:
 * - void useItem(String item)          -> uses a Combat item to restore either the Player's Health or Energy
 * - int getAttackBonus(String item)    -> returns the attack bonus of the item
 * - int getDefenceBonus(String item)   -> returns the defence bonus of the item
 * - int getItemDamage(String item)     -> returns the damage the item does to enemies
 * - String[] getList()                 -> returns a String array of items of which we have a quantity greater than zero
 * - void loadInventory()               -> sets up the inventory in the first place, initialising all of the items
 *
 * Notes:
 * - TODO: May either move useItem into player, or equipItem into inventory. Will see.
 * - TODO: Rebalance items
 */

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

    int getAttackBonus(String item){ return MyEquipment.get(item).getAttackbonus();}
    int getDefenceBonus(String item){ return MyEquipment.get(item).getDefencebonus();}
    /**
     * Toni's edit
     * Added for returning the damage caused by attack combat items
     */
    int getItemDamage(String item) {return MyCombatInv.get(item).getEnemyDamage();}

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

    /**
     * Michael's Edit
     * Added for returning an array of the item descriptions for use in combat
     */
    String[] getDescription()
    {
        /**
         * Dan's edit
         * Changed from a Set to an arrayList
         */
        //Set<String> description = new HashSet();
        ArrayList<String> description=new ArrayList();

        for(Map.Entry<String, CombatItem> entry: MyCombatInv.entrySet())
        {
            if(entry.getValue().getItemQuantity() > 0)
            {
                description.add(entry.getValue().getBattleText());
            }
        }
        /**
         * Dan's edit
         * Changed the return value to the following
         */
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

    void loadInventory(Player player)
    {
        /**
         * Deirdre
         * Loading inventory items from saved file if it exists
         */
        int nCoffee = 10, nEnergyDrink = 10, nNoodles = 10, nSandwich = 10, nIED = 5;
        int nTsquare = 1, nScarf = 1, nMacshield = 1, nBookshield = 1;
        String nEquipWep = "None", nEquipArm = "None";

        if(Gdx.files.isLocalStorageAvailable() && Gdx.files.local("inventory.xml").exists())
        {
            try
            {
                XmlReader reader = new XmlReader();
                FileHandle handle1 = Gdx.files.local("inventory.xml");
                XmlReader.Element root = reader.parse(handle1.readString());
                XmlReader.Element inventory = root.getChildByName("items");
                nTsquare = inventory.getInt("Tsquare");
                nScarf= inventory.getInt("Scarf");
                nMacshield=inventory.getInt("Macshield");
                nBookshield=inventory.getInt("Bookshield");
                nCoffee=inventory.getInt("Coffee");
                nEnergyDrink=inventory.getInt("EnergyDrink");
                nNoodles=inventory.getInt("Noodles");
                nSandwich=inventory.getInt("Sandwich");
                nIED=inventory.getInt("IED");

                XmlReader.Element equipped = root.getChildByName("equipped");
                nEquipArm = equipped.getChildByName("EquippedArmour").getText();
                nEquipWep = equipped.getChildByName("EquippedWeapon").getText();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            System.out.println("Local storage unavailable or file doesn't exist.");
        }

        /**
         * Constructor for combat items is (Type, Text, Health, Energy, Enemy Damage, Quantity, Image)
         */
        CombatItem Coffee, EnergyDrink, Noodles, Sandwich, IED;
        Coffee = new CombatItem("Energy", "Energy Item. Mmm that's good coffee!", 0, 10, 0, nCoffee, "Coffee_cupSmall.png");
        EnergyDrink = new CombatItem("Energy", "Energy Item. Buzzing!", 0, 20, 0, nEnergyDrink, "energy60.png");
        Noodles = new CombatItem("Health", "Health Item. Instant Goodness", 10, 0, 0, nNoodles, "Noodles.png");
        Sandwich = new CombatItem("Health", "Health Item. Needs more mayo!", 20, 0, 0, nSandwich, "sandwichIcon.png");
        IED = new CombatItem("Attack", "Damaging Item. Improvised Explosive Device. Home Made!", 0, 0, 10, nIED, "dynamite.png");


        /**
         * Constructor for equipment is (Type, Text, Attack, Energy, Defence, Health, Quantity, Image)
         */
        Equipment Tsquare, Scarf, Macshield, Bookshield;
        Tsquare = new Equipment("Weapon", "Weapon. +10 Attack. The sign of a true engineer.", 10, 0, 0, 0, nTsquare, "tsquareSmall.png");
        Scarf = new Equipment("Weapon", "Weapon. +5 Attack. McDonalds Manager, here I come!", 5, 0, 0, 0, nScarf, "Scarf.png");
        Macshield = new Equipment("Armour", "Armour. +10 Defence. Overpriced shield!", 0, 0, 10, 0, nMacshield, "macShieldIcon.png");
        Bookshield = new Equipment("Armour", "Armour. +5 Defence. Is there a fine if it's damaged on return?", 0, 0, 5, 0, nBookshield, "bookShieldIcon.png");

        MyCombatInv.put("Coffee", Coffee);
        MyCombatInv.put("Energy Drink", EnergyDrink);
        MyCombatInv.put("Noodles", Noodles);
        MyCombatInv.put("Sandwich", Sandwich);
        MyCombatInv.put("IED", IED);

        MyEquipment.put("Tsquare", Tsquare);
        MyEquipment.put("Scarf", Scarf);
        MyEquipment.put("Macshield", Macshield);
        MyEquipment.put("Bookshield", Bookshield);

        if(!nEquipWep.equals("None"))
        {
            player.equipItem(nEquipWep);
        }
        if(!nEquipArm.equals("None"))
        {
            player.equipItem(nEquipArm);
        }
    }
}
