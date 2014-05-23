package pt.tecnico.aasma.wireflag.agent.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;
import pt.tecnico.aasma.wireflag.util.Logger;

/**
 * This class is responsible to intermediate all means of communication between
 * agents.
 */
public class DeliverySystem extends Thread {

	private Logger logger;

	private BlockingQueue<Message> messages;

	private ArrayList<Agent> subscribers;

	private int teamIdentifier;

	public DeliverySystem(int teamIdentifier) throws IOException {
		this.logger = new Logger("Team" + teamIdentifier + ".messages", "Team "
				+ teamIdentifier);
		this.messages = new LinkedBlockingQueue<Message>();
		this.subscribers = new ArrayList<Agent>();
		this.teamIdentifier = teamIdentifier;
	}

	public boolean subscribe(Agent agent) {
		if (agent.getTeamId() != teamIdentifier) {
			logger.write("Subscribing agent " + agent.getAgentId() + " failed.");
			return false;
		}

		logger.write("Subscribing agent " + agent.getAgentId() + " succeded.");
		return subscribers.add(agent);
	}

	public boolean unsubscribe(Agent agent) {
		logger.write("Unsubscribing agent " + agent.getAgentId());
		return subscribers.remove(agent);
	}

	public void addMessage(Message message) {
		messages.offer(message);
		logger.write("Adding new message " + message.toString());
	}

	public void delivery() {
		try {
			Message message = messages.take();
			Agent sender = message.getSender();

			String log = "Delivering new message from " + sender.getAgentId()
					+ "\nReceivers:";

			if (message.isGlobal() && sender instanceof Patrol) {
				deliverGlobalMessage(message, sender);
				return;
			}

			List<Agent> nearAgents = sender.getNearTeammates();
			Set<Agent> receivers = new HashSet<Agent>();

			receivers.addAll(nearAgents);

			if (message.isBroadcast()) {
				for (Agent agent : receivers) {
					agent.getNearTeammates().remove(sender);
					receivers.addAll(agent.getNearTeammates());
				}
			}

			for (Agent agent : receivers) {
				log += "\tDelivering to: " + agent.getAgentId();
				agent.getMailbox().put(message);
			}
			logger.write(log);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deliverGlobalMessage(Message message, Agent sender)
			throws InterruptedException {
		for (Agent agent : subscribers) {
			if (agent.equals(sender)) {
				continue;
			}
			agent.getMailbox().put(message);
		}
	}

	@Override
	public void run() {
		while (true) {
			delivery();
		}
	}

}
