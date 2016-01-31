package com.mygdx.game.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.entities.Comida;
import com.mygdx.game.entities.Pez;
import com.mygdx.game.partidas.testGame;

public class ContListener implements com.badlogic.gdx.physics.box2d.ContactListener{
	private testGame game;
	public ContListener(testGame game){
		this.game = game;
	}
	
	@Override
    public void beginContact(Contact contact) {
    	contact.setRestitution(0);
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    	Pez pez = null;
    	if (contact.getFixtureA().getBody().getUserData() instanceof Pez) {
    		pez = (Pez) contact.getFixtureA().getBody().getUserData();
		}
    	else if (contact.getFixtureB().getBody().getUserData() instanceof Pez) {
    		pez = (Pez) contact.getFixtureB().getBody().getUserData();
    		
    	}   
    	if (pez.aliveShown || pez.tiempoDesdeComida < Pez.TIME_HASTA_HAMBRE/2) contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    	Pez pez = null;
    	Comida comida = null;
    	if (contact.getFixtureA().getBody().getUserData() instanceof Pez) {
    		pez = (Pez) contact.getFixtureA().getBody().getUserData();
    		//if (contact.getFixtureB().getBody().getUserData() instanceof Comida) {
    			comida = (Comida) contact.getFixtureB().getBody().getUserData();
    			if (!game.bodiesToDestroy.contains(comida.body, true))
    				game.bodiesToDestroy.add(comida.body);
    			game.numComidasActual--;
    			game.comidas.removeValue(comida, true);
    			pez.tiempoDesdeComida = 0;
    			comida.alive = false;
    			pez.steps = 0;
    		//}
		}
    	else if (contact.getFixtureA().getBody().getUserData() instanceof Comida){
    		comida = (Comida) contact.getFixtureA().getBody().getUserData();
    		//if (contact.getFixtureB().getBody().getUserData() instanceof Pez){
	    		pez = (Pez) contact.getFixtureB().getBody().getUserData();
	    		if (!game.bodiesToDestroy.contains(comida.body, true))
    				game.bodiesToDestroy.add(comida.body);
	    		game.numComidasActual--;
	    		game.comidas.removeValue(comida, true);
	    		pez.tiempoDesdeComida = 0;
	    		comida.alive = false;
	    		pez.steps = 0;
    		//}
    	}
    }
}
