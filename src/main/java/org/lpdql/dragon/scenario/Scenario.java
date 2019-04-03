package org.lpdql.dragon.scenario;

import org.lpdql.dragon.bataille.Bataille;
import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.jeu.Message;
import org.lpdql.dragon.objets.Heal;
import org.lpdql.dragon.objets.Objet;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.personnages.PersonnageNonJoueur;
import org.lpdql.dragon.personnages.ennemis.DarkMaster;
import org.lpdql.dragon.personnages.ennemis.Dragon;
import org.lpdql.dragon.personnages.ennemis.Goblin;
import org.lpdql.dragon.personnages.ennemis.Squelette;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import org.lpdql.dragon.scenario.Story;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Scenario {

    /**
     * The game ennemis List. This one depend of
     * the current game scenario and the current map.
     *
     * @see Ennemi
     */
    private List<Ennemi> lesEnnemis;

    /**
     * The game Pnj's List. Also depend of the
     * current game Scenario and The current map.
     *
     * @see org.lpdql.dragon.personnages.PersonnageNonJoueur
     * @see org.lpdql.dragon.carte.Carte
     * @see org.lpdql.dragon.scenario.Scenario
     */
    private List<PersonnageNonJoueur> lesPnj;

    /**
     * The game Objets List. Also depend of the current game
     * Scenario & Map
     *
     * @see org.lpdql.dragon.objets.Objet
     * @see org.lpdql.dragon.carte.Carte
     * @see org.lpdql.dragon.scenario.Scenario
     */
    private List<Objet> lesObjets;

    /**
     * Constant, Position of "ennemis" layer on the map file.
     */
    private final int ENNEMIS_LAYER_INDEX = 1;

    /**
     * Constant, Position of "pnjs" layer on the map file.
     */
    private final int PNJS_LAYER_INDEX = 2;

    /**
     * Constant, Position of "objets" layer on the map file.
     */
    private final int OBJETS_LAYER_INDEX = 3;

    /**
     * Class constructor, instantiate each needed list
     * <tt>lesObjets</tt>
     * <tt>lesPnj</tt>
     * <tt>lesEnnemis</tt>
     *
     * @throws SlickException
     */
    public Scenario() throws SlickException {
        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();
        lesObjets = new ArrayList<Objet>();
    }

    /**
     * Default <tt>charger(Carte map)</tt> method.
     * This method load the story depending on where
     * the character is on the map.
     *
     * @param map the map on which the player is located
     */
    public void charger(Carte map) {

        MyStdOut.write(MyStdColor.BLUE, "Chargement du Scenario : " + this.getClass().getSimpleName());
        MyStdOut.write(MyStdColor.BLUE, "Chargement de la map : " + map.getFileName());

        switch(map.getFileName()) {
            case "maison":
                chargerMaison(map);
                break;
            case "dragon":
                chargerMainMap(map);
                break;
            case "monde_bouclier":
                chargerMondeBouclier(map);
                break;
            case "monde_epe":
                chargerMondeEpee(map);
                break;
            case "monde_feu":
                chargerMondeFeu(map);
                break;
            case "monde_pouvoir":
                chargerMondePouvoir(map);
                break;
        }

        this.findObjets(map.getMap());

        if(Story.TUTOEND.getState()) // test()
            this.findEnnemis(map.getMap());

        InterStateComm.getLeHero().addPnj(this.getLesPnj());
        InterStateComm.getLeHero().addObjets(this.getLesObjets());
    }

    /**
     * We are loading <tt>MondePouvoir</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMondePouvoir(Carte map) {
        MyStdOut.write(MyStdColor.BLUE, "Chargement du Scenario : " + this.getClass().getSimpleName());

    }

    /**
     * We are loading <tt>MondeFeu</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMondeFeu(Carte map) {

    }

    /**
     * We are loading <tt>MondeEpee</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMondeEpee(Carte map) {

    }

    /**
     * We are loading <tt>MondeBouclier</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMondeBouclier(Carte map) {

    }

    /**
     * We are loading <tt>MondeMaison</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMaison(Carte map) {


    }

    /**
     * We are loading <tt>dragon</tt> spec
     *
     * @param map the map on which the player is located
     */
    protected void chargerMainMap(Carte map) {

    }

    /**
     * Draw an ennemi {@code Ennemi} if and only if he is alive!
     * @param g the slick current graphic
     *
     * @see Ennemi
     */
    public void afficherEnnemis(Graphics g) {
        for(Ennemi unEnnemi : this.lesEnnemis) {
            if(!unEnnemi.isMort())
                unEnnemi.afficher(g);
        }
    }

    /**
     * Remove from all list, <tt>lesObjets</tt>, <tt>lesPnj</tt>, <tt>lesEnnemis</tt>
     * Each elements.
     */
    public void resetScenario() {
        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();
        lesObjets = new ArrayList<Objet>();

        InterStateComm.getLeHero().removePnj();
        InterStateComm.getLeHero().removeObjets();
    }

    /**
     * This function manages the movement of the enemy.
     * Also this function detects the ennemis intersection
     * with the player & it can send messages to the player.
     * At least it can also switch Ennemis stats :
     * Friendly -> aggressive.
     * The function {@code mouvement} are looping over the entire
     * {@code lesEnnemis} list if we found a dead ennemi we move on
     * to the next.
     *
     * @param map the map on which the player is located
     * @param delta slick timer method.
     * @param lesMessages messages transmitted from enemies.
     *
     * @see SlickException
     * @see Message
     **/
    public void mouvement(Carte map, int delta, Message lesMessages) {
        for(Ennemi unEnnemi : this.lesEnnemis) {
            if(unEnnemi.isMort())
                continue;

            // Passe l'ennemi de friendly à hostile quand le timer firendly est écoulé.
            unEnnemi.checkTimerFriendly();
            if(unEnnemi.veutCombattre() && !unEnnemi.isFriendly())
            {
                System.out.println(unEnnemi.getNom() + " veut se battre !");
                InterStateComm.setUnEnnemi(unEnnemi);
                // l'ennemi ne déclanche plus de combat.
                unEnnemi.seCalme();

                // Battle Start
                EcranJeu.gameState.enterState(Bataille.ID);
                continue;
            }
            unEnnemi.mouvement(delta, map.getMap());

            // détection de l'intersection avec un ennemi
            if(unEnnemi.getBoundingBox().intersects(InterStateComm.getLeHero().getBoundingBox()))
            {
                if(!unEnnemi.isFriendly()) {
                    System.out.println("intersect avec " + unEnnemi.getNom());
                    lesMessages.add(unEnnemi.parle());
                    unEnnemi.startCombat();
                }
                unEnnemi.setBouge(false);
            } else {
                if(!unEnnemi.isBouge())
                    unEnnemi.setBouge(true);
            }
        }
    }

    /**
     * Draw on the graphics all {@code PersonnageNonJoueur} from
     * pnj List. {@code PersonnageNonJoueur} can transmit messages.
     *
     * @param g Slick current graphic
     * @param lesMessages messages transmitted from Pnjs.
     */
    public void afficherPnj(Graphics g, Message lesMessages) {
        for(PersonnageNonJoueur pnj : this.lesPnj) {
            pnj.afficher(g);

            // transmission des messages
            if(pnj.isParle()) {
                lesMessages.add(pnj.getDialogue());
                pnj.arreteDeParler();
            }
        }
    }

    /**
     * Drawing {@code Objet} ellement from lesObjets. somes objets can
     * share messages.
     *
     * @param g Slick current graphic
     * @param lesMessages messages transmitted from Objet.
     */
    public void afficherObjets(Graphics g, Message lesMessages) {
        for(Objet unObjet : lesObjets)
        {
            unObjet.afficher(g);
            if(unObjet instanceof ObjetMessage) {
                ObjetMessage obj = ((ObjetMessage) unObjet);
                if(obj.isParle()) {
                    lesMessages.add(obj.getMessage());
                    obj.setParle(false);
                }
            }
            if(unObjet instanceof Heal) {
                // System.out.print("draw heal !");
                // unObjet.interaction(InterStateComm.getLeHero());
            }
        }
    }

    /**
     * (a supprimer !)
     * @param uneCarte
     */
    public void findObjets(TiledMap uneCarte) {
        int x, y;
        for(int o=0; o<uneCarte.getObjectCount(0); o++)
        {
            String type = uneCarte.getObjectType(0, o);
            if ("heal".equals(type)) {
                System.err.println("heal found !");
                x = uneCarte.getObjectX(0, o);
                y = uneCarte.getObjectY(0, o);
                this.lesObjets.add(new Heal(new Point(x, y), o));
            }
        }
    }

    /**
     * This method looks for an object on the map named. if she found it
     * she return the (objet) position {@code Point}.
     *
     * @param carte the map on which the player is located
     * @param name object Name (I think we should build an enum (objet) list.
     * @return {@code Point} or null if he dont found any objet by name.
     */
    protected Point findObjetPosition(Carte carte, String name) {

        int layerIndex = 3;
        int nbObjet = carte.getMap().getObjectCount(layerIndex);

        // position
        int x = 0;
        int y = 0;
        for(int i = 0; i<nbObjet; i++)
        {
            String objetName = carte.getMap().getObjectName(layerIndex, i);
            if (objetName.equals(name)) {
                x = carte.getMap().getObjectX(layerIndex, i);
                y = carte.getMap().getObjectY(layerIndex, i);
                return new Point(x, y);
            }
        }
        return null;
    }

    /**
     * This method looks for a PNJ on the map named. if she found it
     * she return the (Pnj) position {@code Point}.
     *
     * @param carte the map on which the player is located
     * @param name object Name (I think we should build an enum (Pnj) list.
     * @return {@code Point} or null if he dont found any Pnj by name.
     *
     * @see Carte
     */
    protected Point findPnjPosition(Carte carte, String name) {

        int layerIndex = 2;
        int nbObjet = carte.getMap().getObjectCount(layerIndex);

        // position
        int x = 0;
        int y = 0;
        for(int i = 0; i<nbObjet; i++)
        {
            String objetName = carte.getMap().getObjectName(layerIndex, i);
            if (objetName.equals(name)) {
                x = carte.getMap().getObjectX(layerIndex, i);
                y = carte.getMap().getObjectY(layerIndex, i);
                return new Point(x, y);
            }
        }
        return null;
    }

    /**
     * This method looks for all ennemis on the map. When she found them
     * she just fill lesEnnemis List.
     *
     * @param map the map on which the player is located
     *
     * @see Carte
     */
    public void findEnnemis(TiledMap map) {
        int layerIndex = 1;
        int nbEnnemi = map.getObjectCount(1);

        //System.out.println("layerIndex : " + map.getObjectCount(1) + " nbEnnemi : " + nbEnnemi);
        MyStdOut.write(MyStdColor.GREEN, "Nombre d'ennemi : " + nbEnnemi);
        int x, y;

        for(int i = 0; i<nbEnnemi; i++)
        {
            String ennemiName = map.getObjectName(layerIndex, i);
            String direction = map.getObjectType(layerIndex, i);

            x = map.getObjectX(layerIndex, i);
            y = map.getObjectY(layerIndex, i);
            switch(ennemiName) {
                case "Squelette":
                    lesEnnemis.add(new Squelette(new Point(x, y), getDirectionFromString(direction)));
                    break;
                case "Goblin":
                    lesEnnemis.add(new Goblin(new Point(x, y), getDirectionFromString(direction)));
                    break;
                case "DarkMaster":
                    lesEnnemis.add(new DarkMaster(new Point(x, y), getDirectionFromString(direction)));
                    break;
                case "Dragon":
                    lesEnnemis.add(new Dragon(new Point(x, y), getDirectionFromString(direction)));
                    break;
                default:
            }
        }
    }

    /**
     * We detect ennemis position on .tmx file.
     * it sends us back the position of the enemies in {@code String} value.
     * the function transforms the {@code String} position into {@code Direction}
     *
     * @param direction
     * @return {@code Direction} from string allowed { @code {h, v, r, i} }
     *         default {@code Direction.IMMOBILE}
     */
    public Direction getDirectionFromString(String direction) {
        Direction d = Direction.IMMOBILE;
        switch (direction) {
            case "h":
                d = Direction.HORIZONTAL;
                break;
            case "v":
                d = Direction.VERTICAL;
                break;
            case "r":
                d = Direction.RANDOM;
                break;
            case "i":
                d = Direction.IMMOBILE;
                break;
            default:
        }
        return d;
    }

    /***
     *
     * @param obj
     * @return
     */
    public Heal getHeals(int obj) {
        for(Objet unHeal : lesObjets)
        {
            if(!unHeal.getClass().getSimpleName().equals("Heal"))
                continue;

            if(unHeal.getPositionSurMap() == obj)
                return (Heal) unHeal;
        }
        return null;
    }

    public List<Ennemi> getLesEnnemis() {
        return lesEnnemis;
    }

    public List<PersonnageNonJoueur> getLesPnj() {
        return lesPnj;
    }

    public List<Objet> getLesObjets() {
        return lesObjets;
    }

    /**
     * This functon detect when the Hero is on a map trigger.
     * This function should be called in StageBasedGame update.
     * @param carte the map on which the player is located
     * @param camera the current display settings
     * @throws SlickException
     */
    public void detectMapChanged(Carte carte, Camera camera) throws SlickException {

        int changementMapGroupId = 0;
        int nbGate = carte
                    .getMap()
                    .getObjectCount(changementMapGroupId);
        String gateType = "";

        for(int i = 0; i<nbGate; i++)
        {
            if(!isHeroisInGate(carte.getMap(), i))
                continue;

            gateType = carte.getMap().getObjectType(changementMapGroupId, i);
            if(gateType.equals("change-map"))
            {
                if(!this.laodMapAuthorization(carte.getNomMap(), carte.getMap().getObjectName(changementMapGroupId, i))) {
                    // teleport on trigger
                    teleportOnSafeTrigger(carte, i);
                    return;
                }
                loadMap(carte, i, camera);
                return;
            }

            if(gateType.equals("heal"))
            {
                this.getHeals(i).interaction(InterStateComm.getLeHero());
            }
        }
    }

    /**
     *
     * @param carte
     * @param gateId
     */
    private void teleportOnSafeTrigger(Carte carte, int gateId) {
        // --
        int changementMapGroupId = 0;
        String destMap = carte.getMap().getObjectName(changementMapGroupId, gateId);

        if(destMap.equals("undefined"))
            return;

        int nbGate = carte.getMap().getObjectCount(changementMapGroupId);

        for(int i=0; i<nbGate; i++) {

            String gateType = carte.getMap().getObjectType(changementMapGroupId, i);
            if (!gateType.equals("position-map"))
                continue;

            if(!carte.getMap().getObjectName(changementMapGroupId, i).equals(destMap))
                continue;

            int x = carte.getMap().getObjectX(changementMapGroupId, i);
            int y = carte.getMap().getObjectY(changementMapGroupId, i);
            InterStateComm.getLeHero().setPosition(new Point(x, y));

            return;
        }
    }


    /**
     * Private Function load the new map when Hero hit a map trigger
     * @param carte the map on which the player is located
     * @param gateId tmx file share the object ID of the trigger
     * @param camera current display settings
     * @throws SlickException
     *
     * @see TiledMap
     */
    private void loadMap(Carte carte, int gateId, Camera camera) throws SlickException {

        int changementMapGroupId = 0;
        String lastMapName = carte.getFileName();
        String destMap = carte.getMap().getObjectName(changementMapGroupId, gateId);

        if(destMap.equals("undefined"))
            return;

        MyStdOut.write(MyStdColor.YELLOW,
                "<scenario> changement de map " + lastMapName + " -> " + destMap);

        // loading a new map
        carte.changeMap("data/" + destMap + ".tmx");
        int nbGate = carte.getMap().getObjectCount(changementMapGroupId);

        for(int i=0; i<nbGate; i++) {

            String gateType = carte.getMap().getObjectType(changementMapGroupId, i);
            if(!gateType.equals("position-map"))
                continue;

            if(!carte.getMap().getObjectName(changementMapGroupId, i).equals(lastMapName))
                continue;

            int x = carte.getMap().getObjectX(changementMapGroupId, i);
            int y = carte.getMap().getObjectY(changementMapGroupId, i);
            InterStateComm.getLeHero().setPosition(new Point(x, y));

            // that camera position
            camera.setX(x);
            camera.setY(y);

            this.resetScenario();
            this.charger(carte);
            return;
        }
    }

    /**
     * Return boolean {@code true} if hero is on trigger, {@code false}
     * if not.
     * @param tm the map on which the player is located
     * @param gateId tmx file share the object ID of the trigger
     * @return boolean {@code true} if hero is on trigger, or {@code false}
     *
     * @see TiledMap
     */
    private boolean isHeroisInGate(TiledMap tm, int gateId) {

        int changementMapGroupId = 0;
        Hero h = InterStateComm.getLeHero();
        int xgate = tm.getObjectX(changementMapGroupId, gateId);
        int ygate = tm.getObjectY(changementMapGroupId, gateId);
        int hgate = tm.getObjectHeight(changementMapGroupId, gateId);
        int wgate = tm.getObjectWidth(changementMapGroupId, gateId);

        return h.getX() > xgate && h.getX() < xgate + wgate &&
               h.getY() > ygate && h.getY() < ygate + hgate;
    }

    public void update(Carte map) {
        /* do somes mushibuchy */
    }

    /**
     *
     * @return
     */
    protected boolean laodMapAuthorization(String originMap, String destMap) {
        MyStdOut.write(MyStdColor.YELLOW, "<" + this.getClass().getSimpleName() + "> performing autorization");

        boolean autorization = true;
        boolean endScenarioTutoForEpee = (originMap.equals("dragon") && destMap.equals("monde_epe")) ? Story.TUTOEND.getState(): true;
        if(!endScenarioTutoForEpee) {
            EcranJeu.lesMessages.add(Story.TUTOEND.getMessage());
        }
        autorization = autorization && endScenarioTutoForEpee;

        boolean endScenarioEpeeForBouclier = (originMap.equals("dragon") && destMap.equals("monde_bouclier")) ? Story.ACTIVATEBOUCLIER.getState(): true;
        if(!endScenarioEpeeForBouclier) {
            EcranJeu.lesMessages.add(Story.ACTIVATEBOUCLIER.getMessage());
        }
        autorization = autorization && endScenarioEpeeForBouclier;

        boolean endScenarioBouclierForFeu = (originMap.equals("dragon") && destMap.equals("monde_feu")) ? Story.ACTIVATEFEU.getState(): true;
        if(!endScenarioBouclierForFeu) {
            EcranJeu.lesMessages.add(Story.ACTIVATEFEU.getMessage());
        }
        autorization = autorization && endScenarioBouclierForFeu;

        boolean endScenarioFeuForPouvoir = (originMap.equals("dragon") && destMap.equals("monde_pouvoir")) ? Story.ACTIVATEPOUVOIR.getState(): true;
        if(!endScenarioFeuForPouvoir) {
            EcranJeu.lesMessages.add(Story.ACTIVATEPOUVOIR.getMessage());
        }
        autorization = autorization && endScenarioFeuForPouvoir;



        return autorization;
    }

}
