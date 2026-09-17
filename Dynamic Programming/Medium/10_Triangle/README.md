# Triangle

**Difficulty:** Medium
**Tags:** Dynamic Programming, DP on Grids
**Companies:** (add as applicable)

## Problem Statement

Given a triangular array `triangle` with `n` rows (row `i` has `i+1` elements), return the minimum falling path sum from the first row to the last, moving only to the bottom or bottom-right cell.

## Examples

### Example 1
```
Input:  triangle = [[1],[1,2],[1,2,4]]
Output: 3
```

### Example 2
```
Input:  triangle = [[1],[4,7],[4,10,50],[-50,5,6,-100]]
Output: -42
```

### Your Turn
```
Input:  triangle = [[3],[-1,3],[-3,2,4],[8,8,1,-4]]
Output: 5
Explanation: Path 3 -> -1 -> 2 -> 1 = 5, or other routes summing to the same minimum.
```

## Constraints

- `n` = number of rows
- `1 <= n <= 200`
- `-10^4 <= triangle[i][j] <= 10^4`
- `triangle[0].length == 1`
- `triangle[i].length = triangle[i-1].length + 1`
- The answer will not exceed `10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n) plain recursion solution |
| `Optimal.java` | O(n^2) bottom-up DP (space-optimized, in-place on last row) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^n) | O(n) (recursion stack) |
| Optimal (Bottom-Up DP, O(n) space) | O(n^2) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This problem is solved **bottom-up** rather than top-down: `dp[j]` represents the minimum path sum from cell `(i,j)` down to the base of the triangle. Building from the last row upward, `dp[j] = triangle[i][j] + min(dp[j], dp[j+1])` — reusing a single array cloned from the last row, since each row only ever needs the row directly below it.
