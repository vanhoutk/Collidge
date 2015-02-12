package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.TimeUtils;

import java.lang.annotation.Target;
import java.util.Random;

/**
 * Created by Daniel on 22/01/2015.
 */
public class Combo
{
    long startTime,allowedTime;
    Random rand=new Random();
    double startX,startY;
    double endX,endY;
    double targetX, targetY;
    boolean comboing;
    double swipeSkill;
    Texture texture;
    Sprite screenMask,dot;

    Combo()
    {

        texture=new Texture("blackSquare.png");
        screenMask=new Sprite(texture);
        screenMask.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        screenMask.setPosition(0f,0f);
        screenMask.setAlpha(.5f);
        texture=new Texture("buttonRound_grey.png");
        dot=new Sprite(texture);
        dot.setSize(Gdx.graphics.getWidth()/10f,Gdx.graphics.getWidth()/10f);

    }

    void update()
    {
        dot.setPosition((float)targetX-Gdx.graphics.getWidth()*.05f,(float)targetY-Gdx.graphics.getWidth()*.05f);
        checkTimer();
    }
    void draw(SpriteBatch batch)
    {
        screenMask.draw(batch);
        dot.draw(batch);

    }
    void initiateCombo(int moveId,Fight fight)
    {
        switch(moveId)
        {
            case 0:
                basicAttack();
                return;
            default:
                return;
        }
    }


    void basicAttack()
    {
        swipeSkill=0;
        comboing=true;
        targetX=(int)(rand.nextDouble()*Gdx.graphics.getWidth());
        targetY=(int)(rand.nextDouble()*Gdx.graphics.getHeight());

        allowedTime=50000;
        startTime=TimeUtils.millis();



        //TODO correct for the expected output of the swipe function
        return;

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

    private void tapCombo(int x, int y, double targetx, double targety)
    {

        swipeSkill= ((1/(x-targetx))*(1/(y-(targety))));
        System.out.println("Skill: "+swipeSkill);
        System.out.println("Tapped: "+x+", "+y);
        System.out.println("Target: "+targetx+", "+targety);
        if(swipeSkill>3)
        {
            swipeSkill=3;
        }
        comboing=false;
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
    void tap(int x, int y)
    {
        tapCombo(x,y,targetX,-targetY+Gdx.graphics.getHeight());

    }
    void checkTimer()
    {
        if(TimeUtils.timeSinceMillis(startTime)>allowedTime)
        {
            swipeSkill=.01;
            comboing=false;
        }
    }


}

