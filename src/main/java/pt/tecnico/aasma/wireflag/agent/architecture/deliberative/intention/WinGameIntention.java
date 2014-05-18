package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.OldAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.WinGamePlan;
import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class WinGameIntention extends Intention {

	@Override
	public boolean suceeded(List<Action> actions, Beliefs beliefs) {
		return EndGameController.getEnd().getGameFinished();
	}

	@Override
	public boolean impossible(List<Action> actions, Beliefs beliefs) {
		MapPosition pos;
		for (Action action : actions) {
			pos = action.getPos();
			if (beliefs.blockedWay(pos.getX(), pos.getY())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new WinGamePlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 6;
	}
}
