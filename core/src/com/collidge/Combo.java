package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
    Sound pop1=Gdx.audio.newSound(Gdx.files.internal("pop1.ogg"));
    long startTime,allowedTime;
    Random rand=new Random();
    double startX,startY;
    double endX,endY;
    double targetX, targetY;
    boolean comboing;
    double swipeSkill;
    int tapTotal;
    int tapsLeft;
    long timer=0;
    long lastCheck;
    double skill;
    Texture texture;
    Sprite screenMask,dot;
    int comboId;

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
        if(TimeUtils.timeSinceMillis(lastCheck)>=150)
        {
            timer++;

        }
        else
        {
            timer+=TimeUtils.timeSinceMillis(lastCheck);
        }
        lastCheck=TimeUtils.millis();
        checkTimer();
        dot.setPosition((float)targetX-Gdx.graphics.getWidth()*.05f,(float)targetY-Gdx.graphics.getWidth()*.05f);

    }
    void draw(SpriteBatch batch)
    {
        screenMask.draw(batch);
        if(comboId==0)
        {
            dot.draw(batch);
        }
        else if(comboId==1)
        {
            dot.draw(batch);
        }

    }
    void initiateCombo(int moveId,Fight fight)
    {
        timer=0;
        lastCheck=TimeUtils.millis();
        switch(moveId)
        {
            case 0:
                basicAttack(fight);
                return;
            default:
                return;
        }
    }

    void basicAttack(Fight fight)
    {
        skill=0;
        comboing=true;
        targetX=(int)(rand.nextDouble()*Gdx.graphics.getWidth());
        targetY=(int)(rand.nextDouble()*Gdx.graphics.getHeight());

        comboId=0;
        tapTotal=10;
        tapsLeft=tapTotal-1;

        allowedTime=5000;
        startTime=TimeUtils.millis();




        return;

    }

    private void tapCombo(int x, int y, double targetx, double targety)//Id:0
    {

        if(Math.abs(x-targetx)<=(Gdx.graphics.getWidth()*.1)&&Math.abs(y-targety)<=(Gdx.graphics.getWidth()*.1))
        {
            skill+=1;

            pop1.play();


        }
        else
        {

        }
        tapsLeft--;

        targetX=(int)(rand.nextDouble()*Gdx.graphics.getWidth());
        targetY=(int)(rand.nextDouble()*Gdx.graphics.getHeight());

        if(tapsLeft==0)
        {
            skill/=tapTotal;
            tapTotal=0;
            comboing=false;
        }



    }

    void tap(int x, int y)
    {
        if(tapTotal>0)
        {
            tapCombo(x,y,targetX,-targetY+Gdx.graphics.getHeight());
        }


    }
    void checkTimer()
    {
        if(!comboing)
        {
            return;
        }
        else if(timer>allowedTime)
        {

            if(comboId==0)
            {
                skill /= tapTotal;
            }
            comboing=false;
            timer=0;
        }
    }

    void delete()
    {
        pop1.dispose();
    }
}

