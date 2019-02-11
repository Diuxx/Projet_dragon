package jeu;

import carte.Carte;
import ennemis.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import retest.Dragon;
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

    // les different arts
    public static enum Art {
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
     * @param currentArt
     * @param map
     * @throws SlickException
     */
    public Scenario(Art currentArt, Carte map, Hero hero) throws SlickException {

        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();

        switch(currentArt) {
            case EPE:
                this.chargerEpe(map, hero);
                break;
        }
    }

    /**
     * Chargement du scenario de l'épé
     */
    private void chargerEpe(Carte map, Hero hero) {

        Point position = map.getPositionPersonnage(0, 0, 255);
        PersonnageNonJoueur test = new PersonnageNonJoueur("pnj Paul", position, 32, 32);

        test.loadAnimation(EcranJeu.spriteSheet, 3, 4,  11);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 4,  9);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 4,  8);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 4,  10);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 6,  11);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 6,  9);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 6,  8);
        test.loadAnimation(EcranJeu.spriteSheet, 3, 6,  10);
        test.setDirection(2);
        test.addDialogue("Salut Nicolas comment vas-tu ?");
        lesPnj.add(test);

        PersonnageNonJoueur unAutrePnj = new PersonnageNonJoueur("Durand", map.getPositionPersonnage(0, 1, 255), 32, 32);
        unAutrePnj.addDialogue("Un grand mystère entours la création du monde. Le roi de dragonia " +
                                    "a subitement disparue suite à la grande révolution du monde !/n Tu sais.." +
                                    "On a besoin que tu trouve pouquoi tout va mal depuis ce fameux jour !");
        lesPnj.add(unAutrePnj);

        PersonnageNonJoueur unAutrePnjs = new PersonnageNonJoueur("pnj pistache", map.getPositionPersonnage(0, 100, 255), 32, 32);
        unAutrePnjs.addDialogue("Je suis pistache! bien le bonjour !");
        lesPnj.add(unAutrePnjs);

        // ennemis
        lesEnnemis.add(new Squelette(map.getPositionPersonnage(0, 255, 255), Direction.RANDOM));
        lesEnnemis.add(new FireWarrior(map.getPositionPersonnage(255, 100, 0), Direction.VERTICAL));
        lesEnnemis.add(new FireWizard(map.getPositionPersonnage(255, 100, 0), Direction.HORIZONTAL));
        lesEnnemis.add(new Chauve(map.getPositionPersonnage(255, 200, 0), Direction.HORIZONTAL));
        Chauve uneChauve = new Chauve(map.getPositionPersonnage(255, 200, 0), Direction.HORIZONTAL);
        uneChauve.addCollision(hero);
        lesEnnemis.add(uneChauve);
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

    public void mouvement(Carte map, int delta) {
        // affichage des mouvement des ennemi
        for(Ennemi unEnnemi : this.lesEnnemis) {
            unEnnemi.mouvement(delta, map.getMap());
            if(unEnnemi.getBoundingBox().intersects(Dragon.getLeHero().getBoundingBox()))
            { // début d'un combat

            }
        }
    }

    public void afficherPnj(Graphics g, Message lesMessages) {
        for(PersonnageNonJoueur pnj : this.lesPnj) {
            pnj.afficher(g);
            if(pnj.isParle()) {
                lesMessages.add(pnj.getDialogue());
                pnj.arreteDeParler();
            }
        }
    }
}
