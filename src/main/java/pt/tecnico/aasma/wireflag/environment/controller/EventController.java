package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.Fire;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class EventController implements GameElement {

	private Random random;
	private int dryness;
	private MapPosition firePos;
	private boolean activeFire;

	public EventController() {
		random = new Random();
		activeFire = false;
	}

	@Override
	public void init() throws SlickException {
	}

	public void update(int delta) throws SlickException {

		if (random.nextInt(10) < 8) {
			dryness += delta;
		} else {
			dryness -= delta;
		}

		if (dryness % 1000 == 0) {
			createFire();
		}
	}

	public void createFire() throws SlickException {

		if (!activeFire && dryness > 10000) {
			firePos = MapController.getMap().getRandomPosition();
			dryness = 0;
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
		Fire fire = new Fire(firePos);
		fire.init();
		MapController.getMap().getLandscape(firePos).setOnFire(fire);
	}

	@Override
	public void render(Graphics g) {
	}

}
