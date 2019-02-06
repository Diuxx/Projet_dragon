package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import sys.EcranJeu;
import sys.Point;
import sys.Taille;

import java.util.ArrayList;
import java.util.List;

import static sys.EcranJeu.spriteSheet;

public class Hero extends Personnage {

    private List<PersonnageNonJoueur> lesPnj;
    private int currentXp;
    private int niveau;
    private int currentGold;

    // construct
    public Hero(String nom, float x, float y, int w, int h, int pointDeVie) {
        super(nom, x, y, w, h, pointDeVie);
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
        this.currentXp = 0;
        this.niveau = 1;
        this.currentGold = 100;
    }

    public Hero(String nom, Point positon, Taille taille, int life) {
        super(nom, positon.getX(), positon.getY(), taille.getLargeur(), taille.getLongeur(),
                life);
        this.chargerImage();
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
    }


    // seul le hero peut être contrôlé
    public void controle(GameContainer container) {
        if(container.getInput().isKeyDown(Input.KEY_UP)) {
            super.setDirection(0);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_LEFT)) {
            super.setDirection(1);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_DOWN)) {
            super.setDirection(2);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            super.setDirection(3);
            super.marcher();
        } else {
            super.stop();
        }
    }

    /**
     *
     * @param lesPnj
     */
    public void addPnj(List<PersonnageNonJoueur> lesPnj)
    {
        this.lesPnj = lesPnj;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isCollisionPnj(float x, float y) {
       for(PersonnageNonJoueur unPnj : lesPnj) {
           boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unPnj.getBoundingBox());
           if(collision) {
               unPnj.setParle();
               return true;
           }
       }
       return false;
    }

    @Override
    public boolean iscollisionLogic(TiledMap map, float x, float y) {
        int tileW = map.getTileWidth();
        int tileH = map.getTileHeight();

        int logicLayer = map.getLayerIndex("solide");
        Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        boolean collision = tile != null;
        if(collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision || isCollisionPnj( x, y);
    }

    /**
     *
     */
    private void chargerImage() {
        this.loadAnimation(spriteSheet, 6, 7,  11);
        this.loadAnimation(spriteSheet, 6, 7,  9);
        this.loadAnimation(spriteSheet, 6, 7,  8);
        this.loadAnimation(spriteSheet, 6, 7,  10);
        this.loadAnimation(spriteSheet, 6, 9,  11);
        this.loadAnimation(spriteSheet, 6, 9,  9);
        this.loadAnimation(spriteSheet, 6, 9,  8);
        this.loadAnimation(spriteSheet, 6, 9,  10);
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(int currentGold) {
        this.currentGold = currentGold;
    }
}
