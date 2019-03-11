package jeu;

import Bataille.Bataille;
import Bataille.BatailleEnnemi;
import Objets.Heal;
import Objets.Objet;
import carte.Carte;
import ennemis.*;

import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import singleton.InterStateComm;
import sys.Direction;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

import java.util.ArrayList;
import java.util.List;

// comment gérer le scenario
public class Scenario {

    // list des ennemis
    private List<Ennemi> lesEnnemis;

    // list des pnj
    private List<PersonnageNonJoueur> lesPnj;

    // List des objets
    private List<Objet> lesObjets;

    // les different arts
    public static enum Art {
        EPE,
        BOUCLIER
    }

    private Heal heal;

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
        lesObjets = new ArrayList<>();
    }

    /**
     *
     * @param map
     */
    public void charger(Carte map) {
        Art currentArt = Scenario.Art.EPE;

        switch(currentArt) {
            case EPE:
                this.chargerEpe(map);
                break;
        }
    }

    /**
     * Chargement du scenario de l'épé
     */
    private void chargerEpe(Carte map) {
        this.findObjets(map);
        if(map.getFileName().equals("dragon")) {
            Point position = map.getPositionPersonnage(0, 0, 255);
            PersonnageNonJoueur test = new PersonnageNonJoueur("pnj Paul", position, 32, 32);

            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4, 11);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4, 9);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4, 8);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 4, 10);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6, 11);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6, 9);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6, 8);
            test.loadAnimation(Mondes.Ressources.spriteSheet, 3, 6, 10);
            test.setDirection(2);
            test.addDialogue("Salut Nicolas comment vas-tu ?");
//            lesPnj.add(test);
//
//            PersonnageNonJoueur PNJquiDonneEpee = new PersonnageNonJoueur("Durand", map.getPositionPersonnage(0, 1, 255), 32, 32);
//            PNJquiDonneEpee.loadAnimation(Mondes.Ressources.spriteSheet_PNJ, 6, 7,  4);
//            PNJquiDonneEpee.addDialogue("Un grand mystère entours la création du monde. Le roi de dragonia " +
//                    "a subitement disparue suite à la grande révolution du monde !/n Tu sais.." +
//                    "On a besoin que tu trouve pouquoi tout va mal depuis ce fameux jour !");
//            lesPnj.add(PNJquiDonneEpee);
//
//            PersonnageNonJoueur PNJServante = new PersonnageNonJoueur("PNJ Servante", map.getPositionPersonnage(0, 255, 255), 32, 32);
//            PNJServante.loadAnimation(Mondes.Ressources.spriteSheet_PNJ, 3, 4,  4);
//            PNJServante.addDialogue("Salut !!");
//            lesPnj.add(PNJServante);
//
//            PersonnageNonJoueur unAutrePnjs = new PersonnageNonJoueur("pnj pistache", map.getPositionPersonnage(0, 100, 255), 32, 32);
//            unAutrePnjs.addDialogue("Je suis pistache! bien le bonjour !");
//            lesPnj.add(unAutrePnjs);

            // ennemis
            lesEnnemis.add(new DarkMaster(map.getPositionPersonnage(0, 255, 255), Direction.RANDOM));
            lesEnnemis.add(new Dragon(map.getPositionPersonnage(0, 255, 255), Direction.RANDOM));
            lesEnnemis.add(new Goblin(map.getPositionPersonnage(0, 255, 255), Direction.RANDOM));
            lesEnnemis.add(new Squelette(map.getPositionPersonnage(0, 255, 255), Direction.RANDOM));
        }
    }

    public List<Ennemi> getLesEnnemis() {
        return lesEnnemis;
    }

    public List<PersonnageNonJoueur> getLesPnj() {
        return lesPnj;
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
        lesObjets = new ArrayList<>();

        InterStateComm.getLeHero().removePnj();
    }

    /**
     *
     * @param map
     * @param delta
     * @param lesMessages
     */
    public void mouvement(Carte map, int delta, Message lesMessages) {
        // affichage des mouvement des ennemi
        for(Ennemi unEnnemi : this.lesEnnemis) {
            if(unEnnemi.isMort())
                continue;

            if(unEnnemi.veutCombattre())
            {
                System.out.println(unEnnemi.getNom() + " veut se battre !");
                // afficher l'ennemi Image sur l'ecran de bataille
                BatailleEnnemi.ennemiImage = unEnnemi.getEnnemiImages();
                // --
                InterStateComm.setUnEnnemi(unEnnemi);

                EcranJeu.gameState.enterState(Bataille.ID);
                unEnnemi.seCalme();
            }
            unEnnemi.mouvement(delta, map.getMap());
            if(unEnnemi.getBoundingBox().intersects(InterStateComm.getLeHero().getBoundingBox()))
            { // début d'un combat
                System.out.println("intersect");
                lesMessages.add("boo !");
                unEnnemi.startCombat();
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

    public void afficherObjets(Graphics g, Message lesMessages) {
        for(Objet unObjet : lesObjets)
        {
            unObjet.afficher(g);
        }
    }

    private void findObjets(Carte uneCarte) {
        int x, y;
        for(int o=0; o<uneCarte.getMap().getObjectCount(0); o++)
        {
            String type = uneCarte.getMap().getObjectType(0, o);
            if ("heal".equals(type)) {
                System.err.println("heal found !");
                x = uneCarte.getMap().getObjectX(0, o);
                y = uneCarte.getMap().getObjectY(0, o);
                this.lesObjets.add(new Objets.Heal(new Point(x, y), o));
            }
        }
    }

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

    public void afficherHeal(Graphics g) {

        heal.afficher(g);
    }
}
