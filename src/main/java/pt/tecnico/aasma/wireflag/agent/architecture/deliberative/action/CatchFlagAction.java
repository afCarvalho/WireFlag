package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class CatchFlagAction extends Action {

	public CatchFlagAction(Beliefs beliefs, MapPosition position,
			Action previousAction) {
		super(beliefs, position, previousAction);
	}

	@Override
	public boolean act(Agent agent, int delta) {
		agent.catchFlag();
		return true;
	}

	@Override
	public double getValue() {
		return 0;
	}
}
