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
public class HeroBataille {

    private Hero hero;
    private Point position;
    private Image image;

    /**
     * Class constructor
     */
    public HeroBataille(Hero e, GameContainer gc) {
        this.hero = e;
        this.position = new Point(gc.getWidth() * 1 / 4, gc.getHeight() / 2);
        this.image = Ressources.spriteSheet.getSubImage(6, 10);
    }

    public void draw(Graphics g, GameContainer gc) {
        this.drawBarHp(g, gc);
        this.drawCurrentHp(g, gc);
        image.draw(position.getX(), position.getY());
    }

    private void drawBarHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawRect(gc.getWidth() * 1 / 4,gc.getHeight() / 2 - this.image.getHeight() / 2 - 30, 130, 20);
        g.setColor(Color.red);
        g.fillRect(gc.getWidth() * 1 / 4,gc.getHeight() / 2 - this.image.getHeight() / 2 - 30,Math.max(0, (hero.getPointDeVieActuel() / hero.getPointDeVie())) * 130, 20);
    }

    private void drawCurrentHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawString("" + (int) Math.max(0, (int) this.hero.getPointDeVieActuel()), gc.getWidth() * 1 / 4, gc.getHeight() / 2 - this.image.getHeight() / 2 - 29);
    }

    public Hero getEnnemi() {
        return hero;
    }

    public void setEnnemi(Hero h) {
        this.hero = h;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}