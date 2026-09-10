/**
 * Optimal Approach: Dynamic Programming with Space Optimization
 * Time Complexity: O(n) - Single loop pass through the array.
 * Space Complexity: O(1) - Only two variables (prev1, prev2) are maintained.
 */
class Solution {

    public int frogJump(int[] heights) {
        int n = heights.length;

        if (n <= 1) return 0;

        int prev2 = 0; // Cost to reach step i-2
        int prev1 = 0; // Cost to reach step i-1

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

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {2, 1, 3, 5, 4};
        System.out.println("Minimum Energy (Optimal DP): " + sol.frogJump(heights));
    }
}
