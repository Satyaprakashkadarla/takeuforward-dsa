/**
 * Problem: Minimum Steps to Reduce N to 1 (Divide by 2/3 or Decrement)
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i) = minimum steps to reduce i down to 1.
 *  - f(1) = 0 (already at the target).
 *  - For i > 1, try all applicable operations and take the best:
 *      - ALWAYS available: decrement -> 1 + f(i - 1)
 *      - IF i % 2 == 0: divide by 2 -> 1 + f(i / 2)
 *      - IF i % 3 == 0: divide by 3 -> 1 + f(i / 3)
 *  - f(i) = 1 + min of whichever of the above apply
 *  - Without memoization, the same values of i are recomputed many
 *    times across different call paths (e.g., reaching the same
 *    intermediate value via different sequences of operations),
 *    causing exponential blowup for larger n.
 *
 * Time Complexity:  Exponential in the worst case -> each call
 *                    branches into up to 3 more calls, with no
 *                    caching of repeated subproblems
 * Space Complexity: O(n) -> maximum recursion depth (bounded by,
 *                    at most, n decrements in the worst case)
 */
public class Bruteforce {

    public int getMinSteps(int n) {
        return solve(n);
    }

    private int solve(int i) {
        if (i == 1) {
            return 0;
        }

        int best = 1 + solve(i - 1); // decrement is always available

        if (i % 2 == 0) {
            best = Math.min(best, 1 + solve(i / 2));
        }
        if (i % 3 == 0) {
            best = Math.min(best, 1 + solve(i / 3));
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.getMinSteps(10));  // Expected: 3
        System.out.println(solution.getMinSteps(1));   // Expected: 0
    }
}
