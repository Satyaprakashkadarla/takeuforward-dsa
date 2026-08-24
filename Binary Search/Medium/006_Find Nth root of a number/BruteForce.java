/**
 * Problem: Find Nth Root of a Number
 * Approach: Brute Force (Linear Scan)
 *
 * Idea:
 *  - Try every candidate x starting from 1 upward.
 *  - Compute x^N (using a helper that bails out early if the running
 *    product ever exceeds M, to avoid unnecessary work and overflow).
 *  - The moment we find an x such that x^N == M, return x.
 *  - If x^N ever exceeds M without hitting an exact match, no integer
 *    Nth root exists, so we can stop early and return -1.
 *
 * Time Complexity:  O(M) in the worst case (checking up to M candidates),
 *                    though in practice it's closer to O(M^(1/N) * N)
 *                    since candidates quickly exceed M for larger x.
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int nthRoot(int N, int M) {
        for (int x = 1; x <= M; x++) {
            long power = computePower(x, N, M);

            if (power == M) {
                return x;
            } else if (power > M) {
                // x^N already exceeds M, and since power grows with x,
                // no larger x can work either - stop early.
                return -1;
            }
        }
        return -1;
    }

    // Computes x^n, but stops early and returns a value > m
    // as soon as the running product exceeds m (avoids overflow
    // and unnecessary work).
    private long computePower(int x, int n, int m) {
        long result = 1;
        for (int i = 0; i < n; i++) {
            result *= x;
            if (result > m) {
                return result;
            }
        }
        return result;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.nthRoot(3, 27));  // Expected: 3
        System.out.println(solution.nthRoot(4, 69));  // Expected: -1
        System.out.println(solution.nthRoot(4, 81));  // Expected: 3
    }
}
