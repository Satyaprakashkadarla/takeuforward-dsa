/**
 * Brute Force Approach: Plain Recursion
 * Time Complexity: O(k^n) - Exponential, exploring all possible paths.
 * Space Complexity: O(n) - Auxiliary stack space due to recursion depth.
 */
class Solution {
    
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        return solve(n - 1, heights, k);
    }

    private int solve(int index, int[] heights, int k) {
        // Base case: Start step requires 0 energy
        if (index == 0) {
            return 0;
        }

        int minEnergy = Integer.MAX_VALUE;

        // Try all possible jumps from 1 to k steps backward
        for (int j = 1; j <= k; j++) {
            if (index - j >= 0) {
                int jumpEnergy = solve(index - j, heights, k) 
                                 + Math.abs(heights[index] - heights[index - j]);
                minEnergy = Math.min(minEnergy, jumpEnergy);
            }
        }

        return minEnergy;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] heights = {15, 4, 1, 14, 15};
        int k = 4;
        System.out.println("Minimum Energy (Brute Force): " + sol.frogJump(heights, k));
    }
}
