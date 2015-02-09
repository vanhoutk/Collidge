package com.collidge;

/**
 * Created by Kris on 30-Jan-15.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MapPlayer extends Sprite
{

    private Vector2 velocity = new Vector2(); //movement velocity
    private float speed = 60*2, gravity = 60*1.8f;
    private TiledMapTileLayer collisionlayer;
    private static int xleft, xright, ydown, yup;

    public MapPlayer(Sprite sprite, TiledMapTileLayer collisionlayer)
    {
        super(sprite);
        this.collisionlayer = collisionlayer;
    }

    @Override
    public void draw(Batch spritebatch)
    {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spritebatch);
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
    }
    private void moveDown()
    {

        velocity.y = -speed;
    }
    private void moveLeft()
    {

        velocity.x = -speed;
    }
    private void moveRight()
    {

        velocity.x = speed;
    }
    private void stopMovement()
    {
        velocity.y = 0;
        velocity.x = 0;
    }





    public boolean touchDown(int screenX, int screenY, int height, int width)
    {

System.out.println(screenX);
        // TODO Auto-generated method stub
        if(screenX<(width/3.0))
        {
            System.out.println("Left");
            moveLeft();
        }
        else if(screenX>(width/1.5))
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
        return false;
    }


    public boolean touchUp(int screenX, int screenY, int height, int width)
    {
        // TODO Auto-generated method stub
        stopMovement();
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int height, int width)
    {
        return false;
    }







}
