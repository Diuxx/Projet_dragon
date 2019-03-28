package org.lpdql.dragon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import scenario.AccomplishTest;
import scenario.ScenarioTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccomplishTest.class,
        ScenarioTest.class
})
public class AppTest {

}