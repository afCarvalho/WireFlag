package pt.tecnico.aasma.wireflag.agent.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;

/**
 * This class is responsible to intermediate all means of communication between agents.
 */
public class DeliverySystem extends Thread {
	
	private BlockingQueue<Message> messages;
	
	private ArrayList<Agent> subscribers;
	
	private int teamIdentifier;
	
	public DeliverySystem(int teamIdentifier) {
		this.messages = new LinkedBlockingQueue<Message>();
		this.subscribers = new ArrayList<Agent>();
		this.teamIdentifier = teamIdentifier;
	}
	
	public boolean subscribe(Agent agent) {
		if (agent.getTeamId() != teamIdentifier) {
			return false;
		}
		
		return subscribers.add(agent);
	}
	
	public boolean unsubscribe(Agent agent) {
		return subscribers.remove(agent);
	}
	
	public void addMessage(Message message) {
		try {
			messages.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delivery() {
		try {
			Message message = messages.take();
			Agent sender = message.getSender();
			
			if (message.isGlobal() && sender instanceof Patrol) {
				deliverGlobalMessage(message, sender);
				return;
			}
			
			List<Agent> receivers = sender.getNearTeammates();
			
			for (Agent agent : receivers) {
				agent.getMailbox().put(message);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deliverGlobalMessage(Message message, Agent sender) throws InterruptedException {
		for (Agent agent : subscribers) {
			if(agent.equals(sender)) {
				continue;
			}
			agent.getMailbox().put(message);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			delivery();
		}
	}
	
}
