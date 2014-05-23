package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntAction extends Action {

	int xInc;
	int yInc;

	public HuntAction(MapPosition position, int xInc, int yInc) {
		super(position);
		this.xInc = xInc;
		this.yInc = yInc;
	}

	@Override
	public boolean act(Beliefs beliefs, Agent agent, int delta) {
		agent.hunt(new MapPosition(position.getX() + xInc, position.getY()
				+ yInc));
		return true;
	}
}
