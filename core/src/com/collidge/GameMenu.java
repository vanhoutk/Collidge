package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by simon on 11/02/15.
 */

public class GameMenu extends GameState
{

    long timer=TimeUtils.millis();
    Player player;
    boolean statsPressed=false, settingsPressed=false, savePressed=false, loadingStats=true;
    int volumeLevel=0, musicLevel=0, healthcounter=0, attackcounter=0, energycounter=0, defcounter=0, intcounter=0, XPcounter=0;
    SpriteBatch batch;
    Texture texture, background;
    Sprite stats,save, settings, backgroundsprite, vol0, vol1, vol2, vol3, vol4, music0, music1, music2, music3, music4, healthbar, energybar, attackbar, defbar, intbar, statsymbol, healthIcon, energyIcon, attackIcon, defenceIcon, intelligenceIcon, XPbar, XPframe;
    BitmapFont Font;


    //constructor
    GameMenu(GameStateManager gsm, Player plr)
    {
        super(gsm);
        player = plr;
    }

    public void initialize()
    {

        Font = new BitmapFont();

        //importing images
        batch = new SpriteBatch();

        texture = new Texture("settings.png");
        settings = new Sprite(texture);
        texture = new Texture("stats.png");
        stats = new Sprite(texture);
        texture = new Texture("save.png");
        save = new Sprite(texture);

        texture = new Texture("volume0.png");
        vol0 = new Sprite(texture);
        vol0.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume1.png");
        vol1 = new Sprite(texture);
        vol1.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume2.png");
        vol2 = new Sprite(texture);
        vol2.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume3.png");
        vol3 = new Sprite(texture);
        vol3.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume4.png");
        vol4 = new Sprite(texture);
        vol4.setSize(screenWidth *2/5, screenWidth /20);

        texture = new Texture("volume0.png");
        music0 = new Sprite(texture);
        music0.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume1.png");
        music1 = new Sprite(texture);
        music1.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume2.png");
        music2 = new Sprite(texture);
        music2.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume3.png");
        music3 = new Sprite(texture);
        music3.setSize(screenWidth *2/5, screenWidth /20);
        texture = new Texture("volume4.png");
        music4 = new Sprite(texture);
        music4.setSize(screenWidth *2/5, screenWidth /20);

        vol0.setPosition(screenWidth /2 - vol0.getWidth()/2, screenHeight/2 - vol0.getHeight()/2);
        vol1.setPosition(screenWidth /2 - vol1.getWidth()/2, screenHeight/2 - vol1.getHeight()/2);
        vol2.setPosition(screenWidth /2 - vol2.getWidth()/2, screenHeight/2 - vol2.getHeight()/2);
        vol3.setPosition(screenWidth /2 - vol3.getWidth()/2, screenHeight/2 - vol3.getHeight()/2);
        vol4.setPosition(screenWidth /2 - vol4.getWidth()/2, screenHeight/2 - vol4.getHeight()/2);
        music0.setPosition(screenWidth /2 - music0.getWidth()/2,screenHeight/4 - music0.getHeight()/2);
        music1.setPosition(screenWidth /2 - music1.getWidth()/2,screenHeight/4 - music1.getHeight()/2);
        music2.setPosition(screenWidth /2 - music2.getWidth()/2,screenHeight/4 - music2.getHeight()/2);
        music3.setPosition(screenWidth /2 - music3.getWidth()/2,screenHeight/4 - music3.getHeight()/2);
        music4.setPosition(screenWidth /2 - music4.getWidth()/2,screenHeight/4 - music4.getHeight()/2);


        texture = new Texture("statbar.png");
        healthbar = new Sprite(texture);
        texture = new Texture("statbar.png");
        energybar = new Sprite(texture);
        texture = new Texture("statbar.png");
        attackbar = new Sprite(texture);
        texture = new Texture("statbar.png");
        defbar = new Sprite(texture);
        texture = new Texture("statbar.png");
        intbar = new Sprite(texture);
        texture = new Texture("statbar.png");
        XPbar = new Sprite(texture);


        texture = new Texture("statsymbol.png");
        statsymbol = new Sprite(texture);
        statsymbol.setSize(screenWidth /8, screenHeight/10);

        texture = new Texture("XPframe.png");
        XPframe = new Sprite(texture);
        XPframe.setSize(screenWidth *7/10, screenHeight/10);
        XPframe.setPosition(screenWidth / 7, screenHeight /15);


        healthIcon = new Sprite(new Texture("techno-heart.png"));
        defenceIcon = new Sprite(new Texture("edged-shield.png"));
        attackIcon = new Sprite(new Texture("shard-sword.png"));
        energyIcon = new Sprite(new Texture("battery-pack.png"));
        intelligenceIcon = new Sprite(new Texture("brain.png"));
        healthIcon.setSize(screenWidth /40, screenWidth /40);
        energyIcon.setSize(screenWidth /40, screenWidth /40);
        attackIcon.setSize(screenWidth /40, screenWidth /40);
        defenceIcon.setSize(screenWidth /40, screenWidth /40);
        intelligenceIcon.setSize(screenWidth /40, screenWidth /40);


        background = new Texture("phonebackground.png");
        backgroundsprite = new Sprite(background);

        stats.setSize(screenWidth/10,screenWidth/8);
        save.setSize(screenWidth/10,screenWidth/8);
        settings.setSize(screenWidth/10,screenWidth/8);

        stats.setPosition(screenWidth *5/21 - stats.getWidth()/2,screenHeight/2 - stats.getHeight()/2);
        settings.setPosition(screenWidth *10/21 - save.getWidth()/2,screenHeight/2 - save.getHeight()/2);
        save.setPosition(screenWidth *15/21 - settings.getWidth()/2,screenHeight/2 - settings.getHeight()/2);
    }

    @Override
    public void draw() {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);//background colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //DRAWING SPRITES
        batch.begin();

        //setting background
        batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (statsPressed==false && settingsPressed==false && savePressed==false)
        {

            stats.draw(batch);
            save.draw(batch);
            settings.draw(batch);

        }


        if(statsPressed)
        {

            //DRAW HEALTH BAR
                healthbar.setColor(Color.GREEN);
                healthbar.setPosition(screenWidth / 5, screenHeight *6/8);
                healthbar.setSize((screenWidth*4/7) * ((5+healthcounter)/500f), screenHeight/20);
                healthbar.draw(batch);

                Font.setColor(Color.GREEN);
                Font.setScale(screenWidth / 500f, screenHeight / 500f);
                Font.draw(batch, " " + healthcounter, healthbar.getX() + healthbar.getWidth(), healthbar.getY()+healthbar.getHeight()/2);


            //DRAW ENERGY BAR
                energybar.setColor(Color.YELLOW);
                energybar.setPosition(screenWidth / 5, screenHeight *5/8);
                energybar.setSize((screenWidth*4/7) * ((5+energycounter)/500f), screenHeight/20);
                energybar.draw(batch);

                Font.setColor(Color.YELLOW);
                Font.setScale(screenWidth / 500f, screenHeight / 500f);
                Font.draw(batch, " " + energycounter, energybar.getX() + energybar.getWidth(), energybar.getY()+healthbar.getHeight()/2);


            // DRAW ATTACK BAR
                attackbar.setColor(Color.RED);
                attackbar.setPosition(screenWidth / 5, screenHeight *4/8);
                attackbar.setSize((screenWidth*4/7) * ((1+attackcounter)/100f), screenHeight/20);
                attackbar.draw(batch);

                Font.setColor(Color.RED);
                Font.setScale(screenWidth / 500f, screenHeight / 500f);
                Font.draw(batch, " " + attackcounter, attackbar.getX() + attackbar.getWidth(), attackbar.getY()+healthbar.getHeight()/2);

            // DRAW DEF BAR
                defbar.setColor(Color.BLUE);
                defbar.setPosition(screenWidth / 5, screenHeight *3/8);
                defbar.setSize((screenWidth*4/7) * ((1+defcounter)/100f), screenHeight/20);
                defbar.draw(batch);

                Font.setColor(Color.BLUE);
                Font.setScale(screenWidth / 500f, screenHeight / 500f);
                Font.draw(batch, " " + defcounter, defbar.getX() + defbar.getWidth(), defbar.getY()+healthbar.getHeight()/2);


            // DRAW INT BAR
                intbar.setColor(Color.PURPLE);
                intbar.setPosition(screenWidth / 5, screenHeight *2/8);
                intbar.setSize((screenWidth*4/7) * ((1+intcounter)/100f), screenHeight/20);
                intbar.draw(batch);

                Font.setColor(Color.PURPLE);
                Font.setScale(screenWidth / 500f, screenHeight / 500f);
                Font.draw(batch, " " + intcounter, intbar.getX() + intbar.getWidth(), intbar.getY()+healthbar.getHeight()/2);


            // DRAW XP BAR
                XPbar.setColor(Color.WHITE);
                XPbar.setPosition(XPframe.getX()+XPframe.getWidth()/14,XPframe.getY());
                XPbar.setSize((XPframe.getWidth() * (6f/7f)) * (XPcounter/(float)player.getExpTarget()), XPframe.getHeight() );

                XPbar.draw(batch);

                Font.setColor(Color.WHITE);
                Font.setScale(screenWidth / 400f, screenHeight / 400f);
                Font.draw(batch, XPcounter + " / " + player.getExpTarget(), XPframe.getX() + XPframe.getWidth()*3/7, XPframe.getY() + XPframe.getHeight()*13/10);

                if(XPcounter>=player.getExpTarget())
                {
                    player.addExperience(-XPcounter);
                    player.addExperience(XPcounter);
                    XPcounter=0;
                    gsm.levelUpState(player);
                }




            XPframe.draw(batch);
            Font.setColor(Color.BLACK);
            Font.setScale(screenWidth / 500f, screenHeight / 500f);
            Font.draw(batch, "" + player.getLevel(), XPframe.getX() + XPframe.getHeight()/8, XPframe.getY() + XPframe.getHeight()*3/5);
            Font.draw(batch, "" + (player.getLevel()+1), XPframe.getX() + XPframe.getWidth()*12/13 , XPframe.getY() + XPframe.getHeight()*3/5);


            statsymbol.setPosition(screenWidth / 7, healthbar.getY() - healthbar.getHeight() / 2);
            statsymbol.setColor(Color.GREEN);
            statsymbol.draw(batch);
            healthIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/5, statsymbol.getY()+healthIcon.getHeight()*3/5);
            healthIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, energybar.getY() - energybar.getHeight()/2);
            statsymbol.setColor(Color.YELLOW);
            statsymbol.draw(batch);
            energyIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/5, statsymbol.getY()+healthIcon.getHeight()*4/5);
            energyIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, attackbar.getY() - attackbar.getHeight()/2);
            statsymbol.setColor(Color.RED);
            statsymbol.draw(batch);
            attackIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/5, statsymbol.getY()+healthIcon.getHeight()*4/5);
            attackIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, defbar.getY() - defbar.getHeight()/2);
            statsymbol.setColor(Color.BLUE);
            statsymbol.draw(batch);
            defenceIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/5, statsymbol.getY()+healthIcon.getHeight()*4/5);
            defenceIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, intbar.getY() - intbar.getHeight()/2);
            statsymbol.setColor(Color.PURPLE);
            statsymbol.draw(batch);
            intelligenceIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/5, statsymbol.getY()+healthIcon.getHeight()*4/5);
            intelligenceIcon.draw(batch);

            if(TimeUtils.timeSinceMillis(timer)>40)
            {

                if(healthcounter<player.getHealth())
                {
                    healthcounter = healthcounter + (player.getHealth()/50);
                    if (healthcounter > player.getHealth()){healthcounter=player.getHealth();}
                }
                if(energycounter<player.getEnergy())
                {
                    energycounter = energycounter + (player.getEnergy()/50);
                    if (energycounter > player.getEnergy())
                    {
                        energycounter=player.getEnergy();
                    }
                }
                if(attackcounter<player.getAttack())
                {
                    attackcounter = attackcounter + (player.getAttack()/50);
                    if (attackcounter > player.getAttack())
                    {
                        attackcounter=player.getAttack();
                    }
                }
                if(defcounter<player.getDefence())
                {
                    defcounter = defcounter + (player.getDefence()/50);
                    if (defcounter > player.getDefence())
                    {
                        defcounter=player.getDefence();
                    }
                }
                if(intcounter<player.getIntelligence())
                {
                    intcounter = intcounter + (player.getIntelligence()/50);
                    if (intcounter > player.getIntelligence())
                    {
                        intcounter=player.getIntelligence();
                    }
                }
                if(XPcounter<player.getExperience())
                {
                    XPcounter = XPcounter + player.getExperience()/50;
                }

                timer=TimeUtils.millis();
            }


        }

        if(settingsPressed)
        {
            //batch.draw(settings, Gdx.graphics.getWidth()/2 - settings.getWidth()/2 , Gdx.graphics.getHeight()*3/4 - stats.getWidth());

            volumeLevel = gsm.getVolume();
            musicLevel = gsm.getMusic();

            Font.setColor(Color.WHITE);
            Font.setScale(screenWidth / 400f, screenHeight / 400f);
            Font.draw(batch, "Volume:", vol0.getX() , vol0.getY() + 3*vol0.getHeight()/2);
            Font.draw(batch, "Music:", music0.getX() , music0.getY() + 3*music0.getHeight()/2);

            if(volumeLevel==0)
            {
                vol0.draw(batch);
            }
            if(volumeLevel==1)
            {
                vol1.draw(batch);
            }
            if(volumeLevel==2)
            {
                vol2.draw(batch);
            }
            if(volumeLevel==3)
            {
                vol3.draw(batch);
            }
            if(volumeLevel==4)
            {
                vol4.draw(batch);
            }
            if(musicLevel==0)
            {
                music0.draw(batch);
            }
            if(musicLevel==1)
            {
                music1.draw(batch);
            }
            if(musicLevel==2)
            {
                music2.draw(batch);
            }
            if(musicLevel==3)
            {
                music3.draw(batch);
            }
            if(musicLevel==4)
            {
                music4.draw(batch);
            }
        }

        if(savePressed)
        {
            //batch.draw(save, Gdx.graphics.getWidth()/2 - save.getWidth()/2 , Gdx.graphics.getHeight()*3/4 - stats.getWidth());
            Font.setColor(Color.WHITE);
            Font.setScale(screenWidth / 400f, screenHeight / 400f);
            Font.draw(batch, "GAME SAVED", screenWidth*2/5 , screenHeight/2);
        }

        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    public boolean touchDown(float x,float y, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }


    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        if(statsPressed || settingsPressed|| savePressed)
        {
            if (x > Gdx.graphics.getWidth()*4/5 && y > Gdx.graphics.getHeight()*3/5)
            {
                statsPressed = false;
                settingsPressed = false;
                savePressed = false;

                healthcounter = 0;
                energycounter = 0;
                attackcounter = 0;
                defcounter = 0;
                intcounter = 0;
                XPcounter=0;
            }
        }

        else {

            if (x > Gdx.graphics.getWidth()*4/5 && y > Gdx.graphics.getHeight()*3/5)
            {
                gsm.closeMenu();
            }

            if (y > stats.getY() && y < stats.getY() + stats.getHeight())
            {
                if (x > stats.getX() && x < stats.getX() + stats.getWidth()) {
                    statsPressed = true;
                }
                if (x > settings.getX() && x < settings.getX() + settings.getWidth()) {
                    settingsPressed = true;
                }
                if (x > save.getX() && x < save.getX() + save.getWidth()) {
                    savePressed = true;
                }
            }

        }
        if (settingsPressed)
        {
            if (y > vol0.getY() && y < vol0.getY() + vol0.getHeight())
            {
                if(x > vol0.getX() && x < vol0.getX() + vol0.getWidth()/5)
                {
                    volumeLevel=0;
                    gsm.setVolume(volumeLevel);
                }
                if(x > vol0.getX() + vol0.getWidth()/5 && x < vol0.getX() + vol0.getWidth()*2/5)
                {
                    volumeLevel=1;
                    gsm.setVolume(volumeLevel);
                }
                if(x > vol0.getX() + vol0.getWidth()*2/5 && x < vol0.getX() + vol0.getWidth()*3/5)
                {
                    volumeLevel=2;
                    gsm.setVolume(volumeLevel);
                }
                if(x > vol0.getX() + vol0.getWidth()*3/5 && x < vol0.getX() + vol0.getWidth()*4/5)
                {
                    volumeLevel=3;
                    gsm.setVolume(volumeLevel);
                }
                if(x > vol0.getX() + vol0.getWidth()*4/5 && x < vol0.getX() + vol0.getWidth())
                {
                    volumeLevel=4;
                    gsm.setVolume(volumeLevel);
                }
            }

            if (y > vol0.getY() + screenHeight/4 && y < vol0.getY() + vol0.getHeight() + screenHeight/4)
            {
                if(x > music0.getX() && x < music0.getX() + music0.getWidth()/5)
                {
                    musicLevel=0;
                    gsm.setMusic(musicLevel);
                }
                if(x > music0.getX() + music0.getWidth()/5 && x < music0.getX() + music0.getWidth()*2/5)
                {
                    musicLevel=1;
                    gsm.setMusic(musicLevel);
                }
                if(x > music0.getX() + music0.getWidth()*2/5 && x < music0.getX() + music0.getWidth()*3/5)
                {
                    musicLevel=2;
                    gsm.setMusic(musicLevel);
                }
                if(x > music0.getX() + music0.getWidth()*3/5 && x < music0.getX() + music0.getWidth()*4/5)
                {
                    musicLevel=3;
                    gsm.setMusic(musicLevel);
                }
                if(x > music0.getX() + music0.getWidth()*4/5 && x < music0.getX() + music0.getWidth())
                {
                    musicLevel=4;
                    gsm.setMusic(musicLevel);
                }
            }
        }

        return true;
    }

    @Override
    public boolean longPress(float x, float y){
        return false;
    }


    @Override
    public boolean fling(float velocityX, float velocityY, int button){
        return false;
    }


    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY){
        return false;
    }


    @Override
    public boolean panStop(float x, float y, int pointer, int button){
        return false;
    }


    @Override
    public boolean zoom(float initialDistance, float distance){
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2){
        return false;
    }


    public void update()
    {

    }

}

