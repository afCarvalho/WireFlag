package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.EndPoint;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ObjectController implements IController {

	private Animal[] animals;

	public ObjectController() {
		animals = new Animal[10];
	}

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

		MapController.getMap().getLandscape(flagPos).setFlag(new Flag(flagPos));

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
	}

	public Animal createAnimal() throws SlickException {

		MapPosition animalPos = MapController.getMap().getRandomPosition();

		while (MapController.getMap().isBlocked(animalPos)) {
			animalPos = MapController.getMap().getRandomPosition();
		}

		Animal animal = new Animal(animalPos);
		MapController.getMap().getLandscape(animalPos).setAnimal(animal);

		return animal;
	}

}
