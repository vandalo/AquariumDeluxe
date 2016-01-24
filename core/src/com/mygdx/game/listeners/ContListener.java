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
    	if (pez.aliveShown || pez.tiempoDesdeComida < pez.TIME_HASTA_HAMBRE/2) contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    	Pez pez = null;
    	Comida comida = null;
    	boolean hayComida = false;
    	if (contact.getFixtureA().getBody().getUserData() instanceof Pez) {
    		pez = (Pez) contact.getFixtureA().getBody().getUserData();
    		comida = (Comida) contact.getFixtureB().getBody().getUserData();
    		game.bodiesToDestroy.add(contact.getFixtureB().getBody());
    		hayComida = true;
		}
    	else if (contact.getFixtureA().getBody().getUserData() instanceof Comida){
    		comida = (Comida) contact.getFixtureA().getBody().getUserData();
    		game.bodiesToDestroy.add(contact.getFixtureA().getBody());
    		pez = (Pez) contact.getFixtureB().getBody().getUserData();
    		hayComida = true;
    	}
    	if (hayComida){
    		pez.tiempoDesdeComida = 0;
    		comida.alive = false;
    		game.numComidasActual--;
    		pez.steps = 0;
    		game.comidas.removeValue(comida, true);
    	}
    	
    }
}
