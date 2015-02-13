package com.collidge;

/**
 * Created by Kris on 25-Jan-15.
 */
public class CombatItem extends Item
{
    private int healthRestore;
    private int energyRestore;
    private int itemQuantity;


    CombatItem(/*String name*/)
    {
        //itemName = name;
        itemType = " ";
        itemText = " ";
        healthRestore = 0;
        energyRestore = 0;
        itemQuantity = 0;
        itemImage = "";
    }

    CombatItem(/*String name, */String type, String text, int health, int energy, int quantity, String image)
    {
        //itemName = name;
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

    int getItemQuantity()
    {
        return itemQuantity;
    }

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