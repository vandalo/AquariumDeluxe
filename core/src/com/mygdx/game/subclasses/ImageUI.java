package com.mygdx.game.subclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.entities.PezBasic;
import com.mygdx.game.partidas.testGame;

public class ImageUI extends Image{
	private Sprite ball;
	private boolean draw;
	final public int position;
	
	public ImageUI(TextureRegion region, TextureRegion ball, boolean draw, final int position, final testGame game){
		super(region);
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                System.out.println("Touched: " + event.getListenerActor().getY() + "position: " + position);
                if (game.pecesDisponibles.size > position && game.pecesDisponibles.get(position) != null){
                	switch (game.pecesDisponibles.get(position)) {
            		case 1:   
            			PezBasic pb = new PezBasic(game.entities.createSprite("ballBasicBlue"), game.world, game, game.entities);
            			if(game.dinero >= pb.precioPez){
	            			pb.recentCreat = true;
	            			game.peces.add(pb);
	            			int i = game.peces.indexOf(pb, true);
	            	        game.peces.get(i).setPosition(Gdx.graphics.getWidth()/2 - 10, Gdx.graphics.getHeight());
	            	        game.peces.get(i).initBody(game.world, 0);
	            	        game.dinero -= pb.precioPez;
            			}
            		default:
            			break;
            		}              	
                }
                else{
                	//Objetivo de la partida
                	if(game.dinero >= game.objetivoPartida) game.win = true;
                }
                //setVisible(false);
                return true;
            }
        });
		this.draw = draw;
		this.ball = new Sprite(ball);
		this.position = position;
		this.ball.setSize(getWidth(), getHeight());
		this.ball.setAlpha(0.8f);
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		//System.out.println("hitted");
		return super.hit(x, y, touchable);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (draw) {
			ball.setPosition(getX() + getWidth()/2 - ball.getWidth()/2, getY() + getHeight()/2 - ball.getHeight()/2);
			ball.draw(batch);
		}
	}
	
}
