package org.lpdql.dragon.monde;

import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.sound.JMusic;
import org.lpdql.dragon.sound.JSound;
import org.lpdql.dragon.sound.MyJSound;
import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ressources {

    public static SpriteSheet spriteSheet, spriteSheet_hFight, spriteSheet_swordHit, spriteSheet_shield;
    public static SpriteSheet spriteSheet_goblin;
    public static SpriteSheet spriteSheet_Ennemis;
    public static SpriteSheet spriteSheet_PNJ;
    public static SpriteSheet spriteSheet_Dragon;
    public static SpriteSheet healSheet;

    public static SpriteSheet spriteSheet_vieilHomme;
    public static SpriteSheet spriteSheet_letter;

    public static SpriteSheet spriteSheet_important, sheetHero;

    private static boolean charger = false;
    private static boolean chargerSound = false;

    public static MyJSound sounds;

    public static Image fondMessage;
    public static Image fondMenu;

    public static void charger() throws SlickException {
        if(!charger) {
            // chargement des textures
            sheetHero = new SpriteSheet("data/sprites/sprite.png", 32, 40);
            //sheetHero = new SpriteSheet("data/sprites/sprite.png", 18, 28);

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

            spriteSheet_important = new SpriteSheet("data/sprites/important-blue.png", 16, 24);

            spriteSheet_hFight = new SpriteSheet("data/sprites/HeroFight.png", 95, 80);
            spriteSheet_swordHit = new SpriteSheet("data/bataille/swordHit.png", 59, 68);

            spriteSheet_shield = new SpriteSheet("data/bataille/bouclier.png", 128, 128);

            charger = true;

            // chargement du personnage
            // InterStateComm.setLeHero(new Hero("LPDQL", new Point(0, 0)));
        }
    }


    public static void loadSound() throws SlickException {
        if(chargerSound)
            return;

        sounds = new MyJSound();
        sounds.setVolumeMusic(15f);
        sounds.setVolumeEffect(7f);
        // sounds.addSound("choc", "data/sound/chocchoc.ogg", 1);
        sounds.addSound(new JMusic("menu", "data/sound/menu-soundtrack.ogg"));
        sounds.addSound(new JMusic("ambiant", "data/sound/ambiant.ogg"));
        sounds.addSound(new JSound("run", "data/sound/run.wav"));
        sounds.addSound(new JSound("run-inner", "data/sound/run_inner.ogg"));
        sounds.addSound(new JSound("test", "data/sound/test.wav"));
        sounds.addSound(new JSound("attaque", "data/sound/swing.wav"));
        sounds.addSound(new JSound("victory", "data/sound/victory.ogg"));
        sounds.addSound(new JSound("beep", "data/sound/beep.wav"));
        sounds.addSound(new JSound("armure", "data/sound/armure.ogg"));

        sounds.addSound(new JSound("sword", "data/sound/sword.wav"));
        sounds.addSound(new JSound("leave", "data/sound/leave.wav"));
        sounds.addSound(new JSound("critical", "data/sound/critical.ogg"));
        sounds.addSound(new JSound("critical2", "data/sound/critical2.ogg"));

        sounds.addSound(new JMusic("battle", "data/sound/battle.ogg"));

        MyStdOut.write(MyStdColor.YELLOW, "<Ressources> Sounds loaded");
        chargerSound = true;
    }

}
