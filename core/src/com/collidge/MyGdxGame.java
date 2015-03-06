package com.collidge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
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

    private static TextBox textBox;

    @Override
    public void create ()
    {


        //FIlE INPUT
        int i=0;

        int[] values=new int[7];


        if(Gdx.files.isLocalStorageAvailable()&&Gdx.files.local("data/save.txt").exists())
        {
            InputStream in = Gdx.files.local("data/save.txt").read();
            int temp = 0;
            InputStreamReader is = new InputStreamReader(in);
            while (i < 7)
            {
                try
                {
                    if (temp == -4)//checks if equal to a comma
                    {
                        temp = in.read() - 48;
                    }

                    while (temp != -4)
                    {
                        if (values[i] > 0)
                        {
                            values[i] *= 10;
                        }
                        values[i] += temp;
                        temp = in.read() - 48;

                        if(temp==-49)
                        {
                            //if you reach the end of line character, end the loop
                            temp=-1;
                            while(i<values.length)
                            {
                                values[i]=0;
                                i++;
                            }
                            i=500;
                        }
                    }


                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                i++;

            }
            for (i = 0; i < 6; i++)
            {
                System.out.println(i + ": " + values[i]);
            }
        }
        else
        {
            values[0]=1;
            for(i=1;i<values.length;i++)
            {
                values[i]=0;
            }
        }
        //FILE INPUT END

        Gdx.input.setCatchBackKey(true);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        gsm = new GameStateManager(values[0],values[1],values[2],values[3],values[4],values[5],values[6]);


        quitFont=new BitmapFont();
        quitFont.setScale(Gdx.graphics.getWidth()/300f,Gdx.graphics.getHeight()/300f);
        quitFont.setColor(Color.WHITE);
        texture=new Texture("blackSquare.png");
        screenMask=new Sprite(texture);
        screenMask.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        screenMask.setPosition(0f,0f);
        screenMask.setAlpha(.8f);
        batch=new SpriteBatch();

        /**
         * Kris - commented out two of the lines which caused the app to crash on mobile.
         */
        if(Gdx.files.local("data/RandomText1.txt").exists())
        {
            FileHandle handle = Gdx.files.local("data/RandomText1.txt");
        }
        else
        {
            System.out.println("XXXXXXXXXXXXXXXXXXXX");
        }
        //String text = handle.readString();
        textBox = new TextBox();
        //textBox.setText(text);
        textBox.setText("Text string");
    }

    @Override
    public void render ()
    {
        if(quit)
        {
            save();

            Gdx.app.exit();
        }
        else
        {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            if(!backKey)
            {
                gsm.update();
            }
            gsm.draw();
            textBox.draw();
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

    public void save()
    {
        //Writes to save, we can add extra int values to the end of the string to store the values of other things (such as what stage in the game we are at, or items, etc. Equipped items might be trickier to hang onto, but im sure we can figure it out
        if(Gdx.files.isLocalStorageAvailable())
        {
            OutputStream out=Gdx.files.local( "data/save.txt" ).write(false);
            try
            {
                String saveVals;
                saveVals=gsm.user.getLevel()+","+gsm.user.getAttackPoints()+","+gsm.user.getDefencePoints()+","+gsm.user.getIntelligencePoints()+","+gsm.user.getHealthPoints()
                        +","+gsm.user.getEnergyPoints()+","+gsm.user.getExperience()+",";

                out.write(saveVals.getBytes());
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
    }

    public static TextBox getTextBox() {
        return textBox;
    }

    public static void setTextBox(String string) {
        textBox.setText(string);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(backKey)
        {
            return true;
        }
        if(textBox.isActive()) {
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
        if(textBox.isActive()) {
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
        if(keycode == Input.Keys.BACK||keycode==Input.Keys.ESCAPE)
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

        if(keycode == Input.Keys.BACK||keycode==Input.Keys.ESCAPE)
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
        if(textBox.isActive()) {
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
        if(textBox.isActive()) {
            y = Gdx.graphics.getHeight() - y;
            if(x < textBox.getX() + textBox.getTextBoxWidth() && x > textBox.getX()) {
                if(y > textBox.getY() && y < textBox.getY() + textBox.getTextBoxHeight()) {
                    textBox.turnPage();
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
