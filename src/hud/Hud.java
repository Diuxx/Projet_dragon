package hud;

//import java.awt.*;

import jeu.Hero;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {
    /**
     *
     */
    private static final int P_BAR_X = 10;
    private static final int P_BAR_Y = 10;
    private static final int BAR_X = P_BAR_X + 47;
    private static final int BAR_Y = P_BAR_Y + 19;
    private static final int BAR_W = 130;
    private static final int BAR_H = 12;

    /**
     *
     */
    private static final Color LIFE_COLOR = new Color(255, 0, 0);
    private static final Color MANA_COLOR = new Color(0, 0, 255);
    private static final Color XP_COLOR_FOND = new Color(255, 255, 224); 
    private static final Color XP_COLOR = new Color(100, 149, 237);
    
    
    /**
     *
     */
    private Image playerbars;
	private Image epee;
	private Image bouclier;
	private Image feu;
	private Image air;

    /**
     *
     */
    public Hud() {
        // --
    }

    /**
     *
     * @throws SlickException
     */
    public void init() throws SlickException {
        this.playerbars = new Image("data/playerBar.png");
        this.epee = new Image("data/art_combat/epee.png");
        this.bouclier = new Image("data/art_combat/bouclier.png");
        this.feu = new Image("data/art_combat/feu.png");
        this.air = new Image("data/art_combat/air.png");
    }

    /**
     *
     * @param g
     */
    public void render(Graphics g, Hero hero) {
    	
    	
        g.resetTransform();
        //---- Nom du Joeur
        Font fontG = g.getFont();
        String nomJoeur = hero.getNom();
        fontG.drawString(60,8,nomJoeur,Color.black);
        
        
        //-----  Vie ---- 
        g.setColor(LIFE_COLOR);
        g.fillRect(BAR_X, BAR_Y , ((hero.getPointDeVieActuel() / hero.getPointDeVie()) * BAR_W), BAR_H);
        g.setColor(XP_COLOR_FOND);
        g.fillRect(40, 64, 190, 13);
        g.fillRoundRect(19, 58, 23, 23, 30);
    
        g.setColor(XP_COLOR);
        g.fillRect(40, 64, 60, 13);
        
        //-- Arte de combat
        if(hero.getArtEpee()) {
        	g.setColor(Color.white);
        	g.fillRoundRect(205, 20, 28, 28, 30);
        	g.drawImage(this.epee, 210, 25);
        }else {
        	g.setColor(Color.black);
        	g.fillRoundRect(205, 20, 28, 28, 30);
        }
        
        if(hero.getArtBouclier()) {
        	g.setColor(Color.white);
    		g.fillRoundRect(241, 22, 28, 28, 30);
    		g.drawImage(this.bouclier, 246, 25);
	    }else {
	    	g.setColor(Color.black);
	    	g.fillRoundRect(241, 22, 28, 28, 30);
	    }
        
        if(hero.getArtFeu()) {
        	g.setColor(Color.white);
    		g.fillRoundRect(277, 21, 28, 28, 30);
    		g.drawImage(this.feu, 282, 25);
	    }else {
	    	g.setColor(Color.black);
	    	g.fillRoundRect(277, 21, 28, 28, 30);
	    }
        
        if(hero.getArtVoler()) {
        	g.setColor(Color.white);
    		g.fillRoundRect(310, 21, 28, 28, 30);
    		g.drawImage(this.air, 317, 25);
	    }else {
	    	g.setColor(Color.black);
	    	g.fillRoundRect(310, 21, 28, 28, 30);
	    }
        
        //------- Experience et Level
        String experience = Integer.toString(hero.getExperience()); 
        String nbrExperienceAtteindreNiveauSuivant = Integer.toString(15);
        String experienceAfficher = experience + "/" +nbrExperienceAtteindreNiveauSuivant;
        
        String level = Integer.toString(hero.getLevel());
        fontG.drawString(115,62,experienceAfficher,Color.black);
        fontG.drawString(24,61,level,Color.black);
        g.drawImage(this.playerbars, P_BAR_X, P_BAR_Y);
    }
}
