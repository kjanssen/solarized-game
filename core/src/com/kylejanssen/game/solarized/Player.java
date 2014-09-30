package com.kylejanssen.game.solarized;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Circle {

    public enum PlayerState { RUNNING, JUMPING, STILL }

    public static final float VELOCITY_MAX = 500f;
    public Vector2 velocity;
    public PlayerState state;

    public Player(float x, float y) {
        super(x, y, 16f);
        velocity = new Vector2(0, 0);
        state = PlayerState.STILL;
    }
}
