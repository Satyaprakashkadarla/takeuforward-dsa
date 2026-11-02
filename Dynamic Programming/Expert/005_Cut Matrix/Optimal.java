/**
 * Problem: Cut Matrix (Ways to Cut a Pizza)
 * Approach: Optimal - 3D DP + 2D Suffix Sums + Binary Search
 *           + Suffix Sums of DP Values
 *
 * Idea:
 *  - After a cut only the bottom (or right) part can be cut again, so
 *    the part still to be cut is always a sub-matrix reaching the
 *    bottom-right corner, identified by its top-left corner (r, c).
 *    State: dp[r][c][p] = number of ways to cut that sub-matrix into
 *    p pieces, each containing at least one 1 (mod 10^9 + 7).
 *
 *  - suf[r][c] = number of 1s in the block from (r, c) to the
 *    bottom-right corner (a 2D suffix sum, built with the usual
 *    inclusion-exclusion). It answers "does this block contain a 1?"
 *    and "how many 1s?" in O(1).
 *
 *  - Base case p = 1: no more cutting, so dp[r][c][1] = 1 if the
 *    sub-matrix contains a 1 (suf[r][c] > 0), else 0.
 *
 *  - Transition for p >= 2, for the sub-matrix starting at (r, c):
 *
 *    HORIZONTAL cut after row i (r <= i <= n - 2):
 *      * the top strip (rows r..i) is a finished piece, so it needs at
 *        least one 1. The number of 1s in it is suf[r][c] - suf[i+1][c],
 *        which is positive exactly when suf[i+1][c] < totalOnes.
 *      * suf[i+1][c] never increases as i grows, so the valid values of
 *        i form a SUFFIX of the range [r, n-2]. A binary search finds
 *        the smallest valid i (validRow).
 *      * Each valid i continues with dp[i+1][c][p-1], so the sum over
 *        all valid i is dp[validRow+1][c][p-1] + ... + dp[n-1][c][p-1],
 *        which is exactly sufRow[validRow+1][c], a precomputed suffix
 *        sum down the column c. No loop over the cuts is needed.
 *
 *    VERTICAL cut after column j (c <= j <= m - 2): symmetric, using
 *      suf[r][j+1] < totalOnes for validity, a binary search for
 *      validCol, and sufCol[r][validCol+1], the suffix sum along row r.
 *
 *    dp[r][c][p] = (horizontal ways + vertical ways) mod 10^9 + 7
 *
 *  - Pieces are built layer by layer in p: sufRow and sufCol are
 *    rebuilt for each p from the dp values of p - 1.
 *
 *  - Answer: dp[0][0][k], for the whole matrix.
 *
 * Time Complexity:  O(k * n * m * (log n + log m))
 *                    -> for each p (k values) and each cell (n * m), two
 *                    binary searches; about 1.2 * 10^8 steps at 200^3
 *                    (a version that loops over every cut would be
 *                    O(k * n * m * (n + m)), about 3.2 * 10^9)
 * Space Complexity: O(n * m * k) -> the dp table (about 8 million ints,
 *                    roughly 32 MB for the largest input); the per-p
 *                    helper arrays add only O(n * m). It could be
 *                    reduced to O(n * m) by keeping only layers p - 1 and p.
 */
import java.util.*;

class Solution {
    public int findWays(int[][] mat, int k) {
        int n = mat.length;
        int m = mat[0].length;
        int MOD = 1000000007;

        // suf[r][c] stores the number of 1s in submatrix from (r, c) to (n-1, m-1)
        int[][] suf = new int[n + 1][m + 1];
        for (int r = n - 1; r >= 0; r--) {
            for (int c = m - 1; c >= 0; c--) {
                suf[r][c] = mat[r][c] + suf[r + 1][c] + suf[r][c + 1] - suf[r + 1][c + 1];
            }
        }

        // dp[r][c][p] = number of ways to cut submatrix (r, c) into p pieces
        int[][][] dp = new int[n][m][k + 1];

        // Base case: p = 1 piece
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                dp[r][c][1] = (suf[r][c] > 0) ? 1 : 0;
            }
        }

        for (int p = 2; p <= k; p++) {
            // Precompute suffix sums of dp values over rows and columns for piece count p - 1
            long[][] sufRow = new long[n + 1][m];
            long[][] sufCol = new long[n][m + 1];

            for (int c = 0; c < m; c++) {
                long sum = 0;
                for (int r = n - 1; r >= 0; r--) {
                    sum = (sum + dp[r][c][p - 1]) % MOD;
                    sufRow[r][c] = sum;
                }
            }

            for (int r = 0; r < n; r++) {
                long sum = 0;
                for (int c = m - 1; c >= 0; c--) {
                    sum = (sum + dp[r][c][p - 1]) % MOD;
                    sufCol[r][c] = sum;
                }
            }

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    long ways = 0;

                    // 1. Horizontal cuts: find rows i where top strip (r to i) has at least one 1
                    // Top strip has 1s if total 1s in (r, c) > total 1s in (i+1, c)
                    int totalOnes = suf[r][c];

                    // Binary search for the first row i where suf[i][c] < totalOnes
                    int low = r, high = n - 2, validRow = -1;
                    while (low <= high) {
                        int mid = low + (high - low) / 2;
                        if (suf[mid + 1][c] < totalOnes) {
                            validRow = mid; // valid cut can be at 'mid' or higher index
                            high = mid - 1;
                        } else {
                            low = mid + 1;
                        }
                    }

                    if (validRow != -1) {
                        // Sum dp[i+1][c][p-1] for all valid i from validRow to n-2
                        // This corresponds to range sum in sufRow[validRow + 1][c]
                        ways = (ways + sufRow[validRow + 1][c]) % MOD;
                    }

                    // 2. Vertical cuts: find cols j where left strip (c to j) has at least one 1
                    low = c; high = m - 2; int validCol = -1;
                    while (low <= high) {
                        int mid = low + (high - low) / 2;
                        if (suf[r][mid + 1] < totalOnes) {
                            validCol = mid;
                            high = mid - 1;
                        } else {
                            low = mid + 1;
                        }
                    }

                    if (validCol != -1) {
                        ways = (ways + sufCol[r][validCol + 1]) % MOD;
                    }

                    dp[r][c][p] = (int) ways;
                }
            }
        }

        return dp[0][0][k];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.findWays(new int[][]{{1, 0, 0}, {1, 1, 1}, {0, 0, 0}}, 3));  // Expected: 3
        System.out.println(solution.findWays(new int[][]{{0, 0}, {1, 1}}, 2));                    // Expected: 1
        System.out.println(solution.findWays(new int[][]{{1, 0}, {0, 0}}, 1));                    // Expected: 1
    }
}
