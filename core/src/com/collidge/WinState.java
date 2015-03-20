package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by stroughm on 09/03/15.
 */
public class WinState extends GameState {

    Player player;
    SpriteBatch batch;
    Sprite textbox, ExpBorder, ExpBar, buttons, Background;
    double ExpEarned, buffer;
    int enemies;
    private BitmapFont winfont;
    float spacing = (screenWidth - 5 * screenWidth/7)/6;
    float sqSide = screenWidth/7;

    Timer.Task Exp=new Timer.Task()
    {
        @Override
        public void run()
        {
            if (ExpEarned > 0)
            {
                ExpEarned-=0.1;
                player.addExperience(0.1);
                /*buffer+=0.1;
                if(buffer>=1)
                {
                    buffer--;
                    player.addExperience(1);

                }*/
            }
        }
    };
    WinState(GameStateManager gsm, Player plr, int newExp, int enemyCount)
    {
        super(gsm);
        player = plr;
        batch = new SpriteBatch();
        ExpEarned = newExp;
        enemies = enemyCount;

        winfont = new BitmapFont();
        buttons = new Sprite(new Texture("inventory_slot_background.png"));
        buttons.setSize(sqSide, sqSide);
        textbox = new Sprite(new Texture("textbox_background_2.png"));
        ExpBorder = new Sprite (new Texture("Transparant_Button.png"));
        ExpBar = new Sprite (new Texture("statbar.png"));
        Background = new Sprite(new Texture("inventoryBackground.jpg"));

    }

    @Override
    public void initialize(){}

    //update will update all game logic before drawing
    @Override
    public  void update()
    {
        Timer.instance().clear();
        Timer.instance().start();
        Timer.instance().postTask(Exp);
        ExpBar.setSize(((float)player.getExperience()*screenWidth)/((float)player.getExpTarget()*2f), sqSide/2f);
        ExpBorder.setSize(screenWidth/2f, sqSide/2f);
    }


    //after updating has occurred in that single frame. Both what or what hasn't been changed has to be drawn/redrawn
    @Override
    public  void draw()
    {
        batch.begin();
        Gdx.gl.glClearColor(150/255f, 106/255f, 73/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Background.setSize(screenWidth, screenHeight);
        Background.setPosition(0, 0);
        Background.draw(batch);
        textbox.setSize(screenWidth-spacing*2f, 4f*screenHeight/8f);

        winfont.setColor(Color.GREEN);
        winfont.setScale(1f/400f*screenWidth, 1f/400f*screenHeight);
        winfont.draw(batch, "You Win!", 1f/2f * screenWidth, 1f/2f * screenHeight);

        textbox.setPosition(spacing, sqSide+spacing);
        textbox.draw(batch);
        textbox.setSize(screenWidth-(sqSide+spacing)*2f, sqSide);
        textbox.setPosition(sqSide+spacing, spacing/2f);
        textbox.draw(batch);

        ExpBar.setPosition(1f/4f*screenWidth, spacing+(sqSide/4f));
        ExpBar.draw(batch);
        ExpBorder.setPosition(1f/4f*screenWidth, spacing+(sqSide/4f));
        ExpBorder.draw(batch);
        buttons.setPosition(spacing,spacing/2f);
        buttons.draw(batch);
        buttons.setPosition(screenWidth-spacing-sqSide, spacing/2f);
        buttons.draw(batch);
        batch.end();
    }

    @Override
    public  boolean tap(float x, float y, int count, int button)
    {
        if (x < buttons.getWidth())
            if (y > screenHeight - buttons.getHeight()) {
                endWinState();
            }
        return false;
    }

    public void endWinState()
    {
        batch.dispose();
        winfont.dispose();
        if(player.getLevelUpCounter()<=0)
        {
            gsm.endFight();
        }
        else
        {
            gsm.levelUpState(player);
        }
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

