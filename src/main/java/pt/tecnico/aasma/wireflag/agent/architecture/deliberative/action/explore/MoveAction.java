package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class MoveAction extends Action {

	public MoveAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
	}

	@Override
	public boolean act(Agent agent, int delta) {

		agent.approachTile(delta, position);
		beliefs.countKm();

		return !beliefs.getAgentPos().isSamePosition(position);
	}

	public double getLandUtility() {
		double result = beliefs.getWorldState(position.getX(), position.getY())
				.getLandRating();

		if (previousAction == null) {
			return result;
		} else {
			return result + ((MoveAction) previousAction).getLandUtility();
		}
	}
}
