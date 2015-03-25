package com.collidge;

//import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;




/*
 * Created by Daniel on 20/01/2015.
 */
public class Fight extends GameState
{



    private double PlayerDam;
    Player playr;
    boolean waitingForTouch=false;
    private double[] damage;
    private int ActionType;
    private int ActionId;
    private boolean comboing;
    private boolean targeting=false;
    private int expEarned;
    private int monsterCode=-1;
    private com.collidge.Animation testAnim;
    private boolean defend;

    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;
    private int damage_taken;
    PopUpText damageNums=new PopUpText();

    SpriteBatch batch;
    Texture texture ;
    Sprite healthBar, healthBackground, EnergyIcon, portrait;
    Sprite menuContainer;
    Sprite selector;
    Sprite player;
    Combo combo;
    private BitmapFont battleFont;
    private TargetPicker targetPicker;
    private Sprite background;
    private Sprite targetArrow,targetReticule,backArrow;
    private int animCount;
    private int[] enemyX,enemyY;


    Timer.Task damager=new Timer.Task()
    {
        @Override
        public void run()
        {                                                                                               


            //if damage was dealt to the player, subtract health
            if (damage[0] > 1)
            {

                damage[0]--;
                playr.changeHealth(-1);
                damage_taken++;
                //check if the player died, if he did, end the fight
                if (playr.getCurrentHealth() <= 0)
                {

                    expEarned=0;
                    DeathState();



                }

            }
            //changes enemy health based on damage done, if enemies die, give their experience to the player and kill them
            for(int i=1;i<damage.length;i++)
            {

                if ((!enemies[i-1].getDead())&&damage[i] >=1)
                {

                    damage[i]--;
                    enemies[i-1].changeHealth(-1);
                    if (enemies[i-1].getHealth() <= 0)
                    {

                        damage[i]=0;
                        expEarned+=enemies[i-1].getExpValue();

                        enemiesLeft--;
                        if(enemiesLeft<=0)
                        {
                            endFight();

                        }
                    }

                }

            }



        }
    };





    //allows the overall player class to be changed within the fight, so that e.g. it can gain experience
    Fight(GameStateManager gsm,Player player)
    {
        super(gsm);
        playr=player;
        EnemySets BasicSet=new EnemySets();
        enemies=BasicSet.getEnemies("Pack");           //Uses the "Pack" EnemyCollection from the EnemySets class. Pack contains up to 7 Freshers.
    }

    Fight(GameStateManager gsm,Player player,String Enemy)
    {
        super(gsm);
        playr=player;
        EnemySets BasicSet=new EnemySets();
        enemies=BasicSet.getEnemies(Enemy);
    }

    @Override
    public void initialize()
    {

       // testAnim=new Animation("walkingRight.png",10);
        combo=new Combo();


        expEarned=0;

        //gets the number and type of enemies to fight

        enemyCount=enemies.length;
        enemiesLeft=enemyCount;
        damage=new double[enemies.length+1];         // damage[0] is player damage taken, damage[1] is for the first enemy, etc.

        enemyX=new int[enemiesLeft];
        enemyY=new int[enemiesLeft];

        for(int i=0;i<enemyX.length;i++)
        {
            enemyX[i]=((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCount)))));
        }
        for(int i=0;i<enemyY.length;i++)
        {
            enemyY[i]=screenHeight / 10 + (int) ((((enemyCount) - (i + 1)) / (double) (enemyCount)) * (screenHeight / 2));
        }
        move=new Attack();        //calls the attack class

        //enemies=new Enemy[enemyCount];
        //allows the player to select a particular enemy to attack
        targetPicker=new TargetPicker(enemies,0);
        //sprite_enemy = new Sprite[enemies.length];
        //addTextures(sprite_enemy, enemies);



        //player and enemy healthbars and attack/tactics panel
        batch = new SpriteBatch();
        texture = new Texture("barHorizontal_green_mid.png");
        healthBar = new Sprite(texture);

        texture = new Texture("barHorizontal_red_mid.png");
        healthBackground = new Sprite(texture);

        texture = new Texture("Transparant_Button.png");
        selector = new Sprite(texture);

        texture = new Texture("blue_circle.png");
        EnergyIcon = new Sprite(texture);

        texture = new Texture("portrait.png");
        portrait = new Sprite(texture);
        texture =new Texture ("background2.png");
        background=new Sprite(texture);
        background.setPosition(0,0);
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        portrait.setPosition(screenWidth / 30 - 3*(screenWidth / 50), 25 * screenHeight / 30);
        portrait.setSize(37*screenWidth/80, 4*screenHeight / 20);
        healthBar.setPosition(screenWidth / 30 + (screenWidth / 50), 25 * screenHeight / 30);
        healthBar.setSize((4 * (screenWidth / 10)), screenHeight / 10);
        healthBackground.setPosition(screenWidth / 30 + (screenWidth / 50), 10 * screenHeight / 30);
        healthBackground.setSize((int) ((playr.getHealth() * (4 * (screenWidth / 10))) / ((double) playr.getHealth())), (int) (screenHeight / 20.0));

        texture = new Texture("panelInset_beige.png");


        menuContainer = new Sprite(texture);
        texture=new Texture("walking_right_animation.png");
        TextureRegion[][] region = TextureRegion.split(texture,32,32);
        texture=new Texture("arrow_up_blue.png");
        targetArrow=new Sprite(texture);
        targetArrow.setSize(screenWidth/12f,screenWidth/12f);
        targetArrow.setOriginCenter();
        texture=new Texture("targetReticule.png");
        targetReticule=new Sprite(texture);
        targetReticule.setSize(targetArrow.getWidth(),targetArrow.getHeight());
        targetReticule.setOriginCenter();
        texture= new Texture("backArrow.png");
        backArrow=new Sprite(texture);
        backArrow.setSize(targetReticule.getWidth(), targetReticule.getHeight());
        backArrow.setOriginCenter();




        testAnim = new com.collidge.Animation(region[0],.2f);

        //calls fightMenu class
        fMenu=new FightMenu(playr);

        waitingForTouch=true;
        battleFont = new BitmapFont();

        Timer.instance().start();
    }


    public void update()
    {
//(int)(((double)(4*(screenWidth/10)))*((double)playr.getCurrentEnergy()/playr.getHealth()))
        damageNums.update();
        testAnim.update(Gdx.graphics.getDeltaTime());

        if(combo.comboing)
        {
            comboing=true;
            combo.update();
        }
        else if(comboing)
        {
            if(monsterCode==-1)
            {
                comboing=false;
            }
            else if(monsterCode==-2)
            {
                comboing = false;
                playerTurnPart3();
            }
            else if(monsterCode<enemies.length)
            {
                if(defend)
                {
                    defend=false;
                    defendTurn(playr, enemies, monsterCode);
                }
                else if(enemies[monsterCode].attackAnimation.getTimesPlayed()>animCount||enemies[monsterCode].getDead())
                {
                    enemyTurnPart2();
                    enemies[monsterCode].attackAnimation.stop();

                }
                else
                {
                    enemies[monsterCode].attackAnimation.update(Gdx.graphics.getDeltaTime());
                }

            }
        }
        Timer.instance().clear();
        Timer.instance().start();
        Timer.instance().postTask(damager);
    }

    @Override
    public void draw()
    {

        batch.begin();


        background.draw(batch);

        portrait.setPosition(0,screenHeight-((int)(portrait.getHeight()*.9)+battleFont.getLineHeight()));
        //draws green health bar and red background. Background size is based on max health and doesn't change- at full hp the bar appears fully green.

        healthBar.setPosition(screenWidth / 30 + (screenWidth / 50), portrait.getY()+(int)(9*portrait.getHeight()/20.0));
        healthBackground.setPosition(healthBar.getX(), healthBar.getY());
        healthBackground.setSize((int)( 35*portrait.getWidth()/40.0),healthBar.getHeight());
        healthBackground.draw(batch);
        healthBackground.setOriginCenter();
        healthBar.setSize((int)((playr.getCurrentHealth()*((int)( 35*portrait.getWidth()/40.0)))/((double)playr.getHealth())),(int)(18*portrait.getHeight()/50.0));
        healthBar.draw(batch);
        battleFont.draw(batch,"MR MAN",healthBackground.getOriginX(),healthBackground.getY()+(healthBackground.getHeight()+battleFont.getLineHeight()));
        battleFont.draw(batch, playr.getCurrentHealth() + "/"+playr.getHealth() ,healthBackground.getOriginX(),(healthBackground.getY()+battleFont.getLineHeight()));

        batch.draw(testAnim.getFrame(),screenWidth/30,screenHeight/30,screenWidth/10,screenHeight/5);


        EnergyIcon.setSize((screenHeight / 20f),(screenHeight / 20f));      //Code Allowing for generation of Energy Icons

        healthBackground.setColor(Color.BLUE);
        healthBackground.setPosition(portrait.getX()+(portrait.getWidth()/10), (int)((healthBar.getY())-(healthBar.getHeight()*.9)));
        healthBackground.setSize((int)(((71*portrait.getWidth()/80.0))*(playr.getCurrentEnergy()/(double)playr.getEnergy())),(int)(healthBackground.getHeight()*.9));
        healthBackground.draw(batch);
        healthBackground.setColor(Color.WHITE);
        battleFont.setColor(Color.WHITE);
        battleFont.draw(batch, playr.getCurrentEnergy()+"" ,healthBackground.getX()+(healthBackground.getWidth()/2),healthBackground.getY()+healthBackground.getHeight());

        portrait.draw(batch);

        //Sets colour and size of battle font, draws "HP" for player health
        battleFont.setColor(Color.BLACK);


        battleFont.setScale(screenWidth/400);


        // Enemy drawing loop
        for(int i=0;i<enemies.length;i++)
        {
            if(!enemies[i].getDead())
            {
                int target=0;
                if((targeting&&targetPicker.getCurrentTarget()==i)||monsterCode==i)
                {
                    //target=Gdx.graphics.getWidth()/10;
                    healthBackground.setPosition((3f * screenWidth /5f), screenHeight - (battleFont.getLineHeight()*3));
                    healthBackground.setSize(2*screenWidth/6f, battleFont.getLineHeight());

                    healthBar.setPosition(healthBackground.getX(),healthBackground.getY());
                    healthBar.setSize((int)(healthBackground.getWidth()*((double)enemies[i].getHealth()/(double)enemies[i].getMaxHealth())),healthBackground.getHeight());

                    healthBackground.setSize(healthBackground.getWidth()-(int)(healthBackground.getWidth()*.05),healthBar.getHeight());

                    healthBackground.draw(batch);
                    healthBar.draw(batch);
                    battleFont.draw(batch,enemies[i].getName(),healthBackground.getX(),healthBackground.getY()+battleFont.getLineHeight()*2);
                    battleFont.draw(batch, enemies[i].getHealth() + "", healthBackground.getX(), healthBackground.getY()+battleFont.getLineHeight());


                    selector.setPosition(enemyX[i],enemyY[i]);
                    selector.setSize(enemies[i].width,enemies[i].height);
                    selector.draw(batch);

                }
                else
                {
                    target=0;
                }

                /*int enemyCountTemp;
                int iTemp;

                if (i < 5) {    //2 rows of enemies, 5 in each
                    //batch.draw(enemies[i].animation.getFrame(), ((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCount))))) - target, screenHeight / 10 + (int) ((((enemyCount) - (i + 1)) / (double) (enemyCount)) * (screenHeight / 2)), enemies[i].width, enemies[i].height);
                    if (enemyCount > 5){
                        enemyCountTemp = 5;
                    }
                    else {
                        enemyCountTemp = enemyCount;
                    }

                    batch.draw(enemies[i].animation.getFrame(),
                            ((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                            screenHeight / 18 + (int) ((((enemyCountTemp) - (i + 1)) / (double) (enemyCountTemp)) * (screenHeight / 1.8)),
                            enemies[i].width,
                            enemies[i].height);


                }

                else if (i < 9 && i >= 5) {
                    //batch.draw(enemies[i].animation.getFrame(), ((int) (screenWidth / 2 + enemies[i].width + (i * (screenWidth / (double) (3 * enemyCount))))) - target, screenHeight / 10 + (int) ((((enemyCount) - (i + 1 - 5)) / (double) (enemyCount)) * (screenHeight / 2)), enemies[i].width, enemies[i].height);

                    enemyCountTemp = enemyCount - 5;
                    iTemp = i-5;

                    batch.draw(enemies[i].animation.getFrame(),
                            ((int) (screenWidth / 2 + 1.5*enemies[i].width + (iTemp * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                            screenHeight / 28 + (int) ((((enemyCountTemp) - (iTemp + 1)) / (double) (enemyCountTemp)) * (screenHeight / 2)),
                            enemies[i].width,
                            enemies[i].height);
                }

                else {
                    enemyCountTemp = enemyCount - 9;
                    iTemp = i-9;


                }*/

                if(!targeting)
/*
                    batch.draw(enemies[i].animation.getFrame(),
                            ((int) (screenWidth / 2 + 2.5*enemies[i].width + (iTemp * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                            2*screenHeight / 5 + (int) ((((enemyCountTemp) - (iTemp + 1)) / (double) (enemyCountTemp)) * (screenHeight / 2)),
                            enemies[i].width,
                            enemies[i].height);
                }



                    if(!targeting)
*/
                {
                    battleFont.setColor(Color.RED);
                    battleFont.draw(batch, enemies[i].getHealth() + "", ((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCount)))))+screenWidth/10, battleFont.getLineHeight());
                    battleFont.setColor(Color.BLACK);
                }
                /*sprite_enemy[i].setSize(screenWidth/12f, screenWidth/12f);
                sprite_enemy[i].setPosition(screenWidth/2f, screenHeight/12f);
                sprite_enemy[i].draw(batch);*/

                if(targeting&&(targetPicker.getCurrentTarget()+targetPicker.getTargetingId()>=i&&targetPicker.getCurrentTarget()-targetPicker.getTargetingId()<=i))
                {
                    /*battleFont.setColor(Color.RED);
                    battleFont.draw(batch, "Tap to choose a target!", screenWidth/20, screenHeight/2);
                    battleFont.setColor(Color.BLACK);


                    /*if (i < 5) {
                        if (enemyCount > 5){
                            enemyCountTemp = 5;
                        }
                        else {
                            enemyCountTemp = enemyCount;
                        }
                        selector.setPosition(((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                                screenHeight / 18 + (int) ((((enemyCountTemp) - (i + 1)) / (double) (enemyCountTemp)) * (screenHeight / 1.8)));
                    }

                    else if (i < 9 && i >= 5) {
                        enemyCountTemp = enemyCount - 5;
                        iTemp = i-5;

                        selector.setPosition(
                                ((int) (screenWidth / 2 + 1.5*enemies[i].width + (iTemp * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                                screenHeight / 28 + (int) ((((enemyCountTemp) - (iTemp + 1)) / (double) (enemyCountTemp)) * (screenHeight / 2)));
                    }

                    else {
                        enemyCountTemp = enemyCount - 9;
                        iTemp = i-9;

                        selector.setPosition(((int) (screenWidth / 2 + 2.5*enemies[i].width + (iTemp * (screenWidth / (double) (3 * enemyCountTemp))))) - target,
                                2*screenHeight / 5 + (int) ((((enemyCountTemp) - (iTemp + 1)) / (double) (enemyCountTemp)) * (screenHeight / 2)));
                    }

                    selector.setSize(enemies[i].width,enemies[i].height);
                    if (targetPicker.targetHighlighted == true) {   //don't draw the selector box unless a target has been tapped
                        battleFont.setColor(Color.RED);
                        battleFont.draw(batch, "Tap again to confirm!", screenWidth/20, screenHeight/2 - battleFont.getLineHeight());
                        battleFont.setColor(Color.BLACK);
                        selector.draw(batch);
                    }*/


                }

                if(monsterCode==i)
                {
                    batch.draw(enemies[i].attackAnimation.getFrame(), enemyX[i], enemyY[i], enemies[i].width, enemies[i].height);

                }
                else
                {
                    batch.draw(enemies[i].animation.getFrame(), ((int) (screenWidth / 2 + (i * (screenWidth / (double) (3 * enemyCount))))) - target, screenHeight / 10 + (int) ((((enemyCount) - (i + 1)) / (double) (enemyCount)) * (screenHeight / 2)), enemies[i].width, enemies[i].height);
                    enemies[i].animation.update(Gdx.graphics.getDeltaTime());

                }

                /*else
                {
                    enemies[i].animation.pause();

                }*/
            }
        }

        if(targeting) //draws old targeting interface
        {

            targetArrow.setRotation(90);
            targetArrow.setPosition(screenWidth / 10, screenHeight / 2);
            targetArrow.draw(batch);
            targetReticule.setPosition(targetArrow.getX() + targetArrow.getWidth(), targetArrow.getY());
            targetReticule.draw(batch);
            targetArrow.setRotation(-90);
            targetArrow.setPosition(targetReticule.getX() + targetReticule.getWidth(), targetReticule.getY());
            targetArrow.draw(batch);
            backArrow.setPosition(targetReticule.getX(), targetReticule.getY() - backArrow.getHeight());
            backArrow.draw(batch);
        }
        if(damageNums.popUps.size()>0)
        {
            damageNums.draw(batch);
        }

        if(!fMenu.actionSelected)
        {
            fMenu.draw(batch,screenWidth,screenHeight);
        }
        if(combo.comboing) //if in combo phase
        {

            combo.draw(batch);

        }

        batch.end();
    }

//----------------------------------------------------------------------------------
// these are just input methods that must be implemented
//----------------------------------------------------------------------------------

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        if (waitingForTouch&&!fMenu.actionSelected)
        {
            fMenu.pan(x, y, deltaX, deltaY);
        }

        else if (combo.comboing)
        {
            combo.pan(x, y, deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        if (waitingForTouch) {
            if (!fMenu.actionSelected) {
                fMenu.panStop(x, y);   //when you swipe, display the tooltip
            } else if (combo.comboing) {
                combo.panStop(x, y, pointer, button);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }

//-------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        //if selecting an action from the fight menu. Actions have an ID and a type.

        if(waitingForTouch)
        {
            if (!fMenu.actionSelected)
            {
                //tap the menu to select an action
                fMenu.tap(x, y);
                if (fMenu.actionSelected)
                {

                    ActionId = fMenu.getActionId();
                    ActionType = fMenu.getActionType();
                    playerTurn(playr, enemies);
                }
            }

            //targeting an enemy after selecting an action

            else if (targeting)
            {
                /*for (int i = 0; i < enemies.length; i++)
                {
                    // if (x> screenWidth/2) {   //arbitrary x values at the moment, vaguely at the right side of the screen

                    // if (y < 9 * screenHeight / 10 - (int) (((enemyCount - (i + 1)) / (double) (enemyCount)) * (screenHeight / 2))
                    //         && y > 9 * screenHeight / 10 - (int) (((enemyCount - (i + 1)) / (double) (enemyCount)) * (screenHeight / 2)) - enemies[0].height) {
*/

                    if (y < targetReticule.getY() && y > targetReticule.getY() - targetReticule.getHeight())
                    {
                        if (x < targetReticule.getX() && x > targetReticule.getX() - targetReticule.getWidth())
                        {
                            targetPicker.Left();

                        } else if (x > targetReticule.getX() + targetReticule.getWidth() && x < targetReticule.getX() + (targetReticule.getWidth() * 2))
                        {
                            targetPicker.Right();

                        } else if (x > targetReticule.getX() && x < targetReticule.getX() + targetReticule.getWidth())
                        {
                            targetPicker.Select();

                        }



                        if (targetPicker.targetSelected)     //move on to the next part of combat after a target is selected
                        {
                            targeting = false;
                            playerTurnPart2();
                            return true;
                        }
                    }
                    else if (x > targetReticule.getX() && x < targetReticule.getX() + targetReticule.getWidth() && y > targetReticule.getY() && y < targetReticule.getY() + targetReticule.getHeight())
                    {
                        targeting = false;
                        waitingForTouch = true;
                        fMenu.actionSelected = false;
                    }
                    if(x>enemyX[0]&&x<enemyX[enemyX.length-1]+enemies[enemyX.length-1].width)
                    {

                       for(int i=0;i<enemyX.length;i++)
                       {
                           if(x>enemyX[i]&&x<enemyX[i]+enemies[i].width)
                           {
                               if(!enemies[i].getDead())
                               {
                                   if(screenHeight-y>enemyY[i]&&screenHeight-y<enemyY[i]+enemies[i].height)
                                   {
                                       targetPicker.goTo(i);
                                   }
                               }
                           }
                       }
                       if (targetPicker.targetSelected)     //move on to the next part of combat after a target is selected
                       {
                            targeting = false;
                            playerTurnPart2();
                            return true;
                       }

                    }

                //}
            } else if (combo.comboing)     //if in combo phase, accept combo input
            {
                combo.tap((int) x, (int) y);
            }

        }

        return false;

    }

    private void playerTurn(Player player,Enemy[] monsters)
    {
        PlayerDam=0;

        monsterCode=-2;
        if(ActionType==2)       //flee
        {

            if(fMenu.getMoveString(ActionType,ActionId).equals("Flee"))
            {

                expEarned=0;
                endFight();
                return;

            }

        }

        if(ActionType==3)       //use an item
        {
            player.useItem(fMenu.getMoveString(ActionType,ActionId));


        }
        else if(ActionType==1)      //attack
        {

            targetPicker.reset(enemies,player.attackRange(fMenu.getMoveString(ActionType,ActionId)));
            targeting=true;
            return;
        }
        playerTurnEnd();

    }

    private void playerTurnPart2()      //Initiating combo
    {


        combo.initiateCombo(ActionId-1,this);
        comboing=true;
    }

    private void playerTurnPart3()      //After the combo, applying the multipliers
    {
        //TODO remove system outs left for debugging of combos
        playr.changeEnergy(-(playr.getAttackEnergyCosts(fMenu.getMoveString(ActionType, ActionId))));
        for(int i=-targetPicker.getTargetingId();i<=targetPicker.getTargetingId();i++)
        {
            System.out.println("Attacking: "+i);
            if(targetPicker.getSelectedTarget()+i>=0&&targetPicker.getSelectedTarget()+i<enemies.length)
            {

                PlayerDam = playr.attackPicker(fMenu.getMoveString(ActionType, ActionId));
                System.out.println("Dam to "+i+": " + PlayerDam);
                if(enemies[targetPicker.getSelectedTarget()+i].getDefence()>0)
                {
                    PlayerDam *= (playr.getAttack() / enemies[targetPicker.getSelectedTarget() + i].getDefence());
                }
                else
                {
                    PlayerDam*=playr.getAttack();
                }
                System.out.println("Atk: " + playr.getAttack() + "   Def: " + enemies[targetPicker.getSelectedTarget() + i].getDefence());
                PlayerDam *= Math.abs(combo.skill);
                System.out.println("After Mult of " + combo.skill + ": " + PlayerDam);
                if (PlayerDam < 1)
                {
                    PlayerDam = 1;
                }
                if(combo.skill>.9)
                {
                    PlayerDam++;
                }
                damage[targetPicker.getSelectedTarget() + 1+i] += PlayerDam;
                if(!enemies[targetPicker.getSelectedTarget() + i].getDead())
                {
                    damageNums.Add
                    (
                            String.valueOf(-(int) PlayerDam),
                            (float)(enemyX[targetPicker.getSelectedTarget()+i]+(enemies[targetPicker.getSelectedTarget()+i].width/2))/screenWidth,
                            ((float)(enemyY[targetPicker.getSelectedTarget()+i]+enemies[targetPicker.getSelectedTarget()+i].height)/screenHeight)
                    );
                }
            }
        }
        playerTurnEnd();
    }
    //at the end of the player's turn, if there are enemies left, start the enemy's turn, otherwise if all are dead end the fight
    private void playerTurnEnd()
    {


        playr.changeEnergy(playr.getIntelligence());

        if(enemiesLeft>0&&playr.getCurrentHealth()>0)
        {
            monsterCode=-1;
            enemyTurn(playr, enemies,0);
        }
        else if (playr.getCurrentHealth()<=0)
        {

            DeathState();

        }
        else
        {
            endFight();
        }

        fMenu.refreshMenus(playr);
    }

    private void enemyTurn(Player player,Enemy[] monsters,int monsterId)
    {



        monsterCode=monsterId;
        System.out.println("Monster:"+monsterId);
        monsters[monsterId].attackAnimation.setCurrentFrame(0);
        animCount=monsters[monsterId].attackAnimation.getTimesPlayed();
        monsters[monsterId].attackAnimation.play();
        defend=false;
        if(monsterId<=monsters.length-1&&!monsters[monsterId].getDead())
        {



            comboing = true;
        }
        else
        {

            boolean fight=false;
            while(monsterId<monsters.length)
            {

                if(!monsters[monsterId].getDead())
                {
                    monsterCode=monsterId;
                    fight=true;
                    monsterId=monsters.length;
                }
                monsterId++;
            }
            if(fight)
            {
                enemyTurn(player, monsters, monsterCode);
            }
            else
            {
                waitingForTouch=true;
                fMenu.actionSelected=false;
            }
        }

    }

    private void enemyTurnPart2()
    {
        if(!enemies[monsterCode].getDead())
        {
            System.out.println("X");
            combo.initiateCombo(-1, this);
            defend = true;
        }
        else
        {
            defend=true;
        }
    }

    private void defendTurn(Player player,Enemy[] monsters,int monsterId)
    {
        double dam=-1;
        System.out.println("Id: "+monsterId+"/"+monsters.length);
        if(monsterId>=monsters.length||monsterId<0)
        {

        }
        else if(!monsters[monsterId].getDead()&&player.getCurrentHealth()>0)
        {
            if(player.getDefence()>0)
            {
                dam=(monsters[monsterId].getAttack() / player.getDefence());
            }
            else
            {
                dam+=monsters[monsterId].getAttack();
            }
            System.out.println("Damage from "+monsters[monsterId].getName()+": "+dam);
            dam*=(1-(combo.skill*combo.skill));

            //      System.out.println("Monster " + i + " attacks");
            if (dam <= 1)
            {
                //        System.out.println("Damage by monster " + i + " resisted");
                if(combo.skill<.9)
                {
                    damage[0]++;
                    damageNums.Add(String.valueOf(1), .15f, .3f);

                }
                else
                {
                    damageNums.Add("Blocked!",.15f,.3f,Color.WHITE,150,4.0);
                }

            }
            else if(combo.skill<.95)
            {
                damage[0]+=dam;
                damageNums.Add(String.valueOf((int)dam),.15f,.3f);
                //player.changeHealth(-(damage));
                //      System.out.println("Enemy " + i + " deals " + (dam) + " damage");
                if (player.getCurrentHealth() <= 0)
                {
                    enemiesLeft = -1;
                    //        System.out.println("you lose");
                    monsterCode=-1;
                }

            }
            else
            {
                damageNums.Add("Blocked!",.15f,.3f,Color.WHITE,150,4.0);

            }

        }

        System.out.println("Skill: "+combo.skill+" >damage: "+dam);
        monsterCode++;
        if(monsterCode>=monsters.length)
        {
            monsterCode=-1;
        }

        if(enemiesLeft>0&&monsterCode==-1)
        {

            waitingForTouch=true;
            fMenu.actionSelected=false;
        }
        else
        {
            System.out.println(monsterCode+":X");
            enemyTurn(player,monsters,monsterCode);
        }
        defend=false;


    }


    private void endFight()
    {
        damage[0]=0;
        int[]ratings = combo.num_ratings;
        combo.delete();
        Timer.instance().clear();
        Timer.instance().stop();
        batch.dispose();
        texture.dispose();
        battleFont.dispose();
        if (expEarned == 0)
            gsm.endFight();
        else
            gsm.StartWinState(playr,expEarned, enemies, damage_taken, ratings);
    }

    private void DeathState()       //this function is called to check when the fight is over, and then display a splash screen
    {
        damage[0]=0;
        combo.delete();
        batch.dispose();
        texture.dispose();
        battleFont.dispose();
        Timer.instance().clear();
        Timer.instance().stop();
        gsm.StartDeathState(playr);
    }
}

