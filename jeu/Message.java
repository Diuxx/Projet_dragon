package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import sys.Camera;

import java.util.ArrayList;

public class Message {

    private ArrayList<String> text;
    private int posisition;
    private final int taille = 80;

    // --
    private int MESSAGE_MAXchar = 80;
    private int MESSAGE_MAXLINE = 3;
    private int MESSAGE_ESPACE = 5;

    Rectangle rect;
    // --
    public Message() {
        // --
        this.rect = new Rectangle(0, 0, 0, 0);
        this.text = new ArrayList<String>();
    }

    public void add(String text) {
        int ligne = 0;
        // ajout des chaine
        String[] listDeChaine = text.split("#");
        for(String chaine : listDeChaine)
        {
            this.text.add(chaine + " (appuyez sur [w] pour continuer...)");
        }
        this.posisition = 0;
    }

    public void add(Message text) {
        this.text = text.getText();
    }

    // afficher un message
    public boolean afficher(Graphics g, GameContainer c, Camera camera) {
        if(this.containMessage()) {
            if(posisition != 0) {
                this.text = new ArrayList<String>();
                this.posisition = 0;
            }
            return false; // --
        }
        int i = 0;
        g.setColor(Color.white);
        rect.setBounds((int) camera.getX() - (c.getWidth() / 2) + MESSAGE_ESPACE,
                       (int) camera.getY() + (c.getHeight() / 2) - this.taille + MESSAGE_ESPACE,
                    c.getWidth() - (MESSAGE_ESPACE + MESSAGE_ESPACE), this.taille - 10);
        g.fill(rect);

        g.setColor(Color.black);
        for(String uneLigne : this.text.get(this.posisition).split("/n"))
        {
            g.drawString(uneLigne, rect.getX() + 5, rect.getY() + 5 + (15 * (i++)));
        }
        return true;
    }

    public boolean containMessage() {
        return (this.posisition >= this.text.size());
    }

    /**
     *
     * @return
     */
    public boolean next() {
        if(posisition <= this.text.size()) {
            posisition = posisition + 1;
            return true;
        }
        return false;
    }

    public ArrayList<String> getText() {
        return this.text;
    }
}
