package pt.tecnico.aasma.wireflag.util;

public class WorldPosition {
	
	private float x;
	private float y;

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public WorldPosition(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}


}
