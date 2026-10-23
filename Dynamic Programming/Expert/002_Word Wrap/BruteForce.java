/**
 * Problem: Word Wrap
 * Approach: Brute Force (Plain Recursion Over Every Line Break)
 *
 * Idea:
 *  - Define f(i) = minimum cost to lay out words i .. n-1, given that
 *    a NEW line starts at word i.
 *  - Try every possible last word j of the line that starts at i.
 *    Words i..j fit on one line only if
 *        (sum of lengths) + (j - i single spaces) <= k
 *    As soon as the line no longer fits, stop (adding more words only
 *    makes it longer).
 *  - The line costs (k - used)^2, except the LAST line (the one that
 *    ends at word n-1), which costs 0.
 *  - Then pay for the rest of the text:
 *        f(i) = min over fitting j of ( cost(i, j) + f(j + 1) )
 *    with f(n) = 0 (nothing left to place).
 *  - Without memoization the same suffix f(j + 1) is recomputed from
 *    many different earlier break choices, so the work is exponential.
 *
 * Time Complexity:  O(2^n) -> in the worst case every gap between words
 *                    can be a line break or not, and nothing is cached
 * Space Complexity: O(n)   -> maximum recursion depth is the number of lines
 *
 * Only practical for small n; it is a correctness baseline.
 */
public class Bruteforce {

    public int solveWordWrap(int[] arr, int k) {
        return solve(arr, k, 0);
    }

    private int solve(int[] arr, int k, int i) {
        int n = arr.length;

        if (i == n) {
            return 0;
        }

        int best = Integer.MAX_VALUE;
        int lineLength = 0;

        for (int j = i; j < n; j++) {
            lineLength += arr[j];
            int used = lineLength + (j - i); // single spaces between words

            if (used > k) {
                break; // adding more words only makes the line longer
            }

            int cost = (j == n - 1) ? 0 : (k - used) * (k - used);

            best = Math.min(best, cost + solve(arr, k, j + 1));
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.solveWordWrap(new int[]{3, 2, 2, 5}, 6));  // Expected: 10
        System.out.println(solution.solveWordWrap(new int[]{3, 2, 2}, 4));     // Expected: 5
    }
}
