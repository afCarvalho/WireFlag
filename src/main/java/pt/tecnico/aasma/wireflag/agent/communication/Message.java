package pt.tecnico.aasma.wireflag.agent.communication;

import pt.tecnico.aasma.wireflag.agent.Agent;

public class Message {

	/** The sender of the message */
	private Agent sender;

	/** The content of the message */
	private Object content;
	
	private boolean broadcast;
	
	private boolean global;

	public Message(Agent sender, Object content, boolean broadcast, boolean global) {
		this.sender = sender;
		this.content = content;
		this.broadcast = broadcast;
		this.global = global;
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
	 * Gets the content of the message.
	 * 
	 * @return the content of the message
	 */
	public final Object getContent() {
		return content;
	}

	/**
	 * Updates the content of the message.
	 * 
	 * @param type
	 *            the new content of the message
	 */
	public final void setMessageType(Object type) {
		this.content = type;
	}

	public final boolean isBroadcast() {
		return broadcast;
	}

	public final void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public final boolean isGlobal() {
		return global;
	}

	public final void setGlobal(boolean global) {
		this.global = global;
	}
}
