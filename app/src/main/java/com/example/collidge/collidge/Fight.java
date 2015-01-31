package com.example.collidge.collidge;

import android.view.MotionEvent;

/**
 * Created by Daniel on 20/01/2015.
 */
public class Fight
{

    Player playr;
    boolean waitingForTouch=false;
    int action;
    private int ActionType;
    private int ActionId;
    private boolean moveSelection=false;

    private FightMenu fMenu;
    private int enemyCount,enemiesLeft;
    private Enemy[] enemies;
    Attack move;
    Fight()
    {

        EnemyTypes BasicSet=new EnemyTypes();
        enemyCount=5;
        enemiesLeft=enemyCount;
        move=new Attack();
        //TODO add randomness and enemy lookup from world sprites
        enemies=new Enemy[enemyCount];
        enemies[0]=new Enemy(BasicSet.getEnemy("Fresher"));
        enemies[1]=new Enemy(BasicSet.getEnemy("Fresher"));
        enemies[2]=new Enemy(BasicSet.getEnemy("Fresher"));
        enemies[3]=new Enemy(BasicSet.getEnemy("Master Debater"));
        enemies[4]=new Enemy(BasicSet.getEnemy("\"Musician\""));


    }

    public void init()
    {

    }

    public void update()
    {
       /* if (insideMenuBool)
        {
            menuInput();

        }
        else if (comboTurnBool)
        {
            comboTurn();
        }
        else if (enemyTurnBool)
        {
            enemyTurn();
        }*/
    }

    public void render()
    {

    }

    public void start(Player player,String[] items)
    {
        fMenu=new FightMenu(player);
        playr=player;
        waitingForTouch=true;
    }
    private void turn(Player player)
    {
        playerTurn(player,enemies);

    }
    private void playerTurn(Player player,Enemy[] monsters)
    {
        int damage=0;

        if(ActionType==3)
        {
            player.useItem(fMenu.getMoveString(ActionType,ActionId));


        }
        else if(ActionType==1)
        {
            damage = player.attackPicker(fMenu.getMoveString(ActionType, ActionId));


            int target;
            target = move.getTarget(damage, monsters);
            //TODO add draw orders
            damage *= move.moveExecute(damage);
            //TODO add lookup for energy cost of each move
            if (target >= 0)
            {
                if ((damage * (player.getAttack() - monsters[target].getDefence())) <= 0)
                {
                    System.out.println("Damage on monster " + target + " resisted");
                } else
                {
                    //damage= move damage*(playerStrength-enemyDefence)
                    monsters[target].changeHealth(-(damage * (player.getAttack() - monsters[target].getDefence())));
                    System.out.println((damage * (player.getAttack() - monsters[target].getDefence()))+" damage done");
                    if (monsters[target].getDead())
                    {
                        player.addExperience(monsters[target].getExpValue());
                        enemiesLeft -= 1;
                    }
                }
            }
        }

        for (int i = 0; i < enemyCount; i++)
        {

            if (!monsters[i].getDead())
            {
                System.out.print(monsters[i].getName() + ": ");
                System.out.println(monsters[i].getHealth());
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



        int damage;
        for(int i=0;i<monsters.length;i++)
        {
            if(!monsters[i].getDead()&&player.getCurrentHealth()>0)
            {
                damage=(monsters[i].getAttack() - player.getDefence());
                System.out.println("Monster " + i + " attacks");
                if (damage <= 0)
                {
                    System.out.println("Damage by monster " + i + " resisted");
                    player.changeHealth(-1);
                } else
                {
                    //damage= move damage*(playerStrength-enemyDefence)
                    player.changeHealth(-(damage));
                    System.out.println("Enemy " + i + " deals " + (damage) + " damage");
                    if (player.getCurrentHealth() <= 0)
                    {
                        enemiesLeft = -1;
                        System.out.println("you lose");
                        i = enemiesLeft;
                    }

                }

            }
        }
        System.out.println("Player- Lvl: \t"+player.getLevel()+" \tExp:\t"+player.getExperience());
        System.out.print("Hp: \t"+player.getCurrentHealth()+"/"+player.getHealth());
        System.out.println("\tEn:\t "+player.getCurrentEnergy()+"/"+player.getEnergy());
        if(enemiesLeft>0)
        {
            waitingForTouch=true;
            fMenu.actionSelected=false;
        }
    }

    public void touchEvent(float x,float y, MotionEvent touch)
    {
        if(waitingForTouch)
        {
            if(!fMenu.actionSelected)
            {
                fMenu.touchEvent(x,y,touch);
                if(fMenu.actionSelected)
                {
                    waitingForTouch = false;
                    moveSelect();

                }
            }

        }

        /*if(waitingForTouch)
        {

            waitingForTouch=false;
            if(touch.getAction()==MotionEvent.ACTION_DOWN)//motion down
            {
                if(x<0&&y<0)
                {
                    //moveSelection=false;
                    action=0;
                    System.out.println("Attack");
                    if(enemiesLeft>0&&playr.getCurrentHealth()>0)
                    {
                        playerTurn(playr,enemies);
                    }

                }
                else if(x<0&&y>0)
                {
                    action=0;
                    System.out.println("Items");
                    waitingForTouch=true;
                }
                else if(x>0&&y<0)
                {
                    action=0;
                    System.out.println("Defend");
                    waitingForTouch=true;
                }
                else if(x>0&&y>0)
                {
                    System.out.println("Run");
                    waitingForTouch=true;
                }
            }
        }*/
    }
}
