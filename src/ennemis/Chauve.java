package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Chauve
 *
 * @author: Diuxx
 */
public class Chauve extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet.getSubImage(3, 13).getScaledCopy(2);

    /**
     * Class constructor
     */
    public Chauve(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Chauve souris", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public Chauve(Point positon, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public Chauve(Point p, Direction direction) {
        this(p.getX(), p.getY(), Chauve.SIZE, direction, Chauve.TIMER, Chauve.VITESSE, Chauve.ennemiImages);
    }

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4,  15);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4,  13);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4,  12);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4,  14);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6,  15);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6,  13);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6,  12);
        this.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6,  14);
    }
}