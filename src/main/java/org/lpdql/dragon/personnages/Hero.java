package org.lpdql.dragon.personnages;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.objets.Objet;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.scenario.Accomplish;
import org.lpdql.dragon.system.*;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lpdql.dragon.system.EcranJeu.lesMessages;

/**
 * Class managing a character controlled by the user.
 *
 * notice : the character knows where the objects, the enemy and the non-player characters are.
 * this class handles every possible collision on the map.
 */
public class Hero extends Personnage {

    /**
     * list des personnages non joueur sur la map sur lequel il se trouve
     */
    private List<PersonnageNonJoueur> lesPnj;

    /**
     * list of objects on the map on which it is located
     */
    private List<Objet> lesObjets;

    /**
     * list of enemies on the map on which it is located
     */
    private List<Ennemi> lesEnnemis;

    /**
     * this variable is the current instance experience
     */
    private int experience;

    /**
     * current level of the character
     */
    private int niveau;

    /**
     * current money of the character
     */
    private int currentGold;

    /**
     * a delete!
     */
    private boolean artEpee;
    private boolean artBouclier;
    private boolean artFeu;
    private boolean artVoler;

    /**
     * State of events related to history
     */
    private Accomplish accomplishement;

    /**
     * Tests values for the hero statistics
     */
    private static final int HEROLIFE = 1120;
    private static final float HEROSPEED = 0.1f;
    private static final int HEROLEVEL = 1;
    private static final int HEROGOLD = 500;

    private boolean nouvellePartie;

    /**
     * is it in the right place?
     */
    private Music music;
    private Music pausedMusic;
    private Sound sound;
    private boolean muted = false;

    /**
     * this variable manage the character's level up
     */
    private HashMap levelExperience;

    /**
     * Construtor of Hero without height and width
     * @param nom Name of the current instance
     * @param position abscissa and ordinate of the character on the map
     */
    public Hero(String nom, Point position)
    {
        super(nom, position, Taille.LARGE_SIZE, HEROLIFE,  HEROSPEED);
        this.lesPnj = new ArrayList<>();
        this.lesObjets = new ArrayList<>();
        this.experience = 0;
        this.niveau = HEROLEVEL;
        this.currentGold = HEROGOLD;

        // a delete
        this.artEpee = true;
        this.artBouclier = false;
        this.artFeu = false;
        this.artVoler = false;

        nouvellePartie = true;

        levelExperience = new HashMap<>();

        // Load accomplishement
        this.accomplishement = new Accomplish();

        // Load sprite tiles from sprite sheet
        this.chargerImage();
    }
    
    public Hero(String nom) {
    	super(nom);
    	this.niveau = HEROLEVEL;
    }
    
    /**
     * this method manages the control of the character by the user
     * Only an Hero can be controled.
     * @param container slick gameContainer
     *
     * @see GameContainer
     */
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
     * this method add a list of non-player characters
     * @param lesPnj list of PersonnageNonJoueur
     *
     * @see PersonnageNonJoueur
     */
    public void addPnj(List<PersonnageNonJoueur> lesPnj)
    {
        this.lesPnj = lesPnj;
    }

    /**
     * this method remove the existing non-player character list
     */
    public void removePnj() {
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
    }

    /**
     * this method add a list of ennemy characters
     * @param lesEnnemis list of Ennemi
     */
    public void addEnnemis(List<Ennemi> lesEnnemis) {
        this.lesEnnemis = lesEnnemis;
    }

    public void removeEnnemis() {
        this.lesEnnemis = new ArrayList<>();
    }

    public void addObjets(List<Objet> lesObjets) {
        this.lesObjets = lesObjets;
    }

    public void removeObjets() {
        this.lesObjets = new ArrayList<Objet>();
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
               if(unPnj.containStoryElement())
                   unPnj.storyDone();

               unPnj.setParle();
               return true;
           }
       }
       return false;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isCollisionObjets(float x, float y) {
        for(Objet unObjet : lesObjets) {
            if(unObjet instanceof ObjetMessage == false)
                continue;

            boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unObjet.getBoundingBox());
            if(collision) {
                if(unObjet.containStoryElement())
                    unObjet.storyDone();

                if(unObjet instanceof ObjetMessage) {
                    ((ObjetMessage) unObjet).setParle(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isCollisionEnnemi(float x, float y) {
        for(Ennemi unEnnemi : lesEnnemis) {
            if(unEnnemi.isMort())
                continue;

            boolean collision = new Rectangle(x - getCenterX(), y - getCenterY(), getWidth(), getHeight()).intersects(unEnnemi.getBoundingBox());
            if(collision) {
                // MyStdOut.write(MyStdColor.RED,"<" + this.getClass().getSimpleName() + "> to " + unEnnemi.getNom());
                unEnnemi.stop();

                if(!unEnnemi.isFriendly()) {
                    if(!unEnnemi.veutCombattre()) lesMessages.add(unEnnemi.parle());
                    unEnnemi.startCombat(); // request fight ?
                    unEnnemi.requestFight();
                }
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
        return collision || isCollisionPnj( x, y) || isCollisionObjets(x, y) || super.isDynamicCollision() || isCollisionEnnemi( x, y);
    }

    /**
     *
     */
    private void chargerImage() {
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  11);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  9);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  8);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  10);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  11);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  9);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  8);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  10);
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
        experience = experienceGagne;
    }

    public int getLevel() {
        return this.niveau;
    }

    public void setLevel(int levelGagne) {
        this.niveau = levelGagne;
    }

    public boolean getArtEpee() { return artEpee ; }

    public boolean getArtBouclier() { return artBouclier; }

    public boolean getArtFeu() {
        return artFeu;
    }

    public boolean getArtVoler() {
        return artVoler;
    }

    public boolean isArtEpee() {
        return artEpee;
    }

    public void setArtEpee(boolean artEpee) {
        this.artEpee = artEpee;
    }

    public boolean isArtBouclier() {
        return artBouclier;
    }

    public void setArtBouclier(boolean artBouclier) {
        this.artBouclier = artBouclier;
    }

    public boolean isArtFeu() {
        return artFeu;
    }

    public void setArtFeu(boolean artFeu) {
        this.artFeu = artFeu;
    }

    public boolean isArtVoler() {
        return artVoler;
    }

    public void setArtVoler(boolean artVoler) {
        this.artVoler = artVoler;
    }

    public void changerMusic(String musique) throws SlickException {
        music = new Music(musique);
        if (this.getMuted()==false) {
            music.loop();
        }
    }
    public void setPausedMusic() {
        pausedMusic = music;
    }
    public void playPausedMusic() {
        if(this.getMuted()== false)
            pausedMusic.loop();
        music = pausedMusic;
    }
    public boolean getMuted() {
        return muted;
    }

    public Accomplish getAccomplishement() {
        return this.accomplishement;
    }

    public void setAccomplishement(Accomplish accomplishement) {
        this.accomplishement = accomplishement;
    }

    /**
     * Update some elements to match the last game backup
     * @param savedData
     */
    public void setSavedData(Save savedData) {
        if(savedData.getSavedHero() == null)
            return;

        System.out.println("Performing loading on saved data!");

        Hero savedHero = savedData.getSavedHero();
        this.setNom(savedHero.getNom());
        this.setPointDeVie(savedHero.getPointDeVie());
        this.setPointDeVieActuel(savedHero.getPointDeVieActuel());
        super.setPosition(savedHero.getX(), savedHero.getY());
        this.setDirection(savedHero.getDirection());
        this.setExperience(savedHero.getExperience());
        this.setNiveau(savedHero.getNiveau());
        this.setCurrentGold(savedHero.getCurrentGold());


        this.setArtEpee(savedHero.getArtEpee());
        this.setArtBouclier(savedHero.getArtBouclier());
        this.setArtFeu(savedHero.getArtFeu());
        this.setArtVoler(savedHero.getArtVoler());

        this.accomplishement = savedHero.getAccomplishement();

        this.accomplishement.getLog();
    }
}
