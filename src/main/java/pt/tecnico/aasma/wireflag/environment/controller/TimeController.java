package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class TimeController implements GameElement {

	int hours = 7;
	int minutes = 0;
	int seconds = 0;

	public TimeController() {
	}

	@Override
	public void init() {

	}

	public void update(int delta) {
		seconds += delta;

		if (seconds > 60) {
			seconds = 0;
			minutes += 1;
		}

		if (minutes == 60) {
			minutes = 0;
			hours += 1;
		}

		if (hours == 24)
			hours = 0;
	}

	@Override
	public void render(Graphics g) {

		if (hours > 17 || hours < 6) {
			g.setColor(new Color(0f, 0f, 0f, 0.6f));
			Rectangle r = new Rectangle(0, 0, MapController.getMap()
					.getMapWidth(), MapController.getMap().getMapHeight());
			g.draw(r);
			g.fill(r);
		}
		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString("Hora: " + hours + "h : " + minutes + " min ", 35f,
				MapController.getMap().getMapWidth() / 2);

	}

}
