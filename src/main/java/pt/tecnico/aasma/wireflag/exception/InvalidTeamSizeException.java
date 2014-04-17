package pt.tecnico.aasma.wireflag.exception;

/**
 * The InvalidTeamSizeException signals the creation of a new team without the
 * necessary minimum members.
 */
public class InvalidTeamSizeException extends Exception {

	/** The constant used when serializing. */
	private static final long serialVersionUID = 1L;

	/** The unique identifier of the team. */
	private int identifier;

	/** The size of the team members. */
	private int size;

	public InvalidTeamSizeException(int identifier, int size) {
		this.identifier = identifier;
		this.size = size;
	}
	
	/**
	 * Gets the team identifier.
	 *
	 * @return the identifier
	 */
	public int getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Gets the team size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}

}
