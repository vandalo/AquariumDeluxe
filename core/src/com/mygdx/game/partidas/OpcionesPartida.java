package com.mygdx.game.partidas;

public class OpcionesPartida {

	public int numPeces, numComidas;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 1:
			numPeces = 20;
			numComidas = 3;
			break;

		default:
			numPeces = 20;
			numComidas = 3;
			break;
		}
	}
	
}
