package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ThunderStorm extends Weather {

	private Animation thunderStorm;

	public ThunderStorm(int duration, MapPosition position) {
		super(duration, position);
		thunderStorm = AnimationLoader.getLoader().getThunderStorm();
	}

	@Override
	public void draw(int x, int y) {
		thunderStorm.draw(x, y);
	}
}
