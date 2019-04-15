package org.lpdql.dragon.bigBataille;


import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.Color;
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
    private Point mouvement;

    private long timer;

    /**
     * Class constructor
     */
    public EnnemiBataille(Ennemi e, GameContainer gc) {
        this.ennemi = e;
        this.position = new Point(gc.getWidth() * 3 / 4, gc.getHeight() / 2);
        this.image = e.getEnnemiImages();
        this.mouvement = new Point(0, 0);
    }

    public void attaque(Hero e) {

    }

    public void draw(Graphics g, GameContainer gc) {
        this.drawBarHp(g, gc);
        this.drawCurrentHp(g, gc);
        image.draw(mouvement.getX() + position.getX(), mouvement.getY() + position.getY());
    }

    private void drawBarHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawRect(gc.getWidth() * 3 / 4 - 50,gc.getHeight() / 2 - this.image.getHeight() / 2 - 30, 130, 20);
        g.setColor(Color.red);
        g.fillRect(gc.getWidth() * 3 / 4 - 50 + 1,gc.getHeight() / 2 - this.image.getHeight() / 2 - 30 + 1,Math.max(0, (this.ennemi.getPointDeVieActuel() / this.ennemi.getPointDeVie())) * 130, 20);
    }

    private void drawCurrentHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawString("" + (int) Math.max(0, (int) this.ennemi.getPointDeVieActuel()), gc.getWidth() * 3 / 4 - 4, gc.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 29);
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

    public Point getMouvement() {
        return mouvement;
    }

    public void setMouvement(Point mouvement) {
        this.mouvement = mouvement;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }
}
