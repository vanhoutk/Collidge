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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

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
    BitmapFont font;

    private static TextBox textBox;

    @Override
    public void create ()
    {


        Gdx.graphics.setVSync(true);

        font=new BitmapFont();
        font.setScale(Gdx.graphics.getWidth()/400,Gdx.graphics.getHeight()/400);

        //FIlE INPUT
        /**
         * Kris
         * Changed Dan's reading to instead read the created xml
         */
        int Level = 0, ATK = 0, DEF = 0, INT = 0, HP = 0, EN = 0, EXP = 0;

        if(Gdx.files.isLocalStorageAvailable() && Gdx.files.local("stats.xml").exists())
        {
            try
            {
                XmlReader reader = new XmlReader();
                FileHandle handle1 = Gdx.files.local("stats.xml");
                XmlReader.Element stats = reader.parse(handle1.readString());
                //XmlReader.Element stats = root.getChildByName("stats");
                Level = stats.getInt("Level");
                ATK = stats.getInt("AttackPoints");
                DEF = stats.getInt("DefencePoints");
                INT = stats.getInt("IntelligencePoints");
                HP = stats.getInt("HealthPoints");
                EN = stats.getInt("EnergyPoints");
                EXP = stats.getInt("Experience");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.println("Level " + Level + " Attack " + ATK);

        Gdx.input.setCatchBackKey(true);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        gsm = new GameStateManager(Level, ATK, DEF, INT, HP, EN, EXP);


        quitFont=new BitmapFont();
        quitFont.setScale(Gdx.graphics.getWidth()/300f,Gdx.graphics.getHeight()/300f);
        quitFont.setColor(Color.WHITE);
        texture=new Texture("blackSquare.png");
        screenMask=new Sprite(texture);
        screenMask.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        screenMask.setPosition(0f,0f);
        screenMask.setAlpha(.8f);
        batch=new SpriteBatch();

        textBox = new TextBox();
        textBox.setText(getNpcString("npc1", "two"));
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
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if(!backKey)
            {
                gsm.update();
            }
            gsm.draw();
            textBox.draw();
            batch.begin();
            font.draw(batch,"FPS: "+Gdx.graphics.getFramesPerSecond()+"",0,font.getLineHeight());

            if(backKey)
            {


                screenMask.draw(batch);
                if(gsm.currentState != 0)
                {
                    quitFont.drawWrapped(batch, "Do you want to exit to the main menu?", Gdx.graphics.getWidth() / 12, 9 * Gdx.graphics.getHeight() / 12, 10*Gdx.graphics.getWidth() / 12, BitmapFont.HAlignment.CENTER);
                }
                else
                {
                    quitFont.drawWrapped(batch, "Do you want to exit the game?", Gdx.graphics.getWidth() / 12, 9 * Gdx.graphics.getHeight() / 12, 10*Gdx.graphics.getWidth() / 12, BitmapFont.HAlignment.CENTER);
                }
                quitFont.draw(batch, "Yes, I hate fun", 1*Gdx.graphics.getWidth()/12,5*Gdx.graphics.getHeight()/12);
                quitFont.draw(batch, "Not just yet!", 8*Gdx.graphics.getWidth()/12,5*Gdx.graphics.getHeight()/12);

                batch.end();
            }
            else
            {
                batch.end();
            }
        }
    }

    public static TextBox getTextBox() {
        return textBox;
    }

    public static void setTextBox(String string) {
        textBox.setText(string);
    }

    public static String getNpcString(String npc, String convo) {
        try {
            XmlReader reader = new XmlReader();
            FileHandle handle1 = Gdx.files.internal("Strings.xml");
            XmlReader.Element root = reader.parse(handle1.readString());
            XmlReader.Element child = root.getChildByName(npc);
            String s = child.getChildByName(convo).getText();

            System.out.println(s);
            return s;
        }
        catch (Exception e) {
            return "";
        }
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


    //USELESS INPUT FOR KEYBOARD AND MOUSE THAT HAS TO BE IMPLEMENTED BUT WE OBVIOUSLY WONT USE

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
            if(backKey == true && backDown)
            {
                if(gsm.currentState == 0)
                {
                    quit = true;
                    return true;
                }
                else
                {
                    gsm.startOpenScreen();
                    return true;
                }
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
                    if(gsm.currentState == 0)
                    {
                        quit = true;
                        return true;
                    }
                    else
                    {
                        gsm.startOpenScreen();
                        backKey = false;
                        return true;
                    }
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
                    if(!textBox.pageDone()) textBox.finishPage();
                    else textBox.turnPage();
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
        if(textBox.isActive()) return true;
        return gsm.returnCurrentState().longPress(x,y);
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        if(backKey)
        {
            return true;
        }
        if(textBox.isActive()) return true;
        return gsm.returnCurrentState().fling(velocityX,velocityY,button);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        if(backKey)
        {
            return true;
        }
        if(textBox.isActive()) return true;
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
        if(textBox.isActive()) return true;
        return gsm.returnCurrentState().zoom(initialDistance,distance);
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        if(backKey)
        {
            return true;
        }
        if(textBox.isActive()) return true;
        return gsm.returnCurrentState().pinch(initialPointer1,initialPointer2,pointer1,pointer2);
    }
}
