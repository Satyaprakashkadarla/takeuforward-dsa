/**
 * Problem: Two Robots Collecting Chocolates (Cherry Pickup II)
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(row, c1, c2) = the maximum chocolates collectable from
 *    `row` downward, given robot 1 stands at column c1 and robot 2
 *    stands at column c2 in the CURRENT row.
 *  - Collect this row's chocolates first: grid[row][c1], plus
 *    grid[row][c2] only if c1 != c2 (a shared cell counts once).
 *  - If this is the last row, that is the whole contribution.
 *  - Otherwise each robot independently moves to column -1, 0 or +1
 *    in the next row (staying inside the grid), which gives up to
 *    3 x 3 = 9 combinations. Recurse on each and keep the best:
 *        f(row, c1, c2) = collected + max over valid (n1, n2) of
 *                         f(row + 1, n1, n2)
 *  - Start from f(0, 0, m - 1).
 *  - Without memoization the same (row, c1, c2) state is recomputed
 *    again and again through different move sequences, giving
 *    exponential time.
 *
 * Time Complexity:  O(9^n) -> 9 branches per row, n rows, no caching
 * Space Complexity: O(n)   -> maximum recursion depth is the number
 *                    of rows
 *
 * Only practical for tiny grids; it is a correctness baseline.
 */
public class Bruteforce {

    public int maxChocolate(int[][] grid) {
        int m = grid[0].length;
        return solve(grid, 0, 0, m - 1);
    }

    private int solve(int[][] grid, int row, int c1, int c2) {
        int n = grid.length;
        int m = grid[0].length;

        int collected = grid[row][c1];
        if (c1 != c2) {
            collected += grid[row][c2];
        }

        if (row == n - 1) {
            return collected;
        }

        int best = 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                int n1 = c1 + d1;
                int n2 = c2 + d2;

                if (n1 >= 0 && n1 < m && n2 >= 0 && n2 < m) {
                    best = Math.max(best, solve(grid, row + 1, n1, n2));
                }
            }
        }

        return collected + best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] g = {{4, 1, 2}, {3, 6, 1}, {1, 6, 6}, {3, 1, 2}};
        System.out.println(solution.maxChocolate(g));  // Expected: 32
    }
}
