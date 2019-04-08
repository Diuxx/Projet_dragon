package org.lpdql.dragon.jeu;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.personnages.Hero;

public class TestLevelExperience 
{
	Hero hero;
	LevelExperience levelExperience;
	
	@Before
	public void setUp() throws Exception {
		hero = new Hero("hero");
		levelExperience = new LevelExperience();
		levelExperience.afficherLesLevelsExperiences();
	}

	@After
	public void tearDown() throws Exception {
		hero.setExperience(0);
		hero.setLevel(1);
	}
	
	
	@Test
	public void TestLevelUpJoueurDeLevel1a5() {
		hero.setExperience(376);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(5, hero.getNiveau());
		assertEquals(0, hero.getExperience());
	}
	
	@Test
	public void testExperienceQuandLeJoeurPasseDeNiveau1a5() {
		hero.setExperience(386);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(5, hero.getNiveau());
		assertEquals(10, hero.getExperience());
	}
	
	
	@Test
	public void testLevelUpJoueurDeLevel2a5() {
		hero.setNiveau(2);
		hero.setExperience(316);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(5, hero.getNiveau());
		assertEquals(0, hero.getExperience());
	}
	
	@Test
	public void testExperienceQuandleJoeurPasseDeLevel2a5() {
		hero.setNiveau(2);
		hero.setExperience(326);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(5, hero.getNiveau());
		assertEquals(10, hero.getExperience());
	}
    
}
