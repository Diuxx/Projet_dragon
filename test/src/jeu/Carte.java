package jeu;

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
                    color = tile.getColor((int) x % map.getTileWidth(), (int) y % map.getTileHeight());
                    System.out.println("position du joueur : x = " + x + "; y = " + y);
                    System.out.println("color : " + color.getBlue() + " - " + color.getGreen() + " - " + color.getRed());
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

    public Point getPositionPersonnage(int r, int g, int b) {
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
                    color = tile.getColor((int) x % map.getTileWidth(), (int) y % map.getTileHeight());
                    System.out.println("position du joueur : x = " + x + "; y = " + y);
                    System.out.println("color : " + color.getBlue() + " - " + color.getGreen() + " - " + color.getRed());
                    if(color.getBlue() == b && color.getGreen() == g && color.getRed() == r) {
                        position.setX(x * map.getTileWidth());
                        position.setY(y * map.getTileHeight()); // --
                        return position;
                    }
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
