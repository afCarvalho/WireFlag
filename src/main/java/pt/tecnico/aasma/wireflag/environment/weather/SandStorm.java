package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SandStorm extends Weather {

	private Animation sandStorm;

	public SandStorm(int duration, MapPosition position) {
		super(duration, position);
		sandStorm = AnimationLoader.getLoader().getSandStorm();
	}

	@Override
	public void draw(int x, int y) {
		sandStorm.draw(x, y);
	}

}
