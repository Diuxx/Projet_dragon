package org.lpdql.dragon.personnages;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Difficulty;

public class HeroStatistiquesTest {

	LevelExperience levelExperience;
	private Hero hero;

	@Before
	public void setUp() throws Exception {
		hero = new Hero("hero");
		levelExperience = new LevelExperience();
		levelExperience.afficherLesLevelsExperiences();
	}

	@After
	public void tearDown() throws Exception {
		this.hero = null;
		this.levelExperience = null;
	}

	@Test
	public void statistiquesTest_HeroLv2_NiveauDuJeuFACILE() {
		this.hero.setExperience(70);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(2, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(45, (int) this.hero.getATK());
		assertEquals(350, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv2_NiveauDuJeuDIFFICILE() {
		this.hero.setExperience(70);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(2, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(33, (int) this.hero.getATK());
		assertEquals(350, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv2_NiveauDuJeuTRES_DIFFICILE() {
		this.hero.setExperience(70);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(2, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(28, (int) this.hero.getATK());
		assertEquals(350, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv3_NiveauDuJeuFACILE() {
		this.hero.setExperience(139);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(3, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(50, (int) this.hero.getATK());
		assertEquals(400, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv3_NiveauDuJeuDIFFICILE() {
		this.hero.setExperience(150);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(3, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(37, (int) this.hero.getATK());
		assertEquals(400, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv3_NiveauDuJeuTRES_DIFFICILE() {
		this.hero.setExperience(150);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(3, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(32, (int) this.hero.getATK());
		assertEquals(400, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv10_NiveauDuJeuFACILE() {
		this.hero.setExperience(1998);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(10, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(85, (int) this.hero.getATK());
		assertEquals(750, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv10_NiveauDuJeuDIFFICILE() {
		this.hero.setExperience(2000);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(10, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(65, (int) this.hero.getATK());
		assertEquals(750, (int) this.hero.getPointDeVie());
	}
	
	@Test
	public void statistiquesTest_HeroLv10_NiveauDuJeuTRES_DIFFICILE() {
		this.hero.setExperience(2100);
		levelExperience.checkUpLevelEtExperience(hero.getExperience(), hero);
		assertEquals(10, this.hero.getLevel());
		
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		InterStateComm.setLeHero(this.hero);
		this.hero.setHeroStatistques(this.hero.getLevel());
		this.hero.rafraichirLePouvoirATK();
		
		assertEquals(60, (int) this.hero.getATK());
		assertEquals(750, (int) this.hero.getPointDeVie());
	}
	
	
}
