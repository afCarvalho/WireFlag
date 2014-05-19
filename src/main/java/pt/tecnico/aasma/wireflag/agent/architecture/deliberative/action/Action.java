package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Action {

	protected Beliefs beliefs;
	protected MapPosition position;
	protected Action previousAction;
	protected boolean isFinished;

	public Action(Beliefs beliefs, MapPosition position, Action previousAction) {
		this.beliefs = beliefs;
		this.position = position;
		this.previousAction = previousAction;
	}

	public abstract boolean act(Agent agent, int delta);

	public MapPosition getPos() {
		return position;
	}

	public void fillActionsList(LinkedList<Action> actionsList) {
		actionsList.addFirst(this);

		if (previousAction != null) {
			previousAction.fillActionsList(actionsList);
		}
	}

	public int getNActions() {
		if (previousAction == null) {
			return 1;
		}
		return 1 + previousAction.getNActions();
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public abstract double getValue();

	public double getSequenceValue() {
		if (previousAction != null) {
			return getValue() + previousAction.getValue();
		} else {
			return getValue();
		}
	}
}
