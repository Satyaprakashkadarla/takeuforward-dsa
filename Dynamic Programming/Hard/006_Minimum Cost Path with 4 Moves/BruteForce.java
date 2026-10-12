/**
 * Problem: Minimum Cost Path with 4 Moves
 * Approach: Brute Force (DFS / Backtracking Over Every Simple Path)
 *
 * Idea:
 *  - Start at (0, 0) with cost grid[0][0] and explore every path to
 *    (n-1, n-1) by trying all 4 directions from each cell.
 *  - Because moves can go up and left as well, a path could wander in
 *    circles forever. To prevent that, keep a `visited` array and mark
 *    a cell while it is on the current path, then UNMARK it when
 *    backtracking, so other paths may still use it.
 *  - Whenever the destination is reached, record the path cost if it
 *    is the smallest seen so far.
 *  - A shortest path in a positive-weight graph never needs to revisit
 *    a cell, so restricting to simple paths loses nothing.
 *  - The number of simple paths on a grid grows exponentially with n,
 *    so this is only usable as a correctness baseline on tiny grids.
 *
 * Time Complexity:  Exponential (the number of simple paths in a grid
 *                    graph grows exponentially)
 * Space Complexity: O(n^2) -> visited array plus recursion depth,
 *                    which can reach n^2
 */
public class Bruteforce {

    private int best;
    private boolean[][] visited;
    private final int[] dr = {-1, 1, 0, 0};
    private final int[] dc = {0, 0, -1, 1};

    public int minimumCostPath(int[][] grid) {
        int n = grid.length;
        best = Integer.MAX_VALUE;
        visited = new boolean[n][n];

        visited[0][0] = true;
        dfs(grid, n, 0, 0, grid[0][0]);

        return best;
    }

    private void dfs(int[][] grid, int n, int r, int c, int cost) {
        if (r == n - 1 && c == n - 1) {
            best = Math.min(best, cost);
            return;
        }

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                visited[nr][nc] = true;                      // choose
                dfs(grid, n, nr, nc, cost + grid[nr][nc]);   // explore
                visited[nr][nc] = false;                     // un-choose (backtrack)
            }
        }
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] g1 = {{9, 4, 9, 9}, {6, 7, 6, 4}, {8, 3, 3, 7}, {7, 4, 9, 10}};
        System.out.println(solution.minimumCostPath(g1));  // Expected: 43

        int[][] g2 = {{4, 4}, {3, 7}};
        System.out.println(solution.minimumCostPath(g2));  // Expected: 14
    }
}
