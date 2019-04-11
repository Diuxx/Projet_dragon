package org.lpdql.dragon.singleton;

import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;

/**
 * Class InterStateComm (communication entre les States du jeu).
 * Classe contenant des methodes pour transmettre des donnees entre les States du jeu (map, bataille...)
 * Les variables sont effacees apres lecture pour eviter l'utilisation de donnees "perimees"
 *
 * source : https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
 */
public final class InterStateComm {

    /**
     * width of the game screen
     */
    public final static int gX = 1200;

    /**
     * height of the game screen
     */
    public final static int gY = 600;

    /**
     * volatile permet d'éviter le cas ou InterStateComm.leHero est non nul
     * mais pas encore instancié :
     */
    private static volatile Hero leHero = null;

    /**
     * This variable refers to an enemy against whom we have to fight
     **/
    private static volatile Ennemi unEnnemi = null;


    /**
     * Constructeur de l'objet
     */
    private InterStateComm() {
        // La présence d'un constructeur privé
        // supprime le constructeur public pas d'instanciation
        super();
    }

    /**
     * returns the current instance of the hero
     * @return
     */
    public final static Hero getLeHero() {
        return InterStateComm.leHero;
    }

    /**
     * modifies the current instance of the hero
     * @param leHero
     */
    public static void setLeHero(Hero leHero) {
        // Synchronized empêche toute instanciation multiple
        // même par différents threads
        synchronized (InterStateComm.class) {
            if (InterStateComm.leHero == null) {
                InterStateComm.leHero = leHero;
            }
        }
    }

    /**
     * add an enemy for a battle
     */
    public final static void setUnEnnemi(Ennemi unEnnemi) {
        synchronized (InterStateComm.class) {
            InterStateComm.unEnnemi = unEnnemi;
            MyStdOut.write(MyStdColor.CYAN, "<InterStateComm> " + unEnnemi.getNom() + " ajoute a la bataille");
        }
    }

    public final static Ennemi getUnEnnemi() {
        return InterStateComm.unEnnemi;
    }

    /**
     * Method that declares dead the enemy of the last battle
     */
    public final static void tuerUnEnnemi() {
        if (InterStateComm.unEnnemi != null) {

            MyStdOut.write(MyStdColor.CYAN, "<InterStateComm> Un ennemi est sur le point de mourir");

            if(unEnnemi.containStoryElement()) {
                EcranJeu.lesMessages.add(unEnnemi.getStoryElement().getMessage());
                unEnnemi.storyDone();
                if(unEnnemi.getStoryElement() == Story.TUTOFIRSTENNEMIWASKILLED) {
                    Story.TUTOEND.done();
                }
            }

            InterStateComm.unEnnemi.setMort(true);
            InterStateComm.unEnnemi = null;
        }
        else {
            System.err.println("Pas d'ennemi enregistre pour la bataille");
        }
    }

    public final static void enleverUnEnnemi() {
        InterStateComm.unEnnemi = null;
    }

    // Niveau du jeu
    private static int niveauDuJeu;

    public static int getNiveauDuJeu() {
        return niveauDuJeu;
    }

    public static void setNiveauDuJeu(int niveauDuJeu) {
        InterStateComm.niveauDuJeu = niveauDuJeu;
    }

}
