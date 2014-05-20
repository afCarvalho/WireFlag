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

	private static LinkedList<MapPosition> actions = new LinkedList<MapPosition>();

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
									arch.getInternal()
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

		for (MapPosition a : actions) {
			AnimationLoader
					.getLoader()
					.getCross()
					.draw(a.getX() * MapController.getMap().getTileWidth(),
							a.getY() * MapController.getMap().getTileHeight());
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

	public static LinkedList<MapPosition> getActions() {
		return actions;
	}

	public static void setActions(LinkedList<MapPosition> actionsList) {
		DeliberativeArchTest.actions = actionsList;
	}
}