package ennemis;


import org.newdawn.slick.Image;

import jeu.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class DarkMaster
 *
 * @author: Ez
 */
public class DarkMaster extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ENNEMI_IMAGE = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(0, 1).getScaledCopy(2);
    private static final int NIVEAU = 2;
    /**
     * Class constructor
     */
    public DarkMaster(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages, int niveau) {
        super("Dark Master", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages, niveau);
        this.chargerImage();
    }
    
    public DarkMaster(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages, int niveau) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages, niveau);
    }

    public DarkMaster(Point p, Direction direction) {
        this(p.getX(), p.getY(), DarkMaster.SIZE, direction, DarkMaster.TIMER, DarkMaster.VITESSE, DarkMaster.ENNEMI_IMAGE, DarkMaster.NIVEAU);
    }
    

    private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 1,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 1,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 1,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 1,  2);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 3,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 3,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 3,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 0, 3,  2);
    }
}