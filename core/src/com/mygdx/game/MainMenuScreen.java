package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.partidas.nivel;

public class MainMenuScreen implements Screen {

	final AquariumDeluxe game;
	final MainMenuScreen mmScreen;
	private TextureAtlas atlas;
	protected Skin skin;
	private Stage stage;
	private Table table;
	private Label heading;
	private TextButton buttonPlay, buttonExit, buttonInventory, buttonAquarium;
	protected Sprite background;
	//private List<String> list;
	//private ScrollPane scrollPane;
	
    public MainMenuScreen(final AquariumDeluxe gam) {
        game = gam;
        mmScreen = this; 
    }
    
	@Override
	public void show() {
		stage = new Stage(new FitViewport(800, 480)); //CON STRETCH Y BACKGROUND EN LA TABLA SE VE BN
		Gdx.input.setInputProcessor(stage);
		//((OrthographicCamera)stage.getCamera()).setToOrtho(false, 800, 480);
		atlas = new TextureAtlas("skins/userInterface.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		buttonPlay = new TextButton("PLAY", skin, "mainMenuBlack");
		buttonPlay.pad(10);
		buttonPlay.padRight(30);
		buttonPlay.padLeft(30);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//game.setScreen(new testGame(game));
	            //dispose();
				game.setScreen(new StageSelector(mmScreen, game));
			}
		});
		
		buttonInventory = new TextButton("INVENTORY", skin, "mainMenuBlack");
		buttonInventory.pad(10);
		buttonInventory.padRight(30);
		buttonInventory.padLeft(30);
		buttonInventory.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//game.setScreen(new testGame(game));
	            //dispose();
				game.setScreen(new Inventory(mmScreen, game));
			}
		});
		
		buttonAquarium = new TextButton("AQUARIUM", skin, "mainMenuBlack");
		buttonAquarium.pad(10);
		buttonAquarium.padRight(30);
		buttonAquarium.padLeft(30);
		buttonAquarium.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//game.setScreen(new testGame(game));
	            //dispose();
				game.setScreen(new nivel(game,1));
			}
		});
		
		buttonExit = new TextButton("EXIT", skin, "mainMenuBlack");
		buttonExit.pad(10);
		buttonExit.padRight(30);
		buttonExit.padLeft(30);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
	            Gdx.app.exit();
			}
		});
		
		heading = new Label("Aquarium Deluxe", skin, "default");
		//heading.setFontScale(2);
		
		table.add(heading);
		table.getCell(heading).padBottom(30);
		//table.setBackground(new Image(new Texture("globe.png")).getDrawable());
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(10);
		table.row();
		table.add(buttonInventory);
		table.getCell(buttonInventory).spaceBottom(10);
		table.row();
		table.add(buttonAquarium);
		table.getCell(buttonAquarium).spaceBottom(10);
		table.row();
		table.add(buttonExit);
		//table.debug();
		table.setOrigin(table.getWidth()/2, table.getHeight()/2);
		table.setFillParent(true);
		stage.addActor(table);
		
		background = new Sprite(new Texture("background.png"));
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		/*list = new List<String>(skin);
		list.setItems(new String[]{"Uno", "dos", "tres"});
		scrollPane = new ScrollPane(list, skin);
		table.add("Select Level");
		table.add(scrollPane);*/

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
			background.draw(game.batch);
		game.batch.end();
        //table.debugTable();
		stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		//table.invalidateHierarchy();
		stage.getCamera().update();
		//background.setBounds(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		atlas.dispose();
		skin.dispose();
		stage.dispose();
		background.getTexture().dispose();
	}

}

/*private ScheduledExecutorService programador;
programador = Executors.newSingleThreadScheduledExecutor();
programador.schedule(accion, timepohastalaprimeraejecucion, TimeUnit.MILLISECONDS);
programador.schedule(accion, 2000, TimeUnit.MILLISECONDS);
 private Runnable accion = new Runnable() {
        @Override
        public void run() {}};
        ---------
        *Timer timer = new Timer();
        *TimerTask tas = new TimerTask(){
	        public void run(){
	        }
	    };
	    
	 	timer.schedule(tas, 2900);        */
