package ennemis;
import jeu.Ennemi;
import sys.EcranJeu;
import sys.Point;
import org.newdawn.slick.tiled.TiledMap;
import sys.Taille;


/**
 * class Goblin
 *
 * @author: Diuxx
 */
public class Goblin extends Ennemi {

    /**
     * Class constructor
     */
    public Goblin(Point positon, Taille taille, char direction, int timer) {
        super("Goblin", positon.getX(), positon.getY(), taille.getLargeur(), taille.getLongeur(),
                300, direction, timer);
        this.chargerImage();
    }

    private void chargerImage() {
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 2);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 3);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 0);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 0, 1, 1);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 2);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 3);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 0);
        this.loadAnimation(EcranJeu.spriteSheet_goblin, 1, 7, 1);
    }
}
