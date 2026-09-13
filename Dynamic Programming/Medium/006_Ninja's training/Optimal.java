/**
 * Problem: Ninja's Training
 * Approach: Optimal - Iterative DP (Space-Optimized 2D DP)
 *
 * Idea:
 *  - This is fundamentally a 2D DP problem: the state depends on
 *    BOTH which day we're on AND which activity was chosen the
 *    previous day. Conceptually:
 *        dp[i][activity] = matrix[i][activity] + max(dp[i-1][other two activities])
 *
 *  - Rather than storing a full n x 3 table, we only ever need the
 *    PREVIOUS day's three values to compute the current day's three
 *    values - so we track a rolling array `prev` of size 3 (one
 *    entry per activity) instead of the full 2D table.
 *
 *  - `prev` starts as day 0's raw points (no previous-day
 *    restriction applies yet).
 *
 *  - For each subsequent day i, compute the best possible score for
 *    EACH of the 3 activities, given that the previous day's chosen
 *    activity must be different:
 *        run     = matrix[i][0] + max(prev[1], prev[2])   // can't have run yesterday
 *        stealth = matrix[i][1] + max(prev[0], prev[2])   // can't have stealth yesterday
 *        fight   = matrix[i][2] + max(prev[0], prev[1])   // can't have fought yesterday
 *  - Update `prev` to these new values and move to the next day.
 *
 *  - After processing all days, the answer is the max of the three
 *    final values in `prev` (whichever activity was best to end on).
 *
 * Time Complexity:  O(n) -> single pass through the days, O(1) work
 *                    per day (fixed 3 comparisons)
 * Space Complexity: O(1) -> only a fixed-size array of 3 values tracked
 */
class Solution {
    public int ninjaTraining(int[][] matrix) {
        int[] prev = matrix[0].clone();

        for (int i = 1; i < matrix.length; i++) {
            int run = matrix[i][0] + Math.max(prev[1], prev[2]);
            int stealth = matrix[i][1] + Math.max(prev[0], prev[2]);
            int fight = matrix[i][2] + Math.max(prev[0], prev[1]);

            prev[0] = run;
            prev[1] = stealth;
            prev[2] = fight;
        }

        return Math.max(prev[0], Math.max(prev[1], prev[2]));
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] m1 = {{10, 40, 70}, {20, 50, 80}, {30, 60, 90}};
        System.out.println(solution.ninjaTraining(m1));  // Expected: 210

        int[][] m2 = {{70, 40, 10}, {180, 20, 5}, {200, 60, 30}};
        System.out.println(solution.ninjaTraining(m2));  // Expected: 290

        int[][] m3 = {{20, 10, 10}, {20, 10, 10}, {20, 30, 10}};
        System.out.println(solution.ninjaTraining(m3));  // Expected: 60
    }
}
