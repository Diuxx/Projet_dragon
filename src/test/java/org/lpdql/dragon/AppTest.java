package org.lpdql.dragon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lpdql.dragon.bigBataille.EnnemiBatailleTest;
import org.lpdql.dragon.bigBataille.HeroBatailleTest;
import org.lpdql.dragon.jeu.MessageTest;
import org.lpdql.dragon.jeu.TestLevelExperience;
import org.lpdql.dragon.objets.HealTest;
import org.lpdql.dragon.personnages.EnnemiStatistiquesTest;
import org.lpdql.dragon.personnages.EnnemiTest;
import org.lpdql.dragon.personnages.PersonnageNonJoueurTest;
import org.lpdql.dragon.scenario.ScenarioTest;
import org.lpdql.dragon.sound.MyJSoundTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScenarioTest.class,
        EnnemiTest.class,
        TestLevelExperience.class,
        MessageTest.class,
        PersonnageNonJoueurTest.class,
        EnnemiBatailleTest.class,
        HeroBatailleTest.class,
        HealTest.class,
        EnnemiStatistiquesTest.class,
        MyJSoundTest.class,
})
public class AppTest {

}