package org.lpdql.dragon.bataille;

import java.util.ArrayList;
import java.util.List;

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
	GameContainer gameContainer;
	Vector2f position;
	Graphics graphics;
	List<AttaqueAnimation> attaqueAnimations;
	List<BatailleAnimation> bataillelAnimation;
	private float x;
	private float y;

	public int getExperience() {
		return experience;
	}

	public void init() {
		this.animation = new PathAnimation(new BezierPath(0, 0, -400, 1, 50, 20, 0, 0), 1000);
		attaqueAnimations = new ArrayList<>();
	}

// <<<<<<< Nicolas_Nicolas
// 	public void setValue(int v) {
// 		this.animation = new PathAnimation(new BezierPath(0, 0, 10, 1, v, 20, 0, 0), 1000);
// 	}

// 	public void render(GameContainer container, Graphics g) {
// 		if(InterStateComm.getUnEnnemi() == null)

	public void render(GameContainer gameContainer, Graphics graphics) {
		if (InterStateComm.getUnEnnemi() == null)
			return;

		if (InterStateComm.getUnEnnemi() != this.ennemi)
			this.ennemi = InterStateComm.getUnEnnemi();

		this.gameContainer = gameContainer;
		this.graphics = graphics;
		this.experience = ennemi.getExperience();

		this.position = animation.currentLocation();
		this.x = position.x + (float) (gameContainer.getWidth() * 3 / 4);
		this.y = position.y + (float) (gameContainer.getHeight() / 2);
		drawEnnemi();
		drawBarHP();
		drawNbHP();

		for (AttaqueAnimation attaqueAnimation : attaqueAnimations) {
			attaqueAnimation.drawAtq(graphics);
		}

		for (int i = 0; i < attaqueAnimations.size(); i++) {
			if (attaqueAnimations.get(i).isEnd())
				attaqueAnimations.remove(i);
		}
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
		ennemi.getEnnemiImages().drawCentered(this.x, this.y);
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

	public void addAttaqueAnimation(String text) {
		this.attaqueAnimations.add(new AttaqueAnimation(text, 500, this.gameContainer.getWidth() * 3 / 4 - 8,
				this.gameContainer.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 50));
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
