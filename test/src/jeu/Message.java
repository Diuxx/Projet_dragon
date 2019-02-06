package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

public class Message {

    private ArrayList<String> text;
    private int posisition;
    private final int taille = 65;

    // --
    private int MAXchar = 80;
    private int maxLine = 3;

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
            for(String uneLigne : chaine.split("/n"))
            {
                for(int i=0; i< (uneLigne.length() % MAXchar); i++)
                {
                    if(uneLigne.length() > MAXchar) {

                        try {
                            chaine = chaine.replace(uneLigne.substring(MAXchar * i, MAXchar * (i+1)), uneLigne.substring(MAXchar * i, MAXchar * (i+1)) + "/n");

                            System.out.println("une ligne a été remplacé : " + chaine);
                            System.out.println(uneLigne.substring(MAXchar * i, MAXchar * (i+1)));
                        } catch (StringIndexOutOfBoundsException e) {
                            // --
                        }
                    }
                }
            }
            this.text.add(chaine + " (appuyez sur [w] pour continuer...)");
        }
        this.posisition = 0;
    }

    public void add(Message text) {
        this.text = text.getText();
    }

    // afficher un message
    public boolean afficher(Graphics g, GameContainer c, float x, float y, int espace) {
        int i = 0;
        if(this.posisition >= this.text.size()) {
            if(posisition != 0) {
                this.text = new ArrayList<String>();
                this.posisition = 0;
            }
            return false; // --
        }

        g.setColor(Color.white);
        rect.setBounds((int) x - (c.getWidth() / 2) + espace,
                       (int) y + (c.getHeight() / 2) - this.taille + espace,
                    c.getWidth() - (espace + espace), this.taille - 10);
        g.fill(rect);

        g.setColor(Color.black);
        for(String uneLigne : this.text.get(this.posisition).split("/n"))
        {
            g.drawString(uneLigne, rect.getX() + 5, rect.getY() + 5 + (15 * (i++)));
        }
        //g.drawString(this.text.get(this.posisition), rect.getX() + 5, rect.getY() + 5);
        return true;
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
