package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import sys.Point;
import sys.Taille;

public class Heal {
	private Image  soin;
	private float x = 0, y = 0;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	public boolean estPris = false;
    public Heal(Point pos, Taille t) {

        x = pos.getX(); y = pos.getY(); width = t.getLargeur(); height = t.getLongeur();
        this.centerX = (int)(width / 2);

        this.centerY = (int)(height - (height / 5));

    }
    public void init() throws SlickException{
		soin = new Image("data/soin.png");
    }
    
    public void afficher(Graphics g) {
    	if (!estPris) {
    		soin.draw(x - centerX, y - centerY, width, height);
    	}
    }

}