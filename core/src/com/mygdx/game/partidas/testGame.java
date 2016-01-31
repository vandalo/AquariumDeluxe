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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
	public TextureAtlas gameUI, entities, atlas, throwables;
	
	private Table table;
	private Skin skin;
	private TextButton monedasView;
	protected ImageUI objetivo, menu;
	protected Skin skinButtons;
	
	public int numComidasMax, numComidasActual, numMonedasMax, numMonedasActual;
	public int dinero;
	private NinePatchDrawable loadingBarBackground, loadingBar;
	public Array<Pez> peces;
	public Array<Comida> comidas;
	public Array<Moneda> monedas;
	protected float tiempoJugado, tiempoTotal;
	public int height, width;
	public Array<Integer> pecesDisponibles;
	public int objetivoPartida;
	public boolean win = false;
	
	public Array<Sprite> spritesPeces, spriteMonedas, spriteComidas;
	
	public testGame(final AquariumDeluxe game) {
		this.game = game;
		 w = Gdx.graphics.getWidth();
		//setting up camera and world
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false,w,Gdx.graphics.getHeight());
		world = new World(new Vector2(0, 0),true);
		//world.setContactListener(new ContListener(this));
		gameUI = new TextureAtlas(Gdx.files.internal("skins/gameUI.pack"));
		entities = new TextureAtlas(Gdx.files.internal("skins/fish.pack"));
		throwables = new TextureAtlas(Gdx.files.internal("skins/throwables.pack"));
		
		
        debugRenderer = new Box2DDebugRenderer();
        setupActors();
        
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
      //setting up processors
  		InputMultiplexer inp = new InputMultiplexer();
  		inp.addProcessor(stage);
  		inp.addProcessor(new InpListener(this));
  		//inp.addProcessor(new GestureDetector(new GestListener(this)));
  		Gdx.input.setInputProcessor(inp);
  		world.setContactListener(new ContListener(this));
  		numComidasActual = 0;
  		
  		spriteMonedas = new Array<Sprite>(3);
  		spriteMonedas.add(throwables.createSprite("monedabronze"));
  		spriteMonedas.add(throwables.createSprite("monedaplata"));
  		spriteMonedas.add(throwables.createSprite("moneda"));
  		
  		spriteComidas = new Array<Sprite>(3);
  		spriteComidas.add(throwables.createSprite("menjar1"));
  		spriteComidas.add(throwables.createSprite("menjar2"));
  		spriteComidas.add(throwables.createSprite("menjar3"));
	}


	private void setupActors() {
		stage = new Stage(new StretchViewport(800, 480));
		skin = new Skin(gameUI);
		table = new Table();
		table.setBounds(0, 0, 80, 480);
		stage.addActor(table);
		//stage.addActor(container);
		//VerticalGroup v = new VerticalGroup();
		//scrollPane = new ScrollPane(table);
		//scrollPane.setFlickScroll(false);
		ImageUI[] image = new ImageUI[6];
		for (int i = 0; i < 6; i++){
			image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true, i, this);
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
        
        atlas = new TextureAtlas("skins/userInterface.pack");
		skinButtons = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		//container = new Table(skinButtons);
		
		//container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		monedasView = new TextButton("BALANCE", skinButtons, "mainMenuBlack");
		objetivo = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true, 7, this);
		menu = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true, 8, this);
		
		monedasView.pad(10);
		stage.addActor(monedasView);
		stage.addActor(menu);
		stage.addActor(objetivo);
		
		monedasView.setPosition(100, 380);
		objetivo.setPosition(100, 25);
		menu.setPosition(750, 430);
		
	}


	@Override
	public void render(float delta) {
		delta = Math.min(0.06f, delta);
		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		world.step(1/30f, 6, 2);
		for (Body body : bodiesToDestroy){
			body.setActive(false);
			world.destroyBody(body);
			bodiesToDestroy.removeValue(body, true);
		}
		tiempoJugado+=delta;
		
		
		//COMPROBAMOS SI HEMOS PERDIDO
		if(!checkLost() && !win){
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
				loadingBarBackground.draw(game.batch, (int)(width*0.25), (int)(height*0.02), (int)(width*0.6875), (int)(height*0.08));
		        loadingBar.draw(game.batch, (int)(width*0.25), (int)(height*0.02), (int)(width*0.6875)*(1-tiempoJugado/tiempoTotal), (int)(height*0.08));//progress * 700
			game.batch.end();
			
			debugRenderer.render(world, camera.combined);
			monedasView.setText("Balance: " + dinero);	
			monedasView.setSize(monedasView.getPrefWidth(), monedasView.getPrefHeight());
			
			//stage.getViewport().apply();
			stage.draw();
			stage.act(delta);
		}
	}
	
	
	private boolean checkLost() {
		if (tiempoJugado > tiempoTotal) return true;
		else return false;
	}


	public void resize (int width, int height) { 
		stage.getViewport().update(width, height,true);
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
    	skin.dispose();
    	skinButtons.dispose();
    	for (int i = 0; i < spritesPeces.size;i++) spritesPeces.get(i).getTexture().dispose();
    	throwables.dispose();
    }

	@Override
	public void show() {
	}


	@Override
	public void hide() {
		//dispose();		
	}
	
}




