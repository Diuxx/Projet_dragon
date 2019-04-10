package org.lpdql.dragon.monde;

import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ressources {

    public static SpriteSheet spriteSheet;
    public static SpriteSheet spriteSheet_goblin;
    public static SpriteSheet spriteSheet_Ennemis;
    public static SpriteSheet spriteSheet_PNJ;
    public static SpriteSheet spriteSheet_Dragon;
    public static SpriteSheet healSheet;

    public static SpriteSheet spriteSheet_vieilHomme;
    public static SpriteSheet spriteSheet_letter;
    private static boolean charger = false;


    public static Image fondMessage;
    public static Image fondMenu;

    public static void charger() throws SlickException {
        if(!charger) {
            // chargement des textures
            spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);
            spriteSheet_goblin = new SpriteSheet("data/goblin.png", 64, 64);
            spriteSheet_Ennemis = new SpriteSheet("data/Ennemis.png", 32, 32);
            spriteSheet_Dragon = new SpriteSheet("data/Dragon.png", 96, 96);
            healSheet = new SpriteSheet("data/heal.png", 16, 16);
            spriteSheet_PNJ = new SpriteSheet("data/PNJ.png", 32, 32);

            spriteSheet_vieilHomme = new SpriteSheet("data/sprites/vieux_sage32x32.png", 32, 32);
            spriteSheet_letter = new SpriteSheet("data/sprites/letter.png", 16, 16);

            fondMessage = new Image("data/menu/uimessage.png");
            fondMenu = new Image("data/menu/uimenu.png");
            charger = true;

            // chargement du personnage
            InterStateComm.setLeHero(new Hero("LPDQL", new Point(0, 0)));
        }
    }
}
