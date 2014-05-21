package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class AbilityAction extends Action {

	public AbilityAction(MapPosition position) {
		super(position);
	}

	@Override
	public boolean act(Beliefs beliefs, Agent agent, int delta) {
		agent.useAbility(position);
		return true;
	}
}
