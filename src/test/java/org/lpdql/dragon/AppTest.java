package org.lpdql.dragon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lpdql.dragon.jeu.MessageTest;
import org.lpdql.dragon.jeu.TestLevelExperience;
import org.lpdql.dragon.personnages.EnnemiTest;
import org.lpdql.dragon.scenario.ScenarioTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ScenarioTest.class,
        EnnemiTest.class,
        TestLevelExperience.class,
        MessageTest.class,
})
public class AppTest {

}