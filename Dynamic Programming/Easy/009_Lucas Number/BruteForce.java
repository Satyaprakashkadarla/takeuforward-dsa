/**
 * Problem: Lucas Number
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Directly translate the recurrence Ln = Ln-1 + Ln-2 into a
 *    recursive function, with base cases L0=2 and L1=1.
 *  - Apply the modulus at each addition to keep intermediate values
 *    bounded (though this doesn't help the EXPONENTIAL TIME problem
 *    at all - it only prevents overflow of individual values).
 *  - Without memoization, this suffers the exact same exponential
 *    blowup as naive Fibonacci recursion, since it's structurally
 *    identical (just different base cases).
 *
 * Time Complexity:  O(2^n) -> each call branches into 2 more calls,
 *                    with no caching of repeated subproblems
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    private static final long MOD = 1_000_000_007L;

    public long lucas(int n) {
        if (n == 0) return 2;
        if (n == 1) return 1;
        return (lucas(n - 1) + lucas(n - 2)) % MOD;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.lucas(5));  // Expected: 11
        System.out.println(solution.lucas(7));  // Expected: 29
    }
}
