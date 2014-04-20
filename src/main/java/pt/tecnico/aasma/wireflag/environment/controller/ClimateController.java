package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Fire;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ClimateController implements IController {

	private Random random;
	private int pressure;
	private int dryness;
	private MapPosition firePos;
	private boolean activeFire;
	private int fireDuration;

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

		if (dryness % 100 == 0) {
			createFire();
		}
	}

	public void createClimateEvent() throws SlickException {

		MapPosition p = MapController.getMap().getRandomPosition();
		int width = MapController.getMap().getNHorizontalTiles();
		int height = MapController.getMap().getNVerticalTiles();
		int duration = new Random().nextInt(10000);

		while (p.getX() > width - 4 || p.getY() > height - 4) {
			p = MapController.getMap().getRandomPosition();
		}

		for (int x = p.getX(); x < p.getX() + 4; x++) {
			for (int y = p.getY(); y < p.getY() + 4; y++) {
				MapController.getMap().getLandscape(new MapPosition(x, y))
						.setExtremeWeather(duration);
			}
		}
	}

	public void createFire() throws SlickException {

		if ((!activeFire && dryness > 1000) || (activeFire && dryness > 25000)) {
			firePos = MapController.getMap().getRandomPosition();
			dryness = 0;
			fireDuration = random.nextInt(10000);
			updateFire();
		} else if (activeFire) {

			int incr = 0;

			if (random.nextInt(2) == 0) {
				incr++;
			} else {
				incr--;
			}

			if (random.nextInt(2) == 0) {
				firePos = new MapPosition(firePos.getX() + incr, firePos.getY());
			} else {
				firePos = new MapPosition(firePos.getX(), firePos.getY() + incr);
			}

			updateFire();
		}

	}

	public void updateFire() throws SlickException {
		activeFire = MapController.getMap().getLandscape(firePos)
				.isInflammable()
				&& !MapController.getMap().getLandscape(firePos).hasFire();

		if (activeFire) {
			setFire();
		}
	}

	public void setFire() throws SlickException {
		Fire fire = new Fire(fireDuration, firePos);
		MapController.getMap().getLandscape(firePos).setOnFire(fire);
	}

	@Override
	public void render(Graphics g) {
	}

}
