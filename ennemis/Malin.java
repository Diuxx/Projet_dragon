package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Malin
 *
 * @author: Ez
 */
public class Malin extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(3, 9).getScaledCopy(2);

    /**
     * Class constructor
     */
    public Malin(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Malin", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public Malin(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public Malin(Point p, Direction direction) {
        this(p.getX(), p.getY(), Malin.SIZE, direction, Malin.TIMER, Malin.VITESSE, Malin.ennemiImages);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  11);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  9);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  8);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  10);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  11);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  9);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  8);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  10);
    }
}