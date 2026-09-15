/**
 * Problem: Unique Paths II
 * Approach: Optimal - 1D DP on Grid (Row-by-Row, Reused Array)
 *
 * Idea:
 *  - Unlike the obstacle-free "Grid Unique Paths" problem, we can't
 *    use a clean closed-form combinatorics formula here, since
 *    obstacles eliminate specific paths in position-dependent ways.
 *    We fall back to standard DP on the grid.
 *
 *  - dp[j] represents the number of unique paths to reach the cell
 *    at the CURRENT row, column j - built up incrementally as we
 *    scan row by row, left to right.
 *
 *  - Quick upfront check: if the start or end cell is itself
 *    blocked, no path can possibly exist -> return 0 immediately.
 *
 *  - Initialize dp[0] = 1 (there's exactly one way to be at the very
 *    first cell, assuming it's not blocked - already checked above).
 *
 *  - For each cell (i, j):
 *      - If blocked (matrix[i][j] == 1): NO paths can pass through
 *        this cell, so dp[j] = 0 (overwriting whatever value it had
 *        from the previous row).
 *      - Otherwise (j > 0): dp[j] += dp[j-1]. This is the key trick
 *        of reusing a 1D array across rows: at this point, dp[j]
 *        still holds the value from the PREVIOUS row (i.e., "paths
 *        arriving from above"), and dp[j-1] has ALREADY been updated
 *        for the CURRENT row (i.e., "paths arriving from the left").
 *        Adding them together combines both contributions correctly,
 *        without needing a full 2D table.
 *      - (If j == 0 and the cell isn't blocked, dp[0] simply carries
 *        forward unchanged from the row above, representing "must
 *        have come straight down the first column.")
 *
 *  - After processing all rows, dp[n-1] holds the total number of
 *    unique paths to the bottom-right cell.
 *
 * Time Complexity:  O(m * n) -> visits every cell exactly once
 * Space Complexity: O(n)     -> a single 1D array of size n, reused
 *                    across all m rows
 */
class Solution {
    public int uniquePathsWithObstacles(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (matrix[0][0] == 1 || matrix[m - 1][n - 1] == 1)
            return 0;

        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Blocked cell
                if (matrix[i][j] == 1) {
                    dp[j] = 0;
                } else if (j > 0) {
                    // paths from top + paths from left
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n - 1];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] m1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m1));  // Expected: 2

        int[][] m2 = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m2));  // Expected: 0

        int[][] m3 = {{0, 0, 0, 0}, {0, 0, 1, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m3));  // Expected: 1
    }
}
