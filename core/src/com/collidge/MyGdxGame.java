package com.collidge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, ApplicationListener,GestureDetector.GestureListener
{
    GameStateManager gsm;

    @Override
    public void create ()
    {


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        gsm = new GameStateManager();
       // Gdx.input.setInputProcessor(this);
        //Gdx.input.setInputProcessor(new GestureDetector(this));
        //setScreen(new Play());
    }

    @Override
    public void render ()
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update();
        gsm.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return gsm.returnCurrentState().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return gsm.returnCurrentState().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return gsm.returnCurrentState().touchDragged(screenX, screenY, pointer);
    }

    @Override
    public void pause()
    {
       // super.pause();
    }

    @Override
    public void dispose()
    {
      //  super.dispose();
    }

    @Override
    public void resize(int width, int height)
    {
       // super.resize(width, height);
    }

    @Override
    public void resume()
    {
       // super.resume();
    }

    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    //USELESS INPUT FOR KEYBOARD AND MOUSE THAT HAS TO BE IMPLEMENTED BUT WE OBVIOUSLY WONT USE
    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        // gsm.get
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        return gsm.returnCurrentState().touchDown(x,y, pointer,button);
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
       return gsm.returnCurrentState().tap(x,y,count,button);
    }

    @Override
    public boolean longPress(float x, float y)
    {
        return gsm.returnCurrentState().longPress(x,y);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {

        return gsm.returnCurrentState().fling(velocityX,velocityY,button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {

        return gsm.returnCurrentState().pan(x,y,deltaX,deltaY);
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {

        return gsm.returnCurrentState().panStop(x,y,pointer,button);
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        return gsm.returnCurrentState().zoom(initialDistance,distance);
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return gsm.returnCurrentState().pinch(initialPointer1,initialPointer2,pointer1,pointer2);
    }
}
