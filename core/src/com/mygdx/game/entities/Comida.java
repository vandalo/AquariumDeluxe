package com.mygdx.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.testGame;

public abstract class Comida extends Sprite {
	
	private float spriteW, spriteH;
	public boolean alive;
	public Body body;
	final short PECES = 0x1;    // 0001
    final short COMIDA = 0x1 << 1; // 0010 or 0x2 in hex
    final short ENEMIGO = 0x1 << 2; // 0010 or 0x2 in hex
    protected int poderComida;
    protected float xSpeed, ySpeed;
	
	public Comida(Sprite sprite, World world){
		super(sprite);
		alive = true;
		spriteW = getWidth(); 
		spriteH = getHeight(); 
		poderComida = 0;
	}
	
	public abstract void initBody(World world);
	

	public void draw(Batch spriteBatch){
		update(Gdx.graphics.getDeltaTime());
		if (alive) {
			super.draw(spriteBatch);
		}
	}


	protected void update(float deltaTime) {		
		//COLISIONES VERTICALES
		if(collidesBottom(getX(), getY() + body.getLinearVelocity().y * deltaTime)){
			alive = false;
			body.setActive(false);
			testGame.pintarComida = false;
		}
		
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}
	

	private boolean isCellBlocked(float x, float y){
		if (y < 1) return true;
		return false;
	}
	
	public abstract void setSpeed();
	
	 
	private boolean collidesBottom(float x, float y) {
        for(float step = 0; step < spriteW+2; step += spriteW/2)
                if(isCellBlocked(x + step, y)) return true;
        return false;
	}
}

