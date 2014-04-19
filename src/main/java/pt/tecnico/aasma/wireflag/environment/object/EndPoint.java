package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class EndPoint implements IGameElement {

	private Animation endPoint;
	private MapPosition endPos;

	public EndPoint(MapPosition endPos) {
		endPoint = AnimationLoader.getLoader().getEndPoint();
		this.endPos = endPos;
	}

	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		endPoint.draw(endPos.getX() * tileWidth, endPos.getY() * tileHeight);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}
}
