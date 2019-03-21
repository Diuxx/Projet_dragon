package org.lpdql.dragon.jeu;

import java.util.HashMap;

public class LevelExperience {

	private int nombreLevels = 20;
	private int xp_premier_level = 60;
	private int xp_dernier_level = 10000;
	public HashMap<Integer, Integer> levelsExperiences = new HashMap<>();

	public LevelExperience() {
		MiseEnPlaceDesLevelsEtXP();
	}

	public void MiseEnPlaceDesLevelsEtXP() {

		double B = Math.log((double) xp_dernier_level / xp_premier_level) / (nombreLevels - 1);
		double A = (double) xp_premier_level / (Math.exp(B) - 1.0);

		for (int i = 1; i <= nombreLevels; i++) {
			int ancien_xp = (int) Math.round(A * Math.exp(B * (i - 1)));
			int nouveau_xp = (int) Math.round(A * Math.exp(B * i));
			levelsExperiences.put(i, nouveau_xp - ancien_xp);
		}

	}

	public HashMap<Integer, Integer> getLevelsExperiences() {
		return levelsExperiences;
	}
	
	
	public static void main(String[] args) {
		for(int i=1;i<10;i++)
			System.out.println(new LevelExperience().levelsExperiences.get(i));
	}
}

