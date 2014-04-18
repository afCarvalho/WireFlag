package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ThunderStorm extends Weather {

	private Animation thunderStorm;

	public ThunderStorm(int duration, MapPosition position) {
		super(duration, position);
	}

	@Override
	public void init() throws SlickException {
		thunderStorm = new Animation(new Image[] { new Image(
				System.getProperty("weather") + "thunder.png") }, new int[] { 300 },
				false);
	}

	@Override
	public void draw(int x, int y) {
		thunderStorm.draw(x, y);
	}
}
