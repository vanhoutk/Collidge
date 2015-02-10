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
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Daniel on 20/01/2015.
 */

/**
 * Small Edit by Michael on 07/02/2015
 */
public class Fight extends GameState
{



    private int PlayerDam;

    Player playr;
    boolean waitingForTouch=false;
    int action;
    private int damage;
    private int ActionType;
    private int ActionId;
    private boolean moveSelection=false;
    private boolean targeting=false;

    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;


    SpriteBatch batch;
    Texture texture,textureMenu ;
    Sprite healthBar;
    Sprite menuContainer;
    Sprite menubutton;


    private BitmapFont battleFont;
    /**
     * Added by Michael 07/2/15
     */
    private Animation[] icon;
    private Animation background;
    private float elapsedTime = 0;
    /**
     * end of Michael 07/2/15
     */
    private TargetPicker targetPicker;

    Fight(GameStateManager gsm,Player player)
    {
        super(gsm);
        playr=player;
    }


    @Override
    public void initialize()
    {


        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {

                if(damage>0)
                {
                    damage--;
                    playr.changeHealth(-1);
                    if (playr.getCurrentHealth() <= 0)
                    {
                        gsm.endFight();
                        damage=0;
                        cancel();


                    }

                }



            }
        }
                , 0, .1f);

        EnemySets BasicSet=new EnemySets();
        enemies=BasicSet.getEnemies("Pack");

        enemyCount=enemies.length;
        enemiesLeft=enemyCount;
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


        /**Michael*/

        TextureAtlas spriteatlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));

        TextureAtlas backgroundatlas = new TextureAtlas(Gdx.files.internal("sprite.atlas"));

        //List<Animation> icon = new ArrayList<Animation>(enemies.length);
        icon = new Animation[enemies.length];
        for (int i =0;i<enemies.length;i++)
            icon[i] = new Animation(1/15f, spriteatlas.getRegions());
        background = new Animation(1/2f, backgroundatlas.getRegions());

        /**End_Michael*/

        fMenu=new FightMenu(playr);

        waitingForTouch=true;
        battleFont = new BitmapFont();
    }


    public void update()
    {
//(int)(((double)(4*(screenWidth/10)))*((double)playr.getCurrentEnergy()/playr.getHealth()))


        if(gsm.returnCurrentState()!=this)
        {
            Timer.instance().clear();
            Timer.instance().stop();
        }
        healthBar.setSize((int)((playr.getCurrentHealth()*(4*(screenWidth/10)))/((double)playr.getHealth())),(int)(screenHeight/20.0));



    }

    @Override
    public void draw()
    {
        batch.begin();

        /**Michael*/
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(background.getKeyFrame(elapsedTime, true), 0, 0);
        for (int i = 0; i<enemies.length;i++)
            if (!enemies[i].getDead())
                batch.draw(icon[i].getKeyFrame(elapsedTime, true), ((9*screenWidth)/10),(screenHeight * (i / (float) enemies.length))+(screenHeight/(float)(enemies.length*enemies.length)));
        /**End_Michael*/

        healthBar.setPosition(screenWidth/30+(screenWidth/50),25*screenHeight/30);
        healthBar.draw(batch);

        battleFont.setColor(Color.WHITE);
        battleFont.setScale(screenWidth/250.0f,screenHeight/250.0f);
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

            menuContainer.setSize(screenWidth/5f,screenHeight/5f);
            menuContainer.setPosition((2.5f/5f)*screenWidth,(4f/6f)*screenHeight);
            menuContainer.draw(batch);

            menuContainer.setPosition((2.5f/5f)*screenWidth,(0.5f/6f)*screenHeight);
            menuContainer.draw(batch);



            if(fMenu.getAboveIcon().endsWith("*"))
            {
                battleFont.setColor(Color.RED);
            }
            else
            {
                battleFont.setColor(Color.WHITE);
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
                battleFont.setColor(Color.WHITE);
            }
            battleFont.draw(batch, fMenu.getBelowIcon(), screenWidth/5,screenHeight/2-battleFont.getLineHeight());

            battleFont.draw(batch, fMenu.getBelowIcon(), screenWidth/5,screenHeight/2-battleFont.getLineHeight());
        }
        battleFont.setColor(Color.WHITE);




        if(enemies.length>4)
        {
            battleFont.setScale(screenWidth / (enemies.length * 60f), screenHeight / (enemies.length * 60f));
        }
        for(int i=0;i<enemies.length;i++)
        {
            if(!enemies[i].getDead())
            {
                healthBar.setPosition((4*screenWidth/5),(screenHeight * (i / (float) enemies.length))+(screenHeight/(float)(enemies.length*enemies.length)));
                healthBar.setSize(enemies[i].getHealth()*((screenWidth/8)/enemies[i].getMaxHealth()),battleFont.getLineHeight()/2);
                healthBar.draw(batch);
                battleFont.draw(batch,enemies[i].getName()+": ",(4*screenWidth/5),(screenHeight * (i / (float) enemies.length))+(screenHeight/(float)(enemies.length*enemies.length)));
            }
            else
            {
                battleFont.setScale(screenWidth / (enemies.length * 80f), screenHeight / (enemies.length * 80f));
                battleFont.draw(batch, "ELIMINATED", (3.5f * screenWidth / 5), (screenHeight * (i / (float) enemies.length)) + (screenHeight / (float) (enemies.length * enemies.length)));
            }
        }

        if(targeting)
        {
            battleFont.setColor(Color.WHITE);
            battleFont.draw(batch,">",(int)(2.7*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(targetPicker.getCurrentTarget()*2)));
        }


        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if(waitingForTouch)
        {
            if(!fMenu.actionSelected)
            {

                fMenu.touchDown((float)screenX/screenWidth,(float)screenY/screenHeight);
                if(fMenu.actionSelected)
                {
                    ActionId=fMenu.getActionId();
                    ActionType=fMenu.getActionType();
                    playerTurn(playr,enemies);
                }
            }

            else if(targeting)
            {

                if(screenX<screenWidth/3)
                {
                    targetPicker.Left();

                }
                else if(screenX>2*(screenWidth/3))
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

        }

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



    public void start(Player player,String[] items)
    {

    }
    private void turn(Player player)
    {
        playerTurn(player,enemies);

    }
    private void playerTurn(Player player,Enemy[] monsters)
    {
        PlayerDam=0;

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
        int target;
        target = targetPicker.getSelectedTarget();
        PlayerDam = playr.attackPicker(fMenu.getMoveString(ActionType, ActionId));
        //PlayerDam *= move.moveExecute(PlayerDam);
        enemies[target].changeHealth(-(PlayerDam*(playr.getAttack()-enemies[target].getDefence())));
        if(enemies[target].getDead())
        {
            enemiesLeft--;
        }
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
                    damage++;
                } else
                {
                    damage+=dam;
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

