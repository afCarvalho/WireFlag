package pt.tecnico.aasma.wireflag.agent.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.message.BroadcastType;

public class Message {

	/** The sender of the message */
	private Agent sender;

	/** The receivers of the message */
	private List<Agent> receivers;

	/** The type of the message */
	private Object messageType;
	
	private BroadcastType broadcastType;

	public Message(Agent sender, Agent receiver, Object messageType, BroadcastType broadcastType) {
		this(sender, new ArrayList<Agent>(Arrays.asList(receiver)), messageType, broadcastType);
	}

	public Message(Agent sender, ArrayList<Agent> receivers, Object messageType, BroadcastType broadcastType) {
		this.sender = sender;
		this.receivers = receivers;
		this.messageType = messageType;
		this.broadcastType = broadcastType;
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
	public final Object getMessageType() {
		return messageType;
	}

	/**
	 * Updates the type of the message.
	 * 
	 * @param type
	 *            the new type of the message
	 */
	public final void setMessageType(Object type) {
		this.messageType = type;
	}
}
