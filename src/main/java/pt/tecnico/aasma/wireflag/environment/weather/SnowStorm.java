package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SnowStorm extends Weather {
	
	private Animation snowStorm;

	public SnowStorm(int duration, MapPosition position) {
		super(duration, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws SlickException {
		snowStorm = new Animation(new Image[] { new Image(
				System.getProperty("weather") + "snow.png") }, new int[] { 300 },
				false);

	}

	@Override
	public void draw(int x, int y) {
		snowStorm.draw(x, y);
	}
}
