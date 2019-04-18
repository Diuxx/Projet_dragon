package org.lpdql.dragon.effet;

import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * class Effet
 *
 * @author: Diuxx
 */
public class Effet implements IDrawable {

    private Point position;
    private Taille taille;
    private List<Animation> animations;
    private int frames;
    private String name;

    /**
     * Class constructor
     */
    public Effet(String name, Point p, Taille taille) {
        this.position = p;
        this.taille = taille;
        this.animations = new ArrayList<Animation>();
        this.frames = 0;
        this.name = name;
    }

    @Override
    public void afficher(Graphics g) {
        /**
         * Quand il n'y a pas d'animation
         */
        if( this.animations.size() > 0) {
            g.drawAnimation(this.animations.get(this.frames), this.position.getX(), this.position.getY());
        } else {
            g.setColor(Color.black);
            g.fill(new Rectangle(this.position.getX(), this.position.getY(), this.taille.getLargeur(), this.taille.getHauteur()));
        }
    }

    @Override
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 350);
        }
        this.animations.add(animation);
    }

    public Animation getAnimation() {
        return this.animations.get(this.frames);
    }

    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int[] duration) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), duration[x]);
        }
        this.animations.add(animation);
    }

    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int h, int[] duration) {
        Animation animation = new Animation();
        for(int i=0; i<h; i++)
        {
            for (int x = startX; x < endX; x++) {
                animation.addFrame(spriteSheet.getSprite(x, y + i), duration[i]);
            }
        }
        this.animations.add(animation);
    }

    public String getName() {
        return this.name;
    }
}
