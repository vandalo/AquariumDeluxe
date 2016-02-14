package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.partidas.nivel;

public class PrePartida implements Screen {
	private StageSelector gameStage;
	private Stage stage;
	private Table table;
	private ImageButton backArrow;
	private TextureAtlas atlas;
	private TextButton buttonPlay;
	private int nivel;
	private Sprite background;
	private Skin skin;
	final AquariumDeluxe Agame;
	
	
	public PrePartida(AquariumDeluxe gam, int nivel, StageSelector gameStage) {
		Agame = gam;
		this.nivel = nivel;
		this.gameStage = gameStage;
	}

	@Override
	public void show() {//addActor lo a�ade al centro, add lo a�ade al medio 
		stage = new Stage(new StretchViewport(800, 480));
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("skins/userInterface.pack");
		skin = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		backArrow = new ImageButton(skin, "backArrow");
		table = new Table(skin);
		table.setBounds(0, 0, 800, 480);
		
		
		backArrow.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Agame.setScreen(gameStage);//TODO GO BACK
			}
		});
		
		buttonPlay = new TextButton("PLAY", skin, "mainMenuBlack");
		buttonPlay.pad(10);
		buttonPlay.padRight(30);
		buttonPlay.padLeft(30);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//game.setScreen(new testGame(game));
	            //dispose();
				Agame.setScreen(new nivel(Agame, nivel));
			}
		});
		buttonPlay.setPosition(370, 190);
		table.add(buttonPlay);
		//stage
		stage.addActor(table);
		stage.addActor(backArrow);
		backArrow.setPosition(10, 326);
		
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
		background.getTexture().dispose();
		stage.dispose();
		skin.dispose();
	}

}
