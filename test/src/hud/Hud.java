package hud;

//import java.awt.*;

import jeu.Hero;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {
    /**
     *
     */
    private static final int P_BAR_X = 10;
    private static final int P_BAR_Y = 10;
    private static final int BAR_X = P_BAR_X + 47;
    private static final int BAR_Y = P_BAR_Y + 19;
    private static final int BAR_W = 130;
    private static final int BAR_H = 12;

    /**
     *
     */
    private static final Color LIFE_COLOR = new Color(255, 0, 0);
    private static final Color MANA_COLOR = new Color(0, 0, 255);
    private static final Color XP_COLOR = new Color(255, 0, 0);

    /**
     *
     */
    private Image playerbars;

    /**
     *
     */
    public Hud() {
        // --
    }

    /**
     *
     * @throws SlickException
     */
    public void init() throws SlickException {
        this.playerbars = new Image("data/playerBar.png");
    }

    /**
     *
     * @param g
     */
    public void render(Graphics g, Hero hero) {
        g.resetTransform();
        g.setColor(LIFE_COLOR);
        g.fillRect(BAR_X, BAR_Y, (hero.getPointDeVieActuel() / hero.getPointDeVie()) * BAR_W, BAR_H);
        g.drawImage(this.playerbars, P_BAR_X, P_BAR_Y);
    }
}
