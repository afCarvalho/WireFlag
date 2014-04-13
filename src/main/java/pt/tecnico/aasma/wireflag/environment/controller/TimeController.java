package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class TimeController implements GameElement {

	int hora = 7;
	int minutos = 0;
	int segundos = 0;

	public TimeController() {
	}

	@Override
	public void init() {

	}

	public void update(int delta) {
		segundos += delta;

		if (segundos > 60) {
			segundos = 0;
			minutos += 1;

		}

		if (minutos == 60) {
			minutos = 0;
			hora += 1;
		}

		if (hora == 24)
			hora = 0;
	}

	@Override
	public void render(Graphics g) {

		if (hora > 17 || hora < 6) {
			g.setColor(new Color(0f, 0f, 0f, 0.6f));
			Rectangle r = new Rectangle(0, 0, MapController.getMap().getMapWidth(), MapController
					.getMap().getMapHeight());
			g.draw(r);
			g.fill(r);
		}

		g.drawString("Hora: " + hora + "h : " + minutos + " min ", 20f, 400f);

	}

}
