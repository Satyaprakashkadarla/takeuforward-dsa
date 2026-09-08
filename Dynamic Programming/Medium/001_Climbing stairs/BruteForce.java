/**
 * Problem: Climbing Stairs
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - To reach step n, your very last move was either:
 *      - a 1-step move from step (n-1), or
 *      - a 2-step move from step (n-2)
 *  - So the number of ways to reach step n is the SUM of the number
 *    of ways to reach step (n-1) and step (n-2):
 *        ways(n) = ways(n-1) + ways(n-2)
 *  - Base cases: ways(1) = 1 (only one way: a single 1-step), and
 *    ways(2) = 2 (either two 1-steps, or one 2-step).
 *  - This recursion tree branches into 2 calls at every level,
 *    without any memoization, leading to a LOT of repeated work
 *    (the same sub-problems get recomputed exponentially many times).
 *
 * Time Complexity:  O(2^n) -> each call branches into 2 more calls,
 *                    with no caching of repeated subproblems
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.climbStairs(2));  // Expected: 2
        System.out.println(solution.climbStairs(3));  // Expected: 3
        System.out.println(solution.climbStairs(1));  // Expected: 1
    }
}
