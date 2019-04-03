package org.lpdql.dragon.bataille;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Difficulty;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class BatailleJoueur {
	private Image joueur;
	private PathAnimation animation;

	public void init() throws SlickException {
		this.joueur = Ressources.spriteSheet.getSubImage(6, 10);
		this.animation = new PathAnimation(new BezierPath(0, 0, 400, 1, -50, 20, 0, 0), 1000);

	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		joueur.drawCentered(p.x + container.getWidth() * 1 / 4, p.y + container.getHeight() / 2);

		g.setColor(new Color(255, 255, 255));
		g.drawRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueur.getHeight() / 2 - 30, 130, 20);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueur.getHeight() / 2 - 30,
				(InterStateComm.getLeHero().getPointDeVieActuel() / InterStateComm.getLeHero().getPointDeVie()) * 130,
				20);

	}

	public void update(int delta) {
		this.animation.update(delta);
	}

	public void attaquer(BatailleEnnemi batailleEnnemi) {
		animation.start();
	}

	public float getBarreVie() {
		return InterStateComm.getLeHero().getPointDeVieActuel();
	}

	public void setBarreVie(int degat) {
		InterStateComm.getLeHero().setPointDeVieActuel(InterStateComm.getLeHero().getPointDeVieActuel() - degat);
	}

	public float getATK() {
		switch (InterStateComm.getNiveauDuJeu()) {
		case Difficulty.FACILE:
			return ((int) (InterStateComm.getLeHero().getLevel() * 5.0 + 35));
		case Difficulty.DIFFICILE:
			return ((int) (InterStateComm.getLeHero().getLevel() * 4.0 + 25));
		case Difficulty.TRES_DIFFICILE:
			return ((int) (InterStateComm.getLeHero().getLevel() * 3.0 + 20));
		default:
			return 0;
		}

	}

	public void addAnimationListener(AnimationListener assignDamage, AnimationListener endAttack) {
		this.animation.addListener(500, assignDamage);
		this.animation.addListener(1000, endAttack);
	}

	public void setAnimation(PathAnimation animation) {
		this.animation = animation;
	}
}
