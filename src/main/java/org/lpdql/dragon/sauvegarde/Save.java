package org.lpdql.dragon.sauvegarde;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.scenario.Accomplish;
import org.lpdql.dragon.scenario.Scenario;
import org.lpdql.dragon.scenario.Art;
import org.lpdql.dragon.scenario.errors.ScenarioError;
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
     *
     */
    private Accomplish accomplishement;

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
        this.accomplishement = savedHero.getAccomplishement();
        this.defaultMapName = "data/" + savedMap + ".tmx";
    }

    /**
     * Detect if a save file exist in current save dir.
     * @return en new instance of save with all data saved.
     */
    public static Save detectSavedData() {
        File file = new File(Save.savedFileName);
        if(!file.exists())
            return new Save();

        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new FileReader(file), JsonObject.class);

            JsonObject mapData = jsonObject.getAsJsonObject("mapData");
            JsonObject playerData = jsonObject.getAsJsonObject("playerData");
            JsonObject accomplishement = jsonObject.getAsJsonObject("accomplishement");

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
            boolean playerSwordArt = playerData.get("epeeArt").getAsBoolean();
            boolean playerShiedArt = playerData.get("shieldArt").getAsBoolean();
            boolean playerFireArt = playerData.get("fireArt").getAsBoolean();
            boolean playerFlyArt = playerData.get("flyArt").getAsBoolean();

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


            savedHero.setArtEpee(playerSwordArt);
            savedHero.setArtBouclier(playerShiedArt);
            savedHero.setArtFeu(playerFireArt);
            savedHero.setArtVoler(playerFlyArt);

            // accomplishement
            Accomplish savedAccomplishement = savedHero.getAccomplishement();
            if(accomplishement.get(Art.ENUM.EPEE.toString()).getAsBoolean())
                savedAccomplishement.setEndArt(Art.ENUM.EPEE.toString());

            if(accomplishement.get(Art.ENUM.BOUCLIER.toString()).getAsBoolean())
                savedAccomplishement.setEndArt(Art.ENUM.BOUCLIER.toString());

            if(accomplishement.get(Art.ENUM.FEU.toString()).getAsBoolean())
                savedAccomplishement.setEndArt(Art.ENUM.FEU.toString());

            if(accomplishement.get(Art.ENUM.VOLER.toString()).getAsBoolean())
                savedAccomplishement.setEndArt(Art.ENUM.VOLER.toString());

            savedHero.setAccomplishement(savedAccomplishement);

            return new Save(savedHero, mapName);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (NullPointerException p) {
            String nameofCurrMethod = new Throwable()
                    .getStackTrace()[0]
                    .getMethodName();

            System.err.println(nameofCurrMethod + " : saved data has been corrupted");
        } catch (ScenarioError scenarioError) {

            scenarioError.printStackTrace();
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
     *
     * @return
     */
    public Accomplish getAccomplishement() {
        return this.accomplishement;
    }

    /**
     * sauvegarde les données
     */
    public boolean save(Hero hero, String currentMapName) {
        File file = new File(Save.savedFileName);
        if(file.exists())
            file.delete();

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

        // a delete
        playerData.addProperty("epeeArt", hero.isArtEpee());
        playerData.addProperty("shieldArt", hero.isArtBouclier());
        playerData.addProperty("fireArt", hero.isArtFeu());
        playerData.addProperty("flyArt", hero.isArtVoler());
        save.add("playerData", playerData);


        JsonObject accomplishement = new JsonObject();

        accomplishement.addProperty(Art.ENUM.EPEE.toString(), hero.getAccomplishement().isArtAccomplished(Art.ENUM.EPEE));
        accomplishement.addProperty(Art.ENUM.BOUCLIER.toString(), hero.getAccomplishement().isArtAccomplished(Art.ENUM.BOUCLIER));
        accomplishement.addProperty(Art.ENUM.FEU.toString(), hero.getAccomplishement().isArtAccomplished(Art.ENUM.FEU));
        accomplishement.addProperty(Art.ENUM.VOLER.toString(), hero.getAccomplishement().isArtAccomplished(Art.ENUM.VOLER));
        save.add("accomplishement", accomplishement);

        try {
            Writer writer = new FileWriter(Save.savedFileName);
            String test = save.toString();
            System.out.println(cleanJsonString(test));

            writer.write(cleanJsonString(test));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.err.println("Successfully game saved...");
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
