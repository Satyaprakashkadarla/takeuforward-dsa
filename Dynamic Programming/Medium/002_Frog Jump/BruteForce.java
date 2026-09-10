/**
 * Brute Force Approach: Recursion
 * Time Complexity: O(2^n) - Explores both 1-step and 2-step jumps at each index.
 * Space Complexity: O(n) - Auxiliary call stack space.
 */
class Solution {

    public int frogJump(int[] heights) {
        int n = heights.length;
        return solve(n - 1, heights);
    }

    private int solve(int index, int[] heights) {
        // Base case: 0 energy needed to stay on the initial step
        if (index == 0) {
            return 0;
        }

        // Jump 1 step backward
        int oneStep = solve(index - 1, heights) + Math.abs(heights[index] - heights[index - 1]);

        // Jump 2 steps backward (if valid)
        int twoStep = Integer.MAX_VALUE;
        if (index > 1) {
            twoStep = solve(index - 2, heights) + Math.abs(heights[index] - heights[index - 2]);
        }

        return Math.min(oneStep, twoStep);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {2, 1, 3, 5, 4};
        System.out.println("Minimum Energy (Brute Force): " + sol.frogJump(heights));
    }
}
