package org.lpdql.dragon.jeu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.singleton.InterStateComm;

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
	
	public void afficherLesLevelsExperiences (){
		for(int i=1;i<20;i++)
			System.out.println("Level :"+i+ ", ExperienceMax :"+new LevelExperience().levelsExperiences.get(i));
	}
	
	public boolean checkUpLevelEtExperience(int experienceAajouter, Hero hero) {
		boolean levelUp = false;
		Iterator itLevelExperience = this.levelsExperiences.entrySet().iterator();
		
		while (itLevelExperience.hasNext()) {
			Map.Entry<Integer,Integer> entry =  (Entry<Integer, Integer>) itLevelExperience.next();
			
			if(hero.getLevel() == entry.getKey()) {
				if(experienceAajouter >= entry.getValue()) {
					levelUp = true;
					int nouveauExp = experienceAajouter - this.levelsExperiences.get(entry.getKey());
					hero.setExperience(nouveauExp);
					hero.setLevel(entry.getKey() + 1);
					experienceAajouter -= this.levelsExperiences.get(entry.getKey());
				}
			}
		}
		return levelUp;
	}
}