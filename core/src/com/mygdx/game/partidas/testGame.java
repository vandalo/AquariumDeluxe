package com.mygdx.game.partidas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.AquariumDeluxe;
import com.mygdx.game.entities.Comida;
import com.mygdx.game.entities.Moneda;
import com.mygdx.game.entities.Pez;
import com.mygdx.game.listeners.ContListener;
import com.mygdx.game.listeners.InpListener;
import com.mygdx.game.subclasses.ImageUI;

public class testGame implements Screen {
	public OrthographicCamera camera;
	final AquariumDeluxe game;
	public World world;
	Box2DDebugRenderer debugRenderer;
	Stage stage;
	protected float w;
    protected Sprite mapSprite;
	public Array<Body> bodiesToDestroy = new Array<Body>(false, 16);
	public TextureAtlas gameUI, entities;
	private Table container, table;
	private ScrollPane scrollPane;
	private Skin skin;
	public int numComidasMax, numComidasActual, numMonedasMax, numMonedasActual;
	public int dinero;
	private NinePatchDrawable loadingBarBackground, loadingBar;
	public Array<Pez> peces;
	public Array<Comida> comidas;
	public Array<Moneda> monedas;
	protected float tiempoJugado, tiempoTotal;
	
	public testGame(final AquariumDeluxe game) {
		this.game = game;
		 w = Gdx.graphics.getWidth();
		//setting up camera and world
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false,w,Gdx.graphics.getHeight());
		world = new World(new Vector2(0, 0),true);
		//world.setContactListener(new ContListener(this));
		gameUI = new TextureAtlas(Gdx.files.internal("skins/gameUI.pack"));
		entities = new TextureAtlas(Gdx.files.internal("skins/entities.pack"));
		
        debugRenderer = new Box2DDebugRenderer();
        setupActors();
        
      //setting up processors
  		InputMultiplexer inp = new InputMultiplexer();
  		inp.addProcessor(stage);
  		inp.addProcessor(new InpListener(this));
  		//inp.addProcessor(new GestureDetector(new GestListener(this)));
  		Gdx.input.setInputProcessor(inp);
  		world.setContactListener(new ContListener(this));
  		numComidasActual = 0;
	}


	private void setupActors() {
		stage = new Stage(new StretchViewport(800, 480));
		skin = new Skin(gameUI);
		container = new Table(skin);
		table = new Table();
		table.setBounds(0, 0, 80, 480);
		stage.addActor(table);
		//stage.addActor(container);
		//VerticalGroup v = new VerticalGroup();
		//scrollPane = new ScrollPane(table);
		//scrollPane.setFlickScroll(false);
		ImageUI[] image = new ImageUI[12];
		for (int i = 0; i < 6; i++){
			image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("ballBasicRed"), true, i);
//			table.add(image[i]).minSize(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16).spaceRight(20);
			table.add(image[i]).minSize(60, 60).spaceBottom(10);
			table.row();
		}	
		table.getColor().mul(1, 1, 1, 0.85f);
		//container.setBounds(0, 0, 80, 480);
		//container.bottom();
		//container.add(scrollPane).padLeft(10).padRight(10);
		//container.getColor().mul(1, 1, 1, 0.65f);
		
		NinePatch loadingBarBackgroundPatch = new NinePatch(gameUI.findRegion("lifeBack"), 2, 2, 2, 2);
        NinePatch loadingBarPatch = new NinePatch(gameUI.findRegion("lifeRed"), 2, 2, 2, 2);
        loadingBar = new NinePatchDrawable(loadingBarPatch);
        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
		
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
			world.destroyBody(body);
			//body.getBody().destroyFixture(body);
			body.setActive(false);
			bodiesToDestroy.removeValue(body, true);
		}
		tiempoJugado+=delta;
		camera.update();
		game.batch.begin();
			mapSprite.draw(game.batch);
			for (int i = 0; i < peces.size; i++){
				peces.get(i).draw(game.batch);
			}
			for (int i = 0; i < comidas.size; ++i){
				if (comidas.get(i) != null) comidas.get(i).draw(game.batch);
			}
			for (int i = 0; i < monedas.size; ++i){
				if (monedas.get(i) != null) monedas.get(i).draw(game.batch);
			}
			loadingBarBackground.draw(game.batch, 200, 10, 550, 40);
	        loadingBar.draw(game.batch, 200, 10, 550*(1-tiempoJugado/tiempoTotal), 40);//progress * 700
		game.batch.end();
		
		debugRenderer.render(world, camera.combined);
		container.debugTable();
		stage.getViewport().apply();
		stage.draw();
		stage.act(delta);
	}
	


	public void resize (int width, int height) { 
		stage.getViewport().update(width, height,true);
		/*pez.updateSizes(width, height);
		pez2.updateSizes(width, height);*/
	    //
	    //new Stage(new StretchViewport(width,Gdx.graphics.getHeight()));
	    //pez.body.setTransform(pez.getX(), pez.getY(), pez.body.getAngle());
	    /*camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.setToOrtho(false,width,Gdx.graphics.getHeight());
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);*/
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
		//dispose();		
	}
	
}




