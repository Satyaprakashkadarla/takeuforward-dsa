/**
 * Problem: Minimum Operations To Reach 1
 * Approach: Brute Force (Plain Recursion Over All Valid Divisor Moves)
 *
 * Idea:
 *  - Define f(i) = minimum number of operations to reduce i down to 1.
 *  - f(1) = 0 (already at the target, no operations needed).
 *  - For i > 1, try EVERY divisor x of i (where 0 < x < i), moving
 *    to (i - x), and take whichever choice leads to the fewest
 *    remaining operations:
 *        f(i) = 1 + min over all valid divisors x of f(i - x)
 *  - This checks every divisor one at a time (a naive O(i) divisor
 *    scan per call, rather than the O(sqrt(i)) trick used in the
 *    optimal version), and recomputes f(i-x) for overlapping
 *    subproblems repeatedly without caching - both factors combine
 *    to make this slow for larger n.
 *
 * Time Complexity:  Exponential in the worst case - branches over
 *                    every divisor of i at each step, with no
 *                    memoization, causing heavy repeated subproblem
 *                    computation
 * Space Complexity: O(n) -> maximum recursion stack depth
 */
public class Bruteforce {

    public int minOperations(int n) {
        return solve(n);
    }

    private int solve(int i) {
        if (i == 1) {
            return 0;
        }

        int best = Integer.MAX_VALUE;

        // Try every valid divisor x of i (0 < x < i)
        for (int x = 1; x < i; x++) {
            if (i % x == 0) {
                best = Math.min(best, 1 + solve(i - x));
            }
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.minOperations(8));  // Expected: 3
        System.out.println(solution.minOperations(5));  // Expected: 3
    }
}
