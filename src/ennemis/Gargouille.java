package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Gargouille
 *
 * @author: Ez
 */
public class Gargouille extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(9, 1).getScaledCopy(2);

    /**
     * Class constructor
     */
    public Gargouille(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Gargouille", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public Gargouille(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public Gargouille(Point p, Direction direction) {
        this(p.getX(), p.getY(), Gargouille.SIZE, direction, Gargouille.TIMER, Gargouille.VITESSE, Gargouille.ennemiImages);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  2);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  2);
    }
}