package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.GetFlagIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class GetFlagDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		MapPosition flagPos = state.getFlagPos();
		MapPosition agentPos = state.getAgentPos();

		if (flagPos != null) {
			return 100 - flagPos.getDistanceFrom(agentPos);
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new GetFlagIntention();
	}
}
