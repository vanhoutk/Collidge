package com.example.collidge.collidge;

/**
 * Created by Daniel on 22/01/2015.
 */
public class Combo
{
    Combo()
    {

    }
    double initiateCombo(int moveId)
    {
        switch(moveId)
        {
            case 0:
                return basicAttack();

            default:
                return 0;
        }
    }
    double basicAttack()
    {
        double skill=0;


        //TODO correct for the expected output of the swipe function
        return skill;

    }
    double swipe(int dx, int dy,int x1,int y1,int x2,int y2)
    {
        //TODO add in swipe code
        return 0;
    }

}

