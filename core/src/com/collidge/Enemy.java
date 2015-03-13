package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by Daniel on 21/01/2015.
 */
public class Enemy
{
    private int maxHealth,currentHealth,attack,defence;
    private boolean dead;
    String name;
    private Texture texture;
    Animation animation;
    int width;
    int height;

    private Random rand=new Random();
    public int getExpValue()
    {
        return expValue;
    }

    private int expValue;

    Enemy(String nm, int h, int a, int d,int exp,float sizeX, float sizeY)
    {
        name=nm;
        maxHealth=h;
        attack=a;
        defence=d;
        expValue=exp;
        currentHealth=maxHealth;
        dead=false;
        texture=new Texture("walking_left_animation.png");
        TextureRegion[][] region = TextureRegion.split(texture,32,32);
        animation=new Animation(region[0],.2f);
        width= (int)(Gdx.graphics.getWidth()/sizeX);
        height=(int)(Gdx.graphics.getHeight()/sizeY);
    }

    Enemy(Enemy template)
    {

        maxHealth=template.maxHealth;
        attack=template.attack;
        defence=template.getDefence();
        expValue=template.getExpValue();
        name=template.getName();
        width=template.width;
        height=template.height;

        maxHealth=(int)(maxHealth*((rand.nextDouble()%.5)+.75));
        attack=(int)(attack*((rand.nextDouble()%.5)+.75));
        defence=(int)(defence*((rand.nextDouble()%.5)+.75));
        expValue=(int)(expValue*((rand.nextDouble()%.5)+.75));
        currentHealth=maxHealth;

       // System.out.println(this.getName()+": "+this.maxHealth+"hp -ATK:"+this.attack+"  DEF:"+this.defence+"  EXP:"+this.expValue);
        dead=false;
        texture=new Texture("walking_left_animation.png");
        TextureRegion[][] region = TextureRegion.split(texture,32,32);
        animation=new Animation(region[0],.2f);




    }

    public boolean getDead()
    {
        return dead;
    }
    public int getHealth()
    {
        return currentHealth;
    }
    public int getMaxHealth()
    {
        return maxHealth;
    }
    public int getDefence()
    {
        return defence;
    }
    public int getAttack()
    {
        return attack;
    }
    public String getName()
    {
        return name;
    }


    //dH will be a negative value, i.e. the damage the player does to the enemy.
    public void changeHealth(int dH)
    {
        currentHealth+=dH;
        if(currentHealth>maxHealth)
        {
            currentHealth=maxHealth;
        }
        else if(currentHealth<=0)
        {
            dead=true;
        }
    }

    private void addTextures(Sprite[] sprite_enemy,Enemy[] enemies)
    {
        for (int i = 0; i< enemies.length; i++) {
            if (enemies[i].getName().equals("Fresher"))
            {
                texture = new Texture("badlogic.jpg");  //Placeholder stuff until I have sprites for enemies
                sprite_enemy[i] = new Sprite(texture);
            }
            else if (enemies[i].getName().equals("Frat boy"))
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }

            else if (enemies[i].getName().equals("Debater"))
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }

            else if (enemies[i].getName().equals("Lecturer"))
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }
            else
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }

        }
    }
}
