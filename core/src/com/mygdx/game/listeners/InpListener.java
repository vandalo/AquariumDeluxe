package com.mygdx.game.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
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
			Vector3 v = game.camera.unproject(new Vector3((float)screenX,(float)screenY,0));
			ComidaBasic cb = new ComidaBasic(game.entities.createSprite("ballDefenderPurple"), game.world, game);
			game.comidas.add(cb);
			i = game.comidas.indexOf(cb, true);
			game.comidas.get(i).setPosition(v.x - game.comidas.get(i).getWidth()/2, 
					v.y - game.comidas.get(i).getHeight()/2);
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
