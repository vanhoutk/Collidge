package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    Sprite textbox, ExpBorder, ExpBar, buttons, Background, continue_button, rank_button, results;
    double ExpEarned;
    int exp_const;
    int enemies;
    private BitmapFont winfont;
    float spacing = (screenWidth - 5 * screenWidth/7)/6;
    float sqSide = screenWidth/7;
    float i;
    int damage;
    int[] ratings;
    Sprite Rating;
    int page_number;

    Music winMusic =  Gdx.audio.newMusic(Gdx.files.internal("winmusic.mp3"));

    Timer.Task Exp=new Timer.Task()
    {
        @Override
        public void run()
        {
            if (ExpEarned > 0)
            {
                ExpEarned-=0.5;
                player.addExperience(0.5);
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

        winfont = new BitmapFont();//Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
        buttons = new Sprite(new Texture("textbox_background_2.png"));
        buttons.setSize(sqSide, sqSide);
        rank_button = new Sprite (new Texture("textbox_background_2.png"));
        textbox = new Sprite(new Texture("textbox_background_2.png"));
        ExpBorder = new Sprite (new Texture("XPframe.png"));
        ExpBar = new Sprite (new Texture("statbar.png"));
        Background = new Sprite(new Texture("inventoryBackground3.jpg"));
        continue_button = new Sprite(new Texture("back_button.png"));
        continue_button.flip(true,false);
        results = new Sprite (new Texture("results.png"));

        if      (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) == 1)
            Rating = new Sprite (new Texture("o.png"));
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.6)
            Rating = new Sprite (new Texture("a.png"));
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.4)
            Rating = new Sprite (new Texture("b.png"));
        else if (((float)(player.getHealth())-(float)damage)/(float)(player.getHealth()) > 0.2)
            Rating = new Sprite (new Texture("c.png"));
        else
            Rating = new Sprite (new Texture("d.png"));

        continue_button.setSize(buttons.getWidth(), buttons.getHeight());
        continue_button.setPosition(screenWidth-(spacing+sqSide), spacing/2f);
        Background.setSize(screenWidth, screenHeight);
        Background.setPosition(0, 0);
    }

    @Override
    public void initialize()
    {
        winMusic.setVolume(gsm.getVolume()/4);
        winMusic.play();
    }

    //update will update all game logic before drawing
    @Override
    public  void update()
    {
        Timer.instance().clear();
        Timer.instance().start();
        Timer.instance().postTask(Exp);
        ExpBorder.setSize(screenWidth-(spacing*3f+2f*sqSide), sqSide/2f);
        ExpBar.setSize((((float)player.getTrueExperience()*screenWidth)/((float)player.getExpTarget()*2f))*(13f/14f), sqSide/2f-ExpBorder.getHeight()/14f);

    }


    //after updating has occurred in that single frame. Both what or what hasn't been changed has to be drawn/redrawn
    @Override
    public  void draw()
    {
        batch.begin();
        Gdx.gl.glClearColor(150/255f, 106/255f, 73/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Background.draw(batch);

        textbox.setSize(screenWidth-spacing*2f, sqSide);
        textbox.setPosition(spacing, spacing/2f);
        textbox.draw(batch);

        //textbox.setSize(screenWidth/3f, sqSide/2f);
        //textbox.setPosition(screenWidth/3f, screenHeight - (sqSide+spacing)/2f);
        //textbox.draw(batch);

        results.setSize(screenWidth/3f,sqSide/2f);
        results.setPosition(screenWidth/3f, screenHeight - (sqSide+spacing)/2f);
        results.draw(batch);

        ExpBar.setPosition(spacing*1.5f+sqSide+ExpBorder.getWidth()*(1f/14f), spacing/2f+(sqSide/4f));
        ExpBar.draw(batch);
        ExpBorder.setPosition(spacing*1.5f+sqSide, spacing/2f+(sqSide/4f));
        ExpBorder.draw(batch);
        winfont.setScale(screenWidth / 500f, screenHeight / 500f);
        winfont.setColor(Color.BLACK);
        winfont.drawWrapped(batch, "" + (player.getLevel() + player.getLevelUpCounter()), ExpBorder.getX() + ExpBorder.getHeight() / 8, ExpBorder.getY() + ExpBorder.getHeight() * 3 / 5, 3*ExpBorder.getHeight() / 6f, BitmapFont.HAlignment.CENTER);
        winfont.drawWrapped(batch, "" + (player.getLevel() + player.getLevelUpCounter()+1), ExpBorder.getX() + ExpBorder.getWidth()*12/13 , ExpBorder.getY() + ExpBorder.getHeight()*3/5, 3*ExpBorder.getHeight() / 6f, BitmapFont.HAlignment.CENTER);


        buttons.setSize(continue_button.getWidth(), continue_button.getHeight());
        rank_button.setSize(continue_button.getWidth(), continue_button.getHeight());
        rank_button.setPosition(spacing,spacing/2f);
        rank_button.draw(batch);
        buttons.setPosition(screenWidth-spacing-sqSide, spacing/2f);
        buttons.draw(batch);
        continue_button.draw(batch);

        //winfont.setScale(1.5f/400f*screenWidth, 1.5f/400f*screenHeight);
        //winfont.setColor(Color.BLACK);
        //winfont.drawWrapped(batch, "RESULTS", textbox.getX(), screenHeight - spacing / 2f - 2f * (textbox.getHeight() - winfont.getLineHeight()) / 3f, textbox.getWidth(), BitmapFont.HAlignment.CENTER);
        winfont.setScale(1f / 400f * screenWidth, 1f / 400f * screenHeight);

        if (page_number == 0) {

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Enemies", spacing + sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());                            //Number of Enemies Result
            winfont.setColor(Color.RED);
            winfont.draw(batch, enemies + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Damage Taken", spacing + sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, damage + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Experience Gained", spacing + sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, exp_const + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Experience to Next Level", spacing + sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());                                 //Health Remainder
            winfont.setColor(Color.RED);
            winfont.draw(batch, player.getExpTarget()-player.getExperience() + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());
        }

        if (page_number == 1)
        {
            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Bads:", spacing + sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[0] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 7f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Okays:", spacing + sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[1] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 9f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Goods:", spacing + sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[2] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 11f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Greats:", spacing + sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[3] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 13f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Amazings:", spacing + sqSide, screenHeight - (sqSide / 2f) - 15f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[4] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 15f * winfont.getXHeight());

            winfont.setColor(Color.WHITE);
            winfont.draw(batch, "Number of Perfects:", spacing + sqSide, screenHeight - (sqSide / 2f) - 17f * winfont.getXHeight());
            winfont.setColor(Color.RED);
            winfont.draw(batch, ratings[5] + "", spacing + 5 * sqSide, screenHeight - (sqSide / 2f) - 17f * winfont.getXHeight());
        }
        winfont.setColor(Color.GREEN);
        winfont.drawWrapped(batch, "Rank", spacing, sqSide, rank_button.getWidth(), BitmapFont.HAlignment.CENTER);
        Rating.setSize(rank_button.getWidth() / 3f, rank_button.getHeight() / 3f);
        Rating.setPosition(rank_button.getX() + (rank_button.getWidth() - Rating.getWidth()) / 2f, rank_button.getY() + rank_button.getHeight() / 4f);
        Rating.draw(batch);
        batch.end();
    }

    @Override
    public  boolean tap(float x, float y, int count, int button)
    {
        if (ExpEarned <= 0 )         //you can only leave the winstate when you have fully leveled up
        {
            if (x > screenWidth-(buttons.getWidth()+spacing) && y > screenHeight - (buttons.getHeight()+spacing))
            {
                if (page_number == 0)
                {
                    page_number = 1;
                    return false;
                }
                endWinState();
            }
        }
        return false;
    }

    public void endWinState()
    {
        winMusic.dispose();

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


