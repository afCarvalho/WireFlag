package pt.tecnico.aasma.wireflag.environment.landscape;

public abstract class Landscape {

	protected final static float NORMALSPD = 1f;
	protected final static float REDUCEDSPD = 0.5f;
	protected final static float VREDUCEDSPD = 0.1f;
	protected final static float NOSPD = 0f;
	protected float movementSpeed;

	public Landscape(float movementSpeed) {
		this.setMovementSpeed(movementSpeed);
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
}
