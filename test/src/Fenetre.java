import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Fenetre extends BasicGame {

    private GameContainer container;
    private TiledMap grassMap;

    private float x = 200, y = 300;
    private int direction = 0;
    private boolean moving = false;
    private Animation[] animations = new Animation[8];

    // --
    public Fenetre() {
        super("???");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        this.grassMap = new TiledMap("data/blank.tmx");
        SpriteSheet spriteSheet = new SpriteSheet("data/Tiny32.png", 32, 32);
        this.animations[0] = loadAnimation(spriteSheet, 6, 7,  8);
        this.animations[1] = loadAnimation(spriteSheet, 6, 7,  9);
        this.animations[2] = loadAnimation(spriteSheet, 6, 7,  10);
        this.animations[3] = loadAnimation(spriteSheet, 6, 7,  11);
        this.animations[4] = loadAnimation(spriteSheet, 6, 9,  8);
        this.animations[5] = loadAnimation(spriteSheet, 6, 9,  9);
        this.animations[6] = loadAnimation(spriteSheet, 6, 9,  10);
        this.animations[7] = loadAnimation(spriteSheet, 6, 9,  11);
    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.grassMap.render(0, 0);
        g.drawAnimation(animations[direction + (moving ? 4 : 0)], x, y);
        // hero.afficher(direction, moving, g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

    }

    // --
    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }
        return animation;
    }
}