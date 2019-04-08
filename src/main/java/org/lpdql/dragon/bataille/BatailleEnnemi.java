package org.lpdql.dragon.bataille;

import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class BatailleEnnemi {

	public Ennemi ennemi;
	private PathAnimation animation;
	private int experience;

	public int getExperience() {
		return experience;
	}


	public void init() {
		this.animation = new PathAnimation(new BezierPath(0, 0, -400, 1, 50, 20, 0, 0), 1000);
	}

	public void render(GameContainer container, Graphics g) {
		if(InterStateComm.getUnEnnemi() == null)
			return;

		if(InterStateComm.getUnEnnemi() != this.ennemi)
			this.ennemi = InterStateComm.getUnEnnemi();

		this.experience = ennemi.getExperience();
		Vector2f p = animation.currentLocation();
		ennemi.getEnnemiImages().drawCentered(p.x + container.getWidth() * 3 / 4, p.y + container.getHeight() / 2);
		g.setColor(new Color(255,255,255));
		g.drawRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemi.getEnnemiImages().getHeight() / 2-30, 130, 20);
		g.setColor(new Color(255,0,0));
		g.fillRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemi.getEnnemiImages().getHeight() / 2-30, (ennemi.getPointDeVieActuel()/ennemi.getPointDeVie()) *130, 20);
	}
	
	
	public void update(int delta) {
		this.animation.update(delta);
	}

	public void attaquer() {
		animation.start();
	}
	
	public Image getEnnemi() {
		return this.ennemi.getEnnemiImages();
	}

	public void addAnimationListener(AnimationListener assignDamage, AnimationListener endAttack) {
		  this.animation.addListener(500, assignDamage);
		  this.animation.addListener(1000, endAttack);
	}
	
	public float getBarreVie() {
		return ennemi.getPointDeVieActuel();
	}
	
	public float getATK() {
		return ennemi.getATK();
	}
	
	public void setBarreVie(int degat) {
		ennemi.setPointDeVieActuel( ennemi.getPointDeVieActuel() - degat);
	}
	public void regenVie() {
		ennemi.setPointDeVieActuel(ennemi.getPointDeVie());
	}
}

