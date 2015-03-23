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
 * Created by Michael on 09/03/15.
 */
public class WinState extends GameState {

    Player player;
    SpriteBatch batch;
    Sprite textbox, ExpBorder, ExpBar, buttons, Background, continue_button;
    double ExpEarned;
    int exp_const;
    int enemies;
    private BitmapFont winfont;
    float spacing = (screenWidth - 5 * screenWidth/7)/6;
    float sqSide = screenWidth/7;
    float i;
    int damage;
    int[] ratings;
    String Rating;
    int page_number;

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
    WinState(GameStateManager gsm, Player plr, int newExp, Enemy[] enemy, int damage_taken, int[] num_ratings)
    {
        super(gsm);
        player = plr;
        batch = new SpriteBatch();
        ExpEarned = newExp;
        exp_const = newExp;
        enemies = enemy.length;
        damage = damage_taken;
        ratings = num_ratings;
        page_number = 0;

        winfont = new BitmapFont();
        buttons = new Sprite(new Texture("textbox_background_2.png"));
        buttons.setSize(sqSide, sqSide);
        textbox = new Sprite(new Texture("textbox_background_2.png"));
        ExpBorder = new Sprite (new Texture("Transparant_Button.png"));
        ExpBar = new Sprite (new Texture("statbar.png"));
        Background = new Sprite(new Texture("inventoryBackground3.jpg"));
        continue_button = new Sprite(new Texture("back_button.png"));
        continue_button.flip(true,false);
        if      (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) == 1)
            Rating = "S";
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.6)
            Rating = "A";
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.4)
            Rating = "B";
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.2)
            Rating = "C";
        else
            Rating = "D";


        continue_button.setSize(buttons.getWidth(), buttons.getHeight());
        continue_button.setPosition(screenWidth-(spacing+sqSide), spacing/2f);
        Background.setSize(screenWidth, screenHeight);
        Background.setPosition(0, 0);
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
        ExpBar.setSize(((float)player.getTrueExperience()*screenWidth)/((float)player.getExpTarget()*2f), sqSide/2f);
        ExpBorder.setSize(screenWidth/2f, sqSide/2f);
    }


    //after updating has occurred in that single frame. Both what or what hasn't been changed has to be drawn/redrawn
    @Override
    public  void draw()
    {
        batch.begin();
        Gdx.gl.glClearColor(150/255f, 106/255f, 73/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        winfont.setScale(1.5f/400f*screenWidth, 1.5f/400f*screenHeight);
        winfont.setColor(Color.BLACK);

        Background.draw(batch);
        textbox.setSize(screenWidth-spacing*2f, screenHeight - spacing*1.5f - sqSide);

        textbox.setPosition(spacing, sqSide+spacing);
        textbox.draw(batch);
        textbox.setSize(screenWidth-spacing*2f, sqSide);
        textbox.setPosition(spacing, spacing/2f);
        textbox.draw(batch);

        ExpBar.setPosition(1f/4f*screenWidth, spacing/2f+(sqSide/4f));
        ExpBar.draw(batch);
        ExpBorder.setPosition(1f/4f*screenWidth, spacing/2f+(sqSide/4f));
        ExpBorder.draw(batch);

        buttons.setPosition(spacing,spacing/2f);
        buttons.draw(batch);
        buttons.setPosition(screenWidth-spacing-sqSide, spacing/2f);
        buttons.draw(batch);
        continue_button.draw(batch);

        winfont.draw(batch, "RESULTS:", spacing + sqSide, screenHeight - (sqSide / 2f));
        winfont.setScale(1f / 400f * screenWidth, 1f / 400f * screenHeight);

        if (page_number == 0) {

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Enemies:", spacing + sqSide, screenHeight - (sqSide / 2f) - 3 * winfont.getXHeight());                            //Number of Enemies Result
            winfont.setColor(Color.RED);
            winfont.draw(batch, enemies + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 3 * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Damage Taken", spacing + sqSide, screenHeight - (sqSide / 2f) - 5f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, damage + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 5f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Experience Gained", spacing + sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, exp_const + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Experience to Next Level", spacing + sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, player.getExpTarget()-player.getExperience() + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());
        }

        if (page_number == 1)
        {
            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Bads:", spacing + sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[0] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Okays:", spacing + sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[1] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Goods:", spacing + sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[2] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Greats:", spacing + sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[3] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Amazings:", spacing + sqSide, screenHeight - (sqSide / 2f) - 15f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[4] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 15f * winfont.getXHeight());

            winfont.setColor(Color.GREEN);
            winfont.draw(batch, "Number of Perfects:", spacing + sqSide, screenHeight - (sqSide / 2f) - 17f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[5] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 17f * winfont.getXHeight());
        }

        winfont.setColor(Color.GREEN);
        winfont.draw(batch, "Rank", 1.5f*spacing, sqSide);
        winfont.setScale(2.5f/400f*screenWidth, 2.5f/400f*screenHeight);
        winfont.draw(batch, Rating+"", spacing+sqSide/3f, spacing/2f+2*sqSide/3f);
        batch.end();
    }

    @Override
    public  boolean tap(float x, float y, int count, int button)
    {
        if (ExpEarned < 0 )         //you can only leave the winstate when you have fully leveled up
        {
            if (x > screenWidth-(buttons.getWidth()+spacing) && y > screenHeight - (buttons.getHeight()+spacing))
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

