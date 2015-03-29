package com.collidge;

/**
 * Created by Toni on 29/03/2015.
 * Created because this makes it a lot easier to have different images for different moves in the fight menu.
 */
public class CombatMove {
    public int attackMultiplier;
    public int attackEnergyCost;
    public int attackAOE;
    public String attackDesc;
    public String attackName;
    public String attackImage;

    CombatMove(int multiplier, int energyCost, int AOE, String name, String image){
        attackMultiplier = multiplier;
        attackEnergyCost = energyCost;
        attackAOE = AOE;
        attackDesc = attackMultiplier + "Dmg, " + attackEnergyCost + "En";
        attackName = name;
        attackImage = image;
    }
}
