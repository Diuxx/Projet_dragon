package org.lpdql.dragon.system;

import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.jeu.Scenario;
import org.lpdql.dragon.singleton.InterStateComm;
import org.newdawn.slick.SlickException;

/**
 * class Trigger
 *
 * @author: Diuxx
 */
public class Trigger {

    /**
     * Class constructor
     */
    private Trigger() {
        // --
    }

    /**
     *
     */
    private void updateTrigger(Camera uneCamera, Scenario unScenario, Carte uneCarte) throws SlickException {
        String type = "";
        int triggerCount = uneCarte.getMap().getObjectCount(0);


        for(int o=0; o<triggerCount; o++)
        {
            if(isInTrigger(uneCarte, o)) {
                type = uneCarte.getMap().getObjectType(0, o);
                if ("change-map".equals(type)) {
                    changeMap(uneCamera, uneCarte, unScenario, o);
                }
            }
        }
    }

    /**
     *
     * @param id
     * @return
     */
    private boolean isInTrigger(Carte uneCarte, int id) {
        return InterStateComm.getLeHero().getX() > uneCarte.getMap().getObjectX(0, id)
                && InterStateComm.getLeHero().getX() < uneCarte.getMap().getObjectX(0, id) + uneCarte.getMap().getObjectWidth(0, id)
                && InterStateComm.getLeHero().getY() > uneCarte.getMap().getObjectY(0, id)
                && InterStateComm.getLeHero().getY() < uneCarte.getMap().getObjectY(0, id) + uneCarte.getMap().getObjectHeight(0, id);
    }

    /**
     *
     * @param objectID
     * @throws SlickException
     */
    private void changeMap(Camera uneCamera, Carte uneCarte, Scenario unScenario, int objectID) throws SlickException {
        String ancienNomMap = uneCarte.getFileName();
        String dest_map = uneCarte.getMap().getObjectName(0, objectID);

        System.err.println(this.getClass().getSimpleName() + " : ancienNom -> " + ancienNomMap);
        if (!"undefined".equals(dest_map)) {
            uneCarte.changeMap("data/" + dest_map + ".tmx");
            for(int o=0; o<uneCarte.getMap().getObjectCount(0); o++)
            {
                String type = uneCarte.getMap().getObjectType(0, o);
                if("position-map".equals(type)) {
                    if(uneCarte.getMap().getObjectName(0, o).equals(ancienNomMap)) {
                        positionMap(uneCamera, uneCarte, o);
                        unScenario.resetScenario();
                        unScenario.charger(uneCarte);
                    }
                }
            }
        }
    }

    /**
     *
     * @param objectID
     * @throws SlickException
     */
    private void positionMap(Camera uneCamera, Carte uneCarte, int objectID) throws SlickException {
        int x = uneCarte.getMap().getObjectX(0, objectID);
        int y = uneCarte.getMap().getObjectY(0, objectID);
        InterStateComm.getLeHero().setPosition(new Point(x, y));
        uneCamera.setX(x);
        uneCamera.setY(y);

        System.err.println(this.getClass().getSimpleName() + " : position hero (" + x + "; " + y + ")");
    }

}
