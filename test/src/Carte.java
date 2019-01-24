import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Carte {

    private TiledMap map;

    // --
    public Carte(String fileMap) throws SlickException  {
        this.map = new TiledMap("data/blank.tmx");
    }

    public TiledMap getMap() {
        return this.map;
    }

    public void afficher() {
        map.render(0, 0);
    }

    public float getHerox() {
        return 0f;
    }

    public float getHeroy() {
        return 0f;
    }
}
