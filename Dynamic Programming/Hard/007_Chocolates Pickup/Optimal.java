/**
 * Problem: Two Robots Collecting Chocolates (Cherry Pickup II)
 * Approach: Optimal - 3D Dynamic Programming (Bottom-Up)
 *
 * Idea:
 *  - Both robots move down exactly one row per step, so after i
 *    moves they are both in row i. The state is therefore
 *    (row, column of robot 1, column of robot 2):
 *        dp[i][c1][c2] = max chocolates collected from row 0 through
 *                        row i, with robot 1 at column c1 and robot 2
 *                        at column c2 in row i.
 *
 *  - Unreachable states are marked with a large negative sentinel
 *    (-1_000_000). Real values are never negative, so any value
 *    above the sentinel means "reachable".
 *
 *  - Base case: at row 0 the robots are fixed at columns 0 and m-1,
 *    so only dp[0][0][m-1] is reachable, equal to
 *    grid[0][0] + grid[0][m-1]. (m >= 2, so the two start cells are
 *    different and both are counted.)
 *
 *  - Transition for row i >= 1: for every (c1, c2), look at the 3 x 3
 *    = 9 combinations of where the robots were in row i-1 (each could
 *    have been one column to the left, the same column, or one to the
 *    right) and take the best previous value, maxPrev. If at least one
 *    of those states was reachable, then
 *        dp[i][c1][c2] = maxPrev + grid[i][c1]
 *                                + (c1 == c2 ? 0 : grid[i][c2])
 *    The ternary implements the rule that a cell shared by both robots
 *    is collected only once.
 *
 *  - Both robots must reach the bottom row, so the answer is the
 *    maximum of dp[n-1][c1][c2] over all column pairs.
 *
 * Time Complexity:  O(n * m^2 * 9) = O(n * m^2) -> n rows, m^2 column
 *                    pairs, 9 previous states each
 * Space Complexity: O(n * m^2) -> the full 3D table. Since row i only
 *                    needs row i-1, it can be reduced to O(m^2) by
 *                    keeping two layers (see NOTES.md).
 */
class Solution {
    public int maxChocolate(int grid[][]) {
        int n = grid.length, m = grid[0].length;
        int[][][] dp = new int[n][m][m];
        
        // Initialize with large negative
        for (int i = 0; i < n; i++)
            for (int c1 = 0; c1 < m; c1++)
                for (int c2 = 0; c2 < m; c2++)
                    dp[i][c1][c2] = -1_000_000;
        
        // First row: both at start positions
        dp[0][0][m - 1] = grid[0][0] + grid[0][m - 1];
        
        for (int i = 1; i < n; i++) {
            for (int c1 = 0; c1 < m; c1++) {
                for (int c2 = 0; c2 < m; c2++) {
                    int maxPrev = -1_000_000;
                    for (int pc1 = Math.max(0, c1 - 1); pc1 <= Math.min(m - 1, c1 + 1); pc1++) {
                        for (int pc2 = Math.max(0, c2 - 1); pc2 <= Math.min(m - 1, c2 + 1); pc2++) {
                            maxPrev = Math.max(maxPrev, dp[i - 1][pc1][pc2]);
                        }
                    }
                    if (maxPrev > -1_000_000) {
                        int collect = grid[i][c1] + (c1 == c2 ? 0 : grid[i][c2]);
                        dp[i][c1][c2] = maxPrev + collect;
                    }
                }
            }
        }
        
        int ans = 0;
        for (int c1 = 0; c1 < m; c1++)
            for (int c2 = 0; c2 < m; c2++)
                ans = Math.max(ans, dp[n - 1][c1][c2]);
        return ans;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] g = {{4, 1, 2}, {3, 6, 1}, {1, 6, 6}, {3, 1, 2}};
        System.out.println(solution.maxChocolate(g));  // Expected: 32
    }
}
