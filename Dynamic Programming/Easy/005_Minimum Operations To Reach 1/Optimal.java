/**
 * Problem: Minimum Operations To Reach 1
 * Approach: Optimal - Bottom-Up DP with Trial Division up to sqrt(i)
 *
 * Idea:
 *  - dp[i] = minimum operations to reduce i down to 1. Build this
 *    up iteratively from i=2 to n (dp[1] stays 0 by default, since
 *    int arrays initialize to 0).
 *
 *  - BASELINE MOVE: subtracting 1 is ALWAYS a valid move (1 is
 *    always a divisor of any i), so dp[i] starts as dp[i-1] + 1 -
 *    a safe fallback before considering any smarter divisor moves.
 *
 *  - BETTER MOVES: for every divisor x of i (other than i itself),
 *    moving from i to (i - x) is also valid. Rather than checking
 *    every possible divisor one by one (which would be O(i) per
 *    number), this uses the classic TRIAL DIVISION trick: only
 *    check x up to sqrt(i). Whenever x divides i, BOTH x and its
 *    "partner" (i / x) are divisors - so each found divisor yields
 *    two candidate moves (one using x, one using i/x) for the price
 *    of a single check.
 *
 *  - The `d < i` guard when considering the partner divisor (i/x)
 *    avoids the degenerate case where i/x equals i itself (which
 *    would only happen if x were 1, but the loop starts at x=2, so
 *    this guard is a safety measure for correctness/clarity rather
 *    than a case that actually triggers given the loop bounds).
 *
 *  - dp[i] is updated to the minimum across the baseline move and
 *    every divisor-based move discovered.
 *
 * Time Complexity:  O(n * sqrt(n)) -> for each of n numbers, trial
 *                    division up to sqrt(i) to find all divisor pairs
 * Space Complexity: O(n) -> the dp array
 */
class Solution {
    int minOperations(int n) {
        int[] dp = new int[n + 1];

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + 1; // subtract 1

            for (int x = 2; x * x <= i; x++) {
                if (i % x == 0) {
                    // x is a divisor
                    dp[i] = Math.min(dp[i],
                            1 + dp[i - x]);

                    // i / x is also a divisor
                    int d = i / x;
                    if (d < i) {
                        dp[i] = Math.min(dp[i],
                                1 + dp[i - d]);
                    }
                }
            }
        }

        return dp[n];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.minOperations(8));  // Expected: 3
        System.out.println(solution.minOperations(5));  // Expected: 3
    }
}
