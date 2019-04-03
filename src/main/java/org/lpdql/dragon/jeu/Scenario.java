package org.lpdql.dragon.jeu;

import org.lpdql.dragon.bataille.Bataille;
import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.objets.Heal;
import org.lpdql.dragon.objets.Objet;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.PersonnageNonJoueur;
import org.lpdql.dragon.personnages.ennemis.DarkMaster;
import org.lpdql.dragon.personnages.ennemis.Dragon;
import org.lpdql.dragon.personnages.ennemis.Goblin;
import org.lpdql.dragon.personnages.ennemis.Squelette;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Direction;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

// comment gérer le scenario
public class Scenario {

    // list des personnages.ennemis
    private List<Ennemi> lesEnnemis;

    // list des pnj
    private List<PersonnageNonJoueur> lesPnj;

    // List des objets
    private List<Objet> lesObjets;

    // les different arts
    public enum Art {
        EPE,
        BOUCLIER
    }

    /**
     * Taille des monstre & Pnj
     */
    private final Taille BASIC_SIZE = new Taille(16, 16);
    private final Taille LARGE_SIZE = new Taille(32, 32);
    private final Taille BIG_SIZE = new Taille(64, 64);

    /**
     *
     * @throws SlickException
     */
    public Scenario() throws SlickException {

        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();
        lesObjets = new ArrayList<Objet>();
    }

    /**
     *
     * @param map
     */
    public void charger(Carte map) {
        // --
        Art currentArt = Scenario.Art.EPE; // hero.getCurrentArt() //
        // currentArt.toString();
        // chargerClass(currentArt.toString());  ->
        switch(currentArt) {
            case EPE:
                //** I WILL REFACTOR THIS CODE WITH CLASS CALLING INSTEAD OF ALL IN ONE METHOD
                this.chargerEpe(map);
                break;
            case BOUCLIER:
                // this.chargerBouclie(map);
                break;
        }
        InterStateComm.getLeHero().addPnj(this.getLesPnj());
        InterStateComm.getLeHero().addObjets(this.getLesObjets());
    }

    /**
     * Chargement du scenario de l'épé
     */
    private void chargerEpe(Carte map) {
        this.findObjets(map);

        // cherche et affiche les ennemis sur la carte
        this.findEnnemis(map);

        if(map.getFileName().equals("maison")) {
            System.err.println("Scenario : maison !(epe)");

            Point pLettre = findObjetPosition(map, "lettre");
            if(pLettre != null) {
                // we should do a letter object instead of (ObjetMessage) !
                ObjetMessage lettre = new ObjetMessage("Lettre", pLettre, BASIC_SIZE, 1);
                lettre.setMessage("blablabla#blablabla\nblablabla");
                this.lesObjets.add(lettre);
            }
        }

        if(map.getFileName().equals("dragon")) {

            Point pServante = findPnjPosition(map, "servante");
            if(pServante != null) {
                PersonnageNonJoueur pnjServante = new PersonnageNonJoueur("Servante", pServante, 32, 32);
                pnjServante.loadAnimation(org.lpdql.dragon.monde.Ressources.spriteSheet_PNJ, 3, 4,  4);
                pnjServante.addDialogue("blablabla !#et blablablabla\nblablablabla");
                lesPnj.add(pnjServante);
            }
        }
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

    public void afficherEnnemis(Graphics g) {
        for(Ennemi unEnnemi : this.lesEnnemis) {
            if(!unEnnemi.isMort()) // si l'ennemi est vivant ?
                unEnnemi.afficher(g);
        }
    }

    /**
     *
     */
    public void resetScenario() {
        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();
        lesObjets = new ArrayList<Objet>();

        InterStateComm.getLeHero().removePnj();
        InterStateComm.getLeHero().removeObjets();
    }

    /**
     * Mouvement des personnages.ennemis sur la map.
     * @param map
     * @param delta
     * @param lesMessages transmet les messages des personnages.ennemis */
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
                unEnnemi.seCalme(); // l'ennemi ne déclanche plus de combat.

                // début d'un combat
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
     *
     * @param g
     * @param lesMessages
     */
    public void afficherPnj(Graphics g, Message lesMessages) {
        for(PersonnageNonJoueur pnj : this.lesPnj) {
            pnj.afficher(g);
            if(pnj.isParle()) {
                lesMessages.add(pnj.getDialogue());
                pnj.arreteDeParler();
            }
        }
    }

    /**
     * Affichage des objet sur la carte
     * @param g
     * @param lesMessages
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
        }
    }

    /**
     * (a supprimer !)
     * @param uneCarte
     */
    private void findObjets(Carte uneCarte) {
        int x, y;
        for(int o=0; o<uneCarte.getMap().getObjectCount(0); o++)
        {
            String type = uneCarte.getMap().getObjectType(0, o);
            if ("heal".equals(type)) {
                System.err.println("heal found !");
                x = uneCarte.getMap().getObjectX(0, o);
                y = uneCarte.getMap().getObjectY(0, o);
                this.lesObjets.add(new Heal(new Point(x, y), o));
            }
        }
    }

    /**
     *
     * @param carte
     * @param name
     * @return
     */
    private Point findObjetPosition(Carte carte, String name) {

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
     *
     * @param carte
     * @param name
     * @return
     */
    private Point findPnjPosition(Carte carte, String name) {

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
     *
     * @param carte
     */
    private void findEnnemis(Carte carte) {

        int layerIndex = 1;
        int nbEnnemi = carte.getMap().getObjectCount(1);

        System.out.println("layerIndex : " + carte.getMap().getObjectCount(1) + " nbEnnemi : " + nbEnnemi);

        int x, y;

        for(int i = 0; i<nbEnnemi; i++)
        {
            String ennemiName = carte.getMap().getObjectName(layerIndex, i);
            String direction = carte.getMap().getObjectType(layerIndex, i);

            x = carte.getMap().getObjectX(layerIndex, i);
            y = carte.getMap().getObjectY(layerIndex, i);
            switch(ennemiName) {
                case "Squelette":
                	lesEnnemis.add(new Goblin(new Point(x, y), getDirectionFromString(direction)));
                	lesEnnemis.add(new Squelette(new Point(x, y), getDirectionFromString(direction)));
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
     *
     * @param direction
     * @return
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
}
