package ennemis;
import jeu.Ennemi;
import jeu.Point;
import org.newdawn.slick.tiled.TiledMap;


/**
 * class Goblin
 *
 * @author: Diuxx
 */
public class Goblin extends Ennemi {

    /**
     * Class constructor
     */
    public Goblin(float x, float y, int w, int h, int pointDeVie) {
        super("Goblin", x, y, w, h, pointDeVie);
    }

    public Goblin(Point pos, int w, int h, int pointDeVie) {
        super("Goblin", pos, w, h, pointDeVie);
    }

    @Override
    public void mouvement(int delta, TiledMap map) {
        super.mouvement(delta, map);
        this.move(delta);
    }

    @Override
    public void move(int delta) {
        setTimeSinceTrigger(getTimeSinceTrigger() + delta);
        if (getTimeSinceTrigger() > 1000)
        {
            setMoving(true);
            setDirection((int)(Math.random() * 4));
            setTimeSinceTrigger( 0);
        }
    }
}
