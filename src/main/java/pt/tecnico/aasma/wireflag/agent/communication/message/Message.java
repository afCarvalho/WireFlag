package pt.tecnico.aasma.wireflag.agent.communication.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;

public class Message {

	/** The sender of the message */
	private Agent sender;

	/** The receivers of the message */
	private List<Agent> receivers;

	/** The type of the message */
	private MessageType type;
	
	private BroadcastType broadcastType;

	public Message(Agent sender, Agent receiver, MessageType type) {
		this(sender, new ArrayList<Agent>(Arrays.asList(receiver)), type);
	}

	public Message(Agent sender, ArrayList<Agent> receivers, MessageType type) {
		this.sender = sender;
		this.receivers = receivers;
		this.type = type;
	}

	/**
	 * Obtains the message' sender.
	 * 
	 * @return the agent that will send the message
	 */
	public final Agent getSender() {
		return sender;
	}

	/**
	 * Gets the message's receivers.
	 * 
	 * @return the agents that will receive the message
	 */
	public final List<Agent> getReceivers() {
		return receivers;
	}

	/**
	 * Adds a new receiver to the message.
	 * 
	 * @param receiver
	 *            the new agent that will receive the message
	 */
	public final void addReceivers(Agent receiver) {
		this.receivers.add(receiver);
	}

	/**
	 * Gets the type of the message.
	 * 
	 * @return the type the message type
	 */
	public final MessageType getType() {
		return type;
	}

	/**
	 * Updates the type of the message.
	 * 
	 * @param type
	 *            the new type of the message
	 */
	public final void setType(MessageType type) {
		this.type = type;
	}
}
