# Grid Unique Paths

**Difficulty:** Medium
**Tags:** Dynamic Programming, Combinatorics, DP on Grids
**Companies:** (add as applicable)

## Problem Statement

Given `m` rows and `n` columns, return the number of unique paths from the top-left cell to the bottom-right cell of an `m x n` grid, moving only **right** or **down** at each step.

## Examples

### Example 1
```
Input:  m = 3, n = 2
Output: 3
```

### Example 2
```
Input:  m = 2, n = 4
Output: 4
```

### Example 3
```
Input:  m = 3, n = 3
Output: 6
```

## Constraints

- `1 <= n, m <= 100`
- The answer will not exceed `10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^(m+n)) plain recursion solution |
| `Optimal.java` | O(min(m,n)) closed-form combinatorics solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^(m+n)) | O(m+n) (recursion stack) |
| DP on Grid (1D array) | O(m*n) | O(n) |
| Optimal (Combinatorics) | O(min(m,n)) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Any path from top-left to bottom-right consists of exactly `(m-1)` "down" moves and `(n-1)` "right" moves, in some order. The number of unique paths is therefore the number of ways to **arrange** these moves — a classic combinatorics problem solved by `C(m+n-2, m-1)` (choosing which of the total moves are "down" moves). This turns an O(m×n) DP grid problem into an O(min(m,n)) direct formula computation.
