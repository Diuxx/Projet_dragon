import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

// comment gérer le scenario
public class Scenario {

    // list des ennemis
    private List<Personnage> lesEnnemis;

    // list des pnj
    private List<PersonnageNonJoueur> lesPnj;

    // les different arts
    public static enum Art {
        EPE,
        BOUCLIER
    }

    public Scenario(Art currentArt, Carte map) {

        lesEnnemis = new ArrayList<Personnage>();
        lesPnj = new ArrayList<PersonnageNonJoueur>();

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
        PersonnageNonJoueur test = new PersonnageNonJoueur("Paul", position);

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

    }

    public List<Personnage> getLesEnnemis() {
        return lesEnnemis;
    }

    public List<PersonnageNonJoueur> getLesPnj() {
        return lesPnj;
    }
}
