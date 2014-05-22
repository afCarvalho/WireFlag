package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.TeamBase;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;
import pt.tecnico.aasma.wireflag.util.position.WorldPosition;

public class ObjectController implements IController {

	private Animal[] animals;
	private Flag flag;
	private static final ObjectController INSTANCE = new ObjectController();

	private ObjectController() {
		animals = new Animal[20];
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public static ObjectController getObjects() {
		return INSTANCE;
	}

	/********************
	 *** GAME RELATED ***
	 ********************/

	@Override
	public void init() throws SlickException {

		for (int i = 0; i < animals.length; i++) {
			animals[i] = createAnimal();
		}

		MapPosition flagPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(flagPos)) {
			flagPos = MapController.getMap().getRandomPosition();
		}

		flag = new Flag(new WorldPosition(flagPos.getX()
				* MapController.getMap().getTileWidth(), flagPos.getY()
				* MapController.getMap().getTileHeight()));
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
