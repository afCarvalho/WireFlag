package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Action {

	protected MapPosition position;

	public Action(MapPosition position) {
		this.position = position;
	}

	public abstract boolean act(Beliefs beliefs, Agent agent, int delta);

	public MapPosition getPos() {
		return position;
	}
}
