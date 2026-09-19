/**
 * Problem: Subset Sum Equals to Target
 * Approach: Optimal - 1D DP (Space-Optimized 0/1 Knapsack Style)
 *
 * Idea:
 *  - dp[sum] = true if SOME subset of the elements processed SO FAR
 *    can sum to exactly `sum`.
 *
 *  - Initialize dp[0] = true (the empty subset always sums to 0,
 *    regardless of which elements are available) - everything else
 *    starts false.
 *
 *  - For each number in arr, update the dp array to reflect that
 *    this number is now available to use. Traverse the sum range
 *    BACKWARDS (from target down to num) - this is the classic
 *    0/1 KNAPSACK space-optimization trick:
 *        dp[sum] = dp[sum] || dp[sum - num]
 *
 *  - WHY BACKWARDS? If we went forwards (sum increasing), then
 *    dp[sum - num] might have ALREADY been updated earlier in THIS
 *    SAME pass for the current number - meaning we could accidentally
 *    use the same element multiple times in one "subset" (e.g.,
 *    using num twice to reach a sum). Going backwards ensures
 *    dp[sum - num] still reflects the state from BEFORE this number
 *    was considered, correctly modeling "each element used at most
 *    once."
 *
 *  - After processing all numbers, dp[target] tells us whether
 *    target is achievable as a subset sum.
 *
 * Time Complexity:  O(n * target) -> for each of n numbers, scan
 *                    through up to `target` possible sums
 * Space Complexity: O(target)     -> a single 1D boolean array of
 *                    size target+1
 */
class Solution {
    public boolean isSubsetSum(int[] arr, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : arr) {
            // Traverse backwards so each element is used at most once
            for (int sum = target; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }

        return dp[target];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {1, 2, 7, 3};
        System.out.println(solution.isSubsetSum(arr1, 6));  // Expected: true

        int[] arr2 = {2, 3, 5};
        System.out.println(solution.isSubsetSum(arr2, 6));  // Expected: false

        int[] arr3 = {7, 54, 4, 12, 15, 5};
        System.out.println(solution.isSubsetSum(arr3, 9));  // Expected: true
    }
}
