package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.partidas.nivel;
import com.mygdx.game.partidas.testGame;
import com.mygdx.game.subclasses.ImageUI;

public class StageSelector implements Screen {
	private MainMenuScreen game;
	private Stage stage;
	private Table table;
	private ImageButton backArrow;
	private TextureAtlas gameUI, atlas, entities;
	private Skin skinButtons;
	final AquariumDeluxe Agame;
	
	
	public StageSelector(MainMenuScreen game, AquariumDeluxe gam) {
		this.game = game;
		Agame = gam;
	}

	@Override
	public void show() {//addActor lo a�ade al centro, add lo a�ade al medio 
		stage = new Stage(new StretchViewport(800, 400));
		Gdx.input.setInputProcessor(stage);
		gameUI = new TextureAtlas(Gdx.files.internal("skins/gameUI.pack"));
		backArrow = new ImageButton(game.skin, "backArrow");
		atlas = new TextureAtlas("skins/userInterface.pack");
		skinButtons = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		table = new Table(skinButtons);
		table.setBackground(new Image(game.background.getTexture()).getDrawable());
		table.setFillParent(true);
		
		backArrow.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.game.setScreen(game);//TODO GO BACK
			}
		});
		
		//stage
		stage.addActor(table);
		stage.addActor(backArrow);
		backArrow.setPosition(10, 326);
		
		entities = new TextureAtlas(Gdx.files.internal("skins/fish.pack"));
		ImageUI[] image = new ImageUI[6];
		for (int i = 0; i < 6; i++){
			image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true, i, Agame);
//			table.add(image[i]).minSize(Gdx.graphics.getWidth()/16, Gdx.graphics.getWidth()/16).spaceRight(20);
			//table.add(image[i]).minSize(60, 60).spaceBottom(10);
			stage.addActor(image[i]);
			image[i].setPosition(i*60, i*25);
		}	
		
		
//		table.add(backArrow).align(Align.topLeft).pad(10);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		table.debugTable();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		stage.getCamera().update();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
