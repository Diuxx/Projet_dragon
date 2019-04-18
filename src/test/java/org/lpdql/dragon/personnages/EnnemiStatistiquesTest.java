package org.lpdql.dragon.personnages;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.jeu.LevelExperience;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.Difficulty;
import org.lpdql.dragon.system.Point;

public class EnnemiStatistiquesTest {

	LevelExperience levelExperience;
	private Ennemi ennemi;

	@Before
	public void setUp() throws Exception {
		ennemi = new Ennemi("test", new Point(100, 100), 32, 32);
	}

	@After
	public void tearDown() throws Exception {
		this.ennemi = null;
	}

	@Test
	public void statistiquesTest_EnnemiLv2_NiveauDuJeuFACILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		this.ennemi.setNiveau(2);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(25, (int) this.ennemi.getATK());
		assertEquals(210, (int) this.ennemi.getPointDeVie());
		assertEquals(60, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv2_NiveauDuJeuDIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		this.ennemi.setNiveau(2);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(32, (int) this.ennemi.getATK());
		assertEquals(210, (int) this.ennemi.getPointDeVie());
		assertEquals(50, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv2_NiveauDuJeuTRES_DIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		this.ennemi.setNiveau(2);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(37, (int) this.ennemi.getATK());
		assertEquals(210, (int) this.ennemi.getPointDeVie());
		assertEquals(30, this.ennemi.getExperience());
	}

	@Test
	public void statistiquesTest_EnnemiLv4_NiveauDuJeuFACILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		this.ennemi.setNiveau(4);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(35, (int) this.ennemi.getATK());
		assertEquals(270, (int) this.ennemi.getPointDeVie());
		assertEquals(70, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv4_NiveauDuJeuDIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		this.ennemi.setNiveau(4);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(44, (int) this.ennemi.getATK());
		assertEquals(270, (int) this.ennemi.getPointDeVie());
		assertEquals(60, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv4_NiveauDuJeuTRES_DIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		this.ennemi.setNiveau(4);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(49, (int) this.ennemi.getATK());
		assertEquals(270, (int) this.ennemi.getPointDeVie());
		assertEquals(40, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv8_NiveauDuJeuFACILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.FACILE);
		this.ennemi.setNiveau(8);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(55, (int) this.ennemi.getATK());
		assertEquals(390, (int) this.ennemi.getPointDeVie());
		assertEquals(90, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv8_NiveauDuJeuDIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.DIFFICILE);
		this.ennemi.setNiveau(8);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(68, (int) this.ennemi.getATK());
		assertEquals(390, (int) this.ennemi.getPointDeVie());
		assertEquals(80, this.ennemi.getExperience());
	}
	
	@Test
	public void statistiquesTest_EnnemiLv8_NiveauDuJeuTRES_DIFFICILE() {
		InterStateComm.setNiveauDuJeu(Difficulty.TRES_DIFFICILE);
		this.ennemi.setNiveau(8);
		this.ennemi.setEnnemiStatistques(this.ennemi.getNiveau());
		this.ennemi.setExp(this.ennemi.getNiveau());
		this.ennemi.setAtk(this.ennemi.getNiveau());
		
		assertEquals(73, (int) this.ennemi.getATK());
		assertEquals(390, (int) this.ennemi.getPointDeVie());
		assertEquals(60, this.ennemi.getExperience());
	}
	
}
