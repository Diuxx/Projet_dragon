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
     * this variable points to a Story Element
     * a story can be activated or not
     * @see StoryElement
     */
    private Story storyElement;

    /**
     * this variable allows the character to display a message
     * @see Message
     */
    private Message dialogue;

    /**
     * detects if the character is speaking
     */
    private boolean parle;

    /**
     * Default constructor of Pnj class.
     *
     * @param nom Name of the current instance
     * @param x abscissa of the character on the map
     * @param y ordinate of the character on the map
     * @param w character width on the map
     * @param h height of the character on the map
     */
    public PersonnageNonJoueur(String nom, float x, float y, int w, int h) {
        super(nom, x, y, w, h, 0, 0.0f, 0);
        this.parle = false;
    }

    /**
     * Construtor overload of PNJ without x and y
     * @param nom Name of the current instance
     * @param pos abscissa and ordinate of the character on the map
     * @param w character width on the map
     * @param h height of the character on the map
     */
    public PersonnageNonJoueur(String nom, Point pos, int w, int h) {
        super(nom, pos, w, h, 0, 0.0f, 0);
        this.parle = false;
    }

    /**
     * add the message to display after interaction
     */
    public void addDialogue(String text) {
        this.dialogue = new Message();
        this.dialogue.add(text);
    }

    /**
     * this method puts an end to an element of the story
     * @see Story
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
     *
     * @see Story
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
     * return current storyElement stored in this instance of class
     * @return return current storyElement stored in this instance of class
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
