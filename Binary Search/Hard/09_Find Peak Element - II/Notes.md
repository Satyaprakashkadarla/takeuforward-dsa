# Notes: Find Peak Element - II

## 1. Problem Recap

Given a 2D matrix (no two adjacent cells equal), find any element that is **strictly greater than all four of its neighbors** (up, down, left, right). Out-of-bounds neighbors are treated as `-1`.

```
mat = [[1, 2, 3],
       [4, 5, 6],
       [7, 8, 9]]

mat[2][2] = 9: neighbors are 8 (left), 6 (top), -1 (right border), -1 (bottom border)
9 > 8, 9 > 6, 9 > -1, 9 > -1 -> 9 is a peak
```

### Generalizing 1D "Find Peak Element" to 2D
Recall the 1D version: binary search over array indices, and at each step, compare `arr[mid]` to `arr[mid+1]` — if climbing, a peak exists to the right; if descending, a peak exists at `mid` or to the left.

The 2D version applies the **same core idea**, but binary searches over **columns** instead of individual elements. For each candidate column, we need a way to "represent" that column with a single value to compare against neighboring columns — and the natural choice is the **maximum value within that column**.

---

## 2. Approach 1: Brute Force (Linear Scan of Every Cell)

### Idea
Check every cell against its four neighbors (treating out-of-bounds as `-1`), and return the first one that's strictly greater than all of them.

### Code Logic
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        int current = mat[i][j];
        int up = (i>0) ? mat[i-1][j] : -1;
        int down = (i<n-1) ? mat[i+1][j] : -1;
        int left = (j>0) ? mat[i][j-1] : -1;
        int right = (j<m-1) ? mat[i][j+1] : -1;
        if (current > up && current > down && current > left && current > right) {
            return new int[]{i, j};
        }
    }
}
```

### Dry Run
`mat = [[1,2,3],[4,5,6],[7,8,9]]`

Scanning row 0: `1` (neighbors: up=-1,down=4,left=-1,right=2 → 1>4? No), `2` (up=-1,down=5,left=1,right=3 → 2>5? No), `3` (up=-1,down=6,left=2,right=-1 → 3>6? No).
Row 1: `4,5,6` — all fail similarly (each has a larger neighbor below or to the side).
Row 2: `7` (up=4,down=-1,left=-1,right=8 → 7>8? No), `8` (up=5,down=-1,left=7,right=9 → 8>9? No), `9` (up=6,down=-1,left=8,right=-1 → 9>6 yes, 9>-1 yes, 9>8 yes, 9>-1 yes → **peak!**)

Result: **[2, 2]** ✅

### Complexity
- **Time:** O(n × m) — checks every cell in the worst case.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on Columns)

### Idea
Binary search over the column range `[0, m-1]`. For each candidate column `mid`:

1. Find the row with the **maximum value** in that column (an O(n) scan).
2. Compare that max value to its immediate left and right neighbors (same row, adjacent columns).
3. **If it beats both neighbors**, it's a valid peak — since it's already the max within its own column, it automatically beats its up/down neighbors too (they're all `<=` it by definition of being the column max). Return its coordinates.
4. **Otherwise**, move toward whichever neighboring column has the larger value — a peak is guaranteed to exist somewhere in that direction (mirroring the 1D "climb toward a peak" guarantee, bounded by the `-1` border).

### Why Finding the Column's MAX Row Is the Right Choice
By picking the maximum value within the candidate column, we guarantee that this specific cell automatically satisfies the up/down peak conditions (since it's `>=` every other value in its column, including its immediate vertical neighbors). This means we only need to separately check the left/right conditions — reducing the 2D peak-finding problem to essentially the same left/right comparison used in the 1D version.

### Code Logic
```java
int left = 0, right = m - 1;

while (left <= right) {
    int mid = left + (right - left) / 2;

    int maxRow = 0;
    for (int i = 1; i < n; i++) {
        if (mat[i][mid] > mat[maxRow][mid]) maxRow = i;
    }

    int curr = mat[maxRow][mid];
    int leftVal = (mid > 0) ? mat[maxRow][mid-1] : -1;
    int rightVal = (mid < m-1) ? mat[maxRow][mid+1] : -1;

    if (curr > leftVal && curr > rightVal) {
        return new int[]{maxRow, mid};
    }

    if (leftVal > curr) {
        right = mid - 1;
    } else {
        left = mid + 1;
    }
}
```

### Dry Run 1
`mat = [[10,20,15],[21,30,14],[7,16,32]]`

| Step | left | right | mid | Column values | maxRow | curr | leftVal | rightVal | Peak? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | [20,30,16] | 1 | 30 | mat[1][0]=21 | mat[1][2]=14 | 30>21 & 30>14 → **Yes** | return [1,1] |

Result: **[1, 1]** ✅ (matches expected output)

### Dry Run 2
`mat = [[10,7],[11,17]]`

| Step | left | right | mid | Column values | maxRow | curr | leftVal | rightVal | Peak? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 1 | 0 | [10,11] | 1 | 11 | -1 (edge) | mat[1][1]=17 | 11>-1 yes, 11>17? No | leftVal(-1)>curr? No → left=1 |
| 2 | 1 | 1 | 1 | [7,17] | 1 | 17 | mat[1][0]=11 | -1 (edge) | 17>11 & 17>-1 → **Yes** | return [1,1] |

Result: **[1, 1]** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`mat = [[1,2,3],[4,5,6],[7,8,9]]`

| Step | left | right | mid | Column values | maxRow | curr | leftVal | rightVal | Peak? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | [2,5,8] | 2 | 8 | mat[2][0]=7 | mat[2][2]=9 | 8>7 yes, 8>9? No | rightVal(9)>curr(8) → left=2 |
| 2 | 2 | 2 | 2 | [3,6,9] | 2 | 9 | mat[2][1]=8 | -1 (edge) | 9>8 & 9>-1 → **Yes** | return [2,2] |

**Result: [2, 2]** ✅

So for the quiz options `[1,2], [1,1], [2,2], [2,1]`, the correct answer is **[2,2]**.

### Complexity
- **Time:** O(n log m) — O(log m) binary search iterations over columns, each doing an O(n) scan to find that column's maximum.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search on Columns) |
|---|---|---|
| Time Complexity | O(n × m) | O(n log m) |
| Space Complexity | O(1) | O(1) |
| Uses the "no adjacent equal" guarantee? | Not essential | Ensures the >/< comparisons are always strict, avoiding tie ambiguity |

For a `500 × 500` matrix (max per constraints), brute force checks up to `250,000` cells, while the optimal approach does roughly `log2(500) ≈ 9` column scans, each O(500) — about `4,500` total operations, a dramatic improvement.

---

## 5. Why the Direction-Choosing Logic (`if (leftVal > curr) right = mid - 1; else left = mid + 1;`) Is Correct

This mirrors the exact logic from 1D Find Peak Element: if the left neighbor is bigger than our current column-max, a peak is guaranteed to exist somewhere to the left (we're "descending" as we move right, so climbing left must eventually hit a peak, bounded by the `-1` border). Otherwise, either the right neighbor is bigger (climb right) or both neighbors are smaller (but we already checked that case above and would have returned already) — so moving right is always the correct choice when we don't move left.

---

## 6. Edge Cases to Consider

1. **Single-cell matrix** — `mat = [[5]]` → trivially a peak (all four neighbors are `-1`).
2. **Single-row matrix** — e.g., `mat = [[1,3,2]]` → degenerates into the 1D peak-finding logic almost exactly, since there's no vertical dimension to worry about (n=1, so `maxRow` is always 0).
3. **Single-column matrix** — e.g., `mat = [[1],[3],[2]]` → the binary search over columns only has one column to consider (m=1), so the algorithm immediately checks that single column's max.
4. **Peak at a corner** — see Dry Run 3, where the peak (9) sits at the bottom-right corner, relying on the `-1` border for two of its four neighbor checks.
5. **Multiple valid peaks** — as noted in Example 1's explanation, both `(1,1)` and `(2,2)` are valid peaks in that matrix; the algorithm returns whichever one its binary search path happens to converge on first, which is a valid answer per the problem's "any peak" requirement.

---

## 7. Related Concepts / Follow-Ups

- **Find Peak Element (1D)**: The foundational version of this problem — understanding that solution's "climb toward a peak" logic is essential groundwork before tackling this 2D generalization.
- **Search a 2D Matrix / Search a 2D Matrix II**: Other 2D matrix problems exploiting sorted or structural properties, though solved with different techniques (staircase search, flattened binary search) rather than this column-based peak search.
- **Saddleback Search**: A related family of 2D search techniques exploiting monotonic structure across two dimensions, conceptually related to both this problem and Search a 2D Matrix II.

---

## 8. Key Takeaways

- This problem elegantly generalizes 1D Find Peak Element by binary searching over **columns**, using each column's **maximum value** as a stand-in for a single "representative" comparison point.
- Picking the column's maximum automatically satisfies the up/down peak conditions "for free," reducing the check to just left/right comparisons — exactly like the 1D version's single neighbor comparison.
- The overall complexity O(n log m) reflects two nested costs: O(log m) for the column binary search, and O(n) for each column-max scan.
- The "no two adjacent cells are equal" constraint ensures every comparison is strictly `>` or `<`, avoiding tie-handling complexity that could otherwise complicate the direction-choosing logic.
