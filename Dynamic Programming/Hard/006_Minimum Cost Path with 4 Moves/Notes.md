# Notes: Minimum Cost Path with 4 Moves

## 1. Problem Recap

Find the cheapest path from the top-left to the bottom-right of an `n x n` grid, moving up, down, left or right. The path cost is the sum of the costs of every cell visited, including the start and end.

```
grid = [[9,  4,  9,  9],
        [6,  7,  6,  4],
        [8,  3,  3,  7],
        [7,  4,  9, 10]]

Cheapest path: 9 -> 4 -> 7 -> 3 -> 3 -> 7 -> 10   (total 43)
```

### Why This Is Not a Plain Grid DP
In earlier problems (Unique Paths, Minimum Path Sum) you could only move **right or down**. Then every cell depends only on the cell above and the cell to its left, so one top-left to bottom-right sweep is enough.

Here you can also move **up and left**. The cheapest way into a cell might come from the cell below or to the right of it, and those cells in turn might be reached through this one. The dependencies form **cycles**, so no fixed fill order works. This is a shortest-path problem on a graph.

### The Graph Model
- **Node:** each cell.
- **Edge:** from a cell to each of its 4 neighbors.
- **Edge weight:** the cost of the cell you step **onto**.
- **Start:** the cost of the first cell is paid up front (`dist[0][0] = grid[0][0]`).

All weights are positive (`1 <= cost <= 500`), which is exactly the requirement for Dijkstra's algorithm.

---

## 2. Approach 1: Brute Force (DFS Over Every Simple Path)

### Idea
Try every route from the start to the destination, with a `visited` array so a path never loops, and keep the minimum cost found.

### Code Logic
```java
private void dfs(int[][] grid, int n, int r, int c, int cost) {
    if (r == n - 1 && c == n - 1) {
        best = Math.min(best, cost);
        return;
    }
    for (int d = 0; d < 4; d++) {
        int nr = r + dr[d], nc = c + dc[d];
        if (inBounds(nr, nc) && !visited[nr][nc]) {
            visited[nr][nc] = true;                    // choose
            dfs(grid, n, nr, nc, cost + grid[nr][nc]); // explore
            visited[nr][nc] = false;                   // backtrack
        }
    }
}
```

Un-marking a cell when backtracking is essential: it lets other paths pass through the same cell.

### Dry Run
`grid = [[4, 4], [3, 7]]`

Two simple paths exist from (0,0) to (1,1):

| Path | Cost |
|---|---|
| (0,0) -> (0,1) -> (1,1) | 4 + 4 + 7 = 15 |
| (0,0) -> (1,0) -> (1,1) | 4 + 3 + 7 = 14 |

Best = **14** ✅

### Complexity
- **Time:** Exponential. The number of simple paths across a grid explodes as `n` grows, so a 500 x 500 grid is hopeless.
- **Space:** O(n^2) for the `visited` array and the recursion depth.

---

## 3. A Middle Ground: Repeated Relaxation (Bellman-Ford Style)

Keep a `dist` table. Keep sweeping over all cells, updating `dist[r][c] = min(dist[r][c], dist[neighbor] + grid[r][c])`, until a full sweep changes nothing. It is correct, but on a grid it can need up to about `n^2` sweeps of `n^2` cells each, so O(n^4) in the worst case. Dijkstra avoids this by always finalizing the cheapest unfinished cell first, so each cell is settled essentially once.

---

## 4. Approach 2: Optimal (Dijkstra with a Min-Heap)

### Idea
1. `dist[r][c]` holds the cheapest known cost to reach `(r, c)`. Start it at `Integer.MAX_VALUE` everywhere except `dist[0][0] = grid[0][0]`.
2. A min-heap holds `{cost, row, col}`. Always take the entry with the smallest cost.
3. For each of its 4 neighbors, compute `newCost = cost + grid[neighbor]`. If this beats `dist[neighbor]`, update it and push the neighbor.
4. The first time the destination is taken from the heap, its cost is final, so return it.

### Why Dijkstra Is Correct Here
When the smallest-cost unfinished cell is popped, no other route can reach it more cheaply: any other route must go through some unfinished cell that already costs at least as much, and every further step adds a **positive** cost. This is exactly why positive weights matter.

### The Stale-Entry Check
```java
if (cost != dist[r][c]) continue;
```
A cell may be pushed more than once as cheaper routes are discovered. Java's `PriorityQueue` has no efficient "decrease key", so the old, more expensive entry stays in the heap. When it is eventually popped, its cost no longer equals `dist[r][c]`, and it is skipped.

### Dry Run 1: Example 1
Order in which cells are finalized (popped), with their final distances:

| Step | Cell (row, col) | Cell cost | Final dist |
|---|---|---|---|
| 1 | (0,0) | 9 | 9 |
| 2 | (0,1) | 4 | 13 |
| 3 | (1,0) | 6 | 15 |
| 4 | (1,1) | 7 | 20 |
| 5 | (0,2) | 9 | 22 |
| 6 | (2,0) | 8 | 23 |
| 7 | (2,1) | 3 | 23 |
| 8 | (1,2) | 6 | 26 |
| 9 | (2,2) | 3 | 26 |
| 10 | (3,1) | 4 | 27 |
| 11 | (1,3) | 4 | 30 |
| 12 | (3,0) | 7 | 30 |
| 13 | (0,3) | 9 | 31 |
| 14 | (2,3) | 7 | 33 |
| 15 | (3,2) | 9 | 35 |
| 16 | (3,3) | 10 | **43** |

Final distance table:

| | col 0 | col 1 | col 2 | col 3 |
|---|---|---|---|---|
| row 0 | 9 | 13 | 22 | 31 |
| row 1 | 15 | 20 | 26 | 30 |
| row 2 | 23 | 23 | 26 | 33 |
| row 3 | 30 | 27 | 35 | 43 |

The destination is popped with cost **43** and returned. Following the cheapest route back through the table gives (0,0)=9, (0,1)=13, (1,1)=20, (2,1)=23, (2,2)=26, (2,3)=33, (3,3)=43, which is `9 + 4 + 7 + 3 + 3 + 7 + 10`.

Result: **43** ✅ (matches expected output)

Notice that the algorithm never needed to go up or left here, but it could have: for example, (3,0) is reached with cost 30 even though it is not on the best path. On other grids the cheapest route really does double back.

### Dry Run 2: Example 2
`grid = [[4, 4], [3, 7]]`

| Step | Pop (cost, r, c) | Relaxations | Heap afterward |
|---|---|---|---|
| start | | `dist[0][0] = 4`, push (4,0,0) | (4,0,0) |
| 1 | (4,0,0) | neighbor (1,0): 4+3 = 7 < MAX, push (7,1,0). Neighbor (0,1): 4+4 = 8 < MAX, push (8,0,1) | (7,1,0), (8,0,1) |
| 2 | (7,1,0) | neighbor (0,0): 7+4 = 11, not < 4, skip. Neighbor (1,1): 7+7 = 14 < MAX, push (14,1,1) | (8,0,1), (14,1,1) |
| 3 | (8,0,1) | neighbor (0,0): 8+4 = 12, skip. Neighbor (1,1): 8+7 = 15, not < 14, skip | (14,1,1) |
| 4 | (14,1,1) | this is the destination, return **14** | |

Result: **14** ✅ (matches expected output)

### Complexity
- **Time:** O(n^2 log n). There are n^2 cells with up to 4 edges each, so at most about 4n^2 pushes, and each heap operation costs O(log(n^2)) = O(log n). For n = 500 that is roughly 250,000 cells, which is fast.
- **Space:** O(n^2) for `dist` and the heap.

---

## 5. Comparing the Approaches

| Aspect | Brute Force DFS | Repeated Relaxation | Dijkstra (Optimal) |
|---|---|---|---|
| Time | Exponential | O(n^4) worst case | O(n^2 log n) |
| Space | O(n^2) | O(n^2) | O(n^2) |
| Handles n = 500? | No | Too slow | Yes |

---

## 6. Implementation Notes on the Provided Code

- **`dist[0][0] = grid[0][0]`:** the cost of the start cell counts, which is why Example 2's answer is `4 + 3 + 7`, not `3 + 7`.
- **Edge weight is the destination cell's cost:** `newCost = cost + grid[nr][nc]`. Each cell's cost is therefore added exactly once per visit.
- **Overflow:** `dist` starts at `Integer.MAX_VALUE`, but it is only ever compared against, never added to. The additions happen on `cost`, which is always a real finite value, so there is no overflow. A path has at most 250,000 cells at cost 500, which is 1.25 x 10^8, comfortably inside `int`.
- **Comparator:** `Integer.compare(a[0], b[0])` avoids the overflow bug that `a[0] - b[0]` can have.
- **Early exit:** returning as soon as the destination is popped avoids exploring the rest of the grid. The final `return dist[n - 1][n - 1];` is just a safety fallback, since the grid is always connected.

---

## 7. Edge Cases to Consider

1. **1 x 1 grid:** the start is the destination, so the answer is `grid[0][0]`. The first poll returns immediately.
2. **All cells have the same cost `k`:** the answer is `k * (2n - 1)`, the cost of a shortest-length path.
3. **A very expensive wall between start and end with a cheap detour:** this is where moving up and left matters, and why a right/down-only DP would give a wrong answer.
4. **Costs are all positive:** if zero or negative costs were allowed, the "first pop is final" argument would break (negative weights would need Bellman-Ford).
5. **Large grid (n = 500):** about 250,000 cells; the heap holds at most a few hundred thousand entries.

---

## 8. Related Concepts / Follow-Ups

- **Minimum Path Sum** (right/down only): a plain grid DP. Compare it with this problem to see exactly when Dijkstra becomes necessary.
- **Path With Minimum Effort** (LeetCode 1631): Dijkstra on a grid, but the path cost is the **maximum** absolute difference along the path, not the sum.
- **Network Delay Time / Cheapest Flights Within K Stops:** Dijkstra and Bellman-Ford on general graphs.
- **0-1 BFS:** a faster variant when edge weights are only 0 or 1.
- **A\* search:** Dijkstra plus a heuristic, useful when you have a good estimate of the remaining distance (like Manhattan distance on a grid).

---

## 9. Key Takeaways

- If movement can go in all 4 directions, the "cheapest way into a cell" can come from any neighbor, so a simple DP sweep fails. Think graph and shortest path.
- Positive weights plus "finalize the cheapest unfinished node first" is Dijkstra's algorithm.
- Pushing duplicate heap entries and skipping stale ones with `cost != dist[r][c]` is the standard way to implement Dijkstra with Java's `PriorityQueue`.
- Dijkstra's O(n^2 log n) is dramatically better than the exponential brute force and the O(n^4) repeated-relaxation approach.
