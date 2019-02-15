package singleton;


import carte.Carte;
import jeu.Ennemi;
import jeu.Hero;

/**
 * Class InterStateComm (communication entre les States du jeu).
 * Classe contenant des m�thodes pour transmettre des donn�es entre les States du jeu (map, bataille...)
 * Les variables sont effac�es apr�s lecture pour �viter l'utilisation de donn�es "p�rim�es"
 */
public final class InterStateComm {
    // volatile permet d'éviter le cas ou InterStateComm.leHero est non nul
    // mais pas encore instancié :
    // https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static volatile Hero leHero = null;

    /**
     * Variable battleEnnemy : ennemi rencontre sur la map et qui lance la bataille
     */
    private static volatile Ennemi unEnnemi = null;

    // ??
    private static volatile Carte laCarte = null;

    // sauvegarde des informations sur le hero
    /** ...  */

    /**
     * Constructeur de l'objet
     */
    private InterStateComm() {
        // La présence d'un constructeur privé supprime le constructeur public/
        super();
    }

    public final static Hero getLeHero() {
        if(InterStateComm.leHero == null) {

        }
        return InterStateComm.leHero;
    }

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
     * Ecriture BattleEnnemy
     */
    public final static void setUnEnnemi(Ennemi unEnnemi) {
        synchronized (InterStateComm.class) {
            InterStateComm.unEnnemi = unEnnemi;
            System.err.println("InterStateComm : " + unEnnemi.getNom() + " ajoute a la bataille !");
        }
    }

    public final static Ennemi getUnEnnemi() {
        synchronized (InterStateComm.class) {
            if(InterStateComm.unEnnemi == null) {
                return null;
            }
        }
        return InterStateComm.unEnnemi;
    }

    /**
     * Methode qui declare mort l'ennemi de la derni�re bataille
     */
    public final static void tuerUnEnnemi() {
        if (InterStateComm.unEnnemi != null) {
            InterStateComm.unEnnemi.setMort(true);
            InterStateComm.unEnnemi = null;
        }
        else {
            System.err.println("Pas d'ennemi enregistre pour la bataille");
        }
    }
}
