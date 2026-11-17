package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * All-DU-pairs tests for DataFlowTargets.gcd (line numbers = file lines).
 * Defs: 13 (x, y), 16 (tmp), 17 (x), 18 (y).
 * Uses: 15 p-use y; 16 c-use x, y; 17 c-use y; 18 c-use tmp; 20 c-use x.
 */
class GcdTest {

    @Test
    @DisplayName("G1 gcd(7,0)=7: x(13,20), y(13,15F) - zero iterations")
    void g1_zeroIterations_x13to20_y13to15F() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2 gcd(5,5)=5: one iteration - x(13,16) x(17,20) y(13,15T) y(13,16) y(13,17) tmp(16,18) y(18,15F)")
    void g2_oneIteration() {
        assertEquals(5, DataFlowTargets.gcd(5, 5));
    }

    @Test
    @DisplayName("G3 gcd(12,8)=4: two iterations - x(17,16) y(18,15T) y(18,16) y(18,17)")
    void g3_twoIterations_loopCarriedPairs() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G4 gcd(0,5)=5: one iteration with x=0 - x(13,16) tmp(16,18) x(17,20)")
    void g4_zeroFirstArgument() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
