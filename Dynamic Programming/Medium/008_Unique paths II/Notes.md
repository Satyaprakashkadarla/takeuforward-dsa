# Notes: Unique Paths II

## 1. Problem Recap

Same as "Grid Unique Paths," but now some cells are **blocked** (value `1`) and no path may pass through them.

```
matrix = [[0,0,0],
          [0,1,0],
          [0,0,0]]
Blocked cell at (1,1). Only 2 valid paths remain (going around it via top-right or bottom-left).
```

### Why the Combinatorics Formula No Longer Works
"Grid Unique Paths" had a clean formula `C(m+n-2, m-1)` because *every* path was equally valid — the count only depended on the grid's dimensions. With obstacles, some paths are eliminated depending on *where* they sit relative to blocked cells — this position-dependent elimination can't be captured by a single binomial coefficient, so we need to fall back to a genuine DP-on-grid approach.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Same recursive structure as the obstacle-free version, with an added check: if the current cell is blocked, immediately return 0 (no paths can pass through it).

### Code Logic
```java
private int solve(int[][] matrix, int i, int j, int m, int n) {
    if (i >= m || j >= n || matrix[i][j] == 1) return 0;
    if (i == m-1 && j == n-1) return 1;
    return solve(matrix, i, j+1, m, n) + solve(matrix, i+1, j, m, n);
}
```

### Dry Run
`matrix = [[0,0,0],[0,1,0],[0,0,0]]`

```
solve(0,0):
  solve(0,1) + solve(1,0)

solve(1,0): matrix[1][0]=0, not blocked
  solve(1,1) + solve(2,0)
  solve(1,1): matrix[1][1]=1 -> BLOCKED, return 0
  solve(2,0): matrix[2][0]=0
    solve(2,1) + solve(3,0)[oob,0]
    solve(2,1): matrix[2][1]=0
      solve(2,2) + solve(3,1)[oob,0]
      solve(2,2) = 1 (destination)
    solve(2,1) = 1
  solve(2,0) = 1
solve(1,0) = 0 + 1 = 1

solve(0,1): matrix[0][1]=0
  solve(0,2) + solve(1,1)
  solve(0,2): matrix[0][2]=0
    solve(0,3)[oob,0] + solve(1,2)
    solve(1,2): matrix[1][2]=0
      solve(1,3)[oob,0] + solve(2,2)=1
    solve(1,2) = 1
  solve(0,2) = 1
  solve(1,1) = 0 (blocked, computed above)
solve(0,1) = 1 + 0 = 1

solve(0,0) = 1 + 1 = 2
```

Result: **2** ✅ (matches expected output)

### Complexity
- **Time:** O(2^(m+n)) — exponential branching, no caching.
- **Space:** O(m+n) — recursion stack depth.

---

## 3. Approach 2: Optimal (1D DP on Grid)

### Idea
Since the combinatorics shortcut doesn't apply here, use the standard grid DP, but space-optimize it down to a single 1D array reused across rows (since each row only depends on the row directly above it).

### The Reused-Array Trick
```
dp[j] BEFORE processing row i = number of paths to reach (i-1, j)  [from above]
dp[j] AFTER processing column j in row i = number of paths to reach (i, j)
```

When we compute `dp[j] += dp[j-1]` while processing row `i`:
- `dp[j]` (before this line executes) still holds the value from row `i-1` — i.e., "paths arriving from above."
- `dp[j-1]` has *already* been updated for row `i` earlier in this same inner loop iteration — i.e., "paths arriving from the left, within the current row."

Adding them combines both contributions, exactly replicating the full 2D DP recurrence `dp[i][j] = dp[i-1][j] + dp[i][j-1]`, but using a single reused array.

### Code Logic
```java
if (matrix[0][0] == 1 || matrix[m-1][n-1] == 1) return 0;

int[] dp = new int[n];
dp[0] = 1;

for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        if (matrix[i][j] == 1) {
            dp[j] = 0;
        } else if (j > 0) {
            dp[j] += dp[j - 1];
        }
    }
}
return dp[n - 1];
```

### Dry Run 1
`matrix = [[0,0,0],[0,1,0],[0,0,0]]`

**Init:** `dp = [1, 0, 0]`

**Row 0** (`[0,0,0]`):
- j=0: not blocked, j=0 so no add. dp=[1,0,0]
- j=1: not blocked, dp[1]+=dp[0]=0+1=1. dp=[1,1,0]
- j=2: not blocked, dp[2]+=dp[1]=0+1=1. dp=[1,1,1]

**Row 1** (`[0,1,0]`):
- j=0: not blocked, dp[0] stays 1 (carries from above). dp=[1,1,1]
- j=1: BLOCKED, dp[1]=0. dp=[1,0,1]
- j=2: not blocked, dp[2]+=dp[1]=1+0=1. dp=[1,0,1]

**Row 2** (`[0,0,0]`):
- j=0: not blocked, dp[0] stays 1. dp=[1,0,1]
- j=1: not blocked, dp[1]+=dp[0]=0+1=1. dp=[1,1,1]
- j=2: not blocked, dp[2]+=dp[1]=1+1=2. dp=[1,1,2]

Return `dp[2] = 2`.

Result: **2** ✅ (matches expected output)

### Dry Run 2
`matrix = [[0,0,0],[0,0,1],[0,1,0]]`

Destination `matrix[2][2] = 0` — not blocked itself, so the upfront check passes. Let's trace:

**Init:** `dp = [1,0,0]`

**Row 0** (`[0,0,0]`): dp=[1,1,1] (same as before)

**Row 1** (`[0,0,1]`):
- j=0: dp[0] stays 1. dp=[1,1,1]
- j=1: dp[1]+=dp[0]=1+1=2. dp=[1,2,1]
- j=2: BLOCKED, dp[2]=0. dp=[1,2,0]

**Row 2** (`[0,1,0]`):
- j=0: dp[0] stays 1. dp=[1,2,0]
- j=1: BLOCKED, dp[1]=0. dp=[1,0,0]
- j=2: dp[2]+=dp[1]=0+0=0. dp=[1,0,0]

Return `dp[2] = 0`.

Result: **0** ✅ (matches expected output — the obstacles at (1,2) and (2,1) together seal off all routes to the destination)

### Dry Run 3 — "Your Turn" Case
`matrix = [[0,0,0,0],[0,0,1,0]]`

**Init:** `dp = [1,0,0,0]`

**Row 0** (`[0,0,0,0]`):
- j=0: dp[0] stays 1. dp=[1,0,0,0]
- j=1: dp[1]+=dp[0]=0+1=1. dp=[1,1,0,0]
- j=2: dp[2]+=dp[1]=0+1=1. dp=[1,1,1,0]
- j=3: dp[3]+=dp[2]=0+1=1. dp=[1,1,1,1]

**Row 1** (`[0,0,1,0]`):
- j=0: dp[0] stays 1. dp=[1,1,1,1]
- j=1: dp[1]+=dp[0]=1+1=2. dp=[1,2,1,1]
- j=2: BLOCKED, dp[2]=0. dp=[1,2,0,1]
- j=3: dp[3]+=dp[2]=1+0=1. dp=[1,2,0,1]

Return `dp[3] = 1`.

**Result: 1** ✅

So for the quiz options `0, 2, 1, 4`, the correct answer is **1**.

### Complexity
- **Time:** O(m × n) — visits every cell exactly once.
- **Space:** O(n) — a single reused row-sized array.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (1D DP on Grid) |
|---|---|---|
| Time Complexity | O(2^(m+n)) | O(m × n) |
| Space Complexity | O(m+n) | O(n) |
| Practical for m,n up to 100? | No — exponential blowup | Yes — at most 10,000 operations |

---

## 5. Edge Cases to Consider

1. **Start or end cell is blocked** — e.g., `matrix[0][0] = 1` → immediately return `0`, since no path can even begin (or end). Handled by the explicit upfront check.
2. **A blocked cell completely seals off all routes** — e.g., an entire row or column of blocks positioned such that no path can get around it (see Dry Run 2).
3. **No blocked cells at all** — degenerates to the same result as "Grid Unique Paths" (though computed via DP here rather than the closed-form formula).
4. **Single row or single column with a block** — e.g., `matrix = [[0,1,0]]` → the single possible path is blocked, so the answer is `0`.
5. **1x1 grid** — `matrix = [[0]]` → trivially 1 path (already at destination). `matrix = [[1]]` → `0` (start cell itself is blocked).

---

## 6. Related Concepts / Follow-Ups

- **Grid Unique Paths**: The obstacle-free version, solvable with a direct combinatorics formula — contrast this with the DP-required approach here to appreciate why obstacles change the problem's nature.
- **Minimum Path Sum**: A very similar DP-on-grid structure, but instead of counting paths, it finds the path with the minimum sum of cell values — same "look up and left" recurrence shape, different combining operation (`min` instead of `+`).
- **DP on Grids (as a broader topic)**: This problem is a foundational example of the DP-on-grids family, which also includes problems like "Maximum Path Sum," "Cherry Pickup," and various obstacle/coin-collection variants.

---

## 7. Key Takeaways

- Obstacles break the clean combinatorics formula that worked for the obstacle-free version, forcing a return to genuine DP-on-grid computation.
- The "reuse a single 1D array across rows" trick works because, at the moment `dp[j] += dp[j-1]` executes, `dp[j]` still holds the previous row's value (paths from above) while `dp[j-1]` has already been updated for the current row (paths from the left) — cleverly encoding the full 2D recurrence in a single array.
- Always check the start and end cells for blockage upfront — this is a cheap O(1) check that avoids unnecessary computation when the answer is trivially 0.
- This problem is a great template for the broader "DP on Grids" topic, where the core recurrence is almost always some variation of "combine the result from above and from the left."
