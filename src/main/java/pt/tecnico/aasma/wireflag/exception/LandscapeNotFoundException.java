package pt.tecnico.aasma.wireflag.exception;

import org.newdawn.slick.SlickException;

public class LandscapeNotFoundException extends SlickException {

	private static final long serialVersionUID = 1L;

	public LandscapeNotFoundException(String landscapeName) {
		super(getMessage(landscapeName));
	}

	/***************
	 *** GETTERS ***
	 ***************/

	private static String getMessage(String landscapeName) {
		return "Landscape with name " + landscapeName + " was not found";
	}
}
