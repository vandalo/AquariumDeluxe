package com.mygdx.game.subclasses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.AquariumDeluxe;
import com.mygdx.game.PrePartida;
import com.mygdx.game.StageSelector;
import com.mygdx.game.entities.PezBasic;
import com.mygdx.game.partidas.nivel;
import com.mygdx.game.partidas.testGame;

public class ImageUI extends Image{
	private boolean draw;
	final public int position;
	
	//COMPRAR PEZ
	public ImageUI(TextureRegion region, TextureRegion ball, boolean draw, final int position, final testGame game){
		super(region);
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                if (game.pecesDisponibles.size > position && game.pecesDisponibles.get(position) != null){
                	switch (game.pecesDisponibles.get(position)) {
                	// game.pecesDisponibles.get(position) tendrea el id del pc que se deve crear
            		case 1:   
            			//Pez Basic
            			//TODO CAMBIAR EL GET1 Y GET2 POR LOS NUM K LE PASEMOS POR EL CONSTRUCTOR
            			PezBasic pb = new PezBasic(game.spritesPeces.get(0), game.world, game, game.spritesPeces.get(1), game.spritesPeces.get(2));
            			if(game.dinero >= pb.precioPez){
	            			pb.recentCreat = true;
	            			game.peces.add(pb);
	            			int i = game.peces.indexOf(pb, true);
	            	        game.peces.get(i).setPosition(game.width/2 - 10, game.height);
	            	        game.peces.get(i).initBody(game.world, 0);
	            	        game.dinero -= pb.precioPez;
            			}
            			break;
            		case 2:
            			//Pez ?? (basic too por el momento)
            			PezBasic pb2 = new PezBasic(game.spritesPeces.get(0), game.world, game, game.spritesPeces.get(1), game.spritesPeces.get(2));
            			if(game.dinero >= pb2.precioPez){
	            			pb2.recentCreat = true;
	            			game.peces.add(pb2);
	            			int i = game.peces.indexOf(pb2, true);
	            	        game.peces.get(i).setPosition(game.width/2 - 10, game.height);
	            	        game.peces.get(i).initBody(game.world, 0);
	            	        game.dinero -= pb2.precioPez;
            			}
            			break;
            		default:
            			break;
            		}              	
                }
                else if (position == 7){
                	//Objetivo de la partida
                	if(game.dinero >= game.objetivoPartida) game.win = true;
                }
                //setVisible(false);
                return true;
            }
        });
		this.draw = draw;
		//this.ball = new Sprite(ball);
		this.position = position;
		//this.ball.setSize(getWidth(), getHeight());
		//this.ball.setAlpha(0.8f);
	}
	
	//para escoger los peces que van en la partida
	public ImageUI(final TextureRegion region, final TextureRegion ball, boolean draw, final int position, final AquariumDeluxe game, final testGame tgame){
		super(region);
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
            	if (tgame.contadorImagen < 6 && tgame.peixos_seleccionats.size < 6){
            		if(tgame.peixos_seleccionats.size == 0){
	            	((ImageUI)tgame.image[tgame.contadorImagen]).setDrawable(new TextureRegionDrawable(ball));
	            	++tgame.contadorImagen;
	            	tgame.peixos_seleccionats.add(pointer);
            		}
            		else if(!tgame.peixos_seleccionats.contains(pointer, false)){
            			((ImageUI)tgame.image[tgame.contadorImagen]).setDrawable(new TextureRegionDrawable(ball));
    	            	++tgame.contadorImagen;
    	            	tgame.peixos_seleccionats.add(pointer);
            		}
	            	
            	}
            	return true;
            }
        });
		this.draw = draw;
		//this.ball = new Sprite(ball);
		this.position = position;
		//this.ball.setSize(getWidth(), getHeight());
		//this.ball.setAlpha(0.8f);
	}
	
	public ImageUI(TextureRegion region, TextureRegion ball, boolean draw, final int position, 
			final AquariumDeluxe game, final StageSelector stg){
		super(region);
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
            	switch (position) {
        		case 0:  
        			game.setScreen(new PrePartida(game,0, stg));
        			break;
        		case 1:  
        			game.setScreen(new PrePartida(game,1, stg));
        			break;
        		case 2:  
        			game.setScreen(new nivel(game,2));
        			break;
        		case 3:  
        			game.setScreen(new nivel(game,3));
        			break;
        		case 4:  
        			game.setScreen(new nivel(game,4));
        			break;
                default:
                	break;
            	}
            	return true;
            }
        });
		this.draw = draw;
		//this.ball = new Sprite(ball);
		this.position = position;
		//this.ball.setSize(getWidth(), getHeight());
		//this.ball.setAlpha(0.8f);
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		//System.out.println("hitted");
		return super.hit(x, y, touchable);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		/*if (draw) {
			ball.setPosition(getX() + getWidth()/2 - ball.getWidth()/2, getY() + getHeight()/2 - ball.getHeight()/2);
			ball.draw(batch);
		}*/
	}
	
}
