package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Fire implements IGameElement {

	private Animation fire;
	private MapPosition firePosition;
	int duration;

	public Fire(int duration, MapPosition firePosition) {
		this.firePosition = firePosition;
		this.duration = duration;
		fire = AnimationLoader.getLoader().getFire();
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean isActive() {
		return duration > 0;
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	@Override
	public void update(int delta) {
		if (duration > 0)
			duration--;
	}

	@Override
	public void render(Graphics g) {
		int width = MapController.getMap().getTileWidth() * firePosition.getX();
		int height = MapController.getMap().getTileHeight()
				* firePosition.getY();

		fire.draw(width, height);
	}
}
