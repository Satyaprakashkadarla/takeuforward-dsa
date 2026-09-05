# Find Peak Element - II

**Difficulty:** Hard
**Tags:** Array, Binary Search, Matrix

## Problem Statement

Given a 0-indexed `n x m` matrix `mat` where no two adjacent cells are equal, find any **peak element** `mat[i][j]` and return `[i, j]`. A peak element is strictly greater than all of its adjacent neighbors (left, right, top, bottom).

Assume the entire matrix is surrounded by an outer perimeter with value `-1` in every cell.

## Examples

### Example 1
```
Input:  mat = [[10,20,15],[21,30,14],[7,16,32]]
Output: [1, 1]
Explanation: mat[1][1] = 30 is greater than all its neighbors (20, 14, 21, 16).
```

### Example 2
```
Input:  mat = [[10,7],[11,17]]
Output: [1, 1]
Explanation: mat[1][1] = 17 is the only peak (neighbors 7, 11, and the -1 border).
```

### Your Turn
```
Input:  mat = [[1,2,3],[4,5,6],[7,8,9]]
Output: [2, 2]
Explanation: mat[2][2] = 9 is greater than its neighbors 8 (left), 6 (top), and the -1 border (right, bottom).
```

## Constraints

- `n == mat.length`
- `m == mat[i].length`
- `1 <= m, n <= 500`
- `1 <= mat[i][j] <= 10^5`
- No two adjacent cells are equal.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n * m) linear scan solution |
| `Optimal.java` | O(n log m) binary search on columns solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan of Every Cell) | O(n * m) | O(1) |
| Optimal (Binary Search on Columns) | O(n log m) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This generalizes the 1D "Find Peak Element" trick to 2D: binary search over **columns**. For each candidate column, find the maximum element in that column (an O(n) scan), then compare it to its left/right neighbors. If it's a peak, done. Otherwise, move toward whichever neighbor is larger — since a peak is guaranteed to exist in that direction, the same way climbing toward a larger value guarantees a peak in the 1D version.
