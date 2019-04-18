package org.lpdql.dragon.sauvegarde;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaveTest {

    private Save uneSave;
    private String str;

    @Before
    public void setUp() throws Exception {
        uneSave = new Save();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void cleanJsonStringTest() {
        str = Save.cleanJsonString("{\"gameName\":\"DragonProject\",\"mapData\":{\"map\":\"dragon\"}}");
        assertEquals("{\n\t\"gameName\":\"DragonProject\",\n\t\"mapData\":{\n\t\t\"map\":\"dragon\"\n\t}\n}", str);
        // -System.out.println(str);
    }


}