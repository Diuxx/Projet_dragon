package org.lpdql.dragon.ecrans;

import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.hud.Hud;
import org.lpdql.dragon.hud.Hud_menu;
import org.lpdql.dragon.jeu.Message;
import org.lpdql.dragon.jeu.Scenario;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Camera;
import org.lpdql.dragon.system.MenuItem;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 */
public class EcranJeu extends BasicGameState {

    /**
     * Slick game State id
     */
    public static final int ID = 2;

    /**
     *
     */
    private boolean updatePaused = false;

    /**
     *
     */
    private Save savedData;

    /**
     * debug variable
     * Modify some elements in game
     **/
    public static final boolean DEBUG = true;

    /**
     * game display management
     **/
    private GameContainer container;

    /**
     * This variable is a slick game state
     * @see BasicGameState
     **/
    public static StateBasedGame gameState;

    /**
     * This variable manages the display of messages in game
     * @see Message
     */
    private Message lesMessages; // test();

    /**
     * This valiable manages the different maps of the game
     * @see Carte
     */
    private Carte carte;

    /**
     * This variable manage the game the points of view
     */
    private Camera camera;

    /**
     * Version 0 de la class scenario / histoire du jeu.
     * à modifier commit 30-03
     **/
    private  org.lpdql.dragon.scenario.Scenario scenario;

    /**
     * This variable manage the game Head up display
     * show progress bars for example.
     * @see Hud
     */
    private Hud hud = new Hud();

    /**
     * This variable manage the game menu
     * see this menu means that the game is paused.
     * @see Hud_menu
     */
    private Hud_menu menu = new Hud_menu();

    /**
     *
     * @param gameContainer
     * @param stateBasedGame
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
            throws SlickException
    {
        // instantiation of the environment
        this.container = gameContainer;
        this.gameState = stateBasedGame;

        // Texture loading
        Ressources.charger();

        // instantiation of the message manager
        lesMessages = new Message();

        savedData = Save.detectSavedData(); // Loading saved data
        carte = new Carte(savedData.getCarteName()); // Map definition

        // Loading the hero of the story
        InterStateComm.getLeHero().setPosition(carte.getCheckPoint());
        InterStateComm.getLeHero().setSavedData(savedData);

        // initialization of camera position
        camera = new Camera(
                InterStateComm.getLeHero().getX(),
                InterStateComm.getLeHero().getY());

        // chargement du scenario..
        /*scenario = new Scenario();
        scenario.charger(carte);*/

        // loading the scenario
        scenario = org.lpdql.dragon.scenario.Charger.charger_scenario(carte);

        hud.init(); // loading h U D
        menu.init(gameContainer); // loading Menu
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
            throws SlickException
    {
        // updated point of view
        camera.translate(graphics, gameContainer);

        // drawing background layer from map
        carte.afficher(carte.getMap().getLayerIndex("background1"));
        carte.afficher(carte.getMap().getLayerIndex("background2"));

        // display non-player characters and enemies
        if(scenario != null) {
            scenario.afficherPnj(graphics, this.lesMessages);
            scenario.afficherEnnemis(graphics);
            scenario.afficherObjets(graphics, this.lesMessages);
        }

        // drawing Hero
        InterStateComm.getLeHero().afficher(graphics);

        // drawing message
        if(lesMessages.afficher(graphics, gameContainer, this.camera) || menu.isShowing())
        {
            // when we are displaying menu or message
            // we put the game on pause.
            this.setUpdatePaused(true);
        } else {
            // disable pause
            if(this.isUpdatePaused()) this.setUpdatePaused(false);
        }

        // drawing overground layer from map
        carte.afficher(carte.getMap().getLayerIndex("overground1"));

        // drawing hud
        hud.render(graphics, InterStateComm.getLeHero());

        // drawing menu
        if(menu.isShowing()) {
            MenuItem menustats = menu.render(gameContainer, graphics);
            switch(menustats) {
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
                    lesMessages.add("Une sauvegarde à été effectué...");
                    break;
                case NONE:
                    break;
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if(this.isUpdatePaused())
            return; // no update when game paused

        // test();
        updateTrigger();

        // updating position + collisions
        InterStateComm.getLeHero().controle(gameContainer);
        InterStateComm.getLeHero().mouvement(delta, carte.getMap());

        // scenario all movement (ennemis/object) collision
        this.scenario.mouvement(this.carte, delta, this.lesMessages);

        // updating camera position
        this.camera.update(gameContainer, this.carte, InterStateComm.getLeHero());
    }


    @Override
    public void keyReleased(int key, char c) {

        if(Input.KEY_W == key) {
            if(lesMessages != null) lesMessages.next();
        }

        // request the menu
        if(Input.KEY_ENTER == key && !menu.isShowing() && this.menu.getDiff() > 1500 && lesMessages.containMessage()) {
            menu.setShowing(true);
            this.setUpdatePaused(true); // game paused

            System.out.println("<game> is paused : " + this.isUpdatePaused());
        }

        if(Input.KEY_A == key) {
            Story.TUTOEND.done(); // test()
        }
    }

    @Override
    public void keyPressed(int key, char c) { }

    /**
     *
     */
    private void updateTrigger() throws SlickException {
        String type;
        for(int o=0; o<this.carte.getMap().getObjectCount(0); o++) {
            if(isInTrigger(o)) {
                type = this.carte.getMap().getObjectType(0, o);
                if ("change-map".equals(type)) {
                    changeMap(o);
                }
                if ("heal".equals(type)) {
                    this.scenario.getHeals(o).interaction(InterStateComm.getLeHero());
                }
            }
        }
    }

    /**
     *
     * @param id
     * @return
     */
    private boolean isInTrigger(int id) {
        return InterStateComm.getLeHero().getX() > this.carte.getMap().getObjectX(0, id)
        && InterStateComm.getLeHero().getX() < this.carte.getMap().getObjectX(0, id) + this.carte.getMap().getObjectWidth(0, id)
        && InterStateComm.getLeHero().getY() > this.carte.getMap().getObjectY(0, id)
        && InterStateComm.getLeHero().getY() < this.carte.getMap().getObjectY(0, id) + this.carte.getMap().getObjectHeight(0, id);
    }

    /**
     *
     * @param objectID
     * @throws SlickException
     */
    private void changeMap(int objectID) throws SlickException {
        String ancienNomMap = this.carte.getFileName();
        String dest_map = this.carte.getMap().getObjectName(0, objectID);

        System.err.println(this.getClass().getSimpleName() + " : ancienNom -> " + ancienNomMap);
        if (!"undefined".equals(dest_map)) {
            this.carte.changeMap("data/" + dest_map + ".tmx");
            for(int o=0; o<this.carte.getMap().getObjectCount(0); o++)
            {
                String type = this.carte.getMap().getObjectType(0, o);
                if("position-map".equals(type)) {
                    if(this.carte.getMap().getObjectName(0, o).equals(ancienNomMap)) {
                        positionMap(o);
                        this.scenario.resetScenario();
                        this.scenario.charger(this.carte);
                    }
                }
            }
        }
    }

    /**
     *
     * @param objectID
     * @throws SlickException
     */
    private void positionMap(int objectID) throws SlickException {
        int x = this.carte.getMap().getObjectX(0, objectID);
        int y = this.carte.getMap().getObjectY(0, objectID);
        InterStateComm.getLeHero().setPosition(new Point(x, y));
        this.camera.setX(x);
        this.camera.setY(y);

        System.err.println(this.getClass().getSimpleName() + " : position hero (" + x + "; " + y + ")");
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