package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class PezBasic extends Pez{
	
	public PezBasic(Sprite sprite, World world, TextureAtlas entities) {
		super(sprite, world, entities.createSprite("ballBoss"), 
							 entities.createSprite("ballFastBlue"),
							 entities.createSprite("ballFastBlue"));
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

}
