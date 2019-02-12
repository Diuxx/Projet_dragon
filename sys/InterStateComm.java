package sys;

import jeu.Ennemi;

/**
 * Class InterStateComm (communication entre les States du jeu).
 * Classe contenant des m�thodes pour transmettre des donn�es entre les States du jeu (map, bataille...)
 * Les variables sont effac�es apr�s lecture pour �viter l'utilisation de donn�es "p�rim�es"
 */
public class InterStateComm {

    /**
     * Variable battleEnnemy : ennemi rencontr� sur la map et qui lance la bataille
     */
	
    private static Ennemi battleEnnemy = null;

    /**
     * Ecriture BattleEnnemy
     */
    public static void setBattleEnnemy(Ennemi unEnnemi) {
        battleEnnemy = unEnnemi;
    }

    /**
     * Methode qui declare mort l'ennemi de la derni�re bataille
     */
    public static void battleEnnemyDead() {
        if (battleEnnemy != null) {
            battleEnnemy.setMort(true);
            battleEnnemy = null;
        }
        else {
            System.err.println("Pas d'ennemi enregistr� pour la bataille");
        }

    }
}
