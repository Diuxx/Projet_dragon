package org.lpdql.dragon.scenario;

import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;

/**
 *
 */
public enum Story {
    TUTOEND("fin_tuto", "Fin du tuto jeu", false),
    TUTOFIRSTKILL("tuto_first_kill", "Premier ennemi tué lors du tuto", false),
    ACTIVATEEPEE("activation_monde_epe", "activation du monde de l'epée", false),
    ACTIVATEBOUCLIER("activation_monde_bouclier", "activation du monde des bouclier", false),
    ACTIVATEFEU("activation_monde_feu", "activation du monde de feu", false),
    ACTIVATEVOLER("activation_monde_voler", "activation du monde des vents", false),
    USERCANSTART("user_can_start", "le joueur peut sortir de la maison", false);

    /**
     *
     */
    private String action;

    /**
     *
     */
    private String savedId;

    /**
     *
     */
    private boolean state;

    /**
     * Private Class constructor
     * @param saveId
     * @param action
     * @param state
     */
    private Story(String saveId, String action, boolean state) {
        this.action = action;
        this.savedId = saveId;
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public String getSavedId() {
        return savedId;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void done() {
        MyStdOut.write(MyStdColor.MAGENTA, "<Story> " + this.action);
        this.state = true;
    }
}