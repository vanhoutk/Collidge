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

    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;


    SpriteBatch batch;
    Texture texture,textureMenu ;
    Sprite healthBar;
    Sprite menuContainer;

    Combo combo;


    private BitmapFont battleFont;


    private TargetPicker targetPicker;


    Fight(GameStateManager gsm,Player player)
    {
        super(gsm);
        playr=player;
    }


    @Override
    public void initialize()
    {


        combo=new Combo();


        EnemySets BasicSet=new EnemySets();
        enemies=BasicSet.getEnemies("Pack");

        enemyCount=enemies.length;
        enemiesLeft=enemyCount;
        damage=new int[enemies.length+1];
        move=new Attack();
        //TODO add randomness and enemy lookup from world sprites
        //enemies=new Enemy[enemyCount];

        targetPicker=new TargetPicker(enemies);





        batch=new SpriteBatch();
        texture =new Texture("barHorizontal_green_mid.png");
        healthBar=new Sprite(texture);

        healthBar.setPosition(screenWidth/30+(screenWidth/50),25*screenHeight/30);
        healthBar.setSize((4*(screenWidth/10)),screenHeight/10);

        texture=new Texture("panelInset_beige.png");

        menuContainer=new Sprite(texture);





        //TODO add someway to input a player rather than create a new one (maybe in the gsm)

        fMenu=new FightMenu(playr);

        waitingForTouch=true;
        battleFont = new BitmapFont();


        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {

                if (damage[0] > 0)
                {
                    damage[0]--;
                    playr.changeHealth(-1);
                    if (playr.getCurrentHealth() <= 0)
                    {
                        combo.delete();
                        gsm.endFight();
                        damage[0] = 0;
                        cancel();


                    }

                }
                for(int i=1;i<damage.length;i++)
                {
                    if ((!enemies[i-1].getDead())&&damage[i] > 0)
                    {
                        damage[i]--;
                        enemies[i-1].changeHealth(-1);
                        if (enemies[i-1].getHealth() <= 0)
                        {

                            damage[i]=0;
                            playr.addExperience(enemies[i-1].getExpValue());

                            enemiesLeft--;
                            if(enemiesLeft<=0)
                            {
                                cancel();
                            }
                        }

                    }
                }



            }
        }
                , 0, .1f);
    }


    public void update()
    {
//(int)(((double)(4*(screenWidth/10)))*((double)playr.getCurrentEnergy()/playr.getHealth()))


        if(gsm.returnCurrentState()!=this)
        {
            Timer.instance().clear();
            Timer.instance().stop();
        }
        if(combo.comboing)
        {
            comboing=true;
            combo.update();
        }
        else if(comboing)
        {
            comboing=false;
            playerTurnPart3();
        }

        healthBar.setSize((int)((playr.getCurrentHealth()*(4*(screenWidth/10)))/((double)playr.getHealth())),(int)(screenHeight/20.0));



    }

    @Override
    public void draw()
    {

        batch.begin();




        healthBar.setPosition(screenWidth/30+(screenWidth/50),25*screenHeight/30);
        healthBar.draw(batch);



        battleFont.setColor(Color.BLACK);
        battleFont.setScale(screenWidth/200.0f,screenHeight/200.0f);
        battleFont.draw(batch, playr.getCurrentHealth()+" Hp", screenWidth/5,9*screenHeight/10);
        battleFont.draw(batch, playr.getCurrentEnergy()+" En", screenWidth/5,(9*screenHeight/10)-battleFont.getLineHeight());

        if(!fMenu.actionSelected)
        {



            menuContainer.setSize(screenWidth/3f,battleFont.getLineHeight()*1.2f);
            menuContainer.setPosition( screenWidth/6,screenHeight/2-battleFont.getLineHeight());
            menuContainer.draw(batch);

            menuContainer.setPosition( screenWidth/6,screenHeight/2);
            menuContainer.draw(batch);

            menuContainer.setPosition( screenWidth/6,screenHeight/2-(battleFont.getLineHeight()*2));
            menuContainer.draw(batch);

            if(fMenu.getAboveIcon().endsWith("*"))
            {
                battleFont.setColor(Color.RED);
            }
            else
            {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, fMenu.getAboveIcon(), screenWidth/5,screenHeight/2+battleFont.getLineHeight());
            if(fMenu.getCurrentIcon().endsWith("*"))
            {
                battleFont.setColor(Color.RED);
            }
            else
            {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, fMenu.getCurrentIcon(), screenWidth/5,screenHeight/2);
            if(fMenu.getBelowIcon().endsWith("*"))
            {
                battleFont.setColor(Color.RED);
            }
            else
            {
                battleFont.setColor(Color.BLACK);
            }
            battleFont.draw(batch, fMenu.getBelowIcon(), screenWidth/5,screenHeight/2-battleFont.getLineHeight());

            battleFont.draw(batch, fMenu.getBelowIcon(), screenWidth/5,screenHeight/2-battleFont.getLineHeight());


        }

        battleFont.setColor(Color.BLACK);



        if(enemies.length>4)
        {
            battleFont.setScale(screenWidth / (enemies.length * 40f), screenHeight / (enemies.length * 40f));
        }
        for(int i=0;i<enemies.length;i++)
        {
            if(!enemies[i].getDead())
            {



                healthBar.setPosition((int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*((i*2)+1)));
                healthBar.setSize(enemies[i].getHealth()*((screenWidth/8)/enemies[i].getMaxHealth()),battleFont.getLineHeight()/2);
                healthBar.draw(batch);
                battleFont.draw(batch,enemies[i].getName()+": ",(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));

            }
        }
        if(targeting)
        {
            battleFont.setColor(Color.BLACK);
            battleFont.draw(batch,">",(int)(2.7*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(targetPicker.getCurrentTarget()*2)));
        }
        else if(combo.comboing)
        {
            combo.draw(batch);
        }

        batch.end();
    }

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
    public boolean tap(float x, float y, int count, int button)
    {
        if(waitingForTouch==true)
        {
            if(fMenu.actionSelected==false)
            {

                fMenu.touchDown((float)x/screenWidth,(float)y/screenHeight);
                if(fMenu.actionSelected)
                {
                    ActionId=fMenu.getActionId();
                    ActionType=fMenu.getActionType();
                    playerTurn(playr,enemies);
                }
            }

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

                if(targetPicker.targetSelected)
                {
                    targeting=false;
                    playerTurnPart2();
                }
            }
            else if(combo.comboing)
            {
                combo.tap((int)x,(int)y);
            }

        }

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



    private void playerTurn(Player player,Enemy[] monsters)
    {
        PlayerDam=0;

        if(ActionType==2)
        {

            combo.delete();
            gsm.endFight();
        }
        if(ActionType==3)
        {
            player.useItem(fMenu.getMoveString(ActionType,ActionId));


        }
        else if(ActionType==1)
        {

            targetPicker.reset(enemies);
            targeting=true;
            return;
        }
        playerTurnEnd();

    }

    private void playerTurnPart2()
    {

        combo.initiateCombo(0,this);
        comboing=true;
        return;
    }

    private void playerTurnPart3()
    {
        PlayerDam = playr.attackPicker(fMenu.getMoveString(ActionType, ActionId));
        PlayerDam*=(playr.getAttack()-enemies[targetPicker.getSelectedTarget()].getDefence());
        PlayerDam *= Math.abs(combo.skill);
        if(PlayerDam<1)
        {
            PlayerDam=1;
        }
        damage[targetPicker.getSelectedTarget()+1]+=PlayerDam;


        playerTurnEnd();
    }

    private void playerTurnEnd()
    {


        playr.changeEnergy(playr.getIntelligence());

        if(enemiesLeft>0&&playr.getCurrentHealth()>=0)
        {
            enemyTurn(playr, enemies);
        }
        else
        {
            combo.delete();
            gsm.endFight();
        }
        fMenu.refreshMenus(playr);
    }

    private void enemyTurn(Player player,Enemy[] monsters)
    {
        int dam;
        for(int i=0;i<monsters.length;i++)
        {
            if(!monsters[i].getDead()&&player.getCurrentHealth()>0)
            {
                dam=(monsters[i].getAttack() - player.getDefence());
          //      System.out.println("Monster " + i + " attacks");
                if (dam <= 0)
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
                        i = enemiesLeft;
                    }

                }

            }
        }
       // System.out.println("Player- Lvl: \t"+player.getLevel()+" \tExp:\t"+player.getExperience());
        //System.out.print("Hp: \t"+player.getCurrentHealth()+"/"+player.getHealth());
        //System.out.println("\tEn:\t "+player.getCurrentEnergy()+"/"+player.getEnergy());
        if(enemiesLeft>0)
        {
            waitingForTouch=true;
            fMenu.actionSelected=false;
        }
    }


}

