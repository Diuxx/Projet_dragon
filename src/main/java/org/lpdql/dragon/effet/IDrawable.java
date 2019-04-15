package org.lpdql.dragon.effet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public interface IDrawable {

    public void afficher(Graphics g);
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y);
}
