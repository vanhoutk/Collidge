package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

import java.lang.annotation.Target;

/**
 * Created by Daniel on 22/01/2015.
 */
public class Combo
{
    double startTime;
    double startX,startY;
    double endX,endY;
    double targetX, targetY;
    boolean comboing;
    double swipeSkill;
    Combo()
    {

    }
    double initiateCombo(int moveId,Fight fight)
    {
        switch(moveId)
        {
            case 0:
                return basicAttack();

            default:
                return 0;
        }
    }


    double basicAttack()
    {
        double skill=0;
        targetX=0;
        targetY=1;

        comboing=true;

        //TODO correct for the expected output of the swipe function
        return skill;

    }
    double swipe(double dx,double dy,double x1,double y1,double x2,double y2)
    {
        double diff=Math.tan((x1-x2)/(y1-y2))-Math.tan(dx/dy);
        diff/=5;
        if(diff>10)
        {
            return 0;
        }
        else
        {
            return (10/diff);
        }

    }

    void touchDown(float x,float y)
    {

        startX=x;
        startY=y;
    }

    void touchUp(float x, float y)
    {
        endX=x;
        endY=y;
        if(Math.abs((endX-startX)*(endY*startY))>.1)
        {
            swipeSkill=swipe(targetX,targetY,startX,startY,endX,endY);
            comboing=false;
        }
    }


}

