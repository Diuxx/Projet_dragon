package jeu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Personnage {

    private List<PersonnageNonJoueur> lesPnj;

    // construct
    public Hero(String nom, float x, float y, int w, int h, int pointDeVie) {
        super(nom, x, y, w, h, pointDeVie);
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
    }

    // seul le hero peut être contrôlé
    public void controle(int key) {
        // --
        switch (key) {
            case Input.KEY_UP:
                super.setDirection(0);
                super.marcher();
                break;
            case Input.KEY_LEFT:
                super.setDirection(1);
                super.marcher();
                break;
            case Input.KEY_DOWN:
                super.setDirection(2);
                super.marcher();
                break;
            case Input.KEY_RIGHT:
                super.setDirection(3);
                super.marcher();
                break;
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

    /*
     *
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
}
