package com.kylejanssen.game.solarized;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

    private SolarizedGame game;
    private Player player;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ShapeRenderer shapeRenderer;

    private static final float HEIGHT_VIEW = 18f;
    private static final float WIDTH_VIEW = 32f;
    private static final float SIZE_TILE = 32f;
    private float WIDTH_MAP;

    public GameScreen(SolarizedGame game) {
        this.game = game;

        player = new Player(4f, 3f);
        shapeRenderer = new ShapeRenderer();
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

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.J)) {
            //camera.position.x += delta * 10;
            player.y -= delta * 10;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.K)) {
            //camera.position.x -= delta * 10;
            player.y += delta * 10;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.H)) {
            //camera.position.x -= delta * 10;
            player.x -= delta * 10;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.L)) {
            //camera.position.x += delta * 10;
            player.x += delta * 10;
        }

        camera.position.x = player.x;

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
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(108 / 255f, 113 / 255f, 196 / 255f, 1);
        //shapeRenderer.circle(player.x, player.y, player.radius);
        shapeRenderer.rect(player.x, player.y, 1f, 1f);
        shapeRenderer.end();
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
