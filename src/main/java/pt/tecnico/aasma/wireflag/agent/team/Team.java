package pt.tecnico.aasma.wireflag.agent.team;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

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
	private static final int MINIMUM_TEAM_SIZE = 4;

	/** The team leader. */
	private Agent leader;

	/** The list of members in the team. */
	private List<Agent> members;

	/** The unique identifier of the team. */
	private String identifier;

	public Team(String identifier, Agent leader, List<Agent> members) throws InvalidTeamSizeException {
		this.identifier = identifier;
		this.leader = leader;
		
		if (members.size() < MINIMUM_TEAM_SIZE) {
			throw new InvalidTeamSizeException(identifier, members.size());
		}
		this.members = members;
	}

	/**
	 * Returns the team leader.
	 * 
	 * @return the leader
	 */
	public final Agent getLeader() {
		return leader;
	}

	/**
	 * Sets the new team leader.
	 *
	 * @param leader the new leader
	 */
	public abstract void setLeader(Agent leader);

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
	 * @param agent the agent to be added
	 */
	public final void addMember(Agent agent) {
		if (members.contains(agent)) {
			return;
		}
		members.add(agent);
	}

	/**
	 * Obtains the team identifier.
	 * 
	 * @return the identifier
	 */
	public final String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Votes according to type of teams.
	 *
	 * @return the result of the voting
	 */
	public abstract boolean vote();
}
