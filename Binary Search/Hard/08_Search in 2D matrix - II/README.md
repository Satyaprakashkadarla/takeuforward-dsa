# Search in 2D Matrix - II

**Difficulty:** Hard
**Tags:** Array, Binary Search, Matrix
**Companies:** (add as applicable)

## Problem Statement

Given a 2D array `matrix` where each **row is sorted ascending left to right** and each **column is sorted ascending top to bottom**, write an efficient algorithm to search for a target integer.

## Examples

### Example 1
```
Input:  matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
Output: True
Explanation: 5 exists at index (1,1).
```

### Example 2
```
Input:  matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
Output: False
Explanation: 20 does not exist in the matrix.
```

### Your Turn
```
Input:  matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 1
Output: True
Explanation: 1 exists at index (0,0).
```

## Constraints

- `n == matrix.length`
- `m == matrix[i].length`
- `1 <= n, m <= 300`
- `-10^9 <= matrix[i][j] <= 10^9`
- Each row is sorted ascending; each column is sorted ascending.
- `-10^9 <= target <= 10^9`

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

Because both rows AND columns are sorted, starting from the **top-right corner** gives a uniquely useful vantage point: moving **left** eliminates a column (all values below the current one in that column are even larger), and moving **down** eliminates a row (all values to the left in that row are even smaller). This single-sweep "staircase" technique visits at most `n + m` cells instead of scanning the whole matrix.
