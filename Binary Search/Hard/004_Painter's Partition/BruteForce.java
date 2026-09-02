/**
 * Problem: Painter's Partition
 * Approach: Brute Force (Linear Scan Over Possible Max-Length-Sums)
 *
 * Idea:
 *  - First, find the minimum possible "max sum" achievable for any
 *    single painter's assigned segment: this lies between the
 *    largest single board (a painter must handle that board alone,
 *    at minimum) and the sum of all board lengths (one painter
 *    handles everything).
 *  - Try every candidate max-length-sum starting from the largest
 *    board upward, greedily checking how many painters would be
 *    needed to keep every painter's assigned segment sum at or below
 *    that candidate.
 *  - The FIRST candidate for which the required painter count is
 *    <= A is the minimum achievable max-length-sum.
 *  - Finally, multiply that minimized sum by B (time per unit
 *    length) to get the actual painting time, then apply the
 *    required modulo.
 *
 * Time Complexity:  O(sum(C) * N) -> up to sum(C) candidate sums,
 *                    each requiring an O(N) greedy check
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    private static final long MOD = 10000003L;

    public int paint(int A, int B, int[] C) {
        long maxBoard = 0;
        long totalLength = 0;
        for (int len : C) {
            maxBoard = Math.max(maxBoard, len);
            totalLength += len;
        }

        A = Math.min(A, C.length);

        long minMaxSum = totalLength;
        for (long candidate = maxBoard; candidate <= totalLength; candidate++) {
            if (canPaint(C, A, candidate)) {
                minMaxSum = candidate;
                break;
            }
        }

        return (int) ((minMaxSum % MOD) * (B % MOD) % MOD);
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
        Bruteforce solution = new Bruteforce();

        int[] c1 = {1, 10};
        System.out.println(solution.paint(2, 5, c1));   // Expected: 50

        int[] c2 = {1, 8, 11, 3};
        System.out.println(solution.paint(10, 1, c2));  // Expected: 11

        int[] c3 = {5, 10, 30, 20};
        System.out.println(solution.paint(3, 2, c3));   // Expected: 60
    }
}
