package com.collidge;

/**
 * Created by Kris on 30-Jan-15.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MapPlayer extends Sprite
{

    private Vector2 velocity = new Vector2(); //movement velocity
    private float speed = 60*2, gravity = 60*1.8f;
    private TiledMapTileLayer collisionlayer;
    private static int xleft, xright, ydown, yup;

    long startTime;
    long currentTime;

    int direction = UP;
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private Texture walkingTextures[];
   /* private TextureRegion walkingLeftRegions[];
    private TextureRegion walkingRightRegions[];
    private TextureRegion walkingUpRegions[];
    private TextureRegion walkingDownRegions[];*/
    private TextureRegion walkingRegions[][];
    private Animation walkingAnimation[];

    private Texture walkingLeftTexture;
    private TextureRegion[] walkingLeftFrames;
    private Animation walkingLeftAnimation;
    private Texture walkingRightTexture;
    private Animation walkingRightAnimation;
    private Texture walkingUpTexture;
    private Animation walkingUpAnimation;
    private Texture walkingDownTexture;
    private Animation walkingDownAnimation;


    /* Declan adding for movement in tiles */
    private float initX= 0;
    private float initY = 0;
    private float endX= 0;
    private float endY = 0;

    private int lastpoint;

    boolean stopped = false; // called on TouchUp is true
    boolean stopping = false;
    //boolean inTile = false; //Ensures is in Tile when stopped

    int tileID; //tile sprite ends up in

    public MapPlayer(Sprite sprite, TiledMapTileLayer collisionlayer)
    {

        super(sprite);
        walkingTextures = new Texture[4];
        walkingTextures[UP] = new Texture("walking_back_animation.png");
        walkingTextures[DOWN] = new Texture("walking_forward_animation.png");
        walkingTextures[RIGHT] = new Texture("walking_right_animation.png");
        walkingTextures[LEFT] = new Texture("walking_left_animation.png");

        walkingAnimation = new Animation[4];
        walkingRegions = new TextureRegion[4][4];
        for(int j = 0; j < 4; j++) {
            TextureRegion[][] region = TextureRegion.split(walkingTextures[j], 32, 32);
            for (int i = 0; i < 4; i++) {
                walkingRegions[j][i] = region[0][i];
            }
        }

        for (int i = 0; i < 4; i++) {
            walkingAnimation[i] = new Animation(walkingRegions[i],.2f);
        }

        startTime = System.currentTimeMillis();
        currentTime = startTime;
        this.collisionlayer = collisionlayer;

    }

    @Override
    public void draw(Batch spritebatch)
    {
        update(Gdx.graphics.getDeltaTime());
        walkingLeftAnimation.update(Gdx.graphics.getDeltaTime());
        super.draw(spritebatch);
    }

    public void draw2(Batch spritebatch, int blaa) {
        update(Gdx.graphics.getDeltaTime());
       /* currentTime = System.currentTimeMillis() - startTime;
        if (currentTime > 3000)
        {
            currentTime = 0;
            direction++;
            if (direction >= 4) direction = 0;
        }*/

     //   super.draw(spritebatch);

        if(velocity.x==0&&velocity.y==0)
        {
            walkingAnimation[direction].stop();
            if(direction!=RIGHT)
            {
                walkingAnimation[direction].setCurrentFrame(1);
            }
        }
        else
        {
            walkingAnimation[direction].play();
            walkingAnimation[direction].setDelay((collisionlayer.getTileWidth()/velocity.len())/2);
            walkingAnimation[direction].update(Gdx.graphics.getDeltaTime());
        }

        spritebatch.draw(walkingAnimation[direction].getFrame(), getX(), getY(),collisionlayer.getTileWidth(),collisionlayer.getTileHeight());
    }

    public void update(float delta)
    {
        float oldX = getX(), oldY = getY(), tilewidth = collisionlayer.getTileWidth(), tileheight = collisionlayer.getTileHeight();
        boolean collisionX = false, collisionY = false, fight = false;

        setX(getX() + velocity.x*delta);

        if(velocity.x < 0)
        {
            //top left
            collisionX = collisionlayer.getCell((int) (getX() / tilewidth), (int) ((getY() + getHeight()) / tileheight)).getTile().getProperties().containsKey("blocked");
            //middle left
            if(!collisionX)
                collisionX = collisionlayer.getCell((int) (getX() / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
            //bottom left
            if(!collisionX)
                collisionX = collisionlayer.getCell((int) (getX() / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
        }

        else if(velocity.x > 0)
        {
            //top right
            collisionX = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) ((getY() + getHeight()) / tileheight)).getTile().getProperties().containsKey("blocked");
            //middle right
            if(!collisionX)
                collisionX = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
            //bottom right
            if(!collisionX)
                collisionX = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
        }

        //react to x collision
        if(collisionX||getX()>(collisionlayer.getWidth()-1)*collisionlayer.getTileWidth()||getX()<0)
        {
            setX(oldX);
            velocity.x = 0;
        }

        setY(getY() + velocity.y*delta);

        if(velocity.y < 0)
        {
            //bottom left
            collisionY = collisionlayer.getCell((int) (getX() / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
            //middle bottom
            if(!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth() / 2) / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
            //bottom right
            if(!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
        }

        else if(velocity.y > 0)
        {
            //top left
            collisionY = collisionlayer.getCell((int) (getX() / tilewidth), (int) ((getY() + getHeight()) / tileheight)).getTile().getProperties().containsKey("blocked");
            //top middle
            if(!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth() / 2) / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
            if(!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) ((getY() + getHeight()) / tileheight)).getTile().getProperties().containsKey("blocked");
        }

        //react to y collision
        if(collisionY||getY()>(collisionlayer.getHeight()-1)*collisionlayer.getTileHeight()||getY()<0)
        {
            setY(oldY);
            velocity.y = 0;
        }


        if(stopped == true && (Math.abs(velocity.x) > 0 || Math.abs(velocity.y) > 0) && stopping == false) {
            stopping = true;
            if ( direction == RIGHT) {
                tileID = (int) (getX() / tilewidth);
                tileID = tileID + 1; //Sets tile to aim towards
            }
            if (direction == LEFT) {
                tileID = (int) (getX() / tilewidth);
                //tileID = tileID; //Sets tile to aim towards
            }
            if (direction == UP) {
                tileID = (int) (getY() / tileheight);
                tileID = tileID + 1; //Sets tile to aim towards
            }
            if (direction == DOWN) {
                tileID = (int) (getY() / tileheight);
            }
        }

        //Once stopping sequence initiated
        if(stopping) {
            if (direction == RIGHT) {
                lastpoint = (int) getX();
                if (lastpoint >= Math.abs(tileID * tilewidth)) {
                    stopMovement();
                    stopping = false;
                    //Wait until past this point
                }
            }
            if (direction == LEFT) {
                lastpoint = (int) getX();
                if (lastpoint <= Math.abs(tileID * tilewidth)) {
                    stopMovement();
                    stopping = false;
                    //Wait until past this point
                }
            }
            if (direction == UP) {
                lastpoint = (int) getY();
                if (lastpoint >= Math.abs(tileID * tileheight)) {
                    stopMovement();
                    stopping = false;
                    //Wait until past this point
                }
            }
            else {
                lastpoint = (int) getY();
                if (lastpoint <= Math.abs(tileID * tileheight)) {
                    stopMovement();
                    stopping = false;
                    //Wait until past this point
                }

            }

        }
        else stopped = false;


    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

	/*public float getGravity()
	{
		return gravity;
	}*/

	/*public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}*/

    public TiledMapTileLayer getCollisionLayer()
    {
        return collisionlayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionlayer)
    {
        this.collisionlayer = collisionlayer;
    }

    private void moveUp()
    {
        velocity.y = speed;
        velocity.x = 0;
     //   animation.up();
    }
    private void moveDown()
    {

        velocity.y = -speed;
        velocity.x = 0;
       // animation.down();
    }
    private void moveLeft()
    {

        velocity.x = -speed;
        velocity.y = 0;

        //animation.left();

    }
    private void moveRight()
    {

        velocity.x = speed;
        velocity.y = 0;

        //animation.right();
    }
    private void stopMovement()
    {
        velocity.y = 0;
        velocity.x = 0;
    //    animation.stopped();
    }





    public void touchDown(int screenX, int screenY, int width, int height)
    {

        float xForCalculation = ((screenX-(width/2))/(float)width);
        float yForCalculation = ((-(screenY-(height/2)))/(float)height);
        getDirection(xForCalculation, yForCalculation);

        if(direction == LEFT)
        {
            moveLeft();
        }
        else if(direction == RIGHT)
        {
            moveRight();
        }
        else if(direction == DOWN)
        {
            moveDown();
        }
        else
        {
            moveUp();

        }
        return;

    }


    public boolean touchUp(int screenX, int screenY, int width, int height)
    {
        // TODO Auto-generated method stub
        stopped = true;
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int width, int height)
    {


        //velocity.x=500*((screenX-(width/2))/(float)width);
        //velocity.y=500*((-(screenY-(height/2)))/(float)height);
        //getDirection();


        return true;
    }


    private void getDirection(float x, float y)
    {
        if(Math.abs(y)/Math.abs(x)>1)
        {
            if(y>0)
            {
                direction=UP;
            }
            else
            {
                direction=DOWN;
            }
        }
        else
        {
            if(x>0)
            {
                direction=RIGHT;
            }
            else
            {
                direction=LEFT;
            }
        }
    }
}