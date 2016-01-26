package com.mygdx.game.partidas;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas, timepoPartida, objetivo;
	public OpcionesPartida(int nivel) {
		switch (nivel) {
		case 1:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			objetivo = 500;
			break;

		default:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			objetivo = 500;
			//objectivo.setBackground("badlogic.png");
			break;
		}
	}
	
}
