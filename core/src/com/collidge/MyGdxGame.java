package com.collidge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, ApplicationListener
{
    GameStateManager gsm;

    @Override
    public void create ()
    {
        gsm = new GameStateManager();
        Gdx.input.setInputProcessor(this);
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
}
