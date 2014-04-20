package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Flag implements IGameElement {

	private Animation flag;
	private MapPosition flagPosition;

	public Flag(MapPosition flagPosition) {
		flag = AnimationLoader.getLoader().getFlag();
		this.flagPosition = flagPosition;
	}

	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		flag.draw(flagPosition.getX() * tileWidth, flagPosition.getY()
				* tileHeight);
	}

	@Override
	public void update(int delta) {
	}
}
