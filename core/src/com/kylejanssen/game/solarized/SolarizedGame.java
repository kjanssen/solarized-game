package com.kylejanssen.game.solarized;

import com.badlogic.gdx.Game;

public class SolarizedGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
