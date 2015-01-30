package com.example.collidge.collidge;

/**
 * Created by Daniel on 21/01/2015.
 */
public class Player
{
    private int level;

    private int health;

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public int getCurrentEnergy()
    {
        return currentEnergy;
    }

    private int currentHealth;
    private int currentEnergy;
    private int attack;
    private int defence;
    private int energy;
    private int intelligence;
    private int experience;
    private Inventory items;


    public int getMovesKnown()
    {
        return movesKnown;
    }
    public String[] getItemList()
    {
        return items.getList();
    }
//TODO finish move functionality
    private int movesKnown=5;
    private boolean[] attacksList;
    private int[] attackMultipliers={1,2,5,7,10};
    private int[] attackEnergyCosts={0,5,15,75,200};
    private String[] attacksNames={"Bash","Slam","Blast","Collidge Spirit","Go Hard"};
    Player()
    {
        items=new Inventory();
        items.loadInventory();

        level=10;
        attacksList=new boolean[5];
        attacksList[0]=true;
        attacksList[1]=false;
        attacksList[2]=false;
        attacksList[3]=false;
        attacksList[4]=false;
        updateStats();


    }

    public int[] getAttackEnergyCosts()
    {
    return attackEnergyCosts;
    }
    public String[] getAttacksNames()
    {
        return attacksNames;
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
        while(experience>=100)
        {
            levelUp();


        }

    }
    private void levelUp()
    {

        level++;

        experience-=100;


        //placeholders for clarity
        System.out.println("Level Up to Level:"+level);
        System.out.println("Hp +15");
        System.out.println("Attack +2");
        System.out.println("Defence +1");
        System.out.println("Energy +10");
        System.out.println("Intelligence +1");
        updateStats();
    }
    private void updateStats()
    {
        //TODO add levelUp selections
        attack=5+(level*2);

        defence=level;
        health=5+(level*15);

        intelligence=level;

        energy=level*10;

        currentEnergy=energy;
        currentHealth=health;


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



}

