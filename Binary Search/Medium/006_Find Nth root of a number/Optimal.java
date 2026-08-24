/**
 * Problem: Find Nth Root of a Number
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - Binary search over the RANGE OF POSSIBLE ANSWERS: any candidate
 *    Nth root X must lie somewhere between 1 and M (inclusive).
 *  - For a candidate mid, use comparePower(mid, N, M) to determine
 *    how mid^N compares to M:
 *      -1 -> mid^N <  M  (mid is too small, search right)
 *       0 -> mid^N == M  (found the exact Nth root!)
 *       1 -> mid^N >  M  (mid is too large, search left)
 *  - comparePower computes mid^N incrementally, multiplying one
 *    factor at a time, and BAILS OUT EARLY the moment the running
 *    result exceeds M. This avoids both unnecessary computation and
 *    integer overflow, since mid^N could otherwise become astronomically
 *    large for bigger N (up to 30) before we'd ever notice it exceeded M.
 *  - If no exact match is found by the time low > high, no integer
 *    Nth root exists, so we return -1.
 *
 * Time Complexity:  O(N log M) -> binary search does O(log M) iterations,
 *                    and each comparePower call does up to O(N) multiplications
 * Space Complexity: O(1)       -> iterative, no extra space
 */
class Solution {
    public int NthRoot(int N, int M) {
        int low = 1, high = M;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int cmp = comparePower(mid, N, M);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    // Returns:
    // -1 if x^n < m
    //  0 if x^n == m
    //  1 if x^n > m
    private int comparePower(int x, int n, int m) {
        long result = 1;

        for (int i = 0; i < n; i++) {
            result *= x;

            if (result > m) {
                return 1;
            }
        }

        return Long.compare(result, m);
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.NthRoot(3, 27));  // Expected: 3
        System.out.println(solution.NthRoot(4, 69));  // Expected: -1
        System.out.println(solution.NthRoot(4, 81));  // Expected: 3
    }
}
