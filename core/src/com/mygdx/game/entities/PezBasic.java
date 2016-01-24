package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;

public class PezBasic extends Pez{
	
	public PezBasic(Sprite sprite, World world, testGame game, TextureAtlas entities) {
		super(sprite, world, game, entities.createSprite("ballBasicBlue"), 
							 entities.createSprite("ballBasicBlue"),
							 entities.createSprite("ballBasicRed"),
							 entities.createSprite("ballBasicRed"),
							 entities.createSprite("ballBasicPurple"));
		xSpeed = 35;
		ySpeed = 20;
	}
	
	
	public void initBody(World world, int playerNum) {
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 0.0f;
        bodyDef.position.set(getX() + getWidth()/2, getY() + getHeight()/2);
        body = world.createBody(bodyDef);
        body.setUserData(this);
        //((Sprite)body.getUserData()).setPosition(body.getPosition().x,body.getPosition().y);
        
        //PolygonShape shape = new PolygonShape();
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth()/2);
        //shape.setAsBox(player.getWidth()/2 / 1, player.getHeight()/2 / 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.1f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.friction = 0.0f;
        if (playerNum == 0){
        	fixtureDef.filter.categoryBits = PECES;
        	fixtureDef.filter.maskBits = ENEMIGO;
        }
        else {
        	fixtureDef.filter.categoryBits = ENEMIGO;
        	fixtureDef.filter.maskBits = PECES;
        }
        //fixtureDef.isSensor = true; --> use it on towers not to react but detect collision
        body.createFixture(fixtureDef);
        shape.dispose();
	}

	@Override
    public void draw(Batch batch) {
		super.draw(batch);
    }


	@Override
	public void crearMoneda(World world) {
		if(tamanoPez == 0 && tiempoUltimaMoneda <= 0 && !aliveShown){
			tiempoUltimaMoneda = 100 + ran.nextInt(200);
			int i = game.numMonedasActual;
			if (i < game.numMonedasMax){
				Moneda m = new Moneda(game.entities.createSprite("ballBasicBlue"), game.world, game);
				game.monedas.add(m);
				i = game.monedas.indexOf(m, true);
				game.monedas.get(i).setPosition(getX(), getY()-5);
				game.monedas.get(i).initBody(game.world);
				game.monedas.get(i).setSpeed();
				game.numMonedasActual++;
			}
		}
		
	}

	@Override
	public int comida_cercana() {
		float x, y;
		x = getX();
		y = getY();
		float dist = 999999999;
		int res = -1;
		for(int i = 0; i < game.comidas.size; i++){
			float xc, yc;
			xc = game.comidas.get(i).getX();
			yc = game.comidas.get(i).getY();
			float distAux = (x-xc)*(x-xc)+(y-yc)*(y-yc);
			if (distAux < dist){
				dist = distAux;
				res = i;
			}
		}
		return res;		
	}
	
	@Override
	public void ir_a_comida() {
		int i = comida_cercana();	
		if(i >= 0){
			Comida Comida = game.comidas.get(i);
			float xComida = Comida.getX();
			float yComida = Comida.getY();
			float Vel = 60/xComida+yComida;
			body.setLinearVelocity(Vel*xComida, Vel*yComida);
		}
	}

}
