package com.example.collidge.collidge;

/**
 * Created by Ben on 24-Jan-15.
 *
 * Abstract gameState which all other gameStates inherit off. Each gameState must then extend this class.
 * Has a reference to the gameStateManager so it can swap to any other state at any time.
 */
public abstract class GameState
{
    protected GameStateManager gsm;

    public abstract void initialize();
    public abstract void update();
    public abstract void render();
}
