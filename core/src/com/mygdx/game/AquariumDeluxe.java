package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Primera classe que se llama, solo hace setScreen al Logo de NoxerGames
public class AquariumDeluxe extends Game {
	public SpriteBatch batch;
	public BitmapFont font;	
	public static int height, width;
	 
	@Override
	public void create () {
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
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
