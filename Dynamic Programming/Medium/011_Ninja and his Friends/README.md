# Ninja and his Friends

**Difficulty:** Medium
**Tags:** Dynamic Programming, DP on Grids, 3D DP
**Companies:** (add as applicable)

## Problem Statement

Alice starts at `(0,0)` and Bob starts at `(0, C-1)` on an `R x C` grid of chocolates. Each move goes to the next row, either straight down, down-left, or down-right. If both land on the same cell, its chocolates count only once. Return the maximum total chocolates collectible.

## Examples

### Example 1
```
Input:  grid = [[2,3,1,2],[3,4,2,2],[5,6,3,5]]
Output: 21
```

### Example 2
```
Input:  grid = [[1,2],[3,4]]
Output: 10
```

### Your Turn
```
Input:  grid = [[10,1,10],[1,1,1],[1,1,1]]
Output: 24
Explanation: Alice and Bob converge/diverge through the middle rows to pick up both corner 10's on row 0 (20) plus extra 1's along the way, totaling 24.
```

## Constraints

- `2 <= R, C <= 50`
- `0 <= grid[i][j] <= 100`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(3^(2R)) plain recursion solution |
| `Optimal.java` | O(R * C^2) row-by-row DP (space-optimized to 2 layers) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(3^(2R)) | O(R) (recursion stack) |
| Optimal (Row-by-Row DP) | O(R * C^2) | O(C^2) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is the first genuinely **3D DP** problem in the series: state = (row, Alice's column, Bob's column). Since both move one row at a time, each row's state only depends on the row directly above — so the DP is computed layer by layer, with each layer being a full `C x C` table of "best total so far" for every possible (AliceCol, BobCol) pairing. The overlap rule (count shared cells once) is handled by a simple `if (j1 != j2)` check when adding the current row's chocolates.
