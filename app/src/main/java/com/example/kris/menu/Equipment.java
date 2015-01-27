package com.example.kris.menu;

/**
 * Created by Kris on 25-Jan-15.
 *
 * Class which contains all of the weapons and armour
 *
 * Variables in this function: attackbonus, energybonus, healthbonus, defencebonus.
 * Armour will have values for health and defence bonus
 * Weapons will have values for attack and energy bonus
 * Subject to change if the combat team want different attributes to be increased
 */
public class Equipment extends Item
{
    private int attackbonus;
    private int energybonus;
    private int healthbonus;
    private int defencebonus;

    /**
     * Empty Constructor
     */
    Equipment(/*String name*/)
    {
        attackbonus = 0;
        energybonus = 0;
        healthbonus = 0;
        defencebonus = 0;
        //itemName = name;
        itemType = " ";
        itemText = " ";
    }

    /**
     * Constructor will need to have String itemImage added to it once the images are done (or once the inventoryMenu
     * class has been started)
     */
    Equipment(/*String name, */ String type, String text, int attack, int energy, int defence, int health)
    {
        //itemName = name;
        itemText = text;
        itemType = type;
        attackbonus = attack;
        energybonus = energy;
        defencebonus = defence;
        healthbonus = health;
    }

    int getAttackbonus()
    {
        return attackbonus;
    }

    int getEnergybonus()
    {
        return energybonus;
    }

    int getHealthbonus()
    {
        return healthbonus;
    }

    int getDefencebonus()
    {
        return defencebonus;
    }
}
