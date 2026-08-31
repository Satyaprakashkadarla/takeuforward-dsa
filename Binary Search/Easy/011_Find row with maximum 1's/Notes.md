# Notes: Find Row With Maximum 1's

## 1. Problem Recap

Given a matrix where every row is individually sorted ascending (so each row looks like `0,0,...,0,1,1,...,1`), find the index of the row containing the most `1`s. Ties go to the smaller index. If there are no `1`s at all, return `-1`.

```
mat = [ [0,0,1],
        [0,1,1],
        [0,1,1] ]

Row 0: one 1
Row 1: two 1s
Row 2: two 1s

Max count = 2, achieved by rows 1 and 2 -> smaller index wins -> answer = 1
```

---

## 2. Approach 1: Brute Force (Linear Scan of Every Cell)

### Idea
For each row, count how many `1`s it has by checking every cell. Track the row with the highest count, using a **strict** `>` comparison (not `>=`) when updating the best answer — this naturally ensures that in a tie, the *first* (smallest index) row with that count is kept, since a later row with an equal count won't overwrite it.

### Code Logic
```java
int maxCount = 0, maxRow = -1;
for (int i = 0; i < n; i++) {
    int count = 0;
    for (int j = 0; j < m; j++) {
        if (mat[i][j] == 1) count++;
    }
    if (count > maxCount) {
        maxCount = count;
        maxRow = i;
    }
}
return maxRow;
```

### Dry Run
`mat = [[0,0,1],[0,1,1],[0,1,1]]`

| row | count | count > maxCount? | maxCount | maxRow |
|---|---|---|---|---|
| 0 | 1 | 1>0 Yes | 1 | 0 |
| 1 | 2 | 2>1 Yes | 2 | 1 |
| 2 | 2 | 2>2? No | 2 | 1 (unchanged) |

Result: **1** ✅ — row 2 ties row 1's count but doesn't overwrite it, correctly preserving the smaller index.

### Complexity
- **Time:** O(n × m) — scans every cell in the matrix.
- **Space:** O(1)

### A Faster Brute-Force Variant (Worth Knowing)
Since each row is individually sorted, you could instead run a binary search (lower bound for the first `1`) on each row to find its count in O(log m), bringing total time down to O(n log m). This is a nice middle-ground optimization, though the truly optimal "staircase search" approach does even better at O(n + m).

---

## 3. Approach 2: Optimal (Staircase Search)

### The Core Insight
Because every row is sorted ascending (`0`s followed by `1`s), if you plot the boundary between `0`s and `1`s across the entire matrix, it traces a "staircase" — as you move down a row, the boundary can only stay the same or shift further left (never right), assuming rows are also somewhat correlated in a sorted matrix context... actually, more precisely: **regardless of any relationship between rows**, the staircase idea works within a single sweep because of how we navigate.

### The Algorithm
Start at the **top-right corner** (`row = 0`, `col = m - 1`):

- **If `mat[row][col] == 1`**: this row has a `1` at this column, meaning it has *at least* this many 1s counted so far. Record this row as our current best (`ans = row`), then move **left** (`col--`) to check if this same row has even more 1s.
- **If `mat[row][col] == 0`**: since the row is sorted, there are no more 1s to find in this row at or before this column. Move **down** (`row++`) to check the next row.

This single sweep — moving left on a `1`, down on a `0` — traces the staircase boundary in a way that visits each row transition and column transition at most once, giving O(n + m) total work rather than O(n × m).

### Why Ties Are Handled Correctly
The pointer `col` only ever moves left when we find a `1`. This means a *later* row can only overwrite `ans` if it manages to push `col` even further left than the previous best row did — i.e., strictly more 1s. If a later row has the *same* count of 1s as the current best, it will hit a `0` at the current `col` position before ever reaching a `1` there, and will simply move down without updating `ans`. This is what correctly preserves the smaller index on ties.

### Dry Run 1
`mat = [[1,1,1],[0,0,1],[0,0,0]]`

| Step | row | col | mat[row][col] | Action | ans |
|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | ans=0, col-- | 0 |
| 2 | 0 | 1 | 1 | ans=0, col-- | 0 |
| 3 | 0 | 0 | 1 | ans=0, col-- | 0 |
| 4 | 0 | -1 | — | col<0, **loop ends** | 0 |

Result: **0** ✅ (matches expected output)

### Dry Run 2
`mat = [[0,0],[0,0]]`

| Step | row | col | mat[row][col] | Action | ans |
|---|---|---|---|---|---|
| 1 | 0 | 1 | 0 | row++ | -1 |
| 2 | 1 | 1 | 0 | row++ | -1 |
| 3 | 2 | 1 | — | row>=n, **loop ends** | -1 |

Result: **-1** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`mat = [[0,0,1],[0,1,1],[0,1,1]]`

| Step | row | col | mat[row][col] | Action | ans |
|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | ans=0, col-- | 0 |
| 2 | 0 | 1 | 0 | row++ | 0 |
| 3 | 1 | 1 | 1 | ans=1, col-- | 1 |
| 4 | 1 | 0 | 0 | row++ | 1 |
| 5 | 2 | 0 | 0 | row++ | 1 |
| 6 | 3 | 0 | — | row>=n, **loop ends** | 1 |

**Result: 1** ✅

So for the quiz options `-1, 0, 2, 1`, the correct answer is **1**.

### Complexity
- **Time:** O(n + m) — `row` only ever increases (up to `n` times) and `col` only ever decreases (up to `m` times), so total pointer movement is bounded by `n + m`.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (Full Scan) | Optimal (Staircase Search) |
|---|---|---|
| Time Complexity | O(n × m) | O(n + m) |
| Space Complexity | O(1) | O(1) |
| Uses row-sorted property? | No | Yes (fully) |
| Handles ties correctly? | Yes (via strict `>`) | Yes (naturally, via monotonic col movement) |

For a `100 × 100` matrix (max per constraints), brute force does up to `10,000` cell checks, while the staircase search does at most `200` (`100 + 100`) pointer movements — a significant improvement, and the difference would be even more pronounced for larger matrices.

---

## 5. Edge Cases to Consider

1. **No 1s anywhere** — e.g., `mat = [[0,0],[0,0]]` → `-1` (see Dry Run 2).
2. **All 1s in every row** — e.g., `mat = [[1,1],[1,1]]` → answer is `0` (tie, smaller index wins).
3. **Only the last row has any 1s** — e.g., `mat = [[0,0],[0,0],[0,1]]` → answer is `2`.
4. **Single row matrix** — e.g., `mat = [[0,1,1]]` → answer is `0` (trivially, since it's the only row, assuming it has at least one 1) or `-1` if that row is all 0s.
5. **Single column matrix** — e.g., `mat = [[0],[1],[1]]` → each row has at most one 1; answer is the first row containing a `1` (index 1 here, since row 0 is `[0]`).
6. **Every row identical** — e.g., `mat = [[0,1],[0,1],[0,1]]` → answer is `0` (all tied, smallest index wins).

---

## 6. Related Concepts / Follow-Ups

- **Search a 2D Matrix**: A related family of problems involving matrices with row-wise and/or column-wise sorted structure, often solvable with similar staircase or binary search techniques.
- **Row-by-Row Binary Search**: The O(n log m) middle-ground approach mentioned above — worth knowing as a stepping stone between brute force and the fully optimal staircase search.
- **Search a 2D Matrix II** (LeetCode 240): A very similar staircase-search technique is used there to search for a target value in a matrix sorted both row-wise and column-wise, starting from a corner and moving based on comparisons — strong conceptual overlap with this problem.

---

## 7. Key Takeaways

- The staircase search technique exploits the fact that each row is individually sorted, letting us start from a corner and make a single, monotonic sweep across the matrix — no need to fully scan or even binary search each row independently.
- Moving **left on a 1** and **down on a 0**, starting from the **top-right corner**, is the key mechanical pattern to remember for this class of problems.
- Ties are handled correctly "for free" by the algorithm's structure — a later row can only overwrite the answer by having strictly more 1s (pushing `col` further left than any previous row managed).
- This staircase pattern generalizes to other problems involving row-and/or-column sorted matrices, such as "Search a 2D Matrix II" — recognizing the pattern here builds intuition transferable to that broader problem family.
