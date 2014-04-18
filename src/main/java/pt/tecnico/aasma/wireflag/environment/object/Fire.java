package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Fire implements IGameElement {

	private Animation fire;
	private MapPosition firePosition;
	int duration;

	public Fire(int duration, MapPosition firePosition) {
		this.firePosition = firePosition;
		this.duration = duration;
		fire=AnimationLoader.getLoader().getFire();
	}

	@Override
	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		fire.draw(firePosition.getX() * tileWidth, firePosition.getY()
				* tileHeight);
	}

	@Override
	public void update(int delta) {
		if (duration > 0)
			duration--;
	}

	public boolean isActive() {
		return duration > 0;
	}
}
