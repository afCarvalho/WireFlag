package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.GetFlagIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class GetFlagDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		MapPosition flagPos = beliefs.getFlagPos();
		MapPosition agentPos = beliefs.getAgentPos();

		if (beliefs.hasFlagPos() && !beliefs.carriesFlag()) {
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
