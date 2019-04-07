package org.lpdql.dragon.personnages;

import org.lpdql.dragon.bataille.Bataille;
import org.lpdql.dragon.interfaces.StoryElement;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.*;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

import static org.lpdql.dragon.system.EcranJeu.lesMessages;

/**
 * class Ennemi
 *
 * @author: Diuxx
 */
public class Ennemi extends Personnage implements StoryElement {

	/**
	 * This variable is a pointer to a Story Element
	 * a story element can be activated with
	 * some methods.
	 * @see StoryElement
	 */
	private Story storyElement;

	/**
	 * This variable is a timer for improve Ennemi movement
	 * mouvement.
	 */
	private float timeSinceTrigger = 0f;

	/**
	 * The actual direction of the ennemi
	 * @see Direction
	 */
	private Direction direction;
	private Direction directionTransition;

	/**
	 * The position of the current object on tiled map
	 */
	private float x, y;

	/**
	 * This variable is the time enlapsed before
	 * Tthe object switch direction.
	 */
	private int tempsChangerDirection;

	/**
	 * Personnage who can collid with this instance of Ennemi
	 * @see Personnage
	 */
	private List<Personnage> lesPersonnages;

	/**
	 * Status of this ennemi. if element can interact with?
	 * {@code true} if showable
	 * {@code false} if not showable
	 */
	private boolean mort;

	/**
	 * {@code true} when an instance of Ennemi want to fight.
	 */
	private boolean veutCombattre;
	private boolean requestFight;

	private Image ennemiImages;
    private Image imageCombat;
	private int niveau;

	private double atk;
	private double maxHP;
	protected int experience;

	/**
	 * by default it set to true for all ennemis
	 * {@code false} considered as fiendly
	 **/
	private boolean friendly;

	/**
	 * L'ennemi peut ne plus être hostile et ne pas attaquer
	 * Variables qui gère la durée durant laquel l'ennemi n'est
	 * pas hostile
	 **/
	private long friendlyTimer;
	private long targetFriendlyTimer;

	/**
	 * This manage a timer who allows the enemy to move after a collision.
	 */
	private long timerCanMoveAfterCollision;

	/**
	 * Default constructor of Ennemi class.
	 *
	 * @param nom name of the current ennemi
	 * @param x "x" position on map
	 * @param y "y" position on map
	 * @param w "w" width of current object
	 * @param h "h" heigth of current object
	 * @param pointDeVie max Life point of current
	 * @param direction movement patern
	 * @param t timer befor switch direction
	 * @param vitesse speed of current object
	 * @param ennemiImages drawable pic
	 * @param niveau level of current
	 */
	public Ennemi(String nom, float x, float y, int w, int h,
	  int pointDeVie, Direction direction, int t, float vitesse, Image ennemiImages, int niveau)
	{
		// need to call super constructor..
		super(nom, x, y, w, h, pointDeVie, vitesse);
		this.direction = this.directionTransition = direction;
		this.x = x;
		this.y = y;
		this.tempsChangerDirection = t;

		this.veutCombattre = false;
		this.requestFight = false; // collision hero -> ennemi

		// firendly gestionnary
		this.friendly = false;
		this.friendlyTimer = 0l;

		this.timerCanMoveAfterCollision = 0l;

		// Statistiques des Ennemis
		this.niveau = niveau;
		setAtk(niveau);
		setMaxHP(niveau);
		this.experience = 40; // test

        // when an instance of ennemi is created
		// he is alive (drawable)
        this.mort = false;

        // "Personnage" who can collid with
        lesPersonnages = new ArrayList<>();

        this.ennemiImages = ennemiImages;
        imageCombat = ennemiImages;
    }

	/**
	 * Surchage of constructor for do what we wanted to do.
	 * @param nom Name of the Ennemi
	 * @param pos Position on map (tiledMap)
	 * @see Point
	 */
	public Ennemi(
			String nom,
			Point pos,
			int w, int h,
	  		int pointDeVie,
			Direction direction,
			int t,
			float vitesse,
			Image ennemiImages,
			int niveau)
	{
		this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t, vitesse, ennemiImages, niveau);
	}

	/**
	 * Surchage of constructor including somes statistics
	 * @param nom name of the current ennemi
	 * @param w "w" width of current object
	 * @param h "h" heigth of current object
	 * @param pointDeVie max Life point of current
	 * @param direction movement patern
	 * @param t timer befor switch direction
	 * @param vitesse speed of current object
	 * @param ennemiImages drawable pic
	 * @param atk current atk of Ennemi
	 * @param maxHP max life point of Ennemi
	 */
	public Ennemi(
			String nom,
			Point pos,
			int w, int h,
			int pointDeVie,
			Direction direction,
			int t,
			float vitesse,
			Image ennemiImages,
			double atk,
			double maxHP)
	{
		this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t, vitesse, ennemiImages, 0);
		this.atk = atk;
		this.maxHP = maxHP;
	}

	/**
	 *
	 * @param nom
	 * @param position
	 * @param width
	 * @param height
	 */
	public Ennemi(String nom, Point position, int width, int height) {
		super(nom, position.getX(), position.getY(), width, height, 100, 0.1f);
	}

	/**
	 * This function manage the movement of an instance of this class.
	 * Ennemi movement can be :
	 * Random, Vertical, horizontal or immobile(static)
	 *
	 * @param delta slick shared timer.
	 *
	 * @see Direction
	 */
	public void move(int delta) {
		switch (((this.direction != this.directionTransition) ? this.directionTransition : this.direction)) {
		case RANDOM:
			this.timeSinceTrigger += delta;
			if (this.timeSinceTrigger > tempsChangerDirection) {
				super.direction = (int) (Math.random() * 4);
				this.timeSinceTrigger = 0;
			}
			break;
		case VERTICAL:
			if (this.timeSinceTrigger < tempsChangerDirection)
				super.direction = 2;
			else
				super.direction = 0;
			break;
		case HORIZONTAL:
			if (this.timeSinceTrigger < tempsChangerDirection)
				super.direction = 3;
			else
				super.direction = 1;
			break;
		case IMMOBILE:
			if (this.direction != this.directionTransition) {
				isCollisionPersonnage(this.getX(), this.getY());
			} // no movement
			break;
		default:
			break;
		}
		this.timeSinceTrigger += delta;
		super.moving = true;
		if (this.timeSinceTrigger > (tempsChangerDirection * 2)) {
			// super.moving = false;
			super.x = this.x;
			super.y = this.y;
			this.timeSinceTrigger = 0;
		}
	}

	/**
	 * This function manage the movement of an instance of this class.
	 *
	 * @param delta slick shared timer.
	 * @param map current map for collsition
	 *
	 * @see TiledMap
	 */
	@Override
	public void mouvement(int delta, TiledMap map) {

		this.checkTimerFriendly();

		if(this.veutCombattre() && !this.requestFight) {
			InterStateComm.setUnEnnemi(this);
			EcranJeu.gameState.enterState(Bataille.ID);
		}

		if(this.requestFight) this.requestFight = false;

		if(!isMoving()) this.marcher();

		super.mouvement(delta, map);
		if (this.moving)
			this.move(delta);
	}

	/**
	 * add to collision list a new Personnage
	 *
	 * @param unPersonnage instance of Personnage
	 *
	 * @see Personnage
	 */
	public void addCollision(Personnage unPersonnage) {
		this.lesPersonnages.add(unPersonnage);
	}

	/**
	 * Test if a collision is appened with Personnage list.
	 * @param x next postion of current instance of Ennemi
	 * @param y next position of current instance of Ennemi
	 * @return {@code true} if a collision is detected {@code false} otherwise
	 */
	public boolean isCollisionPersonnage(float x, float y) {
		for(Personnage p : lesPersonnages) {
			boolean collision = new Rectangle(x - getCenterX(), y - getCenterY() - (getHeight() - getCenterY()), getWidth(), getHeight() + (getHeight() - getCenterY())).intersects(p.getBoundingBox());
			// new Rectangle(x - 16, y - 20 - (32 - 20), 32, 32 + (32 - 20)).intersects(p.getBoundingBox());

			if(collision) {

				System.out.println(this.getBoundingBox().getX() + " = " + x + " & " +
						this.getBoundingBox().getY() + " = " + y + "  => " + p.getBoundingBox().getX());

				MyStdOut.write(MyStdColor.RED, "<" + this.getNom() + "> collision with : " + p.getNom());
				this.stop();

				if( (p instanceof Hero) && !this.isFriendly()) {
					// --
					if(!this.veutCombattre())
						lesMessages.add(this.parle());
					this.startCombat();
				}

				return true;
			}
		}
		return false;
	}

	/**
	 * Stop all movement of current.
	 */
	@Override
	public void stop() {
		super.stop();
		this.timerCanMoveAfterCollision = System.currentTimeMillis();
		this.timeSinceTrigger = 0;
	}

	/**
	 * Enable movmeent for current
	 */
	@Override
	public void marcher() {
		long timeElapsed = System.currentTimeMillis() - this.timerCanMoveAfterCollision;
		if(timeElapsed < 2000)
			return;
		super.marcher();
		this.timerCanMoveAfterCollision = 0l;
	}

	/**
	 * This class manage Collision with the World.
	 * we are trying to find in specific layer(solide) of TiledMap a color.
	 * if we are in a color with alpha > 0 collision with world is detected.
	 * @param map Current map
	 * @param x next position of current.
	 * @param y next position of current.
	 * @return {@code true} id collision appened {@code false} otherwise.
	 */
	@Override
	public boolean iscollisionLogic(TiledMap map, float x, float y) {
		int tileW = map.getTileWidth();
		int tileH = map.getTileHeight();

		int logicLayer = map.getLayerIndex("solide");
		Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) x % tileW, (int) y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision || isCollisionPersonnage(x, y);
	}

	/**
	 * This function manage firendly stats.
	 * @param h
	 * @param timer
	 */
	public void setFriendly(boolean h, int timer) {
		this.friendly = h;
		if(h) {
			this.targetFriendlyTimer = timer;
			this.friendlyTimer = System.currentTimeMillis();
			System.out.println(this.getNom() + " n'est plus une menace !");
		}
	}

	/**
	 * Retourne l'etat d'hostilité de l'ennemi.
	 * @return boolean value (true : when fiendly| false : when not friendly)
	 **/
	public boolean isFriendly() {
		return this.friendly;
	}

	/**
	 * This function manage firendly stats
	 **/
	public void checkTimerFriendly() {
		if(!this.friendly)
			return;

		if(System.currentTimeMillis() - this.friendlyTimer >= this.targetFriendlyTimer)
        {
            System.out.println(this.getNom() + " redevient une menace !");
            this.friendly = false;
        }
	}

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean mort) {
		this.mort = mort;
	}

	public void startCombat() {
		this.veutCombattre = true; // --
	}

	public void seCalme() {
		this.veutCombattre = false;
	}

	public boolean veutCombattre() {
		return veutCombattre;
	}

	public Image getEnnemiImages() {
		return ennemiImages;
	}

	public void setEnnemiImages(Image ennemiImages) {
		this.ennemiImages = ennemiImages;
	}

	/**
	 * Important !
	 * @return
	 */
	public String parle() {
		return this.getNom() + " : viens te battre !";
	}

	public int getNiveau() {
		return niveau;
	}

	public double getATK() {
		return atk;
	}

	public double getHP() {
		return maxHP;
	}
	
	public void setAtk(int niveau) {
		this.atk = niveau * 5.0 + 20;
	}
	public void setMaxHP(int niveau) {
		this.maxHP = niveau * 50.0 + 400;
	}

	/**
	 * This class ends a story element if it exist.
	 */
	@Override
	public void storyDone() {
		// --
		if(this.storyElement == null)
			return;

		this.storyElement.done();
	}

	/**
	 * fill the pointer if needed
	 * @param element
	 */
	@Override
	public void setStoryElement(Story element) {
		this.storyElement = element;
	}

	/**
	 * return {@code true} if storyElement is not Empty
	 * @return
	 */
	@Override
	public boolean containStoryElement() {
		return (this.storyElement != null);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Story getStoryElement() {
		return this.storyElement;
	}

	/**
	 *
	 * @return
	 */
	public int getExperience() {
		return experience;
	}


	public void requestFight() {
		this.requestFight = true;
	}
}
