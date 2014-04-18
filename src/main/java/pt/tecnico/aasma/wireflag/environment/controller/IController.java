package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;

public interface IController extends IGameElement {
	public void init() throws SlickException;
}
