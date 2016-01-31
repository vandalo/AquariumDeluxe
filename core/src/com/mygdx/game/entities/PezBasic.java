package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;

public class PezBasic extends Pez{
	private float xc, yc, distAux, dist, xComida, yComida, Vel, x, y;
	int res, i;
	
	public PezBasic(Sprite sprite, World world, testGame game, Sprite pezhambre, Sprite pezmuerto) {
		super(world, game, sprite,sprite,pezhambre,pezhambre,pezmuerto);
		xSpeed = 35;
		ySpeed = 20;
		precioPez = 200;
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
		if(tamanoPez > 0 && tiempoUltimaMoneda <= 0 && !aliveShown){
			tiempoUltimaMoneda = 300 + ran.nextInt(450);
			int i = game.numMonedasActual;
			if (i < game.numMonedasMax){
				Moneda m = new Moneda(game.spriteMonedas.get(tamanoPez-1), game.world, game);
				m.valor = getValueMoneda();
				game.monedas.add(m);
				i = game.monedas.indexOf(m, true);
				game.monedas.get(i).setPosition(getX(), getY()-5);
				game.monedas.get(i).initBody(game.world);
				game.monedas.get(i).setSpeed();
				game.numMonedasActual++;
			}
		}
		
	}

	public void setNivelPez(){
		if(tiempoPez > 180)tamanoPez = 3;
		else if(tiempoPez > 70)tamanoPez = 2;
		else if(tiempoPez > 20) tamanoPez = 1;	
	}
	
	public int getValueMoneda(){
		if(tamanoPez == 1) return 50;
		else if(tamanoPez == 2)return 100;
		else return 175;		
	}
	
	@Override
	public int comida_cercana() {
		x = getX();
		y = getY();
		dist = 999999999;
		res = -1;
		for(int i = 0; i < game.comidas.size; i++){
			if (game.comidas.get(i) != null){
				xc = game.comidas.get(i).getX();
				yc = game.comidas.get(i).getY();
				distAux = (x-xc)*(x-xc)+(y-yc)*(y-yc);
				if (distAux < dist){
					dist = distAux;
					res = i;
				}
			}
		}
		return res;		
	}
	
	@Override
	public void ir_a_comida() {
		i = comida_cercana();	
		if(i >= 0){
			xComida = game.comidas.get(i).getX();
			yComida = game.comidas.get(i).getY();
			Vel = 20;
			body.setLinearVelocity(-((getX()-xComida) * Vel), -((getY()-yComida) * Vel));
			//System.out.println("Entra x: " + getX() + ". XComida: " + xComida + ". Val i: " + i);
			//body.setLinearVelocity(xSpeed*3, ySpeed*3);
		}
	}

}
