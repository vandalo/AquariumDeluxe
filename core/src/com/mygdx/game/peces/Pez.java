package com.mygdx.game.peces;


import java.util.Random;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public abstract class Pez extends Sprite {
	
	private Vector2 velocity = new Vector2();
	private float spriteW, spriteH;
	public boolean alive, stopMoving, usingAI;
	protected boolean collisionX, collisionY;
	public Body body;
	final short PECES = 0x1;    // 0001
    final short COMIDA = 0x1 << 1; // 0010 or 0x2 in hex
    final short ENEMIGO = 0x1 << 2; // 0010 or 0x2 in hex
    protected int tamanoPez;
    protected float tiempoDesdeComida;
    private Random ran;
    private float dirX, dirY;
    private int steps;
    private boolean collided;
    protected float xSpeed, ySpeed;
    Sprite spriteDer, spriteIzq;
	
	public Pez(Sprite sprite, World world, Sprite derecha, Sprite izquierda){
		super(sprite);
		alive = true;
		stopMoving = true;
		spriteW = getWidth(); 
		spriteH = getHeight(); 
		collided = false;
		tamanoPez = 0;
		tiempoDesdeComida = 0f;
		ran = new Random();
		steps = 0;
		dirX = 10;
		dirY = 5;
		collided = false;
		spriteDer = derecha;
		spriteIzq = izquierda;
	}
	
	public abstract void initBody(World world, int playerNum);
	

	public void draw(Batch spriteBatch){
		update(Gdx.graphics.getDeltaTime());
		if (alive) {
			super.draw(spriteBatch);
		}
	}


	protected void update(float deltaTime) {		
		choseDirection();
		collisionX = false;
		collisionY = false;
		//TODO 
		//SEGUN LA DIRECCION K LLEVE, COMPROBAR LIMITES PANTALLA
		//SI ESTA CERCA LIMITES, CAMBIAR IMAGEN Y DIRECCION Y VELOCIDAD
		
		//CALCULATE VELOCITY AND COLLISIONS FOR AI
		//COLISIONES HORIZONTALES
		//if (!collided){
			if (body.getLinearVelocity().x < 0) {
				collisionX = collidesLeft(getX() + velocity.x * deltaTime, getY());
	
			} else if (body.getLinearVelocity().x > 0) {
				//Top Right
				collisionX = collidesRight(getX() + velocity.x * deltaTime, getY());
			}
			if (collisionX) {
				collided = true;
				if (body.getLinearVelocity().x < 0) {
					
					//HA CHOCADO CON LA IZQUIERDA, VAMOS HACIA LA DERECHA
					body.setLinearVelocity(-body.getLinearVelocity().x, body.getLinearVelocity().y);
					body.setTransform(body.getPosition().x + 1, body.getPosition().y, body.getAngle()); 
				}
				else {
					//HA CHOCADO CON LA DERECHA, VAMOS HACIA LA IZQUIERDA
					body.setLinearVelocity(-body.getLinearVelocity().x, body.getLinearVelocity().y);
					body.setTransform(body.getPosition().x - 1, body.getPosition().y, body.getAngle());
				}
			}
			float valX = (!collisionX) ? getX() + body.getLinearVelocity().x * deltaTime : getX();
			
			
			//COLISIONES VERTICALES
			if (body.getLinearVelocity().y < 0) {
				//Bottom left
				collisionY = collidesBottom(valX, getY() + body.getLinearVelocity().y * deltaTime);
			} else if (body.getLinearVelocity().y > 0) {
				//Top Left
				collisionY = collidesTop(valX, getY() + body.getLinearVelocity().y * deltaTime);
			}
			
			if (collisionY) {
				collided = true;
				if (body.getLinearVelocity().y < 0) {
					body.setLinearVelocity(body.getLinearVelocity().x, -body.getLinearVelocity().y);
					body.setTransform(body.getPosition().x, body.getPosition().y + 1, body.getAngle()); 
				}
				else {
					body.setLinearVelocity(body.getLinearVelocity().x, -body.getLinearVelocity().y);
					body.setTransform(body.getPosition().x, body.getPosition().y - 1, body.getAngle());
				}
			}
		//}
		
		//SET POSITION DE LA IMAGEN K ACOMPANA AL BODY
		if (body.getLinearVelocity().x < 0) set(spriteIzq);
		else set(spriteDer);
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}
	
	
	private void choseDirection() {
		//random horizontal
		if (steps % 360 == 0){
			dirX = (ran.nextFloat() > 0.5) ? 35 : -35;	
			body.setLinearVelocity(dirX, dirY);
		}
		//random vertical
		else if (steps % 180 == 0){
			dirY = ran.nextFloat();	
			if (dirY < 0.33) dirY = -20;
			else if (dirY < 0.66) dirY = 0;
			else dirY = 20;
			body.setLinearVelocity(dirX, dirY);
		}
		
		if (steps % 360000 == 0) steps = 0;
		steps++;
		
	}

	private boolean isCellBlocked(float x, float y){
		if (x < 5 || x > Gdx.graphics.getWidth() - 5 || y > Gdx.graphics.getHeight()-120 || y < 10) return true;
		return false;
	}
	
	
	private boolean collidesRight(float x, float y) {
        for(float step = 0; step < spriteH+2; step += spriteH/2)
                if(isCellBlocked(x + spriteW, y + step)) return true;
        return false;
	}
	 
	private boolean collidesLeft(float x, float y) {
        for(float step = 0; step < spriteH+2; step += spriteH/2)
                if(isCellBlocked(x, y + step)) return true;
        return false;
	}
	 
	private boolean collidesTop(float x, float y) {
        for(float step = 0; step < spriteW+2; step += spriteW/2)
                if(isCellBlocked(x + step, y + spriteH)) return true;
        return false;
	}
	 
	private boolean collidesBottom(float x, float y) {
        for(float step = 0; step < spriteW+2; step += spriteW/2)
                if(isCellBlocked(x + step, y)) return true;
        return false;
	}
}
/*
 * float speedNow = body.getLinearVelocity().len();
recentSpeed = 0.1 * speedNow + 0.9 * recentSpeed;
if ( recentSpeed < someThreshold )
    ... do something ...
/*
 * float speedNow = body.getLinearVelocity().len();
recentSpeed = 0.1 * speedNow + 0.9 * recentSpeed;
if ( recentSpeed < someThreshold )
    ... do something ...
    ----------------------
 */

