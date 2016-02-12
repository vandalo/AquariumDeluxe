package com.mygdx.game.subclasses;

import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.mygdx.game.entities.PezEspada;
import com.mygdx.game.partidas.nivel;
import com.mygdx.game.partidas.testGame;

public class ImageUI extends Image{
	final public int position;
	public int index_pez;
	
	//COMPRAR PEZ
	public ImageUI(TextureRegion region, TextureRegion ball, boolean draw, final int position, final testGame game){
		super(region);
		index_pez = 0;
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
            	// && game.pecesDisponibles.get(position) != null
                if (position < 7){
                	//switch (game.pecesDisponibles.get(position)) {
                	switch (index_pez) {
                	// game.pecesDisponibles.get(position) tendrea el id del pc que se deve crear
            		case 200:   
            			//Pez Basic
            			//el spritepeces gt0 es el k hemos creado en nivel el sprite
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
            		case 201:
            			//Pez Espada
            			PezEspada pespada = new PezEspada(game.spritesPeces.get(3), game.world, game, game.spritesPeces.get(4), game.spritesPeces.get(5));
            			if(game.dinero >= pespada.precioPez){
            				pespada.recentCreat = true;
	            			game.peces.add(pespada);
	            			int i = game.peces.indexOf(pespada, true);
	            	        game.peces.get(i).setPosition(game.width/2 - 10, game.height);
	            	        game.peces.get(i).initBody(game.world, 0);
	            	        game.dinero -= pespada.precioPez;
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
                else return false;
                return true;
            }
        });
		//this.draw = draw;
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
		            	tgame.image[tgame.contadorImagen].index_pez = position;
		            	++tgame.contadorImagen;
		            	tgame.peixos_seleccionats.add(position);
            		}
            		else if(!tgame.peixos_seleccionats.contains(position, false)){
            			((ImageUI)tgame.image[tgame.contadorImagen]).setDrawable(new TextureRegionDrawable(ball));
            			tgame.image[tgame.contadorImagen].index_pez = position;
    	            	++tgame.contadorImagen;
    	            	tgame.peixos_seleccionats.add(position);
            		}
	            	
            	}
            	return true;
            }
        });
		//this.draw = draw;
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
		//this.draw = draw;
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
