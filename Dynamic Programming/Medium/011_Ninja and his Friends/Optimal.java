/**
 * Problem: Ninja and his Friends
 * Approach: Optimal - Row-by-Row DP (3D State Collapsed to 2D Layers)
 *
 * Idea:
 *  - The full state is (row, AliceCol, BobCol) - a genuinely 3D DP.
 *    But since row `i`'s state only ever depends on row `i-1`'s
 *    state, we don't need to store all R layers at once - just the
 *    CURRENT layer (a C x C table: dp[j1][j2] = best total so far
 *    with Alice at column j1, Bob at column j2) and the NEXT layer
 *    being computed.
 *
 *  - Initialize the first row's layer: Alice starts at column 0,
 *    Bob starts at column c-1. dp[0][c-1] = g[0][0] + g[0][c-1]
 *    (with the c-1 term only added if c > 1, to correctly avoid
 *    double-counting when there's only 1 column and Alice/Bob start
 *    on the same cell). All other (j1,j2) combinations are marked
 *    UNREACHABLE (Integer.MIN_VALUE) for this first row, since
 *    Alice and Bob have fixed starting columns.
 *
 *  - For each subsequent row, compute a fresh `next` table: for
 *    every possible (j1, j2) pair (Alice's and Bob's columns in the
 *    CURRENT row), find the best value from the PREVIOUS row's
 *    table by checking all 3x3=9 combinations of where Alice and
 *    Bob could have come from (each could have moved -1, 0, or +1
 *    columns). If no valid previous state exists (all combinations
 *    are unreachable), this (j1,j2) pair remains unreachable too.
 *
 *  - Add the current row's chocolates: g[row][j1] + g[row][j2], but
 *    only add g[row][j2] separately if j1 != j2 (shared cell counted
 *    once, per the problem's rule).
 *
 *  - After processing all rows, the answer is the maximum value
 *    across the entire final layer's (j1,j2) table.
 *
 * Time Complexity:  O(R * C^2 * 9) = O(R * C^2) -> for each of R
 *                    rows, C^2 (j1,j2) pairs, each checking a fixed
 *                    9 combinations
 * Space Complexity: O(C^2) -> two C x C tables (dp and next) at any
 *                    given time
 */
import java.util.Arrays;

class Solution {
    public int maxChocolates(int[][] g) {
        int r = g.length;
        int c = g[0].length;

        int[][] dp = new int[c][c];

        // Mark unreachable states.
        for (int i = 0; i < c; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }

        // Alice starts at (0, 0)
        // Bob starts at (0, c - 1)
        dp[0][c - 1] = g[0][0];

        if (c > 1) {
            dp[0][c - 1] += g[0][c - 1];
        }

        for (int row = 1; row < r; row++) {
            int[][] next = new int[c][c];

            for (int i = 0; i < c; i++) {
                Arrays.fill(next[i], Integer.MIN_VALUE);
            }

            for (int j1 = 0; j1 < c; j1++) {
                for (int j2 = 0; j2 < c; j2++) {

                    int best = Integer.MIN_VALUE;

                    // Previous positions of Alice and Bob.
                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {

                            int p1 = j1 + d1;
                            int p2 = j2 + d2;

                            if (p1 < 0 || p1 >= c ||
                                p2 < 0 || p2 >= c) {
                                continue;
                            }

                            best = Math.max(best, dp[p1][p2]);
                        }
                    }

                    if (best == Integer.MIN_VALUE) {
                        continue;
                    }

                    int chocolates = g[row][j1];

                    // Count cell only once if both are on it.
                    if (j1 != j2) {
                        chocolates += g[row][j2];
                    }

                    next[j1][j2] = best + chocolates;
                }
            }

            dp = next;
        }

        int ans = 0;

        for (int j1 = 0; j1 < c; j1++) {
            for (int j2 = 0; j2 < c; j2++) {
                ans = Math.max(ans, dp[j1][j2]);
            }
        }

        return ans;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] g1 = {{2, 3, 1, 2}, {3, 4, 2, 2}, {5, 6, 3, 5}};
        System.out.println(solution.maxChocolates(g1));  // Expected: 21

        int[][] g2 = {{1, 2}, {3, 4}};
        System.out.println(solution.maxChocolates(g2));  // Expected: 10

        int[][] g3 = {{10, 1, 10}, {1, 1, 1}, {1, 1, 1}};
        System.out.println(solution.maxChocolates(g3));  // Expected: 24
    }
}
