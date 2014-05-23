package pt.tecnico.aasma.wireflag.agent.architecture;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import pt.tecnico.aasma.wireflag.WireFlagGame;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.Message;
import pt.tecnico.aasma.wireflag.agent.communication.message.FlagSpotted;
import pt.tecnico.aasma.wireflag.agent.communication.message.Halt;
import pt.tecnico.aasma.wireflag.agent.communication.message.HaveFlag;
import pt.tecnico.aasma.wireflag.agent.communication.message.SpawnSpotted;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Reactive extends Architecture {

	/* total number of behaviors */
	protected final static int BEHAVIOR_SIZE = 20;

	private List<Perception> perceptions;

	private boolean doHalt = false;

	public Reactive() {
		perceptions = new ArrayList<Perception>();
	}

	/* returns the perception at position actualPos */
	private Perception getPerceptionPos(MapPosition actualPos) {
		for (Perception perception : perceptions) {
			if (actualPos.isSamePosition(perception.getPosition())) {
				return perception;
			}
		}

		return null;
	}

	/* returns a list with the perceptions of the agent adjacent positions */
	private List<Perception> getPerceptionsAdj(Agent agent) {
		List<Perception> perceptionsAdj = new ArrayList<Perception>();
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isAdjacentPosition(perception.getPosition(),
					agent.getDirection())) {
				perceptionsAdj.add(perception);
			}
		}

		return perceptionsAdj;
	}

	/* if the agent has the flag and the team's base is on an adjacent position */
	public boolean reactivePerception0(Agent agent) {

		if (agent.hasFlag()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (perception.hasTeamBase()
						&& perception.getTeamBaseId() == agent.getTeamId()) {
					return true;
				}
			}
		}

		return false;
	}

	public void doAction0(Agent agent, int delta) {
		WireFlagGame.win(agent.getTeamId());
	}

	/* if the flag is in the agent's actual position */
	public boolean reactivePerception1(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		return getPerceptionPos(actualPos).hasFlag();
	}

	public void doAction1(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (getPerceptionPos(actualPos).hasFlag()) {
			agent.catchFlag();
			Message msg = new Message(agent, new HaveFlag(actualPos), true,
					false);
			AgentController.getAgents().getTeamById(agent.getTeamId())
					.getDeliverySystem().addMessage(msg);
		}
	}

	/*
	 * if exists an enemy in an adjacent position and the agent has low life,
	 * then the agent runs away
	 */
	public boolean reactivePerception2(Agent agent) {

		for (Perception perception : getPerceptionsAdj(agent)) {
			if (perception.hasEnemy() && agent.hasLowLife()) {
				return true;
			}
		}

		return false;
	}

	public void doAction2(Agent agent, int delta) {

		for (Perception perception : getPerceptionsAdj(agent)) {
			if (perception.hasEnemy() && agent.hasLowLife()) {

				agent.moveDifferentDirection(delta);
				return;
			}
		}
	}

	/*
	 * if has very low life and a teammate is in it's visibility, the agent
	 * sends a stop message to the teammate and stops
	 */
	public boolean reactivePerception3(Agent agent) {

		if (agent.hasVeryLowLife()) {
			for (Perception perception : perceptions) {
				if (perception.getAgent() != null
						&& perception.getAgent().getAgentId() != agent
								.getAgentId() && !perception.hasEnemy()) {
					return true;
				}
			}
		}
		return false;
	}

	public void doAction3(Agent agent, int delta) {

		if (agent.hasVeryLowLife()) {
			for (Perception perception : perceptions) {
				if (perception.getAgent() != null
						&& perception.getAgent().getAgentId() != agent
								.getAgentId() && !perception.hasEnemy()) {
					Message msg = new Message(agent, new Halt(), false, false);
					AgentController.getAgents().getTeamById(agent.getTeamId())
							.getDeliverySystem().addMessage(msg);
					while (agent.getLife() < 100) {
						agent.stop();
					}
				}
			}
		}
	}

	/* agent stops if has very low life */
	public boolean reactivePerception4(Agent agent) {
		return agent.hasVeryLowLife();
	}

	public void doAction4(Agent agent, int delta) {
		while (agent.getLife() < 100) {
			agent.stop();
		}
	}

	/* agent stops if has extreme fatigue */
	public boolean reactivePerception5(Agent agent) {
		return agent.hasFatigue();
	}

	public void doAction5(Agent agent, int delta) {
		while (agent.getFatigue() > 0) {
			agent.stop();
		}
	}

	/*
	 * when an animal is an adjacent position and the agent has low or very low
	 * life it is devoured
	 */
	public boolean reactivePerception6(Agent agent) {
		/*
		 * an agent with low life also has very low life, so is only necessary
		 * verify one condition (has low life)
		 */
		if (agent.hasLowLife()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (perception.hasAnimal()) {
					return true;
				}
			}
		}

		return false;
	}

	public void doAction6(Agent agent, int delta) {
		for (Perception perception : getPerceptionsAdj(agent)) {
			if (perception.hasAnimal()) {
				agent.hunt(perception.getPosition());
				return;
			}
		}
	}

	/*
	 * if an agent has low life and an animal is in its visibility then agent
	 * approaches the animal position
	 */
	public boolean reactivePerception7(Agent agent) {

		if (agent.hasLowLife()) {
			for (Perception perception : perceptions) {
				if (perception.hasAnimal()) {
					return true;
				}
			}
		}

		return false;
	}

	public void doAction7(Agent agent, int delta) {
		for (Perception perception : perceptions) {
			if (perception.hasAnimal()) {

				agent.approachTile(delta, perception.getPosition());
				return;
			}
		}
	}

	/*
	 * if the agent's actual position has fire it moves to adjacent position if
	 * it is available
	 */
	public boolean reactivePerception8(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();
		Perception perceptionAgentPos = getPerceptionPos(actualPos);
		boolean result = false;

		if (perceptionAgentPos.hasFire()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (!result) {
					result = !perception.hasFire() && !perception.isBlocked();
				} else {
					return result;
				}
			}
		}

		return result;
	}

	public void doAction8(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();
		Perception perceptionAgentPos = getPerceptionPos(actualPos);

		if (perceptionAgentPos.hasFire()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (!perception.hasFire() && !perception.isBlocked()) {

					agent.approachTile(delta, perception.getPosition());
					return;
				}
			}
		}
	}

	/*
	 * if the agent actual position has fire and exists one better position, in
	 * the agent's visibility, the agent approaches it.
	 */
	public boolean reactivePerception9(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();
		boolean result = false;

		if (getPerceptionPos(actualPos).hasFire()) {
			for (Perception perception : perceptions) {

				if (!result) {
					result = !perception.hasFire() && !perception.isBlocked();
				} else {
					return result;
				}
			}
		}

		return result;
	}

	public void doAction9(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (getPerceptionPos(actualPos).hasFire()) {
			for (Perception perception : perceptions) {
				if (!perception.hasFire() && !perception.isBlocked()) {
					agent.approachTile(delta, perception.getPosition());
					return;
				}
			}
		}
	}

	/*
	 * if the agent actual position has extreme weather it moves to adjacent
	 * position if it is available
	 */
	public boolean reactivePerception10(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();
		boolean result = false;

		if (getPerceptionPos(actualPos).hasExtremeWeather()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (!result) {
					result = !perception.hasExtremeWeather()
							&& !perception.isBlocked();
				} else {
					return result;
				}
			}
		}

		return result;
	}

	public void doAction10(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (getPerceptionPos(actualPos).hasExtremeWeather()) {
			for (Perception perception : getPerceptionsAdj(agent)) {
				if (!perception.hasExtremeWeather() && !perception.isBlocked()) {

					agent.approachTile(delta, perception.getPosition());
					return;
				}
			}
		}
	}

	/*
	 * if the agent actual position has extreme weather and exists one better
	 * position in the agent's visibility, the agent approaches it.
	 */
	public boolean reactivePerception11(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();
		boolean result = false;

		if (getPerceptionPos(actualPos).hasExtremeWeather()) {
			for (Perception perception : perceptions) {
				if (!result) {
					result = !perception.hasExtremeWeather()
							&& !perception.isBlocked();
				} else {
					return result;
				}
			}
		}

		return result;
	}

	public void doAction11(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (getPerceptionPos(actualPos).hasExtremeWeather()) {
			for (Perception perception : perceptions) {
				if (!perception.hasExtremeWeather() && !perception.isBlocked()) {
					agent.approachTile(delta, perception.getPosition());
					return;
				}
			}
		}
	}

	/*
	 * if the agent's just ahead position has fire or extreme weather then agent
	 * switches it's direction unless the adjacent positions has extreme weather
	 * or fire.
	 */
	public boolean reactivePerception12(Agent agent) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		Perception aheadPerception = getPerceptionPos(actualPos
				.getAheadPosition(agent.getDirection()));

		if (aheadPerception != null
				&& (aheadPerception.hasFire() || aheadPerception
						.hasExtremeWeather())) {
			for (Perception perceptionAdj : getPerceptionsAdj(agent)) {
				if (!(perceptionAdj.hasFire() || perceptionAdj
						.hasExtremeWeather())) {
					return true;
				}
			}
		}

		return false;
	}

	public void doAction12(Agent agent, int delta) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		Perception aheadPerception = getPerceptionPos(actualPos
				.getAheadPosition(agent.getDirection()));

		if (aheadPerception.hasFire() || aheadPerception.hasExtremeWeather()) {
			for (Perception perceptionAdj : getPerceptionsAdj(agent)) {
				if (!(perceptionAdj.hasFire() || perceptionAdj
						.hasExtremeWeather())) {
					agent.moveDifferentDirection(delta);
					return;
				}
			}
		}
	}

	/*
	 * if the agent has the flag and the team's base is in it's visibility, then
	 * agent approaches base
	 */
	public boolean reactivePerception13(Agent agent) {

		if (agent.hasFlag()) {
			for (Perception perception : perceptions) {
				if (perception.hasTeamBase()
						&& perception.getTeamBaseId() == agent.getTeamId()) {
					return true;
				}
			}
		}

		return false;
	}

	public void doAction13(Agent agent, int delta) {
		if (agent.hasFlag()) {
			for (Perception perception : perceptions) {
				if (perception.hasTeamBase()
						&& perception.getTeamBaseId() == agent.getTeamId()) {
					agent.approachTile(delta, perception.getPosition());
					return;
				}
			}
		}
	}

	/*
	 * if the agent has the flag in it's visibility, then agent approaches the
	 * flag position
	 */
	public boolean reactivePerception14(Agent agent) {
		for (Perception perception : perceptions) {
			if (perception.hasFlag()) {
				return true;
			}
		}

		return false;
	}

	public void doAction14(Agent agent, int delta) {
		for (Perception perception : perceptions) {
			if (perception.hasFlag()) {
				boolean isPatrol = false;
				if (agent instanceof Patrol) {
					isPatrol = true;
				}
				Message msg = new Message(agent, new FlagSpotted(
						perception.getPosition(), false, -1), true, isPatrol);
				AgentController.getAgents().getTeamById(agent.getTeamId())
						.getDeliverySystem().addMessage(msg);

				agent.approachTile(delta, perception.getPosition());
				return;
			}
		}
	}

	/* Agent uses it's ability if exists a reason */
	public boolean reactivePerception15(Agent agent) {
		for (Perception perception : perceptions) {
			if (agent.isAbilityUseful(perception.getPosition())) {
				return true;
			}
		}

		return false;
	}

	public void doAction15(Agent agent, int delta) {
		for (Perception perception : perceptions) {
			if (agent.isAbilityUseful(perception.getPosition())) {
				agent.useAbility(perception.getPosition());
				return;
			}
		}
	}

	/*
	 * if exists an enemy in an adjacent position and the agent does not have
	 * low life, then agent attacks that enemy
	 */
	public boolean reactivePerception16(Agent agent) {

		for (Perception perception : getPerceptionsAdj(agent)) {
			if (perception.hasEnemy() && !agent.hasLowLife()) {
				return true;
			}
		}

		return false;
	}

	public void doAction16(Agent agent, int delta) {

		for (Perception perception : getPerceptionsAdj(agent)) {
			if (perception.hasEnemy() && !agent.hasLowLife()) {

				MapPosition enemyPos = perception.getPosition();
				agent.confront(enemyPos);
				return;
			}
		}
	}

	/* if a teammate asks agent to stop */
	public boolean reactivePerception17(Agent agent) {

		return doHalt;
	}

	public void doAction17(Agent agent, int delta) {

		if (doHalt) {
			agent.stop();
			return;
		}
	}

	/*
	 * Rule: Position ahead blocked. Returns true if the agent can move ahead,
	 * and false otherwise.
	 */
	public boolean reactivePerception18(Agent agent) {

		MapPosition actualPosition = agent.getPos().getMapPosition();
		MapPosition aheadPosition = actualPosition.getAheadPosition(agent
				.getDirection());

		return MapController.getMap().isBlocked(aheadPosition);
	}

	/* position ahead blocked */
	public void doAction18(Agent agent, int delta) {
		agent.moveDifferentDirection(delta);
	}

	/* true case */
	public boolean reactivePerception19(Agent agent) {
		return true;
	}

	/* true case */
	public void doAction19(Agent agent, int delta) {
		agent.moveSameDirection(delta);
	}

	public void makeAction(Agent agent, int delta) {

		perceptions = agent.getPerceptions();

		for (Message message : getMessages()) {
			this.processMessage(message);
		}

		/* if a behavior is applicable then do the correspondent action */
		for (int i = 0; i < BEHAVIOR_SIZE; i++) {
			try {
				Boolean result = (Boolean) this
						.getClass()
						.getDeclaredMethod("reactivePerception" + i,
								Agent.class)
						.invoke(this, new Object[] { agent });

				if (result) {
					this.getClass()
							.getDeclaredMethod("doAction" + i, Agent.class,
									int.class)
							.invoke(this, new Object[] { agent, delta });
					// Debug begin
					/*
					 * System.err.println("Time: " +
					 * TimeController.getTime().getDays() + "d " +
					 * TimeController.getTime().getHours() + "h " +
					 * TimeController.getTime().getMinutes() + "m" + " [T" +
					 * agent.getTeamId() + " A" + agent.getAgentId() +
					 * "] direction: " + agent.getDirection() + " action done: "
					 * + i);
					 */
					// Debug end

					doHalt = false;
					return;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void processMessage(Message message) {
		Object content = message.getContent();
		Perception perception;
		if (content instanceof FlagSpotted) {
			perception = new Perception(((FlagSpotted) content).position);
			perception.setFlag(true);
		} else if (content instanceof SpawnSpotted) {
			perception = new Perception(((SpawnSpotted) content).position);
			perception.setTeamBase(true);
			perception.setTeamBaseId(((SpawnSpotted) content).teamIdentifier);
		} else if (content instanceof Halt) {
			doHalt = true;
			return;
		} else {
			return;
		}

		perceptions.add(perception);
	}
}
