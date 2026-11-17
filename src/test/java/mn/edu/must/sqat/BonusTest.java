package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ---------- Stage 2: ALL-DU-PAIRS (added on top of D1, D2) ----------

    @Test
    @DisplayName("P1 null -> IAE: attendance(31,32T)")
    void p1_nullAttendance() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 1));
    }

    @Test
    @DisplayName("P2 [1] thr=-1 -> IAE: threshold(31,32T)")
    void p2_negativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }

    @Test
    @DisplayName("P3 [] thr=0 -> 5: present(35,50T) threshold(31,50T) best(37,53F) score(51,56) attendance(31,38F)")
    void p3_emptyThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("P4 [0,0,0] thr=1 -> 0: a(38,39F) streak46 killed before any use")
    void p4_allAbsent() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {0, 0, 0}, 1));
    }

    @Test
    @DisplayName("P5 [1,1,1,1,1] thr=6 -> 3: score(49,54) present(40,40) present(40,50F) streak(41,41) best(43,42T) best(43,53T)")
    void p5_fiveStreakBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("P6 [1,0,1,1] thr=2 -> 5: streak(46,41) streak(41,42F) best(43,42F) best(43,53F) present(40,50T)")
    void p6_brokenShortStreak() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 0, 1, 1}, 2));
    }

    @Test
    @DisplayName("P7 [1,1,1,1,1,1] thr=3 -> 8: score(51,54) score(54,56) with streak longer than 5")
    void p7_longStreakAboveThreshold() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1, 1}, 3));
    }

    @Test
    @DisplayName("P8 [1,1,1] thr=3 -> 5: present exactly equals threshold (boundary of 50)")
    void p8_exactlyThreshold() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1, 1}, 3));
    }

    // ---------- Part C: kills mutant M2 (best = present) ----------

    @Test
    @DisplayName("K1 [1,1,1,0,1,1] thr=1 -> 5: present=5 but best=3, so best(43) must come from streak not present")
    void k1_killsM2_presentDiffersFromBest() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1, 1, 0, 1, 1}, 1));
    }
}
