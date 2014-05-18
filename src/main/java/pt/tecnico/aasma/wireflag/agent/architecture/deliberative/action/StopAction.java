package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class StopAction extends Action {

	public StopAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
	}

	@Override
	public boolean act(Agent agent, int delta) {
		agent.stop();
		beliefs.resetKm();
		return true;
	}

	@Override
	public double getValue() {
		double stopUtility = 0;
		double stopFactor = getStopUtility();
		double illFactor = getIllUtility();
		double lifeFactor = getLifeUtility();

		if (stopFactor != 0 && stopFactor < beliefs.getFatigue() + 1) {
			stopUtility = stopUtility + stopFactor;
		} else if (illFactor != 0 && illFactor < 100 - beliefs.getLife()) {
			stopUtility = stopUtility + illFactor;
		} else if (lifeFactor != 0 && lifeFactor < 100 - beliefs.getLife()) {
			stopUtility = stopUtility + lifeFactor;
		}

		return stopUtility;
	}

	private double getStopUtility() {
		if (beliefs.shouldStop() && previousAction != null) {
			return 5 + ((StopAction) previousAction).getStopUtility();
		} else if (beliefs.shouldStop() && previousAction == null) {
			return 5;
		} else {
			return 0;
		}
	}

	private double getIllUtility() {
		if (beliefs.isIll() && previousAction != null) {
			return 1 + ((StopAction) previousAction).getIllUtility();
		} else if (beliefs.isIll() && previousAction == null) {
			return 1;
		} else {
			return 0;
		}
	}

	private double getLifeUtility() {
		if (beliefs.getLife() < 30 && previousAction != null) {
			return 1 + ((StopAction) previousAction).getLifeUtility();
		} else if (beliefs.getLife() < 30 && previousAction == null) {
			return 1;
		} else {
			return 0;
		}
	}
}
