/**
 * Problem: Frog Jump
 * Approach: Optimal - Iterative Dynamic Programming (O(1) Space)
 *
 * Idea:
 *  - Same recurrence as the brute force (dp[i] = min energy to reach
 *    step i from step 0), but computed ITERATIVELY from the bottom
 *    up, avoiding all the repeated recursive work.
 *
 *  - Since dp[i] only ever depends on dp[i-1] and dp[i-2], we don't
 *    need a full array - just two rolling variables (prev1, prev2)
 *    that get updated as we iterate forward, exactly like the
 *    space-optimized Climbing Stairs / Fibonacci pattern.
 *
 *  - At each step i:
 *      oneStep = prev1 + |heights[i] - heights[i-1]|
 *      twoStep = prev2 + |heights[i] - heights[i-2]|   (only if i > 1)
 *      current = min(oneStep, twoStep)
 *    then shift the rolling window forward.
 *
 * Time Complexity:  O(n) -> single pass through the array
 * Space Complexity: O(1) -> only two variables tracked at any time
 */
class Solution {
    public int frogJump(int[] heights) {
        int n = heights.length;

        if (n <= 1) return 0;

        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]

        for (int i = 1; i < n; i++) {
            int oneStep = prev1 + Math.abs(heights[i] - heights[i - 1]);

            int twoStep = Integer.MAX_VALUE;
            if (i > 1) {
                twoStep = prev2 + Math.abs(heights[i] - heights[i - 2]);
            }

            int current = Math.min(oneStep, twoStep);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] h1 = {2, 1, 3, 5, 4};
        System.out.println(solution.frogJump(h1));  // Expected: 2

        int[] h2 = {7, 5, 1, 2, 6};
        System.out.println(solution.frogJump(h2));  // Expected: 9

        int[] h3 = {3, 10, 3, 11, 3};
        System.out.println(solution.frogJump(h3));  // Expected: 0
    }
}
