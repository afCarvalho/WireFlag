package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Rainy extends Weather {

	private Animation rain;

	public Rainy(int duration, MapPosition position) {
		super(duration, position);
		rain = AnimationLoader.getLoader().getRain();
	}

	@Override
	public void draw(int x, int y) {
		rain.draw(x, y);
	}

}
