package com.collidge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, ApplicationListener,GestureDetector.GestureListener
{
    GameStateManager gsm;

    private boolean backKey=false;
    private boolean backDown=false;
    private boolean quit=false;
    private BitmapFont quitFont;
    private Sprite screenMask;
    private Texture texture;
    private SpriteBatch batch;



    @Override
    public void create ()
    {



        /*if(Gdx.files.isLocalStorageAvailable())
        {
            OutputStream out=Gdx.files.local( "data/save.txt" ).write(false);
            try
            {
                out.write("test".getBytes());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    out.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        InputStream in=Gdx.files.local( "data/save.txt" ).read();
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = null;
        try
        {
            read = br.readLine();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        while(read != null) {
            //System.out.println(read);
            sb.append(read);
            try
            {
                read =br.readLine();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        System.out.println(sb.toString());
*/

        Gdx.input.setCatchBackKey(true);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        gsm = new GameStateManager();
        quitFont=new BitmapFont();
        quitFont.setScale(Gdx.graphics.getWidth()/300f,Gdx.graphics.getHeight()/300f);
        quitFont.setColor(Color.WHITE);
        texture=new Texture("blackSquare.png");
        screenMask=new Sprite(texture);
        screenMask.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        screenMask.setPosition(0f,0f);
        screenMask.setAlpha(.8f);
        batch=new SpriteBatch();
       // Gdx.input.setInputProcessor(this);
        //Gdx.input.setInputProcessor(new GestureDetector(this));
        //setScreen(new Play());
    }

    @Override
    public void render ()
    {
        if(quit)
        {


            Gdx.app.exit();
        }
        else
        {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            if(!backKey)
            {
                gsm.update();
            }
            gsm.draw();
            if(backKey)
            {

                batch.begin();
                screenMask.draw(batch);
                quitFont.draw(batch, "Do you want to Quit?", 4*Gdx.graphics.getWidth()/12,9*Gdx.graphics.getHeight()/12);
                quitFont.draw(batch, "Yes, I hate fun", 1*Gdx.graphics.getWidth()/12,5*Gdx.graphics.getHeight()/12);
                quitFont.draw(batch, "Not just yet!", 8*Gdx.graphics.getWidth()/12,5*Gdx.graphics.getHeight()/12);
                batch.end();
            }
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return gsm.returnCurrentState().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(backKey)
        {
            return true;
        }
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
        if(keycode == Input.Keys.BACK)
        {
            if(backKey==true&&backDown)
            {
                quit=true;
                return true;
            }
            backDown=true;


        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {

        if(keycode == Input.Keys.BACK)
        {
            if(backKey==true)
            {
                return true;
            }

            backKey=true;


        }
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
        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().touchDown(x,y, pointer,button);
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        if(backKey)
        {
            if(y<.65*Gdx.graphics.getHeight()&&y>.45*Gdx.graphics.getHeight())
            {
                if (x > Gdx.graphics.getWidth() / 10 && x < Gdx.graphics.getWidth()*.375)
                {
                    quit = true;
                    backKey=false;

                } else if (x > 2*Gdx.graphics.getWidth()/3 && x < .9*Gdx.graphics.getWidth())
                {
                    backKey = false;
                    backDown=false;


                }
            }
            return true;
        }


        return gsm.returnCurrentState().tap(x,y,count,button);
    }

    @Override
    public boolean longPress(float x, float y)
    {
        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().longPress(x,y);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {

        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().fling(velocityX,velocityY,button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {

        if(backKey)
        {
            return true;
        }
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
        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().zoom(initialDistance,distance);
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        if(backKey)
        {
            return true;
        }
        return gsm.returnCurrentState().pinch(initialPointer1,initialPointer2,pointer1,pointer2);
    }
}
