package com.collidge;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;



/**
 * Created by Gary on 26/01/2015.
 */
public class Play extends GameState {

    Player userCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthographicCamera camera2;
    private MapPlayer player;
    private Texture menuButton, inventoryButton;
    private Sprite menuButtonSprite, inventoryButtonSprite;

    private SpriteBatch  batch;

    Play(GameStateManager gsm)
    {
        super(gsm);

        userCharacter=gsm.user;
    }

    @Override
    public void initialize()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,480,480*(h/w));
        camera.update();

        batch = new SpriteBatch();
        TmxMapLoader loader = new TmxMapLoader();

        map = loader.load("TrinityMap1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        player = new MapPlayer(new Sprite(new Texture("player.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(8 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 8) * player.getCollisionLayer().getTileHeight());

        //Adding buttons for inventory and menu to the map
        menuButton = new Texture("android-mobile.png");
        inventoryButton = new Texture("schoolbag.png");
        menuButtonSprite = new Sprite(menuButton);
        inventoryButtonSprite = new Sprite(inventoryButton);
    }

    @Override
    public void draw()
    {
        float cameraX=player.getX() + player.getWidth() / 2;
        float cameraY=player.getY() + player.getHeight() / 2;
        if((cameraX+camera.viewportWidth/2)>(player.getCollisionLayer().getTileWidth()*player.getCollisionLayer().getWidth()))
        {
            cameraX=(player.getCollisionLayer().getTileWidth()*player.getCollisionLayer().getWidth())-(camera.viewportWidth/2);
        }
        else if((cameraX-camera.viewportWidth/2)<0)
        {
            cameraX=camera.viewportWidth/2;
        }
        if((cameraY+camera.viewportHeight/2)>(player.getCollisionLayer().getTileHeight()*player.getCollisionLayer().getHeight()))
        {
            cameraY=(player.getCollisionLayer().getTileHeight()*player.getCollisionLayer().getHeight())-(camera.viewportHeight/2);
        }
        else if((cameraY-camera.viewportHeight/2)<0)
        {
            cameraY=camera.viewportHeight/2;
        }

        camera.position.set(cameraX,cameraY, 0);

        camera.update();

        renderer.setView(camera);

        renderer.render();

        renderer.getBatch().begin();

       // player.draw(renderer.getBatch());

        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        // batch.setProjectionMatrix(camera.combined);
        //camera.update();
        //renderer.setView(camera);
        //renderer.render();

        //button sizes
        menuButtonSprite.setSize(screenWidth/12,screenWidth/12);
        inventoryButtonSprite.setSize(screenWidth/12,screenWidth/12);
        //button positions
        menuButtonSprite.setPosition(screenWidth *11/12 - menuButtonSprite.getWidth()/4,screenHeight* 11/12 - menuButtonSprite.getHeight()/2);
        inventoryButtonSprite.setPosition(screenWidth *10/12 - inventoryButtonSprite.getWidth()/4,screenHeight*11/12 - inventoryButtonSprite.getHeight()/2);
        //

        batch.begin();
        //player.draw(renderer.getBatch());
        /*font.draw(batch, "blaaa", 50, 50);
        player2.draw(batch);*/

        menuButtonSprite.draw(batch);
        inventoryButtonSprite.draw(batch);

        batch.end();
    }


    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        batch.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }

   /* @Override
    public void initialize()
    {
    }*/

    @Override
    public void update()
    {
        if(player.getX() < 624&&player.getY()<1485&&player.getY()>1410&&player.getX()>510)
        {
            //Kris -- just put in to test InventoryState
            //gsm.openInventory(userCharacter);

            if(userCharacter.getCurrentHealth()<=0)
            {
                userCharacter.healAll();
            }
            if(player.direction==0)
            {
                gsm.startFight(userCharacter, "Loner");
            }
            else if(player.direction==1)
            {
                gsm.startFight(userCharacter, "Preppy");
            }
            else if(player.direction==2)
            {
                gsm.startFight(userCharacter, "Pledge");
            }
            else if(player.direction==3)
            {
                gsm.startFight(userCharacter, "Full Set");
            }
            player.setPosition(8 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 8) * player.getCollisionLayer().getTileHeight());
        }
        //else if(player.getY()>player.getCollisionLayer().getTileHeight()*(player.getCollisionLayer().getHeight()-2))
        //{
        //    gsm.openInventory(userCharacter);
        //    player.setPosition(8 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 8) * player.getCollisionLayer().getTileHeight());
        //}
    }

    //@Override
    //  public void draw() {}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if(screenX<Gdx.graphics.getWidth()*8/10 && screenY<inventoryButtonSprite.getHeight())
        {
            player.touchDown(screenX,screenY,screenWidth,screenHeight);
        }
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.touchUp(screenX,screenY,screenWidth,screenHeight);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.touchDragged(screenX,screenY,screenWidth,screenHeight);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        if(y < screenHeight/5)
        {
            if(x > inventoryButtonSprite.getX() && x < menuButtonSprite.getX())
            {
                gsm.openInventory(userCharacter);
                //gsm.StartWinState(userCharacter,15);
            }
            if(x > menuButtonSprite.getX())
            {
                gsm.openMenu(userCharacter);
                //gsm.changeState(1);
            }
            else
            {
                return false;
            }
            return true;
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