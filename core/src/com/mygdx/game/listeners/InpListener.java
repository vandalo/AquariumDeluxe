package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.entities.ComidaBasic;
import com.mygdx.game.partidas.testGame;

public class InpListener extends InputAdapter{
	testGame game;
	//private float timeElapsed;
	
	public InpListener(testGame game){
		this.game = game;
	}
	
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		testGame.pintarComida = true;
		game.comidas.add(new ComidaBasic(game.entities.createSprite("ballBasicBlue"), game.world));
		game.comidas.get(0).setPosition(screenX - game.comidas.get(0).getWidth()/2, 
				Gdx.graphics.getHeight() - screenY - game.comidas.get(0).getHeight()/2);
		game.comidas.get(0).initBody(game.world);
		game.comidas.get(0).setSpeed();
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
