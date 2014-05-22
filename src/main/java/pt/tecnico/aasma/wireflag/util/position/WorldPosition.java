package pt.tecnico.aasma.wireflag.util.position;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;

/* Position dividing the map in pixels: 1400x800 */
public class WorldPosition {

	private float x;
	private float y;

	public WorldPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	/* converts a WorldPosition into a MapPosition */
	public MapPosition getMapPosition() {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		int xPos = (int) x / tileWidth;
		int yPos = (int) y / tileHeight;

		return new MapPosition(xPos, yPos);
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setY(float y) {
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}
}
