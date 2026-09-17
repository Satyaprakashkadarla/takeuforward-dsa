# Notes: Triangle

## 1. Problem Recap

Given a triangular grid, find the minimum-sum path from the single top cell down to the base, moving only straight down or diagonally down-right at each step.

```
triangle = [[3],
            [-1, 3],
            [-3, 2, 4],
            [8, 8, 1, -4]]

Best path sums to 5.
```

### Bottom-Up Instead of Top-Down
Unlike "Minimum Falling Path Sum" (which naturally worked top-down, since you could start *anywhere* in row 0), this problem has a **fixed single starting point** (the apex) and a **fixed set of ending points** (the whole last row, but we need the overall minimum starting specifically from the top). It turns out to be cleaner to think about this **bottom-up**: figure out, for every cell, "if I were standing here, what's the cheapest way down to the base?" — starting from the trivial answer at the base itself, and working upward.

```
dp[i][j] = triangle[i][j] + min(dp[i+1][j], dp[i+1][j+1])
```

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively compute, from any cell, the minimum path sum to the bottom — starting the whole computation from `(0,0)`.

### Code Logic
```java
private int solve(int[][] triangle, int i, int j) {
    if (i == triangle.length - 1) return triangle[i][j];
    int down = solve(triangle, i+1, j);
    int downRight = solve(triangle, i+1, j+1);
    return triangle[i][j] + Math.min(down, downRight);
}
```

### Dry Run
`triangle = [[1],[1,2],[1,2,4]]`, finding `solve(0,0)`

```
solve(0,0) = 1 + min(solve(1,0), solve(1,1))
solve(1,0) = 1 + min(solve(2,0), solve(2,1)) = 1 + min(1,2) = 1+1 = 2
solve(1,1) = 2 + min(solve(2,1), solve(2,2)) = 2 + min(2,4) = 2+2 = 4
solve(0,0) = 1 + min(2,4) = 1+2 = 3
```

Result: **3** ✅ (matches expected output)

### Complexity
- **Time:** O(2^n) — exponential branching, no caching of repeated (i,j) subproblems.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (Bottom-Up DP, Space-Optimized)

### Idea
Start with the trivial base case — the minimum path sum from any cell in the *last* row is just that cell's own value (nowhere left to go). Then work upward, row by row, computing each cell's minimum path sum using the row directly below it (which has already been fully computed).

### Why In-Place Update on a Single Array Works
```java
int[] dp = triangle[n-1].clone();
for (int i = n-2; i >= 0; i--) {
    for (int j = 0; j <= i; j++) {
        dp[j] = triangle[i][j] + Math.min(dp[j], dp[j+1]);
    }
}
```

At the moment we compute `dp[j] = triangle[i][j] + Math.min(dp[j], dp[j+1])` for row `i`:
- `dp[j]` still holds the value from row `i+1` (we haven't touched index `j` yet in this row's processing — we're overwriting it right now for the first time this row).
- `dp[j+1]` **also** still holds its row `i+1` value, because we process `j` in increasing order (`0, 1, 2, ...`), and `j+1` won't be *read* again until we're computing `dp[j+1]` itself in a *later* iteration of this same inner loop, by which point we're overwriting a **different** index than the one we already used at `dp[j]`.

Wait — actually there's a subtlety worth spelling out clearly: since we go `j = 0, 1, 2, ..., i`, when we compute `dp[j]`, we read `dp[j]` and `dp[j+1]` — and `dp[j+1]` has NOT yet been overwritten this row (since we haven't reached iteration `j+1` yet), so it still correctly holds row `i+1`'s value. This confirms the in-place update is safe and correct.

### Dry Run 1
`triangle = [[1],[1,2],[1,2,4]]`

`dp = [1, 2, 4]` (row 2, the last row)

**Row 1** (`i=1`, values `[1,2]`):
- j=0: dp[0] = 1 + min(dp[0]=1, dp[1]=2) = 1+1 = 2
- j=1: dp[1] = 2 + min(dp[1]=2, dp[2]=4) = 2+2 = 4

`dp = [2, 4, 4]`

**Row 0** (`i=0`, value `[1]`):
- j=0: dp[0] = 1 + min(dp[0]=2, dp[1]=4) = 1+2 = 3

`dp = [3, 4, 4]`

Return `dp[0] = 3`.

Result: **3** ✅ (matches expected output)

### Dry Run 2
`triangle = [[1],[4,7],[4,10,50],[-50,5,6,-100]]`

`dp = [-50, 5, 6, -100]` (row 3)

**Row 2** (`i=2`, values `[4,10,50]`):
- j=0: dp[0] = 4 + min(-50, 5) = 4+(-50) = -46
- j=1: dp[1] = 10 + min(5, 6) = 10+5 = 15
- j=2: dp[2] = 50 + min(6, -100) = 50+(-100) = -50

`dp = [-46, 15, -50, -100]`

**Row 1** (`i=1`, values `[4,7]`):
- j=0: dp[0] = 4 + min(-46, 15) = 4+(-46) = -42
- j=1: dp[1] = 7 + min(15, -50) = 7+(-50) = -43

`dp = [-42, -43, -50, -100]`

**Row 0** (`i=0`, value `[1]`):
- j=0: dp[0] = 1 + min(-42, -43) = 1+(-43) = -42

`dp = [-42, ...]`

Return `dp[0] = -42`.

Result: **-42** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`triangle = [[3],[-1,3],[-3,2,4],[8,8,1,-4]]`

`dp = [8, 8, 1, -4]` (row 3)

**Row 2** (`i=2`, values `[-3,2,4]`):
- j=0: dp[0] = -3 + min(8, 8) = -3+8 = 5
- j=1: dp[1] = 2 + min(8, 1) = 2+1 = 3
- j=2: dp[2] = 4 + min(1, -4) = 4+(-4) = 0

`dp = [5, 3, 0, -4]`

**Row 1** (`i=1`, values `[-1,3]`):
- j=0: dp[0] = -1 + min(5, 3) = -1+3 = 2
- j=1: dp[1] = 3 + min(3, 0) = 3+0 = 3

`dp = [2, 3, 0, -4]`

**Row 0** (`i=0`, value `[3]`):
- j=0: dp[0] = 3 + min(2, 3) = 3+2 = 5

`dp = [5, ...]`

Return `dp[0] = 5`.

**Result: 5** ✅

So for the quiz options `16, 5, 0, -2`, the correct answer is **5**.

### Complexity
- **Time:** O(n^2) — total cells across the triangle sum to `n(n+1)/2`, which is O(n^2).
- **Space:** O(n) — a single array sized to the longest (last) row.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Bottom-Up DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n^2) |
| Space Complexity | O(n) | O(n) |
| Practical for n up to 200? | No — exponential blowup | Yes — at most ~20,000 operations |

---

## 5. Why Bottom-Up Instead of Top-Down Here?

You might wonder why this problem is naturally solved bottom-up, while "Minimum Falling Path Sum" was solved top-down. The key difference: "Minimum Falling Path Sum" allowed starting at *any* cell in the first row, which meant initializing `dp` directly from row 0's raw values (each cell trivially "starts" there) and propagating downward made sense. Here, there's only **one** starting cell (the apex), but *conceptually* every cell in the triangle could be asked "what's my best path to the bottom?" — and the bottom row's answer is the only *trivial* base case available (each bottom cell's "path" is just itself). So working from that known, trivial bottom-row base case upward toward the single apex is the natural direction here.

---

## 6. Edge Cases to Consider

1. **Single-row triangle (n=1)** — the triangle is just `[[x]]`; the answer is trivially `x` itself (loop doesn't execute since `n-2 = -1 < 0`).
2. **All negative values** — the algorithm handles negatives naturally via `Math.min`; see Example 2 and the "your turn" case for triangles with negative entries.
3. **Two-row triangle** — e.g., `triangle = [[1],[2,3]]` → answer is `1 + min(2,3) = 3`.
4. **All values equal** — every path sums to the same total (`n * value`), since all choices are equivalent.
5. **Very deep triangle (n=200)** — verifies the O(n^2) approach scales comfortably (about 20,000 cells total), unlike the exponential brute force.

---

## 7. Related Concepts / Follow-Ups

- **Minimum Falling Path Sum**: The rectangular-grid, "start/end anywhere" cousin of this problem, solved top-down instead of bottom-up due to its different start/end flexibility.
- **Unique Paths / Unique Paths II**: Other DP-on-grid problems sharing the "combine results from a small set of neighboring cells" recurrence shape.
- **Pascal's Triangle**: A different (but visually similar) triangular structure, generated by a simple additive recurrence rather than a minimization — worth contrasting the two "triangle" problems conceptually.

---

## 8. Key Takeaways

- Triangle is solved bottom-up: start from the trivial base case at the last row (each cell's answer is itself), and propagate the minimum-path-sum computation upward toward the single apex.
- The in-place single-array update (`dp[j] = triangle[i][j] + min(dp[j], dp[j+1])`, processing `j` left to right) is safe because `dp[j+1]` hasn't been overwritten yet for the current row at the point it's read.
- This bottom-up direction contrasts with "Minimum Falling Path Sum," which was naturally top-down due to its "start anywhere in row 0" flexibility — recognizing which direction fits a given DP-on-grid problem's start/end constraints is a valuable skill.
- Both approaches (top-down for rectangular grids, bottom-up for triangles) share the same core theme: build up a solution using a small, fixed set of "neighboring" DP values from an adjacent row.
