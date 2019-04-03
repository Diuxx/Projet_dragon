package org.lpdql.dragon.personnages;

import org.lpdql.dragon.interfaces.StoryElement;
import org.lpdql.dragon.jeu.Message;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.system.Point;

/**
 * class personnages.PersonnageNonJoueur
 *
 * @author: Diuxx
 */
public class PersonnageNonJoueur extends Personnage implements StoryElement {

    /**
     * That a pointer to a Story Element
     */
    private Story storyElement;

    /**
     * Dialogue entre le personnage et le pnj
     */
    private Message dialogue;

    /**
     * detecte si le personnage parle
     */
    private boolean parle;

    /**
     * Class constructor
     */
    public PersonnageNonJoueur(String nom, float x, float y, int w, int h) {
        super(nom, x, y, w, h, 0, 0.0f);
        this.parle = false;
    }

    /**
     *
     * @param nom
     * @param pos
     */
    public PersonnageNonJoueur(String nom, Point pos, int w, int h) {
        super(nom, pos, w, h, 0, 0.0f);
        this.parle = false;
    }

    /**
     *
     */
    public void addDialogue(String text) {
        this.dialogue = new Message();
        this.dialogue.add(text);
    }

    /**
     * This class ends a story element if it exist.
     */
    @Override
    public void storyDone() {
        // --
        if(this.storyElement == null)
            return;

        this.storyElement.done();
    }

    /**
     * fill the pointer if needed
     * @param element
     */
    @Override
    public void setStoryElement(Story element) {
        this.storyElement = element;
    }

    /**
     * return {@code true} if storyElement is not Empty
     * @return
     */
    @Override
    public boolean containStoryElement() {
        return (storyElement != null);
    }

    /**
     *
     * @return
     */
    @Override
    public Story getStoryElement() {
        return null;
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
