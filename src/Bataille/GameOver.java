package Bataille;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState{
	public static final int GameOver = 2;
	private Image background;
	
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		this.background = new Image("image/youlose.png");		
	}

	
	public void render(GameContainer fenetreDeJeu, StateBasedGame arg1, Graphics arg2) throws SlickException {
		background.draw(0, 0, fenetreDeJeu.getWidth(), fenetreDeJeu.getHeight());
		
	}
	
	
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
