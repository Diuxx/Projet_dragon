package org.lpdql.dragon.ecrans;

import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.hud.Hud;
import org.lpdql.dragon.hud.Hud_menu;
import org.lpdql.dragon.jeu.Message;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static org.lpdql.dragon.monde.Ressources.sounds;

/**
 *
 */
public class EcranJeu extends BasicGameState {

	/**
	 * Slick game State id
	 */
	public static final int ID = 8;

	/**
	 *
	 */
	private boolean updatePaused = false;

	/**
	 *
	 */
	private Save savedData;

	/**
	 * debug variable Modify some elements in game
	 **/
	public static final boolean DEBUG = false;

	/**
	 * game display management
	 **/
	private GameContainer container;

	/**
	 * This variable is a slick game state
	 * 
	 * @see BasicGameState
	 **/
	public static StateBasedGame gameState;

	/**
	 * This variable manages the display of messages in game
	 * 
	 * @see Message
	 */
	public static Message lesMessages; // test();

	/**
	 * This valiable manages the different maps of the game
	 * 
	 * @see Carte
	 */
	private Carte carte;

	/**
	 * This variable manage the game the points of view
	 */
	private Camera camera;

	/**
	 * Version 0 de la class scenario / histoire du jeu. à modifier commit 30-03
	 **/
	private org.lpdql.dragon.scenario.Scenario scenario;

	/**
	 * This variable manage the game Head up display show progress bars for example.
	 * 
	 * @see Hud
	 */
	private Hud hud = new Hud();

	/**
	 * This variable manage the game menu see this menu means that the game is
	 * paused.
	 * 
	 * @see Hud_menu
	 */
	private Hud_menu menu = new Hud_menu();

	// fade in effect
	private long current = System.currentTimeMillis();
	Image fadeImage;
	public static boolean fade;
	private float alpha = 1f;
	//

	/**
	 *
	 * @param gameContainer
	 * @param stateBasedGame
	 * @throws SlickException
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

		// fade in effect
		this.fadeImage = new Image("Data/logos/fade.png");
		this.fadeImage.setAlpha(alpha);

		// instantiation of the environment
		this.container = gameContainer;
		EcranJeu.gameState = stateBasedGame;

		Ressources.charger();
		InterStateComm.setLeHero(new Hero("", new Point(0, 0)));

		// instantiation of the message manager
		EcranJeu.lesMessages = new Message();

		hud.init(); // loading h U D
		menu.init(gameContainer); // loading Menu
	}

	public static boolean init = false;

	/**
	 * Cette fonction permet d'initialiser les données du jeu quand on en a besoin.
	 * Slick charge les GameStats(init) automatiquement au lancement du jeu, pour
	 * detecter et charger une nouvelle partie ce n'est pas pratique.
	 * 
	 * @throws SlickException
	 */
	private void init() throws SlickException {
		System.out.println("init out");

		// InterStateComm.setLeHero(new Hero("LPDQL", new Point(0, 0)));
		savedData = Save.detectSavedData(); // Loading saved data
		carte = new Carte(savedData.getCarteName()); // Map definition

		// Loading the hero of the story
		InterStateComm.getLeHero().setPosition(carte.getCheckPoint());
		InterStateComm.getLeHero().setSavedData(savedData);

		// initialization of camera position
		camera = new Camera(InterStateComm.getLeHero().getX(), InterStateComm.getLeHero().getY());

		// loading the scenario
		scenario = org.lpdql.dragon.scenario.Charger.charger_scenario(carte);
		init = true;
	}

	public static boolean victory = false;

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		if (!init)
			this.init();

		if (victory) {
			sounds.playZik("victory");
			EcranJeu.victory = false;
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		sounds.stopAll();
	}

	/**
	 *
	 * @param gameContainer
	 * @param stateBasedGame
	 * @param graphics
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		// updated point of view
		camera.translate(graphics, gameContainer);

		// drawing background layer from map
		carte.afficher(carte.getMap().getLayerIndex("background1"));
		carte.afficher(carte.getMap().getLayerIndex("background2"));

		// display non-player characters and enemies
		if (scenario != null) {
			scenario.afficherPnj(graphics, EcranJeu.lesMessages);
			scenario.afficherEnnemis(graphics);
			scenario.afficherObjets(graphics, EcranJeu.lesMessages);
		}

		// drawing Hero
		InterStateComm.getLeHero().afficher(graphics);

		// drawing message
		if (EcranJeu.lesMessages.afficher(graphics, gameContainer, this.camera) || menu.isShowing()) {
			// when we are displaying menu or message
			// we put the game on pause.
			this.setUpdatePaused(true);
		} else {
			// disable pause
			if (this.isUpdatePaused())
				this.setUpdatePaused(false);
		}

		// drawing overground layer from map
		carte.afficher(carte.getMap().getLayerIndex("overground1"));
		this.scenario.afficherEffets(graphics);

		// drawing hud
		hud.render(graphics, InterStateComm.getLeHero());

		// drawing menu
		if (menu.isShowing()) {
			MenuItem menustats = menu.render(gameContainer, graphics);
			switch (menustats) {
			case EXITGAME:
				container.exit();
				break;
			case BACK:
				menu.setShowing(false);
				this.setUpdatePaused(false);
				break;
			case SAVEGAME:
				savedData.save(InterStateComm.getLeHero(), carte.getFileName());
				menu.setShowing(false);
				this.setUpdatePaused(false);
				EcranJeu.lesMessages.add("Une sauvegarde à été effectué...");
				break;
			case NONE:
				break;
			}
		}

		// fade in effect
		if (EcranJeu.fade) {
			fadeImage.draw();
		}

		if (System.currentTimeMillis() - current > 5 && EcranJeu.fade) {
			this.fadeImage.setAlpha(this.fadeImage.getAlpha() - 0.005f);
			current = System.currentTimeMillis();
		}

		if (this.fadeImage.getAlpha() <= 0) {
			this.fadeImage.destroy();
			EcranJeu.fade = false;
		}
		/////////////
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		if (this.isUpdatePaused())
			return; // no update when game paused
		// test();
		scenario.detectMapChanged(this.carte, this.camera);
		scenario.update(this.carte, this.camera); // --

		// updating position + collisions
		InterStateComm.getLeHero().controle(gameContainer);
		InterStateComm.getLeHero().mouvement(delta, carte.getMap());

		// scenario all movement (ennemis/object) collision
		this.scenario.mouvement(this.carte, delta, EcranJeu.lesMessages);

		// updating camera position
		this.camera.update(gameContainer, this.carte, InterStateComm.getLeHero());
	}

	@Override
	public void keyReleased(int key, char c) {

		if (Input.KEY_W == key) {
			if (lesMessages != null && !lesMessages.containMessage()) {
				if (sounds.playing("beep")) {
					sounds.stop("beep");
				}
				sounds.playZik("beep");
				lesMessages.next();
			}
		}

		// request the menu
		if (Input.KEY_ENTER == key && !menu.isShowing() && this.menu.getDiff() > 1500 && lesMessages.containMessage()) {
			menu.setShowing(true);
			this.setUpdatePaused(true); // game paused

			System.out.println("<game> is paused : " + this.isUpdatePaused());
		}

		if (Input.KEY_A == key) {
			// Story.TUTOEND.done(); // test()
			MyStdOut.write(MyStdColor.BLUE, "<difficulte> " + InterStateComm.getNiveauDuJeu());
		}
	}

	@Override
	public void keyPressed(int key, char c) {
	}

	public boolean isUpdatePaused() {
		return updatePaused;
	}

	public void setUpdatePaused(boolean updatePaused) {
		this.updatePaused = updatePaused;
	}

	@Override
	public int getID() {
		return EcranJeu.ID;
	}
}