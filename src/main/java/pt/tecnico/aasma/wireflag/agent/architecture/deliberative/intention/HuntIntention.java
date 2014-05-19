package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.HuntPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntIntention extends Intention {

	private boolean isDevoured;

	public HuntIntention() {
	}

	public void setDevoured(boolean isDevoured) {
		this.isDevoured = isDevoured;
	}

	@Override
	public boolean suceeded(List<Action> actions, Beliefs beliefs) {
		return isDevoured;
	}

	@Override
	public boolean impossible(List<Action> actions, Beliefs beliefs) {
		MapPosition pos;
		for (Action action : actions) {
			pos = action.getPos();

			if (beliefs.blockedWay(pos.getX(), pos.getY())
					&& !beliefs.getAnimalState().getPosition()
							.isSamePosition(pos)) {
				return true;
			}
		}

		if (!beliefs.getAnimalState().hasAnimal()) {
			return true;
		}

		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new HuntPlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 4;
	}
}
