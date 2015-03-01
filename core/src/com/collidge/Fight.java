package com.collidge;

//import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;




/**
 * Created by Daniel on 20/01/2015.
 */
public class Fight extends GameState
{



    private int PlayerDam;
    Player playr;
    boolean waitingForTouch=false;
    int action;
    private int[] damage;
    private int ActionType;
    private int ActionId;
    private boolean comboing;
    private boolean targeting=false;
    private int expEarned;
    private int monsterCode=-1;


    Timer.Task damager=new Timer.Task()
    {
        @Override
        public void run()
        {


            //if damage was dealt to the player, subtract health
            if (damage[0] > 0)
            {

                damage[0]--;
                playr.changeHealth(-1);
                //check if the player died, if he did, end the fight
                if (playr.getCurrentHealth() <= 0)
                {

                    expEarned=0;
                    endFight();



                }

            }
            //changes enemy health based on damage done, if enemies die, give their experience to the player and kill them
            for(int i=1;i<damage.length;i++)
            {

                if ((!enemies[i-1].getDead())&&damage[i] > 0)
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



    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;


    SpriteBatch batch;
    Texture texture ;
    Sprite healthBar, healthBackground, EnergyIcon;
    Sprite menuContainer;
    Sprite selector;
    Sprite player;
    Sprite[] sprite_enemy;
    Combo combo;
    private BitmapFont battleFont;
    private TargetPicker targetPicker;


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
        enemies=BasicSet.getEnemies(Enemy);           //Uses the "Pack" EnemyCollection from the EnemySets class. Pack contains up to 7 Freshers.
    }

    @Override
    public void initialize()
    {

        combo=new Combo();

        expEarned=0;

        //gets the number and type of enemies to fight
        sprite_enemy = new Sprite[enemies.length];
        addTextures(sprite_enemy,enemies);          //loads the textures for each of the enemies into an array
        enemyCount=enemies.length;
        enemiesLeft=enemyCount;
        damage=new int[enemies.length+1];         // damage[0] is player damage taken, damage[1] is for the first enemy, etc.

        move=new Attack();        //calls the attack class

        //enemies=new Enemy[enemyCount];

        //allows the player to select a particular enemy to attack
        targetPicker=new TargetPicker(enemies);




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
        healthBar.setPosition(screenWidth / 30 + (screenWidth / 50), 25 * screenHeight / 30);
        healthBar.setSize((4 * (screenWidth / 10)), screenHeight / 10);
        healthBackground.setPosition(screenWidth / 30 + (screenWidth / 50), 10 * screenHeight / 30);
        healthBackground.setSize((int) ((playr.getHealth() * (4 * (screenWidth / 10))) / ((double) playr.getHealth())), (int) (screenHeight / 20.0));

        texture = new Texture("panelInset_beige.png");

        menuContainer = new Sprite(texture);






        //calls fightMenu class
        fMenu=new FightMenu(playr);

        waitingForTouch=true;
        battleFont = new BitmapFont();



        Timer.instance().start();
    }


    public void update()
    {
//(int)(((double)(4*(screenWidth/10)))*((double)playr.getCurrentEnergy()/playr.getHealth()))
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
                defendTurn(playr,enemies,monsterCode);
            }
        }
        Timer.instance().clear();
        Timer.instance().start();
        Timer.instance().postTask(damager);
        healthBackground.setSize((int) ((playr.getHealth() * (4 * (screenWidth / 10))) / ((double) playr.getHealth())), (int) (screenHeight / 16.0));
        healthBar.setSize((int)((playr.getCurrentHealth()*(4*(screenWidth/10)))/((double)playr.getHealth())),(int)(screenHeight/16.0));
    }

    @Override
    public void draw() {

        batch.begin();


        //draws green health bar and red background. Background size is based on max health and doesn't change- at full hp the bar appears fully green.
        healthBackground.setPosition(screenWidth / 30 + (screenWidth / 50), 28 * screenHeight / 30);
        healthBackground.draw(batch);
        healthBar.setPosition(screenWidth / 30 + (screenWidth / 50), 28 * screenHeight / 30);
        healthBar.draw(batch);

        EnergyIcon.setSize((float)(screenHeight / 16.0),(float)(screenHeight / 16.0));      /**Code Allowing for generation of Energy Icons  */
        for (int i = 0; i < playr.getCurrentEnergy(); i++)
        {
            if (i<16)
                EnergyIcon.setPosition((float)(4*screenWidth /75 + i*screenHeight/16.0), 209 * screenHeight / 240);
            else
                EnergyIcon.setPosition((float)(4*screenWidth /75 + i*screenHeight/16.0), 97 * screenHeight / 120);

            EnergyIcon.draw(batch);
        }
        //Sets colour and size of battle font, draws "HP" and "EN" for player health and energy
        battleFont.setColor(Color.BLACK);

        //if no action has been selected in the fight menu, draws the fight menu
        if(!fMenu.actionSelected)
        {
            fMenu.draw(batch,screenWidth,screenHeight);
        }

        battleFont.setColor(Color.BLACK);

        battleFont.setScale(screenWidth / (9 * 40f), screenHeight / (9 * 40f));

        /*********************** Enemy Generation Loop **********************/
        for(int i=0;i<enemies.length;i=i+2)
        {
            if(!enemies[i].getDead())
            {

                healthBackground.setPosition((int) (3.0 * screenWidth / 5), screenHeight - (battleFont.getLineHeight() * (( i + 1 ) * 2 )));
                healthBackground.setSize(2*screenWidth/15f, battleFont.getLineHeight());
                healthBackground.draw(batch);
                healthBar.setPosition((int)(3.0*screenWidth/5),screenHeight - (battleFont.getLineHeight() * (( i + 1 ) * 2 )));
                healthBar.setSize(enemies[i].getHealth()*((2*screenWidth/15f)/enemies[i].getMaxHealth()),battleFont.getLineHeight());
                healthBar.draw(batch);
                battleFont.draw(batch,enemies[i].getName(),(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));
                battleFont.draw(batch, enemies[i].getHealth() + "", 3 * screenWidth / 5, (screenHeight - battleFont.getLineHeight() - (battleFont.getLineHeight() * (i * 2))));

            }
            else
                battleFont.draw(batch,"Defeated",(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));
            if (i+1 >= enemies.length)
                break;
            if(!enemies[i+1].getDead())
            {

                healthBackground.setPosition((int) (4.0 * screenWidth / 5), screenHeight - (battleFont.getLineHeight() * (( i + 1 ) * 2 )));
                healthBackground.setSize(2*screenWidth/15f, battleFont.getLineHeight());
                healthBackground.draw(batch);
                healthBar.setPosition((int)(4.0*screenWidth/5),screenHeight - (battleFont.getLineHeight() * (( i + 1 ) * 2 )));
                healthBar.setSize(enemies[i+1].getHealth()*((2*screenWidth/15f)/enemies[i+1].getMaxHealth()),battleFont.getLineHeight());
                healthBar.draw(batch);
                battleFont.draw(batch,enemies[i+1].getName(),(int)(4.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));
                battleFont.draw(batch, enemies[i+1].getHealth() + "", 4 * screenWidth / 5, (screenHeight - battleFont.getLineHeight() - (battleFont.getLineHeight() * (i * 2))));

            }
            else
                battleFont.draw(batch,"Defeated",(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));
        }

        //moving the target selector icon from enemy to enemy, if you are in the targeting phase of the fight (after an offensive action is selected)
        if(targeting)
        {
            selector.setSize((float)(3.0*screenWidth/20.0), battleFont.getLineHeight()*2);
            if (targetPicker.getCurrentTarget()%2 ==0)      /**Determines what column the Enemy's data should be in **/
                selector.setPosition(3f/5f*screenWidth,screenHeight-(battleFont.getLineHeight()*((targetPicker.getCurrentTarget()+1)*2)));
            else
                selector.setPosition(4f/5f*screenWidth,screenHeight-(battleFont.getLineHeight()*((targetPicker.getCurrentTarget())*2)));

            selector.draw(batch);
        }
        //TODO replace Placeholder
        else if(combo.comboing) //if in combo phase
        {

            combo.draw(batch);
            /*
            battleFont.setColor(Color.WHITE);
            if(combo.skill/combo.tapTotal<.2)
            {
                battleFont.draw(batch,"Bad",(int)(2*screenWidth/5),battleFont.getLineHeight());
            }
            else if(combo.skill/combo.tapTotal<.4)
            {
                battleFont.draw(batch,"OK",(int)(2*screenWidth/5),battleFont.getLineHeight());
            }
            else if(combo.skill/combo.tapTotal<.6)
            {
                battleFont.draw(batch,"Good",(int)(2*screenWidth/5),battleFont.getLineHeight());
            }
            else if(combo.skill/combo.tapTotal<.8)
            {
                battleFont.draw(batch,"Great",(int)(2*screenWidth/5),battleFont.getLineHeight());
            }
            else
            {
                battleFont.draw(batch,"Perfect",(int)(2*screenWidth/5),battleFont.getLineHeight());
            }*/
           // battleFont.draw(batch,((combo.skill/combo.tapTotal))+"",(int)(2*screenWidth/5),battleFont.getLineHeight());


        }
        batch.end();
    }

//*********************************************************************************************************************
//******************************* these are just input methods that must be implemented *******************************
//*********************************************************************************************************************

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
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
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

//*************************************************************************************************************************
//*************************************************************************************************************************

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        //if selecting an action from the fight menu. Actions have an ID and a type.
        if(waitingForTouch==true)
        {
            if(fMenu.actionSelected==false)
            {
                //tap the menu to select an action
                fMenu.touchDown(x/screenWidth,y/screenHeight, screenHeight);
                if(fMenu.actionSelected)
                {
                    ActionId=fMenu.getActionId();
                    ActionType=fMenu.getActionType();
                    playerTurn(playr,enemies);
                }
            }

            //targeting an enemy after selecting an action- tap left or right of screen to move target picker icon, tap middle to select target
            else if(targeting)
            {

                if(x<screenWidth/3)
                {
                    targetPicker.Left();

                }
                else if(x>2*(screenWidth/3))
                {
                    targetPicker.Right();

                }
                else
                {
                    targetPicker.Select();

                }

                if(targetPicker.targetSelected)     //move on to the next part of combat after a target is selected
                {
                    targeting=false;
                    playerTurnPart2();
                }
            }

            else if(combo.comboing)     //if in combo phase, accept combo input
            {
                combo.tap((int)x,(int)y);
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

            if(fMenu.getMoveString(ActionType,ActionId)=="Flee")
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

            targetPicker.reset(enemies);
            targeting=true;
            return;
        }
        playerTurnEnd();

    }

    private void playerTurnPart2()      //Initiating combo
    {


        combo.initiateCombo(ActionId-1,this);
        comboing=true;
        return;
    }

    private void playerTurnPart3()      //After the combo, applying the multipliers
    {
        //TODO remove system outs left for debugging of combos
        PlayerDam = playr.attackPicker(fMenu.getMoveString(ActionType, ActionId));
        System.out.println("Dam: "+PlayerDam);
        PlayerDam*=(playr.getAttack()-enemies[targetPicker.getSelectedTarget()].getDefence());
        System.out.println("Atk: "+playr.getAttack()+"   Def: "+ enemies[targetPicker.getSelectedTarget()].getDefence());
        PlayerDam *= Math.abs(combo.skill);
        System.out.println("After Mult of "+combo.skill+": "+PlayerDam);
        if(PlayerDam<1)
        {
            PlayerDam=1;
        }
        damage[targetPicker.getSelectedTarget()+1]+=PlayerDam;


        playerTurnEnd();
    }
    //at the end of the player's turn, if there are enemies left, start the enemy's turn, otherwise if all are dead end the fight
    private void playerTurnEnd()
    {


        playr.changeEnergy(playr.getIntelligence());

        if(enemiesLeft>0&&playr.getCurrentHealth()>=0)
        {
            monsterCode=-1;
            enemyTurn(playr, enemies,0);
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
        if(monsterId<=monsters.length-1&&!monsters[monsterId].getDead())
        {
            System.out.println("X");
            combo.initiateCombo(-1, this);
            comboing = true;
        }
        else
        {
            boolean fight=false;
            while(monsterId<monsters.length-1)
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

    private void defendTurn(Player player,Enemy[] monsters,int monsterId)
    {
        double dam=-1;
        System.out.println("Id: "+monsterId+"/"+monsters.length);
        if(monsterId>=monsters.length||monsterId<0)
        {

        }
        else if(!monsters[monsterId].getDead()&&player.getCurrentHealth()>0)
        {
            dam=(monsters[monsterId].getAttack() - player.getDefence());
            System.out.println("Damage from "+monsters[monsterId].getName()+": "+dam);
            dam*=(1-(combo.skill/2));

            //      System.out.println("Monster " + i + " attacks");
            if (dam <= 1)
            {
                //        System.out.println("Damage by monster " + i + " resisted");
                damage[0]++;

            }
            else
            {
                damage[0]+=dam;

                //player.changeHealth(-(damage));
                //      System.out.println("Enemy " + i + " deals " + (dam) + " damage");
                if (player.getCurrentHealth() <= 0)
                {
                    enemiesLeft = -1;
                    //        System.out.println("you lose");
                    monsterCode=-1;
                }

            }

        }

        System.out.println("Skill: "+combo.skill+" >damage: "+dam);
        monsterCode++;
        if(monsterCode>=monsters.length)
        {
            monsterCode=-1;
        }
        // System.out.println("Player- Lvl: \t"+player.getLevel()+" \tExp:\t"+player.getExperience());
        //System.out.print("Hp: \t"+player.getCurrentHealth()+"/"+player.getHealth());
        //System.out.println("\tEn:\t "+player.getCurrentEnergy()+"/"+player.getEnergy());
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


    }

    private void addTextures(Sprite[] sprite_enemy,Enemy[] enemies)
    {
        for (int i = 0; i< enemies.length; i++) {
            if (enemies[i].getName() == "Fresher")
            {
                texture = new Texture("badlogic.jpg");  //Placeholder stuff until I have sprites for enemies
                sprite_enemy[i] = new Sprite(texture);
            }
            else if (enemies[i].getName() == "Frat boy")
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }

            else if (enemies[i].getName() == "Debater")
            {
                texture = new Texture("badlogic.jpg");
                sprite_enemy[i] = new Sprite(texture);
            }

            else if (enemies[i].getName() == "Lecturer")
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
    };

    private void endFight()
    {
        damage[0]=0;
        combo.delete();
        Timer.instance().clear();
        Timer.instance().stop();
        playr.addExperience(expEarned);
        if(playr.getLevelUpCounter()<=0)
        {
            gsm.endFight();
        }
        else
        {
            gsm.levelUpState(playr);
        }
        return;
    }

}

