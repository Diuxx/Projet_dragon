package org.lpdql.dragon.scenario;

import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;

/**
 *
 */
public enum Story {
    TUTOPARLEROLDMAN("speak_to_old_man", "parler au vieux monsieur", "Un vieux monsieur a besoin de vous parler..", false),
    TUTOSPAWNENNEMI("tuto_spawn_ennemi", "Apparition du premier ennemi", "Un ennemi est apparu tu dois le tuer..", false),
    TUTOFIRSTENNEMIWASKILLED("tuto_first_kill", "Le premier ennemi est mort", "Bien joué vous avez tué votre premier ennemi.. L'aventure peut commencer pour vous.\n" +
            "Fais quand même attention les bois ne sont plus très sûr de nos jours..", false),
    TUTOEND("fin_tuto", "Fin du tuto jeu", "Le tuto doit être terminé pour poursuivre..", false),
    OWNBASICSWORD("ownbasicsword", "Gagner l'épée basique", "Vous n'avez pas encore gagné l'épée basique..", false),

    FIRSTSTART("newGame", "le jeu commence", "", false),
    GAMESTART("debut_jeu", "le tuto est termine", "Le tuto doit être termine pour debuter le jeu..", false),

    LIRELETTRE("lire_lettre", "Lecture de la lettre", "Vous devez lire la lettre avant de sortir..", false),
    ACTIVATEEPEE("activation_monde_epe", "activation du monde de l'epée", "", false),
    ACTIVATEBOUCLIER("activation_monde_bouclier", "activation du monde des bouclier", "Le scenario épée doit être terminé pour poursuivre..", false),
    ACTIVATEFEU("activation_monde_feu", "activation du monde de feu", "Le scenario bouclier doit être terminé pour poursuivre..", false),
    ACTIVATEPOUVOIR("activation_monde_pouvoir", "activation du monde des pouvoir", "Le scenario Feu doit être terminé pour poursuivre..", false),
    TESTSTORYELEMENT("test", "unit test on it", "", true),


    ARTEPEE("Epee", "", "", false),
    ARTBOUCLIER("Bouclier", "", "", false),
    ARTFEU("Feu", "", "", false),
    ARTVOLER("Voler", "", "", false);


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