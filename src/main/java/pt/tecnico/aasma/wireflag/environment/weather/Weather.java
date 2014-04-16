package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public abstract class Weather implements GameElement {

	private int duration;
	private int xCoord;
	private int yCoord;

	public Weather(int duration, int xCoord, int yCoord) {
		this.duration = duration;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	@Override
	public abstract void init() throws SlickException;

	@Override
	public void update(int delta) {
		duration--;
	}

	@Override
	public void render(Graphics g) {
		for (int x = xCoord; x < x + MapController.getMap().getTileWidth(); x++){
			for (int y = yCoord; y < y + MapController.getMap().getTileHeight(); y++) {
				// draw(x, y);
			}
		}
	}

	public abstract void draw(int x, int y);
}
