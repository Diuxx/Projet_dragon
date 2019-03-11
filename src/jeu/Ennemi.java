package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import sys.Direction;
import sys.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * class jeu.Ennemi
 *
 * @author: Diuxx
 */
public class Ennemi extends Personnage {

	// --
	private float timeSinceTrigger = 0;
	private Direction direction;
	private Direction directionTransition;

	private float x, y;
	private int tempsChangerDirection;

	private Hero lehero; // --
	private List<Personnage> lesPersonnages;
	private boolean mort;

	private boolean collid = false;

	private boolean bouge;
	private boolean veutCombattre;

	private Image ennemiImages;
	private int niveau;

	private double atk;
	private double maxHP;

	/**
	 * Class constructor
	 */
	public Ennemi(String nom, float x, float y, int w, int h, int pointDeVie, Direction direction, int t, float vitesse,
			Image ennemiImages, int niveau) {
		super(nom, x, y, w, h, pointDeVie, vitesse);
		this.direction = this.directionTransition = direction;
		this.x = x;
		this.y = y;
		this.tempsChangerDirection = t;
		this.bouge = true;
		this.veutCombattre = false;
		
		this.niveau = niveau;
		this.atk = niveau * 5.0 + 20;
		this.maxHP = niveau * 50.0 + 400;

		/**
		 * L'ennemi quand il est crée est vivant ! (visible)
		 */
		this.mort = false;
		lesPersonnages = new ArrayList<Personnage>();

		this.ennemiImages = ennemiImages;
	}

	/**
	 *
	 * @param nom
	 * @param pos
	 */
	public Ennemi(String nom, Point pos, int w, int h, int pointDeVie, Direction direction, int t, float vitesse,
			Image ennemiImages, int niveau) {
		this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t, vitesse, ennemiImages, niveau);
	}
	
	public Ennemi(String nom, Point pos, int w, int h, int pointDeVie, Direction direction, int t, float vitesse,
			Image ennemiImages, double atk, double maxHP) {
		this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t, vitesse, ennemiImages, 0);
		this.atk = atk;
		this.maxHP = maxHP;
	}

	/**
	 *
	 * @param delta
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
			super.moving = false;
			super.x = this.x;
			super.y = this.y;
			this.timeSinceTrigger = 0;
		}
	}

	@Override
	public void mouvement(int delta, TiledMap map) {
		if (!this.isBouge())
			return;
		super.mouvement(delta, map);
		this.move(delta);
	}

	/**
	 *
	 * @return
	 */
	public float getTimeSinceTrigger() {
		return timeSinceTrigger;
	}

	/**
	 *
	 * @param timeSinceTrigger
	 */
	public void setTimeSinceTrigger(float timeSinceTrigger) {
		this.timeSinceTrigger = timeSinceTrigger;
	}

	/**
	 *
	 * @param
	 */
	public void addCollision(Personnage unPersonnage) {
		this.lesPersonnages.add(unPersonnage);
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isCollisionPersonnage(float x, float y) {
		for (Personnage unPersonnage : lesPersonnages) {
			boolean collision = new Rectangle(x - 16, y - 20, this.getWidth(), this.getHeight())
					.intersects(unPersonnage.getBoundingBox());
			if (collision) {
				System.out.println("Ennemi -> Personnage");
				return true;
			} else {
				this.directionTransition = this.direction;
			}
		}
		return false;
	}

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

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean mort) {
		this.mort = mort;
	}

	public boolean isBouge() {
		return bouge;
	}

	public void setBouge(boolean bouge) {
		this.bouge = bouge;
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
	
	/////////////////

	public int getNiveau() {
		return niveau;
	}

	public double getATK() {
		return atk;
	}

	public double getHP() {
		return maxHP;
	}
}
