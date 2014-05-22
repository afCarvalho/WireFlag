package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pt.tecnico.aasma.wireflag.WireFlagGame;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.communication.Message;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Deliberative extends Architecture {

	InternalState state;
	LinkedList<Action> actions;
	Random random;

	public Deliberative() {
		state = new InternalState();
		actions = new LinkedList<Action>();
		random = new Random();
	}

	public InternalState getInternal() {
		return state;
	}

	public void makeAction(Agent agent, int delta) {

		// System.out.println("MAKE ACTION");

		// System.out.println("hasEndPoint " + state.hasEndPos()
		// + " agent has flag " + agent.hasFlag());

		// if (state.hasFlagPos()) {
		/*
		 * System.out.println("FLAG POS " + state.getFlagPos().getX() + " " +
		 * state.getFlagPos().getY());
		 */
		// }

		// if (state.hasEndPos()) {
		/*
		 * System.out.println("AGENT POS " +
		 * agent.getPos().getMapPosition().getX() + " " +
		 * agent.getPos().getMapPosition().getY()); System.out.println("ENDPOS "
		 * + state.getEndPos().getX() + " " + state.getEndPos().getY());
		 */
		// }

		// System.out.println("INIT " + agent.getPos().getMapPosition().getX()
		// + " " + agent.getPos().getMapPosition().getY());

		updateInternalState(agent);

		if (actions.size() < 2) {
			actions = plan(getIntentions(agent), agent.getPos()
					.getMapPosition(), agent.getVisibilityRange(), agent);
		}

		if (actions.size() < 2) {

			//System.out.println("SIZE < 2 ");
			// agent.stop();
			return;

		}

		// System.out.println("planing finished" + actions.size());

		// for (int i = 0; i < MapController.getMap().getNHorizontalTiles();
		// i++)
		// for (int j = 0; j < MapController.getMap().getNVerticalTiles(); j++)
		// MapController.getMap().getLandscape(new MapPosition(i, j)).isSet =
		// false;

		// System.out.println("size " + actions.size());

		/*
		 * for (Action a : actions) {
		 * MapController.getMap().getLandscape(a.getPos()).isSet = true; }
		 */

		Action a;

		// System.out.println("N ACTIONS " + actions.size());

		/*
		 * for (Action ac : actions) { System.out.print(ac.getAction() + "/" +
		 * ac.getNActions() + " " + "( " + ac.getPos().getX() + " " +
		 * ac.getPos().getY() + " )");
		 * 
		 * }
		 */
		// System.out.println();

		a = actions.removeFirst();

		/*
		 * System.out.println("Agent pos " + agent.getPos().getX() + " " +
		 * agent.getPos().getY() + agent.getPos().getMapPosition().getX() + " "
		 * + agent.getPos().getMapPosition().getY());
		 */
		for (Action ac : actions) {
			MapPosition acPos = ac.getPerception().getPosition();

			if (ac.getAction() == Action.STOP_ACTION) {
				// System.out.println("PAROU POR ACTION");
				break;
			} else if (state.shouldStop(agent.getFatigue())) {
				// System.out.println("PAROU POR FATIGUE");
				actions.clear();
				return;
			} else if (state.getWorld()[acPos.getX()][acPos.getY()].isBlocked()

					|| state.getWorld()[acPos.getX()][acPos.getY()].hasFire()
					&& !state.getWorld()[agent.getPos().getMapPosition().getX()][agent
							.getPos().getMapPosition().getY()].hasFire()

					|| state.getWorld()[acPos.getX()][acPos.getY()]
							.hasExtremeWeather()
					&& !state.getWorld()[agent.getPos().getMapPosition().getX()][agent
							.getPos().getMapPosition().getY()]
							.hasExtremeWeather()) {

				/*
				 * System.out.println("BLOCKED WAY " +
				 * state.getWorld()[acPos.getX()][acPos.getY()] .hasFire() + " "
				 * + state.getWorld()[acPos.getX()][acPos.getY()]
				 * .hasExtremeWeather());
				 */

				actions.clear();
				return;
			}
		}

		if (a.getAction() == Action.STOP_ACTION) {
			/*
			 * System.out.println("STOP " + agent.getFatigue() + "actions " +
			 * a.getNActions());
			 */
			agent.stop();
			state.resetKm(agent.getFatigue());
		} else if (a.getAction() == Action.HABILITY_ACTION) {
			// System.out.println("HAB");
		} else if (a.getAction() == Action.MOVE_ACTION) {
			while (agent.getFatigue() == 100) {
				System.exit(0);
			}
			;
			// System.out.println("MOVE " + agent.getFatigue());

			/*
			 * System.out.println("MOVE " + actions.getFirst().getPos().getX() +
			 * " " + actions.getFirst().getPos().getY() + " utility " +
			 * a.getValue() + " move utility " + a.getMoveUtility(agent) +
			 * " stop utility " + a.getStopUtility(agent, state) + " ancestor "
			 * + a.getAncestor());
			 */

			agent.approachTile(delta, actions.getFirst().getPos());
			state.countKm(agent.getFatigue());

			/*
			 * System.out.println(MapController.getMap().isBlocked(
			 * actions.getFirst().getPos()));
			 */

			if (!agent.getPos().getMapPosition()
					.isSamePosition(actions.getFirst().getPos())) {
				actions.addFirst(a);

			}

		} else if (a.getAction() == Action.GET_FLAG) {
			System.out.println("FLAG");
			agent.catchFlag();
		} else if (a.getAction() == Action.GET_END) {
			System.out.println("END");
			agent.dropFlag();
			WireFlagGame.win(agent.getTeamId());
		} else if (a.getAction() == Action.GET_ANIMAL) {
			// System.out.println("ANIMAL");
			agent.modifyLife(MapController.getMap()
					.getLandscape(actions.getFirst().getPos()).killAnimal());
		} else if (a.getAction() == Action.ATTACK) {
			// System.out.println("ATTACK");
			agent.attack(MapController.getMap()
					.getLandscape(actions.getFirst().getPos()).getAgent());
		}

		/*
		 * if (agent.hasFatigue()) { System.out.println("HAS FATIGUE");
		 * actions.clear(); return; }
		 */

	}

	public void updateInternalState(Agent agent) {
		state.setPerceptions(MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition(),
				agent.getVisibilityRange()));
	}

	public int getIntentions(Agent agent) {

		// isto podia vir da intention!
		if (state.hasEndPos() && agent.hasFlag()) {
			return Intention.END_GAME;
		}

		if (state.hasFlagPos() && !state.teamHasFlag()) {
			return Intention.GET_FLAG;
		}

		if (agent.hasLowLife() || agent.hasFatigue()) {
			return Intention.SURVIVE;
		}

		if (state.hasWeakTeamMember() > 0) {
			return Intention.TEAM_SURVIVE;
		}

		if (state.hasEnemyClose(agent) > 0 && !agent.hasLowLife()) {
			return Intention.ATTACK;
		}

		return Intention.MOVE;
	}

	public WorldState getPerception(int x, int y) {
		return state.getWorld()[x][y];
	}

	public void addAction(Action a, LinkedList<Action> actions,
			boolean usedPerception[][], int intention, int x, int y) {
		MapPosition pos = a.getPos();
		// Perception percept;

		// System.out.println("STOP " + pos.getX() + " " + pos.getY() + " "
		// + a.getValue());// + " " +
		// a.getNActions());
		// percept = getPerception(pos.getX(), pos.getY());
		// actions.addLast(new Action(a, percept, intention));

		WorldState wState = getPerception(pos.getX() + x, pos.getY() + y);

		if (wState != null && !usedPerception[pos.getX() + x][pos.getY() + y]) {
			actions.addLast(new Action(a, wState, intention));
			usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}

	}

	public LinkedList<Action> plan(int intention, MapPosition initialPos,
			int visibility, Agent agent) {

		// System.out.println("PLAN");

		boolean usedPerception[][] = new boolean[state.getHorizontalSize()][state
				.getVerticalSize()];
		LinkedList<Action> actions = new LinkedList<Action>();

		WorldState initialPercept = getPerception(initialPos.getX(),
				initialPos.getY());
		actions.add(new Action(null, initialPercept, intention));
		usedPerception[initialPos.getX()][initialPos.getY()] = true;

		for (int i = 0; i < state.getHorizontalSize(); i++) {
			for (int j = 0; j < state.getVerticalSize(); j++) {
				if (getPerception(i, j).isBlocked()) {
					usedPerception[i][j] = true;
				}
			}
		}

		Action bestExploreAction = actions.getFirst();
		Action bestMoveAction = actions.getFirst();
		Action bestGoAwayAction = actions.getFirst();

		while (!actions.isEmpty()) {
			Action a = actions.removeFirst();

			// System.out.println("BFS " + " " + a.getAction() + " " +
			// a.getPos().getX() + " " + a.getPos().getY() + " " +
			// a.getNActions());

			if (a != null) {

				if (a.getValue(state, agent, initialPos, Action.GO_AWAY) > bestGoAwayAction
						.getValue(state, agent, initialPos, Action.GO_AWAY)) {
					bestGoAwayAction = a;
				}

				if (a.getValue(state, agent, initialPos, Action.GO_FURTHER) > bestMoveAction
						.getValue(state, agent, initialPos, Action.GO_FURTHER)) {
					bestMoveAction = a;
				}

				if (a.getValue(state, agent, initialPos, Action.GO_CLOSE) > bestExploreAction
						.getValue(state, agent, initialPos, Action.GO_CLOSE)) {
					bestExploreAction = a;
				}

				if (a.getAction() == Action.STOP_ACTION) {
					// System.out.println("STOP");

					// System.out.println("Action " + a.getAction());
					/*
					 * System.out.println("STOP " + a.getPos().getX() + " " +
					 * a.getPos().getY() + " " + a.getNActions());
					 */
					actions.addLast(new Action(a, a.getPerception(), intention));
				} else {

					int order = random.nextInt(8);

					if (order == 0) {
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, 0, -1);
					} else if (order == 1) {
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, 0, 1);
					} else if (order == 2) {
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, 0, -1);
					} else if (order == 3) {
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, 0, 1);
					} else if (order == 4) {
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, -1, 0);
					} else if (order == 5) {
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 1, 0);
					} else if (order == 6) {
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, 1, 0);
						addAction(a, actions, usedPerception, intention, -1, 0);
					} else if (order == 7) {
						addAction(a, actions, usedPerception, intention, 0, -1);
						addAction(a, actions, usedPerception, intention, 0, 1);
						addAction(a, actions, usedPerception, intention, -1, 0);
						addAction(a, actions, usedPerception, intention, 1, 0);
					}
				}

				// MapPosition pos = a.getPos();
				// System.out.println("pos" + a.getPos().getX() + " "
				// + a.getPos().getY());

				/*
				 * Perception p1 = getPerception(pos.getX() + 1, pos.getY());
				 * Perception p2 = getPerception(pos.getX() - 1, pos.getY());
				 * Perception p3 = getPerception(pos.getX(), pos.getY() + 1);
				 * Perception p4 = getPerception(pos.getX(), pos.getY() - 1);
				 * 
				 * if (p1 != null && !usedPerception[p1.getId()]) {
				 * actions.addLast(new Action(a, p1, intention));
				 * usedPerception[p1.getId()] = true; }
				 * 
				 * if (p2 != null && !usedPerception[p2.getId()]) {
				 * actions.addLast(new Action(a, p2, intention));
				 * usedPerception[p2.getId()] = true; }
				 * 
				 * if (p3 != null && !usedPerception[p3.getId()]) {
				 * actions.addLast(new Action(a, p3, intention));
				 * usedPerception[p3.getId()] = true; }
				 * 
				 * if (p4 != null && !usedPerception[p4.getId()]) {
				 * actions.addLast(new Action(a, p4, intention));
				 * usedPerception[p4.getId()] = true; }
				 */

				// System.out.println("value "
				// + a.getValue(state, agent, initialPos));

			}
		}

		// System.out.println("Choosen " + bestAction.getNActions() + " "
		// + bestAction.getValue(state, agent, initialPos));

		LinkedList<Action> acList = new LinkedList<Action>();

		if (bestExploreAction.getPerception().getCondition() == 1
				&& bestExploreAction.getValue() > 0) {
			// System.out.println("EXPLORE");
			bestExploreAction.getActionsList(acList);
		} else if (bestMoveAction.getValue() > 0) {
			// System.out.println("MOVE With NActions"
			// + bestMoveAction.getNActions() + " and value "
			// + bestMoveAction.getValue());
			bestMoveAction.getActionsList(acList);
		}

		if (acList.size() < 2) {
			/*System.out.println("GO AWAY "
					+ bestGoAwayAction.getNActions()
					+ " "
					+ bestGoAwayAction.getMoveUtility(agent)
					+ " "
					+ bestGoAwayAction.getValue()
					+ " "
					+ Math.min(Math.min(bestGoAwayAction.getClimateUtility(),
							bestGoAwayAction.getFireUtility()),
							bestGoAwayAction.getBlockedUtility(agent)));*/
			acList.clear();
			bestGoAwayAction.getActionsList(actions);
		}

		return acList;
	}

	@Override
	public void processMessages(List<Message> message) {
		// TODO Auto-generated method stub
	}
}
