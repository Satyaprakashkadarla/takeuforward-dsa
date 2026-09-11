/**
 * Problem: Maximum Sum of Non-Adjacent Elements
 * Approach: Optimal - Iterative Dynamic Programming (O(1) Space)
 *
 * Idea:
 *  - Same recurrence as the brute force:
 *        dp[i] = max( dp[i-1], nums[i] + dp[i-2] )
 *    computed ITERATIVELY from the bottom up instead of recursively.
 *
 *  - Since dp[i] only ever depends on dp[i-1] and dp[i-2], we track
 *    just two rolling variables (prev1, prev2) instead of a full
 *    array - the same space-optimization pattern used in Climbing
 *    Stairs and Frog Jump.
 *
 *  - At each index i:
 *      skip = prev1               (best sum without taking nums[i])
 *      take = nums[i] + prev2     (best sum including nums[i])
 *      current = max(skip, take)
 *    then shift the rolling window forward.
 *
 *  - Base cases: prev2 = 0 (representing "no elements considered"),
 *    prev1 = nums[0] (best sum considering only the first element).
 *
 * Time Complexity:  O(n) -> single pass through the array
 * Space Complexity: O(1) -> only two variables tracked at any time
 */
class Solution {
    public int nonAdjacent(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];

        int prev2 = 0;          // dp[i-2], starts as "no elements"
        int prev1 = nums[0];    // dp[i-1], best sum using just nums[0]

        for (int i = 1; i < n; i++) {
            int skip = prev1;
            int take = nums[i] + prev2;
            int current = Math.max(skip, take);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {1, 2, 4};
        System.out.println(solution.nonAdjacent(nums1));  // Expected: 5

        int[] nums2 = {2, 1, 4, 9};
        System.out.println(solution.nonAdjacent(nums2));  // Expected: 11

        int[] nums3 = {1, 7, 16, 8};
        System.out.println(solution.nonAdjacent(nums3));  // Expected: 17
    }
}
