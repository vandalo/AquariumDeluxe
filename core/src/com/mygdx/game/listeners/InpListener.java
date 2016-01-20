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
		int i = game.numComidasActual;
		if (i < game.numComidasMax){
			ComidaBasic cb = new ComidaBasic(game.entities.createSprite("ballBasicBlue"), game.world, game);
			game.comidas.add(cb);
			i = game.comidas.indexOf(cb, true);
			game.comidas.get(i).setPosition(screenX - game.comidas.get(i).getWidth()/2, 
					Gdx.graphics.getHeight() - screenY - game.comidas.get(i).getHeight()/2);
			game.comidas.get(i).initBody(game.world);
			game.comidas.get(i).setSpeed();
			game.numComidasActual++;
		}

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
