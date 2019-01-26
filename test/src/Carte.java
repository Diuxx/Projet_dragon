import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Carte {

    private TiledMap map;

    // --
    public Carte(String fileMap) throws SlickException  {
        this.map = new TiledMap(fileMap);
    }

    public TiledMap getMap() {
        return this.map;
    }

    public void afficher(int layer) {
        map.render(0, 0, layer);
    }

    public Point getPositionPersonnage() {
        Point position = new Point(); // --
        int positionLayer = map.getLayerIndex("position");
        Image tile;
        Color color;

        System.out.println(map.getWidth() + " - " + map.getHeight());
        for(int x = 0; x < this.map.getWidth(); x++) {
            for(int y = 0; y < this.map.getHeight(); y++)
            {
                tile = map.getTileImage(x, y, positionLayer);
                if(tile != null) {
                    color = tile.getColor(x, y);
                    /*color = tile.getColor(x, y);
                    System.out.println(x + " - " + y);
                    System.out.println("color : " + color.getBlue() + " - " + color.getGreen() + " - " + color.getRed());
                    if(color.getBlue() == 97 && color.getGreen() == 113 && color.getRed() == 117) {
                        position.setX(x);
                        position.setY(y); // --
                        return position;
                    }*/
                }
            }
        }
        return position;
    }

    public float getHerox() {
        return 0f;
    }

    public float getHeroy() {
        return 0f;
    }
}
