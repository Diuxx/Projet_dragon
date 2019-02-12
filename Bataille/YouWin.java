package Bataille;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class YouWin extends BasicGameState{

	public static final int YouWin = 3;
	private Image background;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		this.background = new Image("image/youwin.jpg");
	}

	@Override
	public void render(GameContainer fenetreDeJeu, StateBasedGame arg1, Graphics arg2) throws SlickException {
		background.draw(0, 0, fenetreDeJeu.getWidth(), fenetreDeJeu.getHeight());	
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
	}
	
	@Override
	public int getID() {
		
		return 3;
	}

}

