package com.collidge;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Ben on 06/03/15.
 * animatedText class. I tried to keep abstraction as high as possible so the user only needs one function (returnString()
 * This function does all the animation
 */
public class AnimatedText {
    private String fullText;
    private String currentText;
    private int length;
    private int currentLetter;
    private boolean animationDone;

    private long startTime;
    private long currentTime;
    private int letterDelay;

    public AnimatedText(String string) {
        fullText = string;
        currentText = new String();
        length = fullText.length();
        currentLetter = 0;
        animationDone = false;
        letterDelay = 0; //miliseconds
        startTime = TimeUtils.millis();
        currentTime = 0;
    }

    public AnimatedText(String string, int delay) {
        this(string);
        letterDelay = delay;
    }

    String returnString() {
        if (animationDone) return currentText;

        if (TimeUtils.timeSinceMillis(startTime) > letterDelay) {
            startTime = TimeUtils.millis();
            if(currentLetter < length) {
                currentText += fullText.charAt(currentLetter++);
            }
            else animationDone = true;
        }
        else
        {
            System.out.println("Skip");
        }
        return currentText;
    }

    void finishAnimation() {
        currentText = fullText;
        animationDone = true;
    }

    public boolean pageDone() {
        return animationDone;
    }

    void start() {
        startTime = TimeUtils.millis();
        currentTime = startTime;
    }

    void setDelay(int delay) {
        letterDelay = delay;
    }

    int getLetterDelay() {
        return letterDelay;
    }
}

