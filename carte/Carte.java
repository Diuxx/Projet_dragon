package carte;

import jeu.Hero;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import sys.Point;

public class Carte {

    /**
     *
     */
    private TiledMap map;
    private String dernierMap;

    private String nomMap;

    // --
    public Carte(String fileMap) throws SlickException {
        this.map = new TiledMap(fileMap);
        this.nomMap = fileMap.split("/")[fileMap.split("/").length - 1].split("\\.")[0];
    }

    public TiledMap getMap() {
        return this.map;
    }

    public void afficher(int layer) {
        map.render(0, 0, layer); // we can do somes calcule here !
    }

    /**
     * do some refactoring on it!
     * @return
     */
    public Point getPositionPersonnage() {
        Point position = new Point(); // --
        int positionLayer = map.getLayerIndex("position");
        Image tile;
        Color color;
        for(int x = 0; x < this.map.getWidth(); x++) {
            for(int y = 0; y < this.map.getHeight(); y++)
            {
                tile = map.getTileImage(x, y, positionLayer);
                if(tile != null) {
                    color = tile.getColor((int) x % map.getTileWidth(), (int) y % map.getTileHeight());
                    // System.out.println("position -> (" + x + "; " + y + ") [ " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "]");

                    if(color.getBlue() == 201 && color.getGreen() == 174 && color.getRed() == 255) {
                        position.setX(x * map.getTileWidth());
                        position.setY(y * map.getTileHeight()); // --
                        return position;
                    }
                }
            }
        }
        return position;
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public Point getPositionPersonnage(int r, int g, int b) {
        Point position = new Point(); // --
        int positionLayer = map.getLayerIndex("position");
        Image tile;
        Color color;
        System.out.print("InterStateComm is searching : [ " + r + ", " + g + ", " + b + "]");
        //boolean found = false;

        for(int x = 0; x < this.map.getWidth(); x++) {
            for(int y = 0; y < this.map.getHeight(); y++)
            {
                tile = map.getTileImage(x, y, positionLayer);
                if(tile != null) {
                    color = tile.getColor((int) x % map.getTileWidth(), (int) y % map.getTileHeight());
                    if(color.getBlue() == b && color.getGreen() == g && color.getRed() == r) {
                        // System.out.println("position -> (" + x + "; " + y + ") [ " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "]");
                        System.out.println(" found ! (" + x + "; " + y + ")");


                        position.setX(x * map.getTileWidth());
                        position.setY(y * map.getTileHeight()); // --
                        return position;
                    }
                }
            }
        }
        System.out.println(" not found ! ");
        return position;
    }

    // --
    public String getFileName() {
        return this.nomMap;
    }

    /**
     * Change la Map acctuellement chargé
     * @param dest
     */
    public void changeMap(String dest) throws SlickException {
        this.nomMap = dest.split("/")[dest.split("/").length - 1].split("\\.")[0];
        this.map = new TiledMap(dest);
    }

    public float getHerox() {
        return 0f;
    }

    public float getHeroy() {
        return 0f;
    }

    /**
     * @return
     */
    public boolean isHeroCollidLeftLimit(GameContainer fenetre, Hero h) {
        return (h.getX() - (fenetre.getWidth() / 2)) < 0;
    }
    /**
     * @return
     */
    public boolean isHeroCollidRightLimit(GameContainer fenetre, Hero h) {
        int largeurDeCarte = this.map.getWidth() * this.map.getTileWidth();
        return ((largeurDeCarte - (fenetre.getWidth() / 2)) - h.getX()) < 0;
    }

    /**
     * @return
     */
    public boolean isHeroCollidTopLimit(GameContainer fenetre, Hero h) {
        return (h.getY() - (fenetre.getHeight() / 2)) < 0;
    }

    /**
     * @return
     */
    public boolean isHeroCollidBotLimit(GameContainer fenetre, Hero h) {
        int hauteurDeCarte = this.map.getHeight() * this.map.getTileHeight();
        return ((hauteurDeCarte - (fenetre.getHeight() / 2)) - h.getY()) < 0;
    }
}
