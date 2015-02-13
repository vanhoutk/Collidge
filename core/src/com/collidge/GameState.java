package com.collidge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ben on 24-Jan-15.
 *
 * Abstract gameState which all other gameStates inherit off. Abstract meaning that it can't be made into an object and that states that inherit off it must implement every function.
 * Has a reference to the gameStateManager so it can swap to any other state at any time using the gsm's public functions.
 */
public abstract class GameState extends ApplicationAdapter
{
    //reference to the gsm so can change to any other at any time. if (anything) gsm.changeState(state);
    protected GameStateManager gsm;
    int screenWidth, screenHeight;

    //constructor assigns gsm to arguments gsm so every gameState has same gsm.
    GameState(GameStateManager gsm)
    {
        this.gsm = gsm;
        screenWidth= Gdx.graphics.getWidth();
        screenHeight= Gdx.graphics.getHeight();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //6 FUNCTIONS THAT EVERY GAMESTATE MUST IMPLEMENT. EVEN IF COMPLETELY EMPTY!
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //initialize is the same as constructor but sometimes you want to reset an object without rebuilding it.
    public abstract void initialize();
    //update will update all game logic before drawing
    public abstract void update();
    //after updating has occured in that single frame. Both what or what hasn't been changed has to be drawn/redrawn
    public abstract void draw();

    //3 touch events that you can handle inside your own class
    //first touchDown(int,int,pointer,button) is from the input handler, the second uses floats and is in the gesture listener(possibly more accurate than ints)
    //Use whichever you want, but only use one, and return false on the other
    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);
    public abstract boolean touchDown(float x,float y, int pointer, int button);
    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);
    public abstract boolean touchDragged(int screenX, int screenY, int pointer);
    public abstract boolean tap(float x, float y, int count, int button);
    public abstract boolean longPress(float x, float y);


    public abstract boolean fling(float velocityX, float velocityY, int button);



    public abstract boolean pan(float x, float y, float deltaX, float deltaY);


    public abstract boolean panStop(float x, float y, int pointer, int button);


    public abstract boolean zoom(float initialDistance, float distance);
    public abstract boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2);
}
