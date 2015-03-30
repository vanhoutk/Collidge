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
    public String Tooltip = "";
    private int[] previousMenus;
    private String[][] attackDesc;
    private int currentIcon, icon2, icon3, icon4, tooltipIcon, aboveIcon,belowIcon,overflow;
    private int[][] menu;
    private int menuNext =4;
    private int menuPrev =4;
    private int page = 1;
    private int selectedMenu;
    private int selectedIcon;
    private int currentMenu;
    public Boolean menuStyle2 = true;
    public boolean actionSelected;
    public boolean tooltipSelected=false;
    private BitmapFont battleFont;
    private Sprite backIcon, fightIcon, itemIcon, fleeIcon, rechargeIcon, tooltipBackground;

    float dx, dy, Tooltipdx, Tooltipdy;       //for pan function

    FightMenu(Player player)
    {
        battleFont = new BitmapFont();

        Texture texture = new Texture("back3.png");
        backIcon = new Sprite(texture);

        texture = new Texture("fightIcon.png");
        fightIcon = new Sprite(texture);

        texture = new Texture("bagicon.png");
        itemIcon = new Sprite(texture);

        texture = new Texture("flee.png");
        fleeIcon = new Sprite(texture);

        texture = new Texture("battery-pack2.png");
        rechargeIcon = new Sprite(texture);

        texture = new Texture("tooltipBackground.png");
        tooltipBackground = new Sprite(texture);



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
    private void Select(int iconToSelect)
    {
        selectedMenu=currentMenu;
        selectedIcon = iconToSelect;
        //selectedIcon=currentIcon;
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
        else if(menu[currentMenu][iconToSelect]==1)
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
            currentMenu+=iconToSelect;


        }
        //actionSelected
        else if(menu[currentMenu][iconToSelect]==3)
        {
            selectedIcon=iconToSelect;
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
    private void Right()
    {
        if (menuStyle2) {
            //currentIcon--;
            if (menu[currentMenu][menuNext] != 0) {
                page++;
                menuNext += 4;
                currentIcon += 4;   //go to next page
                if (currentIcon < 0) {
                    currentIcon = (menu[0].length - overflow) - 1;
                }
                validate();
            }
        }

        else {
            currentIcon++;

            if (currentIcon >= menu[0].length - overflow) {
                currentIcon %= menu.length - overflow;
            }

            validate();
        }


    }
    private void Left()
    {
        if (menuStyle2) {
            //currentIcon++;
            if (menu[currentMenu][menuPrev] != 0 && page > 1) {
                page--;
                menuNext -= 4;

                currentIcon -= 4;   //go to previous page

                if (currentIcon < 0) {
                    currentIcon = (menu[0].length - overflow) - 1;
                }
                validate();
            }
        }
        else {
            currentIcon--;
            if (currentIcon < 0) {
                currentIcon = (menu[0].length - overflow) - 1;
            }
            validate();
        }
    }
    private void validate()
    {
        if (menuStyle2){
            icon2=currentIcon+1;
            icon3=currentIcon+2;
            icon4=currentIcon+3;

            currentIcon%=(menu[currentMenu].length-overflow);
            icon2%=(menu[currentMenu].length-overflow);
            icon3%=(menu[currentMenu].length-overflow);
            icon4%=(menu[currentMenu].length-overflow);
        }
        else {
            aboveIcon = currentIcon + 1;
            belowIcon = currentIcon - 1;
            if (currentIcon < 0) {
                currentIcon = (menu[0].length - overflow) - 1;
            }
            if (belowIcon < 0) {
                belowIcon = (menu[0].length - overflow) - 1;
            }
            currentIcon %= (menu[currentMenu].length - overflow);
            aboveIcon %= (menu[currentMenu].length - overflow);
            belowIcon %= (menu[currentMenu].length - overflow);
        }

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
                attackDesc[i][j]="";  //Initialises the String Array holding the description of actions
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
        for (int j=1; j<player.getCombatMoves().length + 1; j++)
        {
            Texture texture = new Texture(player.getCombatMoves()[j-1].attackImage);
            menuSprites[1][j]= new Sprite(texture);

            if(player.getCombatMoves()[j-1].attackEnergyCost > player.getCurrentEnergy())
                {
                    menuWords[1][j]=player.getCombatMoves()[j-1].attackName + "*";
                    attackDesc[1][j]= player.getCombatMoves()[j-1].attackDesc;
                }
                else

                {
                    menuWords[1][j] = player.getCombatMoves()[j-1].attackName;
                    attackDesc[1][j] = player.getCombatMoves()[j-1].attackDesc;
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
        attackDesc[2][1] = "Rest for a turn & Gain "+player.getIntelligence()+" Energy";

        menuWords[2][2]="Flee";
        menuSprites[2][2]=fleeIcon;
        attackDesc[2][2] = "Run away & end the fight";
    }

    public void draw(SpriteBatch batch, int screenWidth, int screenHeight) {

        if (menuStyle2) {
            battleFont.setScale(screenWidth / 400.0f, screenHeight / 350.0f);
            menuSprites[currentMenu][currentIcon].setSize(screenWidth / 8f, screenHeight / 8f);
            menuSprites[currentMenu][currentIcon].setPosition(screenWidth / 12, screenHeight / 2);
            menuSprites[currentMenu][currentIcon].draw(batch);
            if (getCurrentIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
            if (menuWords[currentMenu][currentIcon] == "Improvised Explosive Device"){
                menuWords[currentMenu][currentIcon] = "IED";
            }
            battleFont.drawWrapped(batch, menuWords[currentMenu][currentIcon], menuSprites[currentMenu][currentIcon].getX() - menuSprites[currentMenu][currentIcon].getWidth()/6, menuSprites[currentMenu][currentIcon].getY() - battleFont.getLineHeight()/2, 4*menuSprites[currentMenu][currentIcon].getWidth()/3, BitmapFont.HAlignment.CENTER);


            if (menuSprites[currentMenu][icon2] != menuSprites[currentMenu][currentIcon]) {
                menuSprites[currentMenu][icon2].setSize(screenWidth / 8f, screenHeight / 8f);
                menuSprites[currentMenu][icon2].setPosition(screenWidth / 4, screenHeight / 2);
                menuSprites[currentMenu][icon2].draw(batch);
                    if (menuWords[currentMenu][icon2].endsWith("*")) {
                        battleFont.setColor(Color.RED);
                    } else {
                        battleFont.setColor(Color.BLACK);
                    }
                if (menuWords[currentMenu][icon2] == "Improvised Explosive Device"){
                    menuWords[currentMenu][icon2] = "IED";
                }
                    battleFont.drawWrapped(batch, menuWords[currentMenu][icon2], menuSprites[currentMenu][icon2].getX() - menuSprites[currentMenu][icon2].getWidth()/6, menuSprites[currentMenu][icon2].getY() - battleFont.getLineHeight()/2, 4*menuSprites[currentMenu][icon2].getWidth()/3, BitmapFont.HAlignment.CENTER);
           }

            if (menuSprites[currentMenu][icon3] != menuSprites[currentMenu][currentIcon]) {
                menuSprites[currentMenu][icon3].setSize(screenWidth / 8f, screenHeight / 8f);
                menuSprites[currentMenu][icon3].setPosition(screenWidth / 12, screenHeight / 4);
                menuSprites[currentMenu][icon3].draw(batch);
                if (menuWords[currentMenu][icon3].endsWith("*")) {
                    battleFont.setColor(Color.RED);
                } else {
                    battleFont.setColor(Color.BLACK);
                }
                if (menuWords[currentMenu][icon3] == "Improvised Explosive Device"){
                    menuWords[currentMenu][icon3] = "IED";
                }
                battleFont.drawWrapped(batch, menuWords[currentMenu][icon3], menuSprites[currentMenu][icon3].getX() - menuSprites[currentMenu][icon3].getWidth()/6, menuSprites[currentMenu][icon3].getY() - battleFont.getLineHeight()/2, 4*menuSprites[currentMenu][icon3].getWidth()/3, BitmapFont.HAlignment.CENTER);

            }

            if (menuSprites[currentMenu][icon4] != menuSprites[currentMenu][currentIcon]) {
                menuSprites[currentMenu][icon4].setSize(screenWidth / 8f, screenHeight / 8f);
                menuSprites[currentMenu][icon4].setPosition(screenWidth / 4, screenHeight / 4);
                menuSprites[currentMenu][icon4].draw(batch);
                if (menuWords[currentMenu][icon4].endsWith("*")) {
                    battleFont.setColor(Color.RED);
                } else {
                    battleFont.setColor(Color.BLACK);
                }
                if (menuWords[currentMenu][icon4] == "Improvised Explosive Device"){
                    menuWords[currentMenu][icon4] = "IED";
                }
                battleFont.drawWrapped(batch, menuWords[currentMenu][icon4], menuSprites[currentMenu][icon4].getX() - menuSprites[currentMenu][icon4].getWidth()/6, menuSprites[currentMenu][icon4].getY() - battleFont.getLineHeight()/2, 4*menuSprites[currentMenu][icon4].getWidth()/3, BitmapFont.HAlignment.CENTER);



                if (tooltipSelected == true && Tooltip != "") {     //for drawing Tooltip - Tooltips can have 1 or 2 rows
                    battleFont.setScale(screenWidth / 420.0f, screenHeight / 380.0f);
                    battleFont.setColor(Color.BLACK);
                    if (Tooltip.length() > 25) {
                        tooltipBackground.setPosition(menuSprites[currentMenu][tooltipIcon].getX() - menuSprites[currentMenu][tooltipIcon].getWidth() / 2, menuSprites[currentMenu][tooltipIcon].getY());
                        tooltipBackground.setSize(2 * menuSprites[currentMenu][tooltipIcon].getWidth(), 4*menuSprites[currentMenu][tooltipIcon].getHeight()/3);
                        tooltipBackground.draw(batch);

                        battleFont.drawWrapped(batch, Tooltip, tooltipBackground.getX(), tooltipBackground.getY() + 3*battleFont.getLineHeight(), tooltipBackground.getWidth(), BitmapFont.HAlignment.CENTER);
                    }

                    else {
                        tooltipBackground.setPosition(menuSprites[currentMenu][tooltipIcon].getX() - menuSprites[currentMenu][tooltipIcon].getWidth() / 2, menuSprites[currentMenu][tooltipIcon].getY());
                        tooltipBackground.setSize(2 * menuSprites[currentMenu][tooltipIcon].getWidth(), menuSprites[currentMenu][tooltipIcon].getHeight());
                        tooltipBackground.draw(batch);
                        battleFont.drawWrapped(batch, Tooltip, tooltipBackground.getX(), tooltipBackground.getY() + 2*battleFont.getLineHeight(), tooltipBackground.getWidth(), BitmapFont.HAlignment.CENTER);
                    }
                    battleFont.setScale(screenWidth / 300.0f, screenHeight / 250.0f);
                }


            }
        } else {
            battleFont.setScale(screenWidth / 350.0f, screenHeight / 300.0f);
            getAboveSprite().setSize(screenWidth / 8f, screenHeight / 8f);
            getAboveSprite().setPosition(screenWidth / 30 + (screenWidth / 50), screenHeight / 3);
            getAboveSprite().setColor(Color.LIGHT_GRAY);
            getAboveSprite().draw(batch);

            getCurrentSprite().setSize(screenWidth / 5f, screenHeight / 5f);
            getCurrentSprite().setPosition(screenWidth / 30 + (screenWidth / 50) + getAboveSprite().getWidth(), screenHeight / 3 + screenHeight / 16f);

            if (getCurrentIcon().endsWith("*")) {
                getCurrentSprite().setColor(Color.DARK_GRAY);
            } else {
                getCurrentSprite().setColor(Color.WHITE);
            }
            getCurrentSprite().draw(batch);

            getBelowSprite().setSize(screenWidth / 8f, screenHeight / 8f);
            getBelowSprite().setPosition(screenWidth / 30 + (screenWidth / 50) + getAboveSprite().getWidth() + getCurrentSprite().getWidth(), screenHeight / 3);
            getBelowSprite().setColor(Color.LIGHT_GRAY);
            getBelowSprite().draw(batch);

            if (getCurrentIcon().endsWith("*")) {
                battleFont.setColor(Color.RED);
            } else {
                battleFont.setColor(Color.BLACK);
            }
                battleFont.drawWrapped(batch, getCurrentIcon(), getCurrentSprite().getX(), getBelowSprite().getY() + battleFont.getLineHeight(), getCurrentSprite().getWidth(), BitmapFont.HAlignment.CENTER);

            if (tooltipSelected == true && Tooltip != "") {     //for drawing Tooltip - Tooltips can have 1 or 2 rows
                battleFont.setScale(screenWidth / 420.0f, screenHeight / 380.0f);
                battleFont.setColor(Color.BLACK);
                if (Tooltip.length() > 25) {
                    tooltipBackground.setPosition(getCurrentSprite().getX() - getCurrentSprite().getWidth() / 2, getCurrentSprite().getY() + getCurrentSprite().getY() / 2);
                    tooltipBackground.setSize(2 * getCurrentSprite().getWidth(), getCurrentSprite().getHeight() / 2);
                    tooltipBackground.draw(batch);

                    battleFont.drawWrapped(batch, Tooltip, tooltipBackground.getX(), tooltipBackground.getY() + 2 * battleFont.getLineHeight(), tooltipBackground.getWidth(), BitmapFont.HAlignment.CENTER);
                }

                else {
                    tooltipBackground.setPosition(getCurrentSprite().getX() - getCurrentSprite().getWidth() / 2, getCurrentSprite().getY() + getCurrentSprite().getY() / 2);
                    tooltipBackground.setSize(2 * getCurrentSprite().getWidth(), getCurrentSprite().getHeight() / 4);
                    tooltipBackground.draw(batch);

                    battleFont.drawWrapped(batch, Tooltip, tooltipBackground.getX(), tooltipBackground.getY() + battleFont.getLineHeight(), tooltipBackground.getWidth(), BitmapFont.HAlignment.CENTER);
                }
                battleFont.setScale(screenWidth / 300.0f, screenHeight / 250.0f);
            }

            battleFont.setScale(screenWidth / 400.0f, screenHeight / 350.0f);
        }
    }


    public void displayTooltip(int icon) {      //assigns tooltips to the menu

        if (currentMenu == 0){
            //System.out.println("Attack");
            Tooltip = "";
        }

        else if (currentMenu == 1){
            //System.out.println("Attack");
            Tooltip = attackDesc[1][icon];
        }

        else if (currentMenu == 2){
            //System.out.println("Tactics");
            Tooltip = attackDesc[2][icon];
        }

        else if (currentMenu ==3){
            //System.out.println("Items");
            Tooltip = attackDesc[3][icon];
        }
    }


    public void tap(float x, float y) {

        //TODO this doesn't use any Y values yet
        if (!menuStyle2) {
            if (x > getCurrentSprite().getX() && x < getCurrentSprite().getX() + getCurrentSprite().getWidth()) {
                {
                    if (getCurrentIcon().endsWith("*")) {
                        tooltipSelected = true;
                        battleFont.setColor(Color.RED);
                        Tooltip = "You do not have enough energy to use this attack.";
                        battleFont.setColor(Color.BLACK);
                    } else {
                        tooltipSelected = false;
                        Select(currentIcon);
                    }
                }
            }
        } else {
            //top left
            if (x > getCurrentSprite().getX() && x < getCurrentSprite().getX() + getCurrentSprite().getWidth()
                    && y < Gdx.graphics.getHeight() / 2) {
                {
                    if (getCurrentIcon().endsWith("*")) {
                        tooltipIcon = currentIcon;
                        tooltipSelected = true;
                        battleFont.setColor(Color.RED);
                        Tooltip = "You do not have enough energy to use this attack.";
                        battleFont.setColor(Color.BLACK);
                    } else {
                        tooltipSelected = false;
                        Select(currentIcon);
                    }
                }
            }
            //top right
            else if (x > menuSprites[currentMenu][icon2].getX() && x < menuSprites[currentMenu][icon2].getX() + menuSprites[currentMenu][icon2].getWidth()
                    && y < Gdx.graphics.getHeight() / 2) {
                if (menuWords[currentMenu][icon2].endsWith("*")) {
                    tooltipIcon = icon2;
                    tooltipSelected = true;
                    battleFont.setColor(Color.RED);
                    Tooltip = "You do not have enough energy to use this attack.";
                    battleFont.setColor(Color.BLACK);
                } else {
                    tooltipSelected = false;
                    Select(icon2);
                }
            }

            //bottom left
            else if (x > menuSprites[currentMenu][icon3].getX()
                    && x < menuSprites[currentMenu][icon3].getX() + menuSprites[currentMenu][icon3].getWidth()
                    && y > Gdx.graphics.getHeight() / 2) {

                if (menuWords[currentMenu][icon3].endsWith("*")) {
                    tooltipIcon = icon3;
                    tooltipSelected = true;
                    battleFont.setColor(Color.RED);
                    Tooltip = "You do not have enough energy to use this attack.";
                    battleFont.setColor(Color.BLACK);
                } else {
                    tooltipSelected = false;
                    Select(icon3);
                }
            }

            //bottom right
            if (x > menuSprites[currentMenu][icon4].getX()
                    && x < menuSprites[currentMenu][icon4].getX() + menuSprites[currentMenu][icon4].getWidth()
                    && y > Gdx.graphics.getHeight() / 2) {
                if (menuWords[currentMenu][icon4].endsWith("*")) {
                    tooltipIcon = icon4;
                    tooltipSelected = true;
                    battleFont.setColor(Color.RED);
                    Tooltip = "You do not have enough energy to use this attack.";
                    battleFont.setColor(Color.BLACK);
                } else {
                    tooltipSelected = false;
                    Select(icon4);
                }
            }
        }
    }



    //pan (swipe) is for navigating through menu
    public boolean pan(float x, float y, float deltaX, float deltaY){
        if ((deltaX > 20 || deltaX < -20) && (deltaY < 20 || deltaY > -20)) {   //ignore vertical swipes or very short swipes
            dx = deltaX;
            dy = deltaY;
            Tooltipdx = 0;
            Tooltipdy = 0;
            return true;
        }
        else if ((deltaX < 10 || deltaX > -10) && (deltaY >15 || deltaY < -15)) {   //vertical swipes for tooltip
            Tooltipdx = deltaX;
            Tooltipdy = deltaY;
            dx = 0;
            dy = 0;
            return true;
        }

        return false;
    }

    public boolean panStop(float x, float y) {


            if (y < 9 * Gdx.graphics.getHeight() / 10
                    && y > Gdx.graphics.getWidth() / 10) {
                if (dx > 0) {
                    Right();   //right swipe
                } else if (dx < 0) {
                    Left();     //left swipe
                }


                if (!menuStyle2){
                    if (Tooltipdy < 0){     //swipe upwards to show tooltip
                        displayTooltip(currentIcon);
                        tooltipSelected = true;
                    }
                    else{      //swipe downwards or do anything else to hide tooltip
                        tooltipSelected = false;
                    }
                }

                if (menuStyle2) {
                    //top left
                    if (Tooltipdy < 0 && x > getCurrentSprite().getX() && x < getCurrentSprite().getX() + getCurrentSprite().getWidth()
                            && y < Gdx.graphics.getHeight() / 2) {
                        displayTooltip(currentIcon);
                        tooltipIcon = currentIcon;
                        tooltipSelected = true;
                        //top right
                    } else if (Tooltipdy < 0 && x > menuSprites[currentMenu][icon2].getX()
                            && x < menuSprites[currentMenu][icon2].getX() + menuSprites[currentMenu][icon2].getWidth()
                            && y < Gdx.graphics.getHeight() / 2) {
                        displayTooltip(icon2);
                        tooltipIcon = icon2;
                        tooltipSelected = true;
                        //bottom left
                    } else if (Tooltipdy < 0 && x > menuSprites[currentMenu][icon3].getX()
                            && x < menuSprites[currentMenu][icon3].getX() + menuSprites[currentMenu][icon3].getWidth()
                            && y > Gdx.graphics.getHeight() / 2) {
                        displayTooltip(icon3);
                        tooltipIcon = icon3;
                        tooltipSelected = true;
                        //bottom right
                    } else if (Tooltipdy < 0 && x > menuSprites[currentMenu][icon4].getX()
                            && x < menuSprites[currentMenu][icon4].getX() + menuSprites[currentMenu][icon4].getWidth()
                            && y > Gdx.graphics.getHeight() / 2) {
                        displayTooltip(icon4);
                        tooltipIcon = icon4;
                        tooltipSelected = true;
                    } else {      //swipe downwards or do anything else to hide tooltip
                        tooltipSelected = false;
                    }
                }
                return true;
            }
            return false;
        }



    public String getMoveString(int menu, int icon){ return menuWords[menu][icon]; }

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

