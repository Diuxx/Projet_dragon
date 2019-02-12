package sys;

import jeu.Ennemi;

/**
 * Class InterStateComm (communication entre les States du jeu).
 * Classe contenant des méthodes pour transmettre des données entre les States du jeu (map, bataille...)
 * Les variables sont effacées après lecture pour éviter l'utilisation de données "périmées"
 */
public class InterStateComm {

    /**
     * Variable battleEnnemy : ennemi rencontré sur la map et qui lance la bataille
     */
	
    private static Ennemi battleEnnemy = null;

    /**
     * Ecriture BattleEnnemy
     */
    public static void setBattleEnnemy(Ennemi unEnnemi) {
        battleEnnemy = unEnnemi;
    }

    /**
     * Methode qui declare mort l'ennemi de la dernière bataille
     */
    public static void battleEnnemyDead() {
        if (battleEnnemy != null) {
            battleEnnemy.setMort(true);
            battleEnnemy = null;
        }
        else {
            System.err.println("Pas d'ennemi enregistré pour la bataille");
        }

    }
}
