package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.OldAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.ExplorePlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExploreIntention extends Intention {

	@Override
	public boolean suceeded(List<Action> actions, Beliefs beliefs) {
		return beliefs.getWorldExploredPercentage() == 100;
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
		return new ExplorePlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 1;
	}
}
