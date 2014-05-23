package pt.tecnico.aasma.wireflag.agent.communication.message;

import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class FlagSpotted {
	
	public final MapPosition position;
	
	public final boolean moving;
	
	public final int teamIdentifier;

	public FlagSpotted(MapPosition position, boolean moving, int teamIdentifier) {
		this.position = position;
		this.moving = moving;
		this.teamIdentifier = teamIdentifier;
	}
}
