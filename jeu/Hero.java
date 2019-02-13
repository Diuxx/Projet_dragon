package jeu;

import Bataille.Bataille;

//import org.junit.experimental.theories.Theories;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import sys.EcranJeu;
import sys.InterStateComm;
import sys.Point;
import sys.Taille;

import java.util.ArrayList;
import java.util.List;

import static sys.EcranJeu.spriteSheet;

public class Hero extends Personnage {

    // commentaire nico !
    // Ma nouvelle fonctionnalité de nico

    private List<PersonnageNonJoueur> lesPnj;
    private List<Ennemi> lesEnnemis;
    private int currentXp;
    private int niveau;
    private int currentGold;

    // hero information
    private static final float HEROLIFE = 1120;
    private static final float HEROSPEED = 0.1f;
    private static final int HEROLEVEL = 1;
    private static final int HEROGOLD = 500;
	private int experience = 5;
	private int level = 5;
	private boolean artEpee;
	private boolean artBouclier;
	private boolean artFeu;
	private boolean artVoler;

    /**
     * Constructeur de la class Hero (projet Dragon);
     * @param nom
     * @param positon
     */
    public Hero(String nom, Point positon) {
        super(nom, positon, Taille.LARGE_SIZE, HEROLIFE,  HEROSPEED);
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
        this.lesEnnemis = new ArrayList<Ennemi>();
        this.currentXp = 0;
        this.niveau = HEROLEVEL;
        this.currentGold = HEROGOLD;
        // --
        this.chargerImage();
        this.artEpee = true;
        this.artBouclier=true;
        this.artFeu = false;
        this.artVoler = false;
    }

    public Hero(String save) {

        super("?", 0, 0, 0, 0, 0,  HEROSPEED);
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
     * @param lesEnnemis
     */
    public void addEnnemis(List<Ennemi> lesEnnemis) {
        this.lesEnnemis = lesEnnemis;
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

    private boolean isCollisionEnnemi(float x, float y) {
        for(Ennemi unEnnemi : lesEnnemis) {
            boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unEnnemi.getBoundingBox());
            if(collision && !unEnnemi.isMort()) {
                // --
                System.out.println("Fight");
                InterStateComm.setBattleEnnemy(unEnnemi);
                EcranJeu.gameState.enterState(Bataille.ID);

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
        return collision || isCollisionPnj( x, y) || isCollisionEnnemi( x, y);
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

	public int getExperience() {
		return this.experience;
	}
	
	public void setExperience(int experienceGagne) {
		experience += experienceGagne;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int levelGagne) {
		this.level += levelGagne;
	}

	public boolean getArtEpee() {
		
		return artEpee ;
	}

	public boolean getArtBouclier() {
		
		return artBouclier;
	}

	public boolean getArtFeu() {
		return artFeu;
	}

	public boolean getArtVoler() {
		return artVoler;
	}

}
