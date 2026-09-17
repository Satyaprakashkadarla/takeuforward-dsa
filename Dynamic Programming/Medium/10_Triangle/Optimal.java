/**
 * Problem: Triangle
 * Approach: Optimal - Bottom-Up DP (Space-Optimized)
 *
 * Idea:
 *  - Instead of working top-down (start at the apex, decide which
 *    way to go), work BOTTOM-UP: figure out, for every cell, what
 *    the minimum path sum from THAT cell down to the base already
 *    is - starting from the base itself (trivial) and working
 *    upward toward the apex.
 *
 *  - dp[j] represents "the minimum path sum from the current row's
 *    column j, down to the bottom of the triangle."
 *
 *  - Initialize dp as a clone of the LAST row - trivially, the
 *    minimum path sum from a cell that's already at the bottom is
 *    just that cell's own value (no more moves possible).
 *
 *  - Then, moving from the second-to-last row up to the top row,
 *    update dp in place:
 *        dp[j] = triangle[i][j] + min(dp[j], dp[j+1])
 *    Here, dp[j] and dp[j+1] still hold values from the ROW BELOW
 *    (since we haven't overwritten them yet for the earlier indices
 *    - we process j from 0 to i, and dp[j+1] for the current row
 *    hasn't been touched yet at the point we read it, and dp[j] is
 *    about to be overwritten with the current row's answer). This
 *    correctly combines "move straight down" (dp[j]) and "move
 *    down-right" (dp[j+1]) options from the row below.
 *
 *  - After processing all rows up to row 0, dp[0] holds the minimum
 *    path sum for the entire triangle, starting from the single
 *    apex cell.
 *
 * Time Complexity:  O(n^2) -> total number of cells in the triangle
 *                    is n(n+1)/2, so this is O(n^2)
 * Space Complexity: O(n)   -> a single array sized to the last
 *                    (longest) row, reused throughout
 */
class Solution {
    public int minTriangleSum(int[][] triangle) {
        int n = triangle.length;

        // dp[j] = minimum sum from current position to bottom
        int[] dp = triangle[n - 1].clone();

        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = triangle[i][j] + Math.min(dp[j], dp[j + 1]);
            }
        }

        return dp[0];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] t1 = {{1}, {1, 2}, {1, 2, 4}};
        System.out.println(solution.minTriangleSum(t1));  // Expected: 3

        int[][] t2 = {{1}, {4, 7}, {4, 10, 50}, {-50, 5, 6, -100}};
        System.out.println(solution.minTriangleSum(t2));  // Expected: -42

        int[][] t3 = {{3}, {-1, 3}, {-3, 2, 4}, {8, 8, 1, -4}};
        System.out.println(solution.minTriangleSum(t3));  // Expected: 5
    }
}
