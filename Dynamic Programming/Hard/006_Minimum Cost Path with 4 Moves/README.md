# Minimum Cost Path with 4 Moves

**Difficulty:** Hard (as rated)
**Tags:** Graph, Shortest Path, Dijkstra's Algorithm, Priority Queue, Matrix

## Problem Statement

Given an `n x n` grid where each cell holds a positive integer cost, find the path from the top-left cell to the bottom-right cell with the **minimum total cost**. From cell `(i, j)` you may move to `(i, j-1)`, `(i, j+1)`, `(i-1, j)` or `(i+1, j)` (left, right, up, down). The cost of a path is the sum of the costs of **all** cells on it, including the start and end cells.

## Examples

### Example 1
```
Input:  grid = [[9, 4, 9, 9],
                [6, 7, 6, 4],
                [8, 3, 3, 7],
                [7, 4, 9, 10]]
Output: 43
Explanation: 9 + 4 + 7 + 3 + 3 + 7 + 10 = 43
```

### Example 2
```
Input:  grid = [[4, 4],
                [3, 7]]
Output: 14
Explanation: 4 + 3 + 7 = 14
```

## Constraints

- `1 <= n <= 500`
- `1 <= cost of cells <= 500`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | DFS/backtracking over every simple path (exponential) |
| `Optimal.java` | Dijkstra's algorithm with a min-heap, O(n^2 log n), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (try every simple path) | Exponential | O(n^2) |
| Middle ground (Bellman-Ford style repeated relaxation) | O(n^4) worst case | O(n^2) |
| Optimal (Dijkstra with priority queue) | O(n^2 log n) | O(n^2) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is **not** a plain grid DP like "Unique Paths" or "Minimum Path Sum". Those only allow moves right and down, so every cell depends only on cells above and to the left and a simple row-by-row fill works. Here you can also move **up and left**, so the cheapest route to a cell may come from any of its 4 neighbors, and the dependencies form cycles. A single sweep cannot work. Treat the grid as a graph (each cell is a node, each move is an edge whose weight is the cost of the cell you step onto) and run **Dijkstra's shortest-path algorithm**. All costs are positive, which is exactly the condition Dijkstra needs.
