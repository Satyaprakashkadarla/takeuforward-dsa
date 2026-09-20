/**
 * Problem: Partition Equal Subset Sum
 * Approach: Optimal - 1D DP (Direct Reuse of Subset Sum Logic)
 *
 * Idea:
 *  - Compute the total sum of the array. If it's ODD, an equal
 *    two-way partition is IMPOSSIBLE (an odd number can't split into
 *    two equal integer halves) -> return false immediately, no DP
 *    needed at all.
 *
 *  - If the sum is even, the problem becomes EXACTLY the Subset Sum
 *    problem: does some subset sum to exactly (total / 2)? If such a
 *    subset exists, it forms one half of the partition, and
 *    everything else (the complement subset) automatically forms
 *    the other half - guaranteed to also sum to (total / 2), since
 *    the two halves must add up to the total.
 *
 *  - This uses the IDENTICAL 1D DP approach as Subset Sum:
 *        dp[j] = true if some subset sums to exactly j
 *    updated by traversing j BACKWARDS (from target down to x) for
 *    each element x, ensuring each element is used at most once
 *    (the standard 0/1 knapsack space-optimization technique).
 *
 * Time Complexity:  O(n * sum/2) -> for each of n elements, scan
 *                    through up to (sum/2) possible partial sums
 * Space Complexity: O(sum/2)     -> a single 1D boolean array
 */
class Solution {
    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;

        for (int x : arr) {
            sum += x;
        }

        // Odd total cannot be divided equally.
        if ((sum & 1) == 1) {
            return false;
        }

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int x : arr) {
            // Traverse backwards so each element is used only once.
            for (int j = target; j >= x; j--) {
                dp[j] = dp[j] || dp[j - x];
            }
        }

        return dp[target];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {1, 10, 21, 10};
        System.out.println(solution.equalPartition(arr1.length, arr1));  // Expected: true

        int[] arr2 = {1, 2, 3, 5};
        System.out.println(solution.equalPartition(arr2.length, arr2));  // Expected: false

        int[] arr3 = {2, 2, 1, 1};
        System.out.println(solution.equalPartition(arr3.length, arr3));  // Expected: true
    }
}
