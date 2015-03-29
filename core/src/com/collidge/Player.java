package com.collidge;

import java.util.HashMap;

/**
 * Created by Daniel on 21/01/2015.
 */
public class Player
{
    private int level;
    private int expTarget;
    private int health;
    private int currentHealth;
    private int currentEnergy;
    private int attack;
    private int defence;
    private int energy;
    private int intelligence;
    private double experience;
    public Inventory items;
    public int itemDamage;
    public String itemType;
    public String player_name;


    private int levelUpCounter=0;


    private int attackPoints,defencePoints,intelligencePoints,healthPoints,energyPoints;
    private double attackPointsMult=.5,
            defencePointsMult=.5,
            intelligencePointsMult=.2,
            healthPointsMult=2,
            energyPointsMult=2;


    private int baseAttackPoints=3;
    private int baseDefencePoints=0;
    private int baseIntelligencePoints=1;


    private int baseHealthPoints=20;
    private int baseEnergyPoints=5;

    //Kris Added- Variables for equipped Weapon and equipped Armour

    String equippedWeapon;
    String equippedArmour;


    public String getPlayer_name()
    {
        return player_name;
    }

    public int getBaseAttackPoints()
    {
        return baseAttackPoints;
    }

    public int getBaseDefencePoints()
    {
        return baseDefencePoints;
    }

    public int getBaseIntelligencePoints()
    {
        return baseIntelligencePoints;
    }

    public int getBaseHealthPoints()
    {
        return baseHealthPoints;
    }

    public int getBaseEnergyPoints()
    {
        return baseEnergyPoints;
    }

    public double getAttackPointsMult()
    {
        return attackPointsMult;
    }

    public double getDefencePointsMult()
    {
        return defencePointsMult;
    }

    public double getIntelligencePointsMult()
    {
        return intelligencePointsMult;
    }

    public double getHealthPointsMult()
    {
        return healthPointsMult;
    }

    public double getEnergyPointsMult()
    {
        return energyPointsMult;
    }


    public int getAttackPoints()
    {
        return attackPoints;
    }

    public int getDefencePoints()
    {
        return defencePoints;
    }

    public int getIntelligencePoints()
    {
        return intelligencePoints;
    }

    public int getHealthPoints()
    {
        return healthPoints;
    }

    public int getEnergyPoints()
    {
        return energyPoints;
    }

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public int getCurrentEnergy()
    {
        return currentEnergy;
    }

    public int getMovesKnown()
    {
        return movesKnown;
    }

    public String[] getItemList()
    {
        return items.getList();
    }

    public String getItemImage(String item) {return items.getItemImage(item);}

    public String[] getItemDesc() { return items.getDescription();}

    public int getLevelUpCounter()
    {
        return levelUpCounter;
    }

    public void addStat(int statId) {
        switch(statId)
        {
            case 0:
                healthPoints++;
                break;
            case 1:
                defencePoints++;
                break;
            case 2:
                attackPoints++;
                break;
            case 3:
                energyPoints++;
                break;
            case 4:
                intelligencePoints++;
                break;
        }
        levelUpCounter--;
        levelUp();

    }
    /**
     * Kris Added
     * - String[] getEquipList() -> returns a list of the Equipment items owned for use in the InventoryState
     */
    public String[] getEquipList(){return items.getEquipmentList();}


    private int movesKnown=5;
    //constructor for combat moves is multiplier, energy cost, aoe, image
    CombatMove Bash = new CombatMove(1, 0, 0, "Bash", "bash.png");
    CombatMove Slam = new CombatMove(2, 5, 0, "Slam", "slam.png");
    CombatMove Blast = new CombatMove(2, 15, 1, "Blast", "blast.png");
    CombatMove Spirit = new CombatMove(2, 75, 10, "Spirit", "spirit.png");
    CombatMove Smash = new CombatMove(10, 200, 0, "Smash", "smash.png");

    private HashMap<String, CombatMove> StringToCombatMove = new HashMap();

    public CombatMove[] getCombatMoves(){
        CombatMove[] Moves = {Bash, Slam, Blast, Spirit, Smash};
        for (int i =0; i < movesKnown; i++){
            StringToCombatMove.put(Moves[i].attackName, Moves[i]);
        }
        return Moves;}

    public int getEnergyCost(String move){return StringToCombatMove.get(move).attackEnergyCost;}
    public int getAttackMultiplier(String move){return StringToCombatMove.get(move).attackMultiplier;}
    public int attackRange(String move)
    {

        if (move.equals("IED"))
        {
            return 10;
        }

        System.out.println("moveName is " + StringToCombatMove.get(move).attackName);
        return StringToCombatMove.get(move).attackMultiplier;

        //TODO change to allow other damaging items too
    }

    Player()
    {
        level=1;
        player_name = "Mr Man";



        attackPoints=0;
        defencePoints=0;
        intelligencePoints=0;
        healthPoints=0;
        energyPoints=0;

        //Kris -- Initialise the equipped variables to none
        equippedWeapon = "None";
        equippedArmour = "None";

        items = new Inventory();
        items.loadInventory(this);
        updateStats();
        healAll();
    }

    Player(int Level, int ATK,int DEF, int INT,int HP,int EN, int EXP)
    {
        player_name = "Mr Man";
        level=Level;

        attackPoints=ATK;
        defencePoints=DEF;
        intelligencePoints=INT;
        healthPoints=HP;
        energyPoints=EN;
        experience=EXP;

        /**
         * Kris
         * Initialise the equipped variables to none
         */
        equippedWeapon = "None";
        equippedArmour = "None";

        items = new Inventory();
        items.loadInventory(this);
        updateStats();
        healAll();
    }

    Player(String name,int Level, int ATK,int DEF, int INT,int HP,int EN, int EXP)
    {
        player_name = name;
        level=Level;

        attackPoints=ATK;
        defencePoints=DEF;
        intelligencePoints=INT;
        healthPoints=HP;
        energyPoints=EN;
        experience=EXP;

        //Kris -- Initialise the equipped variables to none
        equippedWeapon = "None";
        equippedArmour = "None";

        items = new Inventory();
        items.loadInventory(this);
        updateStats();
        healAll();
    }

    public int getExpTarget()
    {
        return expTarget;
    }


    public void learnMove(int moveId)
    {
        movesKnown++;
    }

    public void healAll()
    {
        currentEnergy=energy;
        currentHealth=health;
    }

    public void useItem(String item)
    {
        items.useItem(this,item);
        //itemDamage is needed to specify when an item does enemy damage
        itemDamage = items.getItemDamage(item);
    }

    public void changeHealth(int dH)
    {
        currentHealth=currentHealth+dH;

        if(currentHealth<=0)
        {
            //TODO add death state
            System.out.println("You have died");
            currentHealth=0;
        }
        else if(currentHealth>health)
        {
            currentHealth=health;

        }
    }

    public void changeEnergy(int dE)
    {
        currentEnergy+=dE;
        if(currentEnergy>=energy)
        {
            currentEnergy=energy;
        }
        else if(currentEnergy<0)
        {
            currentEnergy=0;
        }
    }

    public int getExperience()
    {
        return (int) experience;
    }

    public double getTrueExperience()
    {
        return experience;
    }

    public int getLevel()
    {
        return level;
    }

    public void addExperience(int newExp)
    {
        experience+=newExp;
        while(experience>=expTarget)
        {
            experience-=expTarget;
            levelUpCounter++;
            expTarget=((int)Math.pow((level+levelUpCounter),1.5))+5;

        }

    }
    public void addExperience(double newExp)
    {
        experience+=newExp;
        while(experience>=expTarget)
        {
            experience-=expTarget;
            levelUpCounter++;
            expTarget=((int)Math.pow((level+levelUpCounter),1.5))+5;

        }
    }


    private void levelUp()
    {
        level++;
        currentEnergy+=health/3;
        currentHealth+=health/3;
        updateStats();
        changeHealth(0);
        changeEnergy(0);


    }

    private void updateStats()
    {
        //TODO add levelUp selections
        String weapon,armour;
        weapon=equippedWeapon;
        armour=equippedArmour;
        unequipItem(equippedWeapon);
        unequipItem(equippedArmour);
        attack=baseAttackPoints+(int)((level+attackPoints)*attackPointsMult);

        defence=baseDefencePoints+(int)((defencePoints+level)*defencePointsMult);
        health=baseHealthPoints+(int)((level+healthPoints)*healthPointsMult);

        intelligence=baseIntelligencePoints+(int)((level+intelligencePoints)*intelligencePointsMult);

        energy=baseEnergyPoints+(int)((level+energyPoints)*energyPointsMult);
        //TODO figure out a good curve for xp to follow
        expTarget=((int)Math.pow((level),1.5))+5;
        //expTarget=1;

        equipItem(weapon);
        equipItem(armour);

    }

    public int getHealth()
    {
        if (health > 500)
        {
            health = 500;
        }
        return health;
    }

    public int getAttack()
    {
        if (attack > 100)
        {
            attack = 100;
        }
        return attack;
    }

    public int getDefence()
    {
        if (defence > 100)
        {
            defence = 100;
        }
        return defence;
    }

    public int getEnergy()
    {
        if (energy > 500)
        {
            energy = 500;
        }
        return energy;
    }

    public int getIntelligence()
    {
        if (intelligence > 100)
        {
            intelligence = 100;
        }
        return intelligence;
    }

    /**
     * Kris -- Start
     * Added functions for equipping / unequipping items
     */
    public void equipItem(String item)
    {
        if(item.equals("None"))
        {
            return;
        }
        else if (item.equals("")) {
            return;
        }
        else if(items.getItemType(item).equals("Weapon"))
        {
            if(!equippedWeapon.equals("None"))
            {
                unequipItem(equippedWeapon);
            }

            attack += items.getAttackBonus(item);
            //energy += items.getEnergyBonus(item);
            equippedWeapon = item;
        }
        else
        {
            if(!equippedArmour.equals("None"))
            {
                unequipItem(equippedArmour);
            }

            defence += items.getDefenceBonus(item);
            //health += items.getHealthBonus(item);
            equippedArmour = item;
        }
    }

    public void unequipItem(String item)
    {
        if(item.equals("None"))
        {
            return;
        }
        else if (item.equals("")) {
            return;
        }
        else if(items.getItemType(item).equals("Weapon"))
        {
            attack -= items.getAttackBonus(item);
            //energy -= items.getEnergyBonus(item);
            equippedWeapon = "None";
        }
        else
        {
            defence -= items.getDefenceBonus(item);
            //health -= items.getHealthBonus(item);
            equippedArmour = "None";
        }
    }
    /**
     * Kris -- end
     */
}

