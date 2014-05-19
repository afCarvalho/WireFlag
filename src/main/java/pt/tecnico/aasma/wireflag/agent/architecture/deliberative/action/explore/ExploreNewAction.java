package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExploreNewAction extends MoveAction {

	public ExploreNewAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getValue() {
		/*
		 * System.out.println("LAND UTIL " + getLandUtility() + " DANGER UTIL "
		 * + getDangerUtility() + " CONDITION " + (1 +
		 * beliefs.getWorldState(position.getX(), position.getY())
		 * .getCondition()) + " N ACTIONS " + getNActions());
		 */
		return getLandUtility();
	}
}
