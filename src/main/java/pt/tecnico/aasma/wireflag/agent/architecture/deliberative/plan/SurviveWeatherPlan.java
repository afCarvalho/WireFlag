package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class SurviveWeatherPlan extends Plan {

	public SurviveWeatherPlan(Beliefs beliefs) {
		super(beliefs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		ActionSequence seq;

		if (actionSeq == null) {
			seq = new ActionSequence(beliefs);
		} else {
			seq = new ActionSequence(beliefs, actionSeq);
		}

		seq.addAction(new MoveAction(pos));
		actSequences.add(seq);

		if (!beliefs.getWorldState(pos.getX(), pos.getY()).hasExtremeWeather()) {
			seq.setFinished(true);
		}
	}
}
