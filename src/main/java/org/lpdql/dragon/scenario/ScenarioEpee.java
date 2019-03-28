package org.lpdql.dragon.scenario;


import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.personnages.PersonnageNonJoueur;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.SlickException;

/**
 * class ScenarioEpee
 *
 * @author: Diuxx
 */
public class ScenarioEpee extends Scenario {

    private final Taille BASIC_SIZE = new Taille(16, 16);
    private final Taille LARGE_SIZE = new Taille(32, 32);
    private final Taille BIG_SIZE = new Taille(64, 64);

    /**
     * Class constructor
     */
    public ScenarioEpee() throws SlickException {
        super();
    }

    @Override
    protected void chargerMondePouvoir(Carte map) {

    }

    @Override
    protected void chargerMondeFeu(Carte map) {

    }

    @Override
    protected void chargerMondeEpee(Carte map) {

    }

    @Override
    protected void chargerMondeBouclier(Carte map) {

    }

    /**
     *
     */
    @Override
    protected void chargerMaison(Carte map) {
        Point pLettre = findObjetPosition(map, "lettre");
        if(pLettre != null) {
            // we should do a letter object instead of (ObjetMessage) !
            ObjetMessage lettre = new ObjetMessage("Lettre", pLettre, BASIC_SIZE, 1);
            lettre.setMessage("blablabla#blablabla\nblablabla");
            super.getLesObjets().add(lettre);
        }
    }

    /**
     *
     */
    @Override
    protected void chargerMainMap(Carte map) {
        Point pServante = findPnjPosition(map, "servante");
        if(pServante != null) {
            PersonnageNonJoueur pnjServante = new PersonnageNonJoueur("Servante", pServante, 32, 32);
            pnjServante.loadAnimation(org.lpdql.dragon.monde.Ressources.spriteSheet_PNJ, 3, 4,  4);
            pnjServante.addDialogue("blablabla !#et blablablabla\nblablablabla");
            super.getLesPnj().add(pnjServante);
        }
    }

}
