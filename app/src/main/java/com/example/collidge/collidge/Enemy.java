package com.example.collidge.collidge;

/**
 * Created by Daniel on 21/01/2015.
 */
public class Enemy
{
    private int maxHealth,currentHealth,attack,defence;
    private boolean dead;

    public int getExpValue()
    {
        return expValue;
    }

    private int expValue;
    public int enemyId;
    Enemy(int h, int a, int d,int exp)
    {
        maxHealth=h;
        attack=a;
        defence=d;
        expValue=exp;
        currentHealth=maxHealth;
        dead=false;
    }
    Enemy()
    {
        maxHealth=20;
        attack=3;
        defence=2;
        expValue=30;
        currentHealth=maxHealth;
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
    public int getDefence()
    {
        return defence;
    }
    public int getAttack()
    {
        return attack;
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
