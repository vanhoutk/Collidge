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

    /**
     * Kris -- Added in variables for equipped Weapon and equipped Armour
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

    /**
     * Kris -- Added in getEquipList to return a list of the Equipment items owned for use in the InventoryState
     */
    public String[] getEquipList(){return items.getEquipmentList();}

//TODO finish move functionality
    private int movesKnown=2;
    private boolean[] attacksList;
    private int[] attackMultipliers={1,2,5,7,10};
    private int[] attackEnergyCosts={0,5,15,75,200};
    private String[] attacksNames={"Bash","Slam","Blast","Spirit","Smash"};

    Player()
    {
        items=new Inventory();
        items.loadInventory();

        level=4;
        attacksList=new boolean[5];
        attacksList[0]=true;
        attacksList[1]=false;
        attacksList[2]=false;
        attacksList[3]=false;
        attacksList[4]=false;



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
        if(moveId<attacksList.length)
        {
            System.out.println("Trying to access the hidden technique (outside range of move array)");

        }
        else
        {
            attacksList[moveId]=true;
            movesKnown++;
        }
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
            levelUp();




        }

    }

    private void levelUp()
    {
        level++;
        updateStats();
        currentEnergy+=health/4;
        currentHealth+=health/4;
    }

    private void updateStats()
    {
        //TODO add levelUp selections
        attack=5+(level);

        defence=0;
        health=5+(level*15);

        intelligence=(int)(level/2.5);

        energy=level*5;
        expTarget=(int)(level*level/1.2)+55;
        equipItem(equippedArmour);
        equipItem(equippedWeapon);

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
     * Kris -- added functions for equipping / unequipping items
     */
    public void equipItem(String item)
    {
        if(item=="None")
        {
            return;
        }
        if(items.getItemType(item) == "Weapon")
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
        if(items.getItemType(item) == "Weapon")
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
}

