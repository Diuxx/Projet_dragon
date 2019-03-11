package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import sys.StateGame;

public class Main {
	public static int gX = 1200;
	public static int gY = 600;

	public static void main(String[] args) {
    	
        try {
            new AppGameContainer(new StateGame(), gX, gY, false).start();
        } catch (SlickException e) {
            // e.printStackTrace();
        }
    }
}
