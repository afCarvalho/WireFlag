package pt.tecnico.aasma.wireflag.agent.team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.DeliverySystem;
import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.object.TeamBase;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;
import pt.tecnico.aasma.wireflag.util.position.WorldPosition;

/**
 * The class Team represents a generic team. A team is composed by a leader and
 * a initial minimum of 4 members. Each team have its own view of the map, which
 * is the result of the shared view of the members. One team can be Anarchical,
 * Democratical or Hierarchical.
 */
public abstract class Team {

	/**
	 * The constant that represents the minimum number of elements that a team
	 * must have, besides the leader.
	 */

	/** The list of members in the team. */
	private ArrayList<Agent> members;

	/** The unique identifier of the team. */
	private int id;

	private int nextMemberID;

	private MapPosition teamPosition;

	private DeliverySystem deliverySystem;

	private MapPosition teamBasePos;

	public Team(int id) throws SlickException, IOException {
		this.id = id;
		this.members = new ArrayList<Agent>();
		this.nextMemberID = 0;
		this.deliverySystem = new DeliverySystem(id);

		/*
		 * if (members.size() < MINIMUM_TEAM_SIZE) { throw new
		 * InvalidTeamSizeException(identifier, members.size()); }
		 */

	}

	/**
	 * Gets the list of members.
	 * 
	 * @return the members
	 */
	public final List<Agent> getMembers() {
		return members;
	}

	/**
	 * Adds a new member to the team.
	 * 
	 * @param agent
	 *            the agent to be added
	 */
	public final void addMember(Agent agent) {
		if (members.contains(agent)) {
			return;
		}
		members.add(agent);
		deliverySystem.subscribe(agent);
	}

	/**
	 * Removes the member. If it is the leader then it's elected a new one.
	 * 
	 * @param agent
	 *            the member to be removed
	 */
	public final void removeMember(Agent agent) {
		members.remove(agent);
		deliverySystem.unsubscribe(agent);
	}

	/**
	 * Obtains the team identifier.
	 * 
	 * @return the identifier
	 */
	public final int getID() {
		return id;
	}

	public MapPosition getTeamPosition() {
		return teamPosition;
	}

	public void setTeamPosition(MapPosition teamPosition) {
		this.teamPosition = teamPosition;
	}

	public void addAgent(Agent agent) {
		members.add(agent);
		EndGameController.getEnd().increaseNAliveAgents();
	}

	public void setTeamUp() {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileWidth();
		teamPosition = MapController.getMap().getRandomPosition();

		while (!isValidTeamPosition()) {
			teamPosition = MapController.getMap().getRandomPosition();
		}
		teamBasePos = new MapPosition(teamPosition.getX(), teamPosition.getY());

		/* put the team's base in the world */
		MapController.getMap().getLandscape(teamPosition)
				.setTeamBase(new TeamBase(teamBasePos, id));
		this.teamPosition.setX(teamPosition.getX() + 1);

		/* put the team's agents in the world */
		for (Agent agent : members) {
			agent.setPos(new WorldPosition(teamPosition.getX() * tileWidth,
					teamPosition.getY() * tileHeight));
			MapController.getMap().getLandscape(teamPosition).setAgent(agent);
			this.teamPosition.setX(teamPosition.getX() + 1);
		}
	}

	public boolean isValidTeamPosition() {
		int x = teamPosition.getX();
		int y = teamPosition.getY();
		int nTiles = MapController.getMap().getNHorizontalTiles();
		boolean res = true;

		/* members plus team's base */
		for (int i = 0; i < members.size() + 1; i++) {
			res = (x + i < nTiles)
					&& !MapController.getMap().isBlocked(
							new MapPosition(x + i, y)) && res;
		}
		return res;
	}

	public final DeliverySystem getDeliverySystem() {
		return deliverySystem;
	}

	public final void setDeliverySystem(DeliverySystem deliverySystem) {
		this.deliverySystem = deliverySystem;
	}

	/**
	 * Creates a representation of the team. This representation is a list
	 * containing the leader and members of the team.
	 * 
	 * @return the list containing all the agents.
	 */
	public List<Agent> toList() {
		return members;
	}

	public int getMemberID() {
		return nextMemberID++;
	}

	/**
	 * Elects a new leader.
	 */
	protected abstract void electLeader();

	/**
	 * Votes according to type of teams.
	 * 
	 * @return the result of the voting
	 */
	protected abstract boolean vote();

	public MapPosition getTeamBasePos() {
		return teamBasePos;
	}
}
