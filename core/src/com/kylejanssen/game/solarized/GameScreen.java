package com.kylejanssen.game.solarized;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

    private SolarizedGame game;
    private Player player;
    private OrthographicCamera camera;
    private TiledMap map;
    private TiledMapTileLayer collisionLayer;
    private OrthogonalTiledMapRenderer renderer;
    private ShapeRenderer shapeRenderer;

    private static final float SIZE_TILE = 32f;
    private static final float HEIGHT_VIEW = 18f * SIZE_TILE;
    private static final float WIDTH_VIEW = 32f * SIZE_TILE;
    private final float WIDTH_MAP;

    private static final float GRAVITY = -750f;
    private static final float FRICTION = -1500f;

    public GameScreen(SolarizedGame game) {
        this.game = game;

        player = new Player(4f * SIZE_TILE, 3f * SIZE_TILE);
        shapeRenderer = new ShapeRenderer();
        map = new TmxMapLoader().load("maps/solarized-1.tmx");
        collisionLayer = (TiledMapTileLayer)map.getLayers().get("Collision");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH_VIEW, HEIGHT_VIEW);
        camera.update();

        WIDTH_MAP = map.getProperties().get("width", Integer.class) * SIZE_TILE;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updatePlayer(delta);
        updateCamera();

        renderer.setView(camera);
        renderer.render();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(108 / 255f, 113 / 255f, 196 / 255f, 1);
        //shapeRenderer.circle(player.x, player.y, player.radius);
        shapeRenderer.rect(player.x, player.y, SIZE_TILE, SIZE_TILE);
        shapeRenderer.end();
    }

    private void updatePlayer(float delta) {

        float oldX = player.x;
        float oldY = player.y;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            reset();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.J)) {
            //player.y -= delta * Player.VELOCITY_MAX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.K)) {
            //player.y += delta * Player.VELOCITY_MAX;

            if (player.state != Player.PlayerState.JUMPING) {
                player.velocity.y = Player.VELOCITY_MAX;
            }

            player.state = Player.PlayerState.JUMPING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.H)) {
            //player.x -= delta * Player.VELOCITY_MAX;
            player.velocity.x = -Player.VELOCITY_MAX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.L)) {
            //player.x += delta * Player.VELOCITY_MAX;
            player.velocity.x = Player.VELOCITY_MAX;
        }

        // Move player x
        player.x += player.velocity.x * delta;

        if (collisionLayer.getCell(toCell(player.x), toCell(player.y)) != null) {
            player.x = oldX;
            player.velocity.x = 0f;
        }

        // Move player y
        player.y += player.velocity.y * delta;

        if (collisionLayer.getCell(toCell(player.x), toCell(player.y)) != null) {
            player.y = oldY;
            if (player.velocity.y < 0) {
                player.state = Player.PlayerState.STILL;
            }
            player.velocity.y = 0f;
        }

        // Here be dragons: this code clamps the players position within the bounds of the map.
        player.x = player.x < 0 ? 0 : player.x > WIDTH_MAP - SIZE_TILE ? WIDTH_MAP - SIZE_TILE : player.x;
        player.y = player.y < 0 ? 0 : player.y > HEIGHT_VIEW - SIZE_TILE ? HEIGHT_VIEW - SIZE_TILE : player.y;


        // Update velocity
        if (player.state == Player.PlayerState.JUMPING) {
            player.velocity.y += GRAVITY * delta;
        }

        if (player.velocity.x > 0) {
            player.velocity.x = Math.max(0, player.velocity.x + FRICTION * delta);
        } else {
            player.velocity.x = Math.min(0, player.velocity.x - FRICTION * delta);
        }

        if (player.velocity.isZero()) {
            player.state = Player.PlayerState.STILL;
        } else if (player.velocity.y != 0) {
            player.state = Player.PlayerState.JUMPING;
        } else {
            player.state = Player.PlayerState.RUNNING;
        }
    }

    private void updateCamera() {

        camera.position.x = player.x;

        float offset = WIDTH_VIEW / 2;

        if (camera.position.x < offset) {
            camera.position.x = offset;
        }

        if (camera.position.x > WIDTH_MAP - offset) {
            camera.position.x = WIDTH_MAP - offset;
        }

        camera.update();
    }

    private void reset() {
        player.x = 4 * SIZE_TILE;
        player.y = 3 * SIZE_TILE;
        player.velocity.x = 0f;
        player.velocity.y = 0f;
        player.state = Player.PlayerState.STILL;
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

    private static int toCell(float num) {
        return (int)(num / SIZE_TILE);
    }
}
