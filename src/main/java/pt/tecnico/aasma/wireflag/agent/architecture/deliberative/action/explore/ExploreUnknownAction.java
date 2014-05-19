package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExploreUnknownAction extends MoveAction {

	public ExploreUnknownAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
	}

	@Override
	public double getValue() {
		return ((getLandUtility() * getDangerUtility()) / (getNActions()))
				+ getNUnknownAdjacent();
	}

	public double getNUnknownAdjacent() {

		MapPosition pos = beliefs.getWorldState(position.getX(),
				position.getY()).getPosition();
		MapPosition agentPos = beliefs.getAgentPos();
		double result = 10;
		double distance = agentPos.getDistanceFrom(pos);

		WorldState p1 = beliefs.getWorldState(pos.getX() + 1, pos.getY());
		WorldState p2 = beliefs.getWorldState(pos.getX() - 1, pos.getY());
		WorldState p3 = beliefs.getWorldState(pos.getX(), pos.getY() + 1);
		WorldState p4 = beliefs.getWorldState(pos.getX(), pos.getY() - 1);

		if (p1 != null && p1.isUnknown()) {
			result = result / distance;
		} else if (p2 != null && p2.isUnknown()) {
			result = result / distance;
		} else if (p3 != null && p3.isUnknown()) {
			result = result / distance;
		} else if (p4 != null && p4.isUnknown()) {
			result = result / distance;
		}

		return result;
	}

}
