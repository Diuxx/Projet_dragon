package org.lpdql.dragon.bataille;

import org.lpdql.dragon.personnages.Ennemi;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class BatailleAnimation {
	
	private long duration;
	private long bufferDuration;
	private long bufferEnd;
	private Image image;
	private float x;
	private float y;
	
	public BatailleAnimation(Ennemi ennemi) {
		
		this.bufferDuration = 50000;
		this.bufferEnd = System.currentTimeMillis();
	}
	
	public void drawAtq(Graphics graphics) {
		if (System.currentTimeMillis() - this.bufferDuration > 10) {
			this.y -= 1;
			this.bufferDuration = System.currentTimeMillis();
		}
	}
	
	public boolean isEnd()
	{
		return (System.currentTimeMillis() - this.bufferEnd) > this.duration;
		
	}

	public Image getImage() {
		return this.image;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

}
