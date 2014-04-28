package pt.tecnico.aasma.wireflag.agent.communication;

import pt.tecnico.aasma.wireflag.environment.perception.Perception;

/**
 * Class to share a given perception in the map.
 */
public class SharePerception extends Message {

    /**
     * The perception to be shared
     */
    private Perception perception;

    /**
     * Creates a new message with a perception.
     *
     * @param perception the perception to be shared.
     */
    public SharePerception(Perception perception) {
        this.perception = perception;
    }

    /**
     * Gets the perception to be shared.
     *
     * @return the perception
     */
    public final Perception getPerception() {
        return this.perception;
    }
}
