package com.collidge;

import java.io.IOException;
import java.io.OutputStream;
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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

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
   // public static Music elevatorMusic = Gdx.audio.newMusic(Gdx.files.internal("elevatormusic.mp3"));

    Equipment Tsquare, Scarf, Macshield, Bookshield;
    CombatItem Coffee, EnergyDrink, Noodles, Sandwich, IED;

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

    void loadInventory()
    {
       // elevatorMusic.play();
        /**
         * Constructor for combat items is (Type, Text, Health, Energy, Enemy Damage, Quantity, Image)
         */
       // CombatItem Coffee, EnergyDrink, Noodles, Sandwich, IED;

        Coffee = new CombatItem("Energy", "Energy Item. Mmm that's good coffee!", 0, 10, 0, 10, "Coffee_cupSmall.png");
        EnergyDrink = new CombatItem("Energy", "Energy Item. Buzzing!", 0, 10, 0, 10, "energy60.png");
        Noodles = new CombatItem("Health", "Health Item. Instant Goodness", 10, 0, 0, 10, "Noodles.png");
        Sandwich = new CombatItem("Health", "Health Item. Needs more mayo!", 10, 0, 0, 10, "sandwichIcon.png");
        IED = new CombatItem("Attack", "Deals damage to all enemies.", 0, 0, 10, 10, "dynamite.png");

        MyCombatInv.put("Coffee", Coffee);
        MyCombatInv.put("Energy Drink", EnergyDrink);
        MyCombatInv.put("Noodles", Noodles);
        MyCombatInv.put("Sandwich", Sandwich);
        MyCombatInv.put("Improvised Explosive Device", IED);

        /**
         * Constructor for equipment is (Type, Text, Attack, Energy, Defence, Health, Quantity)
         */
        //Equipment Tsquare, Scarf, Macshield, Bookshield;

        Tsquare = new Equipment("Weapon", "Weapon. +10 Attack. The sign of a true engineer.", 10, 0, 0, 0, 1, "tsquareSmall.png");
        Scarf = new Equipment("Weapon", "Weapon. +5 Attack. McDonalds Manager, here I come!", 5, 0, 0, 0, 1, "Scarf.png");
        Macshield = new Equipment("Armour", "Armour. +10 Defence. Overpriced shield!", 0, 0, 10, 0, 1, "macShieldIcon.png");
        Bookshield = new Equipment("Armour", "Armour. +5 Defence. Is there a fine if it's damaged on return?", 0, 0, 5, 0, 1, "bookShieldIcon.png");

        MyEquipment.put("Tsquare", Tsquare);
        MyEquipment.put("Scarf", Scarf);
        MyEquipment.put("Macshield", Macshield);
        MyEquipment.put("Bookshield", Bookshield);
    }

    /**
     * Deirdre
     * Saving Inventory
     */
    public void saveInventory(){
        if(Gdx.files.isLocalStorageAvailable()) {
            OutputStream out = Gdx.files.local("inventory.xml").write(false);
            try {
                System.out.println("Saving inventory");
                String saveInventory;
                saveInventory = ("<inventory>"
                        + "<Tsquare>" + Tsquare.getItemQuantity() + "</Tsquare>"
                        + "<Scarf>" + Scarf.getItemQuantity()+ "</Scarf>"
                        + "<Macshield>" + Macshield.getItemQuantity() + "</Macshield>"
                        + "<Bookshield>" + Bookshield.getItemQuantity() + "</Bookshield>"
                        + "<Coffee>" + Coffee.getItemQuantity() + "</Coffee>"
                        + "<EnergyDrink>" + EnergyDrink.getItemQuantity() + "</EnergyDrink>"
                        + "<Noodles>" + Noodles.getItemQuantity() +"</Noodles>"
                        + "<Sandwich>" + Sandwich.getItemQuantity() + "</Sandwich>"
                        + "<IED>" + IED.getItemQuantity() + "</IED>");
                out.write(saveInventory.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    out.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }






}
