package com.example.collidge.collidge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Gary on 26/01/2015.
 */
public class Play implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private MapPlayer player;
    @Override
    public void show()
    {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("trinitymap1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        player = new MapPlayer(new Sprite(new Texture("player.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(8 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 8) * player.getCollisionLayer().getTileHeight());
        Gdx.input.setInputProcessor(player);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        camera.update();

        renderer.setView(camera);
        renderer.render();

        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }
}
