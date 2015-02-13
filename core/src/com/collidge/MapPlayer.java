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
        walkingAnimation[direction].update(Gdx.graphics.getDeltaTime());
     //   super.draw(spritebatch);

        spritebatch.draw(walkingAnimation[direction].getFrame(), getX(), getY());
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
        if(collisionX)
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
        if(collisionY)
        {
            setY(oldY);
            velocity.y = 0;
        }


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
     //   animation.up();
    }
    private void moveDown()
    {

        velocity.y = -speed;
       // animation.down();
    }
    private void moveLeft()
    {

        velocity.x = -speed;
        //animation.left();
    }
    private void moveRight()
    {

        velocity.x = speed;
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


        velocity.x=screenX-(width/2);
        velocity.y=-(screenY-(height)/2);


        velocity.y*=1.5;
        getDirection();

        return;

/*        if(screenX<(width/4))
        {

            moveLeft();

        }
        else if(screenX>((3*width)/4))
        {
            moveRight();
        }
        else if(screenY>(height/2))
        {
            moveDown();
        }
        else
        {
            moveUp();
        }
        return;*/
    }


    public boolean touchUp(int screenX, int screenY, int width, int height)
    {
        // TODO Auto-generated method stub
        stopMovement();
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int width, int height)
    {


        velocity.x=screenX-(width/2);
        velocity.y=-(screenY-(height/2));
        velocity.y*=1.5;
        getDirection();


        return true;
    }

    private void getDirection()
    {
        if(velocity.x==0||Math.abs(velocity.y)/Math.abs(velocity.x)>1)
        {
            if(velocity.y>0)
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
            if(velocity.x>0)
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