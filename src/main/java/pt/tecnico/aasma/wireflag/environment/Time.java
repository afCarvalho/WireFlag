package pt.tecnico.aasma.wireflag.environment;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class Time implements GameElement {

	int hora = 7;
	int minutos = 0;
	double segundos = 0;
	private WireFlagGame game;

	public Time(WireFlagGame game) {
		this.game = game;
	}

	@Override
	public void init() {

	}

	public void update() {
		segundos += 1;

		if (segundos == 1000) {
			segundos = 0;
			minutos += 10;

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
			Rectangle r = new Rectangle(0, 0, game.getMapWidth(),
					game.getMapHeight());
			g.draw(r);
			g.fill(r);
		}

		g.drawString("Hora: " + hora + "h : " + minutos + " min ", 20f, 400f);

	}

}
