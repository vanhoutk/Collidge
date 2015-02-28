package com.collidge;

/**
 * Created by Daniel on 06/02/2015.
 */
public class TargetPicker
{
    Enemy[] monsters;
    private int currentTarget;
    private int selectedTarget;
    public boolean targetSelected;



    TargetPicker(Enemy[] enemies)
    {

       reset(enemies);
    }

    public void Left()
    {

        currentTarget--;
        if(currentTarget<0||monsters[currentTarget].getDead())
        {
            if(currentTarget<0)
            {
                currentTarget=monsters.length;
            }
            Left();
        }
    }
    public void Right()
    {

        currentTarget++;
        if(currentTarget>=monsters.length||monsters[currentTarget].getDead())
        {
            if(currentTarget>=monsters.length)
            {
                currentTarget%=monsters.length;
                currentTarget--;
            }
            Right();
        }
    }
    public void Select()
    {
        selectedTarget=currentTarget;
        targetSelected=true;

    }

    public int getCurrentTarget()
    {
        return currentTarget;
    }

    public void reset(Enemy[] enemies)
    {
        int enemyCount=0;
        for(int i=0;i<enemies.length;i++)
        {
            if(!enemies[i].getDead())
            {
                currentTarget=i;
                enemyCount++;
            }
        }
        if(enemyCount>1)
        {
            monsters=enemies;
            targetSelected=false;
        }
        else
        {
            selectedTarget=currentTarget;

        }
    }
    public int getSelectedTarget()
    {
        return selectedTarget;
    }
}
