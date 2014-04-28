package pt.tecnico.aasma.wireflag.agent.communication;

import pt.tecnico.aasma.wireflag.agent.Agent;

/**
 * This class is responsible to intermediate all means of communication between agents.
 */
public class Messenger {

    /**
     * The creator which the Messenger is responsible of.
     */
    private Agent creator;

    /**
     * Creates a new instance of the Messenger class and stores its creator.
     *
     * @param creator the agent who instantiated the Messenger.
     */
    public Messenger(Agent creator) {
        this.creator = creator;
    }

    /**
     * Gets the agent who created the instance of this class.
     *
     * @return the creator
     */
    public Agent getCreator() {
        return creator;
    }

    public int deliveryMessage(Message message) {
        return 0;
    }
}
