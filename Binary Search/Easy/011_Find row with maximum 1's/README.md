# Find Row With Maximum 1's

**Difficulty:** Easy
**Tags:** Array, Binary Search, Matrix
**Companies:** (add as applicable)

## Problem Statement

Given a non-empty grid `mat` consisting only of `0`s and `1`s, where every row is sorted in **ascending order**, find the index of the row with the **maximum number of 1s**.

If two rows have the same number of 1s, return the one with the **smaller index**. If no `1` exists anywhere in the matrix, return `-1`.

## Examples

### Example 1
```
Input:  mat = [[1,1,1],[0,0,1],[0,0,0]]
Output: 0
Explanation: Row 0 has 3 ones, the most of any row.
```

### Example 2
```
Input:  mat = [[0,0],[0,0]]
Output: -1
Explanation: No 1s exist anywhere in the matrix.
```

### Your Turn
```
Input:  mat = [[0,0,1],[0,1,1],[0,1,1]]
Output: 1
Explanation: Rows 1 and 2 both have 2 ones (the max), but row 1 has the smaller index.
```

## Constraints

- `n == mat.length`
- `m == mat[i].length`
- `1 <= n, m <= 100`
- `mat[i][j]` is either `0` or `1`.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n * m) linear scan solution |
| `Optimal.java` | O(n + m) staircase search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan / Row-by-Row Binary Search) | O(n * m) or O(n log m) | O(1) |
| Optimal (Staircase Search) | O(n + m) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Because every row is individually sorted, the matrix as a whole has a "staircase" shape when you trace the 0/1 boundary. Starting from the **top-right corner** and moving **left** whenever you find a `1` (or **down** whenever you find a `0`) traces this staircase in a single pass — visiting each row and column at most once, giving O(n + m) instead of scanning every cell.
