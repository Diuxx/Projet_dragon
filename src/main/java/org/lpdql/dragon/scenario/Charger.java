package org.lpdql.dragon.scenario;


import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.SlickException;

/**
 * class ScenarioCharger
 *
 * @author: Diuxx
 */
public class Charger {

    /**
     * Private Class constructor
     * this class should not be instantiated
     */
    private Charger() {

    }

    /**
     * This class load the scenario depending of the hero current accomplishment
     *
     * @param map the map on which the player is located
     * @return current scenario data
     * @throws SlickException default slick exception if somthing about slick is wrong
     *
     * @see Scenario
     * @see Carte
     */
    public static Scenario charger_scenario(Carte map) throws SlickException {

        Scenario scenario = null;
        /**
         * We can test somes things here !
         */
        //String art = InterStateComm.getLeHero().getAccomplishement().getCurrentArt();
        String art = InterStateComm.getLeHero().getCurrentArt();
        switch(art)
        {
            case "Epee":
                scenario = new ScenarioEpee();
                scenario.charger(map);
                break;
            case "Bouclier":
                scenario = new ScenarioBouclier();
                scenario.charger(map);
                break;
            case "Feu":
                scenario = new ScenarioFeu();
                scenario.charger(map);
                break;
            case "Voler":
                scenario = new ScenarioVoler();
                scenario.charger(map);
                break;
            default:
                scenario = new ScenarioEnd();
                scenario.charger(map);
        }
        return scenario;
    }
}
