package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.HuntPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntIntention extends Intention {

	public HuntIntention() {
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return !beliefs.getAnimalState().hasAnimal();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
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
		return new HuntPlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return HUNT;
	}
}
