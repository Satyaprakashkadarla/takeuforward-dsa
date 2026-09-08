/**
 * Problem: Climbing Stairs
 * Approach: Optimal - Iterative Dynamic Programming (O(1) Space)
 *
 * Idea:
 *  - The same recurrence as the brute force (ways(n) = ways(n-1) +
 *    ways(n-2)) is computed here, but ITERATIVELY from the bottom up,
 *    instead of recursively from the top down.
 *
 *  - Since each new value only depends on the PREVIOUS TWO values,
 *    we don't need to store the entire sequence in an array (which
 *    would cost O(n) space) - we only need two variables (`a` and
 *    `b`) that we continuously update as we move forward, one step
 *    at a time. This is a classic "space-optimized DP" technique.
 *
 *  - Base cases: n <= 2 returns n directly (1 way for n=1, 2 ways
 *    for n=2), matching the recurrence's natural starting point.
 *
 *  - For n >= 3, iterate from 3 up to n, computing each new value
 *    `c` as the sum of the previous two (`a` and `b`), then shifting
 *    the window forward (a becomes the old b, b becomes the new c).
 *
 * Time Complexity:  O(n) -> single pass from 3 to n, no repeated work
 * Space Complexity: O(1) -> only two variables tracked at any time,
 *                    regardless of how large n is
 */
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int a = 1; // ways to reach step 1
        int b = 2; // ways to reach step 2

        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }

        return b;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.climbStairs(2));  // Expected: 2
        System.out.println(solution.climbStairs(3));  // Expected: 3
        System.out.println(solution.climbStairs(1));  // Expected: 1
    }
}
