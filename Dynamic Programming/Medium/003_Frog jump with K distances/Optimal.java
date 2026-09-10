/**
 * Optimal Approach: Dynamic Programming (Bottom-Up Tabulation)
 * Time Complexity: O(n * k) - Loop runs n times; inner loop runs at most k times.
 * Space Complexity: O(n) - Tabulation array dp[] storing minimal costs.
 */
class Solution {

    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];

        // Base case: 0 cost to stay on the first step
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;

            // Check all back steps from 1 to k
            for (int j = 1; j <= k && i - j >= 0; j++) {
                int cost = dp[i - j] + Math.abs(heights[i] - heights[i - j]);
                dp[i] = Math.min(dp[i], cost);
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {15, 4, 1, 14, 15};
        int k = 4;
        System.out.println("Minimum Energy (Optimal DP): " + sol.frogJump(heights, k));
    }
}
