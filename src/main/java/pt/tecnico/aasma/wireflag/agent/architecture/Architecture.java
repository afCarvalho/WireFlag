package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.Message;

public abstract class Architecture {

	BlockingQueue<Message> mailbox;

	public Architecture() {
		mailbox = new LinkedBlockingQueue<Message>();
	}
	
	public BlockingQueue<Message> getMailbox() {
		return this.mailbox;
	}

	public abstract void makeAction(Agent agent, int delta);

	public List<Message> getMessages() {
		List<Message> messages = new ArrayList<Message>();
		mailbox.drainTo(messages);
		return messages;
	}

	protected abstract void processMessage(Message message);

}
