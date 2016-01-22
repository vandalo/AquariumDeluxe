package com.mygdx.game.entities;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;

import sun.rmi.runtime.Log;

public abstract class Pez extends Sprite {
	
	private Vector2 velocity = new Vector2();
	private float spriteW, spriteH;
	public boolean alive, aliveShown;
	protected boolean collisionX, collisionY;
	public Body body;
	final short PECES = 0x1;    // 0001
    final short COMIDA = 0x1 << 1; // 0010 or 0x2 in hex
    final short ENEMIGO = 0x11 << 1; // 0010 or 0x2 in hex
    protected int tamanoPez;
    public float tiempoDesdeComida;
    protected Random ran;
    private float dirX, dirY;
    private int steps;
    protected float xSpeed, ySpeed;
    Sprite spriteDer, spriteIzq, spriteMuerto;
    private int randomSteep;
    protected int width, height;
    testGame game;
    protected int tiempoUltimaMoneda;
	
	public Pez(Sprite sprite, World world, testGame game, Sprite derecha, Sprite izquierda,
			Sprite muerto){
		super(sprite);
		alive = true;
		spriteW = getWidth(); 
		spriteH = getHeight(); 
		tamanoPez = 0;
		tiempoDesdeComida = 0f;
		ran = new Random();
		steps = 0;
		dirX = 35;
		dirY = 5;
		spriteDer = derecha;
		spriteIzq = izquierda;
		spriteMuerto = muerto;
		randomSteep = (int) (650 + ran.nextInt(100));
		if (randomSteep % 2 != 0) randomSteep++;
		width = Gdx.graphics.getWidth()*9/10;
		height = Gdx.graphics.getHeight()*3/4;
		aliveShown = false;
		this.game = game;
		tiempoUltimaMoneda = 100 + ran.nextInt(200);
	}
	
	public abstract void initBody(World world, int playerNum);
	public abstract void crearMoneda(World world);
	
	public void updateSizes(int width, int height){
		/*this.width = width * 5 / 10;
		this.height = height * 2/4;*/
	}

	public void draw(Batch spriteBatch){
		if (alive) {
			update(Gdx.graphics.getDeltaTime());
			super.draw(spriteBatch);
		}
	}


	protected void update(float deltaTime) {		
		choseDirection();
		collisionX = false;
		collisionY = false;
		tiempoDesdeComida += deltaTime;
		tiempoUltimaMoneda -= deltaTime;
		System.out.println(tiempoUltimaMoneda);
		crearMoneda(game.world);
		
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
				
				//SI EL PEZ MUERE BORRAMOS EL BODY
				if (aliveShown && collisionY) {
					body.setActive(false);
					alive = false;
					game.bodiesToDestroy.add(body);
				}
			} else if (body.getLinearVelocity().y > 0) {
				//Top Left
				collisionY = collidesTop(valX, getY() + body.getLinearVelocity().y * deltaTime);
			}
			
			if (collisionY) {
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
		
		if(tiempoDesdeComida > 10){
			set(spriteMuerto);
			aliveShown = true;
		}
		else if (body.getLinearVelocity().x < 0) set(spriteIzq);
		else set(spriteDer);
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}
	
	
	private void choseDirection() {
		if(alive && !aliveShown){
		//random horizontal
			if (steps % randomSteep == 0){
				dirX = (ran.nextFloat() > 0.65) ? -dirX : dirX;	
				body.setLinearVelocity(dirX, dirY);
			}
			//random vertical
			else if (steps % (randomSteep/3) == 0){
				dirY = ran.nextFloat();	
				if (dirY < 0.2) dirY = -20;
				else if(dirY < 0.4) dirY = -10;	
				else if (dirY < 0.6) dirY = 0;
				else if(dirY < 0.8) dirY = 10;
				else dirY = 20;
				body.setLinearVelocity(dirX, dirY);
			}
		}
		else if (aliveShown){
			body.setLinearVelocity(0, -15);
		}	
		if (steps % 360000 == 0) steps = 0;
		steps++;
	}

	private boolean isCellBlocked(float x, float y){
		if (x < 85 || (x > width) || (y > height) || y < 5){
			steps = 1; 
			return true;
		}
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


