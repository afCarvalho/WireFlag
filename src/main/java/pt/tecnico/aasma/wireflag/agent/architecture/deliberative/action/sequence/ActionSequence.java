package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ActionSequence {

	protected Beliefs beliefs;
	private boolean isFinished;
	protected double value;
	protected LinkedList<Action> actions;

	public ActionSequence(Beliefs beliefs) {
		this.actions = new LinkedList<Action>();
		this.beliefs = beliefs;
	}

	@SuppressWarnings("unchecked")
	public ActionSequence(Beliefs beliefs, ActionSequence sequence) {
		this.actions = (LinkedList<Action>) sequence.getActions().clone();
		// System.out.println("SEQUENCE VALUE " + sequence.value);
		value = sequence.value;
		this.beliefs = beliefs;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public double getSequenceValue() {
		return value;
	}

	public void addAction(Action action) {
		actions.addLast(action);
		value++;
	}

	public MapPosition getTailPos() {
		return actions.getLast().getPos();
	}

	public LinkedList<Action> getActions() {
		return actions;
	}
}
