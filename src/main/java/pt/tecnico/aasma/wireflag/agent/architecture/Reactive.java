package pt.tecnico.aasma.wireflag.agent.architecture;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Reactive extends Architecture {

	/* total number of behaviors */
	protected final static int BEHAVIOR_SIZE = 20;

	public Reactive() {
		// TODO Auto-generated constructor stub
	}

	public boolean reactivePerception1(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction1(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception2(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction2(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception3(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction3(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception4(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction4(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception5(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction5(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception6(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction6(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception7(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction7(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception8(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction8(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception9(Agent agent, List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction9(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception10(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction10(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception11(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction11(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception12(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction12(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception13(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction13(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception14(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction14(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception15(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction15(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception16(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction16(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception17(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction17(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception18(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction18(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception19(Agent agent,
			List<Perception> perceptions) {
		// TODO
		return false;
	}

	public void doAction19(Agent agent, int delta) {
		// TODO
	}

	public boolean reactivePerception20(Agent agent,
			List<Perception> perceptions) {
		return true;
	}

	public void doAction20(Agent agent, int delta) {
		agent.randomMovement(delta);
	}

	public void makeAction(Agent agent, int delta) {

		List<Perception> perceptions = MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition());

		// TODO em obras...

		/* if a behavior is applicable then do the correspondent action */
		for (int i = 0; i <= BEHAVIOR_SIZE; i++) {
			try {
				Boolean result = (Boolean) this
						.getClass()
						.getDeclaredMethod("reactivePerception" + i,
								Agent.class, List.class)
						.invoke(this, new Object[] { agent, perceptions });

				if (result) {
					this.getClass()
							.getDeclaredMethod("doAction" + i, Agent.class,
									int.class)
							.invoke(this, new Object[] { agent, delta });
					return;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
