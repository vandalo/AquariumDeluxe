package com.mygdx.game.partidas;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas, timepoPartida, objetivo, dineroInicial;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 0:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			objetivo = 500;
			dineroInicial = 100000;
			break;
		case 1:
			numPeces = 100;
			numComidas = 4;
			numMonedas = 200;
			timepoPartida = 30;
			objetivo = 2000;
			dineroInicial = 1000;
			break;
		default:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			objetivo = 500;
			dineroInicial = 200;
			break;
		}
	}
	
}
