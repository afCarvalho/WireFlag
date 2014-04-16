package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;

public class TimeController implements GameElement {

	/** The starting hour of the game */
	private static final int INITIAL_HOUR = 7;
	
	/** The days counter */
	private int days;
	
	/** The hours counter */
	private int hours;
	
	/** The minutes counter */
	private int minutes;
	
	/** The seconds counter */
	private int seconds;

	public TimeController() {
		this.days = 0;
		this.hours = INITIAL_HOUR;
		this.minutes = 0;
		this.seconds = 0;
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

		if (hours == 24) {
			hours = 0;
			days++;
		}
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
		
		if (days > 0) {
			g.drawString(" Time: " + days + "d" + hours + "h" + minutes + "m", 35f,
					MapController.getMap().getMapWidth() / 2);
		} else {
			g.drawString("Time: " + hours + "h" + minutes + "m", 35f,
					MapController.getMap().getMapWidth() / 2);
		}
		

	}
}
