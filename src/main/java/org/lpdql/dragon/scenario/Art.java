package org.lpdql.dragon.scenario;


/**
 * class Art
 *
 * @author: Diuxx
 */
public class Art {

    /**
     *
     */
    public static enum ENUM {
        EPEE("Epee"),
        BOUCLIER("Bouclier"),
        FEU("Feu"),
        VOLER("Voler");

        private String artString;
        private ENUM(String artString) {
            this.artString = artString;
        }
        public String toString() {
            return this.artString;
        }
    }

    /**
     * Name of the current art.
     */
    private String name;

    /**
     * lets know if this art it's done
     */
    private Boolean done;

    /**
     * that a class Constructor.
     */
    public Art(String artName, boolean done) {
        this.name = artName;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
