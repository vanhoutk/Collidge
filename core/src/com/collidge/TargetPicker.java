package com.collidge;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Daniel on 06/02/2015.
 */
public class TargetPicker
{
    Enemy[] monsters;
    private int currentTarget;
    private int selectedTarget;
    public boolean targetHighlighted;   //true when the target has been tapped and square box is displayed around them
    public boolean targetSelected;      //true when target is selected, go to next state (e.g. combo)


    private int targetingId;

    TargetPicker(Enemy[] enemies,int targetArea)
    {
        //texture=new Texture("arrow_up_blue.png");
        //sprite=new Sprite(texture);
        reset(enemies,targetArea);
    }

    public void Left()
    {

        System.out.println("L");
        currentTarget--;
        if(currentTarget<0||monsters[currentTarget].getDead())
        {
            if(currentTarget<0)
            {
                currentTarget=monsters.length;
            }

            Left();
        }
        System.out.println(currentTarget);
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
                System.out.println("X");
            }
            System.out.println("O");

            Right();

        }
        System.out.println("R");
        System.out.println(currentTarget);
    }

    public void goTo(int i)
    {
        if(currentTarget==i)
        {
            selectedTarget=i;
            targetSelected=true;
        }
        else
        {
            currentTarget=i;
        }
    }

    public void Target(int id)
    {
        if (currentTarget==id && targetHighlighted){
            Select();
        }
        else {
            currentTarget = id;
            System.out.println("enemy id " + id);
            targetHighlighted = true;
            targetSelected = false;
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

    public void reset(Enemy[] enemies,int targetingArea)
    {

        targetingId=targetingArea;
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
            targetHighlighted=false;
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
    public int getTargetingId()
    {
        return targetingId;
    }
}
