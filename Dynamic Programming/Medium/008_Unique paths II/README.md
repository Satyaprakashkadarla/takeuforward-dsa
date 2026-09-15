# Unique Paths II

**Difficulty:** Medium
**Tags:** Dynamic Programming, DP on Grids
**Companies:** (add as applicable)

## Problem Statement

Given an `m x n` grid where each cell is `0` (open) or `1` (blocked), return the number of unique paths from the top-left to the bottom-right, moving only right or down, avoiding blocked cells.

## Examples

### Example 1
```
Input:  matrix = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2
```

### Example 2
```
Input:  matrix = [[0,0,0],[0,0,1],[0,1,0]]
Output: 0
```

### Your Turn
```
Input:  matrix = [[0,0,0,0],[0,0,1,0]]
Output: 1
Explanation: Only one path avoids the obstacle at (1,2): right,right,down,right (or equivalent routing that avoids the blocked cell).
```

## Constraints

- `m` = number of rows, `n` = number of columns
- `1 <= n, m <= 100`
- Each cell is `0` or `1`
- The answer will not exceed `10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^(m+n)) plain recursion solution |
| `Optimal.java` | O(m * n) 1D DP on grid solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^(m+n)) | O(m+n) (recursion stack) |
| Optimal (1D DP on Grid) | O(m * n) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Unlike "Grid Unique Paths" (no obstacles), the clean combinatorics formula `C(m+n-2, m-1)` **cannot** handle blocked cells — obstacles eliminate specific paths in position-dependent ways a single binomial coefficient can't capture. So we fall back to the standard DP-on-grid approach: `dp[j] = dp[j] (from above) + dp[j-1] (from left)`, with blocked cells forced to `dp[j] = 0`. This can be space-optimized to a single 1D array reused across rows.
