package jeu;

import ennemis.Boo;
import ennemis.Chauve;
import ennemis.Goblin;
import ennemis.Slim;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

import java.awt.*;
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
    private Taille BASIC_SIZE = new Taille(16, 16);
    private Taille LARGE_SIZE = new Taille(32, 32);
    private Taille BIG_SIZE = new Taille(64, 64);

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

        PersonnageNonJoueur unAutrePnj = new PersonnageNonJoueur("pnj Nicolas", map.getPositionPersonnage(0, 1, 255), 32, 32);
        unAutrePnj.addDialogue("Un grand mystère entours la création du monde Un grand mystère entours la création\n " +
                                    "du monde ! tu sais..");
        lesPnj.add(unAutrePnj);

        PersonnageNonJoueur unAutrePnjs = new PersonnageNonJoueur("pnj pistache", map.getPositionPersonnage(0, 100, 255), 32, 32);
        unAutrePnjs.addDialogue("Je suis pistache! bien le bonjour !");
        lesPnj.add(unAutrePnjs);



        // ennemis
        Goblin unGoblin = new Goblin(map.getPositionPersonnage(0, 255, 255), BIG_SIZE, 'n', 1);
        unGoblin.addTarget(hero);
        lesEnnemis.add(unGoblin);

        lesEnnemis.add(new Slim(map.getPositionPersonnage(255, 100, 0), LARGE_SIZE, 'v', 1000));
        lesEnnemis.add(new Chauve(map.getPositionPersonnage(255, 200, 0), LARGE_SIZE, 'h', 1000));
        //lesEnnemis.add(new Boo(map.getPositionPersonnage(255, 255, 255), LARGE_SIZE, 'v', 1000));
        lesEnnemis.add(new Chauve(map.getPositionPersonnage(255, 255, 255), LARGE_SIZE, 'h', 1000));

    }

    public List<Ennemi> getLesEnnemis() {
        return lesEnnemis;
    }

    public List<PersonnageNonJoueur> getLesPnj() {
        return lesPnj;
    }
}
