package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.DropFlagAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class WinGamePlan extends Plan {

	public WinGamePlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		addAction(new DropFlagAction(beliefs, pos, previousAction), 0, 0);		
	}
}
