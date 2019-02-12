package retest;


import carte.Carte;
import jeu.Ennemi;
import jeu.Hero;

/**
 * class DragonState
 *
 * @author: Diuxx
 */
public final class Dragon {
    // volatile permet d'éviter le cas ou Dragon.leHero est non nul
    // mais pas encore instancié :
    // https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static volatile Hero leHero = null;
    private static volatile Ennemi unEnnemi = null;

    // ??
    private static volatile Carte laCarte = null;

    // sauvegarde des informations sur le hero
    /** ...  */

    /**
     * Constructeur de l'objet
     */
    private Dragon() {
        // La présence d'un constructeur privé supprime le constructeur public/
        super();
    }

    public final static Hero getLeHero() {
        if(Dragon.leHero == null) {

        }
        return Dragon.leHero;
    }

    public static void setLeHero(Hero leHero) {
        // Synchronized empêche toute instanciation multiple
        // même par différents threads
        synchronized (Dragon.class) {
            if (Dragon.leHero == null) {
                Dragon.leHero = leHero;
            }
        }
    }

    public final static void setUnEnnemi(Ennemi unEnnemi) {
        synchronized (Dragon.class) {
            Dragon.unEnnemi = unEnnemi;
        }
    }

    public final static Ennemi getUnEnnemi() {
        synchronized (Dragon.class) {
            if(Dragon.unEnnemi == null) {
                return null;
            }
        }
        return Dragon.unEnnemi;
    }
}
