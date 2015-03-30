package com.collidge;

/**
 * Created by Kris on 25-Jan-15. Last modified 27-Mar-15.
 *
 * Comments in the class have been added to indicate additions made by Toni & Michael
 *
 * Combat Item Class:
 * - Combat Items are those items usable in combat (i.e. health potions and energy potions)
 * - This class extends (inherits from) the Item class
 *
 * Functions in this class:
 * - CombatItem(String type, String text, int health, int energy, int damage, int quantity, String image) -> Constructor
 * - int getHealthRestore()             -> returns the amount of health an item restores
 * - int getEnergyRestore()             -> returns the amount of energy an item restores
 * - int getEnemyDamage()               -> returns the amount of damage an item does
 * - int getItemQuantity()              -> returns the quantity of the item
 * - String getBattleText()             -> returns string of restore information for use in combat
 * - void setItemQuantity(int quantity) -> sets the quantity of the item
 * - void changeItemQuantity(int dQ)    -> increases/decreases the item quantity by dQ
 *
 * Notes:
 */
public class CombatItem extends Item
{
    private int healthRestore;
    private int energyRestore;
    private int enemyDamage;
    private int itemQuantity;
    private String battleText;

    CombatItem(String type, String text, int health, int energy, int damage, int quantity, String image)
    {
        itemType = type;
        healthRestore = health;
        energyRestore = energy;
        /**
         * Toni's edit
         * Combat tooltip creation and enemy damage parameter
         */
        enemyDamage = damage;
        if (energyRestore != 0 && healthRestore != 0)
        {
            battleText = "Restores "+ healthRestore + " Health & " + energyRestore + " Energy";
        }
        else if (healthRestore != 0)
        {
            battleText = "Restores " + healthRestore + " Health";
        }
        else if (energyRestore != 0)
        {
            battleText = "Restores " + energyRestore + " Energy";
        }
        if (enemyDamage!=0)
        {
            battleText = "Deals " + enemyDamage + " Damage to all enemies";
        }
        /**
         * Creates inventory description by combining a descriptive
         * message with the restore info from battleText
         */
        itemText = text + " " + battleText;

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

    /**
     * Toni's edit
     * Added function for returning the amount of damage an attack item does
     */
    int getEnemyDamage() {return enemyDamage;}

    int getItemQuantity() { return itemQuantity;}

    void setItemQuantity(int quantity)
    {
        itemQuantity = quantity;
    }

    /**
     * Michael's edit
     * Added function for returning the text for battle
     */
    String getBattleText(){ return battleText;}

    void changeItemQuantity(int dQ)
    {
        itemQuantity += dQ;
        if(itemQuantity <= 0)
        {
            itemQuantity = 0;
        }
    }
}