package com.mygdx.game.partidas;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OpcionesPartida {

	public int numPeces, numComidas, numMonedas, timepoPartida, objetivo, dineroInicial;
	public OpcionesPartida(int nivel) {
		System.out.println(nivel);
		switch (nivel) {
		case 0:
			numPeces = 20;
			numComidas = 3;
			numMonedas = 50;
			timepoPartida = 300;
			objetivo = 500;
			dineroInicial = 10;
			break;
		case 1:
			System.out.println("entra a 1");
			numPeces = 100;
			numComidas = 4;
			numMonedas = 200;
			timepoPartida = 30;
			objetivo = 2000;
			dineroInicial = 1000;
			break;
		default:
			System.out.println("entra a default");
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
