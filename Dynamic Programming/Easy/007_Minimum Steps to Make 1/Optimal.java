/**
 * Problem: Minimum Steps to Reduce N to 1 (Divide by 2/3 or Decrement)
 * Approach: Optimal - Bottom-Up DP
 *
 * Idea:
 *  - Same recurrence as the brute force, computed ITERATIVELY from
 *    the bottom up (i = 2 to n) instead of recursively from the top
 *    down:
 *        dp[i] = 1 + dp[i-1]                   (decrement, always valid)
 *        dp[i] = min(dp[i], 1 + dp[i/2])        if i % 2 == 0
 *        dp[i] = min(dp[i], 1 + dp[i/3])        if i % 3 == 0
 *
 *  - Since every candidate predecessor (i-1, i/2, i/3) is strictly
 *    SMALLER than i, by the time we compute dp[i], all three
 *    possible predecessors have already been finalized earlier in
 *    the loop - a straightforward left-to-right fill is sufficient,
 *    no special ordering trickery needed.
 *
 *  - dp[1] = 0 is the base case (already at the target).
 *
 * Time Complexity:  O(n) -> single pass from i=2 to n, O(1) work
 *                    per value (at most 3 constant-time checks)
 * Space Complexity: O(n) -> the dp array
 */
class Solution {
    public int getMinSteps(int n) {
        if (n == 1) return 0;

        int[] dp = new int[n + 1];
        dp[1] = 0;

        for (int i = 2; i <= n; i++) {
            // Operation: decrement by 1
            dp[i] = dp[i - 1] + 1;

            // Operation: divide by 2
            if (i % 2 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 2] + 1);
            }

            // Operation: divide by 3
            if (i % 3 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 3] + 1);
            }
        }

        return dp[n];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.getMinSteps(10));  // Expected: 3
        System.out.println(solution.getMinSteps(1));   // Expected: 0
    }
}
