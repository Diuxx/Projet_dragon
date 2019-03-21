package org.lpdql.dragon.personnages;

import org.lpdql.dragon.system.EcranJeu;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class Personnage {

    // animation du personnage
    private List<Animation> animation;

    // ajout de collision avec d'autre personnage
    private List<Personnage> lesAutre;

    // information sur le personnage
    private String nom;
    private float pointDeVie;
    private float pointDeVieActuel;

    // mouvement du personnage
    protected float x = 0, y = 0;
    protected int direction = 0;
    protected boolean moving = false;

    // gestion des collisions
    private Rectangle box;

    private float futurX = 0;
    private float futurY = 0;
    private boolean collision = false;

    // taille de la tile
    private int width;
    private int height;
    private int centerX;
    private int centerY;

    // vitesse des personnages
    private float vitesse = 0.1f;

    private boolean dynamicCollision;

    // --
    public Personnage(String nom, float x, float y, int w, int h, float pointDeVie, float vitesse) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        this.centerX = (int)(w / 2);
        this.centerY = (int)(h - (h / 5));

        this.pointDeVie = pointDeVie;
        this.pointDeVieActuel = pointDeVie;

        this.vitesse = vitesse;

        this.animation = new ArrayList<Animation>();

        this.dynamicCollision = false;

        box = new Rectangle(x - this.centerX, y - this.centerY, w, h);
    }

    // --
    public Personnage(String nom, Point pos, int w, int h, float pointDeVie, float vitesse) {
        this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, vitesse);
    }

    // --
    public Personnage(String nom, Point pos, Taille t, float pointDeVie, float vitesse) {
        this(nom, pos.getX(), pos.getY(), t.getLargeur(), t.getLongeur(), pointDeVie, vitesse);
    }


    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    // mouvement du personnage
    public void mouvement(int delta, TiledMap map) {
        if (this.moving) {
            futurX = this.getFuturX(delta);
            futurY = this.getFuturY(delta);

            // float savedX = box.getX();
            // float savedY = box.getY();
            // box.setBounds(futurX - centerX, futurY - centerY, this.width, this.height);

            collision = iscollisionLogic(map, futurX, futurY);
            if(collision) {
                this.moving = false;
                this.dynamicCollision = false; // test
                // box.setBounds(savedX, savedY, this.width, this.height);
            } else {
                this.x = futurX;
                this.y = futurY;
                box.setBounds(futurX - centerX, futurY - centerY, this.width, this.height);
            }
        }
    }

    // changement de direction du personnage
    public void setDirection(int direction) {
        this.direction = direction;
    }

    // le personnage est en mouvement
    public void marcher() {
        this.moving = true;
    }

    // le personnage ne bouge pas
    public void stop() {
        this.moving = false;
    }

    /**
     * affichage du personnage dans le graphique
     */
    public void afficher(Graphics g) {
        /**
         * Quand il n'y a pas d'animation
         */
        if( this.animation.size() > 0) {
            g.drawAnimation(this.animation.get(direction + (moving ? 4 : 0)), x - centerX, y - centerY);

        } else {
            g.setColor(Color.black);
            g.fill(new Rectangle(x - centerX, y - centerY, this.width, this.height));
        }
        if(EcranJeu.DEBUG) {
            g.setColor(Color.red);
            g.draw(this.box);
            g.drawString(this.nom, x, y);
        }
    }

    // collision logic
    public boolean iscollisionLogic(TiledMap map, float x, float y) {
        int tileW = map.getTileWidth();
        int tileH = map.getTileHeight();

        int logicLayer = map.getLayerIndex("solide");
        Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        boolean collision = tile != null;
        if(collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        // test
        return collision || this.dynamicCollision;
    }

    public Rectangle getBoundingBox() {
        return this.box;
    }

    // chargement des animations pour un personnage.
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }//return animation;
        this.animation.add(animation);
    }

    // clacule de la position (x) futur
    private float getFuturX(int delta) {
        float futurX = this.x;
        switch (this.direction) {
            case 1:
                futurX = this.x - this.vitesse * delta;
                break;
            case 3:
                futurX = this.x + this.vitesse * delta;
                break;
        }
        return futurX;
    }

    private float getFuturY(int delta) {
        float futurY = this.y;
        switch (this.direction) {
            case 0:
                futurY = this.y - this.vitesse * delta;
                break;
            case 2:
                futurY = this.y + this.vitesse * delta;
                break;
        }
        return futurY;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getPointDeVie() {
        return pointDeVie;
    }

    public void setPointDeVie(float pointDeVie) {
        this.pointDeVie = pointDeVie;
    }

    public float getPointDeVieActuel() {
        return pointDeVieActuel;
    }

    public void setPointDeVieActuel(float f) {
        this.pointDeVieActuel = f;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    
    public String getNom() {
    	return this.nom;
    }
    
    public void setNom(String nom) {
    	this.nom = nom;
    }

    public void setPosition(Point p) {
        this.x = p.getX();
        this.y = p.getY();
        box.setBounds(this.x - centerX, this.y - centerY, this.width, this.height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        box.setBounds(this.x - centerX, this.y - centerY, this.width, this.height);
    }

    public boolean isDynamicCollision() {
        return dynamicCollision;
    }

    public void setDynamicCollision(boolean dynamicCollision) {
        this.dynamicCollision = dynamicCollision;
    }
}
