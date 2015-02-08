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

    public void Left()
    {

    }
}
