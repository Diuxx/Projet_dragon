package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Warrior
 *
 * @author: Ez
 */
public class Warrior extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ennemiImages = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(9, 5).getScaledCopy(2);

    /**
     * Class constructor
     */
    public Warrior(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages) {
        super("Warrior", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages);
        this.chargerImage();
    }
    
    public Warrior(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages);
    }

    public Warrior(Point p, Direction direction) {
        this(p.getX(), p.getY(), Warrior.SIZE, direction, Warrior.TIMER, Warrior.VITESSE, Warrior.ennemiImages);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  7);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  5);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  4);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 10,  6);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  7);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  5);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  4);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 9, 11,  6);
    }
}