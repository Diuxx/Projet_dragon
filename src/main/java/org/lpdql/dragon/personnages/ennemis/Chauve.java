package org.lpdql.dragon.personnages.ennemis;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.system.Direction;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.Image;

/**
 * class Chauve
 *
 * @author: Diuxx
 */
public class Chauve extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;

    private static final Image ENNEMI_IMAGE = Ressources.spriteSheet.getSubImage(3, 13);
    private static final int NIVEAU = 2;

    /**
     * Class constructor
     */
    public Chauve(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages, int niveau) {
            super("Chauve souris", x, y, taille.getLargeur(), taille.getHauteur(),
                    50, direction, timer, vitesse, ennemiImages, niveau);

        this.chargerImage();
    }

    public Chauve(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages, int niveau) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages, niveau);
    }

    public Chauve(Point p, Direction direction) {
        this(p.getX(), p.getY(), Chauve.SIZE, direction, Chauve.TIMER, Chauve.VITESSE, Chauve.ENNEMI_IMAGE, Chauve.NIVEAU);
    }

    private void chargerImage() {
        this.loadAnimation(Ressources.spriteSheet, 3, 4,  15);
        this.loadAnimation(Ressources.spriteSheet, 3, 4,  13);
        this.loadAnimation(Ressources.spriteSheet, 3, 4,  12);
        this.loadAnimation(Ressources.spriteSheet, 3, 4,  14);
        this.loadAnimation(Ressources.spriteSheet, 3, 6,  15);
        this.loadAnimation(Ressources.spriteSheet, 3, 6,  13);
        this.loadAnimation(Ressources.spriteSheet, 3, 6,  12);
        this.loadAnimation(Ressources.spriteSheet, 3, 6,  14);
    }
}