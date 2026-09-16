# Notes: Minimum Falling Path Sum

## 1. Problem Recap

Starting from **any** cell in the first row, find the minimum-sum path to **any** cell in the last row, moving each step to the cell directly below, or diagonally below-left/below-right.

```
matrix = [[4,3,4],
          [4,5,1],
          [4,6,2],
          [4,1,4]]

Best path: 3 (row0,col1) -> 1 (row1,col2) -> 2 (row2,col2) -> 1 (row3,col1)
Sum = 3+1+2+1 = 7
```

### The Recurrence
Define `dp[i][j]` = minimum path sum to reach cell `(i,j)` from *any* valid starting cell in row 0. Since the move into `(i,j)` could have come from `(i-1,j-1)`, `(i-1,j)`, or `(i-1,j+1)`:

```
dp[i][j] = matrix[i][j] + min(dp[i-1][j-1], dp[i-1][j], dp[i-1][j+1])
```

(with boundary checks when `j-1` or `j+1` falls outside the row). The final answer is `min` over the entire last row of `dp`.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively compute, from any cell, the minimum path sum needed to reach the *bottom* row — then take the minimum starting point across row 0.

### Code Logic
```java
private int solve(int[][] matrix, int i, int j, int m, int n) {
    if (i == m - 1) return matrix[i][j];
    int best = solve(matrix, i+1, j, m, n);
    if (j > 0) best = Math.min(best, solve(matrix, i+1, j-1, m, n));
    if (j+1 < n) best = Math.min(best, solve(matrix, i+1, j+1, m, n));
    return matrix[i][j] + best;
}
```
Called for every starting column in row 0, taking the overall minimum.

### Dry Run
`matrix = [[1,4,3,1],[2,3,-1,-1],[1,1,-1,8]]`, checking `solve(0,3)` (starting at the 4th cell of row 0, value 1)

```
solve(0,3) [value=1]:
  down: solve(1,3) [value=-1]
    down: solve(2,3)=8 (last row)
    left: solve(2,2)=-1 (last row)
    best = min(8,-1) = -1
    solve(1,3) = -1 + (-1) = -2
  left: solve(1,2) [value=-1]
    down: solve(2,2)=-1
    left: solve(2,1)=1
    right: solve(2,3)=8
    best = min(-1,1,8) = -1
    solve(1,2) = -1 + (-1) = -2
  best = min(-2,-2) = -2
solve(0,3) = 1 + (-2) = -1
```

Result: **-1** ✅ (matches Example 2's expected output)

### Complexity
- **Time:** O(3^m) — up to 3 branches per row level, no caching.
- **Space:** O(m) — recursion stack depth.

---

## 3. Approach 2: Optimal (Row-by-Row DP, Space-Optimized)

### Idea
Compute `dp[j]` iteratively row by row, starting from row 0's raw values (since we can start anywhere in row 0). For each subsequent row, each column's new value is its own matrix value plus the minimum of the three "parent" cells directly above it.

### Code Logic
```java
int[] dp = matrix[0].clone();

for (int i = 1; i < m; i++) {
    int[] next = new int[n];
    for (int j = 0; j < n; j++) {
        int best = dp[j];
        if (j > 0) best = Math.min(best, dp[j-1]);
        if (j+1 < n) best = Math.min(best, dp[j+1]);
        next[j] = matrix[i][j] + best;
    }
    dp = next;
}

int ans = dp[0];
for (int j = 1; j < n; j++) ans = Math.min(ans, dp[j]);
return ans;
```

### Dry Run 1
`matrix = [[1,2,10,4],[100,3,2,1],[1,1,20,2],[1,2,2,1]]`

`dp = [1, 2, 10, 4]` (row 0)

**Row 1** (`[100,3,2,1]`):
- j=0: best=min(1,2)=1 → next0=100+1=101
- j=1: best=min(2,1,10)=1 → next1=3+1=4
- j=2: best=min(10,2,4)=2 → next2=2+2=4
- j=3: best=min(4,10)=4 → next3=1+4=5

`dp = [101, 4, 4, 5]`

**Row 2** (`[1,1,20,2]`):
- j=0: best=min(101,4)=4 → next0=1+4=5
- j=1: best=min(4,101,4)=4 → next1=1+4=5
- j=2: best=min(4,4,5)=4 → next2=20+4=24
- j=3: best=min(5,4)=4 → next3=2+4=6

`dp = [5, 5, 24, 6]`

**Row 3** (`[1,2,2,1]`):
- j=0: best=min(5,5)=5 → next0=1+5=6
- j=1: best=min(5,5,24)=5 → next1=2+5=7
- j=2: best=min(24,5,6)=5 → next2=2+5=7
- j=3: best=min(6,24)=6 → next3=1+6=7

`dp = [6, 7, 7, 7]`

Final answer: `min(6,7,7,7) = 6`

Result: **6** ✅ (matches expected output)

### Dry Run 2 — "Your Turn" Case
`matrix = [[4,3,4],[4,5,1],[4,6,2],[4,1,4]]`

`dp = [4, 3, 4]` (row 0)

**Row 1** (`[4,5,1]`):
- j=0: best=min(4,3)=3 → next0=4+3=7
- j=1: best=min(3,4,4)=3 → next1=5+3=8
- j=2: best=min(4,3)=3 → next2=1+3=4

`dp = [7, 8, 4]`

**Row 2** (`[4,6,2]`):
- j=0: best=min(7,8)=7 → next0=4+7=11
- j=1: best=min(8,7,4)=4 → next1=6+4=10
- j=2: best=min(4,8)=4 → next2=2+4=6

`dp = [11, 10, 6]`

**Row 3** (`[4,1,4]`):
- j=0: best=min(11,10)=10 → next0=4+10=14
- j=1: best=min(10,11,6)=6 → next1=1+6=7
- j=2: best=min(6,10)=6 → next2=4+6=10

`dp = [14, 7, 10]`

Final answer: `min(14,7,10) = 7`

**Result: 7** ✅

So for the quiz options `8, 10, 19, 7`, the correct answer is **7**.

### Complexity
- **Time:** O(m × n) — every cell visited once.
- **Space:** O(n) — two row-sized arrays (`dp` and `next`) at any given time.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Row-by-Row DP) |
|---|---|---|
| Time Complexity | O(3^m) | O(m × n) |
| Space Complexity | O(m) | O(n) |
| Practical for m,n up to 100? | No — exponential blowup | Yes — at most 10,000 operations |

---

## 5. How "Any Start, Any End" Is Handled Without Extra Bookkeeping

A nice detail: the problem allows starting at *any* cell in row 0 and ending at *any* cell in the last row — but the DP handles both of these constraints "for free," with no special extra logic:

- **Any start**: Initializing `dp` directly as `matrix[0].clone()` naturally represents "the minimum cost to reach each cell in row 0, given that we could have started right there" — since starting there costs exactly that cell's value, with nothing added yet.
- **Any end**: Taking the `min` across the *entire* final `dp` array (rather than looking at a single specific cell) naturally represents "whichever ending column turns out cheapest."

No special-casing is needed beyond the natural initialization and final reduction step.

---

## 6. Edge Cases to Consider

1. **Single row (m=1)** — the "path" is just a single cell; the answer is simply the minimum value in that row (no falling moves needed at all).
2. **Single column (n=1)** — no diagonal moves are ever possible (j>0 and j+1<n both fail); the path is forced straight down, and the answer is simply the sum of that single column.
3. **All negative values** — the algorithm handles negative numbers naturally, since `Math.min` doesn't care about sign; see Example 2 for a case with negative values in the optimal path.
4. **All values equal** — every path has the same sum, so the answer is trivially `m * value`.
5. **1x1 matrix** — a single cell serves as both start and end; the answer is that cell's value.

---

## 7. Related Concepts / Follow-Ups

- **Unique Paths / Unique Paths II**: Closely related DP-on-grid problems, though those count paths rather than minimize a sum — the recurrence shape (look at cells "above" in some sense) is similar.
- **Minimum Path Sum** (LeetCode 64): A simpler variant allowing only right/down moves (no diagonals) from a fixed start to a fixed end — worth comparing to see how the "any start, any end, diagonal moves allowed" twist changes the DP setup here.
- **Triangle** (LeetCode 120): A very similar "falling path" problem on a triangular grid instead of a rectangular one, using the same "look at up to 2-3 parent cells above" DP idea.

---

## 8. Key Takeaways

- The core recurrence looks at up to 3 "parent" cells directly above (straight, diagonal-left, diagonal-right) and takes the cheapest continuation, adding the current cell's own value.
- "Start anywhere in the first row" and "end anywhere in the last row" are both handled naturally by the DP's initialization (clone the first row directly) and final reduction (take the min across the whole last row) — no special extra bookkeeping required.
- Space can be optimized to O(n) by only keeping the current and next row's values, rather than a full O(m×n) table.
- This problem is part of a broader "DP on Grids" family alongside Unique Paths, Minimum Path Sum, and Triangle — all sharing the core idea of building up a solution row by row (or level by level) based on a small, fixed set of "parent" cells.
