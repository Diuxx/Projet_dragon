import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import sys.StateGame;

public class Main {
    public static void main(String[] args) {

        try {
            new AppGameContainer(new StateGame(), 1200, 600, false).start();
        } catch (SlickException e) {
            // e.printStackTrace();
        }

    }
}
