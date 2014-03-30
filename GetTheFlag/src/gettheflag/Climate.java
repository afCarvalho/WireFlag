package gettheflag;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Climate implements GameElement {

	private String climate = "";
	private Animation rain;
	private Random random;
	private WireFlagGame game;

	public Climate(WireFlagGame game) {

		random = new Random();
		this.game = game;

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
			for (int i = 0; i < game.getMapHeight(); i++)
				for (int j = 0; j < game.getMapWidth(); j++) {
					if (i % 50 == 0 && j % 50 == 0)
						rain.draw(j * 1.0f, i * 1.0f);
				}

		}

	}

}
