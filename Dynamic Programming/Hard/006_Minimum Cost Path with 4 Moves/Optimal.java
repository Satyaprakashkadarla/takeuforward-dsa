/**
 * Problem: Minimum Cost Path with 4 Moves
 * Approach: Optimal - Dijkstra's Algorithm with a Min-Heap
 *
 * Idea:
 *  - Model the grid as a weighted graph. Every cell is a node. From a
 *    cell you can step to each of its 4 neighbors, and the weight of
 *    that edge is the cost of the cell you step ONTO. The start cell's
 *    own cost is added up front (dist[0][0] = grid[0][0]).
 *
 *  - Moves can go up and left too, so the cheapest way into a cell can
 *    come from ANY neighbor. That makes the dependencies circular, and
 *    a simple row-by-row DP cannot work. All costs are positive,
 *    which is exactly what Dijkstra's algorithm needs.
 *
 *  - dist[r][c] = cheapest known total cost to reach (r, c). It starts
 *    at Integer.MAX_VALUE ("unreached") everywhere except the start.
 *
 *  - The priority queue always hands back the unfinished cell with the
 *    SMALLEST known cost, stored as {cost, row, col}.
 *
 *  - Stale entries: a cell can be pushed several times as cheaper
 *    routes are found. When an older, more expensive entry is polled,
 *    `cost != dist[r][c]` detects that it is outdated and skips it.
 *
 *  - Early exit: the first time the destination (n-1, n-1) is polled,
 *    its cost is final (every other unfinished route is at least as
 *    expensive), so it is returned immediately.
 *
 *  - Relaxation: for each neighbor, if going through the current cell
 *    gives a cheaper total (newCost < dist[nr][nc]), update dist and
 *    push the neighbor.
 *
 * Time Complexity:  O(n^2 log n) -> there are n^2 cells, each with up
 *                    to 4 edges, and each heap operation costs
 *                    O(log(n^2)) = O(log n)
 * Space Complexity: O(n^2) -> the dist table plus the priority queue
 */
import java.util.*;

class Solution {
    public int minimumCostPath(int[][] grid) {
        int n = grid.length;
        int[][] dist = new int[n][n];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        dist[0][0] = grid[0][0];
        pq.offer(new int[]{grid[0][0], 0, 0});

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int cost = curr[0];
            int r = curr[1];
            int c = curr[2];

            if (cost != dist[r][c]) {
                continue;
            }

            if (r == n - 1 && c == n - 1) {
                return cost;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    int newCost = cost + grid[nr][nc];

                    if (newCost < dist[nr][nc]) {
                        dist[nr][nc] = newCost;
                        pq.offer(new int[]{newCost, nr, nc});
                    }
                }
            }
        }

        return dist[n - 1][n - 1];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] g1 = {{9, 4, 9, 9}, {6, 7, 6, 4}, {8, 3, 3, 7}, {7, 4, 9, 10}};
        System.out.println(solution.minimumCostPath(g1));  // Expected: 43

        int[][] g2 = {{4, 4}, {3, 7}};
        System.out.println(solution.minimumCostPath(g2));  // Expected: 14
    }
}
