package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

	final AquariumDeluxe game;
	final MainMenuScreen mmScreen;
	private TextureAtlas atlas, gameUI;
	protected Skin skin, skin2;
	private Stage stage;
	private Table table;
	protected Sprite background;
	private ImageButton menu;
	
    public MainMenuScreen(final AquariumDeluxe gam) {
        game = gam;
        mmScreen = this; 
    }
    
	@Override
	public void show() {
		//stage = new Stage(new FitViewport(800, 480)); //CON STRETCH Y BACKGROUND EN LA TABLA SE VE BN
		stage = new Stage(new StretchViewport(800, 480));
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("skins/userInterface.pack");
		gameUI = new TextureAtlas("skins/gameUI.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		skin2 = new Skin(Gdx.files.internal("skins/gameUI.json"), gameUI);
		table = new Table(skin);
		table.setBounds(0, 0, 800, 480);
		
		//menu = new ImageButton(skin2, "timon");
		
		//buttonPlay = new TextButton("PLAY", skin, "mainMenuBlack");
		//SpriteDrawable
		ImageButtonStyle b = new ImageButtonStyle();
		b.imageUp = skin2.getDrawable("options");
		//b.imageDown = skin2.getDrawable("options");
		b.pressedOffsetX = 1;
		b.pressedOffsetY = -1;
		b.imageUp.setMinHeight(65);
		b.imageUp.setMinWidth(65);
		menu = new ImageButton(b);
		//heading = new Label("Aquarium Deluxe", skin, "default");
		//heading.setFontScale(2);
		
		//table.add(heading);
		//table.getCell(heading).padBottom(30);
		menu.setPosition(700, 400);
		//menu.setSize(150, 150);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				gameUI.dispose();
				game.setScreen(new StageSelector(mmScreen, game));
			}
		});
		
		//table.add(menu);
		stage.addActor(table);
		table.addActor(menu);
		
		background = new Sprite(new Texture("fondo2.png"));
		background.setBounds(0, 0, AquariumDeluxe.width, AquariumDeluxe.height);
		//background.setBounds(0, 0, 800, 480);
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
			background.draw(game.batch);
		game.batch.end();
        //table.debugTable();
		//stage.getViewport().apply();
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
		gameUI.dispose();
		background.getTexture().dispose();
	}

}
	
	/*
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		*/


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
