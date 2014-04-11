package pt.tecnico.aasma.wireflag.environment;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class Climate implements GameElement {

	private String climate = "";
	private Animation rain;
	private Random random;

	public Climate() {
		random = new Random();
	}

	@Override
	public void init() throws SlickException {

		rain = new Animation(new Image[] { new Image("data/rain.png") },
				new int[] { 300 }, false);

	}

	public void update() {
		if (random.nextInt(10) > 90) {
			if (climate.equals("rain"))
				climate = "sun";
			else
				climate = "rain";

		}
	}

	@Override
	public void render(Graphics g) {

		if (climate.equals("rain")) {
			for (int i = 0; i < Map.getMap().getMapHeight(); i++)
				for (int j = 0; j < Map.getMap().getMapWidth(); j++) {
					if (i % 50 == 0 && j % 50 == 0)
						rain.draw(j * 1.0f, i * 1.0f);
				}

		}

	}

}
