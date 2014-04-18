package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SnowStorm extends Weather {

	private Animation snowStorm;

	public SnowStorm(int duration, MapPosition position) {
		super(duration, position);
		snowStorm = AnimationLoader.getLoader().getSnowStorm();
	}

	@Override
	public void draw(int x, int y) {
		snowStorm.draw(x, y);
	}
}
