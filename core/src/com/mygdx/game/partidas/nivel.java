package com.mygdx.game.partidas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AquariumDeluxe;
import com.mygdx.game.entities.Comida;
import com.mygdx.game.entities.Moneda;
import com.mygdx.game.entities.Pez;
import com.mygdx.game.entities.PezBasic;

public class nivel extends testGame{

	public nivel(AquariumDeluxe game, int i) {//le pasamos num de opciones partida, array de peces
		super(game);
		OpcionesPartida ops = new OpcionesPartida(i);
		//mapSprite = new Sprite(new Texture("backgroundd.png"));
		mapSprite = new Sprite(new Texture("fondo1-01.png"));
        mapSprite.setPosition(0, 0);                                            
        mapSprite.setSize(w, Gdx.graphics.getHeight()); 
        peces = new Array<Pez>(false, ops.numPeces);
        enemigosPeces = new Array<Pez>(false, ops.numEnemigos);
        comidas = new Array<Comida>(false, ops.numComidas);
        monedas = new Array<Moneda>(false, ops.numMonedas);
        numComidasMax = ops.numComidas;
        numMonedasMax = ops.numMonedas;
        objetivoPartida = ops.objetivo;
        enemigosIndex = ops.enemigosIndex;
        enemigosTiempo = ops.enemigosTiempo;
    	numEnemigos = ops.numEnemigos;
        dinero = ops.dineroInicial;
        
        spritesPeces = new Array<Sprite>(7);
        //Pez Basic 200
        //0,1,2
        spritesPeces.add(entities.createSprite("pezbasic"));
        spritesPeces.add(entities.createSprite("pezhambriento"));
        spritesPeces.add(entities.createSprite("pezmuerto"));
        
        //Pez Espada 201
        //3,4,5
        spritesPeces.add(entities.createSprite("espasa"));
        spritesPeces.add(entities.createSprite("espasa_gana"));
        spritesPeces.add(entities.createSprite("espasa_mort"));
        
        //Pez Barracuda 1
        //6,7
        spritesPeces.add(entities.createSprite("barracuda"));
        spritesPeces.add(entities.createSprite("barracuda_mort"));
        
        peces.add( new PezBasic(spritesPeces.get(0), world, this, spritesPeces.get(1), spritesPeces.get(2)));       
        peces.get(0).setPosition(Gdx.graphics.getWidth()/2 - 10, Gdx.graphics.getHeight()/2);
        peces.get(0).initBody(world, 0);
        
        peces.add(new PezBasic(spritesPeces.get(0), world, this, spritesPeces.get(1), spritesPeces.get(2)));
        peces.get(1).setPosition(Gdx.graphics.getWidth()/2 + 20, Gdx.graphics.getHeight()/2);
        peces.get(1).initBody(world, 0);
        
        tiempoJugado = 0;
        tiempoTotal = ops.timepoPartida;
        pecesDisponibles = new Array<Integer>(6); //en vez de 6 sera el array k le pasemos a nivel
        pecesDisponibles.add(1);
	}

}
