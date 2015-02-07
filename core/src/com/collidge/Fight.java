package com.collidge;

//import android.view.MotionEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;


/**
 * Created by Daniel on 20/01/2015.
 */
public class Fight extends GameState
{



    Player playr;
    boolean waitingForTouch=false;
    int action;
    private int damage;
    private int ActionType;
    private int ActionId;
    private boolean moveSelection=false;

    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;


    SpriteBatch batch;
    Texture texture ;
    Sprite healthBar;



    private BitmapFont battleFont;




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






        batch=new SpriteBatch();
        texture =new Texture("barHorizontal_green_mid.png");
        healthBar=new Sprite(texture);

        healthBar.setPosition(screenWidth/30+(screenWidth/50),25*screenHeight/30);
        healthBar.setSize((4*(screenWidth/10)),screenHeight/10);

        //TODO add someway to input a player rather than create a new one (maybe in the gsm)

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
        healthBar.draw(batch);


        battleFont.setColor(Color.BLACK);
        battleFont.setScale(screenWidth/200.0f,screenHeight/200.0f);
        battleFont.draw(batch, playr.getCurrentHealth()+" Hp", screenWidth/5,9*screenHeight/10);
        battleFont.draw(batch, playr.getCurrentEnergy()+" En", screenWidth/5,(9*screenHeight/10)-battleFont.getLineHeight());

        if(!fMenu.actionSelected)
        {


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

                battleFont.draw(batch,enemies[i].getName()+": ",(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*(i*2)));
                battleFont.draw(batch,enemies[i].getHealth()+"",(int)(3.0*screenWidth/5),screenHeight-(battleFont.getLineHeight()*((i*2)+1)));

            }
        }

        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if(waitingForTouch==true)
        {
            if(fMenu.actionSelected==false)
            {

                fMenu.touchDown((float)screenX/screenWidth,(float)screenY/screenHeight);
                if(fMenu.actionSelected)
                {
                    ActionId=fMenu.getActionId();
                    ActionType=fMenu.getActionType();
                    playerTurn(playr,enemies);
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
        int dam=0;

        if(ActionType==3)
        {
            player.useItem(fMenu.getMoveString(ActionType,ActionId));


        }
        else if(ActionType==1)
        {
            dam = player.attackPicker(fMenu.getMoveString(ActionType, ActionId));
            int target;
            target = move.getTarget(dam, monsters);
            dam *= move.moveExecute(dam);

            if (target >= 0)
            {
                if ((dam * (player.getAttack() - monsters[target].getDefence())) <= 0)
                {
                   // System.out.println("Damage on monster " + target + " resisted");
                    monsters[target].changeHealth(-1);
                } else
                {
                    //damage= move damage*(playerStrength-enemyDefence)
                    monsters[target].changeHealth(-(dam * (player.getAttack() - monsters[target].getDefence())));
                   // System.out.println((dam * (player.getAttack() - monsters[target].getDefence()))+" damage done");
                    if (monsters[target].getDead())
                    {
                        player.addExperience(monsters[target].getExpValue());
                        enemiesLeft -= 1;
                        if(enemiesLeft<=0)
                        {
                            gsm.endFight();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < enemyCount; i++)
        {

            if (!monsters[i].getDead())
            {
               // System.out.print(monsters[i].getName() + ": ");
               // System.out.println(monsters[i].getHealth());
            }

        }

        player.changeEnergy(player.getIntelligence());

        if(enemiesLeft>0&&playr.getCurrentHealth()>=0)
        {
            enemyTurn(player, enemies);
        }
        fMenu.refreshMenus(player);

    }
    private void moveSelect()
    {
        ActionType=fMenu.getActionType();
        ActionId= fMenu.getActionId();
        fMenu.actionSelected=true;
        playerTurn(playr,enemies);
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


    private void dealDamage(int target, int damage)
    {
        if(target<0)
        {
            playr.changeHealth(-1);

            if(playr.getCurrentHealth()<=0)
            {
                return;
            }
        }
        else if (target>=0)
        {
            if(enemies[target].getHealth()!=0)
            {
                enemies[target].changeHealth(-1);
            }
            else return;

        }
        damage=damage-1;

        /*try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/

        if(damage>0)
        {
            dealDamage(target, damage);
        }
        return;
    }








}

