import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import sys.StateGame;

import static singleton.InterStateComm.gX;
import static singleton.InterStateComm.gY;

public class  Main {

	public static void main(String[] args) {
    	
        try {
            new AppGameContainer(new StateGame(), gX, gY, false).start();
        } catch (SlickException e) {
            // e.printStackTrace();
        }
    }
}
