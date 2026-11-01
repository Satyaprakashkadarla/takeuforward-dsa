# Cut Matrix (Ways to Cut a Pizza)

**Difficulty:** Expert (as rated)
**Tags:** Dynamic Programming, 3D DP, 2D Prefix Sums, Binary Search, Matrix

## Problem Statement

Given a matrix `mat[][]` of 0s and 1s and an integer `k`, divide the matrix into `k` pieces so that **every piece contains at least one 1**. Cuts are made as follows:

- Choose a direction (vertical or horizontal) and an index to split the matrix into two pieces.
- After a **horizontal** cut, only the **bottom** part can be cut further.
- After a **vertical** cut, only the **right** part can be cut further.

Return the number of different ways to divide the matrix, modulo `10^9 + 7`.

## Examples

### Example 1
```
Input:  mat = [[1, 0, 0],
               [1, 1, 1],
               [0, 0, 0]], k = 3
Output: 3
Explanation:
  1) horizontal cut after row 0, then vertical cut after col 0 on the bottom part
  2) horizontal cut after row 0, then vertical cut after col 1 on the bottom part
  3) vertical cut after col 0, then vertical cut after col 1 on the right part
```

### Example 2
```
Input:  mat = [[0, 0],
               [1, 1]], k = 2
Output: 1
Explanation: Only a vertical cut in the middle works; the top half would contain no 1.
```

### Example 3
```
Input:  mat = [[1, 0],
               [0, 0]], k = 1
Output: 1
Explanation: No cut is needed; the whole matrix is a single piece containing a 1.
```

## Constraints

- `1 <= k, mat.size(), mat[i].size() <= 200`
- `0 <= mat[i][j] <= 1`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Plain recursion over every sequence of cuts |
| `Optimal.java` | 3D DP + 2D suffix sums + binary search + suffix sums of DP values, as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (recursion over every cut sequence) | Exponential, about O((n + m)^k) | O(k) recursion stack |
| Middle ground (memoized DP, loop over every cut) | O(k * n * m * (n + m)) | O(n * m * k) |
| Optimal (as provided) | O(k * n * m * (log n + log m)) | O(n * m * k) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Because the cut rule keeps only the **bottom** or **right** part for further cutting, every remaining piece is a **bottom-right sub-matrix**, identified by its top-left corner `(r, c)`. So the state is `dp[r][c][p]`, the number of ways to split the sub-matrix starting at `(r, c)` into `p` valid pieces. Two ideas make it fast: (1) a **2D suffix sum** of ones tells in O(1) whether a strip contains a 1; (2) the set of **valid cut positions is a contiguous range** (once a strip contains a 1, longer strips do too), so a **binary search** finds where it starts and a **precomputed suffix sum of the DP values** adds up the whole range at once, instead of looping over each cut.
