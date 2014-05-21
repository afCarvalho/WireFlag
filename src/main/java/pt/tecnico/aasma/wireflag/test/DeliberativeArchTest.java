package pt.tecnico.aasma.wireflag.test;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.OldAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Deliberative;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class DeliberativeArchTest {

	private static LinkedList<Action> actions = new LinkedList<Action>();

	public static void run(Graphics g, Deliberative arch) {

		int width = MapController.getMap().getNHorizontalTiles();
		int height = MapController.getMap().getNVerticalTiles();

		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		for (int xAxis = 0; xAxis < width; xAxis++) {
			for (int yAxis = 0; yAxis < height; yAxis++) {
				MapPosition landPos = new MapPosition(xAxis, yAxis);

				int tileXPosition = landPos.getX() * tileWidth;
				int tileYPosition = landPos.getY() * tileHeight;

				for (int x = tileXPosition; x < tileXPosition + tileWidth; x++) {
					for (int y = tileYPosition; y < tileYPosition + tileHeight; y++) {
						if (x % 25 == 0 && y % 25 == 0) {
							g.drawString(
									arch.getBeliefs()
											.getWorldState(landPos.getX(),
													landPos.getY())
											.getCondition()
											+ " ", x, y);

						}

					}

				}
				// Landscape land =
				// MapController.getMap().getLandscape(landPos);

			}
		}

		for (Action a : actions) {
			AnimationLoader
					.getLoader()
					.getCross()
					.draw(a.getPos().getX()
							* MapController.getMap().getTileWidth(),
							a.getPos().getY()
									* MapController.getMap().getTileHeight());
		}

		// System.out.println(landscapePos.getX() + " " + landscapePos.getY());

		/*
		 * g.drawString(landscapePos.getX() + "/" + landscapePos.getY(),
		 * landscapePos.getX() MapController.getMap().getNHorizontalTiles() +
		 * MapController.getMap().getTileWidth(), landscapePos.getY()
		 * MapController.getMap().getNVerticalTiles() +
		 * MapController.getMap().getTileHeight());
		 */

	}

	@SuppressWarnings("unchecked")
	public static void setActions(Object actionsList) {
		DeliberativeArchTest.actions = (LinkedList<Action>) actionsList;
	}
}
