package Objets;

import sys.Point;
import sys.Taille;

public class ObjetMessage extends Objet {

    /**
     * Message de l'objet
     */
    private String message;

    /**
     detecte si l'objet parle */
    private boolean parle;

    /**
     * Construceur
     *
     * @param nom
     * @param p
     * @param taille */
    public ObjetMessage(String nom, Point p, Taille taille) {
        super(nom, p, taille);
        this.message = "no message set..";
    }

    /**
     * add somes Messages
     * @param unMessage
     */
    public void setMessage(String unMessage) {
        this.message = unMessage;
    }
}
