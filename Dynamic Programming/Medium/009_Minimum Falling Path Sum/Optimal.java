/**
 * Problem: Minimum Falling Path Sum
 * Approach: Optimal - Row-by-Row DP (Space-Optimized)
 *
 * Idea:
 *  - dp[j] represents the minimum path sum to reach column j of the
 *    CURRENT row, considering all valid starting points in row 0.
 *
 *  - Initialize dp as a clone of the first row - since we can start
 *    at ANY cell in row 0, each cell's own value is trivially the
 *    "minimum path sum to reach itself" at this starting point.
 *
 *  - For each subsequent row i, compute a fresh `next` array: for
 *    each column j, the cheapest way to arrive at (i, j) is via
 *    whichever of the three cells directly above it (straight up,
 *    up-left, up-right) had the smallest dp value - add matrix[i][j]
 *    to that minimum.
 *
 *  - Boundary checks (j > 0, j+1 < n) prevent looking outside the
 *    row's column range when at the edges.
 *
 *  - After processing all rows, dp holds the minimum path sum to
 *    reach each column of the LAST row. Since we can END at any
 *    cell in the last row, the final answer is the minimum value
 *    across the whole dp array.
 *
 * Time Complexity:  O(m * n) -> visits every cell exactly once,
 *                    doing O(1) work per cell
 * Space Complexity: O(n)     -> two arrays of size n (dp and next)
 *                    at any given time
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] dp = matrix[0].clone();

        for (int i = 1; i < m; i++) {
            int[] next = new int[n];

            for (int j = 0; j < n; j++) {
                int best = dp[j];

                if (j > 0) {
                    best = Math.min(best, dp[j - 1]);
                }

                if (j + 1 < n) {
                    best = Math.min(best, dp[j + 1]);
                }

                next[j] = matrix[i][j] + best;
            }

            dp = next;
        }

        int ans = dp[0];
        for (int j = 1; j < n; j++) {
            ans = Math.min(ans, dp[j]);
        }

        return ans;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] m1 = {{1, 2, 10, 4}, {100, 3, 2, 1}, {1, 1, 20, 2}, {1, 2, 2, 1}};
        System.out.println(solution.minFallingPathSum(m1));  // Expected: 6

        int[][] m2 = {{1, 4, 3, 1}, {2, 3, -1, -1}, {1, 1, -1, 8}};
        System.out.println(solution.minFallingPathSum(m2));  // Expected: -1

        int[][] m3 = {{4, 3, 4}, {4, 5, 1}, {4, 6, 2}, {4, 1, 4}};
        System.out.println(solution.minFallingPathSum(m3));  // Expected: 7
    }
}
