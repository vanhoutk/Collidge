package com.example.kris.menu;

import java.util.ArrayList;

/**
 * Created by Ben on 24-Jan-15.
 *
 * Object containing all other states with function to update and render whatever state is the current state. This is the most important part.
 * Also through reference the gameState itself or the GSM can change the currentState to any other state at any time.
 */
public class GameStateManager
{
    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int NUMGAMESTATES = 2;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    public GameStateManager()
    {
        gameStates = new ArrayList<GameState>();

        currentState = MENUSTATE;
        loadState(currentState);
    }

    private void loadState(int state)
    {
//        if(state == MENUSTATE)
//            gameStates[state] = new MenuState(this);
    }

    public void setState(int state) {
        currentState = state;
        loadState(currentState);
    }

    public void update()
    {
        try
        {
//           gameStates[currentState].update();
        } catch(Exception e) {}
    }

    public void render()
    {
        try
        {
//           gameStates[currentState].render();
        } catch(Exception e) {}
    }
}
