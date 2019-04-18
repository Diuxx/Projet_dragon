package org.lpdql.dragon.sauvegarde;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.scenario.Story;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.MyStdColor;
import org.lpdql.dragon.system.MyStdOut;
import org.lpdql.dragon.system.Point;

import java.io.*;

/**
 * class Save
 *
 * @author: Diuxx
 */
public class Save {
    /**
     * Nom du fichier dans lequel la sauvegarde sera enregistré
     */
    private static final String savedFileName = "dragonSave.json";

    /**
     * Données sauvegardé du hero
     */
    private Hero savedHero;

    /**
     * Map par défault
     */
    private String defaultMapName = "data/maison.tmx";

    /**
     * Class constructor
     */
    public Save() {
        this.savedHero = null;
    }

    /**
     * Class constructor
     */
    public Save(Hero savedHero, String savedMap) {
        this.savedHero = savedHero;
        this.defaultMapName = "data/" + savedMap + ".tmx";
    }

    public static boolean isSaveExist() {
        return (new File(Save.savedFileName)).exists();
    }


    /**
     * Detect if a save file exist in current save dir.
     * @return en new instance of save with all data saved.
     */
    public static Save detectSavedData() {
        File file = new File(Save.savedFileName);
        if(!file.exists()) {
            for(Story s : Story.values()) {
                s.setState(false); // reset des elements de l'histoire
                if(s.getSavedId().equals("test"))
                    s.setState(true);
            }
            return new Save();
        }


        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new FileReader(file), JsonObject.class);

            JsonObject mapData = jsonObject.getAsJsonObject("mapData");
            JsonObject playerData = jsonObject.getAsJsonObject("playerData");
            JsonObject accomplishement = jsonObject.getAsJsonObject("accomplishement");
            JsonObject difficulte = jsonObject.getAsJsonObject("difficulte");

            // get all data
            String mapName = mapData.get("map").getAsString();

            String playerName = playerData.get("name").getAsString();
            float playerLifePoint = playerData.get("lifePoint").getAsInt();
            float playerCurrentLifePoint = playerData.get("currentLifePoint").getAsFloat();
            float playerx = playerData.get("x").getAsFloat();
            float playery = playerData.get("y").getAsFloat();
            int playerDirection = playerData.get("direction").getAsInt();
            int playerExperience = playerData.get("experience").getAsInt();
            int playerLevel = playerData.get("level").getAsInt();
            int playerGold = playerData.get("gold").getAsInt();

            Hero savedHero = new Hero("savedName", new Point(0, 0));
            savedHero.setNom(playerName);
            savedHero.setPointDeVie(playerLifePoint);
            savedHero.setPointDeVieActuel(playerCurrentLifePoint);
            savedHero.setX(playerx);
            savedHero.setY(playery);
            savedHero.setDirection(playerDirection);
            savedHero.setExperience(playerExperience);
            savedHero.setNiveau(playerLevel);
            savedHero.setCurrentGold(playerGold);

            for(Story s : Story.values()) {
                if(accomplishement.get(s.getSavedId()).getAsBoolean())
                    s.done();
            }

            InterStateComm.setNiveauDuJeu(difficulte.get("niveau").getAsInt());

            return new Save(savedHero, mapName);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (NullPointerException p) {
            String nameofCurrMethod = new Throwable()
                    .getStackTrace()[0]
                    .getMethodName();

            System.err.println("<" + nameofCurrMethod + "> : saved data has been corrupted");
        }
        return new Save();
    }

    /**
     *
     */
    public String getCarteName() {
        return this.defaultMapName;
    }

    /**
     *
     * @return
     */
    public Hero getSavedHero()
    {
        return this.savedHero;
    }

    /**
     * this function manages the backup of game data
     * @param hero current hero instance
     * @param currentMapName the map on which the hero is
     * @return {@code true} if everything went well {@code false} otherwise
     *
     * @see Hero
     */
    public boolean save(Hero hero, String currentMapName) {
        Save.deleteSave();

        JsonObject save = new JsonObject();
        save.addProperty("gameName", "DragonProject");

        JsonObject mapData = new JsonObject();
        mapData.addProperty("map", currentMapName);
        save.add("mapData", mapData);

        JsonObject playerData = new JsonObject();
        playerData.addProperty("name", hero.getNom());
        playerData.addProperty("lifePoint", hero.getPointDeVie());
        playerData.addProperty("currentLifePoint", hero.getPointDeVieActuel());
        playerData.addProperty("x", hero.getX());
        playerData.addProperty("y", hero.getY());
        playerData.addProperty("direction", hero.getDirection());
        playerData.addProperty("experience", hero.getExperience());
        playerData.addProperty("level", hero.getNiveau());
        playerData.addProperty("gold", hero.getCurrentGold());
        save.add("playerData", playerData);

        JsonObject accomplishement = new JsonObject();
        for(Story s : Story.values()) {
            // sauvegarde
            accomplishement.addProperty(s.getSavedId(), s.getState());
        }
        save.add("accomplishement", accomplishement);

        JsonObject difficulte = new JsonObject();
        difficulte.addProperty("niveau", InterStateComm.getNiveauDuJeu());
        save.add("difficulte", difficulte);

        return this.writeSave(save);
    }

    /**
     *
     */
    public static void newGame() {
        Save.deleteSave();
    }

    /**
     * This function write saved data in savedFile
     * @param jsonObject
     * @return {@code true} if everything went well {@code false} otherwise
     *
     * @see JsonObject
     */
    private boolean writeSave(JsonObject jsonObject) {
        boolean status = true;
        try {
            Writer writer = new FileWriter(Save.savedFileName);
            writer.write(cleanJsonString(jsonObject.toString()));
            writer.close();
            // message d'execution
            System.out.println("<Save> game successfully saved as : " + Save.savedFileName);
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        } finally {
            // --
        }
        return status;
    }

    /**
     * delete current saved game if exist
     * @return @return {@code true} if everything went well {@code false} otherwise
     */
    public static boolean deleteSave() {
        File file = new File(Save.savedFileName);
        if(file.exists()) {
            file.delete();
        }
        return file.exists();
    }

    private long timer = 0l;

    public boolean autoSave(long delay, String currentMapName) {
        if(timer == 0l)
            timer = System.currentTimeMillis();

        if(System.currentTimeMillis() - timer >= delay) {
            this.save(InterStateComm.getLeHero(), currentMapName);
            MyStdOut.write(MyStdColor.RED, "<Save> game auto saved");

            timer = System.currentTimeMillis();
        }
        return true;
    }

    /**
     * Ajoute les indentations / sauts de lignes à un string contenant du json.
     * @param json
     * @return formated json
     */
    public static String cleanJsonString(String json) {
        String cleanJson = "";
        int tabulation_count = 0;
        for (int i=0, j=0; i<json.length(); i++) {
            if(json.charAt(i) == '{')
            {
                tabulation_count++;
                cleanJson += replaceAtCut(j, i, json, "{\n" + writeTab(tabulation_count));
                j = (i == 0) ? 1 : (i + 1);
            }
            if(json.charAt(i) == '}')
            {
                tabulation_count--;
                cleanJson += replaceAtCut(j, i, json, "\n" + writeTab(tabulation_count) + "}");
                j = i + 1;
            }
            if(json.charAt(i) == ',') {
                cleanJson += replaceAtCut(j, i, json, ",\n" + writeTab(tabulation_count));
                j = i + 1;
            }
        }
        return cleanJson;
    }

    /**
     * Ajoute une chaine entre une autre chaine
     * @param position
     * @param str
     * @param sub
     * @return
     */
    public static String replaceAt(int position, String str, String sub) {
        String replacedString = str;
        try {
            replacedString = str.substring(0, position) + sub + str.substring(position + 1, str.length());
        } catch(StringIndexOutOfBoundsException e) {
            System.err.println("replaceAt : outOfBoundsExeption.");
        }
        return replacedString;
    }

    /**
     *
     * @param depPosition
     * @param endPosition
     * @param str
     * @param sub
     * @return
     */
    public static String replaceAtCut(int depPosition, int endPosition, String str, String sub) {
        String replacedString = str;
        try {
            replacedString = str.substring(depPosition, endPosition) + sub;
        } catch(StringIndexOutOfBoundsException e) {
            System.err.println("replaceAt : outOfBoundsExeption.");
        }
        return replacedString;
    }

    /**
     * Retourn n tabulation
     * @param n
     * @return string with n tabulation
     */
    public static String writeTab(int n)
    {
        String tabulation = "";
        for(int i=0; i<n; i++) {
            tabulation += "\u0009";
        }
        return tabulation;
    }
}
