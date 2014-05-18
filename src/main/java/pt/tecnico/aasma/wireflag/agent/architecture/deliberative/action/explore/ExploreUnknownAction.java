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
		return (((getLandUtility())) / (getNActions()))
				+ ((getNUnknownAdjacent()));
	}

	public double getNUnknownAdjacent() {

		MapPosition pos = beliefs.getWorldState(position.getX(),
				position.getY()).getPosition();
		MapPosition aPos = beliefs.getAgentPos();
		double result = 1;
		double distance = Math.abs(pos.getX() - aPos.getX())
				+ Math.abs(pos.getY() - aPos.getY()) + 1;

		WorldState p1 = beliefs.getWorldState(pos.getX() + 1, pos.getY());
		WorldState p2 = beliefs.getWorldState(pos.getX() - 1, pos.getY());
		WorldState p3 = beliefs.getWorldState(pos.getX(), pos.getY() + 1);
		WorldState p4 = beliefs.getWorldState(pos.getX(), pos.getY() - 1);

		if (p1 != null && p1.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p2 != null && p2.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p3 != null && p3.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p4 != null && p4.isUnknown()) {
			result += 10 * result / distance;
		}

		return result;
	}

}
