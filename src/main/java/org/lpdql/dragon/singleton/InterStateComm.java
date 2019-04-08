package org.lpdql.dragon.singleton;

import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.system.Difficulty;

/**
 * Class InterStateComm (communication entre les States du jeu).
 * Classe contenant des m�thodes pour transmettre des donn�es entre les States du jeu (map, bataille...)
 * Les variables sont effac�es apr�s lecture pour �viter l'utilisation de donn�es "p�rim�es"
 */
public final class InterStateComm {

    // screen width x height
    public static final int gX = 1200;
    public static final int gY = 600;
    
    // Niveau du jeu
    private static int niveauDuJeu;

    public static int getNiveauDuJeu() {
		return niveauDuJeu;
	}

	public static void setNiveauDuJeu(int niveauDuJeu) {
		InterStateComm.niveauDuJeu = niveauDuJeu;
	}


	// volatile permet d'éviter le cas ou InterStateComm.leHero est non nul
    // mais pas encore instancié :
    // https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)
    private static volatile Hero leHero = null;

    /**
     * Variable battleEnnemy : ennemi rencontre sur la map et qui lance la bataille */
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
        return InterStateComm.unEnnemi;
    }

    /**
     * Methode qui declare mort l'ennemi de la dernière bataille
     */
    public final static void tuerUnEnnemi() {
        if (InterStateComm.unEnnemi != null) {
 //           System.err.println("Un ennemi est sur le point de mourir !");
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
}
