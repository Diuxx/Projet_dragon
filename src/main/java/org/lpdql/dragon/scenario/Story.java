package org.lpdql.dragon.scenario;

import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;

/**
 *
 */
public enum Story {
    LIRELETTRE("lire_lettre", "Lecture de la lettre", "Vous devez lire la lettre avant de sortir..", false),
    TUTOEND("fin_tuto", "Fin du tuto jeu", "Le tuto doit être terminé pour poursuivre..", false),
    TUTOFIRSTKILL("tuto_first_kill", "Premier ennemi tué lors du tuto", "", false),
    ACTIVATEEPEE("activation_monde_epe", "activation du monde de l'epée", "", false),
    ACTIVATEBOUCLIER("activation_monde_bouclier", "activation du monde des bouclier", "Le scenario épe doit être terminé pour poursuivre..", false),
    ACTIVATEFEU("activation_monde_feu", "activation du monde de feu", "Le scenario bouclier doit être terminé pour poursuivre..", false),
    ACTIVATEPOUVOIR("activation_monde_pouvoir", "activation du monde des pouvoir", "Le scenario Feu doit être terminé pour poursuivre..", false),
    USERCANSTART("user_can_start", "le joueur peut sortir de la maison", "", false);

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
     *
     */
    private String message;

    /**
     * Private Class constructor
     * @param saveId
     * @param action
     * @param state
     */
    private Story(String saveId, String action, String message, boolean state) {
        this.action = action;
        this.savedId = saveId;
        this.state = state;
        this.message = message;
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

    public String getMessage() {
        return this.message;
    }
}