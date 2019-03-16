package carte;

import personnages.Hero;
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
    public Point getCheckPoint() {
        System.out.println(map.getObjectCount(0));
        for(int i=0; i<map.getObjectCount(0); i++)
        {
            String objType = map.getObjectType(0, i);
            if(objType.equals("check-point")) {
                // System.out.println(this.map.getObjectX(0, i) + " : " + i);
                // System.out.println(this.map.getObjectY(0, i) + " : " + i);
                return new Point(
                        this.map.getObjectX(0, i),
                        this.map.getObjectY(0, i)
                );
            }
        }
        return new Point(0, 0);
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
