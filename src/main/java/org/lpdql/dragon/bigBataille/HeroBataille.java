package org.lpdql.dragon.bigBataille;


import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.monde.Ressources;
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

    private boolean defenceMode;

    private Image joueurImage;

    public static int ATK;
    private int attaqueBonus;

    public boolean aAttaqueStart = false;
    public boolean aDefenceStart = false;

    /**
     * Class constructor
     */
    public HeroBataille(Hero e, GameContainer gc) {
        this.hero = e;
        this.position = new Point(gc.getWidth() * 1 / 4 , gc.getHeight() / 2);
        // this.image = Ressources.spriteSheet.getSubImage(6, 10);

        this.animations = new ArrayList<>();
        this.joueurImage = Ressources.spriteSheet_hFight.getSubImage(0, 0);
        this.loadAnimation(Ressources.spriteSheet_hFight, 0, 2, 0);
        this.frames = 0;

        this.mouvement = new Point(0, 0);
        this.defenceMode = false;
        this.attaqueBonus = 0;
    }

    public HeroBataille(Hero e) {
        this.hero = e;
        this.position = new Point(0, 0);
        // this.image = Ressources.spriteSheet.getSubImage(6, 10);

        this.animations = new ArrayList<>();
        this.joueurImage = null;
        this.frames = 0;

        this.mouvement = new Point(0, 0);
        this.defenceMode = false;
        this.attaqueBonus = 0;
    }

    public Image getJoueurImage() {
        return joueurImage;
    }

    public void attaque(EnnemiBataille e) {
        aAttaqueStart = true;
        this.timer = System.currentTimeMillis();
        this.timerRetour = System.currentTimeMillis();

        if(pas < 0) pas *= -1;
    }

    public void defence(EnnemiBataille e) {
        aDefenceStart = true;
        this.timer = System.currentTimeMillis();
        this.timerRetour = System.currentTimeMillis();

        // bloquerProchinEnnemiAtk(); // bloqueur 5% ~ 15% de la prochine ennemi attaque
        // augmenterProchineHeroAtk(); // augmenter la prochine hero attaque 25% ~ 75%

        if(pas > 0) pas *= -1;
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
        g.drawString("" + (int) Math.max(0, (int) this.hero.getPointDeVieActuel()), position.getX() - 60 + (95/2) + 50, position.getY() - 29);
    }

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

    public void defeneAnimation(EnnemiBataille ennemi, List<Effet> effets) {
        if (System.currentTimeMillis() - this.timerRetour >= 250 && this.pas < 0) this.pas *= -1;
        if (System.currentTimeMillis() - this.timerRetour >= 500 && this.pas > 0) this.pas *= -1;
        if (System.currentTimeMillis() - this.timerRetour >= 750 && this.pas < 0) this.pas *= -1;

        if (System.currentTimeMillis() - this.timerRetour >= 1000 && this.pas > 0) {

            this.setDefenceMode(true);
            this.mouvement.setX(0);
            this.mouvement.setY(0);
            aDefenceStart = false; // do damage here..
        }
        if (System.currentTimeMillis() - this.timer > 100) {
            this.mouvement.setX(mouvement.getX() + this.pas);
            // this.mouvement.setY(mouvement.getY() - this.pas);
            this.timer = System.currentTimeMillis();
        }
    }

    public void damageTo(EnnemiBataille e) {
        // animate here
        /*e.takeDamage(this.getATK() + HeroBataille.ATK);
        printHeroAtkLog(this.getATK(), (int) e.getEnnemi().getPointDeVieActuel(), HeroBataille.ATK);
        HeroBataille.ATK = 0;*/
        e.takeDamage(this.getATK() + this.getAttaqueBonus());
        printHeroAtkLog(this.getATK(), (int) e.getEnnemi().getPointDeVieActuel(), this.getAttaqueBonus());
        this.setAttaqueBonus(0);
    }

    public void takeDamage(int damage) {
        this.hero.setPointDeVieActuel(this.hero.getPointDeVieActuel() - damage);
    }

    public int getATK() {
        this.hero.rafraichirLePouvoirATK();
        return (int) this.hero.getATK();
    }

    public boolean isAnnimationEnd() {
        return !this.aAttaqueStart && !this.aDefenceStart;
    }

    public void update(EnnemiBataille e, List<Effet> effets) {
        if(aAttaqueStart) attaqueAnimation(e, effets);
        if(aDefenceStart) defeneAnimation(e, effets);
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

    public int getAttaqueBonus() {
        return attaqueBonus;
    }

    public void setAttaqueBonus(int attaque) {
        this.attaqueBonus = attaque;
    }

    public boolean isDefenceModeActivated() {
        return defenceMode;
    }

    public void setDefenceMode(boolean defenceMode) {
        this.defenceMode = defenceMode;
    }

    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 350);
        }
        this.animations.add(animation);
    }

    public void printHeroAtkLog(int atk, int pv, int crt) {
        System.out.println();
        System.out.println("<Bataille> Hero turn");
        System.out.println(
                "Hero ATK power      ----> " + atk + " Critical + " + crt);
        System.out.println("ennemi restant vie  ----> " + pv);
    }

    public Effet swingEffet(EnnemiBataille ennemi) {
        Effet e = new Effet("swing", ennemi.getPosition(), new Taille(59, 68));
        e.loadAnimation(Ressources.spriteSheet_swordHit, 0, 2, 0, new int[] { 200, 200 });
        e.getAnimation().stopAt(1);
        return e;
        // for movible effet extend a new class MovibleEffet with Effet, and add depart
        // position & endPosition..
    }

    public Effet shiealdEffet() {
        Effet e = new Effet("shield", new Point(this.getPosition().getX(), this.getPosition().getY() - 32), new Taille(128, 128));
        e.loadAnimation(Ressources.spriteSheet_shield, 0, 3, 0, 3, new int[] { 100, 100, 100});
        e.getAnimation().stopAt(8);
        return e;
    }
}
