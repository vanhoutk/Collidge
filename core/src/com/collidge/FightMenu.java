package com.collidge;

//import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
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
    private Sprite[][] menuSprites;
    public String Tooltip = "blank"; //inititalising to this so some actions can have no tooltip
    private int[] previousMenus;
    private String[][] attackDesc;
    private int currentIcon,aboveIcon,belowIcon,overflow;
    private int[][] menu;
    private int selectedMenu;
    private int selectedIcon;
    private int currentMenu;
    public boolean actionSelected;
    public boolean tooltipSelected=false;
    private BitmapFont battleFont;
    private Sprite backIcon, fightIcon, itemIcon, fleeIcon, rechargeIcon, menuContainer, arrow_down, arrow_up;

    float dx, dy;       //for pan function

    FightMenu(Player player)
    {
        battleFont = new BitmapFont();

        Texture texture = new Texture("panel_blue.png");
        menuContainer = new Sprite(texture);

        texture = new Texture("back2.png");
        backIcon = new Sprite(texture);

        texture = new Texture("fightIcon.png");
        fightIcon = new Sprite(texture);

        texture = new Texture("bagicon.png");
        itemIcon = new Sprite(texture);

        texture = new Texture("flee.png");
        fleeIcon = new Sprite(texture);

        texture = new Texture("battery-pack2.png");
        rechargeIcon = new Sprite(texture);


        texture = new Texture("arrow_down_blue.png");
        arrow_down = new Sprite(texture);

        texture = new Texture("arrow_up_blue.png");
        arrow_up = new Sprite(texture);


        previousMenus=new int[3];   //array to store previous menus (for use with back button)
        previousMenus[0]=50;
        previousMenus[1]=50;
        previousMenus[2]=50;

        menu=new int[7][50];        //menu array to store ids for menu
        menuWords=new String[7][50];    //string array, to store the word values for the menu
        menuSprites=new Sprite[7][50];
        attackDesc = new String[7][50];
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
                menuWords[i][j]=null;   //Initialises the String Array holding the actions
                menuSprites[i][j] = null;
                attackDesc[i][j]="blank";  //Initialises the String Array holding the description of actions
            }
        }
        menuWords[0][0]="Attack";
        menuWords[0][1]="Tactics";
        menuWords[0][2]="Items";

        menuSprites[0][0]= fightIcon;
        menuSprites[0][1]= fleeIcon;
        menuSprites[0][2]= itemIcon;


        //set up back
        for(int i=1;i<menuWords.length;i++)
        {
            menuWords[i][0]="Back";
            menuSprites[i][0]=backIcon;
        }
        //Set up attacks
        for(int j=1;j<=player.getAttacksNames().length && j<=player.getMovesKnown();j++){
            menuSprites[1][j]=fightIcon;

                if(player.getAttackEnergyCosts()[j-1]>player.getCurrentEnergy())
                {
                    menuWords[1][j]=player.getAttacksNames()[j-1]+"*";
                    attackDesc[1][j]= player.getAttackEnergyCosts()[j-1]+" En";
                }
                else

                {
                    menuWords[1][j] = player.getAttacksNames()[j - 1];
                    attackDesc[1][j] = player.getAttackDesc()[j - 1 ];
                }
        }


        //set up items
        for(int z=1;z<=player.getItemList().length;z++)
        {
            //populate with currentItems
            menuWords[3][z]=player.getItemList()[z-1];
            Texture texture = new Texture(player.getItemImage(menuWords[3][z]));
            menuSprites[3][z]=new Sprite (texture);
        }


        for(int z=1;z<=player.getItemDesc().length;z++)
        {
            //populate with currentItem Descriptions
            attackDesc[3][z]=player.getItemDesc()[z-1];
        }

        menuWords[2][1]="Recharge";
        menuSprites[2][1]=rechargeIcon;
        attackDesc[2][1] = "Rest & Gain "+player.getIntelligence()+"En";

        menuWords[2][2]="Flee";
        menuSprites[2][2]=fleeIcon;
        attackDesc[2][2] = "End fight";
    }

    public void draw(SpriteBatch batch, int screenWidth, int screenHeight)
    {
        battleFont.setScale(screenWidth/300.0f,screenHeight/250.0f);

        menuContainer.setSize(1.2f*screenWidth/3f,battleFont.getLineHeight()*3.3f);
        menuContainer.setPosition(screenWidth / 8, screenHeight / 2 - (battleFont.getLineHeight()));

            getAboveSprite().setSize(screenWidth/8f, screenHeight/8f);
            getAboveSprite().setPosition(screenWidth/8, screenHeight/3);
            getAboveSprite().setColor(Color.LIGHT_GRAY);
            getAboveSprite().draw(batch);

            getCurrentSprite().setSize(screenWidth/5f, screenHeight/5f);
            getCurrentSprite().setPosition(screenWidth/8 + getAboveSprite().getWidth(), screenHeight/3 + screenHeight/16f);

        if (getCurrentIcon().endsWith("*")) {
            getCurrentSprite().setColor(Color.DARK_GRAY);
        }
        else {
            getCurrentSprite().setColor(Color.WHITE);
        }
            getCurrentSprite().draw(batch);

            getBelowSprite().setSize(screenWidth/8f, screenHeight/8f);
            getBelowSprite().setPosition(screenWidth/8 + getAboveSprite().getWidth() + getCurrentSprite().getWidth(), screenHeight/3);
            getBelowSprite().setColor(Color.LIGHT_GRAY);
            getBelowSprite().draw(batch);

        if (getCurrentIcon().endsWith("*")) {
            battleFont.setColor(Color.RED);
        }
        else {
            battleFont.setColor(Color.BLACK);
        }
            battleFont.draw(batch, getCurrentIcon(), getCurrentSprite().getX(), getBelowSprite().getY() + battleFont.getLineHeight());




        /*
            arrow_down.setSize(screenWidth / 12f, screenWidth / 12f);
            arrow_down.setPosition(screenWidth / 28, screenHeight / 2 - (2.3f * battleFont.getLineHeight()));
            arrow_down.draw(batch);

            arrow_up.setSize(screenWidth / 12f, screenWidth / 12f);
            arrow_up.setPosition(screenWidth / 28, screenHeight / 2 + (1.3f * battleFont.getLineHeight()));
            arrow_up.draw(batch);

            //menuContainer.setPosition( screenWidth/6,screenHeight/2-battleFont.getLineHeight());
            //menuContainer.draw(batch);

            // menuContainer.setPosition( screenWidth/6,screenHeight/2);
            // menuContainer.draw(batch);

            menuContainer.draw(batch);

            //drawing above icon
            if (getAboveIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, getAboveIcon(), screenWidth / 7, screenHeight / 2 + 2 * battleFont.getLineHeight());

            //drawing current icon
            if (getCurrentIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }

            if (tooltipSelected == true && Tooltip != "blank") {     //for drawing Tooltip
                battleFont.draw(batch, Tooltip, screenWidth / 7, screenHeight / 2 + battleFont.getLineHeight());
            } else {
                battleFont.draw(batch, getCurrentIcon(), screenWidth / 7, screenHeight / 2 + battleFont.getLineHeight());
            }

            //drawing below icon
            if (getBelowIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, getBelowIcon(), screenWidth / 7, screenHeight / 2);
            */

            battleFont.setScale(screenWidth / 400.0f, screenHeight / 350.0f);


        /*
        //for drawing text below the menu - for error messages etc maybe (you don't have enough energy)
        if (currentMenu == 1)
        {
            //System.out.println("Attack");
            battleFont.draw(batch, attackDesc[1][currentIcon], screenWidth/7, 2*screenHeight/7);
        }

        else if (currentMenu == 2)
        {
            //System.out.println("Tactics");
            battleFont.draw(batch, attackDesc[2][currentIcon], screenWidth/7, 2*screenHeight/7);

        }

        else if (currentMenu ==3)
        {
            //System.out.println("Items");
            battleFont.draw(batch, attackDesc[3][currentIcon], screenWidth/7, 2*screenHeight/7);

        }*/

    }


    public void displayTooltip() {      //assigns tooltips to the menu

        if (currentMenu == 0){
            //System.out.println("Attack");
            Tooltip = "blank";
        }

        else if (currentMenu == 1){
            //System.out.println("Attack");
            Tooltip = attackDesc[1][currentIcon];
        }

        else if (currentMenu == 2){
            //System.out.println("Tactics");
            Tooltip = attackDesc[2][currentIcon];
        }

        else if (currentMenu ==3){
            //System.out.println("Items");
            Tooltip = attackDesc[3][currentIcon];
        }
    }


    public void tap(float x, float y) {
        /*
        if(x>arrow_up.getX() && x<arrow_up.getX() + arrow_up.getWidth())    //x co-ordinates must be on the arrow sprite
        {

            //y co-ordinates for the up arrow
            if (y < arrow_up.getY() + 0.5*arrow_up.getHeight() && y > arrow_up.getY() - 0.5*arrow_up.getHeight()) {
                Up();
                tooltipSelected = false; //don't display tooltip anymore if arrow is clicked
            }

            //y co-ordinates for the down arrow
            else if (y > arrow_down.getY() - 0.5*arrow_down.getHeight() && y < arrow_down.getY() + 0.5*arrow_down.getHeight()) {
                Down();
                tooltipSelected = false; //don't display tooltip anymore if arrow is clicked
            }
        }*/
        //x and y co-ordinates for the menu
        //if (x > menuContainer.getX() && x < menuContainer.getX() + menuContainer.getWidth()) {
           // if (y > menuContainer.getY() - menuContainer.getHeight() / 6 && y < menuContainer.getY() + menuContainer.getHeight() / 3)

                //TODO this doesn't use any Y values yet
                if (x > getCurrentSprite().getX() && x < getCurrentSprite().getX() + getCurrentSprite().getWidth()){
                    {
                        tooltipSelected = false;
                        Select();
                    }
                }
        }



    //pan (swipe) is for displaying tooltip - swipe right to show tooltip and swipe left to go back to action selection
    public boolean pan(float x, float y, float deltaX, float deltaY){
       // System.out.println("deltaX=" + deltaX + " deltaY=" + deltaY);
        if ((deltaX > 20 || deltaX < -20) && (deltaY < 20 || deltaY > -20)) {   //ignore vertical swipes or very short swipes
            dx = deltaX;
            dy = deltaY;
         //   System.out.println("dx=" + dx + " dy=" + dy);
            return true;
        }
        return false;
    }

    public boolean panStop(float x, float y){
        /*
        //old tooltip code
        if (x > menuContainer.getX() && y > menuContainer.getY() - menuContainer.getHeight() / 6 && y < menuContainer.getY() + menuContainer.getHeight() / 3) {

            if (dx>0){       //if the swipe was to the right, show tooltip
                displayTooltip();
                tooltipSelected = true;
            }
            else {      //if swipe was to the left, hide tooltip
                tooltipSelected = false;
            }
        */

        if(y < 9* Gdx.graphics.getHeight()/10
                && y > Gdx.graphics.getWidth()/10){
            if (dx>0){       //if the swipe was to the right, show tooltip
                //displayTooltip();
                //tooltipSelected = true;
                Down();
            }
            else {      //if swipe was to the left, hide tooltip
                Up();
                //tooltipSelected = false;
            }


            return true;
        }
        return false;
    }

    /*
    //here x needs to be fed in (called in the fight class) as x/screenWidth and y as y/screenHeight - the current version is hopefully ok though - Toni
    public void touchDown(float x, float y, int screenWidth, int screenHeight)
    {
        //System.out.println(x + " " + y + " " + 0.5+(battleFont.getLineHeight())/screenHeight);
        System.out.println("x=" + x + " y=" + y + " " + "width=" + screenWidth + " height=" + screenHeight);
        int sWidth = screenWidth/1000;
        int sHeight =  screenHeight/1000;

        if(x< (screenWidth/8))
        {
            // System.out.println(y);
            if (y > 0.5+(battleFont.getLineHeight())/screenHeight)
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
    */




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
    public void refreshMenus(Player player) {
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

    public Sprite getAboveSprite()
    {
        return menuSprites[currentMenu][aboveIcon];
    }
    public Sprite getCurrentSprite()
    {
        return menuSprites[currentMenu][currentIcon];
    }
    public Sprite getBelowSprite()
    {
        return menuSprites[currentMenu][belowIcon];
    }

}

