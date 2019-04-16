package org.lpdql.dragon.bigBataille;


import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * class EnnemiBataille
 *
 * @author: Diuxx
 */
public class HeroBataille {

    private Hero hero;
    private Point position;

    private List<Animation> animations;
    private int frames;

    private Point mouvement;

    private long timer;
    private long timerRetour;
    private int pas = 10;

    /**
     * Class constructor
     */
    public HeroBataille(Hero e, GameContainer gc) {
        this.hero = e;
        this.position = new Point(gc.getWidth() * 1 / 4, gc.getHeight() / 2);
        // this.image = Ressources.spriteSheet.getSubImage(6, 10);

        this.animations = new ArrayList<>();
        this.loadAnimation(Ressources.spriteSheet_hFight, 0, 2, 0);
        this.frames = 0;

        this.mouvement = new Point(0, 0);
    }

    public void attaque(EnnemiBataille e) {
        aAttaqueStart = true;
        this.timer = System.currentTimeMillis();
        this.timerRetour = System.currentTimeMillis();

        if(pas < 0)
            pas *= -1;
    }

    public void draw(Graphics g, GameContainer gc) {
        this.drawBarHp(g, gc);
        this.drawCurrentHp(g, gc); // image.draw(mouvement.getX() + position.getX(), mouvement.getY() + position.getY());

        if( this.animations.size() > 0) {
            g.drawAnimation(this.animations.get(this.frames), mouvement.getX() + this.position.getX(), mouvement.getY() + this.position.getY());
        } else {
            g.setColor(Color.black);
            g.fill(new Rectangle(this.position.getX(), this.position.getY(), 32, 32));
        }
    }

    private void drawBarHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawRect(position.getX() - 60 + (95/2), position.getY() - 30, 130, 20);
        g.setColor(Color.red);
        g.fillRect(position.getX() - 60 + (95/2), position.getY() - 30, Math.max(0, (hero.getPointDeVieActuel() / hero.getPointDeVie())) * 130, 20);
    }

    private void drawCurrentHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawString("" + (int) Math.max(0, (int) this.hero.getPointDeVieActuel()), position.getX() - 60 + (95/2) + 60, position.getY() - 26);
    }


    public boolean aAttaqueStart = false;

    // --
    public void attaqueAnimation(EnnemiBataille ennemi, List<Effet> effets) {
        if(System.currentTimeMillis() - this.timerRetour >= 500 && this.pas > 0) {

            this.damageTo(ennemi);
            this.pas *= -1;
        }
        if(System.currentTimeMillis() - this.timerRetour >= 1000 && this.pas < 0) {
            this.mouvement.setX(0);
            aAttaqueStart = false; // do damage here..
        }
        if(System.currentTimeMillis() - this.timer > 50) {
            this.mouvement.setX(mouvement.getX() + this.pas);
            this.timer = System.currentTimeMillis();
        }
    }

    private Effet swingEffet(EnnemiBataille ennemi) {
        Effet e = new Effet("swing", ennemi.getPosition(), new Taille(59, 68));
        e.loadAnimation(Ressources.spriteSheet_swordHit, 0, 2, 0);
        e.getAnimation().stopAt(2);
        return e;
        // for movible effet extend a new class MovibleEffet with Effet, and add depart position & endPosition..
    }

    public void damageTo(EnnemiBataille e) {
        // animate here

        e.takeDamage(this.getATK());
    }

    public void takeDamage(int damage) {
        this.hero.setPointDeVieActuel(this.hero.getPointDeVieActuel() - damage);
    }

    public int getATK() {
        this.hero.rafraichirLePouvoirATK();
        return (int) this.hero.getATK();
    }

    public boolean isAnnimationEnd() {
        return !this.aAttaqueStart;
    }

    public void update(EnnemiBataille e, List<Effet> effets) {
        if(aAttaqueStart) attaqueAnimation(e, effets);
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero h) {
        this.hero = h;
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

    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 350);
        }
        this.animations.add(animation);
    }
}