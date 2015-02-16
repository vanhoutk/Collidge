package com.collidge;

/**
 * Created by Kris on 24-Jan-15.
 *
 * Parent class to Equipment and CombatItem classes
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
    int getItemQuantity() { return itemQuantity;}
}
