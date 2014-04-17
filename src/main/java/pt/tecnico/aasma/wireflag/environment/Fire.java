package pt.tecnico.aasma.wireflag.environment;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.Position;

public class Fire implements GameElement {

	private Animation fire;
	private Position firePosition;

	public Fire(Position firePosition) {
		this.firePosition = firePosition;
	}

	@Override
	public void init() throws SlickException {
		fire = new Animation(new Image[] { new Image(System.getProperty("data")
				+ "fire.png") }, new int[] { 300 }, false);

	}

	@Override
	public void render(Graphics g) {
		fire.draw(firePosition.getX() * MapController.getMap().getTileWidth(), firePosition.getY()
				* MapController.getMap().getTileHeight());

	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

}
