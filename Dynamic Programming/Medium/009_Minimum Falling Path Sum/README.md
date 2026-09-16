# Minimum Falling Path Sum

**Difficulty:** Medium
**Tags:** Dynamic Programming, DP on Grids
**Companies:** (add as applicable)

## Problem Statement

Given a 2D `matrix`, return the minimum path sum starting at **any** cell in the first row and ending at **any** cell in the last row. Movement is allowed only to the bottom, bottom-left, or bottom-right cell.

## Examples

### Example 1
```
Input:  matrix = [[1,2,10,4],[100,3,2,1],[1,1,20,2],[1,2,2,1]]
Output: 6
```

### Example 2
```
Input:  matrix = [[1,4,3,1],[2,3,-1,-1],[1,1,-1,8]]
Output: -1
```

### Your Turn
```
Input:  matrix = [[4,3,4],[4,5,1],[4,6,2],[4,1,4]]
Output: 7
Explanation: Start at 3 (row0,col1) -> 1 (row1,col2) -> 2 (row2,col2) -> 1 (row3,col1). Sum = 3+1+2+1 = 7.
```

## Constraints

- `m` = rows, `n` = columns
- `1 <= n, m <= 100`
- `-1000 <= matrix[i][j] <= 1000`
- The answer will not exceed `10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(3^m) plain recursion solution |
| `Optimal.java` | O(m * n) row-by-row DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(3^m) | O(m) (recursion stack) |
| Optimal (Row-by-Row DP, O(n) space) | O(m * n) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

`dp[j]` tracks the minimum path sum to reach column `j` in the current row. Each new row's value at column `j` is `matrix[i][j] + min(dp[j-1], dp[j], dp[j+1])` — the cheapest of the three possible cells directly above (straight, diagonal-left, diagonal-right) in the previous row. Since the "any starting cell" and "any ending cell" constraints are naturally handled by starting `dp` as the first row and taking the min of the final row, no extra bookkeeping is needed beyond the core recurrence.
