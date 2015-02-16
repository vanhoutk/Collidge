package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sive on 10-Feb-15.
 */
public class Animation {

    private TextureRegion[] frames; //take in a array of textures and gives back the right texture region
    private Texture texture;
    private float time; //current time
    private float delay; //time between each frame
    private int currentFrame;
    private int timesPlayed;

    public Animation()
    {
        frames = new TextureRegion[3];
    }

    public Animation(TextureRegion[] frames)
    {
        this(frames, 1 / 12f);
        //no delay, just calls other constructor.
    }

    public Animation(TextureRegion[] frames, float delay)
    {
        setFrames(frames, delay);

    }

    public void setFrames(TextureRegion[] frames, float delay)
    {
        //resets with new set of frames
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
        timesPlayed =  0;

    }

    public void update(float dt)
    {
        if(delay <= 0) return;
        time += dt;
        while(time >= delay)
        {
            step();
        }
    }

    public void step()
    {
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length)
        {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame()
    {
        return frames[currentFrame];
    }

    public int getTimesPlayed()
    {
        return timesPlayed;
    }

    public void stopped()
    {
        texture = new Texture("player.png");
    }
}
