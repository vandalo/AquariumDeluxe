package com.mygdx.game.partidas;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas, timepoPartida;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 1:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			break;

		default:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			break;
		}
	}
	
}
