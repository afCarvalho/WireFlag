package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.OldAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Intention {

	protected abstract int getIntentionId();

	public abstract boolean suceeded(List<Action> actions, Beliefs beliefs);

	public abstract boolean impossible(List<Action> actions, Beliefs beliefs);

	public abstract Plan getPlan(Beliefs beliefs);

	public boolean isSame(Intention actualIntention) {
		return actualIntention.getIntentionId() == getIntentionId();
	}

}
