/**
 * Problem: Lucas Number
 * Approach: Optimal - Iterative DP (O(1) Space, Same Pattern as Fibonacci)
 *
 * Idea:
 *  - Same recurrence as the brute force (Ln = Ln-1 + Ln-2), computed
 *    ITERATIVELY from the bottom up instead of recursively.
 *
 *  - Since each value only depends on the previous two, track just
 *    two rolling variables (a, b) instead of a full array - the
 *    exact same space-optimization pattern as Fibonacci / Climbing
 *    Stairs, just with different starting values (a=L0=2, b=L1=1
 *    instead of Fibonacci's 0 and 1).
 *
 *  - The modulus is applied at each addition step, keeping every
 *    intermediate value bounded within a safe range despite n being
 *    as large as 10^6 (Lucas numbers grow exponentially, so without
 *    the modulus, raw values would far exceed any fixed-size integer
 *    type long before n reaches even a few hundred).
 *
 * Time Complexity:  O(n) -> single pass from i=2 to n
 * Space Complexity: O(1) -> only two variables tracked
 */
class Solution {
    public long lucas(int n) {
        final long MOD = 1_000_000_007L;

        if (n == 0) return 2;
        if (n == 1) return 1;

        long a = 2; // L0
        long b = 1; // L1

        for (int i = 2; i <= n; i++) {
            long c = (a + b) % MOD;
            a = b;
            b = c;
        }

        return b;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.lucas(5));  // Expected: 11
        System.out.println(solution.lucas(7));  // Expected: 29
    }
}
