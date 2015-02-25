package com.collidge;

/**
 * Created by Kris on 25-Jan-15. Last modified 24-Feb-15.
 *
 * All code in this class was written by Kris.
 *
 * Combat Item Class:
 * - Combat Items are those items usable in combat (i.e. health potions and energy potions)
 * - This class extends (inherits from) the Item class
 *
 * Functions in this class:
 * - CombatItem(String type, String text, int health, int energy, int quantity, String image) -> Constructor
 * - int getHealthRestore()             -> returns the amount of health an item restores
 * - int getEnergyRestore()             -> returns the amount of energy an item restores
 * - int getItemQuantity()              -> returns the quantity of the item
 * - void setItemQuantity(int quantity) -> sets the quantity of the item
 * - void changeItemQuantity(int dQ)    -> increases/decreases the item quantity by dQ
 *
 * Notes:
 */
public class CombatItem extends Item
{
    private int healthRestore;
    private int energyRestore;
    private int itemQuantity;

    CombatItem(String type, String text, int health, int energy, int quantity, String image)
    {
        itemType = type;
        itemText = text;
        healthRestore = health;
        energyRestore = energy;
        itemQuantity = quantity;
        itemImage = image;
    }

    int getHealthRestore()
    {
        return healthRestore;
    }

    int getEnergyRestore()
    {
        return energyRestore;
    }

    int getItemQuantity() { return itemQuantity;}

    void setItemQuantity(int quantity)
    {
        itemQuantity = quantity;
    }

    void changeItemQuantity(int dQ)
    {
        itemQuantity += dQ;
        if(itemQuantity <= 0)
        {
            itemQuantity = 0;
        }
    }
}