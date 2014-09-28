package com.kylejanssen.game.solarized;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

    private SolarizedGame game;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private static final float HEIGHT_VIEW = 16f;
    private static final float WIDTH_VIEW = 28.4444f;
    private static final float SIZE_TILE = 32f;
    private float WIDTH_MAP;

    public GameScreen(SolarizedGame game) {
        this.game = game;

        map = new TmxMapLoader().load("maps/solarized-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / SIZE_TILE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH_VIEW, HEIGHT_VIEW);
        camera.update();

        WIDTH_MAP = map.getProperties().get("width", Integer.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= delta * 10;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += delta * 10;
        }

        float offset = WIDTH_VIEW / 2;

        if (camera.position.x < offset) {
            camera.position.x = offset;
        }

        if (camera.position.x > WIDTH_MAP - offset) {
            camera.position.x = WIDTH_MAP - offset;
        }

        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
