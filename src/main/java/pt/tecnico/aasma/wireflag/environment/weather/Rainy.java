package pt.tecnico.aasma.wireflag.environment.weather;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Rainy extends Weather {

	private Animation rain;

	public Rainy(int duration, int xCoord, int yCoord) {
		super(duration, xCoord, yCoord);
	}

	@Override
	public void init() throws SlickException {
		rain = new Animation(new Image[] { new Image("data/rain.png") },
				new int[] { 300 }, false);
	}

	@Override
	public void draw(int x, int y) {
		rain.draw(x*1.0f,y*1.0f);
	}

}
