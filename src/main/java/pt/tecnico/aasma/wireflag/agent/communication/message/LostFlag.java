package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class LostFlag {
	
	public final MapPosition position;
	
	public LostFlag(MapPosition position) {
		this.position = position;
	}
	
}
