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


    private int levelUpCounter=0;
    private int attackPoints,defencePoints,intelligencePoints,healthPoints,energyPoints;

    /**
     * Kris Added
     * - Variables for equipped Weapon and equipped Armour
     */
    String equippedWeapon;
    String equippedArmour;

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
    public int getLevelUpCounter()
    {
        return levelUpCounter;
    }


    public void addStat(int statId)
    {
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


    private int movesKnown=2;
    private int[] attackMultipliers={1,2,5,7,10};
    private int[] attackEnergyCosts={0,5,15,75,200};
    private String[] attacksNames={"Bash","Slam","Blast","Spirit","Smash"};

    Player()
    {
        items = new Inventory();
        items.loadInventory();

        level=1;




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

    public int[] getAttackEnergyCosts()
    {
        return attackEnergyCosts;
    }

    public String[] getAttacksNames()
    {
        return attacksNames;
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
    }

    public int attackPicker(String moveName)
    {
        for(int i=0;i<attacksNames.length;i++)
        {
            if(moveName==attacksNames[i])
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


    }

    private void updateStats()
    {
        //TODO add levelUp selections
        String weapon,armour;
        weapon=equippedWeapon;
        armour=equippedArmour;
        unequipItem(equippedWeapon);
        unequipItem(equippedArmour);
        attack=5+(level+attackPoints);

        defence=defencePoints/2+1;
        health=20+((level+healthPoints)*5);

        intelligence=(level/3)+intelligencePoints+1;

        energy=(level+energyPoints)*5;
        //TODO put actual exp value back in after testing is over
        //expTarget=(level*level)+20;
        expTarget=1;

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
        else if(items.getItemType(item) == "Weapon")
        {
            if(equippedWeapon != "None")
            {
                unequipItem(equippedWeapon);
            }

            attack += items.getAttackBonus(item);
            //energy += items.getEnergyBonus(item);
            equippedWeapon = item;
        }
        else
        {
            if(equippedArmour != "None")
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
        else if(items.getItemType(item) == "Weapon")
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

