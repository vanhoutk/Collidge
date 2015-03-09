package com.collidge;

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
    private int experience;
    public Inventory items;
    public String player_name;


    private int levelUpCounter=0;


    private int attackPoints,defencePoints,intelligencePoints,healthPoints,energyPoints;
    private double attackPointsMult=.8,
            defencePointsMult=.3,
            intelligencePointsMult=.5,
            healthPointsMult=3.5,
            energyPointsMult=3.5;


    private int baseAttackPoints=3,
            baseDefencePoints=0,
            baseIntelligencePoints=1,
            baseHealthPoints=20,
            baseEnergyPoints=5;

    //Kris Added- Variables for equipped Weapon and equipped Armour

    String equippedWeapon;
    String equippedArmour;

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

    public String[] getItemDesc() { return items.getDesc();}

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
    private int[] attackMultipliers={1,2,5,7,10};
    private int[] attackEnergyCosts={0,5,15,75,200};
    private String[] attacksNames={"Bash","Slam","Blast","Spirit","Smash"};
    //private String[] attackDesc = {"1 Dmg, 0 En", "2 Dmg, 5 En" , "5 Dmg, 15 En" , "7 Dmg, 75 En", "10 Dmg, 200 En"};       //attack tooltips for FightMenu
    private String[] attackDesc = {attackMultipliers[0] + "Dmg, " + attackEnergyCosts[0] + "En",    //attack tooltips for FightMenu
            attackMultipliers[1] + "Dmg, " + attackEnergyCosts[1] + "En",
            attackMultipliers[2] + "Dmg, " + attackEnergyCosts[2] + "En",
            attackMultipliers[3] + "Dmg, " + attackEnergyCosts[3] + "En",
            attackMultipliers[4] + "Dmg, " + attackEnergyCosts[4] + "En"};

    Player()
    {
        items = new Inventory();
        items.loadInventory();

        level=1;
        player_name = "Mr Man";



        attackPoints=0;
        defencePoints=0;
        intelligencePoints=0;
        healthPoints=0;
        energyPoints=0;

        //Kris -- Start off with no armour/weapons equipped
        equippedWeapon = "None";
        equippedArmour = "None";
        updateStats();
        healAll();
    }

    Player(int Level, int ATK,int DEF, int INT,int HP,int EN, int EXP)
    {
        items = new Inventory();
        items.loadInventory();
        player_name = "Mr Man";
        level=Level;

        attackPoints=ATK;
        defencePoints=DEF;
        intelligencePoints=INT;
        healthPoints=HP;
        energyPoints=EN;
        experience=EXP;

        //Kris -- Start off with no armour/weapons equipped
        equippedWeapon = "None";
        equippedArmour = "None";
        updateStats();
        healAll();
    }


    public int[] getAttackEnergyCosts()
    {
        return attackEnergyCosts;
    }

    public String[] getAttacksNames()
    {
        return attacksNames;
    }

    public String[] getAttackDesc(){return attackDesc;}

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
    }

    public int attackPicker(String moveName)
    {
        for(int i=0;i<attacksNames.length;i++)
        {
            if(moveName.equals(attacksNames[i]))
            {
                this.changeEnergy(-(this.attackEnergyCosts[i]));
                return attackMultipliers[i];
            }
        }
        return 0;
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
        expTarget=((int)Math.pow(level,1.5))+5;
        //expTarget=1;

        equipItem(weapon);
        equipItem(armour);

    }

    public int getHealth()
    {
        return health;
    }

    public int getAttack()
    {
        return attack;
    }

    public int getDefence()
    {
        return defence;
    }

    public int getEnergy()
    {
        return energy;
    }

    public int getIntelligence()
    {
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

