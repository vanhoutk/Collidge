package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by stroughm on 09/03/15.
 */
public class WinState extends GameState {

    Player player;
    SpriteBatch batch;
    String Wintext[];
    Sprite textbox;
    int ExpEarned;
    private BitmapFont winfont;
    WinState(GameStateManager gsm, Player plr, int newExp)
    {
        super(gsm);
        player = plr;
        batch = new SpriteBatch();
        ExpEarned = newExp;


        winfont = new BitmapFont();

        textbox = new Sprite(new Texture("textbox_background_2.png"));

    }

    @Override
    public void initialize(){}

    //update will update all game logic before drawing
    @Override
    public  void update()
    {

    }


    //after updating has occurred in that single frame. Both what or what hasn't been changed has to be drawn/redrawn
    @Override
    public  void draw()
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public  boolean tap(float x, float y, int count, int button)
    {
        return false;
    }

    public void endWinState()
    {

    }


    //3 touch events that you can handle inside your own class
    //first touchDown(int,int,pointer,button) is from the input handler, the second uses floats and is in the gesture listener(possibly more accurate than ints)
    //Use whichever you want, but only use one, and return false on the other

    @Override
    public  boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public  boolean touchDown(float x,float y, int pointer, int button)
    {
        return false;
    }

    @Override
    public  boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public  boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public  boolean longPress(float x, float y)
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
}

