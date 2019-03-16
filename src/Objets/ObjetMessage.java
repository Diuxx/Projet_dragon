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
    public ObjetMessage(String nom, Point p, Taille taille, int positionSurMap) {
        super(nom, p, taille, positionSurMap);
        this.message = "no message set..";
    }

    /**
     * add somes Messages
     * @param unMessage
     */
    public void setMessage(String unMessage) {
        this.message = unMessage;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    public boolean isParle() {
        return parle;
    }

    public void setParle(boolean parle) {
        this.parle = parle;
    }
}
