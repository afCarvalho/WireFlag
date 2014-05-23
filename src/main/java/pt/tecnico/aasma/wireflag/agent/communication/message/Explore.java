package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Explore {
	
	public final MapPosition position;
	
	public Explore(MapPosition position) {
		this.position = position;
	}

}
