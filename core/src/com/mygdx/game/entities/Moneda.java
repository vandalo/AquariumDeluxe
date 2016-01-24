package com.mygdx.game.entities;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.partidas.testGame;


public class Moneda extends Sprite {

	private float spriteW, spriteH;
	public boolean alive;
	public Body body;
	final short PECES = 0x1;    // 0001
    final short COMIDA = 0x1 << 1; // 0010 or 0x2 in hex
    final short ENEMIGO = 0x11 << 1; // 00110 or 0x2 in hex
    final short MONEDA = 0x1 << 3; //1000
    protected int valor;
    public float tiempoDesdeCaida;
    Sprite spriteMoneda;
    protected int width, height;
    testGame game;
    private boolean startCount;
	
	public Moneda(Sprite sprite, World world, testGame game){
		super(sprite);
		alive = true;
		spriteW = getWidth(); 
		spriteH = getHeight(); 
		valor = 0;
		tiempoDesdeCaida = 0f;
		spriteMoneda = sprite;
		width = Gdx.graphics.getWidth()*9/10;
		height = Gdx.graphics.getHeight()*3/4;
		this.game = game;
		startCount = false;
	}
	

	
	public void updateSizes(int width, int height){
	}

	public void draw(Batch spriteBatch){
		if (alive) {
			update(Gdx.graphics.getDeltaTime());
			super.draw(spriteBatch);
		}
	}


	protected void update(float deltaTime) {		
		//SET POSITION DE LA IMAGEN K ACOMPANA AL BODY
		if(collidesBottom(width/2, getY() + body.getLinearVelocity().y * deltaTime)){
			startCount = true;
		}
		if(startCount){
			body.setLinearVelocity(0, 0);
			tiempoDesdeCaida += deltaTime;
			if (tiempoDesdeCaida > 5) {
				body.setActive(false);
				alive = false;
				game.monedas.removeValue(this, true);
				game.bodiesToDestroy.add(body);
				game.numMonedasActual--;
			}
		}
		else{
			body.setLinearVelocity(0, -15);
		}
		setPosition(body.getPosition().x - spriteW/2, body.getPosition().y - spriteH/2);
	}
	
	

	private boolean isCellBlocked(float x, float y){
		if (x < 85 || (x > width) || (y > height) || y < 55){
			return true;
		}
		return false;
	}
	 
	private boolean collidesBottom(float x, float y) {
        for(float step = 0; step < spriteW+2; step += spriteW/2)
                if(isCellBlocked(x + step, y)) return true;
        return false;
	}
	
	public void initBody(World world) {
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
    	fixtureDef.filter.categoryBits = MONEDA;
    	fixtureDef.filter.maskBits = 0x0;
  
        //fixtureDef.isSensor = true; --> use it on towers not to react but detect collision
        body.createFixture(fixtureDef);
        shape.dispose();
	}
	
	public void setSpeed(){
		body.setLinearVelocity(0, -15);
	}
}

