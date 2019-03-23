package org.lpdql.dragon.personnages.ennemis;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.system.Direction;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.Image;

/**
 * class InterStateComm
 *
 * @author: Ez
 */
public class Dragon extends Ennemi {


    private static final Taille SIZE = new Taille(96, 96);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ENNEMI_IMAGE = Ressources.spriteSheet_Dragon.getSubImage(0, 1);
    private static final int NIVEAU = 2;
    /**
     * Class constructor
     */
    public Dragon(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages, int niveau) {
        super("Dragon", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages, niveau);
        this.chargerImage();
    }
    
    public Dragon(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages, int niveau) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages, niveau);
    }

    public Dragon(Point p, Direction direction) {
        this(p.getX(), p.getY(), Dragon.SIZE, direction, Dragon.TIMER, Dragon.VITESSE, Dragon.ENNEMI_IMAGE, Dragon.NIVEAU);
    }

    private void chargerImage() {
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 1,  3);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 1,  1);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 1,  0);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 1,  2);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 3,  3);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 3,  1);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 3,  0);
        this.loadAnimation(Ressources.spriteSheet_Dragon, 0, 3,  2);
    }
}