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
    public boolean paused;

    public Animation(TextureRegion[] Frames)
    {
        this(Frames, 1 / 12f);
        //no delay, just calls other constructor.
    }

    public Animation(TextureRegion[] Frames, float Delay)
    {
        setFrames(Frames, Delay);
    }

    public void setFrames(TextureRegion[] Frames, float Delay)
    {
        //resets with new set of frames
        this.frames = Frames;
        this.delay = Delay;
        time = 0;
        currentFrame = 0;
        timesPlayed =  0;
    }

    public void pause()
    {
        paused=false;
    }

    public void update(float dt)
    {
        if(!paused)
        {
            if (delay <= 0) return;
            time += dt;
            while (time >= delay)
            {
                step();
            }
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

    public void setDelay(float Delay)
    {
        delay=Delay;
    }

    public void stop()
    {
        paused=true;
        currentFrame=0;
    }

    public void setCurrentFrame(int i)
    {
        if(i<frames.length)
        {
            currentFrame = i;
        }
        else
        {
            System.out.println("Invalid frame accessed");
        }
    }

    public void play()
    {
        paused=false;
    }

    public TextureRegion getFrame()
    {
        return frames[currentFrame];
    }

    public int getNumberFrames()
    {
        return frames.length;
    }
    public int getCurrentFrameNum()
    {
        return currentFrame;
    }
    public int getTimesPlayed()
    {
        return timesPlayed;
    }
}
