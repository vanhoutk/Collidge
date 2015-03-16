package com.collidge;

/**
 * Created by Kris on 24-Jan-15. Last modified 24-Feb-15.
 *
 * All code in this class was written by Kris.
 *
 * Item Class:
 * - Parent class to Equipment and CombatItem classes
 * - Contains variables and methods which both Equipment and CombatItems use
 *
 * Functions in this class:
 * - String getItemImage()              -> returns the name of the itemImage
 * - String getItemText()               -> returns the text describing the item
 * - String getItemType()               -> returns the itemType
 *
 * Notes:
 */

public class Item
{
    String itemType;
    String itemText;
    String itemImage;
    int itemQuantity;

    String getItemImage() { return itemImage;}
    String getItemText() { return itemText;}
    String getItemType() { return itemType;}
}
