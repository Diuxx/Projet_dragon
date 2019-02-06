package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import sys.Point;

/**
 * class jeu.Ennemi
 *
 * @author: Diuxx
 */
public class Ennemi extends Personnage {

    // --
    private float timeSinceTrigger = 0;
    private char direction;
    private float x, y;
    private int tempsChangerDirection;

    private Hero lehero; // --
    private boolean mort;

    /**
     * Class constructor
     */
    public Ennemi(String nom, float x, float y, int w, int h,  int pointDeVie, char direction, int t) {
        super(nom, x, y, w, h, pointDeVie);
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.tempsChangerDirection = t;

        /**
         * L'ennemi quand il est crée est vivant ! (visible)
         */
        this.mort = false;
    }


    /**
     *
     * @param nom
     * @param pos
     */
    public Ennemi(String nom, Point pos, int w, int h, int pointDeVie, char direction, int t) {
        this(nom, pos.getX(), pos.getY(), w, h, pointDeVie, direction, t);
    }


    /**
     *
     * @param delta
     */
    public void move(int delta) {
        switch (this.direction) {
            case 'r':
                this.timeSinceTrigger += delta;
                if (this.timeSinceTrigger > tempsChangerDirection) {
                    super.moving = true;
                    super.direction = (int) (Math.random() * 4);
                    this.timeSinceTrigger = 0;
                }
                break;
            case 'v':
                if (this.timeSinceTrigger < tempsChangerDirection)
                    super.direction = 2;
                else
                    super.direction = 0;
                this.timeSinceTrigger += delta;
                super.moving = true;
                break;
            case 'h':
                if (this.timeSinceTrigger < tempsChangerDirection)
                    super.direction = 3;
                else
                    super.direction = 1;
                 this.timeSinceTrigger += delta;
                 super.moving = true;
                 break;

            case 'n':
                // no movement
                break;

            default:
                break;
        }
        if (this.timeSinceTrigger > (tempsChangerDirection * 2)) {
            super.moving = false;
            super.x = this.x;
            super.y = this.y;
            this.timeSinceTrigger = 0;
        }
        if (super.moving) {
            switch (this.direction) {
                case 0:
                    super.y -= .1f * delta;
                    break;
                case 1:
                    super.x -= .1f * delta;
                    break;
                case 2:
                    super.y += .1f * delta;
                    break;
                case 3:
                    super.x += .1f * delta;
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void mouvement(int delta, TiledMap map) {
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
     * @param unHero
     */
    public void addTarget(Hero unHero) {
        this.lehero = unHero;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isCollisionHero(float x, float y) {
        boolean collision = new Rectangle(x - 16, y - 20, this.getWidth(), this.getHeight()).intersects(this.lehero.getBoundingBox());
        System.out.println(new Rectangle(x - 16, y - 20, this.getWidth(), this.getHeight()).intersects(this.lehero.getBoundingBox()));
        if(collision) {
            System.out.println("Fight ?");
            return true;
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
        if(collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision || ((this.lehero != null) ? isCollisionHero( x, y) : true);
    }



    public boolean isMort() {
        return mort;
    }

    public void setMort(boolean mort) {
        this.mort = mort;
    }
}
