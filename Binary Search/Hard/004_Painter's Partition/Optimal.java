/**
 * Problem: Painter's Partition
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - First, binary search for the minimum possible "max sum" any
 *    single painter's contiguous segment could have, given A
 *    painters. This lies between:
 *      low  = the largest single board (a painter must handle that
 *             board alone, so the answer can never be smaller)
 *      high = the sum of all board lengths (one painter handles
 *             everything, trivially satisfying A >= 1)
 *
 *  - As the candidate max-length-sum increases, fewer painters are
 *    needed to cover all boards (monotonic relationship) - this is
 *    the same "minimize the maximum" pattern as Split Array Largest
 *    Sum, Ship Packages Within D Days, and Book Allocation.
 *
 *  - If there are more painters than boards (A > C.length), the
 *    extra painters are simply unused - clamp A down to C.length so
 *    the feasibility check behaves correctly.
 *
 *  - canPaint() greedily assigns boards to the current painter IN
 *    ORDER: keep adding to the running length sum as long as it
 *    doesn't exceed the candidate limit; the moment adding the next
 *    board WOULD exceed the limit, move to a new painter.
 *
 *  - Standard binary search narrows down to the smallest feasible
 *    max-length-sum (using "high = mid" / "low = mid + 1" style,
 *    converging when low == high).
 *
 *  - FINAL STEP (the twist specific to this problem): once we know
 *    the minimized max-length-sum (`low`), the actual painting TIME
 *    is that sum multiplied by B (time per unit length). Since the
 *    result could be large, everything is done with `long` and the
 *    final result is taken modulo 10000003 as required by the
 *    problem statement.
 *
 * Time Complexity:  O(N log(sum(C))) -> binary search does
 *                    O(log(sum(C))) iterations, each doing an O(N)
 *                    greedy check
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int paint(int A, int B, int[] C) {
        final long MOD = 10000003L;

        long low = 0;
        long high = 0;

        // Minimum possible = largest board
        // Maximum possible = sum of all boards
        for (int len : C) {
            low = Math.max(low, len);
            high += len;
        }

        // If there are more painters than boards,
        // extra painters simply remain unused.
        A = Math.min(A, C.length);

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (canPaint(C, A, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return (int) ((low % MOD) * (B % MOD) % MOD);
    }

    private boolean canPaint(int[] C, int painters, long limit) {
        int used = 1;
        long current = 0;

        for (int len : C) {
            if (current + len <= limit) {
                current += len;
            } else {
                used++;
                current = len;

                if (used > painters) {
                    return false;
                }
            }
        }

        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] c1 = {1, 10};
        System.out.println(solution.paint(2, 5, c1));   // Expected: 50

        int[] c2 = {1, 8, 11, 3};
        System.out.println(solution.paint(10, 1, c2));  // Expected: 11

        int[] c3 = {5, 10, 30, 20};
        System.out.println(solution.paint(3, 2, c3));   // Expected: 60
    }
}
