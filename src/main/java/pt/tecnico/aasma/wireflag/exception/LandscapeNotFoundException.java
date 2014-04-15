package pt.tecnico.aasma.wireflag.exception;

public class LandscapeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String landscapeName;

	public LandscapeNotFoundException(String landscapeName) {
		this.landscapeName = landscapeName;
	}

	public String getMessage() {
		return "Landscape with name " + landscapeName + " was not found";
	}
}
