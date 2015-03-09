package com.collidge;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by Ben on 24-Jan-15.
 *
 * Object containing all other states. 2 functions which will call the currentStates update and draw functions. This is for convenience and makes it easy to split the project.
 * Also through reference the gameState itself (which contains the object GameStateManager) can change the currentState to any other state at any time.
 */


public class GameStateManager
{
    //ArrayList (dynamic list) which holds every state
    private ArrayList<GameState> gameStates;
    //There can only be one current state
    public int currentState;
    public Player user;
    float x = 8, y = 9;



    //ENUMS FOR HANDYNESS. So you can do "currentState = MENUSTATE;" instead of "currentState = 0;" and not know what state you are in.

   /* public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int FIGHTSTATE =2;*/


    //Constructor creates all states and adds them to the list. Sets current state and loads it.
    public GameStateManager()
    {
        gameStates = new ArrayList<GameState>();
        //use of polymorphoism. Arraylist contains "GameState"s but is full of objects which inherit off GameState.
        GameState state1 = new Play(this, x, y);
       // GameState state2 = new TestState2(this);
        gameStates.add(state1);

        //currentState = MENUSTATE;

        currentState = 0;
        gameStates.get(currentState).initialize();


    }

    public GameStateManager(int Level, int ATK,int DEF, int INT,int HP,int EN,int EXP)
    {


        user=new Player(Level, ATK,DEF, INT,HP, EN,EXP);
        gameStates = new ArrayList<GameState>();
        //use of polymorphoism. Arraylist contains "GameState"s but is full of objects which inherit off GameState.
        GameState state1 = new Play(this, x, y);
        // GameState state2 = new TestState2(this);
        gameStates.add(state1);

        //currentState = MENUSTATE;

        currentState = 0;
        gameStates.get(currentState).initialize();


    }

    public void setUpPlayer(int Level, int ATK,int DEF, int INT,int HP,int EN,int EXP)
    {
        user=new Player(Level, ATK,DEF, INT,HP, EN,EXP);
        gameStates.get(currentState).initialize();
    }

    //change state to any other state. Alternatively we could make a new object each time instead of keeping them in memory.
    public void changeState(int state)
    {
        if (state >= gameStates.size())
        {
            state = 0;
        }
        gameStates.get(currentState).dispose();
        currentState = state;
        gameStates.get(currentState).initialize();
//        if(state == MENUSTATE)
//            gameStates[state] = new MenuState(this);
    }

    //self explanatory. Returns reference to current state.
    public GameState returnCurrentState() {
        return gameStates.get(currentState);
    }

    //Kris -- adding in for testing of Inventory State
    public void openInventory(Player player, float px, float py)
    {
        x = px;
        y = py;
        gameStates.add(new InventoryState(this, player, x, y));
        changeState(gameStates.size()-1);
    }

    public void closeInventory(float px, float py)
    {
        x = px;
        y = py;
        changeState(0);
        gameStates.get(gameStates.size()-1).dispose();
        gameStates.remove(gameStates.size()-1);
    }

    public void startFight(Player player)
    {

        gameStates.add(new Fight(this,player));
        changeState(gameStates.size()-1);
    }

    public void startFight(Player player,String Enemy)
    {
        gameStates.add(new Fight(this,player,Enemy));
        changeState(gameStates.size()-1);
    }

    public void endFight()
    {

        //TODO add previous state class stuff
        changeState(0);
        gameStates.get(gameStates.size()-1).dispose();
        gameStates.remove(gameStates.size()-1);


    }

    public void openMenu(Player player)
    {
        gameStates.add(new GameMenu(this, player));
        changeState(gameStates.size()-1);
    }

    public void closeMenu()
    {
        changeState(0);
        gameStates.get(gameStates.size()-1).dispose();
        gameStates.remove(gameStates.size()-1);
    }




    public void levelUpState(Player player)
    {
        gameStates.get(gameStates.size()-1).dispose();
        gameStates.remove(gameStates.size()-1);
        gameStates.add(new LevelUpSplash(this,player));
        changeState(gameStates.size()-1);
    }
    //Two functions below are the most important functions. These get called in the main loop each frame by calling the currentState's individual update/draw functions.
    public void update()
    {
        if(gameStates.get(currentState)!=null)
        {
            gameStates.get(currentState).update();
        }
    }

    public void draw()
    {
        gameStates.get(currentState).draw();
    }
}