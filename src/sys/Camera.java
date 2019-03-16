package sys;

import carte.Carte;
import personnages.Hero;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * class Camera
 *
 * @author: Diuxx
 */
public class Camera {

    /**
     *
     */
    private float x, y;

    /**
     *
     */
    public Camera() {
        this.x = 0;
        this.y = 0;
    }

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param g
     * @param gameContainer
     */
    public void translate(Graphics g, GameContainer gameContainer) throws SlickException {
        g.translate(gameContainer.getWidth() / 2 - (int)x,
                    gameContainer.getHeight() / 2 - (int)y);
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void update(GameContainer gameContainer, Carte carte, Hero hero) {
        if(gameContainer.getHeight() > (carte.getMap().getTileHeight() * carte.getMap().getHeight()) &&
                gameContainer.getWidth() > (carte.getMap().getTileWidth() * carte.getMap().getWidth()))
            return;

        float xx = hero.getX();
        float yy = hero.getY();

        if(carte.isHeroCollidLeftLimit(gameContainer, hero)) // 1200 / 2 = 600
            xx = getX();
        if(carte.isHeroCollidTopLimit(gameContainer, hero))
            yy = getY();
        if(carte.isHeroCollidRightLimit(gameContainer, hero))
            xx = getX();
        if(carte.isHeroCollidBotLimit(gameContainer, hero))
            yy = getY();

        // System.err.println(xx + " : " + carte.getMap().getTileHeight() * carte.getMap().getWidth());

        this.update(xx, yy);
        this.correctionPosition(gameContainer, carte, xx, yy);
    }

    /**
     *
     * @param carte
     * @param x
     * @param y
     */
    private void correctionPosition(GameContainer gc, Carte carte, float x, float y) {
        int limit_left = (gc.getWidth() / 2);
        int limit_right = (carte.getMap().getTileWidth() * carte.getMap().getWidth()) - (gc.getWidth() / 2);
        int limit_top = (gc.getHeight() / 2);
        int limit_bottom = (carte.getMap().getTileHeight() * carte.getMap().getHeight()) - (gc.getHeight() / 2);

        if(x < limit_left)
            this.x = limit_left;
        if(x > limit_right)
            this.x = limit_right;
        if(y < limit_top)
            this.y = limit_top;
        if(y > limit_bottom)
            this.y = limit_bottom;
    }
}
