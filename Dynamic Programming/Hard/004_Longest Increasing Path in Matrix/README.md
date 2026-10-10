# Longest Increasing Path in Matrix

**Difficulty:** Hard
**Tags:** Matrix, Graph, Topological Sort (Kahn's Algorithm), DFS + Memoization, DP

## Problem Statement

Given an `n x m` matrix, find the length of the longest path such that:

- The path can start and end at any cell.
- A cell cannot be visited more than once.
- Values along the path are **strictly increasing**.
- Moves are left, right, up, or down only (no diagonals, no leaving the matrix).

## Examples

### Example 1
```
Input:  n = 3, m = 3, matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: 5
Explanation: 1 -> 2 -> 3 -> 6 -> 9
```

### Example 2
```
Input:  n = 3, m = 3, matrix = [[3,4,5],[6,2,6],[2,2,1]]
Output: 4
Explanation: 3 -> 4 -> 5 -> 6
```

### Example 3
```
Input:  n = 2, m = 2, matrix = [[1,1],[1,1]]
Output: 1
Explanation: All values are equal, so no move is allowed. The longest path is a single cell.
```

## Constraints

- `1 <= n, m <= 1000`
- `0 <= matrix[i][j] <= 2^30`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Plain DFS from every cell, no memoization (exponential worst case) |
| `Optimal.java` | O(n * m) topological sort (Kahn's algorithm, level-by-level BFS), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (DFS from every cell, no memo) | Exponential in the worst case | O(n * m) recursion stack |
| Middle ground (DFS + memoization) | O(n * m) | O(n * m) |
| Optimal (Topological Sort / Kahn's BFS) | O(n * m) | O(n * m) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Treat every cell as a node, and draw a directed edge from a cell to each neighbor with a **strictly larger** value. Because values must strictly increase along an edge, this graph can never contain a cycle: it is a **DAG**. The longest increasing path is then simply the **longest path in a DAG**, which Kahn's topological sort finds directly: peel off the "layers" of the DAG one at a time (starting from cells with no smaller neighbor), and the **number of layers is the answer**. No recursion is needed, which also avoids stack overflow on a 1000 x 1000 grid.
