package Objets;

import jeu.Hero;
import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

import java.util.ArrayList;
import java.util.List;

public class Objet {


    private int positionSurMap;
    /**
     * Position de l'objet
     */
    private Point position;
    private Taille taille;

    /**
     * Gestion des collisions.
     */
    private Rectangle box;

    /**
     * Detection
     */
    private boolean collision;

    /**
     * Nom de l'objet */
    private String nom;

    /**
     * Annimation de l'objet */
    private List<Animation> animations;

    /**
     * */
    private int frames;

    /**
     * Construceur
     * @param nom
     * @param p */
    public Objet(String nom, Point p, Taille taille, int positionSurMap) {
        // --
        this.position = p;
        this.taille = taille;
        this.collision = false;
        this.nom = nom;

        this.animations = new ArrayList<Animation>();
        this.frames = 0;

        this.box = new Rectangle(p.getX(), p.getY(), taille.getLargeur(), taille.getLongeur());
        this.positionSurMap = positionSurMap;
    }


    /**
     * affichage du personnage dans le graphique */
    public void afficher(Graphics g) {
        /**
         * Quand il n'y a pas d'animation
         */
        if( this.animations.size() > 0) {
            g.drawAnimation(this.animations.get(this.frames), this.position.getX(), this.position.getY());

        } else {
            g.setColor(Color.black);
            g.fill(new Rectangle(this.position.getX(), this.position.getY(), this.taille.getLargeur(), this.taille.getLongeur()));
        }
        if(EcranJeu.DEBUG) {
            g.setColor(Color.blue);
            g.draw(this.box);
            g.drawString(this.nom, this.position.getX(), this.position.getY() + 10);
        }
    }

    // chargement des animations pour un personnage.
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 350);
        }
        this.animations.add(animation);
    }


    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(List<Animation> animations) {
        this.animations = animations;
    }

    public int getDirection() {
        return frames;
    }

    public void setDirection(int direction) {
        this.frames = direction;
    }

    public void interaction(Hero h) {
        System.err.println("no interaction with hero!");
    }

    public int getPositionSurMap() {
        return positionSurMap;
    }

    public void setPositionSurMap(int positionSurMap) {
        this.positionSurMap = positionSurMap;
    }
}