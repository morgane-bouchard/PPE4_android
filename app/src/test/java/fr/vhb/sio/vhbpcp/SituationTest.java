package fr.vhb.sio.vhbpcp;

import org.junit.Test;
import java.sql.Date;

import fr.vhb.sio.vhbpcp.metier.Situation;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SituationTest {
    @Test
    public void testCreerSituation() {
        Situation uneSituation = new Situation("90","Libellé court", "Descriptif", "2", "4", Date.valueOf("2016-02-29"), Date.valueOf("2016-03-18"));
        assertNotNull(uneSituation);
        assertEquals("Libellé court", uneSituation.getLibcourt());
    }
    @Test
    public void testToString() {
        Situation uneSituation = new Situation("90","Libellé court", "Descriptif", "2", "4", Date.valueOf("2016-02-29"), Date.valueOf("2016-03-18"));
        assertNotNull(uneSituation);
        assertEquals("Libellé court", uneSituation.toString());
    }
}