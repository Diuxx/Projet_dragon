package Bataille;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class BatailleEnnemi {
	public static Image ennemiImage;
	private int barreVie = 100;
	private PathAnimation animation;

	public void init() throws SlickException {
		this.animation = new PathAnimation(new BezierPath(0, 0, -400, 1, 50, 20, 0, 0), 1000);
	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		ennemiImage.drawCentered(p.x + container.getWidth() * 3 / 4, p.y + container.getHeight() / 2);
		g.setColor(new Color(255,255,255));
		g.drawRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemiImage.getHeight() / 2-30, 100, 20);
		g.setColor(new Color(255,0,0));
		g.fillRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemiImage.getHeight() / 2-30, barreVie, 20);
	}
	
	


	public void update(int delta) {
		this.animation.update(delta);
	}

	public void attaquer() {
		animation.start();
	}
	public Image getEnnemiImage() {
		return this.ennemiImage;
	}

	public void addAnimationListener(AnimationListener assignDamage, AnimationListener endAttack) {
		  this.animation.addListener(500, assignDamage);
		  this.animation.addListener(1000, endAttack);
	}
	
	public int getBarreVie() {
		return barreVie;
	}
	
	public void setBarreVie(int degat) {
		barreVie= barreVie-degat;
	}
	public void regenVie() {
		barreVie= 100;
	}
	
}

