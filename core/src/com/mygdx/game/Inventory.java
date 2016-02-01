package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.subclasses.ImageUI;

public class Inventory implements Screen {
	private MainMenuScreen game;
	private Stage stage;
	private Table table, inventary;
	private ImageButton backArrow;
	private TextureAtlas gameUI, atlas, entities;
	private Skin skinButtons;
	final AquariumDeluxe Agame;
	protected Actor background;
	protected ScrollPane scrollPane;
	
	
	public Inventory(MainMenuScreen game, AquariumDeluxe gam) {
		this.game = game;
		Agame = gam;
	}

	@Override
	public void show() {//addActor lo a�ade al centro, add lo a�ade al medio 
		stage = new Stage(new StretchViewport(800, 480));
		Gdx.input.setInputProcessor(stage);
		gameUI = new TextureAtlas(Gdx.files.internal("skins/gameUI.pack"));
		backArrow = new ImageButton(game.skin, "backArrow");
		atlas = new TextureAtlas("skins/userInterface.pack");
		skinButtons = new Skin(Gdx.files.internal("skins/userInterface.json"), atlas);
		
		table = new Table(skinButtons);
		inventary = new Table(skinButtons);
		scrollPane = new ScrollPane(inventary);
		
		table.setBackground(new Image(game.background.getTexture()).getDrawable());
		//inventary.setBackground(new Image(game.background.getTexture()).getDrawable());
		
		table.setBounds(0, 0, 800, 80);
		scrollPane.setBounds(0, 80, 800, 400);
		inventary.setBounds(0, 80, 800, 400);
		
		backArrow.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.game.setScreen(game);//TODO GO BACK
			}
		});
		
		stage.addActor(table);
		stage.addActor(backArrow);
		
		stage.addActor(scrollPane);
		/*final Table tableInvent = new Table();
		tableInvent.setFillParent(true);
		tableInvent.add(scrollPane).setActorBounds(0, 80, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage.addActor(tableInvent);
		*/
		entities = new TextureAtlas(Gdx.files.internal("skins/fish.pack"));
		
		//Peixos triats
		ImageUI[] image = new ImageUI[6];
		for (int i = 0; i < 6; i++){
			image[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true,i+100, Agame);
			table.add(image[i]).minSize(60, 60).spaceBottom(10);
		}	
		table.getColor().mul(1, 1, 1, 0.85f);

		
		//Peixos Possibles
		ImageUI[] imagePossibles = new ImageUI[24];
		inventary.padTop(15);
		for (int i = 0; i < 24; i++){
			imagePossibles[i] = new ImageUI(gameUI.findRegion("dissabledbutton"), entities.findRegion("pezbasic"), true,i+200, Agame);
			inventary.add(imagePossibles[i]).minSize(60, 60).spaceTop(20).spaceLeft(25);
			if((i+1)%4 == 0)inventary.row();
		}	
		inventary.padBottom(10);
		table.getColor().mul(1, 1, 1, 0.85f);
//		table.add(backArrow).align(Align.topLeft).pad(10);*/
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Agame.batch.begin();
			game.background.draw(Agame.batch);
		Agame.batch.end();
		stage.act(delta);
		stage.draw();
		//table.debugTable();
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