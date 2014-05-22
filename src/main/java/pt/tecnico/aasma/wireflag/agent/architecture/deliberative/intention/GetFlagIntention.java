package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.GetFlagPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class GetFlagIntention extends Intention {

	public GetFlagIntention() {
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		//System.err.println("HAS FLAG " + beliefs.carriesFlag());
		return beliefs.carriesFlag();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		MapPosition pos;
		for (Action action : actions) {
			pos = action.getPos();

			if (beliefs.blockedWay(pos.getX(), pos.getY())) {
				//System.err.println("BLOCKED WAY");
				return true;
			}
		}

		if (!beliefs.getWorldState(beliefs.getFlagPos().getX(),
				beliefs.getFlagPos().getY()).hasFlag()) {
			//System.err.println("FLAG GONE");
			return true;
		}

		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new GetFlagPlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 4;
	}
}
