package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.testGame;
import com.mygdx.game.entities.ComidaBasic;

public class InpListener extends InputAdapter{
	testGame game;
	private float timeElapsed;
	
	public InpListener(testGame game){
		this.game = game;
	}
	
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		testGame.pintarComida = true;
		game.comida1 = new ComidaBasic(game.entities.createSprite("ballBasicBlue"), game.world);
		game.comida1.setPosition(screenX - game.comida1.getWidth()/2, Gdx.graphics.getHeight() - screenY - game.comida1.getHeight()/2);
		game.comida1.initBody(game.world);
		game.comida1.setSpeed();
		return true;
	}


	

/*
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (timeElapsed < 0.15f)timeElapsed += Gdx.graphics.getDeltaTime();
		else if (!game.touchingPad && pointer == 0) {
			moveCamera(screenX, screenY);     
			dragged = true;
		}
        return false;
	}*/

}
