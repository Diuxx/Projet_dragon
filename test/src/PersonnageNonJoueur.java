import java.util.ArrayList;
import java.util.List;

/**
 * class PersonnageNonJoueur
 *
 * @author: Diuxx
 */
public class PersonnageNonJoueur extends Personnage {

    // dialogue entre le personnage et le pnj
    private Message dialogue;

    // detecte si le personnage parle
    private boolean parle;

    /**
     * Class constructor
     */
    public PersonnageNonJoueur(String nom, float x, float y) {
        super(nom, x, y, 0);
        this.parle = false;
    }

    /**
     *
     * @param nom
     * @param pos
     */
    public PersonnageNonJoueur(String nom, Point pos) {
        super(nom, pos.getX(), pos.getY(), 0);
        this.parle = false;
    }

    /*
     *
     */
    public void addDialogue(String text) {
        this.dialogue = new Message();
        this.dialogue.add(text);
    }

    public Message getDialogue() {
        return this.dialogue;
    }

    public boolean isParle() {
        return parle;
    }

    public void setParle() {
        this.parle = true;
    }

    public void arreteDeParler() {
        this.parle = false;
    }

}
