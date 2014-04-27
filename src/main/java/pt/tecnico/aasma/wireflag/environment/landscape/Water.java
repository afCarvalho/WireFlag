package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Water extends Landscape {

	public Water(MapPosition position) {
		super(VREDUCEDSPD, position, REDUCEDVSB, VHIGHFATIGUE);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new ThunderStorm(duration, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return false;
	}

	@Override
	public Animal createAnimal() {
		animal = new Animal(landscapePos, AnimationLoader.getLoader()
				.getTurtle());
		return animal;
	}

	@Override
	public void render(Graphics g) {
		weather.render(g);

		if (hasFire()) {
			fire.render(g);
		}

		if (hasAnimal()) {
			animal.render(g);
		}

		if (hasEndPoint()) {
			endPoint.render(g);
		}

		if (hasAgent()) {
			agent.render(g);
			AnimationLoader.getLoader().getBoat()
					.draw(agent.getPos().getX(), agent.getPos().getY());
		}
	}

}
