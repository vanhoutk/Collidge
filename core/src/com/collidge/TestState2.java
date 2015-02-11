package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Ben on 02/02/2015.
 */
public class TestState2 extends GameState {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Pixmap pixmap;
    private BitmapFont font;
    int x = 0;
    int y = 0;

    private TextureAtlas textureAtlas;
    private Sprite sprite2;
    private int currentFrame = 1;
    private String currentAtlasKey = new String("0001");


    TestState2(GameStateManager gsm) {
        super(gsm);
        //initialize();
    }

    @Override
    public void initialize () {
        textureAtlas = new TextureAtlas("spritesheet.atlas");
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion("0001");
        sprite2 = new Sprite(region);
        sprite2.setPosition(120, 100);
        sprite2.scale(2.5f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentFrame++;
                if (currentFrame > 20)
                    currentFrame = 1;

                // ATTENTION! String.format() doesnt work under GWT for god knows why...
                currentAtlasKey = String.format("%04d", currentFrame);
                sprite2.setRegion(textureAtlas.findRegion(currentAtlasKey));
            }
        }
                , 0, 1 / 15.0f);


        batch = new SpriteBatch();
        texture = new Texture("jet.png");
        sprite = new Sprite(texture);
        // sprite.setScale(3.0f, 3.0f);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.setScale(3.0f, 10.0f);

        // A Pixmap is basically a raw image in memory as repesented by pixels
        // We create one 256 wide, 128 height using 8 bytes for Red, Green, Blue and Alpha channels
        pixmap = new Pixmap(256,128, Pixmap.Format.RGBA8888);

        //Fill it red
        pixmap.setColor(Color.RED);
        pixmap.fill();

        //Draw two lines forming an X
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine(0, 0, pixmap.getWidth()-1, pixmap.getHeight()-1);
        pixmap.drawLine(0, pixmap.getHeight()-1, pixmap.getWidth()-1, 0);

        //Draw a circle about the middle
        pixmap.setColor(Color.YELLOW);
        pixmap.drawCircle(pixmap.getWidth()/2, pixmap.getHeight()/2, pixmap.getHeight()/2 - 1);

        texture = new Texture(pixmap);

        //It's the textures responsibility now... get rid of the pixmap
        pixmap.dispose();

        sprite = new Sprite(texture);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        texture.dispose();
    }

    @Override
    public void update () {

    }

    @Override
    public void draw () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(img, 200, 500);
        sprite.draw(batch);
        font.draw(batch, "width = " + Gdx.graphics.getWidth() + " height = " + Gdx.graphics.getHeight(), 1000, 400);
        sprite2.setPosition(40, 40);
        sprite2.draw(batch);
        sprite2.setPosition(500, 600);
        sprite2.draw(batch);
        sprite2.setPosition(700, 900);
        sprite2.draw(batch);
        x--; y--;
        sprite.setPosition(x, y);
        sprite.draw(batch);
        sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        sprite.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void keyDown() {

    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //gsm.currentState++;
        //if(gsm.currentState > 1) gsm.currentState = 0;
        gsm.changeState(++gsm.currentState);
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
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
