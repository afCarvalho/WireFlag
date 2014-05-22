package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;

public abstract class Intention {

	protected final int ABILITY = 0;
	protected final int BATTLE = 1;
	protected final int CURE = 2;
	protected final int EXPLORE = 3;
	protected final int FLEE = 4;
	protected final int FLAG = 5;
	protected final int HEAL = 6;
	protected final int HUNT = 7;
	protected final int REST = 8;
	protected final int STOP = 9;
	protected final int FIRE = 10;
	protected final int WEATHER = 11;
	protected final int WAIT = 12;
	protected final int WIN = 13;

	protected abstract int getIntentionId();

	public abstract boolean suceeded(LinkedList<Action> actions, Beliefs beliefs);

	public abstract boolean impossible(LinkedList<Action> actions,
			Beliefs beliefs);

	public abstract Plan getPlan(Beliefs beliefs);

	public boolean isSame(Intention actualIntention) {
		return actualIntention.getIntentionId() == getIntentionId();
	}

	public int getId() {
		return getIntentionId();
	}

}
