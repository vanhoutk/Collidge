package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create ()
    {
        setScreen(new Play());

	}

	@Override
	public void render ()
    {
	    super.render();
	}

    public void pause()
    {
        super.pause();
    }

    public void dispose()
    {
        super.dispose();
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public void resume()
    {
        super.resume();
    }

}
