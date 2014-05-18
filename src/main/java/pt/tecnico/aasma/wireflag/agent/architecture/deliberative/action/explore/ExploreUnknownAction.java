package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExploreUnknownAction extends MoveAction {

	public ExploreUnknownAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
