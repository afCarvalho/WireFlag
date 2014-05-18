package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntAction extends Action {

	Animal animal;

	public HuntAction(Beliefs beliefs, Animal animal, MapPosition position,Action previouAction) {
		super(beliefs, position, previouAction);
		this.animal = animal;
	}

	@Override
	public boolean act(Agent agent, int delta) {
		agent.hunt(animal);
		return true;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
