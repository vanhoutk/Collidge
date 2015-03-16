package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Daniel on 20/01/2015.
 */
public class Attack
{


    public int moveExecute(int moveId)
    {
        if(moveId==0)
        {
            return basicAttack();
        }

        return 1;
    }

    private int basicAttack()
    {
        System.out.println("Damage was done");
        return 1;
    }

    public int getTarget(int moveId,Enemy[] enemies) {
        int target;
        target = 0;
        while (enemies[target].getDead()) {
            target++;
        }
        return target;
    }
}

