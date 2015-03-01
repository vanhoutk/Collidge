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
    Sprite screenMask,dot,swipe;
    int comboId;
    BitmapFont font;
    float dx,dy;
    double swipeAngle=-45;
    float targetDx,targetDy;

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
        swipe.setSize(Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight());
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


    }
    void draw(SpriteBatch batch)
    {
        screenMask.draw(batch);
        if(comboId==-1)
        {
            font.draw(batch,"Tap to defend! "+(int)skill+"/"+tapTotal,Gdx.graphics.getWidth()/3,font.getLineHeight());
            dot.draw(batch);
        }
        if(comboId==0)
        {
            font.draw(batch,"Tap to power up your attack! "+(int)skill+"/"+tapTotal,Gdx.graphics.getWidth()/3,font.getLineHeight());

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

            swipe.setPosition(Gdx.graphics.getWidth()/2,0);
            swipe.setRotation((float)swipeAngle);
            swipe.draw(batch);
        }

    }
    void initiateCombo(int moveId,Fight fight)
    {
        timer=0;
        lastCheck=TimeUtils.millis();
        comboId=moveId;
        skill=0;
        switch(moveId)
        {
            case 0:
                basicAttack();
                break;
            case 1:

                attack1();
                break;
            case 2:
                attack2();
                break;
            default:
                defaultAttack(25);
                comboId=-1;
                break;
        }
    }

    void defaultAttack(int numTaps)//currently used to defend
    {
        tapTotal=numTaps;
        tapsLeft=numTaps;
        allowedTime=2000;
        startTime=TimeUtils.millis();
        comboing=true;
        targetX=Gdx.graphics.getWidth()/2;
        targetY=Gdx.graphics.getHeight()/2;
    }
    void basicAttack()
    {
        skill=0;
        comboing=true;
        targetX=(int)(rand.nextDouble()*Gdx.graphics.getWidth());
        targetY=(int)(rand.nextDouble()*Gdx.graphics.getHeight());

        comboId=0;
        tapTotal=10;
        tapsLeft=tapTotal;

        allowedTime=5000;
        startTime=TimeUtils.millis();




        return;

    }

    private void attack1()
    {
        skill=0;
        comboing=true;
        targetX=(rand.nextDouble()*Gdx.graphics.getWidth()/2)+(Gdx.graphics.getWidth()/3);
        targetY=Gdx.graphics.getHeight()/2;
        startX=Gdx.graphics.getWidth()/20;
        startTime=TimeUtils.millis();
        allowedTime=3000;
    }
    private void attack2()
    {
        skill=0;
        comboing=true;
        dx=0;
        dy=0;
        targetDx=rand.nextInt();
        targetDy=rand.nextInt();
        swipeAngle=Math.toDegrees(Math.atan2(targetDx,targetDy));
        startTime=TimeUtils.millis();
        allowedTime=3000;
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
        if(tapsLeft>0&&comboId==-1)
        {
            skill+=1;
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

            if(comboId==0||comboId==-1)
            {
                skill /= tapTotal;
            }
            comboing=false;
            timer=0;
        }
    }

    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        if(comboId==2)
        {
            System.out.println("pan:"+deltaX+""+deltaY);
            dx+=deltaX;
            dy+=deltaY;
            System.out.println(dx+", "+dy);
            return true;
        }
        return false;
    }


    public boolean panStop(float x, float y, int pointer, int button)
    {
        if(comboId==2)
        {
            double angle=Math.toDegrees(Math.atan2(dx,dy));
            System.out.println("Angle: "+(int)angle);
            skill=Math.abs(angle-swipeAngle);

            if(skill>25)
            {
                skill=0;
            }
            else
            {
                skill = 1 - (skill / 25);
            }
            comboing=false;
            return true;
        }
        return false;
    }
    void delete()
    {
        pop1.dispose();
    }
}

