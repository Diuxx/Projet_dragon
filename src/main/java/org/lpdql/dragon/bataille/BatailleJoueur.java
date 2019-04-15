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
	private int value = -50;
	
	public void init() throws SlickException {
		this.joueur = InterStateComm.getLeHero();
		this.joueurImage = Ressources.spriteSheet.getSubImage(6, 10);
		this.animation = new PathAnimation(new BezierPath(0, 0, 10, 1, value, 20, 0, 0), 1000);

	}

	public void setValue(int v) {
		this.animation = new PathAnimation(new BezierPath(0, 0, 10, 1, v, 20, 0, 0), 1000);
	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		
		joueurImage.drawCentered(p.x + container.getWidth() * 1 / 4, p.y + container.getHeight() / 2);

		g.setColor(new Color(255, 255, 255));
		g.drawRect(container.getWidth() * 1 / 4 - 80, container.getHeight() / 2 - joueurImage.getHeight() / 2 - 30, 130, 20);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(container.getWidth() * 1 / 4 - 80 + 1, container.getHeight() / 2 - joueurImage.getHeight() / 2 - 30 + 1,
				Math.max(0, (joueur.getPointDeVieActuel() / joueur.getPointDeVie())) * 129,
				19);
		g.setColor(new Color(Color.white));
		g.drawString("" + (int) Math.max(0, (int) joueur.getPointDeVieActuel()), container.getWidth() * 1 / 4 - 30 , container.getHeight() / 2 - joueurImage.getHeight() / 2 - 29);
		
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
