package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SpawnSpotted {
	
	public final MapPosition position;
	
	public final int teamIdentifier;
	
	public SpawnSpotted(MapPosition position, int teamIdentifier) {
		this.position = position;
		this.teamIdentifier = teamIdentifier;
	}

}
