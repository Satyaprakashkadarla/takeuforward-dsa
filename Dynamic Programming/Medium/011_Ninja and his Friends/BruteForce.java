/**
 * Problem: Ninja and his Friends
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(row, j1, j2) = maximum chocolates collectible from
 *    `row` onward, given Alice is at column j1 and Bob is at column
 *    j2 in the CURRENT row.
 *  - Collect the current row's chocolates: g[row][j1] + g[row][j2],
 *    but only count g[row][j2] if j1 != j2 (shared cell counted once).
 *  - If this is the last row, that's the final contribution.
 *  - Otherwise, try all 3x3 = 9 combinations of Alice's and Bob's
 *    next-row moves (each can go to column-1, column, or column+1,
 *    provided still in bounds), and recurse, adding the best
 *    possible continuation.
 *  - Without memoization, this recomputes the same (row,j1,j2)
 *    states repeatedly across different paths, causing exponential
 *    blowup.
 *
 * Time Complexity:  O(3^(2R)) -> at each of R rows, up to 3 choices
 *                    for Alice AND up to 3 choices for Bob (9 total
 *                    branches per row), with no caching
 * Space Complexity: O(R)      -> maximum recursion stack depth
 */
public class Bruteforce {

    public int maxChocolates(int[][] g) {
        int c = g[0].length;
        return solve(g, 0, 0, c - 1);
    }

    private int solve(int[][] g, int row, int j1, int j2) {
        int r = g.length;
        int c = g[0].length;

        int chocolates = g[row][j1];
        if (j1 != j2) {
            chocolates += g[row][j2];
        }

        if (row == r - 1) {
            return chocolates;
        }

        int best = 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                int nj1 = j1 + d1;
                int nj2 = j2 + d2;
                if (nj1 < 0 || nj1 >= c || nj2 < 0 || nj2 >= c) {
                    continue;
                }
                best = Math.max(best, solve(g, row + 1, nj1, nj2));
            }
        }

        return chocolates + best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] g1 = {{2, 3, 1, 2}, {3, 4, 2, 2}, {5, 6, 3, 5}};
        System.out.println(solution.maxChocolates(g1));  // Expected: 21

        int[][] g2 = {{1, 2}, {3, 4}};
        System.out.println(solution.maxChocolates(g2));  // Expected: 10

        int[][] g3 = {{10, 1, 10}, {1, 1, 1}, {1, 1, 1}};
        System.out.println(solution.maxChocolates(g3));  // Expected: 24
    }
}
