package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class IceMan
 *
 * @author: Ez
 */
public class IceMan extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(3, 5).getScaledCopy(2);

    /**
     * Class constructor
     */
    public IceMan(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Ice Man", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public IceMan(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public IceMan(Point p, Direction direction) {
        this(p.getX(), p.getY(), IceMan.SIZE, direction, IceMan.TIMER, IceMan.VITESSE, IceMan.ennemiImages);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  7);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  5);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  4);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  6);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  7);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  5);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  4);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  6);
    }
}