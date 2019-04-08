package org.lpdql.dragon.bataille;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class BatailleJoueur {
	private Hero joueur;
	private Image joueurImage;
	private PathAnimation animation;

	public void init() throws SlickException {
		this.joueur = InterStateComm.getLeHero();
		this.joueurImage = Ressources.spriteSheet.getSubImage(6, 10);
		this.animation = new PathAnimation(new BezierPath(0, 0, 400, 1, -50, 20, 0, 0), 1000);

	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		joueurImage.drawCentered(p.x + container.getWidth() * 1 / 4, p.y + container.getHeight() / 2);

		g.setColor(new Color(255, 255, 255));
		g.drawRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueurImage.getHeight() / 2 - 30, 130, 20);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueurImage.getHeight() / 2 - 30,
				(joueur.getPointDeVieActuel() / joueur.getPointDeVie()) * 130,
				20);

	}

	public void update(int delta) {
		this.animation.update(delta);
	}

	public void attaquer(BatailleEnnemi batailleEnnemi) {
		animation.start();
	}

	public float getBarreVie() {
		return joueur.getPointDeVieActuel();
	}

	public void setBarreVie(int degat) {
		joueur.setPointDeVieActuel(joueur.getPointDeVieActuel() - degat);
	}

	public float getATK() {
		joueur.rafraichirLePouvoirATK();
		return ((int) (joueur.getATK()));
	}

	public void addAnimationListener(AnimationListener assignDamage, AnimationListener endAttack) {
		this.animation.addListener(500, assignDamage);
		this.animation.addListener(1000, endAttack);
	}

	public void setAnimation(PathAnimation animation) {
		this.animation = animation;
	}
}
