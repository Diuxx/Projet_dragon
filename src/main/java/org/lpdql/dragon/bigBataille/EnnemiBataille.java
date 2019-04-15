package org.lpdql.dragon.bigBataille;


import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * class EnnemiBataille
 *
 * @author: Diuxx
 */
public class EnnemiBataille {

    private Ennemi ennemi;
    private Point position;
    private Image image;

    /**
     * Class constructor
     */
    public EnnemiBataille(Ennemi e, GameContainer gc) {
        this.ennemi = e;
        this.position = new Point(gc.getWidth() * 3 / 4, gc.getHeight() / 2);
        this.image = e.getEnnemiImages();
    }

    public void draw(Graphics graphics) {
        image.draw(position.getX(), position.getY());
    }

    private void drawHp(Graphics graphics) {

    }


    public Ennemi getEnnemi() {
        return ennemi;
    }

    public void setEnnemi(Ennemi ennemi) {
        this.ennemi = ennemi;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
