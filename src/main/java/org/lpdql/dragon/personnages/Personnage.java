package org.lpdql.dragon.personnages;

import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
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
     * Array who contains all the animations of the character
     * @see Animation
     */
    private List<Animation> animation;

    /**
     * Name of the current instance
     */
    private String nom;

    /**
     * total life point of the character
     */
    private float pointDeVie;

    /**
     * current point of life of the character
     */
    private float pointDeVieActuel;

    /**
     * character position on the map
     */
    protected float x, y;

    /**
     * direction du personnage
     * {0, 1, 2, 3}
     */
    protected int direction = 0;

    /**
     * Character movement management
     */
    protected boolean moving = false;

    /**
     * debug rectangle and collision management
     * @see Rectangle
     */
    private Rectangle box;

    /**
     * Calculated future position for collision management
     */
    private float futurX = 0, futurY = 0;

    /**
     * variable collision
     * {@code true} if a collision occurs
     */
    private boolean collision = false;

    /**
     * character size
     */
    private int width, height;

    /**
     * center of gravity of the character
     */
    private int centerX, centerY;

    /**
     * character speed
     */
    private float vitesse;

    /**
     * variable collision
     * {@code true} if a collision occurs
     */
    private boolean dynamicCollision;

    /**
     * Main Constructor of Personnage class.
     * @param nom Name of the current instance
     * @param x abscissa of the character on the map
     * @param y ordinate of the character on the map
     * @param w character width on the map
     * @param h height of the character on the map
     * @param pointDeVie total life point of the character
     * @param vitesse character speed
     */
    public Personnage(String nom, float x, float y, int w, int h, float pointDeVie, float vitesse)
    {
        // default information
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        // center of gravity calculated automatically
        this.centerX = (int)(w / 2);
        this.centerY = (int)(h - (h / 5));

        // life point
        this.pointDeVie = pointDeVie;
        this.pointDeVieActuel = pointDeVie;

        this.vitesse = vitesse;

        // Array of animations
        this.animation = new ArrayList<Animation>();

        this.dynamicCollision = false;

        box = new Rectangle(x - this.centerX, y - this.centerY, w, h);
    }

    public Personnage(String nom) {
    	this.nom = nom;
    }

    /**
     * Construtor overload of Personnage without x and y
     * @param nom Name of the current instance
     * @param pos abscissa and ordinate of the character on the map
     * @param w character width on the map
     * @param h height of the character on the map
     * @param pointDeVie total life point of the character
     * @param vitesse character speed
     *
     * @see Point
     */
    public Personnage(String nom, Point pos, int w, int h, float pointDeVie, float vitesse) {
        this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, vitesse);
    }

    /**
     * Construtor overload of Personnage without height and width
     * @param nom Name of the current instance
     * @param pos abscissa and ordinate of the character on the map
     * @param t character width & height on the map
     * @param pointDeVie total life point of the character
     * @param vitesse character speed
     *
     * @see Point
     * @see Taille
     */
    public Personnage(String nom, Point pos, Taille t, float pointDeVie, float vitesse) {
        this(nom, pos.getX(), pos.getY(), t.getLargeur(), t.getLongeur(), pointDeVie, vitesse);
    }

    /**
     * This method manages the movement of the character.
     * She also deals with the collision with the map and
     * with the different characters.
     * @param delta Slick timer
     * @param map card on which the character evolves
     *
     * @see TiledMap
     */
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

    /**
     * change of direction of the character
     * HORIZONTAL = 0
     * VERTICAL = 1
     * RANDOM = 2
     * IMMOBILE = 3
     * @param direction
     *
     * @see Direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * This method allows the character to move
     */
    public void marcher() {
        this.moving = true;
    }

    /**
     * This method prevents the character from moving
     */
    public void stop() {
        this.moving = false;
    }


    /**
     * This method show the character on the graphics
     * displays a black square with the character's dimention
     * if there is no animation.
     * @param g Slick graphics
     *
     * @see Graphics
     */
    public void afficher(Graphics g)
    {
        // --
        if( this.animation.size() > 0) {
            g.drawAnimation(this.animation.get(direction + (moving ? 4 : 0)), x - centerX, y - centerY);
        } else {
            // if there is no animation
            g.setColor(Color.black);
            g.fill(new Rectangle(x - centerX, y - centerY, this.width, this.height));
        }
        if(EcranJeu.DEBUG) {
            g.setColor(Color.red);
            g.draw(this.box);
            if(this instanceof Ennemi) {
                g.setColor(Color.cyan);
                g.draw(new Rectangle(x - centerX, y - centerY - (getHeight() - centerY), getWidth(), getHeight() + (getHeight() - centerY)));
                g.setColor(Color.red);
            }
            g.drawString(this.nom, x, y);
        }
    }

    /**
     * This class manages collisison with map elements
     * @param map map on which evolves the character
     * @param x future position for collision management
     * @param y future position for collision management
     * @return {@code true} if collsion appened {@code false} otherwise
     */
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

    /**
     * load the different animations of the character
     * @param spriteSheet loaded images
     * @param startX beginning abscissa
     * @param endX end abscissa
     * @param y position
     *
     * @see SpriteSheet
     */
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }//return animation;
        this.animation.add(animation);
    }

    /**
     * calculate with speed the future position of the character
     * @param delta slick timer
     * @return float future "x" of the character
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
     * calculate with speed the future position of the character
     * @param delta slick timer
     * @return float future "y" of the character
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

	// Level UP HERO
	public void setHeroStatistques(int niveau) {
		System.out.println("-----------------------------------");
		System.out.println("Hero Level UP++  ===> New hero statistiques : ");
		System.out.println("ATK power			  		: " + InterStateComm.getLeHero().getATK());
		// level up hero point de vie
		this.pointDeVie = (int) (niveau * 50.0 + 250);
		System.out.println("Total point de vie  				: " + this.pointDeVie);
		// bonus 10% vie for level up
		System.out.println("Point de vie actuel				: " + this.pointDeVieActuel);
		System.out.println("Bonus for level up 10% vie 			: " + this.pointDeVie * 0.10);
		this.pointDeVieActuel += (this.pointDeVie * 0.10);
		System.out.println("Point de vie actuel after the bouns 		: " + this.pointDeVieActuel);
		System.out.println("-----------------------------------");

	}

	// Level UP Ennemi
	public void setEnnemiStatistques(int niveau) {
		this.pointDeVieActuel = this.pointDeVie = (int) (niveau * 30.0 + 150);
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

    public Rectangle getBoundingBox() {
        return this.box;
    }

    public boolean isDynamicCollision() {
        return dynamicCollision;
    }

    public void setDynamicCollision(boolean dynamicCollision) {
        this.dynamicCollision = dynamicCollision;
    }
}
