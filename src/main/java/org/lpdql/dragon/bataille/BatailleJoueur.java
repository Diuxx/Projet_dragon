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
	GameContainer gameContainer;
	Vector2f position;
	Graphics graphics;
	
	public void init() throws SlickException {
		this.joueur = InterStateComm.getLeHero();
		this.joueurImage = Ressources.spriteSheet.getSubImage(6, 10);
		this.animation = new PathAnimation(new BezierPath(0, 0, 400, 1, -50, 20, 0, 0), 1000);

	}

	public void render(GameContainer gameContainer, Graphics graphics) {
		this.position = animation.currentLocation();
		this.gameContainer = gameContainer;
		this.graphics = graphics;	
		drawJoueur();
		drawBarHP();
		drawNbHP();
		
	}

	private void drawJoueur() {
		joueurImage.drawCentered(this.position.x + this.gameContainer.getWidth() * 1 / 4, this.position.y + this.gameContainer.getHeight() / 2);
	}

	private void drawBarHP() {
		this.graphics.setColor(new Color(255, 255, 255));
		this.graphics.drawRect(this.gameContainer.getWidth() * 1 / 4 - 80, this.gameContainer.getHeight() / 2 - this.joueurImage.getHeight() / 2 - 30, 130, 20);
		this.graphics.setColor(new Color(255, 0, 0));
		this.graphics.fillRect(this.gameContainer.getWidth() * 1 / 4 - 80, this.gameContainer.getHeight() / 2 - this.joueurImage.getHeight() / 2 - 30,
				Math.max(0, (joueur.getPointDeVieActuel() / joueur.getPointDeVie())) * 130,
				20);
	}

	private void drawNbHP() {
		this.graphics.setColor(new Color(Color.white));
		this.graphics.drawString("" + (int) Math.max(0, (int) this.joueur.getPointDeVieActuel()), this.gameContainer.getWidth() * 1 / 4 - 30 , this.gameContainer.getHeight() / 2 - this.joueurImage.getHeight() / 2 - 29);
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
