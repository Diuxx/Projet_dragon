package org.lpdql.dragon.bigBataille;

import org.newdawn.slick.Graphics;

public class AttaqueAnimation {
	
	private long duration;
	private long bufferDuration;
	private long bufferEnd;
	private String anims;
	private float x;
	private float y;
	
	public AttaqueAnimation(String text, long duration, float x, float y) {
		this.anims = text;
		this.duration = duration;
		this.x = x;
		this.y = y;
		this.bufferDuration = 50000;
		this.bufferEnd = System.currentTimeMillis();
	}
	
	public void drawAtq(Graphics graphics) {
		if (System.currentTimeMillis() - this.bufferDuration > 10) {
			this.y -= 1;
			this.bufferDuration = System.currentTimeMillis();
		}
		graphics.drawString(anims, x, y);
	}
	
	public boolean isEnd()
	{
		return (System.currentTimeMillis() - this.bufferEnd) > this.duration;
		
	}

	public String getText() {
		// TODO Auto-generated method stub
		return this.anims;
	}

	public float getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

}
