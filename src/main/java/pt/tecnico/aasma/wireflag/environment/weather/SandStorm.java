package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SandStorm extends Weather {

	private Animation sandStorm;

	public SandStorm(int duration, MapPosition position) {
		super(duration, position);
	}

	@Override
	public void init() throws SlickException {
		sandStorm = new Animation(new Image[] { new Image(
				System.getProperty("weather") + "sand.png") },
				new int[] { 300 }, false);

	}

	@Override
	public void draw(int x, int y) {
		sandStorm.draw(x, y);
	}

}
