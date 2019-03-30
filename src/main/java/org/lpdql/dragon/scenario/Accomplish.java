package org.lpdql.dragon.scenario;


import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.scenario.errors.ScenarioError;

import java.util.ArrayList;
import java.util.List;

/**
 * class Accomplish
 *
 * @author: Diuxx
 */
public class Accomplish {
    /**
     * Art. Periode du jeu ou le hero doit accomplir diff taches pour valider les Art;
     */
    private List<Art> arts;

    /**
     * the value of the last art.
     */
    private final String lastArt = "endGame";

    /**
     * that a class Constructor.
     */
    public Accomplish() {

        this.arts = new ArrayList<>();

        // add all knowed arts.
        for (Art.ENUM unArt : Art.ENUM.values()) {
            // --
            arts.add(new Art(unArt.toString(), false));
        }
    }

     /**
     * that a sub class Constructor.
     * @param savedData that should be a {@code Save} element
     */
    public void load(Save savedData) {
        System.out.println("Performing loading data on (accomplishement)");

        // instance of accomplish
        Accomplish save = savedData.getAccomplishement();
    }

    /**
     *
     *
     */
    public void save() {
        System.out.println("Accomplishement backup in progress\n");

    }

    /**
     *
     */
    public boolean setStoryStat(Story storyElement, boolean state) {
        storyElement.setState(state);
        return true;
    }

    /**
     * Returns {@code String} of the current Art name.
     * If all's arts is done he just return {@code String}
     * equivalent to "endGame".
     * @return {@code String} of the current Art name.
     */
    public String getCurrentArt() {
        for(Art unArt : arts)
        {
            if(!unArt.isDone())
                return unArt.getName();
        }
        return this.lastArt;
    }

    /**
     * Returns {@code String} corresponding to the cu
     * @param currentArt {@code String} the current Art
     * @throws ScenarioError if and only if the name is not matched.
     */
    public void setEndArt(String currentArt)
        throws ScenarioError
    {
        for(Art unArt : arts)
        {
            if(unArt.getName().equals(currentArt)) {
                unArt.setDone(true);
                return;
            }
        }
        throw new ScenarioError("unknown art Name!");
    }

    /**
     *
     * @param art
     * @return
     */
    public boolean isArtAccomplished(Art.ENUM art) {
        for(Art unArt : arts) {
            if(unArt.getName().equals(art.toString())) {
                if(unArt.isDone()) return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getLog() {
        String log = this.getClass().getSimpleName() + " -> log:";
        for(Art unArt : arts)
        {
            log += unArt.getName() + "(" + unArt.isDone() + ") - ";
        }
        System.err.println(log);
        return log;
    }

}
