package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Daniel on 02/02/2015.
 */
public class fightTest
{



    Texture img;
    fightTest()
    {
        img = new Texture("badlogic.jpg");

    }


    public void render(SpriteBatch batch,int x, int y)
    {
        //player-enemy-enemy
        //
        batch.begin();

        /*batch.draw(img, Gdx.graphics.getWidth() / 8,
                Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 8,
                Gdx.graphics.getHeight() / 2);
        batch.draw(img,4*Gdx.graphics.getWidth()/8,
                (int)(1.1*Gdx.graphics.getHeight()/4),Gdx.graphics.getWidth()/7,
                Gdx.graphics.getHeight()/2);*/
        batch.draw(img, x-(Gdx.graphics.getWidth() / 8),y-(Gdx.graphics.getHeight()/8), Gdx.graphics.getWidth() / 4,
                Gdx.graphics.getHeight() / 4);

        batch.end();
    }



}
