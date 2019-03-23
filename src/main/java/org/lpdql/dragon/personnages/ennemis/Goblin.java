package org.lpdql.dragon.personnages.ennemis;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.system.Direction;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.Image;

/**
 * class Goblin
 *
 * @author: Diuxx
 */
public class Goblin extends Ennemi {

    private static final Taille SIZE = new Taille(64, 64);
    private static final int TIMER = 1000;
    private static final float VITESSE = 0.1f;
    private static final Image ENNEMI_IMAGE = Ressources.spriteSheet_goblin.getSubImage(9, 3);
    private static final int NIVEAU = 2;
    /**
     * Class constructor
     */
    public Goblin(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages, int niveau) {
        super("Goblin", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages, niveau);
        this.chargerImage();
    }
    
    public Goblin(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages, int niveau) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages, niveau);
    }

    public Goblin(Point p, Direction direction) {
        this(p.getX(), p.getY(), Goblin.SIZE, direction, Goblin.TIMER, Goblin.VITESSE, Goblin.ENNEMI_IMAGE, Goblin.NIVEAU);
    }

    private void chargerImage() {
        this.loadAnimation(Ressources.spriteSheet_goblin, 0, 1, 2);
        this.loadAnimation(Ressources.spriteSheet_goblin, 0, 1, 3);
        this.loadAnimation(Ressources.spriteSheet_goblin, 0, 1, 0);
        this.loadAnimation(Ressources.spriteSheet_goblin, 0, 1, 1);
        this.loadAnimation(Ressources.spriteSheet_goblin, 1, 7, 2);
        this.loadAnimation(Ressources.spriteSheet_goblin, 1, 7, 3);
        this.loadAnimation(Ressources.spriteSheet_goblin, 1, 7, 0);
        this.loadAnimation(Ressources.spriteSheet_goblin, 1, 7, 1);
    }
}
