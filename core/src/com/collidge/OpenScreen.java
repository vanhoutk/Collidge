package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



/**
 * Created by Conor
 */
public class OpenScreen extends GameState{

    SpriteBatch batch;
    Texture texture ;
    Sprite settingsIcon , playIcon, newgameIcon, textBanner, background;

    float horSize = screenWidth/7;
    float vertGap = ((screenHeight/2)- horSize)/2;

    Player plr = new Player();


    OpenScreen(GameStateManager gsm){

        super(gsm);

        texture = new Texture("background.png");
        settingsIcon = new Sprite(new Texture("collidgeSettings.png"));
        playIcon = new Sprite(new Texture("collidgePlay.png"));
        newgameIcon = new Sprite(new Texture("collidgeNewFile.png"));
        textBanner = new Sprite(new Texture("collidgeText.png"));
        background = new Sprite(new Texture("background3.png"));

        batch = new SpriteBatch();

    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(150/255f, 106/255f, 73/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.setSize(screenWidth, screenHeight);
        background.setPosition(0, 0);
        background.draw(batch);

        textBanner.setSize(screenWidth, screenHeight/2);
        textBanner.setPosition(0,screenHeight/2);
        textBanner.draw(batch);

        newgameIcon.setSize(horSize, horSize);
        newgameIcon.setPosition(horSize, vertGap);
        newgameIcon.draw(batch);

        playIcon.setSize(horSize, horSize);
        playIcon.setPosition((3*horSize), vertGap);
        playIcon.draw(batch);

        settingsIcon.setSize(horSize, horSize);
        settingsIcon.setPosition((5*horSize) , vertGap);
        settingsIcon.draw(batch);

        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDown(float x,float y, int pointer, int button)
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

    public boolean tap(float x, float y, int count, int button){
        if(x > horSize && x < 2*horSize && y > screenHeight/2){
            //new game pressed
            Gdx.app.exit();
        }
        else if(x > 3*horSize && x < 4*horSize && y > screenHeight/2){
            gsm.changeState(1);
        }
        else if (x > 5 * horSize && x < 6 * horSize && y > screenHeight / 2) {
            gsm.openMenu(plr);
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
