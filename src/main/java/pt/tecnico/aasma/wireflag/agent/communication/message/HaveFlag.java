package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class HaveFlag {
	
	public final MapPosition position;
	
	public HaveFlag(MapPosition position) {
		this.position = position;
	}
	
}
