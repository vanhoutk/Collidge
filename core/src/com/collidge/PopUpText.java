package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Daniel on 20/03/2015.
 */

public class PopUpText
{

    private class text
    {
        text(String w,float xv,float yv, float xd, float yd, Color colour,int duration,double size)
        {
            word=w;
            xVal=xv;
            yVal=yv;
            dx=xd;
            dy=yd;
            textColour=colour;
            lifetime=duration;
            fontSize=size;
        }
        text(String w,float xv,float yv, float xd, float yd, Color colour,int duration)
        {
            word=w;
            xVal=xv;
            yVal=yv;
            dx=xd;
            dy=yd;
            textColour=colour;
            lifetime=duration;
            fontSize=3;

        }
        double fontSize;
        String word;
        float xVal,yVal,dx,dy;
        Color textColour;
        int lifetime;

        public void update(float dt)
        {
            xVal+=dx*dt;
            yVal+=dy*dt;
            lifetime-=dt;
        }

    }
    BitmapFont font;
    ArrayList<text> popUps;


    PopUpText()
    {
        font=new BitmapFont();
        popUps= new ArrayList();

    }





//------------Below is a collection of 'add' instructions to allow for varying degrees of precision in defining pop ups-----------------------
    public void Add(String words)
    {
        popUps.add(new text(words,.5f,0f,0f,.1f,Color.RED,100));
    }

    public void Add(String words,float x,float y)
    {
        popUps.add(new text(words,x,y,0f,.1f,Color.RED,100));
    }

    public void Add(String words,float x, float y, Color fontColor)
    {
        popUps.add(new text(words,x,y,0f,.1f,fontColor,100));
    }

    public void Add(String words, float x, float y, float speedX,float speedY)
    {
        popUps.add(new text(words,x,y,speedX,speedY,Color.RED,100));
    }

    public void Add(String words,float x, float y, float speedX, float speedY, Color fontColor)
    {
        popUps.add(new text(words,x,y,speedX,speedY,fontColor,100));
    }
    public void Add(String words, Color fontColor)
    {
        popUps.add(new text(words,.5f,.5f,0f,.1f,fontColor,100));
    }

    public void Add(String words,int duration)
    {
        popUps.add(new text(words,.5f,0f,0f,.1f,Color.RED,duration));
    }

    public void Add(String words,float x,float y,int duration)
    {
        popUps.add(new text(words,x,y,0f,.1f,Color.RED,duration));
    }

    public void Add(String words,float x, float y, Color fontColor,int duration)
    {
        popUps.add(new text(words,x,y,0f,.1f,fontColor,duration));
    }

    public void Add(String words, float x, float y, float speedX,float speedY,int duration)
    {
        popUps.add(new text(words,x,y,speedX,speedY,Color.RED,duration));
    }

    public void Add(String words,float x, float y, float speedX, float speedY, Color fontColor,int duration)
    {
        popUps.add(new text(words,x,y,speedX,speedY,fontColor,duration));
    }
    public void Add(String words, Color fontColor,int duration)
    {
        popUps.add(new text(words,.5f,.5f,0f,.1f,fontColor,duration));
    }

    public void Add(String words,double size)
    {
        popUps.add(new text(words,.5f,0f,0f,.1f,Color.RED,100,size));
    }

    public void Add(String words,float x,float y,double size)
    {
        popUps.add(new text(words,x,y,0f,.1f,Color.RED,100,size));
    }

    public void Add(String words,float x, float y, Color fontColor,double size)
    {
        popUps.add(new text(words,x,y,0f,.1f,fontColor,100,size));
    }

    public void Add(String words, float x, float y, float speedX,float speedY,double size)
    {
        popUps.add(new text(words,x,y,speedX,speedY,Color.RED,100,size));
    }

    public void Add(String words,float x, float y, float speedX, float speedY, Color fontColor,double size)
    {
        popUps.add(new text(words,x,y,speedX,speedY,fontColor,100,size));
    }
    public void Add(String words, Color fontColor,double size)
    {
        popUps.add(new text(words,.5f,.5f,0f,.1f,fontColor,100,size));
    }

    public void Add(String words,int duration,double size)
    {
        popUps.add(new text(words,.5f,0f,0f,.1f,Color.RED,duration));
    }

    public void Add(String words,float x,float y,int duration,double size)
    {
        popUps.add(new text(words,x,y,0f,.1f,Color.RED,duration,size));
    }

    public void Add(String words,float x, float y, Color fontColor,int duration,double size)
    {
        popUps.add(new text(words,x,y,0f,.1f,fontColor,duration,size));
    }

    public void Add(String words, float x, float y, float speedX,float speedY,int duration,double size)
    {
        popUps.add(new text(words,x,y,speedX,speedY,Color.RED,duration,size));
    }

    public void Add(String words,float x, float y, float speedX, float speedY, Color fontColor,int duration,double size)
    {
        popUps.add(new text(words,x,y,speedX,speedY,fontColor,duration,size));
    }
    public void Add(String words, Color fontColor,int duration,double size)
    {
        popUps.add(new text(words,.5f,.5f,0f,.1f,fontColor,duration,size));
    }
//-----------------------------------END OF ADD STATEMENTS-----------------------------------------

    //This updates the position of the popUp if it have a velocity, and removes it from the ArrayList if it has reached the end of its lifespan
    public void update()
    {
        for(int i=0;i<popUps.size();i++)
        {
            popUps.get(i).update(Gdx.graphics.getDeltaTime());

            if(popUps.get(i).lifetime<=0)
            {
                popUps.remove(i);
                i--;
            }
        }
    }
    //Draws the popUp wherever it is called, be careful you arent drawing it behind other sprites.
    // Also bear in mind that all popUps are drawn in one go, in order of newest first
    public void draw(SpriteBatch batch)
    {
        for(int i=popUps.size()-1;i>=0;i--)
        {
            font.setColor(popUps.get(i).textColour);
            font.setScale((Gdx.graphics.getWidth()*(float)popUps.get(i).fontSize)/1200);
            font.draw(batch, popUps.get(i).word, Gdx.graphics.getWidth()*popUps.get(i).xVal, Gdx.graphics.getHeight()*popUps.get(i).yVal);
        }
    }
}
