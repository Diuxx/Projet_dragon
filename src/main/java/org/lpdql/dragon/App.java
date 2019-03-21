package org.lpdql.dragon;

import org.lpdql.dragon.system.StateGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import static org.lpdql.dragon.singleton.InterStateComm.gX;
import static org.lpdql.dragon.singleton.InterStateComm.gY;

public class App {
	public static void main(String[] args) throws SlickException {
        new AppGameContainer(new StateGame(), gX, gY, false).start();
    }
}
