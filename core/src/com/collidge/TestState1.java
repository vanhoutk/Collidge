package com.collidge;


import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Created by Ben on 02/02/2015.
 */
public class TestState1 extends GameState {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Pixmap pixmap;
    private BitmapFont font;
    int x = 0;
    int y = 0;

    Player player = new Player();
    private TextureAtlas textureAtlas;
    private Sprite sprite2;
    private int currentFrame = 1;
    private String currentAtlasKey = new String("0001");

    private Music song=Gdx.audio.newMusic(Gdx.files.internal("mymusic.mp3"));
    private Music song2=Gdx.audio.newMusic(Gdx.files.internal("mymusic2.mp3"));




    TestState1(GameStateManager gsm)
    {
        super(gsm);
        textureAtlas = new TextureAtlas("spritesheet.atlas");

        AtlasRegion region = textureAtlas.findRegion("0001");

        sprite2 = new Sprite(region);
        sprite2.setPosition(120, 100);
        sprite2.setSize(screenWidth/4,screenHeight/4);
        // sprite2.scale(2.5f);



        batch = new SpriteBatch();
        texture = new Texture("jet.png");
        sprite = new Sprite(texture);
        // sprite.setScale(3.0f, 3.0f);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        currentAtlasKey = String.format("%04d", currentFrame);
        sprite2.setRegion(textureAtlas.findRegion(currentAtlasKey));
        //font.setScale(Gdx.graphics.getHeight()/10,Gdx.graphics.getHeight()/);

        // A Pixmap is basically a raw image in memory as repesented by pixels
        // We create one 256 wide, 128 height using 8 bytes for Red, Green, Blue and Alpha channels
        pixmap = new Pixmap(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/6, Pixmap.Format.RGBA8888);

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

        //initialize();
    }

    @Override
    public void initialize ()
    {

        song2.stop();
        song.play();
        song.setLooping(true);


    }

    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
        texture.dispose();
    }

    @Override
    public void update ()
    {

    }

    @Override
    public void draw ()
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.flush();
        batch.begin();
        //batch.draw(img, 200, 500);
        sprite.draw(batch);
        font.setScale((int)(screenWidth/250.0),(int)(screenHeight/250.0));
        font.draw(batch, "Lvl: " + player.getLevel() + " Exp: " + player.getExperience()+"/"+player.getExpTarget(), Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        font.draw(batch, "Hp: " + player.getCurrentHealth()+"/"+player.getHealth(), Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-font.getLineHeight());
        sprite2.setPosition(x-screenWidth/8,y-screenWidth/8);
        sprite2.draw(batch);

        //x++; y++;


        //sprite.setPosition((int)(x-Gdx.graphics.getWidth()/12), (int)(y-Gdx.graphics.getWidth()));
       // sprite.draw(batch);
        //sprite.setPosition(x-(screenWidth/12), y-(screenHeight/12));
        //sprite.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {
    }

    public void keyDown()
    {

    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //gsm.currentState++;
       // if(gsm.currentState > 1) gsm.currentState = 0;
        //gsm.changeState(++gsm.currentState);
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenY>screenHeight/2&&screenX>screenWidth/2)
        {
            if(player.getCurrentHealth()>0)
            {

                song.pause();
                song2.play();
                song2.setLooping(true);
                gsm.startFight(player);
            }

        }
        else if(screenY<screenX/2&&screenX>screenWidth/2)
        {
            player.healAll();
        }
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {


        x=screenX;
        y=(-screenY)+Gdx.graphics.getHeight();
     //   System.out.println(x+", "+y);
        return false;
    }
}
