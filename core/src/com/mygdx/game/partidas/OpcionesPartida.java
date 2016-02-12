package com.mygdx.game.partidas;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas, timepoPartida, objetivo, dineroInicial;
	public float[]  enemigosTiempo;
	public int[] enemigosIndex;
	public int numEnemigos;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 0:
			numPeces = 100;
			numComidas = 3;
			numMonedas = 100;
			timepoPartida = 180;
			objetivo = 500;
			dineroInicial = 200;
			numEnemigos = 2;
			enemigosIndex = new int[numEnemigos];
			enemigosTiempo = new float[numEnemigos];
			enemigosIndex[0] = 1;
			enemigosTiempo[0] = 5;
			enemigosIndex[1] = 1;
			enemigosTiempo[1] = 5;
			break;
		case 1:
			numPeces = 100;
			numComidas = 3;
			numMonedas = 100;
			timepoPartida = 180;
			objetivo = 600;
			dineroInicial = 200;
			numEnemigos = 0;
			enemigosIndex = new int[numEnemigos];
			enemigosTiempo = new float[numEnemigos];
			break;
		default:
			numPeces = 100;
			numComidas = 3;
			numMonedas = 100;
			timepoPartida = 180;
			objetivo = 500;
			dineroInicial = 200;
			numEnemigos = 0;
			enemigosIndex = new int[numEnemigos];
			enemigosTiempo = new float[numEnemigos];
			break;
		}
	}
	
}
