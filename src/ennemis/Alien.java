package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Alien
 *
 * @author: Ez
 */
public class Alien extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(9, 9).getScaledCopy(2);

    /**
     * Class constructor
     */
    public Alien(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Alien", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public Alien(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public Alien(Point p, Direction direction) {
        this(p.getX(), p.getY(), Alien.SIZE, direction, Alien.TIMER, Alien.VITESSE, Alien.ennemiImages);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  11);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  9);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  8);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  10);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  11);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  9);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  8);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  10);
    }
}