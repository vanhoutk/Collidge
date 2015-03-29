package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.XmlReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by simon on 11/02/15.
 */

public class GameMenu extends GameState
{

    long timer=TimeUtils.millis();
    Player player;
    boolean statsPressed=false, settingsPressed=false, savePressed=false, muted;
    int volumeLevel=0, musicLevel=0, healthcounter=0, attackcounter=0, energycounter=0, defcounter=0, intcounter=0, XPcounter=0, musicPre, volPre;
    SpriteBatch batch;
    Texture texture, background;
    Sprite stats,save, settings, backgroundsprite, vol0, vol1, vol2, vol3, vol4, music0, music1, music2, music3, music4, healthMax, energyMax, healthbar, energybar, attackbar, defbar, intbar, statsymbol, healthIcon, energyIcon, attackIcon, defenceIcon, intelligenceIcon, XPbar, XPframe, mute, unmute;
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

        texture = new Texture("mute.png");
        mute = new Sprite(texture);
        texture = new Texture("unmute.png");
        unmute = new Sprite(texture);
        mute.setSize(screenWidth /15, screenWidth /15);
        unmute.setSize(screenWidth /15, screenWidth /15);

        vol0.setPosition(screenWidth /2 - vol0.getWidth()/2, screenHeight*7/10 - vol0.getHeight()/2);
        vol1.setPosition(screenWidth /2 - vol1.getWidth()/2, screenHeight*7/10 - vol1.getHeight()/2);
        vol2.setPosition(screenWidth /2 - vol2.getWidth()/2, screenHeight*7/10 - vol2.getHeight()/2);
        vol3.setPosition(screenWidth /2 - vol3.getWidth()/2, screenHeight*7/10 - vol3.getHeight()/2);
        vol4.setPosition(screenWidth /2 - vol4.getWidth()/2, screenHeight*7/10 - vol4.getHeight()/2);
        music0.setPosition(screenWidth /2 - music0.getWidth()/2,screenHeight*4/10 - music0.getHeight()/2);
        music1.setPosition(screenWidth /2 - music1.getWidth()/2,screenHeight*4/10 - music1.getHeight()/2);
        music2.setPosition(screenWidth /2 - music2.getWidth()/2,screenHeight*4/10 - music2.getHeight()/2);
        music3.setPosition(screenWidth /2 - music3.getWidth()/2,screenHeight*4/10 - music3.getHeight()/2);
        music4.setPosition(screenWidth /2 - music4.getWidth()/2,screenHeight*4/10 - music4.getHeight()/2);

        mute.setPosition(screenWidth/2 - mute.getWidth()/2 , screenHeight/5 - mute.getHeight()/2);
        unmute.setPosition(screenWidth/2 - unmute.getWidth()/2 , screenHeight/5 - unmute.getHeight()/2);


        texture = new Texture("statbar.png");
        healthMax = new Sprite(texture);
        texture = new Texture("statbar.png");
        energyMax = new Sprite(texture);
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
            healthMax.setColor(Color.TEAL);
            healthMax.setPosition(screenWidth / 5, screenHeight *6/8);
            healthMax.setSize((screenWidth*4/7), screenHeight/20);
            healthMax.draw(batch);

            healthbar.setColor(Color.GREEN);
            healthbar.setPosition(screenWidth / 5, screenHeight *6/8);
            healthbar.setSize((screenWidth*4/7) * (healthcounter/(float)player.getHealth()), screenHeight/20);
            healthbar.draw(batch);

            Font.setColor(Color.GREEN);
            Font.setScale(screenWidth / 500f, screenHeight / 500f);
            Font.draw(batch, " " + healthcounter, healthbar.getX() + healthbar.getWidth(), healthbar.getY()+healthbar.getHeight()/2);


            //DRAW ENERGY BAR
            energyMax.setColor(Color.ORANGE);
            energyMax.setPosition(screenWidth / 5, screenHeight *5/8);
            energyMax.setSize((screenWidth*4/7), screenHeight/20);
            energyMax.draw(batch);

            energybar.setColor(Color.YELLOW);
            energybar.setPosition(screenWidth / 5, screenHeight *5/8);
            energybar.setSize((screenWidth*4/7) * (energycounter/(float)player.getEnergy()), screenHeight/20);
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

            Font.setScale(screenWidth / 650f, screenHeight / 650f);
            Font.drawWrapped(batch, " Lv. ", XPframe.getX() + XPframe.getWidth()*2/300, XPframe.getY() + XPframe.getHeight()*4/5, XPframe.getWidth()/14, BitmapFont.HAlignment.CENTER);
            Font.drawWrapped(batch, " Lv. ", XPframe.getX() + XPframe.getWidth()*275/300 , XPframe.getY() + XPframe.getHeight()*4/5, XPframe.getWidth()/14, BitmapFont.HAlignment.CENTER);

            Font.setScale(screenWidth / 450f, screenHeight / 450f);
            Font.drawWrapped(batch, "" + player.getLevel(), XPframe.getX() + XPframe.getHeight()/20, XPframe.getY() + XPframe.getHeight()*3/5, XPframe.getWidth()/13, BitmapFont.HAlignment.CENTER);
            Font.drawWrapped(batch, "" + (player.getLevel() + 1), XPframe.getX() + XPframe.getWidth()*21/23 , XPframe.getY() + XPframe.getHeight()*3/5, XPframe.getWidth()/13, BitmapFont.HAlignment.CENTER);


            statsymbol.setPosition(screenWidth / 7, healthbar.getY() - healthbar.getHeight() / 2);
            statsymbol.setColor(Color.GREEN);
            statsymbol.draw(batch);
            healthIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/4, statsymbol.getY()+healthIcon.getHeight()*3/5);
            healthIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, energybar.getY() - energybar.getHeight()/2);
            statsymbol.setColor(Color.YELLOW);
            statsymbol.draw(batch);
            energyIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/4, statsymbol.getY() + healthIcon.getHeight() * 7/10);
            energyIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, attackbar.getY() - attackbar.getHeight()/2);
            statsymbol.setColor(Color.RED);
            statsymbol.draw(batch);
            attackIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/4, statsymbol.getY()+healthIcon.getHeight() * 7/10);
            attackIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, defbar.getY() - defbar.getHeight()/2);
            statsymbol.setColor(Color.BLUE);
            statsymbol.draw(batch);
            defenceIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/4, statsymbol.getY()+healthIcon.getHeight()*6/10);
            defenceIcon.draw(batch);

            statsymbol.setPosition(screenWidth / 7, intbar.getY() - intbar.getHeight()/2);
            statsymbol.setColor(Color.PURPLE);
            statsymbol.draw(batch);
            intelligenceIcon.setPosition(statsymbol.getX() + healthIcon.getWidth()/4, statsymbol.getY()+healthIcon.getHeight()*7/10);
            intelligenceIcon.draw(batch);

            if(TimeUtils.timeSinceMillis(timer)>40)
            {

                if(healthcounter<player.getCurrentHealth())
                {
                    healthcounter = healthcounter + 1 + (player.getCurrentHealth()/50);
                    if (healthcounter > player.getCurrentHealth())
                    {
                        healthcounter=player.getCurrentHealth();
                    }
                }
                if(energycounter<player.getCurrentEnergy())
                {
                    energycounter = energycounter + 1 + (player.getCurrentEnergy()/50);
                    if (energycounter > player.getCurrentEnergy())
                    {
                        energycounter=player.getCurrentEnergy();
                    }
                }
                if(attackcounter<player.getAttack())
                {
                    attackcounter = attackcounter + 1 + (player.getAttack()/50);
                    if (attackcounter > player.getAttack())
                    {
                        attackcounter=player.getAttack();
                    }
                }
                if(defcounter<player.getDefence())
                {
                    defcounter = defcounter + 1 + (player.getDefence()/50);
                    if (defcounter > player.getDefence())
                    {
                        defcounter=player.getDefence();
                    }
                }
                if(intcounter<player.getIntelligence())
                {
                    intcounter = intcounter + 1 + (player.getIntelligence()/50);
                    if (intcounter > player.getIntelligence())
                    {
                        intcounter=player.getIntelligence();
                    }
                }
                if(XPcounter<player.getExperience())
                {
                    XPcounter = XPcounter + 1 + player.getExperience()/50;
                    if (XPcounter > player.getExperience())
                    {
                        XPcounter=player.getExperience();
                    }
                }

                timer=TimeUtils.millis();
            }


        }

        if(settingsPressed)
        {
            volumeLevel = gsm.getVolume();
            musicLevel = gsm.getMusic();


            if (volumeLevel == 0 && musicLevel == 0)
            {
                muted = true;
            }
            else {muted=false;}

            if (muted == true)
            {
                mute.draw(batch);
            }
            else {unmute.draw(batch);}

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
                    /**
                     * Kris
                     * Added to call the save stats method
                     */
                    saveStats();
                    /**
                     * Deirdre: Save Inventory..
                     */
                    saveInventory();
                }
            }

        }
        if (settingsPressed)
        {
            if (y < music0.getY() && y > music0.getY() - vol0.getHeight())
            {
                if(x > vol0.getX() && x < vol0.getX() + vol0.getWidth()/5)
                {
                    volumeLevel=0;

                }
                if(x > vol0.getX() + vol0.getWidth()/5 && x < vol0.getX() + vol0.getWidth()*2/5)
                {
                    volumeLevel=1;

                }
                if(x > vol0.getX() + vol0.getWidth()*2/5 && x < vol0.getX() + vol0.getWidth()*3/5)
                {
                    volumeLevel=2;

                }
                if(x > vol0.getX() + vol0.getWidth()*3/5 && x < vol0.getX() + vol0.getWidth()*4/5)
                {
                    volumeLevel=3;

                }
                if(x > vol0.getX() + vol0.getWidth()*4/5 && x < vol0.getX() + vol0.getWidth())
                {
                    volumeLevel=4;

                }
                gsm.setVolume(volumeLevel);
                //gsm.backgroundMus.setVolume((float)volumeLevel/4);
            }

            if (y < vol0.getY() && y > vol0.getY() - vol0.getHeight())
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
                gsm.backgroundMus.setVolume((float)musicLevel/4);
            }

            //mute
            if (y < screenHeight - mute.getY() && y > screenHeight - mute.getY() - mute.getHeight())
            {
                if (muted == true)
                {
                    muted=false;
                    gsm.setMusic(musicPre);
                    gsm.setVolume(volPre);
                }
                else
                {
                    musicPre = gsm.getMusic();
                    volPre = gsm.getVolume();
                    muted = true;
                    gsm.setVolume(0);
                    gsm.setMusic(0);
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

    /**
     * Kris
     * Moved Dan's saving here and changed it to save as an xml format to make it more readable
     */
    public void saveStats()
    {
        if(Gdx.files.isLocalStorageAvailable())
        {
            OutputStream out=Gdx.files.local( "stats.xml" ).write(false);
            try
            {
                System.out.println("Saving stats");
                String saveStats;
                saveStats = ("<stats>"
                                + "<Level>" + gsm.user.getLevel() + "</Level>"
                                + "<AttackPoints>" + gsm.user.getAttackPoints() + "</AttackPoints>"
                                + "<DefencePoints>" + gsm.user.getDefencePoints() + "</DefencePoints>"
                                + "<IntelligencePoints>" + gsm.user.getIntelligencePoints() + "</IntelligencePoints>"
                                + "<HealthPoints>" + gsm.user.getHealthPoints() + "</HealthPoints>"
                                + "<EnergyPoints>" + gsm.user.getEnergyPoints() + "</EnergyPoints>"
                                + "<Experience>" + gsm.user.getExperience() + "</Experience>"
                                + "</stats>)");
                out.write(saveStats.getBytes());
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

    /**
     * Deirdre
     * Saving Inventory
     * Called when save button in GameMenu is selected
     */
    public void saveInventory()
    {
        if(Gdx.files.isLocalStorageAvailable())
        {
            OutputStream out = Gdx.files.local("inventory.xml").write(false);
            try {
                System.out.println("Saving inventory");
                String saveInventory;
                saveInventory = ("<inventory>"
                        + "<items>"
                            + "<Tsquare>" + gsm.user.items.getItemQuantity("Tsquare") + "</Tsquare>"
                            + "<Scarf>" + gsm.user.items.getItemQuantity("Scarf")+ "</Scarf>"
                            + "<Macshield>" + gsm.user.items.getItemQuantity("Macshield") + "</Macshield>"
                            + "<Bookshield>" + gsm.user.items.getItemQuantity("Bookshield") + "</Bookshield>"
                            + "<Coffee>" + gsm.user.items.getItemQuantity("Coffee") + "</Coffee>"
                            + "<EnergyDrink>" + gsm.user.items.getItemQuantity("Energy Drink") + "</EnergyDrink>"
                            + "<Noodles>" + gsm.user.items.getItemQuantity("Noodles") +"</Noodles>"
                            + "<Sandwich>" + gsm.user.items.getItemQuantity("Sandwich") + "</Sandwich>"
                            + "<IED>" + gsm.user.items.getItemQuantity("IED") + "</IED>"
                        + "</items>"
                        + "<equipped>"
                            + "<EquippedWeapon>" + gsm.user.equippedWeapon + "</EquippedWeapon>"
                            + "<EquippedArmour>" + gsm.user.equippedArmour + "</EquippedArmour>"
                        + "</equipped>"
                                + "</inventory>");
                out.write(saveInventory.getBytes());
                //System.out.println(saveInventory);
            } catch (IOException e) {
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
}




