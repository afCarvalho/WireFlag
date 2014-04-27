package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.EndPoint;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class ObjectController implements IController {

	private Animal[] animals;
	private Flag flag;

	public ObjectController() {
		animals = new Animal[20];
	}

	/********************
	 *** GAME RELATED ***
	 ********************/

	@Override
	public void init() throws SlickException {

		for (int i = 0; i < animals.length; i++) {
			animals[i] = createAnimal();
		}

		MapPosition endPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(endPos)) {
			endPos = MapController.getMap().getRandomPosition();
		}

		MapController.getMap().getLandscape(endPos)
				.setEndPoint(new EndPoint(endPos));

		MapPosition flagPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(flagPos)) {
			flagPos = MapController.getMap().getRandomPosition();
		}

		flag = new Flag(new WorldPosition(flagPos.getX()
				* MapController.getMap().getMapWidth(), flagPos.getY()
				* MapController.getMap().getMapHeight()));
		MapController.getMap().getLandscape(flagPos).setFlag(flag);
	}

	public void update(int delta) throws SlickException {

		for (int i = 0; i < animals.length; i++) {
			if (!animals[i].isAlive()) {
				animals[i] = createAnimal();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		flag.render(g);
	}

	public Animal createAnimal() throws SlickException {
		MapPosition animalPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(animalPos)) {
			animalPos = MapController.getMap().getRandomPosition();
		}

		return MapController.getMap().getLandscape(animalPos).createAnimal();
	}
}
