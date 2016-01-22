package com.mygdx.game.partidas;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 1:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			break;

		default:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			break;
		}
	}
	
}
