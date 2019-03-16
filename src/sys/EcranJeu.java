package sys;

import Mondes.Ressources;
import sauvegarde.Save;
import carte.Carte;
import hud.Hud;
import hud.Hud_menu;
import jeu.Message;
import jeu.Scenario;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import singleton.InterStateComm;

public class EcranJeu extends BasicGameState {

    private boolean updatePaused = false;

    private Save savedData;

    /**
     * Variable de debug
     * Modifie certains élements d'affichage dans l'environnement */
    public static final boolean DEBUG = true;
    public static final int ID = 2;

    /**
     * Gestion de la fenetre et de l'affichage */
    private GameContainer container;
    public static StateBasedGame gameState;

    /**
     * Variable de jeu et d'affichage */
    private Message lesMessages; /* test */
    private Carte carte;
    private Camera camera;

    /**
     * Version 0 de la class scenario / histoire du jeu. */
    private Scenario scenario;

    private Hud hud = new Hud();
    private Hud_menu menu = new Hud_menu();

    @Override
    public int getID() {
        return EcranJeu.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        // initalisation de la fenètre
        this.container = gameContainer;
        this.gameState = stateBasedGame;

        // chargement des textures
        Ressources.charger();
        lesMessages = new Message();

        /**
         * Chargement des données sauvegardés du jeu
         */
        savedData = Save.detectSavedData();
        carte = new Carte(savedData.getCarteName());

        // Chargement du hero de l'histoire
        InterStateComm.getLeHero().setPosition(carte.getCheckPoint());
        InterStateComm.getLeHero().setSavedData(savedData);

        // initialisation de la camera
        camera = new Camera(InterStateComm.getLeHero().getX(), InterStateComm.getLeHero().getY());

        // chargement du scenario..
        scenario = new Scenario();
        scenario.charger(carte);

        hud.init(); // --
        menu.init(gameContainer);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        camera.translate(graphics, gameContainer);

        // affichage de la carte
        carte.afficher(carte.getMap().getLayerIndex("background1"));
        carte.afficher(carte.getMap().getLayerIndex("background2"));

        // affichage des personnages non joueurs et des personnages.ennemis
        if(scenario != null) {
            scenario.afficherPnj(graphics, this.lesMessages);
            scenario.afficherEnnemis(graphics);
            scenario.afficherObjets(graphics, this.lesMessages);
        }

        // Affichage du hero
        InterStateComm.getLeHero().afficher(graphics);

        // affichage des messages
        if(lesMessages.afficher(graphics, gameContainer, this.camera) || menu.isShowing())
        {
            this.setUpdatePaused(true);
        } else {
            if(this.isUpdatePaused())
                this.setUpdatePaused(false);
        }

        // images positionné de telle façon
        // que les personnages/personnages.ennemis/pnjs puissent passer derrière.
        carte.afficher(carte.getMap().getLayerIndex("overground1"));
        hud.render(graphics, InterStateComm.getLeHero());

        if(menu.isShowing()) {
            sys.MenuItem menustats = menu.render(gameContainer, graphics);
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
            return;
        updateTrigger();

         // Gestion des mouvements et des collisions du hero
         // Les collisions sont géré automatiquement dans la class du herp
        InterStateComm.getLeHero().controle(gameContainer);
        InterStateComm.getLeHero().mouvement(delta, carte.getMap());

        // affichage des mouvement des ennemi
        this.scenario.mouvement(this.carte, delta, this.lesMessages);

        // mouvement de la camera
        this.camera.update(gameContainer, this.carte, InterStateComm.getLeHero());
    }

    @Override
    public void keyReleased(int key, char c) {
        if(Input.KEY_W == key) {
            if(lesMessages != null) lesMessages.next();
        }
        // Affichage du menu du jeu.
        if(Input.KEY_ENTER == key && !menu.isShowing() && this.menu.getDiff() > 1000) {
            menu.setShowing(true);
            this.setUpdatePaused(true);

            System.out.println("is paused : " + this.isUpdatePaused());
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
}