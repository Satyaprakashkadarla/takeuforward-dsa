/**
 * Problem: Count Ways to Reach the Nth Stair (1, 2 or 3 Steps)
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - To reach step n, the child's very last hop was one of:
 *      - a 1-step hop from step (n-1)
 *      - a 2-step hop from step (n-2)
 *      - a 3-step hop from step (n-3)
 *  - So the total number of ways is the SUM of the ways to reach
 *    each of those three earlier steps:
 *        ways(n) = ways(n-1) + ways(n-2) + ways(n-3)
 *  - Base cases:
 *        ways(0) = 1  (one way to "be at the start": do nothing)
 *        ways(negative) = 0  (can't land on a step below the start)
 *    These give ways(1) = 1, ways(2) = 2, ways(3) = 4 automatically.
 *  - Without memoization, the same subproblems are recomputed many
 *    times (e.g. ways(n-3) is reached via three different paths),
 *    causing exponential blowup.
 *
 * Time Complexity:  O(3^n) -> each call branches into 3 more calls,
 *                    with no caching of repeated subproblems
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    static int countWays(int n) {
        if (n < 0) return 0;
        if (n == 0) return 1;
        return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
    }

    // Simple test driver
    public static void main(String[] args) {
        System.out.println(countWays(3));  // Expected: 4
        System.out.println(countWays(4));  // Expected: 7
        System.out.println(countWays(1));  // Expected: 1
    }
}
