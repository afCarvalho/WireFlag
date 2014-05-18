package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Action {

	protected Beliefs beliefs;
	protected MapPosition position;
	protected Action previousAction;

	public Action(Beliefs beliefs, MapPosition position, Action previousAction) {
		this.beliefs = beliefs;
		this.position = position;
		this.previousAction = previousAction;
	}

	public abstract boolean act(Agent agent, int delta);

	public abstract double getValue();

	public MapPosition getPos() {
		return position;
	}

	public void fillActionsList(LinkedList<Action> actionsList) {
		actionsList.addFirst(this);

		if (previousAction != null) {
			previousAction.fillActionsList(actionsList);
		}
	}
}
