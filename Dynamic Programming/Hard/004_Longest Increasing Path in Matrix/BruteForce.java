/**
 * Problem: Longest Increasing Path in Matrix
 * Approach: Brute Force (Plain DFS From Every Cell, No Memoization)
 *
 * Idea:
 *  - Define dfs(i, j) = length of the longest strictly increasing
 *    path that STARTS at cell (i, j).
 *  - From (i, j), try each of the 4 neighbors; if a neighbor's value
 *    is strictly greater than matrix[i][j], we can step onto it, so
 *        dfs(i, j) = 1 + max( dfs(neighbor) ) over such neighbors
 *    (or just 1 if no neighbor is larger).
 *  - The answer is the max of dfs(i, j) over every starting cell.
 *  - No visited array is needed: because values must be STRICTLY
 *    increasing, a path can never return to a cell it already
 *    visited, so cycles are impossible.
 *  - Without memoization, the same cell's longest path gets
 *    recomputed again and again every time a different path reaches
 *    it, which blows up exponentially on grids with many increasing
 *    routes (for example, a grid that increases row by row and
 *    column by column).
 *
 * Time Complexity:  Exponential in the worst case (no caching of
 *                    repeated subproblems)
 * Space Complexity: O(n * m) -> worst-case recursion depth, when the
 *                    path snakes through every cell
 *
 * Warning: for large grids (up to 1000 x 1000) this is both far too
 * slow and at risk of StackOverflowError. It is included as a
 * correctness baseline for small inputs.
 */
public class Bruteforce {

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public int longIncPath(int[][] matrix, int n, int m) {
        int best = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                best = Math.max(best, dfs(matrix, n, m, i, j));
            }
        }

        return best;
    }

    private int dfs(int[][] matrix, int n, int m, int i, int j) {
        int best = 1; // the cell itself

        for (int d = 0; d < 4; d++) {
            int x = i + dx[d];
            int y = j + dy[d];

            if (x >= 0 && x < n && y >= 0 && y < m
                    && matrix[x][y] > matrix[i][j]) {
                best = Math.max(best, 1 + dfs(matrix, n, m, x, y));
            }
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] m1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(solution.longIncPath(m1, 3, 3));  // Expected: 5

        int[][] m2 = {{3, 4, 5}, {6, 2, 6}, {2, 2, 1}};
        System.out.println(solution.longIncPath(m2, 3, 3));  // Expected: 4

        int[][] m3 = {{1, 1}, {1, 1}};
        System.out.println(solution.longIncPath(m3, 2, 2));  // Expected: 1
    }
}
