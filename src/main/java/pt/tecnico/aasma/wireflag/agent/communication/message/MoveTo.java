package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class MoveTo {
	
	public final MapPosition position;
	
	public MoveTo(MapPosition position) {
		this.position = position;
	}

}
