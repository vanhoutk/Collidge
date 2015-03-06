package com.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Ben on 06/03/2015.
 * Class for any message to be displayed on the textbox
 */
public class TextBox extends BitmapFont {
    //libGDX data
    private BitmapFont textBoxFont;
    private Sprite textBoxSprite;
    private Sprite backButtonSprite;
    private Texture arrowDownTexture;
    private SpriteBatch batch;
    private float x, y;
    private float textBoxWidth;
    private float textBoxHeight;
    private boolean active = true;

    //text data
    private String fullText;
    private ArrayList<String> pages;
    private int currentPage = 0;
    private static final int noOfLines = 5;

    public TextBox() {
        pages = new ArrayList<String>();

        x = (1/5.0f) * Gdx.graphics.getWidth();
        y = 0.0f;
        textBoxWidth = Gdx.graphics.getWidth() * (5 / 8.0f);
        textBoxHeight = Gdx.graphics.getHeight() * (3 / 8.0f);
        textBoxFont = new BitmapFont();
        textBoxFont.setColor(Color.WHITE);
        //textBoxFont.scale(1.0f);
        arrowDownTexture = new Texture("arrowBeige_down.png");
        Texture exitTexture = new Texture("Actions-arrow-right-icon.png");
        backButtonSprite = new Sprite(exitTexture);
        backButtonSprite.setScale(0.6f);
        backButtonSprite.setPosition(x + textBoxWidth - (4 * Gdx.graphics.getWidth() / 25.0f), y + (Gdx.graphics.getHeight() / 140));
        Texture textBoxTexture = new Texture("panel_beige.png");;
        textBoxSprite = new Sprite(textBoxTexture);
        textBoxSprite.setSize(textBoxWidth, textBoxHeight);
        textBoxSprite.setPosition(x,y);
        textBoxSprite.setAlpha(.9f);

        batch = new SpriteBatch();
    }

    public void draw() {
        if (active) {
            batch.begin();
            textBoxSprite.draw(batch);
            float xPadding = Gdx.graphics.getWidth() / 25.0f;
            float yPadding = Gdx.graphics.getHeight() / 20.0f;
            textBoxFont.drawWrapped(batch, pages.get(currentPage), textBoxSprite.getX() + xPadding, textBoxSprite.getY() + textBoxSprite.getHeight() - yPadding, textBoxWidth - xPadding * 1.8f);
            if(currentPage < pages.size() - 1) batch.draw(arrowDownTexture,textBoxSprite.getX() + textBoxWidth - 2 * xPadding , textBoxSprite.getY() + yPadding);
            else if(currentPage == pages.size() - 1) backButtonSprite.draw(batch);
            batch.end();
        }
    }

    public void turnPage() {
        if(currentPage < pages.size() - 1) currentPage++;
        else active = false;
    }

    /*
    ----------------GETTERS AND SETTERS----------------------
     */

    public String getText() {
        return fullText;
    }

    public void setText(String string) {
        fullText = string;
        currentPage = 0;
        pages.clear();
        final int pageLetterCount = 80;

        int noOfPages = fullText.length() / pageLetterCount;
        boolean remainderPage = false;
        int remainder = fullText.length() - (noOfPages * pageLetterCount);
        if (remainder > 0) {
            noOfPages++;
            remainderPage = true;
        }

        int currentLetter = 0;
        for (int page = 0; page < noOfPages; page++) {
            String pageToAdd = new String();
            if(page == noOfPages - 1 && remainderPage) {
                for (int i = currentLetter; i < currentLetter + remainder; i++) {
                    pageToAdd += fullText.charAt(i);
                }
            }
            else {
                for (int i = currentLetter; i < currentLetter + pageLetterCount; i++) {
                    pageToAdd += fullText.charAt(i);
                }
            }
            currentLetter += pageLetterCount;
            pages.add(pageToAdd);
        }
    }

    public void setOnOrOff(boolean trueOrFalse) {
        active = trueOrFalse;
    }

    public boolean isActive() {
        return active;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getTextBoxWidth() {
        return textBoxWidth;
    }

    public float getTextBoxHeight() {
        return textBoxHeight;
    }
}
