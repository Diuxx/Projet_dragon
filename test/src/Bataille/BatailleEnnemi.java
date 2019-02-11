package Bataille;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

public class BatailleEnnemi {
	private Image ennemi;
	private int barreVie = 100;
	private PathAnimation animation;

	public void init() throws SlickException {
		this.ennemi = new Image("data/bataille/ennemi.png");
		this.animation = new PathAnimation(new BezierPath(0, 0, -400, 1, -50, 20, 0, 0), 1000);
	}

	public void render(GameContainer container, Graphics g) {
		Vector2f p = animation.currentLocation();
		ennemi.drawCentered(p.x + container.getWidth() * 3 / 4, p.y + container.getHeight() / 2);

		g.setColor(new Color(255,255,255));
		g.drawRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemi.getHeight() / 2-30, 100, 20);

		g.setColor(new Color(255,0,0));
		g.fillRect(container.getWidth() * 3 / 4 - 50, container.getHeight() / 2 - ennemi.getHeight() / 2-30, barreVie, 20);

	}


	public void attaquer(BatailleJoeur batailleJoeur) {
		this.animation.start();
	}

	public Image getEnnemi() {
		return this.ennemi;
	}


	public int getBarreVie() {
		return barreVie;
	}

	public void setBarreVie(int degat) {
		barreVie= barreVie-degat;
	}

	public void update(int delta) {
		this.animation.update(delta);
	}
}