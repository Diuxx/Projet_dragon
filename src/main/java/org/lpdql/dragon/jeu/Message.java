package org.lpdql.dragon.jeu;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.system.Camera;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

/**
 *
 */
public class Message {

    /**
     * List contenant les messages à afficher
     */
    private ArrayList<String> text;

    /**
     * Position du message (page)
     */
    private int posisition;

    /**
     * Hauteur de la fenetre d'affichage des messages
     */
    private final int taille = 80;

    /**
     * Espace entre deux ligne de text.
     */
    private final int MESSAGE_ESPACE = 5;

    /**
     * Rectangle d'affichage des messages
     */
    private Rectangle rect;

    /**
     * Constructor of class message
     */
    public Message() {
        this.posisition = 0;
        this.rect = new Rectangle(0, 0, 0, 0);
        this.text = new ArrayList<String>();// uimessage
    }

    /**
     * constructor in which we can directly add a message
     * @param message the text that needs to be added
     */
    public Message(String message) {
        this();
        this.add(message);
    }

    /**
     * this method adds text to the text Array to display
     * @param text the text that needs to be added
     */
    public void add(String text) {
        int ligne = 0;
        String[] listDeChaine = text.split("#");
        for(String chaine : listDeChaine)
        {
            this.text.add(chaine);// + ((chaine.length() > 0) ? " ":"") + "(appuyez sur [w] pour continuer...)");
        }
        this.posisition = 0;
    }

    /**
     * add Message in this instance of Message
     * @param text Message instance
     *
     * @see Message
     */
    public void add(Message text) {
        this.text = text.getText();
    }

    /**
     * display messages on the graph
     * @param g Graphics where the message will be displayed
     * @param c slick gameContainer
     * @param camera the dragon game variable
     * @return {@code true} if the message are correctly displayed
     *
     * @see Camera
     */
    public boolean afficher(Graphics g, GameContainer c, Camera camera) {
        if(this.containMessage()) {
            if(posisition != 0) {
                this.text = new ArrayList<String>();
                this.posisition = 0;
            }
            return false; // --
        }
        int i = 0;

        int x = (int) camera.getX() - (c.getWidth() / 2) + MESSAGE_ESPACE;
        int y = (int) camera.getY() + (c.getHeight() / 2) - this.taille + MESSAGE_ESPACE;
        int w = c.getWidth() - (MESSAGE_ESPACE + MESSAGE_ESPACE);
        int h = this.taille - 10;
        rect.setBounds(x, y, w, h);

        if(Ressources.fondMessage != null)
        {
            Ressources.fondMessage.draw(x, y);
        } else {
            g.setColor(Color.orange);
            g.fill(rect);
        }

        g.setColor(Color.black);
        for(String uneLigne : this.text.get(this.posisition).split("/n"))
        {
            g.drawString(uneLigne, rect.getX() + 8, rect.getY() + 5 + (15 * (i++)));
        }
        return true;
    }

    /**
     * this function return {@code true} if text contain no more messages.
     * @return {@code true} if all messages have been displayed
     */
    public boolean containMessage() {
        return (this.posisition >= this.text.size());
    }

    /**
     * this function goes to the next message
     * @return {@code true} if the text Array still contains messages
     */
    public boolean next() {
        if(posisition <= this.text.size()) {
            posisition = posisition + 1;
            return true;
        }
        return false;
    }

    /**
     * return the message Array
     * @return the message Array
     */
    public ArrayList<String> getText() {
        return this.text;
    }


    /***
     * this function returns the number of messages in the Array
     * @return returns the number of messages in the Array
     */
    public int getTextSize() {
        return this.text.size();
    }

    public String getText(int position) {
        if(position >= this.text.size())
            throw new IndexOutOfBoundsException("no more text in the Message array");

        return this.text.get(position);
    }

    /**
     * @return Postion of the message currently displayed
     */
    public int getPosisition() {
        return posisition;
    }
}
