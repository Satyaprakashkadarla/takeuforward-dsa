/**
 * Problem: House Robber (Circular)
 * Approach: Optimal - Iterative DP, Reduced to Two Linear Subproblems
 *
 * Idea:
 *  - Since house 0 and house (n-1) are circularly adjacent, any
 *    valid robbery plan can NEVER include both of them. This splits
 *    the problem into exactly two mutually exclusive cases:
 *      (a) Exclude house (n-1): solve the LINEAR "max sum, no two
 *          adjacent" problem over the range [0, n-2].
 *      (b) Exclude house 0: solve the same linear problem over the
 *          range [1, n-1].
 *  - The overall answer is simply the larger of these two results -
 *    at least one of them is guaranteed to represent a valid optimal
 *    circular solution, since any valid plan excludes at least one
 *    of house 0 or house (n-1).
 *
 *  - `rob(a, l, r)` solves the standard LINEAR non-adjacent-max-sum
 *    problem (the same recurrence as "Maximum Sum of Non-Adjacent
 *    Elements") over the inclusive range [l, r], using the familiar
 *    two-rolling-variable O(1) space technique:
 *        dp[i] = max( dp[i-1], a[i] + dp[i-2] )
 *
 *  - Special case: if there's only 1 house total, there's no
 *    adjacency concern at all (a single house can't be "adjacent to
 *    itself"), so the answer is simply that house's value.
 *
 * Time Complexity:  O(n) -> two linear O(n) passes (still O(n) overall,
 *                    since running the same-order algorithm twice
 *                    doesn't change the complexity class)
 * Space Complexity: O(1) -> only a few rolling variables used
 */
class Solution {
    public int houseRobber(int[] money) {
        int n = money.length;

        if (n == 1) return money[0];

        return Math.max(
            rob(money, 0, n - 2),
            rob(money, 1, n - 1)
        );
    }

    private int rob(int[] a, int l, int r) {
        int prev2 = 0, prev1 = 0;

        for (int i = l; i <= r; i++) {
            int cur = Math.max(prev1, prev2 + a[i]);
            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] money1 = {2, 1, 4, 9};
        System.out.println(solution.houseRobber(money1));  // Expected: 10

        int[] money2 = {1, 5, 2, 1, 6};
        System.out.println(solution.houseRobber(money2));  // Expected: 11

        int[] money3 = {9, 4, 1, 8};
        System.out.println(solution.houseRobber(money3));  // Expected: 12
    }
}
