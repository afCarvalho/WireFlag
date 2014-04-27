package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Flag {

	private Animation flag;

	public Flag(MapPosition flagPosition) {
		flag = AnimationLoader.getLoader().getFlag();
	}

	public void draw(float x, float y) {
		flag.draw(x, y);
	}

}
