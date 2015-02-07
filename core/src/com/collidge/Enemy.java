package com.collidge;

import java.util.Random;

/**
 * Created by Daniel on 21/01/2015.
 */
public class Enemy
{
    private int maxHealth,currentHealth,attack,defence;
    private boolean dead;
    String name;

    private Random rand=new Random();
    public int getExpValue()
    {
        return expValue;
    }

    private int expValue;

    Enemy(String nm, int h, int a, int d,int exp)
    {
        name=nm;
        maxHealth=h;
        attack=a;
        defence=d;
        expValue=exp;
        currentHealth=maxHealth;
        dead=false;
    }

    Enemy(Enemy template)
    {

        maxHealth=template.maxHealth;
        attack=template.attack;
        defence=template.getDefence();
        expValue=template.getExpValue();
        name=template.getName();

        maxHealth+=(int)(maxHealth*(rand.nextDouble()-.5));
        attack+=(int)(attack*(rand.nextDouble()-.5));
        defence+=(int)(defence*(rand.nextDouble()-.5));
        expValue+=(int)(expValue*(rand.nextDouble()-.5));
        currentHealth=maxHealth;

       // System.out.println(this.getName()+": "+this.maxHealth+"hp -ATK:"+this.attack+"  DEF:"+this.defence+"  EXP:"+this.expValue);
        dead=false;



    }

    public boolean getDead()
    {
        return dead;
    }
    public int getHealth()
    {
        return currentHealth;
    }
    public int getMaxHealth()
    {
        return maxHealth;
    }
    public int getDefence()
    {
        return defence;
    }
    public int getAttack()
    {
        return attack;
    }
    public String getName()
    {
        return name;
    }

    public void changeHealth(int dH)
    {
        currentHealth+=dH;
        if(currentHealth>maxHealth)
        {
            currentHealth=maxHealth;
        }
        else if(currentHealth<=0)
        {
            dead=true;
        }
    }


}
