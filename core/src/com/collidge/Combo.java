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
    double skill;
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

        tapTotal=10;
        tapsLeft=tapTotal;

        allowedTime=5000;
        startTime=TimeUtils.millis();



        //TODO correct for the expected output of the swipe function
        return;

    }

    private void tapCombo(int x, int y, double targetx, double targety)
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
        else if(TimeUtils.timeSinceMillis(startTime)>allowedTime)
        {

            skill/=tapTotal;
            comboing=false;
        }
    }


}

