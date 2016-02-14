package com.mygdx.game.listeners;


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
			if(v.x > 85 && v.y < game.height - 20){
				ComidaBasic cb = new ComidaBasic(game.spriteComidas.get(0), game.world, game);
				game.comidas.add(cb);
				i = game.comidas.indexOf(cb, true);
				game.comidas.get(i).setPosition(v.x - game.comidas.get(i).getWidth()/2, 
						v.y - game.comidas.get(i).getHeight()/2);
				game.comidas.get(i).initBody(game.world);
				game.comidas.get(i).setSpeed();
				game.numComidasActual++;
			}
		}

		return true;
	}
	
	
	/*@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int i = game.numComidasActual;
		if (i < game.numComidasMax){
			//Vector3 v = game.camera.unproject(new Vector3((float)screenX,(float)screenY,0));
			if(screenX > 85 && screenY < game.height - 20){
				ComidaBasic cb = new ComidaBasic(game.spriteComidas.get(0), game.world, game);
				game.comidas.add(cb);
				i = game.comidas.indexOf(cb, true);
				game.comidas.get(i).setPosition(screenX - game.comidas.get(i).getWidth()/2, 
						480 - screenY - game.comidas.get(i).getHeight()/2);
				game.comidas.get(i).initBody(game.world);
				game.comidas.get(i).setSpeed();
				game.numComidasActual++;
			}
		}

		return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int i = game.numComidasActual;
		if (i < game.numComidasMax){
			//Vector3 v = game.camera.unproject(new Vector3((float)screenX,(float)screenY,0));
			//System.out.println("v.x: " + v.x + ". v.y: "+ v.y + ". game height: " + game.height);
			if(screenX > 85 && screenY < Gdx.graphics.getHeight() - 50){
				ComidaBasic cb = new ComidaBasic(game.spriteComidas.get(0), game.world, game);
				game.comidas.add(cb);
				i = game.comidas.indexOf(cb, true);
				game.comidas.get(i).setPosition(screenX - game.comidas.get(i).getWidth()/2, AquariumDeluxe.height -
						screenY - game.comidas.get(i).getHeight()/2);
				game.comidas.get(i).initBody(game.world);
				game.comidas.get(i).setSpeed();
				game.numComidasActual++;
			}
		}

		return true;
	}*/


	

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
