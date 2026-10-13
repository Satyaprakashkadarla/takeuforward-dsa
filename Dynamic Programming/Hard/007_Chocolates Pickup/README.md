# Two Robots Collecting Chocolates (Cherry Pickup II)

**Difficulty:** Hard
**Tags:** Dynamic Programming, 3D DP, DP on Grids

## Problem Statement

Given an `n x m` grid where `grid[i][j]` is the number of chocolates at `(i, j)`, two robots collect chocolates:

- Robot 1 starts at the top-left `(0, 0)`.
- Robot 2 starts at the top-right `(0, m - 1)`.

From `(i, j)` a robot can move to `(i + 1, j - 1)`, `(i + 1, j)` or `(i + 1, j + 1)`. A robot collects all chocolates in each cell it visits. If both robots land on the same cell, that cell is counted **only once**. Robots cannot leave the grid and both must keep moving until they reach the bottom row.

Return the maximum total chocolates both robots can collect.

## Example

```
Input:  grid = [[4, 1, 2],
                [3, 6, 1],
                [1, 6, 6],
                [3, 1, 2]]
Output: 32
Explanation:
  Robot 1: (0,0) -> (1,0) -> (2,1) -> (3,0) = 4 + 3 + 6 + 3 = 16
  Robot 2: (0,2) -> (1,1) -> (2,2) -> (3,2) = 2 + 6 + 6 + 2 = 16
  Total = 32
```

## Constraints

- `2 <= n, m <= 70`
- `0 <= grid[i][j] <= 100`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Plain recursion over both robots' moves, O(9^n) |
| `Optimal.java` | 3D DP `dp[row][col1][col2]`, O(n * m^2), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(9^n) | O(n) recursion stack |
| Optimal (3D DP as provided) | O(n * m^2 * 9) = O(n * m^2) | O(n * m^2) |
| Space-optimized (two layers) | O(n * m^2) | O(m^2) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

The two robots move **down one row at a time in lockstep**, so after `i` moves both are in row `i`. The only things that matter are the row and the two column positions, giving the state `(row, col1, col2)`. Because the robots never interact except for the "count a shared cell once" rule, the best total for a state depends only on the 9 combinations of where each robot could have been one row earlier. This is the same problem as **Ninja and his Friends** (and LeetCode 1463, Cherry Pickup II).
