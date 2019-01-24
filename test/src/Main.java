import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) throws SlickException {
        // --
        new AppGameContainer(new Fenetre(), 640, 480, false).start();
    }
}
