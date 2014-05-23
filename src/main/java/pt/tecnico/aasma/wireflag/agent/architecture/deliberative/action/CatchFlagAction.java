package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.communication.Message;
import pt.tecnico.aasma.wireflag.agent.communication.message.HaveFlag;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class CatchFlagAction extends Action {

	public CatchFlagAction(MapPosition position) {
		super(position);
	}

	@Override
	public boolean act(Beliefs beliefs, Agent agent, int delta) {
		agent.catchFlag();
		Message message = new Message(agent, new HaveFlag(agent.getPos().getMapPosition()), true, false);
		AgentController.getAgents().getTeamById(agent.getTeamId()).getDeliverySystem().addMessage(message);
		return true;
	}
}
