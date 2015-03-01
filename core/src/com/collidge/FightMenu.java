package com.collidge;

//import android.view.MotionEvent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Daniel on 26/01/2015.
 */
public class FightMenu
{

    private String[][] menuWords;
    private int[] previousMenus;
    private String[] attackList;
    private int currentIcon,aboveIcon,belowIcon,overflow;
    private int[][] menu;
    private int selectedMenu;
    private int selectedIcon;
    private int currentMenu;
    public boolean actionSelected;
    private BitmapFont battleFont;
    private Sprite menuContainer, arrow_down, arrow_up;
    private Texture texture;

    FightMenu(Player player)
    {
        battleFont = new BitmapFont();

        texture = new Texture("panel_beige.png");
        menuContainer = new Sprite(texture);

        texture = new Texture("arrowBeige_down.png");
        arrow_down = new Sprite(texture);

        texture = new Texture("arrowBeige_up.png");
        arrow_up = new Sprite(texture);
        previousMenus=new int[3];   //array to store previous menus (for use with back button)
        previousMenus[0]=50;
        previousMenus[1]=50;
        previousMenus[2]=50;

        menu=new int[7][50];        //menu array to store ids for menu
        menuWords=new String[7][50];    //string array, to store the word values for the menu
        currentMenu=0;              //id for the current menu being shown. basically menu[currentMenu]
        fillMenus(player);          //populate menus (both word and id) with values
        currentIcon=0;              //current item being pointed at. basically menu[][currentIcon]
        overflow=getMenuOverflow(); //number of empty items on the menus, used for wrap around
        validate();
        /*for(int  i=0;i<menu.length;i++)
        {
            for(int j=0;j<menu[0].length;j++)
            {
                System.out.print(menu[i][j]);
            }
            System.out.println();
        }
        for(int  i=0;i<menu.length;i++)
        {
            for(int j=0;j<menu[0].length;j++)
            {
                if(menuWords[i][j]!=null)
                {
                    System.out.print(menuWords[i][j] + ", ");
                }
            }
            System.out.println();
        }*/

    }
    private void Select()
    {


        selectedMenu=currentMenu;
        selectedIcon=currentIcon;
        //back functionality
        if(menu[currentMenu][currentIcon]==2)
        {
            for(int i=previousMenus.length-1;i>=0;i--)
            {
                if(previousMenus[i]!=50)
                {
                    currentMenu=previousMenus[i];

                    previousMenus[i]=50;
                    i=-1;
                }
            }
        }
        //secondary menu selected
        else if(menu[currentMenu][currentIcon]==1)
        {
            for(int i=0; i<3;i++)
            {
                if(previousMenus[i]==50)
                {
                    previousMenus[i]=currentMenu;
                    i=5;
                }
            }
            if(currentMenu==0)
            {
                currentMenu++;
            }
            currentMenu+=currentIcon;


        }
        //actionSelected
        else if(menu[currentMenu][currentIcon]==3)
        {
            selectedIcon=currentIcon;
            currentMenu=0;
            previousMenus[0]=50;
            previousMenus[1]=50;
            previousMenus[2]=50;
            actionSelected=true;
        }

        overflow=getMenuOverflow();
        currentIcon=0;
        validate();
    }
    private void Up()
    {
        currentIcon--;
        if(currentIcon<0)
        {
            currentIcon=(menu[0].length-overflow)-1;
        }


        validate();
    }
    private void Down()
    {

        currentIcon++;

        if(currentIcon>=menu[0].length-overflow)
        {
            currentIcon %= menu.length-overflow;
        }

        validate();
    }
    private void validate()
    {
        aboveIcon=currentIcon+1;
        belowIcon=currentIcon-1;
        if(currentIcon<0)
        {
            currentIcon=(menu[0].length-overflow)-1;
        }
        if(belowIcon<0)
        {
            belowIcon=(menu[0].length-overflow)-1;
        }
        currentIcon%=(menu[currentMenu].length-overflow);
        aboveIcon%=(menu[currentMenu].length-overflow);
        belowIcon%=(menu[currentMenu].length-overflow);

    }
    private int getMenuOverflow()
    {
        int overflowCount=0;
        for(int i=0;i<menu[currentMenu].length;i++)
        {
            if(menu[currentMenu][i]==0)
            {
                overflowCount++;
            }
        }
        return overflowCount;
    }
    private void fillMenus(Player player)
    {
        populateStringMenu(player);
        //array contains descriptive identifier integers
        //1=new menu
        //2=return to higher menu level
        //3=action
        for(int i=0;i<menu.length;i++)
        {
            for(int j=0; j<menu[i].length;j++)
            {
                if(i==0)
                {
                    if(j<3)
                    {
                        menu[i][j] = 1;
                    }
                }
                else
                {
                    if(j==0)
                    {
                        menu[i][j]=2;
                    }

                    else if(menuWords[i][j]!=null)
                    {

                        menu[i][j]=3;
                        if(menuWords[i][j].endsWith("*"))
                        {
                            menu[i][j]=2;
                        }

                    }
                    else
                    {
                        menu[i][j]=0;
                    }
                }
            }
        }
    }

    private void populateStringMenu(Player player)
    {
        for (int i=0;i<menuWords.length;i++)
        {
            for(int j=0;j<menuWords[0].length;j++)
            {
                menuWords[i][j]=null;
            }
        }
        menuWords[0][0]="Attack";
        menuWords[0][1]="Tactics";
        menuWords[0][2]="Items";

        //set up back
        for(int i=1;i<menuWords.length;i++)
        {
            menuWords[i][0]="Back";
        }
        //Set up attacks
        for(int j=1;j<=player.getAttacksNames().length&&j<=player.getMovesKnown();j++)
        {
            if(j<=player.getMovesKnown())
            {
                if(player.getAttackEnergyCosts()[j-1]>player.getCurrentEnergy())
                {
                    menuWords[1][j]=player.getAttacksNames()[j-1]+"*";
                }
                else

                {
                    menuWords[1][j] = player.getAttacksNames()[j - 1];
                }
            }
        }


        //set up items
        for(int z=1;z<=player.getItemList().length;z++)
        {
            //populate with currentItems
            menuWords[3][z]=player.getItemList()[z-1];
        }
        menuWords[2][1]="Recharge";
        menuWords[2][2]="Flee";


    }


    public void draw(SpriteBatch batch, int screenWidth, int screenHeight)
    {
        battleFont.setScale(screenWidth/300.0f,screenHeight/250.0f);

        menuContainer.setSize(screenWidth/3f,battleFont.getLineHeight()*3.3f);


        arrow_down.setSize(screenWidth/12f,screenWidth/12f);
        arrow_down.setPosition(screenHeight / 20, screenHeight / 2 - (2.3f * battleFont.getLineHeight()));
        arrow_down.draw(batch);

        arrow_up.setSize(screenWidth/12f,screenWidth/12f);
        arrow_up.setPosition( screenHeight / 20,screenHeight/2+(1.3f*battleFont.getLineHeight()));
        arrow_up.draw(batch);
        //menuContainer.setPosition( screenWidth/6,screenHeight/2-battleFont.getLineHeight());
        //menuContainer.draw(batch);

       // menuContainer.setPosition( screenWidth/6,screenHeight/2);
       // menuContainer.draw(batch);

        menuContainer.setPosition( screenWidth/8,screenHeight/2-(battleFont.getLineHeight()));
        menuContainer.draw(batch);
        {
            if (getAboveIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, getAboveIcon(), screenWidth / 7, screenHeight / 2 + 2 * battleFont.getLineHeight());

            if (getCurrentIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, getCurrentIcon(), screenWidth / 7, screenHeight / 2 + battleFont.getLineHeight());

            if (getBelowIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, getBelowIcon(), screenWidth / 7, screenHeight / 2);
        }
    }


    public void touchDown(float x, float y, int screenHeight)
    {
        System.out.println(x + " " + y + " " + 0.5+(battleFont.getLineHeight())/screenHeight);
        if(x<0.1)
        {
           // System.out.println(y);
            if (y > 0.5+(battleFont.getLineHeight())/screenHeight && y < 0.5+(battleFont.getLineHeight())/screenHeight)
            {

                Up();

            } else //if (y < 1/ 2 - (2.3f * battleFont.getLineHeight()))
            {

                Down();

            }
        }
        else if (x > 0.1 && x < 0.433)
            if (y < .6 && y > .35)
                Select();
    }




    public String getMoveString(int menu, int icon)
    {
        return menuWords[menu][icon];
    }
    public int getActionType()
    {
        return selectedMenu;
    }
    public int getActionId()
    {
        return selectedIcon;
    }
    public void refreshMenus(Player player)
    {
        fillMenus(player);
        overflow=getMenuOverflow();
    }
    public String getAboveIcon()
    {
        return menuWords[currentMenu][aboveIcon];
    }
    public String getCurrentIcon()
    {
        return menuWords[currentMenu][currentIcon];
    }
    public String getBelowIcon()
    {
        return menuWords[currentMenu][belowIcon];
    }



}

