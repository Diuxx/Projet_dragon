package org.lpdql.dragon.bataille;

import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class BatailleEnnemi {
// ez
	public Ennemi ennemi;
	private PathAnimation animation;
	private int experience;
	GameContainer gameContainer;
	Vector2f position;
	Graphics graphics;

	public int getExperience() {
		return experience;
	}

	public void init() {
		this.animation = new PathAnimation(new BezierPath(0, 0, -400, 1, 50, 20, 0, 0), 1000);
	}

	public void render(GameContainer gameContainer, Graphics graphics) {

		if (InterStateComm.getUnEnnemi() == null)
			return;

		if (InterStateComm.getUnEnnemi() != this.ennemi)
			this.ennemi = InterStateComm.getUnEnnemi();
		this.gameContainer = gameContainer;
		this.graphics = graphics;
		this.experience = ennemi.getExperience();
		this.position = animation.currentLocation();
		
		drawEnnemi();
		drawBarHP();
		drawNbHP();

	}

	private void drawBarHP() {
		this.graphics.setColor(new Color(255, 255, 255));
		this.graphics.drawRect(this.gameContainer.getWidth() * 3 / 4 - 50,
				this.gameContainer.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 30, 130, 20);
		this.graphics.setColor(new Color(255, 0, 0));
		this.graphics.fillRect(this.gameContainer.getWidth() * 3 / 4 - 50,
				this.gameContainer.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 30,
				Math.max(0, (this.ennemi.getPointDeVieActuel() / this.ennemi.getPointDeVie())) * 130, 20);
	}

	private void drawNbHP() {
		this.graphics.setColor(new Color(Color.white));
		this.graphics.drawString("" + Math.max(0, (int) this.ennemi.getPointDeVieActuel()),
				this.gameContainer.getWidth() * 3 / 4 - 4,
				this.gameContainer.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 29);
	}

	private void drawEnnemi() {
		ennemi.getEnnemiImages().drawCentered(this.position.x + this.gameContainer.getWidth() * 3 / 4,
				this.position.y + this.gameContainer.getHeight() / 2);
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
		ennemi.setPointDeVieActuel(ennemi.getPointDeVieActuel() - degat);
	}

	public void regenVie() {
		ennemi.setPointDeVieActuel(ennemi.getPointDeVie());
	}
}
