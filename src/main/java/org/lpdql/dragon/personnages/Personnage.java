package org.lpdql.dragon.personnages;

import org.lpdql.dragon.system.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Personnage {

    /**
     * Array containing all animations of the object
     * @see Animation
     */
    private List<Animation> animation;

    /**
     * Name of the current {@code Personnage}
     */
    private String nom;

    /**
     * {@code Personnage} total life Points
     */
    private float pointDeVie;

    /**
     * Current {@code Personnage} life Points
     */
    private float pointDeVieActuel;

    /**
     * {@code Personnage} position on map.
     */
    protected float x;
    protected float y;

    /**
     *
     */
    protected int direction = 0;
    protected boolean moving = false;

    /**
     * Debug box managing collision
     * @see Rectangle
     */
    private Rectangle box;

    /**
     * Future calculated position for collisions
     */
    private float futurX = 0;
    private float futurY = 0;

    /**
     * If collision appened this variable value switch to {@code true}
     */
    private boolean collision = false;

    // taille de la tile
    private int width;
    private int height;
    private int centerX;
    private int centerY;

    // vitesse des personnages
    private float vitesse = 0.1f;

    private boolean dynamicCollision;

    /**
     * Main Constructor of Personnage class.
     * @param nom
     * @param x
     * @param y
     * @param w
     * @param h
     * @param pointDeVie
     * @param vitesse
     */
    public Personnage(String nom, float x, float y, int w, int h, float pointDeVie, float vitesse) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        // default center
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

    // mouvement du personnage
    public void mouvement(int delta, TiledMap map) {
        if (this.moving) {
            futurX = this.getFuturX(delta);
            futurY = this.getFuturY(delta);

            collision = iscollisionLogic(map, futurX, futurY);
            if(collision) {
                if(!(this instanceof Ennemi))
                    this.moving = false;
                this.dynamicCollision = false;
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
        // no annimation..
        if( this.animation.size() > 0) {

            g.drawAnimation(this.animation.get(direction + (moving ? 4 : 0)), x - centerX, y - centerY);
        } else {
            g.setColor(Color.black);
            g.fill(new Rectangle(x - centerX, y - centerY, this.width, this.height));
        }
        if(EcranJeu.DEBUG) {
            g.setColor(Color.red);
            g.draw(this.box);

            if(this instanceof Ennemi) {
                g.setColor(Color.cyan);
                // g.draw(new Rectangle(x - 16, y - 20 - (32 - 20), 32, 32 + (32 - 20)));
                g.draw(new Rectangle(x - centerX, y - centerY - (getHeight() - centerY), getWidth(), getHeight() + (getHeight() - centerY)));
                g.setColor(Color.red);
            }

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

    /**
     *
     * @param spriteSheet
     * @param startX
     * @param endX
     * @param y
     */
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }//return animation;
        this.animation.add(animation);
    }

    /**
     *
     * @param delta
     * @return
     */
    private float getFuturX(int delta) {
        float futurX = this.x;
        float vitesse = (!this.isMoving()) ? 0f : this.vitesse;

        switch (this.direction) {
            case 1:
                futurX = this.x - vitesse * delta;
                break;
            case 3:
                futurX = this.x + vitesse * delta;
                break;
        }
        return futurX;
    }

    /**
     *
     * @param delta
     * @return
     */
    private float getFuturY(int delta) {
        float futurY = this.y;
        float vitesse = (!this.isMoving()) ? 0f : this.vitesse;

        switch (this.direction) {
            case 0:
                futurY = this.y - vitesse * delta;
                break;
            case 2:
                futurY = this.y + vitesse * delta;
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

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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
