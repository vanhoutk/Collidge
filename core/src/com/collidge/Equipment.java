package com.collidge;

/**
 * Created by Kris on 25-Jan-15. Last modified 24-Feb-15.
 *
 * All code in this class was written by Kris.
 *
 * Equipment Class:
 * - Equipment refers to items worn to increase stats (i.e. weapons and armour)
 * - This class extends (inherits from) the Item class
 *
 * Functions in this class:
 * - Equipment(String type, String text, int attack, int energy, int defence, int health, int quantity, String image) -> Constructor
 * - int getAttackBonus()           -> returns the attack bonus of the item
 * - int getHealthBonus()           -> returns the health bonus of the item
 * - int getEnergyBonus()           -> returns the energy bonus of the item
 * - int getDefenceBonus()          -> returns the defence bonus of the item
 *
 * Notes:
 * - Currently weapons only add to attack and armour only adds to defence.
 * - TODO: May change the health/energy bonus to increase other stats instead
 * - TODO: May add other equipment types, i.e. accessories
 */
public class Equipment extends Item
{
    private int attackbonus;
    private int energybonus;
    private int healthbonus;
    private int defencebonus;

    Equipment(String type, String text, int attack, int energy, int defence, int health, int quantity, String image)
    {
        itemText = text;
        itemType = type;
        attackbonus = attack;
        energybonus = energy;
        defencebonus = defence;
        healthbonus = health;
        itemQuantity = quantity;
        itemImage = image;
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