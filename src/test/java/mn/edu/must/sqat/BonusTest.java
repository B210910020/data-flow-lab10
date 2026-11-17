package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Data-flow tests for DataFlowTargets.bonus (line numbers = file lines 31-56).
 *
 * Stage 1 - ALL-DEFS: every definition reaches at least one use.
 * Two tests are the minimum: score=0 (49) reaches a use only when 51 is NOT
 * executed (present < threshold), while score=5 (51) is only defined when
 * present >= threshold. No single run can do both.
 */
class BonusTest {

    // ---------- Stage 1: ALL-DEFS ----------

    @Test
    @DisplayName("D1 [1,0,1,1,1,1,1] thr=1 -> 8: defs 31,35,36,37,38,40,41,43,46,51,54 reach a use")
    void d1_allDefsExceptScore49() {
        // 46 -> 41 (absent day then present), 43 -> 53, 51 -> 54, 54 -> 56
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 0, 1, 1, 1, 1, 1}, 1));
    }

    @Test
    @DisplayName("D2 [] thr=1 -> 0: def score(49) reaches use 56")
    void d2_score49ReachesReturn() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {}, 1));
    }
}
