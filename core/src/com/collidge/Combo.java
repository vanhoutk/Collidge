package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    double targetX, targetY;
    boolean comboing;
    int tapTotal;
    boolean defending=false;
    int tapsLeft;
    long timer=0;
    long lastCheck;
    double skill;
    Texture texture;
    Sprite screenMask,dot,swipe;
    int comboId;
    BitmapFont font;
    float dx,dy;
    double swipeAngle=-45;
    float targetDx,targetDy;
    int numSwipes,numSwipesLeft;

    Combo()
    {
        font=new BitmapFont();
        texture=new Texture("blackSquare.png");
        screenMask=new Sprite(texture);
        screenMask.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        screenMask.setPosition(0f,0f);
        screenMask.setAlpha(.5f);
        texture=new Texture("buttonRound_grey.png");
        dot=new Sprite(texture);
        dot.setSize(Gdx.graphics.getWidth()/10f,Gdx.graphics.getWidth()/10f);
        texture=new Texture("swipeArrowWhite.png");
        swipe=new Sprite(texture);
        swipe.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight());
        swipe.setOriginCenter();


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
        if(comboId==1)
        {
            startX+=(Gdx.graphics.getWidth()/2000f)*(TimeUtils.timeSinceMillis(lastCheck));
        }
        lastCheck=TimeUtils.millis();
        checkTimer();
        dot.setPosition((float)targetX-Gdx.graphics.getWidth()*.05f,(float)targetY-Gdx.graphics.getWidth()*.05f);

        if(defending)
        {
            swipe.setColor(Color.TEAL);
            dot.setColor(Color.TEAL);
            float alpha=(1-((float)timer/allowedTime));
            if(alpha<.2)
            {
                alpha=.2f;
            }
            swipe.setAlpha(alpha);
            dot.setAlpha(alpha);
        }
        else
        {
            swipe.setAlpha(1);
            dot.setAlpha(1);
            swipe.setColor(Color.WHITE);
            dot.setColor(Color.WHITE);
        }

    }
    void draw(SpriteBatch batch)
    {
        screenMask.draw(batch);

        if(comboId==-1)
        {
            font.draw(batch,"Tap to defend! "+(int)(skill*tapTotal)+"/"+tapTotal,Gdx.graphics.getWidth()/3,font.getLineHeight());
            dot.draw(batch);
        }
        if(comboId==0)
        {
            font.draw(batch,"Tap to power up your attack! "+(int)(skill*tapTotal)+"/"+tapTotal,Gdx.graphics.getWidth()/3,font.getLineHeight());

            dot.draw(batch);
        }
        else if(comboId==1)
        {

            font.draw(batch,"Tap when they overlap",Gdx.graphics.getWidth()/3,font.getLineHeight());

            dot.draw(batch);
            dot.setColor(Color.GRAY);
            dot.setPosition((float)startX-Gdx.graphics.getWidth()*.05f,(float)targetY-Gdx.graphics.getWidth()*.05f);
            dot.draw(batch);
            dot.setColor(Color.WHITE);

        }
        else if(comboId==2)
        {


            swipe.setPosition(Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()/20,0);
            swipe.setRotation((float)swipeAngle);
            swipe.draw(batch);
        }


    }
    void initiateCombo(int moveId,Fight fight)
    {
        timer=0;
        lastCheck=TimeUtils.millis();
        comboId=moveId;
        defending=false;
        skill=0.0001;
        switch(moveId)
        {
            case 0:
                defaultAttack(10,5000);
                break;
            case 1:

                attack1();
                break;
            case 2:
                attack2(2,3000);
                break;
            default:
                System.out.println("D "+defending);
                defending=true;
                attack2(1,1000);
                comboId=2;
                break;
        }
    }

    void defaultAttack(int numTaps,int timeMillis)//currently used to defend
    {
        tapTotal=numTaps;
        tapsLeft=numTaps;
        allowedTime=timeMillis;
        startTime=TimeUtils.millis();
        comboing=true;
        targetX=Gdx.graphics.getWidth()/2;
        targetY=Gdx.graphics.getHeight()/2;
    }
    void basicAttack(int targetTap,int timeMilli)
    {

        comboing=true;
        targetX=(int)(rand.nextDouble()*Gdx.graphics.getWidth());
        targetY=(int)(rand.nextDouble()*Gdx.graphics.getHeight());

        comboId=0;
        tapTotal=targetTap;
        tapsLeft=tapTotal;

        allowedTime=timeMilli;
        startTime=TimeUtils.millis();




        return;

    }

    private void attack1()
    {

        comboing=true;
        targetX=(rand.nextDouble()*Gdx.graphics.getWidth()/2)+(Gdx.graphics.getWidth()/3);
        targetY=Gdx.graphics.getHeight()/2;
        startX=Gdx.graphics.getWidth()/20;
        startTime=TimeUtils.millis();
        allowedTime=3000;
    }
    private void attack2(int swipeTot,int timeMilli)
    {
        generateSwipe();
        comboing=true;
        numSwipes=swipeTot;
        numSwipesLeft=numSwipes;
        startTime=TimeUtils.millis();
        allowedTime=timeMilli;
    }

    private void tapCombo(int x, int y, double targetx, double targety)//Id:0
    {

        if(Math.abs(x-targetx)<=(Gdx.graphics.getWidth()*.1)&&Math.abs(y-targety)<=(Gdx.graphics.getWidth()*.1))
        {
            skill+=1.0/tapTotal;

            pop1.play();


        }
        else
        {

        }
        tapsLeft--;

        targetX=(int)(((rand.nextDouble()%.8)+.1)*Gdx.graphics.getWidth());
        targetY=(int)(((rand.nextDouble()%.8)+.1)*Gdx.graphics.getHeight());

        if(tapsLeft==0)
        {

            tapTotal=0;
            comboing=false;
        }



    }

    private void generateSwipe()
    {
        dx=0;
        dy=0;
        targetDx=rand.nextInt();
        targetDy=rand.nextInt();
        swipeAngle=Math.toDegrees(Math.atan2(targetDx,targetDy));
    }

    void tap(int x, int y)
    {
        if(tapsLeft>0&&comboId==-1)
        {
            skill+=1.0/tapTotal;
            tapsLeft--;
        }
        else if(tapTotal>0&&comboId==0)
        {
            tapCombo(x,y,targetX,-targetY+Gdx.graphics.getHeight());
        }
        else if(comboId==1)
        {
            skill=Math.abs((startX/Gdx.graphics.getWidth())-(targetX/Gdx.graphics.getWidth()));
            skill*=2;
            skill=1-skill;
            skill=skill*skill*skill*skill;
            if(skill>.8)
            {

                pop1.play();
            }
            comboing=false;
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
            comboing=false;
            timer=0;
        }
    }

    private void evaluateSwipe()
    {

        double angle=Math.toDegrees(Math.atan2(dx,dy));

        if(angle-swipeAngle>20)
        {

        }
        else
        {
            angle=Math.abs(angle-swipeAngle);
            skill +=( 1 - (angle / 20))/(double)numSwipes;
        }
        System.out.println(skill);
    }

    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        if(comboId==2)
        {
            dx+=deltaX;
            dy+=deltaY;
            return true;
        }
        return false;
    }


    public boolean panStop(float x, float y, int pointer, int button)
    {
        if(comboId==2)
        {
            evaluateSwipe();
            if(numSwipesLeft==1)
            {
                comboing=false;
            }
            else
            {
                numSwipesLeft--;
                generateSwipe();
            }
            return true;
        }
        return false;
    }
    void delete()
    {
        pop1.dispose();
    }
}

