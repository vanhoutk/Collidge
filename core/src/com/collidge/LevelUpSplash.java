package com.collidge;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Daniel on 25/02/2015.
 */
public class LevelUpSplash extends GameState
{

    Player player;
    private SpriteBatch batch;
    private Texture texture ;

    private Sprite healthIcon,defenceIcon,attackIcon,energyIcon,intelligenceIcon;
    private Sprite panel,levelUpText;
    private BitmapFont text;
    private String infoText;
    private int selected;

    public LevelUpSplash(GameStateManager gsm,Player playr)
    {
        super(gsm);
        player=playr;
    }

    @Override
    public void initialize()
    {

        selected=50;
        batch=new SpriteBatch();
        texture=new Texture("panelInset_beige.png");
        panel=new Sprite(texture);
        texture= new Texture("levelUpText.png");
        levelUpText=new Sprite(texture);
        texture=new Texture("techno-heart.png");
        healthIcon=new Sprite(texture);
        texture=new Texture("edged-shield.png");
        defenceIcon=new Sprite(texture);
        texture=new Texture("shard-sword.png");
        attackIcon=new Sprite(texture);
        texture=new Texture("battery-pack.png");
        energyIcon=new Sprite(texture);
        texture=new Texture("brain.png");
        intelligenceIcon=new Sprite(texture);
        healthIcon.setSize(screenWidth/11,screenWidth/11);

        attackIcon.setSize(screenWidth/11,screenWidth/11);

        defenceIcon.setSize(screenWidth/11,screenWidth/11);

        energyIcon.setSize(screenWidth/11,screenWidth/11);

        intelligenceIcon.setSize(screenWidth/11,screenWidth/11);


        text=new BitmapFont();
        text.setScale(screenWidth/300.0f,screenHeight/300.0f);
        text.setColor(Color.BLACK);


    }

    @Override
    public void update()
    {

        switch(selected)
        {
            case 0:
                infoText="Health: More Hp for a healthier you   \n+5 Hp";
                break;
            case 1:
                infoText="Defence: Take less damage from attacks, insults still sting though\n+0.5 Def";
                break;
            case 2:
                infoText="Attack: Kick more ass, take more names, chew less gum\n+1 Atk";
                break;
            case 3:
                infoText="Energy: WOW, that's the biggest energy bar I've ever seen!\n+5 En";
                break;
            case 4:
                infoText="Intelligence: You're a smart guy, you know this stat helps you regenerate energy\n+1 Int";
                break;
            default:
                infoText="Pick a stat to increase! (On top of standard level up values of\n+5Hp +1Atk +5En +.3Int)";
                break;
        }

    }

    @Override
    public void draw()
    {

        batch.begin();

        panel.setColor(Color.LIGHT_GRAY);
        panel.setSize(screenWidth*3,screenHeight*3);
        panel.setPosition(-screenWidth,-screenHeight);
        panel.draw(batch);
        panel.setColor(Color.WHITE);
        levelUpText.setSize(screenWidth / 3, screenHeight / 10);
        levelUpText.setPosition(screenWidth / 3, screenHeight / 1.2f);
        levelUpText.draw(batch);
        text.draw(batch,"You've reached to level "+(player.getLevel()+1),screenWidth/4.1f,screenHeight/1.2f);
        panel.setSize(screenWidth/11,screenWidth/11);
        for(int i=0;i<5;i++)
        {
            panel.setPosition(((1 + (2 * i)) * (screenWidth / 11)), (screenHeight / (1.3f)) - (panel.getHeight() * 2));
            panel.draw(batch);
            switch (i)
            {
                case 0:
                    healthIcon.setPosition(panel.getX(), panel.getY());
                    healthIcon.draw(batch);
                    break;
                case 1:
                    defenceIcon.setPosition(panel.getX(), panel.getY());
                    defenceIcon.draw(batch);
                    break;
                case 2:
                    attackIcon.setPosition(panel.getX(), panel.getY());
                    attackIcon.draw(batch);
                    break;
                case 3:
                    energyIcon.setPosition(panel.getX(), panel.getY());
                    energyIcon.draw(batch);
                    break;
                case 4:
                    intelligenceIcon.setPosition(panel.getX(), panel.getY());
                    intelligenceIcon.draw(batch);
                    break;
                default:
                    break;
            }

        }
        panel.setSize(9*screenWidth/11,text.getLineHeight()*4);
        panel.setPosition(healthIcon.getX(), healthIcon.getY() - (text.getLineHeight() * 5));
        panel.draw(batch);
        text.drawWrapped(batch, infoText, panel.getX() + screenWidth / 20, panel.getY() + (screenHeight / 20) + text.getLineHeight() * 3, panel.getWidth() - (2 * screenWidth / 20));
        panel.setPosition(screenWidth / 3, screenHeight / 15);
        panel.setSize(screenWidth / 3, screenHeight / 10);
        panel.setColor(Color.GREEN);
        panel.draw(batch);
        text.draw(batch,"CONFIRM",panel.getX()+screenWidth/20,panel.getY()+panel.getHeight()-screenHeight/30);
        panel.setColor(Color.WHITE);
        batch.end();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
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


        y=-y;
        y+=screenHeight;
        if(x>screenWidth/3&&x<screenWidth/1.5&&y<screenHeight/6)
        {
            if (selected < 5 && selected >= 0)
            {
                player.addStat(selected);
                batch.dispose();
                texture.dispose();
                text.dispose();
                if (player.getLevelUpCounter() > 0)
                {

                    this.initialize();
                }
                else
                {

                    gsm.endFight();
                }
            }
        }
        else if(y<(levelUpText.getY()-healthIcon.getHeight())&&y>screenHeight/2.4)
        {
            if(x>1*screenWidth/11&&x<2*screenWidth/11)
            {
                selected=0;
            }
            else if(x>3*screenWidth/11&&x<4*screenWidth/11)
            {

                selected=1;
            }
            else if(x>5*screenWidth/11&&x<6*screenWidth/11)
            {

                selected=2;
            }
            else if(x>7*screenWidth/11&&x<8*screenWidth/11)
            {

                selected=3;
            }
            else if(x>9*screenWidth/11&&x<10*screenWidth/11)
            {

                selected=4;
            }
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
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
