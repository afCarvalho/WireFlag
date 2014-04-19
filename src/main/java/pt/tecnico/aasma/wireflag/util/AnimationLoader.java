package pt.tecnico.aasma.wireflag.util;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.EndPoint;

public class AnimationLoader {

	private static final AnimationLoader INSTANCE = new AnimationLoader();

	private Animation rain, sand, snow, thunder, goat, pig, end, fire, flag,
			s_up, s_down, s_left, s_right, d_up, d_down, d_left, d_right, ill;
	private final String weather = System.getProperty("weather");
	private final String object = System.getProperty("object");
	private final String animal = System.getProperty("animal");
	private final String agent = System.getProperty("agent");

	private AnimationLoader() {

	}

	public static AnimationLoader getLoader() {
		return INSTANCE;
	}

	public void loadImages() throws SlickException {

		int[] duration1 = { 300 };
		int[] duration4 = { 300, 300, 300, 300 };

		rain = new Animation(new Image[] { new Image(weather + "rain.png") },
				duration1, false);
		sand = new Animation(new Image[] { new Image(weather + "sand.png") },
				duration1, false);
		snow = new Animation(new Image[] { new Image(weather + "snow.png") },
				duration1, false);
		thunder = new Animation(
				new Image[] { new Image(weather + "thunder.png") }, duration1,
				false);

		goat = new Animation(new Image[] { new Image(animal + "goat.png") },
				duration1, false);
		pig = new Animation(new Image[] { new Image(animal + "pig.png") },
				duration1, false);

		end = new Animation(new Image[] { new Image(object + "endpoint.png") },
				duration1, false);
		fire = new Animation(new Image[] { new Image(object + "fire.png") },
				duration1, false);
		flag = new Animation(
				new Image[] { new Image(object + "SmallFlag.png") }, duration1,
				false);

		s_up = new Animation(new Image[] { new Image(agent + "s_back.png") },
				duration1, false);
		s_down = new Animation(
				new Image[] { new Image(agent + "s_front.png") }, duration1,
				false);
		s_left = new Animation(new Image[] { new Image(agent + "s_left.png") },
				duration1, false);
		s_right = new Animation(
				new Image[] { new Image(agent + "s_right.png") }, duration1,
				false);

		d_up = new Animation(new Image[] { new Image(agent + "d_back1.png"),
				new Image(agent + "d_back2.png"),
				new Image(agent + "d_back3.png"),
				new Image(agent + "d_back4.png") }, duration4, false);
		d_down = new Animation(new Image[] { new Image(agent + "d_front1.png"),
				new Image(agent + "d_front2.png"),
				new Image(agent + "d_front3.png"),
				new Image(agent + "d_front4.png") }, duration4, false);
		d_left = new Animation(new Image[] { new Image(agent + "d_left1.png"),
				new Image(agent + "d_left2.png"),
				new Image(agent + "d_left3.png"),
				new Image(agent + "d_left4.png") }, duration4, false);
		d_right = new Animation(new Image[] {
				new Image(agent + "d_right1.png"),
				new Image(agent + "d_right2.png"),
				new Image(agent + "d_right3.png"),
				new Image(agent + "d_right4.png") }, duration4, false);

		ill = new Animation(new Image[] { new Image(agent + "ill.png") },
				duration1, false);

	}

	/**************************
	 *** WEATHER ANIMATIONS ***
	 *************************/

	public Animation getRain() {
		return rain;
	}

	public Animation getSandStorm() {
		return sand;
	}

	public Animation getSnowStorm() {
		return snow;
	}

	public Animation getThunderStorm() {
		return thunder;
	}

	/*************************
	 *** OBJECT ANIMATIONS****
	 *************************/

	public Animation getGoat() {
		return goat;
	}

	public Animation getPig() {
		return pig;
	}

	public Animation getEndPoint() {
		return end;
	}

	public Animation getFire() {
		return fire;
	}

	public Animation getFlag() {
		return flag;
	}

	/**************************
	 *** SOLDIER ANIMATIONS ***
	 *************************/

	public Animation getSoldierUp() {
		return s_up;
	}

	public Animation getSoldierDown() {
		return s_down;
	}

	public Animation getSoldierLeft() {
		return s_left;
	}

	public Animation getSoldierRight() {
		return s_right;
	}

	/*************************
	 *** DOCTOR ANIMATIONS ***
	 *************************/

	public Animation getDoctorUp() {
		// TODO Auto-generated method stub
		return d_up;
	}

	public Animation getDoctorDown() {
		// TODO Auto-generated method stub
		return d_down;
	}

	public Animation getDoctorRight() {
		// TODO Auto-generated method stub
		return d_right;
	}

	public Animation getDoctorLeft() {
		// TODO Auto-generated method stub
		return d_left;
	}

	/*************************
	 *** BUILDER ANIMATIONS****
	 *************************/

	public Animation getBuilderUp() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getBuilderDown() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getBuilderRight() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getBuilderLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	/*************************
	 *** PATROL ANIMATIONS ***
	 *************************/

	public Animation getPatrolUp() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getPatrolLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getPatrolRight() {
		// TODO Auto-generated method stub
		return null;
	}

	public Animation getPatrolDown() {
		// TODO Auto-generated method stub
		return null;
	}

	/*************************
	 *** STATE ANIMATIONS ***
	 *************************/

	public Animation getIll() {
		return ill;
	}
}
