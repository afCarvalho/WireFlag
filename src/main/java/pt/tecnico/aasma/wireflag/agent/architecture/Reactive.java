package pt.tecnico.aasma.wireflag.agent.architecture;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import pt.tecnico.aasma.wireflag.WireFlagGame;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Reactive extends Architecture {

	/* total number of behaviors */
	protected final static int BEHAVIOR_SIZE = 20;

	public Reactive() {
		// Nothing to do here
	}

	/* agent has the flag and is on the end point */
	public boolean reactivePerception0(Agent agent, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (perception.getPosition().isSamePosition(actualPos)) {
				return agent.hasFlag() && perception.hasEndPoint();
			}
		}

		return false;
	}

	public void doAction0(Agent agent, int delta, List<Perception> perceptions) {
		WireFlagGame.win(agent.getTeamId());
	}

	/* agent stop if has very low life */
	public boolean reactivePerception1(Agent agent, List<Perception> perceptions) {
		return agent.hasVeryLowLife();
	}

	public void doAction1(Agent agent, int delta, List<Perception> perceptions) {
		// System.out.println("action 1");
		agent.stop();
	}

	/* agent stop if has extreme fatigue */
	public boolean reactivePerception2(Agent agent, List<Perception> perceptions) {
		return agent.hasFatigue();
	}

	public void doAction2(Agent agent, int delta, List<Perception> perceptions) {
		// System.out.println("action 2");
		agent.stop();
	}

	/*
	 * when an animal is in the ahead, left, right or behind position and the
	 * agent has low or very low life it is devoured
	 */
	public boolean reactivePerception3(Agent agent, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (agent.hasVeryLowLife() || agent.hasLowLife()) {
			for (Perception perception : perceptions) {
				if (actualPos.isJustAhead(perception.getPosition(),
						agent.getDirection())
						|| actualPos.isJustBehind(perception.getPosition(),
								agent.getDirection())
						|| actualPos.isCloseOnLeft(perception.getPosition(),
								agent.getDirection())
						|| actualPos.isCloseOnRight(perception.getPosition(),
								agent.getDirection())) {
					return perception.hasAnimal();
				}
			}
		}

		return false;
	}

	public void doAction3(Agent agent, int delta, List<Perception> perceptions) {
		// System.out.println("action 3");
		MapPosition actualPos = agent.getPos().getMapPosition();
		int killResult = 0;

		for (Perception perception : perceptions) {
			if (actualPos.isJustAhead(perception.getPosition(),
					agent.getDirection())
					|| actualPos.isJustBehind(perception.getPosition(),
							agent.getDirection())
					|| actualPos.isCloseOnLeft(perception.getPosition(),
							agent.getDirection())
					|| actualPos.isCloseOnRight(perception.getPosition(),
							agent.getDirection())) {
				killResult = MapController.getMap()
						.getLandscape(perception.getPosition()).killAnimal();
				break;
			}
		}

		agent.increaseLife(killResult);
	}

	/*
	 * if an agent has low life and an animal is in it´s visibility then agent
	 * approaches the animal position
	 */
	public boolean reactivePerception4(Agent agent, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		if (agent.hasLowLife()) {
			for (Perception perception : perceptions) {
				if (actualPos.isAhead(perception.getPosition(),
						agent.getDirection())
						|| actualPos.isBehind(perception.getPosition(),
								agent.getDirection())
						|| actualPos.isLeft(perception.getPosition(),
								agent.getDirection())
						|| actualPos.isRight(perception.getPosition(),
								agent.getDirection())) {
					return perception.hasAnimal();
				}
			}
		}

		return false;
	}

	public void doAction4(Agent agent, int delta, List<Perception> perceptions) {
		// System.out.println("action 4");
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isJustAhead(perception.getPosition(),
					agent.getDirection())
					|| actualPos.isJustBehind(perception.getPosition(),
							agent.getDirection())
					|| actualPos.isCloseOnLeft(perception.getPosition(),
							agent.getDirection())
					|| actualPos.isCloseOnRight(perception.getPosition(),
							agent.getDirection())) {
				agent.approachTile(delta, perception.getPosition());
				return;
			}
		}
	}

	/* if the flag is in the agent actual position */
	public boolean reactivePerception5(Agent agent, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isSamePosition(perception.getPosition())) {
				return perception.hasFlag();
			}
		}

		return false;
	}

	public void doAction5(Agent agent, int delta, List<Perception> perceptions) {
		// System.out.println("action 5");
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isSamePosition(perception.getPosition())
					&& perception.hasFlag()) {
				agent.catchFlag();
				return;
			}
		}
	}

	/*
	 * if the agent actual position has fire it moves to adjacent position if it
	 * is available
	 */
	public boolean reactivePerception6(Agent agent, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isSamePosition(perception.getPosition())
					&& perception.hasFire()) {
				for (Perception perception1 : perceptions) {
					if (actualPos.isJustAhead(perception1.getPosition(),
							agent.getDirection())
							|| actualPos.isJustBehind(
									perception1.getPosition(),
									agent.getDirection())
							|| actualPos.isCloseOnLeft(
									perception1.getPosition(),
									agent.getDirection())
							|| actualPos.isCloseOnRight(
									perception1.getPosition(),
									agent.getDirection())) {
						return !perception1.hasFire()
								&& !MapController.getMap().isBlocked(
										perception1.getPosition());
					}
				}
			}
		}

		return false;
	}

	public void doAction6(Agent agent, int delta, List<Perception> perceptions) {
		MapPosition actualPos = agent.getPos().getMapPosition();

		for (Perception perception : perceptions) {
			if (actualPos.isSamePosition(perception.getPosition())
					&& perception.hasFire()) {
				for (Perception perception1 : perceptions) {
					if (!perception1.hasFire()
							&& !MapController.getMap().isBlocked(
									perception1.getPosition())) {
						if (actualPos.isJustAhead(perception1.getPosition(),
								agent.getDirection())) {
							agent.moveUp(delta, actualPos);
						} else if (actualPos
								.isJustBehind(perception1.getPosition(),
										agent.getDirection())) {
							agent.moveDown(delta, actualPos);
						} else if (actualPos
								.isCloseOnLeft(perception1.getPosition(),
										agent.getDirection())) {
							agent.moveLeft(delta, actualPos);
						} else if (actualPos
								.isCloseOnRight(perception1.getPosition(),
										agent.getDirection())) {
							agent.moveRight(delta, actualPos);
						}
					}
				}
			}
		}
	}

	public boolean reactivePerception7(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction7(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception8(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction8(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception9(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction9(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception10(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction10(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception11(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction11(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception12(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction12(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception13(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction13(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception14(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction14(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception15(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction15(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception16(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction16(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	public boolean reactivePerception17(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction17(Agent agent, int delta, List<Perception> perceptions) {
		// TODO
	}

	/*
	 * Rule: Position ahead blocked. Returns true if the agent can move ahead,
	 * and false otherwise.
	 */
	public boolean reactivePerception18(Agent agent,
			List<Perception> perceptions) {

		MapPosition actualPosition = agent.getPos().getMapPosition();
		MapPosition aheadPosition = actualPosition.getAhedPosition(agent
				.getDirection());

		// debug begin
		System.out.println("18 actualPosition x: " + actualPosition.getX()
				+ " y: " + actualPosition.getY());
		System.out.println("18 aheadPosition x: " + aheadPosition.getX()
				+ " y: " + aheadPosition.getY());
		System.out
				.println("18 MapController.getMap().isBlocked(aheadPosition): "
						+ MapController.getMap().isBlocked(aheadPosition));
		// debug end

		return MapController.getMap().isBlocked(aheadPosition);
	}

	/* position ahead blocked */
	public void doAction18(Agent agent, int delta, List<Perception> perceptions) {
		agent.moveDifferentDirection(delta);
	}

	/* true case */
	public boolean reactivePerception19(Agent agent,
			List<Perception> perceptions) {
		return true;
	}

	/* true case */
	public void doAction19(Agent agent, int delta, List<Perception> perceptions) {
		agent.moveSameDirection(delta);
	}

	public void makeAction(Agent agent, int delta) {

		List<Perception> perceptions = MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition(),
				agent.getVisibilityRange());

		/* if a behavior is applicable then do the correspondent action */
		for (int i = 0; i < BEHAVIOR_SIZE; i++) {
			try {
				// Debug begin
				System.out.println("try perception: " + i);
				// Debug end

				Boolean result = (Boolean) this
						.getClass()
						.getDeclaredMethod("reactivePerception" + i,
								Agent.class, List.class)
						.invoke(this, new Object[] { agent, perceptions });

				if (result) {
					// Debug begin
					System.out.println("perception true: " + i);
					// Debug end

					this.getClass()
							.getDeclaredMethod("doAction" + i, Agent.class,
									int.class, List.class)
							.invoke(this,
									new Object[] { agent, delta, perceptions });
					// Debug begin
					System.out.println("action done: " + i);
					// Debug end
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
}
