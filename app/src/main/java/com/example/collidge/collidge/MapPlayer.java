package com.example.collidge.collidge;

/**
 * Created by Kris on 30-Jan-15.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MapPlayer extends Sprite implements InputProcessor
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
		/*//applies gravity
		velocity.y -= gravity*delta;
		//clamp velocity
		if(velocity.y > speed)
		{
			velocity.y = speed;
		}
		else if(velocity.y < speed)
		{
			velocity.y = -speed;
		}*/

        float oldX = getX(), oldY = getY(), tilewidth = collisionlayer.getTileWidth(), tileheight = collisionlayer.getTileHeight();
        boolean collisionX = false, collisionY = false;

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

    @Override
    public boolean keyDown(int keycode)
    {
        switch(keycode)
        {
            case Keys.W:
                velocity.y = speed;
                break;
            case Keys.A:
                velocity.x = -speed;
                break;
            case Keys.D:
                velocity.x = speed;
                break;
            case Keys.S:
                velocity.y = -speed;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch(keycode)
        {
            case Keys.W:
                velocity.y = 0;
                break;
            case Keys.A:
                velocity.x = 0;
                break;
            case Keys.D:
                velocity.x = 0;
                break;
            case Keys.S:
                velocity.y = 0;
                break;

        }
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        // TODO Auto-generated method stub
        return false;
    }





}
