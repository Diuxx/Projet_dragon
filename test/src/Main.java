import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import sys.StateGame;

public class Main {

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new StateGame(), 800, 600, false).start();
    }
}
