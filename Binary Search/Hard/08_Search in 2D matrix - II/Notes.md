# Notes: Search in 2D Matrix - II

## 1. Problem Recap

Given a matrix where **every row** is sorted ascending left-to-right, and **every column** is sorted ascending top-to-bottom, determine whether `target` exists anywhere in the matrix.

```
matrix = [[ 1,  4,  7, 11, 15],
          [ 2,  5,  8, 12, 19],
          [ 3,  6,  9, 16, 22],
          [10, 13, 14, 17, 24],
          [18, 21, 23, 26, 30]]
```

Note this is a **different (weaker) sorted structure** than a fully sorted matrix — rows and columns are sorted independently, but there's no guarantee that, say, `matrix[0][4] < matrix[1][0]` (indeed, `15 < 2` is false here). This distinction matters — a simpler "Search a 2D Matrix I" problem (where the whole matrix behaves like one giant sorted array) can be solved with a single binary search treating the matrix as 1D; this problem's weaker sorted structure requires a different technique.

---

## 2. Approach 1: Brute Force (Linear Scan of Every Cell)

### Idea
Check every cell, row by row, comparing against the target.

### Code Logic
```java
for (int i = 0; i < rows; i++) {
    for (int j = 0; j < cols; j++) {
        if (matrix[i][j] == target) return true;
    }
}
return false;
```

### Dry Run
`target = 5` in the example matrix

Scanning row 0: `1, 4, 7, 11, 15` — no match.
Scanning row 1: `2, 5` — match found at `(1,1)`.

Result: **true** ✅

### Complexity
- **Time:** O(n × m) — scans every cell in the worst case.
- **Space:** O(1)

### A Better Brute-Force Variant (Worth Knowing)
Since each ROW is individually sorted, you could instead binary search each row independently (O(log m) per row), giving O(n log m) overall — a nice middle ground between full brute force and the fully optimal staircase search. This doesn't exploit the *column* sorting at all, though, which is what the optimal approach uses to do even better.

---

## 3. Approach 2: Optimal (Staircase Search)

### The Core Insight
Start at the **top-right corner** of the matrix. This specific corner is special because it sits at the intersection of "largest in its row" and "smallest in its column" — which gives us a clean decision rule:

- **If `current == target`**: found it.
- **If `current > target`**: the target can't be anywhere below in this same column (column values only increase going down, so everything below is even bigger). Move **left** to eliminate this column.
- **If `current < target`**: the target can't be anywhere to the left in this same row (row values only increase going right, so everything to the left is even smaller). Move **down** to eliminate this row.

Each move eliminates an entire row or column from consideration, so we never revisit the same row or column twice — bounding total moves at `rows + cols`.

### Why the Top-Right (or Bottom-Left) Corner Specifically?
The top-**left** corner doesn't work for this technique: if `matrix[0][0] < target`, you wouldn't know whether to move right (into the row) or down (into the column) — both directions increase, giving no clear elimination rule. Similarly, the bottom-**right** corner has the same ambiguity in reverse. Only the top-right (or symmetrically, the bottom-left) corner gives a clean "one direction increases, the other decreases" property essential for this technique.

### Dry Run 1
`target = 5`

| Step | row | col | matrix[row][col] | Comparison | Action |
|---|---|---|---|---|---|
| 1 | 0 | 4 | 15 | 15 > 5 | col-- |
| 2 | 0 | 3 | 11 | 11 > 5 | col-- |
| 3 | 0 | 2 | 7 | 7 > 5 | col-- |
| 4 | 0 | 1 | 4 | 4 < 5 | row++ |
| 5 | 1 | 1 | 5 | 5 == 5 | **return true** |

Result: **true** ✅ (matches expected output)

### Dry Run 2
`target = 20`

| Step | row | col | matrix[row][col] | Comparison | Action |
|---|---|---|---|---|---|
| 1 | 0 | 4 | 15 | 15 < 20 | row++ |
| 2 | 1 | 4 | 19 | 19 < 20 | row++ |
| 3 | 2 | 4 | 22 | 22 > 20 | col-- |
| 4 | 2 | 3 | 16 | 16 < 20 | row++ |
| 5 | 3 | 3 | 17 | 17 < 20 | row++ |
| 6 | 4 | 3 | 26 | 26 > 20 | col-- |
| 7 | 4 | 2 | 23 | 23 > 20 | col-- |
| 8 | 4 | 1 | 21 | 21 > 20 | col-- |
| 9 | 4 | 0 | 18 | 18 < 20 | row++ |
| — | 5 | 0 | — | row>=rows | **loop ends -> return false** |

Result: **false** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`target = 1`

| Step | row | col | matrix[row][col] | Comparison | Action |
|---|---|---|---|---|---|
| 1 | 0 | 4 | 15 | 15 > 1 | col-- |
| 2 | 0 | 3 | 11 | 11 > 1 | col-- |
| 3 | 0 | 2 | 7 | 7 > 1 | col-- |
| 4 | 0 | 1 | 4 | 4 > 1 | col-- |
| 5 | 0 | 0 | 1 | 1 == 1 | **return true** |

**Result: True** ✅

So for the quiz options `False, True`, the correct answer is **True**.

### Complexity
- **Time:** O(n + m) — `row` only ever increases (up to `n` times), `col` only ever decreases (up to `m` times).
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (Full Scan) | Optimal (Staircase Search) |
|---|---|---|
| Time Complexity | O(n × m) | O(n + m) |
| Space Complexity | O(1) | O(1) |
| Uses row-sorted property? | No | Yes |
| Uses column-sorted property? | No | Yes (fully) |

For a `300 × 300` matrix (max per constraints), brute force could check up to `90,000` cells, while the staircase search checks at most `600` (`300 + 300`) — a massive difference, and this gap only widens for larger matrices.

---

## 5. Edge Cases to Consider

1. **Target smaller than every element** — e.g., `target = 0` → the staircase search moves left repeatedly across row 0 until `col < 0`, correctly returning `false`.
2. **Target larger than every element** — e.g., `target = 100` → moves down repeatedly until `row >= rows`, correctly returning `false`.
3. **Target at the very corners** — top-left (see Dry Run 3), top-right, bottom-left, bottom-right — all should be found quickly since the starting point is already at one corner.
4. **Single row or single column matrix** — e.g., `matrix = [[1,3,5,7]]` — degenerates to a simple linear/binary search pattern; the staircase logic still works correctly, just with `row` never incrementing (or `col` moving through everything in the single row).
5. **1x1 matrix** — e.g., `matrix = [[5]]`, `target = 5` → immediate match at the single cell.
6. **Duplicate values within the matrix** — the algorithm's correctness doesn't depend on uniqueness; it will find *a* match if one exists, though the problem doesn't ask for a specific index.

---

## 6. Related Concepts / Follow-Ups

- **Find Row With Maximum 1's**: Uses the exact same staircase search technique (top-right corner, move left/down) applied to a binary (0/1) matrix instead of searching for an arbitrary target — strong conceptual overlap.
- **Search a 2D Matrix I** (LeetCode 74): A different, *stronger* sorted structure (the matrix behaves like one giant sorted 1D array when read row by row), solvable with a single binary search treating `(row, col)` as a flattened index — worth contrasting against this problem's weaker sorted guarantee.
- **Kth Smallest Element in a Sorted Matrix**: Another problem exploiting row/column sorted structure, though typically solved with a min-heap or binary-search-on-value technique rather than the staircase traversal used here.

---

## 7. Key Takeaways

- The staircase search technique specifically requires starting from the **top-right** (or bottom-left) corner — the other two corners don't provide a usable elimination rule, since both directions from those corners move in the same direction (both increasing or both decreasing).
- Moving **left on "too big"** and **down on "too small"** eliminates an entire row or column with each step, bounding the total work at O(n + m) instead of O(n × m).
- This is the *exact same technique* as "Find Row With Maximum 1's" — recognizing that pattern match means you've already internalized this approach once you've solved either problem.
- Distinguishing between this problem's row/column-independently-sorted structure and the *fully* sorted structure of "Search a 2D Matrix I" is important, since they call for genuinely different algorithms (staircase search vs. flattened 1D binary search).
