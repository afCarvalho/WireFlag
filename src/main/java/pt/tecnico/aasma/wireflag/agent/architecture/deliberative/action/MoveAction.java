package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class MoveAction extends Action {

	public MoveAction(MapPosition position) {
		super(position);
	}

	@Override
	public boolean act(Beliefs beliefs, Agent agent, int delta) {
		agent.approachTile(delta, position);
		beliefs.countKm();

		return beliefs.getAgentPos().isSamePosition(position);
	}

}
