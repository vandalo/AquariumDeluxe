package com.mygdx.game.subclasses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBar extends Actor {
/*
    private NinePatchDrawable loadingBarBackground;

    private NinePatchDrawable loadingBar;

    public HealthBar() {
        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"));
        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 6, 6);
        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 6, 6);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
    }
*/
    @Override
    public void draw(Batch batch, float parentAlpha) {
   /*     float progress = 0.9f;

        loadingBarBackground.draw(batch, 100, 30, 25, 10);
        loadingBar.draw(batch, 100, 30, progress * 25, 10);
        
        loadingBarBackground.draw(batch, 50, 30, 25, 10);
        loadingBar.draw(batch, 50, 30, progress * 25, 10);*/
    }
}