package org.lpdql.dragon.ecrans;

import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Bataille extends BasicGameState {

    public static final int ID = 22;

    private Image background;
    private Ennemi ennemi;
    private Hero hero;

    private Image ennemi_image;
    private Image hero_image;

    private Point positionEnnemi;
    private Point positionHero;

    private Graphics g = null;
    private GameContainer gc = null;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.background = new Image("data/bataille/Forest_background.png");
        this.ennemi = null;
        this.hero = null;
        this.hero_image = null;
        this.ennemi_image = null;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        this.ennemi = InterStateComm.getUnEnnemi();
        this.hero = InterStateComm.getLeHero();
        this.ennemi_image = this.ennemi.getEnnemiImages();
        this.hero_image = Ressources.spriteSheet.getSubImage(6, 10);

        this.positionEnnemi = new Point(container.getWidth() * 3 / 4, container.getHeight() / 2);
        this.positionHero = new Point(container.getWidth() * 1 / 4, container.getHeight() / 2);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        this.ennemi = null;
        this.hero = null;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.g = graphics;
        this.gc = gameContainer;

        background.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

        drawInformation();
        hero_image.draw(positionHero.getX(), positionHero.getY());
        ennemi_image.draw(positionEnnemi.getX(), positionEnnemi.getY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    private void drawInformation() {
        drawHeroBarHP();
        drawHeroNbHP();
    }

    private void drawHeroBarHP() {
        this.g.setColor(Color.white);
        this.g.drawRect(this.gc.getWidth() * 1 / 4,
                this.gc.getHeight() / 2 - this.hero_image.getHeight() / 2 - 30, 130, 20);
        this.g.setColor(Color.red);
        this.g.fillRect(this.gc.getWidth() * 1 / 4,
                this.gc.getHeight() / 2 - this.hero_image.getHeight() / 2 - 30,
                Math.max(0, (hero.getPointDeVieActuel() / hero.getPointDeVie())) * 130, 20);
    }

    private void drawHeroNbHP() {
        this.g.setColor(Color.white);
        this.g.drawString("" + (int) Math.max(0, (int) this.hero.getPointDeVieActuel()),
                this.gc.getWidth() * 1 / 4,
                this.gc.getHeight() / 2 - this.hero_image.getHeight() / 2 - 29);
    }
}
