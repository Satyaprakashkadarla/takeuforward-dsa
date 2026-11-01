/**
 * Problem: Cut Matrix (Ways to Cut a Pizza)
 * Approach: Brute Force (Plain Recursion Over Every Sequence of Cuts)
 *
 * Idea:
 *  - After every cut, only the BOTTOM part (horizontal cut) or the
 *    RIGHT part (vertical cut) can be cut again. The other part is a
 *    finished piece. So the piece still to be cut is always a
 *    sub-matrix that extends to the bottom-right corner, described by
 *    its top-left corner (r, c).
 *
 *  - Define f(r, c, p) = the number of ways to cut the sub-matrix
 *    starting at (r, c) into p pieces, each containing at least one 1.
 *      * p == 1: no more cutting, so there is exactly 1 way if the
 *        sub-matrix contains a 1, otherwise 0.
 *      * p > 1: try every horizontal cut after row i (r <= i <= n-2).
 *        The top strip (rows r..i) becomes a finished piece, so it
 *        must contain a 1; then continue with f(i + 1, c, p - 1).
 *        Likewise for every vertical cut after column j (c <= j <= m-2):
 *        the left strip (columns c..j) must contain a 1, and we
 *        continue with f(r, j + 1, p - 1).
 *
 *  - Whether a strip contains a 1 is checked by scanning it cell by
 *    cell, and nothing is cached, so the same (r, c, p) is solved
 *    again and again.
 *
 *  - The result is returned modulo 10^9 + 7 (the counts grow quickly).
 *
 * Time Complexity:  Exponential, roughly O((n + m)^k) calls, each also
 *                    paying a scan of up to n * m cells
 * Space Complexity: O(k) -> recursion depth is the number of pieces
 *
 * Only usable for tiny inputs; it is a correctness baseline.
 */
public class Bruteforce {

    private static final int MOD = 1_000_000_007;

    public int findWays(int[][] mat, int k) {
        return (int) (solve(mat, 0, 0, k) % MOD);
    }

    private long solve(int[][] mat, int r, int c, int p) {
        int n = mat.length;
        int m = mat[0].length;

        if (p == 1) {
            return hasOne(mat, r, c, n, m) ? 1 : 0;
        }

        long ways = 0;

        // Horizontal cuts: after row i, the top strip is rows r..i
        for (int i = r; i <= n - 2; i++) {
            if (hasOne(mat, r, c, i + 1, m)) {
                ways = (ways + solve(mat, i + 1, c, p - 1)) % MOD;
            }
        }

        // Vertical cuts: after column j, the left strip is columns c..j
        for (int j = c; j <= m - 2; j++) {
            if (hasOne(mat, r, c, n, j + 1)) {
                ways = (ways + solve(mat, r, j + 1, p - 1)) % MOD;
            }
        }

        return ways;
    }

    // Does the block with rows r1..r2-1 and columns c1..c2-1 contain a 1?
    private boolean hasOne(int[][] mat, int r1, int c1, int r2, int c2) {
        for (int i = r1; i < r2; i++) {
            for (int j = c1; j < c2; j++) {
                if (mat[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.findWays(new int[][]{{1, 0, 0}, {1, 1, 1}, {0, 0, 0}}, 3));  // Expected: 3
        System.out.println(solution.findWays(new int[][]{{0, 0}, {1, 1}}, 2));                    // Expected: 1
        System.out.println(solution.findWays(new int[][]{{1, 0}, {0, 0}}, 1));                    // Expected: 1
    }
}
