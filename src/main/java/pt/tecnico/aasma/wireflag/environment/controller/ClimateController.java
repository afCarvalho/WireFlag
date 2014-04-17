package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.Fire;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ClimateController implements GameElement {

	private Random random;
	private int pressure;
	private int dryness;
	private MapPosition firePosition;
	private boolean activeFire;

	public ClimateController() {
		random = new Random();
		activeFire = false;
	}

	@Override
	public void init() throws SlickException {
	}

	public void update(int delta) throws SlickException {

		if (random.nextInt(10) < 8) {
			pressure += delta;
		} else {
			pressure -= delta;
		}

		if (random.nextInt(10) < 8) {
			dryness += delta;
		} else {
			dryness -= delta;
		}

		if (pressure > 10000) {
			createClimateEvent();
			pressure = 0;
		}

		if (dryness % 1000 == 0) {
			createFire();
		}
	}

	public void createClimateEvent() throws SlickException {
		MapPosition p = MapController.getMap().getRandomPosition();
		int width = MapController.getMap().getNHorizontalTiles();
		int height = MapController.getMap().getNVerticalTiles();

		while (p.getX() > width - 4 || p.getY() > height - 4) {
			p = MapController.getMap().getRandomPosition();
		}

		for (int x = p.getX(); x < p.getX() + 4; x++) {
			for (int y = p.getY(); y < p.getY() + 4; y++) {
				MapController.getMap().setExtremeWeather(new MapPosition(x, y));
			}
		}
	}

	public void createFire() throws SlickException {

		if (!activeFire && dryness > 10000) {
			firePosition = MapController.getMap().getRandomPosition();
			dryness = 0;
			updateFire();
		} else if (activeFire) {

			int increment = 0;

			if (random.nextInt(2) == 0) {
				increment++;
			} else {
				increment--;
			}

			if (random.nextInt(2) == 0) {
				firePosition.setX(firePosition.getX() + increment);
			} else {
				firePosition.setY(firePosition.getY() + increment);
			}

			updateFire();
		}

	}

	public void updateFire() throws SlickException {
		activeFire = MapController.getMap().getLandscape(firePosition)
				.isInflammable()
				&& !MapController.getMap().getLandscape(firePosition).hasFire();

		if (activeFire) {
			setFire();
		}
	}

	public void setFire() throws SlickException {
		Fire fire = new Fire(firePosition);
		fire.init();
		MapController.getMap().getLandscape(firePosition).setOnFire(fire);
	}

	@Override
	public void render(Graphics g) {
	}

}
