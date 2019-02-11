package Bataille;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class BatailleJoeur {
	private Image joueur;
	private int barreVie = 100;
	private PathAnimation animation;
	public void init() throws SlickException {
		this.joueur = new Image("data/bataille/hero.png");
		this.animation = new PathAnimation(new BezierPath(0, 0, 400, 1, -50, 20, 0, 0), 1000);
	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		joueur.drawCentered(p.x + container.getWidth() * 1 / 4, p.y + container.getHeight() / 2);
	    //joueur.drawCentered(container.getWidth() * 1 / 4,   container.getHeight() / 2);
	    g.setColor(new Color(255,255,255));
	    g.drawRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueur.getHeight() / 2-30, 100, 20);
	    
	    g.setColor(new Color(255,0,0));
	    g.fillRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueur.getHeight() / 2-30, barreVie, 20);
	    
	}
	public void update(int delta) {
		  this.animation.update(delta);
		}

	public void attaquer(BatailleEnnemi batailleEnnemi) {
		this.animation.start();
		//batailleEnnemi.setBarreVie(10);
		
	}
	
	public int getBarreVie() {
		return barreVie;
	}
	
	public void setBarreVie(int degat) {
		barreVie= barreVie-degat;
	}
	public void addAnimationListener(AnimationListener assignDamage, AnimationListener endAttack) {
		  this.animation.addListener(500, assignDamage);
		  this.animation.addListener(1000, endAttack);
	}
}

