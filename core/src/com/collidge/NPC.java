package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;


import java.util.Map;
import java.util.TimerTask;


/**
 * Created by ssakac on 09/03/15.
 */

public class NPC extends Sprite
{
    int direction = UP;
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    boolean xory;
    boolean positive;
    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2;
    private Boolean movrnot;
    private MapPlayer player;
    private float countDelta;
    private TiledMapTileLayer collisionlayer;
    public NPC(Sprite sprite, TiledMapTileLayer collisionlayer, boolean movrnoty, MapPlayer player, boolean xoryplane, boolean positive)
    {
        super(sprite);
        movrnot = movrnoty;
        xory = xoryplane;
        this.positive = positive;
        this.player = player;
        this.collisionlayer = collisionlayer;
        this.countDelta = 0;
        if(movrnot == true) {
            if(xory) {
                if (positive )this.velocity.x = speed;
                else velocity.x = -1 * speed;
            }
            else {
                if (positive) velocity.y = speed;
                else velocity.y = -speed;
            }
        }
    }

    @Override
    public void draw(Batch spritebatch)
    {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spritebatch);
    }

    public void draw2(Batch spritebatch, int blaa)
    {
        update(Gdx.graphics.getDeltaTime());
    }

    public void update(float delta)
    {
        float oldX = getX(), oldY = getY(), tilewidth = collisionlayer.getTileWidth(), tileheight = collisionlayer.getTileHeight();
        boolean collisionX = false, collisionY = false, fight = false;

        boolean move = true;
        if(move && movrnot) {
            if(xory) {
                if(positive ) velocity.x = speed;
                else velocity.x = -speed;
            }
            else {
                if (positive) velocity.y = speed;
                else velocity.y = -speed;
            }
        }

        if (velocity.x < 0)
        {
            //middle left
            if (!collisionX)
                collisionX = collisionlayer.getCell((int) (getX() / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
        }
        else if (velocity.x > 0)
        {
            //middle right
            if (!collisionX)
                collisionX = collisionlayer.getCell((int) ((getX() + getWidth()) / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
        }

        //react to x collision
        if (collisionX)
        {
            setX(oldX);
            if(velocity.x > 0)
            {
                velocity.x = -speed;
                positive = !positive;
            }
            else if(velocity.x < 0)
            {
                velocity.x = speed;
                positive = !positive;
            }
        }

        if (velocity.y < 0)
        {
            //middle bottom
            if (!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth() / 2) / tilewidth), (int) (getY() / tileheight)).getTile().getProperties().containsKey("blocked");
        }
        else if (velocity.y > 0) {
            //top middle
            if (!collisionY)
                collisionY = collisionlayer.getCell((int) ((getX() + getWidth() / 2) / tilewidth), (int) ((getY() + getHeight() / 2) / tileheight)).getTile().getProperties().containsKey("blocked");
            }

        //react to y collision
        if (collisionY)
        {
            setY(oldY);
            if(velocity.y > 0)
            {
                velocity.y *= -1;
                positive = !positive;
            }
            else if(velocity.y < 0)
            {
                velocity.y *= -1;
                positive = !positive;
            }
        }

        if(countDelta > 300f)
        {
            velocity.x = -velocity.x;
            countDelta = 0;
        }

        //check if colliding with player
        if(movrnot) {
            int npcTileX =(int) ((getX() +getWidth() / 2) / tilewidth);
            int npcTileY = (int)((getY() + getHeight() / 2)/ tileheight);
            int playerTileX = (int) ((player.getX() + player.getWidth() / 2)/ tilewidth);
            int playerTileY = (int) ((player.getY() + player.getHeight() / 2)/ tileheight);
            int differenceX = npcTileX - playerTileX;
            int differenceY = npcTileY - playerTileY;

            if (differenceX == 1 && differenceY == 0 && velocity.x < 0) {
                if(Math.abs(getX() - player.getX()) <= 32) move = false;
            }
            else if (differenceX == -1 && differenceY == 0 && velocity.x > 0) {
                if(Math.abs(getX() - player.getX()) <= 32) move = false;
            }
            if (differenceY == 1 && differenceX == 0 && velocity.y < 0) {
                if(Math.abs(getY() - player.getY()) <= 32) move = false;
            }
            else if (differenceY == -1 && differenceX == 0 && velocity.y > 0) {
                if(Math.abs(getY() - player.getY()) <= 32) move = false;
            }
        }

        if(getX() < 0 || getX() + getWidth() > collisionlayer.getWidth() * tilewidth) {
            velocity.x *= -1;
            positive = !positive;
        }
        if(getY() < 0 || getY() + getHeight() > collisionlayer.getHeight() * tileheight) {
            velocity.y *= -1;
            positive = !positive;
        }

        if (!move) {
            velocity.x = 0;
            velocity.y = 0;
        }

        setY(getY() + velocity.y * delta);
        setX(getX() + velocity.x * delta);

        countDelta++;
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


    public TiledMapTileLayer getCollisionLayer()
    {
        return collisionlayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionlayer)
    {
        this.collisionlayer = collisionlayer;
    }
}
