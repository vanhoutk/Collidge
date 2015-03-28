package com.collidge;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Gary on 26/01/2015.
 */
public class Play extends GameState {

    private int cameraRotation=0,rotationSpeed=12;//use rotation speed to set the speed of rotation, note that it must be a factor of 360 to stop on the same spot it starts on
    Player userCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthographicCamera camera2;
    private MapPlayer player;
    int noOfNpcs = 16;
    private ArrayList<NPC> npcList;
    private Texture menuButton, inventoryButton;
    private Sprite menuButtonSprite, inventoryButtonSprite,nightMask,whiteSquare;
    private float ppx, ppy, px, py;
    private PopUpText popUps;
    private long enteringFight=0;
    private String fighting;
    long time;
    long seconds;
    long minutes;
    long hours;
    private SpriteBatch  batch;
    float nightOpacity,twilightOpacity;

    Play(GameStateManager gsm)
    {
        super(gsm);
        popUps=new PopUpText();


        userCharacter=gsm.user;

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

        player.setPosition(18 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 61) * player.getCollisionLayer().getTileHeight());
        npcList = new ArrayList<NPC>();
        NPC []npcArray = new NPC[noOfNpcs];

        npcArray[0] = new NPC(new Sprite(new Texture("shev.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, true);
        npcArray[0].setPosition(12 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 41) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[1] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[1].setPosition(11 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 59) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[2] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[2].setPosition(23 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 59) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[3] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, true);
        npcArray[3].setPosition(17 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 60) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[4] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[4].setPosition(15 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 47) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[5] = new NPC(new Sprite(new Texture("shev.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, true);
        npcArray[5].setPosition(18 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 48) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[6] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, false);
        npcArray[6].setPosition(19 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 53) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[7] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, true);
        npcArray[7].setPosition(12 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 41) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[8] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, true, false);
        npcArray[8].setPosition(6 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 34) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[9] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[9].setPosition(32 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 29) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[10] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[10].setPosition(21 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 23) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[11] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, true);
        npcArray[11].setPosition(10 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 26) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[12] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[12].setPosition(3 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 20) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[13] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), true, player, true, false);
        npcArray[13].setPosition(29 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 1) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[14] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[14].setPosition(25 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 18) * npcArray[0].getCollisionLayer().getTileHeight());
        npcArray[15] = new NPC(new Sprite(new Texture("rpgman.png")), (TiledMapTileLayer) map.getLayers().get(0), false, player, false, false);
        npcArray[15].setPosition(10 * npcArray[0].getCollisionLayer().getTileWidth(), (npcArray[0].getCollisionLayer().getHeight() - 19) * npcArray[0].getCollisionLayer().getTileHeight());

        for (int i = 0; i < noOfNpcs; i++) {
            npcList.add(npcArray[i]);
        }

        //Adding buttons for inventory and menu to the map
        menuButton = new Texture("android-mobile.png");
        inventoryButton = new Texture("bagicon.png");
        menuButtonSprite = new Sprite(menuButton);
        inventoryButtonSprite = new Sprite(inventoryButton);
        menuButton=new Texture("nightMask.png");
        nightMask= new Sprite(menuButton);
        menuButton=new Texture("whiteSquare.png");
        whiteSquare=new Sprite(menuButton);
        nightMask.setSize(screenWidth,screenHeight);
        nightMask.setPosition(0,0);
        whiteSquare.setSize(screenWidth,screenHeight);
        whiteSquare.setPosition(0,0);

    }

    @Override
    public void initialize()
    {
        time = System.currentTimeMillis();
        seconds = (long)(time / 1000);
        minutes = seconds / 60;
        hours = minutes / 60;
       // hours%=24;
        hours=(seconds/3)%24;
        minutes%=60;
        seconds%=60;
        System.out.println(hours+": "+minutes+": "+seconds);
    }

    public void setMap(String mapFile,int positionX, int positionY)
    {
        TmxMapLoader loader = new TmxMapLoader();

        map = loader.load(mapFile);

        player.setPosition(positionX,positionY);
    }
    @Override
    public void draw()
    {
        if(TimeUtils.timeSinceMillis(enteringFight)>5000)
        {
            float cameraX = player.getX() + player.getWidth() / 2;
            float cameraY = player.getY() + player.getHeight() / 2;
            if ((cameraX + camera.viewportWidth / 2) > (player.getCollisionLayer().getTileWidth() * player.getCollisionLayer().getWidth()))
            {
                cameraX = (player.getCollisionLayer().getTileWidth() * player.getCollisionLayer().getWidth()) - (camera.viewportWidth / 2);
            } else if ((cameraX - camera.viewportWidth / 2) < 0)
            {
                cameraX = camera.viewportWidth / 2;
            }
            if ((cameraY + camera.viewportHeight / 2) > (player.getCollisionLayer().getTileHeight() * player.getCollisionLayer().getHeight()))
            {
                cameraY = (player.getCollisionLayer().getTileHeight() * player.getCollisionLayer().getHeight()) - (camera.viewportHeight / 2);
            } else if ((cameraY - camera.viewportHeight / 2) < 0)
            {
                cameraY = camera.viewportHeight / 2;
            }

            camera.position.set(cameraX, cameraY, 0);
        }

        else if(TimeUtils.timeSinceMillis(enteringFight)<2000)
        {
            camera.rotate(rotationSpeed); //Mobile rotation speed
            cameraRotation+=rotationSpeed;
            cameraRotation%=360;
        }
        if(TimeUtils.timeSinceMillis(enteringFight)>800&&cameraRotation==rotationSpeed)
        {
            enteringFight=0;
            cameraRotation=0;
            camera.setToOrtho(false,480,(480*(((float)Gdx.graphics.getHeight())/(float)Gdx.graphics.getWidth())));
            gsm.startFight(userCharacter,fighting);
        }
        camera.update();

        renderer.setView(camera);


// calls the collision and ground layers prior to loading the player and NPC models
        renderer.getBatch().begin();

        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("collision"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("ground"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("walls"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("front_fence"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("randoms_layer"));

        if(TimeUtils.timeSinceMillis(enteringFight)>3000)
        {
            player.draw(renderer.getBatch());
        }
        for(int i = 0; i < noOfNpcs; i++) {
            npcList.get(i).draw(renderer.getBatch());
        }

        //Loads the rest of the map on top of the NPC and Player models
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("back_fence"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree1"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree2_and_windows"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("roof"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("roof2"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tower1"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tower2"));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tower3"));
        player.drawoutline(renderer.getBatch());
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

        if(hours<7||hours>=22)
        {
            whiteSquare.setColor(Color.GRAY);
            twilightOpacity=.3f;
            if(hours>10)
            {
                nightOpacity = .95f * (1f-((26f-hours)/4f));
            }
            else
            {
                if(hours<2)
                {
                    nightOpacity = .95f * 1f-((float) (Math.abs((2f - hours) / 3f)));
                }
                else if(hours==2)
                {
                    nightOpacity=.95f;
                }
                else
                {
                    nightOpacity=.95f * 1f-(float) (Math.abs((2f - hours )/ 5f));
                }
            }

        }
        else
        {
            if(hours<12)
            {
                twilightOpacity = .3f * (1f-((float)(hours - 7)/4f));
            }
            else if(hours>=19)
            {
                twilightOpacity=.3f*(1f-((22f-hours)/3f));
            }
            else
            {
                twilightOpacity=0f;
            }
            nightOpacity=0f;
            whiteSquare.setColor(.98f, .84f, .65f,1f);
        }
        if(hours<12||hours>19)
        {
            whiteSquare.draw(batch, twilightOpacity);
            if(hours<=7||hours>=22)
            {
                nightMask.draw(batch, nightOpacity);
            }
        }


        //System.out.println(nightOpacity+"---"+twilightOpacity);

        //nightMask.draw(batch,1f);
        menuButtonSprite.draw(batch);
        inventoryButtonSprite.draw(batch);
        popUps.update();
        popUps.draw(batch);


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
        time = System.currentTimeMillis();
        seconds = (long)(time / 1000);
        minutes = seconds / 60;
        hours = minutes / 60;
        // hours%=24;
        hours=(seconds)%24;
        minutes%=60;
        seconds%=60;
        System.out.println(hours+"------"+twilightOpacity);

        if(player.getX() < 624&&player.getY()<1485&&player.getY()>1410&&player.getX()>510&&TimeUtils.timeSinceMillis(enteringFight)>3000)
        {
            //Kris -- just put in to test InventoryState
            //gsm.openInventory(userCharacter);


            if(userCharacter.getCurrentHealth()<=0)
            {
                userCharacter.healAll();
            }
            if(player.direction==0)
            {
                enteringFight=TimeUtils.millis();
                saveplay();
                fighting= "Loner";
                player.setPosition(getx(),gety()-10);
            }
            else if(player.direction==1)
            {
                enteringFight=TimeUtils.millis();
                saveplay();
                fighting="Preppy";
                player.setPosition(getx(),gety()+100);
            }
            else if(player.direction==2)
            {
                enteringFight=TimeUtils.millis();
                saveplay();
                fighting="Pledge";
                player.setPosition(getx()+100,gety());
            }
            else if(player.direction==3)
            {
                enteringFight=TimeUtils.millis();
                saveplay();
                fighting= "Full Set";
                player.setPosition(getx()-100,gety());
            }

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
        if(screenX>Gdx.graphics.getWidth()*8/10  && screenY>inventoryButtonSprite.getHeight())
        {
            player.touchDown(100,100,100,100);

        }
        else player.touchDown(screenX, screenY, screenWidth, screenHeight);


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

    public void saveplay()
    {
       setx(player.getX());
       sety(player.getY());
    }

    public void setx(float x)
    {
      px = x;
    }
    public void sety(float y)
    {
     py = y;
    }

    public float getx()
    {
        return px;
    }

    public float gety()
    {
        return py;
    }

   /* public void loadplay()
    {
        float x = getx();
        float y = gety();
        Play play = new Play(gsm, x , y);
    }*/


    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        if(x>Gdx.graphics.getWidth()*.45&&x<Gdx.graphics.getWidth()*.55&
                y>Gdx.graphics.getHeight()*.45&&y<Gdx.graphics.getHeight()*.55)
        {

            popUps.Add("Stop poking me!",.45f,.55f,0f,.2f, Color.WHITE,50);
        }
        if(y < screenHeight/5)
        {
            if(x > inventoryButtonSprite.getX() && x < menuButtonSprite.getX())
            {
                saveplay();
                gsm.openInventory(userCharacter);
               // gsm.StartDeathState(userCharacter);
                player.setPosition(getx(), gety());
            }
            if(x > menuButtonSprite.getX())
            {
                saveplay();
                gsm.openMenu(userCharacter);
                player.setPosition(getx(), gety());
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
