package org.lpdql.dragon.personnages;

import org.lpdql.dragon.bigBataille.Bataille;
import org.lpdql.dragon.interfaces.StoryElement;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.*;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

import static org.lpdql.dragon.ecrans.EcranJeu.lesMessages;

/**
 * class personnages.Ennemi
 *
 * @author: Diuxx
 */
public class Ennemi extends Personnage implements StoryElement {

	/**
	 * this variable points to a Story Element
	 * a story can be activated or not
	 * @see StoryElement
	 */
	private Story storyElement;

	/**
	 * this varibale manages the movement of an enemy
	 */
	private float timeSinceTrigger = 0f;

	/**
	 * these variables manages the directions that the enemy can take
	 * @see Direction
	 */
	private Direction direction, directionTransition;

	/**
	 * the starting position saved from the enemy
	 * it's not the variables managing the movement
	 */
	private float x, y;

	/**
	 * this variable represents the time elapsed before changing direction
	 */
	private int tempsChangerDirection;

	/**
	 * objects that can collide with an enemy
	 * @see Personnage
	 */
	private List<Personnage> lesPersonnages;

	/**
	 * Enemy states. if element can interact with?
	 * {@code true} if it exists
	 * {@code false} if it not exists
	 */
	private boolean mort;

	/**
	 * just before the start of a fight this variable goes to {@code true}
	 */
	private boolean veutCombattre;

	/**
	 * if the hero collides, to post a message you must request a fight
	 * when it's the enemy that collides not need
	 */
	private boolean requestFight;

	/**
	 * image representing the enemy
	 */
	private Image ennemiImages, imageCombat;

	/**
	 * Enemy statistics
	 */
	private int niveau;

	protected int experience;
	private float atk;

	/**
	 * this variable manage the aggressiveness of an enemy
	 * by default it set to true for all ennemis
	 * {@code false} considered as fiendly
	 **/
	private boolean friendly;

	/**
	 * Variables that manages the duration during which the enemy is
	 * not hostile
	 * The enemy may no longer be hostile and not attack
	 **/
	private long friendlyTimer, targetFriendlyTimer;

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
		setEnnemiStatistques(this.niveau);
		setAtk(this.niveau);
		switch (InterStateComm.getNiveauDuJeu()) {
		case Difficulty.FACILE:
			this.experience = niveau * 5 + 50;
			break;
		case Difficulty.DIFFICILE:
			this.experience = niveau * 5 + 40;
			break;
		case Difficulty.TRES_DIFFICILE:
			this.experience = niveau * 5 + 20;
			break;
		}

        // when an instance of ennemi is created
		// he is alive (drawable)
        this.mort = false;

        // "Personnage" who can collid with
        lesPersonnages = new ArrayList<>();

        this.ennemiImages = ennemiImages;
        imageCombat = ennemiImages;
    }

	/**
	 * Construtor overload of Personnage without x and y
	 * @param nom name of the current ennemi
	 * @param pos abscissa and ordinate of the character on the map
	 * @param w "w" width of current object
	 * @param h "h" heigth of current object
	 * @param pointDeVie max Life point of current
	 * @param direction movement patern
	 * @param t timer befor switch direction
	 * @param vitesse speed of current object
	 * @param ennemiImages drawable pic
	 * @param niveau level of current
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
			float atk,
			double maxHP)
	{
		this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t, vitesse, ennemiImages, 0);
	}


	/**
	 * Test constructor
	 * @param nom Name of the current instance
	 * @param position abscissa and ordinate of the character on the map
	 * @param width character width on the map
	 * @param height height of the character on the map
	 */
	public Ennemi(String nom, Point position, int width, int height) {
		this(nom, position.getX(), position.getY(), width, height, 100, Direction.IMMOBILE, 100, 0f, null, 1);
	}

	/**
	 * This function manage the movement of the character
	 * HORIZONTAL = 0
	 * VERTICAL = 1
	 * RANDOM = 2
	 * IMMOBILE = 3
	 *
	 * @param delta slick timer.
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
	 * This function manage the movement of the character
	 *
	 * @param delta slick timer.
	 * @param map card on which the character evolves
	 *
	 * @see TiledMap
	 */
	@Override
	public void mouvement(int delta, TiledMap map) {

		this.checkTimerFriendly();

		if(this.veutCombattre() && !this.requestFight) {
			InterStateComm.setUnEnnemi(this);
			//EcranJeu.gameState.enterState(EcranBataille.ID);
			EcranJeu.gameState.enterState(Bataille.ID);
		}

		if(this.requestFight) this.requestFight = false;

		if(!isMoving()) this.marcher();

		super.mouvement(delta, map);
		if (this.moving)
			this.move(delta);
	}

	/**
	 * add a character to the character list for collisions
	 *
	 * @param unPersonnage instance of Personnage
	 * @see Personnage
	 */
	public void addCollision(Personnage unPersonnage) {
		this.lesPersonnages.add(unPersonnage);
	}

	/**
	 * test if a collision occurs with a character
	 * @param x future position for collision management
	 * @param y future position for collision management
	 * @return {@code true} if a collision is detected {@code false} otherwise
	 */
	public boolean isCollisionPersonnage(float x, float y) {
		for(Personnage p : lesPersonnages) {
			boolean collision = new Rectangle(x - getCenterX(), y - getCenterY() - (getHeight() - getCenterY()), getWidth(), getHeight() + (getHeight() - getCenterY())).intersects(p.getBoundingBox());
			if(collision) {
				MyStdOut.write(MyStdColor.RED, "<" + this.getNom() + "> collision with : " + p.getNom());
				this.stop();

				if( (p instanceof Hero) && !this.isFriendly()) {
					// --
					InterStateComm.getLeHero().stopWalkingSound();

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

		try {
			Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
			boolean collision = tile != null;
			if (collision) {
				Color color = tile.getColor((int) x % tileW, (int) y % tileH);
				collision = color.getAlpha() > 0;
			}
			return collision || isCollisionPersonnage(x, y);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		return false;
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
	 * Return the hostile state of the enemy
	 * @return Return the hostile state of the enemy {@code true} if appened
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

    public float getATK() {
		return this.atk;
	}

	public void setAtk(int niveau) {
		switch (InterStateComm.getNiveauDuJeu()) {
		case Difficulty.FACILE:
			this.atk = (int) (niveau * 5.0 + 15);
			break;
		case Difficulty.DIFFICILE:
			this.atk = (int) (niveau * 6.0 + 20);
			break;
		case Difficulty.TRES_DIFFICILE:
			this.atk = (int) (niveau * 6.0 + 25);
			break;
		default:
			this.atk =  0;
		}
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
