package jeu;

import ennemis.Goblin;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

// comment gérer le scenario
public class Scenario {

    // list des ennemis
    private List<Ennemi> lesEnnemis;

    // list des pnj
    private List<PersonnageNonJoueur> lesPnj;

    private SpriteSheet spriteSheet_goblin;

    // les different arts
    public static enum Art {
        EPE,
        BOUCLIER
    }

    public Scenario(Art currentArt, Carte map) throws SlickException {

        lesEnnemis = new ArrayList<Ennemi>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();
        spriteSheet_goblin = new SpriteSheet("data/goblin.png", 64, 64);

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

        Point position = map.getPositionPersonnage(0, 255, 0);
        PersonnageNonJoueur test = new PersonnageNonJoueur("Paul", position, 32, 32);

        test.loadAnimation(Fenetre.spriteSheet, 3, 4,  11);
        test.loadAnimation(Fenetre.spriteSheet, 3, 4,  9);
        test.loadAnimation(Fenetre.spriteSheet, 3, 4,  8);
        test.loadAnimation(Fenetre.spriteSheet, 3, 4,  10);
        test.loadAnimation(Fenetre.spriteSheet, 3, 6,  11);
        test.loadAnimation(Fenetre.spriteSheet, 3, 6,  9);
        test.loadAnimation(Fenetre.spriteSheet, 3, 6,  8);
        test.loadAnimation(Fenetre.spriteSheet, 3, 6,  10);
        test.setDirection(2);
        test.addDialogue("Salut Nicolas comment vas-tu ?");

        lesPnj.add(test);

        position = map.getPositionPersonnage(0, 0, 255);
        Goblin unGoblin = new Goblin(position, 64, 64, 100);
        unGoblin.loadAnimation(spriteSheet_goblin, 0, 1, 2);
        unGoblin.loadAnimation(spriteSheet_goblin, 0, 1, 3);
        unGoblin.loadAnimation(spriteSheet_goblin, 0, 1, 0);
        unGoblin.loadAnimation(spriteSheet_goblin, 0, 1, 1);
        unGoblin.loadAnimation(spriteSheet_goblin, 1, 7, 2);
        unGoblin.loadAnimation(spriteSheet_goblin, 1, 7, 3);
        unGoblin.loadAnimation(spriteSheet_goblin, 1, 7, 0);
        unGoblin.loadAnimation(spriteSheet_goblin, 1, 7, 1);

        lesEnnemis.add(unGoblin);
    }

    public List<Ennemi> getLesEnnemis() {
        return lesEnnemis;
    }

    public List<PersonnageNonJoueur> getLesPnj() {
        return lesPnj;
    }
}
