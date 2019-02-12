package sys;

import carte.Carte;
import jeu.Hero;
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
        float xx = hero.getX();
        float yy = hero.getY();

        if(carte.isHeroCollidLeftLimit(gameContainer, hero))
            xx = getX();
        if(carte.isHeroCollidTopLimit(gameContainer, hero))
            yy = getY();
        if(carte.isHeroCollidRightLimit(gameContainer, hero))
            xx = getX();
        if(carte.isHeroCollidBotLimit(gameContainer, hero))
            yy = getY();

        this.update(xx, yy);
    }
}
