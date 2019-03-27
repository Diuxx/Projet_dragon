package org.lpdql.dragon.scenario;


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
     * Art. Periode du jeu ou le hero doit accomplir tache pour valider les Art;
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
        arts.add(new Art("Epee", false));
        arts.add(new Art("Bouclier", false));
        arts.add(new Art("Feu", false));
        arts.add(new Art("Voler", false));

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

}
