package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.peces.Pez;
import com.mygdx.game.peces.PezBasic;

public class testGame implements Screen {
	public OrthographicCamera camera;
	final AquariumDeluxe game;
	//public Ball[] balls;
	public Vector3 touchPos, last_touch_down = new Vector3();
	World world;
	Box2DDebugRenderer debugRenderer;
	Stage stage;
    private Sprite mapSprite;
	public Array<Body> bodiesToDestroy = new Array<Body>(false, 16);
	private TextureAtlas gameUI, entities;
	private Table container, table;
	private ScrollPane scrollPane;
	private Skin skin;
	private Pez pez;
	
	public testGame(final AquariumDeluxe game) {
		this.game = game;
		float w = Gdx.graphics.getWidth();
		mapSprite = new Sprite(new Texture("backgroundd.png"));
        mapSprite.setPosition(0, 0);                                            
        mapSprite.setSize(w, Gdx.graphics.getHeight()-100); 
		//setting up camera and world
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false,w,Gdx.graphics.getHeight());
		touchPos = new Vector3();
		world = new World(new Vector2(0, 0),true);
		//world.setContactListener(new ContListener(this));
		gameUI = new TextureAtlas(Gdx.files.internal("skins/gameUI.pack"));
		entities = new TextureAtlas(Gdx.files.internal("skins/entities.pack"));
		
		pez = new PezBasic(entities.createSprite("ballBasicBlue"), world);
		pez.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		pez.initBody(world, 0);
		
        debugRenderer = new Box2DDebugRenderer();
        setupActors();
        
      //setting up processors
  		InputMultiplexer inp = new InputMultiplexer();
  		inp.addProcessor(stage);
  		//inp.addProcessor(new InpListener(this));
  		//inp.addProcessor(new GestureDetector(new GestListener(this)));
  		Gdx.input.setInputProcessor(inp);
	}


	private void setupActors() {
		stage = new Stage(new StretchViewport(800, 480));
		skin = new Skin(gameUI);
		container = new Table(skin);
		table = new Table();
		stage.addActor(container);
		
		scrollPane = new ScrollPane(table);
		scrollPane.setFlickScroll(true);
		/*ImageUI[] image = new ImageUI[12];
		for (int i = 0; i < 12; i++){
			if (i < ply.balls.size) 
				image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("ballBasicRed"), true, i);
			else image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("ballBasicRed"), false, i);
//			table.add(image[i]).minSize(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16).spaceRight(20);
			table.add(image[i]).minSize(60, 60).spaceRight(20);
		}	*/
		container.setBounds(0, Gdx.graphics.getHeight()-100, 800, 100);
		container.bottom();
		container.add(scrollPane).padLeft(10).padRight(10);
		container.getColor().mul(1, 1, 1, 0.65f);
		
	}


	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, delta);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		world.step(1f/30f, 6, 2);
		for (Body body : bodiesToDestroy){
			//world.destroyBody(body);
			//body.getBody().destroyFixture(body);
			body.setActive(false);
			bodiesToDestroy.removeValue(body, true);
		}
		camera.update();
		game.batch.begin();
			mapSprite.draw(game.batch);
			pez.draw(game.batch);
		game.batch.end();
		
		debugRenderer.render(world, camera.combined);
		container.debugTable();
		stage.getViewport().apply();
		stage.draw();
		stage.act(delta);
	}
	


	public void resize (int width, int height) { 
		stage.getViewport().update(width, height,true);
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera = new OrthographicCamera();
	    camera.setToOrtho(false,width,Gdx.graphics.getHeight());
	    new Stage(new StretchViewport(width,Gdx.graphics.getHeight()));
	}

	public void pause () { 
	}

    public void resume () {
    }

    public void dispose () { 
    	game.batch.dispose();
    	stage.dispose();
    	world.dispose();
    	gameUI.dispose();
    	entities.dispose();
    	debugRenderer.dispose();
    	mapSprite.getTexture().dispose();
    }

	@Override
	public void show() {
	}


	@Override
	public void hide() {
		dispose();		
	}
	
}



/*
 * 
 * Matrix4 uiMatrix = cam.combined.cpy();
uiMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT);
batch.setProjectionMatrix(uiMatrix);
batch.begin();
 * 
 * 
//Access the sprite
//((Sprite)body.getUserData()).setPosition(body.getPosition().x,body.getPosition().y);

DistanceJointDef distJoint = new DistanceJointDef();
distJoint.bodyA = body;
distJoint.bodyB = body2;
distJoint.dampingRatio = 0.5f;
distJoint.frequencyHz = 4;
distJoint.length = 230;
//world.createJoint(distJoint);

RopeJointDef ropeJoint = new RopeJointDef();
ropeJoint.bodyA = body;
ropeJoint.bodyB = body2;
ropeJoint.maxLength = balls[1].getWidth()*2;
ropeJoint.localAnchorA.set(0, 0);
ropeJoint.localAnchorB.set(0, 0);
//world.createJoint(ropeJoint);
 * 
 * Pixmap pixmap = new Pixmap( 64, 64, Format.RGBA8888 );
pixmap.setColor( 0, 1, 0, 0.75f );
pixmap.fillCircle( 32, 32, 32 );
Texture pixmaptex = new Texture( pixmap );
pixmap.dispose();
 * 
 * */


