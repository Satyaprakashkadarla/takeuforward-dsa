# Notes: Longest Increasing Path in Matrix

## 1. Problem Recap

Find the length of the longest path through a grid where each step moves up, down, left, or right onto a cell with a **strictly larger** value. The path may start and end anywhere.

```
matrix = [[3, 4, 5],
          [6, 2, 6],
          [2, 2, 1]]
Longest path: 3 -> 4 -> 5 -> 6   (length 4)
```

### The Key Insight: The Graph Is a DAG
Draw a directed edge from each cell to every neighbor with a strictly larger value. Along any edge the value goes up, so you can never return to a cell you already left. That means:

- There are **no cycles**, so no `visited` array is ever needed.
- The problem is exactly "longest path in a DAG," which has two classic solutions: **DFS + memoization** and **topological sort**.

---

## 2. Approach 1: Brute Force (Plain DFS From Every Cell)

### Idea
`dfs(i, j)` returns the longest increasing path **starting** at `(i, j)`. Try each larger neighbor and take the best.

### Code Logic
```java
private int dfs(int[][] matrix, int n, int m, int i, int j) {
    int best = 1;
    for (int d = 0; d < 4; d++) {
        int x = i + dx[d], y = j + dy[d];
        if (inBounds(x, y) && matrix[x][y] > matrix[i][j]) {
            best = Math.max(best, 1 + dfs(matrix, n, m, x, y));
        }
    }
    return best;
}
// answer = max over all (i, j) of dfs(i, j)
```

### Dry Run
`matrix = [[1,2,3],[4,5,6],[7,8,9]]`, computing `dfs(0,0)` (value 1):

```
dfs(0,0)=1 : larger neighbors are (0,1)=2 and (1,0)=4
  dfs(0,1)=2 : larger neighbors (0,2)=3 and (1,1)=5
    dfs(0,2)=3 : larger neighbor (1,2)=6
      dfs(1,2)=6 : larger neighbor (2,2)=9
        dfs(2,2)=9 : none -> 1
      dfs(1,2) = 1 + 1 = 2
    dfs(0,2) = 1 + 2 = 3
    dfs(1,1)=5 : larger neighbors (1,2)=6 and (2,1)=8
      dfs(1,2) = 2 (recomputed!)
      dfs(2,1)=8 : larger neighbor (2,2)=9 -> 2
    dfs(1,1) = 1 + 2 = 3
  dfs(0,1) = 1 + max(3, 3) = 4
  dfs(1,0)=4 : ... also leads to 4 more steps at most
dfs(0,0) = 1 + 4 = 5
```

Result: **5** (matches the expected output).

Notice `dfs(1,2)` and `dfs(2,2)` were each computed multiple times. On bigger grids this repetition multiplies and becomes exponential.

### Complexity
- **Time:** Exponential in the worst case, because there is no caching of repeated subproblems.
- **Space:** O(n * m) recursion depth in the worst case.

### Why It Fails the Constraints
With `n, m` up to 1000 the grid has up to 10^6 cells. Exponential time is hopeless, and the recursion depth alone (up to 10^6 frames) would overflow the Java stack.

---

## 3. Middle Ground: DFS + Memoization

Add a `memo[i][j]` table. The first time `dfs(i, j)` is computed, store it; every later call returns the stored value instantly.

```java
if (memo[i][j] != 0) return memo[i][j];
// ... compute best as before ...
memo[i][j] = best;
return best;
```

- **Time:** O(n * m): every cell is computed once, 4 neighbor checks each.
- **Space:** O(n * m) for the memo table plus recursion stack.
- **Catch:** the recursion can go up to 10^6 deep on a snake-like grid, which risks `StackOverflowError` in Java. That is the main reason the iterative topological sort below is preferred for these constraints.

---

## 4. Approach 2: Optimal (Topological Sort, Kahn's Algorithm)

### Idea
Instead of asking "how long is the path starting here?" recursively, peel the DAG in layers from the bottom (smallest values) upward.

1. **indegree[i][j]** = number of neighbors with a strictly smaller value (the number of edges coming into the cell).
2. Cells with indegree 0 have no smaller neighbor. They are local minima and can only be the **start** of a path. They form **layer 1**.
3. Process the queue **one whole layer at a time**. For every cell removed, decrement the indegree of each larger neighbor. When a neighbor's indegree reaches 0, every smaller cell that could precede it has been handled, so it joins the next layer.
4. Each layer is one more cell on a path, so **the number of layers = the length of the longest increasing path**.

### Why the Layer Count Equals the Longest Path
A cell lands in layer `k` exactly when its longest increasing path ending at that cell has `k` cells: it enters the queue only after **all** of its smaller neighbors have been processed, and the last of those neighbors was in layer `k - 1`. So the deepest layer gives the maximum path length.

### Code Logic
```java
// 1. indegree = count of strictly smaller neighbors
// 2. queue <- all cells with indegree 0
// 3. while queue not empty:
//        length++
//        for each cell in the current layer:
//            for each strictly larger neighbor:
//                indegree--
//                if indegree == 0: enqueue
// 4. return length
```

### Dry Run 1: Example 1
`matrix = [[1,2,3],[4,5,6],[7,8,9]]`

**Indegrees** (count of strictly smaller neighbors):

| | col 0 | col 1 | col 2 |
|---|---|---|---|
| row 0 | 0 (value 1) | 1 (value 2) | 1 (value 3) |
| row 1 | 1 (value 4) | 2 (value 5) | 2 (value 6) |
| row 2 | 1 (value 7) | 2 (value 8) | 2 (value 9) |

**Layers:**

| Layer | Cells in queue (values) | What happens |
|---|---|---|
| 1 | (0,0)=1 | Releases 4 and 2 (their indegree drops to 0) |
| 2 | (1,0)=4, (0,1)=2 | 4 reduces 7 to 0 and 5 to 1; 2 reduces 5 to 0 and 3 to 0 |
| 3 | (2,0)=7, (1,1)=5, (0,2)=3 | 7 reduces 8 to 1; 5 reduces 8 to 0 and 6 to 1; 3 reduces 6 to 0 |
| 4 | (2,1)=8, (1,2)=6 | 8 reduces 9 to 1; 6 reduces 9 to 0 |
| 5 | (2,2)=9 | No larger neighbors |

`length = 5`

Result: **5** ✅ (matches expected output)

### Dry Run 2: Example 2
`matrix = [[3,4,5],[6,2,6],[2,2,1]]`

**Indegrees:**

| | col 0 | col 1 | col 2 |
|---|---|---|---|
| row 0 | 0 (3) | 2 (4) | 1 (5) |
| row 1 | 3 (6) | 0 (2) | 3 (6) |
| row 2 | 0 (2) | 1 (2) | 0 (1) |

**Layers:**

| Layer | Cells in queue (values) | What happens |
|---|---|---|
| 1 | (0,0)=3, (1,1)=2, (2,0)=2, (2,2)=1 | Cells 4, 6 (left), and 2 (bottom-middle) become free; the right 6 is still waiting |
| 2 | (0,1)=4, (1,0)=6, (2,1)=2 | 4 releases 5 |
| 3 | (0,2)=5 | 5 releases the right-hand 6 |
| 4 | (1,2)=6 | Done |

`length = 4`

Result: **4** ✅ (matches expected output)

### Dry Run 3: Example 3
`matrix = [[1,1],[1,1]]`

All four cells have indegree 0 (no neighbor is strictly smaller). They all enter layer 1, and none has a strictly larger neighbor, so nothing else is ever enqueued. `length = 1`.

Result: **1** ✅ (matches expected output)

### Complexity
- **Time:** O(n * m): each cell is enqueued once and examines 4 neighbors a constant number of times.
- **Space:** O(n * m): the indegree grid plus the `n * m` queue array.

---

## 5. Comparing the Approaches

| Aspect | Brute Force DFS | DFS + Memo | Topological Sort (Optimal) |
|---|---|---|---|
| Time Complexity | Exponential | O(n * m) | O(n * m) |
| Space Complexity | O(n * m) stack | O(n * m) + stack | O(n * m) |
| Stack overflow risk on 1000 x 1000? | Yes | Yes | **No** (iterative) |
| Handles the full constraints? | No | Risky in Java | Yes |

---

## 6. Implementation Notes on the Provided Code

- **Edge direction:** edges point from smaller to larger, so `indegree` counts **smaller** neighbors. The queue therefore starts from local minima.
- **Array-based queue:** `int[n * m][2]` with `front` and `rear` pointers works because each cell is enqueued **at most once**, so `n * m` slots always suffice. This avoids `LinkedList` or `ArrayDeque` object overhead on up to 10^6 cells.
- **Level counting:** `int size = rear - front;` freezes the current layer's size before the inner loop, so newly added cells are only processed in the next outer iteration.
- **Equal values:** a neighbor with an equal value is neither smaller nor larger, so no edge exists between them. That is how the strictly-increasing rule is enforced, and why all-equal grids give 1.

---

## 7. Edge Cases to Consider

1. **All values equal** (Example 3): no edges at all, every cell is a layer-1 node, answer is `1`.
2. **Single cell (1 x 1):** one node, answer `1`.
3. **Strictly increasing snake-like grid:** the answer can reach `n * m`, which is exactly why a recursive DFS can overflow the stack.
4. **Plateaus and ties:** equal neighbors never connect, so paths cannot cross them.
5. **Single row or single column:** the grid is just an array, and the answer is the length of its longest strictly increasing contiguous run.
6. **Large values up to 2^30:** only comparisons are used (no arithmetic on values), so `int` is safe.
7. **Multiple local minima:** each one seeds layer 1, and they are processed together in the same BFS wave.

---

## 8. Related Concepts / Follow-Ups

- **Topological sorting (Kahn's algorithm):** used here to compute the **longest** path in a DAG by counting BFS layers. The same layering trick appears in Course Schedule II and in "parallel courses" style problems.
- **Longest Increasing Subsequence (LIS):** the 1D cousin. The matrix version replaces "next element in the array" with "any strictly larger neighbor."
- **DFS + Memoization on a DAG:** the standard alternative, often the first thing people write for this problem (LeetCode 329, Longest Increasing Path in a Matrix).
- **Longest path in a DAG:** easy because there are no cycles, whereas the longest path in a general graph is NP-hard.

---

## 9. Key Takeaways

- Strictly increasing movement means the grid, viewed as a graph, is a **DAG**, so no `visited` set is needed.
- Longest path in a DAG can be computed by **Kahn's topological sort, counting how many BFS layers it takes**. The layer count is the answer.
- The iterative approach avoids deep recursion, which matters because a 1000 x 1000 grid can force a path of up to 10^6 cells.
- Replacing object-based queues with a preallocated `int[n * m][2]` array is a handy constant-factor optimization when every node is enqueued at most once.
