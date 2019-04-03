package org.lpdql.dragon.personnages;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.objets.Objet;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.sauvegarde.Save;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Hero extends Personnage {

    private List<PersonnageNonJoueur> lesPnj;
    private List<Objet> lesObjets;

    private List<Ennemi> lesEnnemis;
    private int experience;
    private int niveau;
    private int currentGold;
    
    private boolean lettre;
    private boolean artEpee;
    private boolean artBouclier;
    private boolean artFeu;
    private boolean artVoler;

    // hero information
    private static final int HEROLIFE = 1120;
    private static final float HEROSPEED = 0.1f;
    private static final int HEROLEVEL = 1;
    private static final int HEROGOLD = 500;

    private boolean nouvellePartie;

    // interstate ? instead of hero !
    private Music music;
    private Music pausedMusic;
    private Sound sound;
    private boolean muted = false;

    private HashMap levelExperience;

    /**
     * Constructeur de la class Hero (projet InterStateComm);
     * @param nom
     * @param positon
     */
    public Hero(String nom, Point positon) {
        super(nom, positon, Taille.LARGE_SIZE, HEROLIFE,  HEROSPEED);
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
        this.lesObjets = new ArrayList<Objet>();
        this.experience = 0;
        this.niveau = HEROLEVEL;
        this.currentGold = HEROGOLD;

        this.artEpee = true;
        this.artBouclier = false;
        this.artFeu = false;
        this.artVoler = false;

        nouvellePartie = true;
        // --
        levelExperience = new HashMap<>();
        this.chargerImage();
    }

    /**
     *
     * @param savedData
     */
    public void setSavedData(Save savedData) {
        if(savedData.getSavedHero() == null)
            return;

        System.out.println("Somes Data is loading !");

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


    public void removePnj() {
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
    }
    /**
     *
     * @param lesEnnemis
     */
    public void addEnnemis(List<Ennemi> lesEnnemis) {
        this.lesEnnemis = lesEnnemis;
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
                if(unObjet instanceof ObjetMessage) {
                    ((ObjetMessage) unObjet).setParle(true);
                    if(unObjet.getNom() == "Lettre") {
                    	this.setLettre();
                    	this.lesPnj.get(0).addDialogue("Tu devrais commencer par te rendre au pays de l'Epee#Leur trésor te sera utile pour obtenir les 3 autres.");
                    }
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
            boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unEnnemi.getBoundingBox());
            if(collision) {
                // --
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
        return collision || isCollisionPnj( x, y) || isCollisionObjets(x, y) || super.isDynamicCollision()/* || isCollisionEnnemi( x, y)*/;
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
    
    public void setLettre() {
    	this.lettre = true;
    	System.out.println("Lettre trouvée");
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
    public boolean isLettre() {
    	return this.lettre;
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
}
