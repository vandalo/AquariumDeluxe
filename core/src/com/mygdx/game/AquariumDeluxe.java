package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AquariumDeluxe extends Game {
	public SpriteBatch batch;
	public BitmapFont font;	
	 
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new LogoScreen(this));
	}


	@Override
	public void render () {
		super.render();
	}
	
	public void resize (int width, int height) { 
		super.resize(width, height);
	}

	public void pause () { 
	}

    public void resume () {
    }

    public void dispose () { 
    	batch.dispose();
    	font.dispose();
    }
}
