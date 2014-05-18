package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.WireFlagGame;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class DropFlagAction extends Action {

	public DropFlagAction(Beliefs beliefs, MapPosition position,
			Action previousAction) {
		super(beliefs, position, previousAction);
	}

	@Override
	public boolean act(Agent agent, int delta) {
		agent.dropFlag();
		WireFlagGame.win(agent.getTeamId());
		return true;
	}

	@Override
	public double getValue() {
		return getEndUtility();
	}

	private double getEndUtility() {
		if (beliefs.hasEndPos()
				&& beliefs.getEndPos().isAdjacentPosition(position,
						beliefs.getDirection()) && beliefs.carriesFlag()) {
			return 100;
		} else {
			return 1;
		}
	}
}
