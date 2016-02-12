package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;

public class PezEspada extends Pez{
	private float xc, yc, distAux, dist, xComida, yComida, Vel, x, y;
	int res, i;
	
	public PezEspada(Sprite sprite, World world, testGame game, Sprite pezhambre, Sprite pezmuerto) {
		super(world, game, sprite,sprite,pezhambre,pezhambre,pezmuerto);
		xSpeed = 35;
		ySpeed = 20;
		precioPez = 200;
		Vel = 20;
		dirX = 35;
		dirY = 5;
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
		
	}

	public void setNivelPez(){
	
	}
	
	public int getValueMoneda(){
		return 1;
	}
	
	@Override
	public int comida_cercana() {
		return 1;
	}
	
	@Override
	public void ir_a_comida() {
		
	}
	
	protected void update(float deltaTime) {		
		//setNivelPez();
		collisionX = false;
		collisionY = false;
		tiempoDesdeComida += deltaTime;
		tiempoUltimaMoneda -= deltaTime;
		tiempoPez += deltaTime;
		//crearMoneda(game.world);
		
		//CALCULATE VELOCITY AND COLLISIONS FOR AI
		//COLISIONES HORIZONTALES
		//si ha comido reinicie la IA de direccion
		if (haHabidoComida && game.numComidasActual < 1){
			haHabidoComida = false;
			steps = 0;
		}
		//colocar el pez en el centro
		if(recentCreat == true){
			body.setLinearVelocity(0, (getY()-(height/2))*(-10));
			if(getY() < height/2)recentCreat = false;
		}
		//si esta muerto de hambre (literalmente)
		else if (tiempoDesdeComida > TIME_HASTA_MUERTE){
			body.setLinearVelocity(0,15);
			if (collidesTop(getX(), getY()-50)){
				alive = false;
				game.bodiesToDestroy.add(body);
				body.setActive(false);
				game.peces.removeValue(this, true);
			}
		}
		
		else if (tiempoDesdeComida > TIME_HASTA_HAMBRE && game.numComidasActual > 0){
			haHabidoComida = true;
			ir_a_comida();
		}
		else{
			choseDirection();
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
		}
		//}
		
		//SET POSITION DE LA IMAGEN K ACOMPANA AL BODY
		
		if(tiempoDesdeComida > TIME_HASTA_MUERTE){
			set(spriteMuerto);
			setSize(width/8, width/(int)(8*1.487));
			aliveShown = true;
		}
		else if(tiempoDesdeComida > TIME_HASTA_HAMBRE){
			set(spriteHambDer);
			setSize(width/8, width/(int)(8*1.487));
			if (body.getLinearVelocity().x < 0) setFlip(true, false);
			else setFlip(false, false);
		}
		else {
			set(spriteDer);
			setSize(width/8, width/(int)(8*1.487));
			if (body.getLinearVelocity().x < 0) setFlip(true, false);//set(spriteIzq);
			else setFlip(false, false);
		}
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}

}
