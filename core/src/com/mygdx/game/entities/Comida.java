package com.mygdx.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;

public abstract class Comida extends Sprite {
	
	private float spriteW, spriteH;
	public boolean alive;
	public Body body;
	final short PECES = 0x1;    // 0001
    final short COMIDA = 0x1 << 1; // 0010 or 0x2 in hex
    final short ENEMIGO = 0x1 << 2; // 0010 or 0x2 in hex
    protected int poderComida, estado; //activo 1, inactivo 0
    protected float xSpeed, ySpeed;
    protected int width, height;
    testGame game;
	
	public Comida(Sprite sprite, World world, testGame game){
		super(sprite);
		alive = true;
		poderComida = 0;
		this.game = game;
		setSize(game.width/20, game.width/20);
		spriteW = getWidth(); 
		spriteH = getHeight(); 
	}
	
	public abstract void initBody(World world);
	

	public void draw(Batch spriteBatch){
		if (alive) {
			update(Gdx.graphics.getDeltaTime());
			super.draw(spriteBatch);
		}
	}


	protected void update(float deltaTime) {		
		//COLISIONES VERTICALES
		if(collidesBottom(getX(), getY() + body.getLinearVelocity().y * deltaTime)){
			alive = false;
			body.setActive(false);
			game.comidas.removeValue(this, true);
			game.numComidasActual--;
			game.bodiesToDestroy.add(body);
		}
		
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}
	

	private boolean isCellBlocked(float x, float y){
		if (y < 55) return true;
		return false;
	}
	
	public abstract void setSpeed();
	
	 
	private boolean collidesBottom(float x, float y) {
        for(float step = 0; step < spriteW+2; step += spriteW/2)
                if(isCellBlocked(x + step, y)) return true;
        return false;
	}
}

