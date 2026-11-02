# Notes: Cut Matrix (Ways to Cut a Pizza)

## 1. Problem Recap

Cut a 0/1 matrix into exactly `k` pieces so that **every piece contains at least one 1**. After each cut you keep only the **bottom** part (horizontal cut) or the **right** part (vertical cut) for further cutting; the other part is a finished piece. Count the ways, modulo `10^9 + 7`.

```
mat = [[1, 0, 0],
       [1, 1, 1],
       [0, 0, 0]],  k = 3

Ways:
 1) horizontal cut after row 0, then vertical cut after col 0 (bottom part)
 2) horizontal cut after row 0, then vertical cut after col 1 (bottom part)
 3) vertical cut after col 0, then vertical cut after col 1 (right part)
```

### The Key Observation: The Remaining Piece Is Always a Bottom-Right Block
Cutting off the top strip or the left strip and keeping the rest means the piece we continue with always extends to the **bottom-right corner**. It is fully described by its top-left corner `(r, c)`. That gives a compact state:

```
dp[r][c][p] = number of ways to cut the block starting at (r, c) into p valid pieces
```

---

## 2. The Two Building Blocks

### (a) A 2D Suffix Sum of Ones
`suf[r][c]` = number of 1s in the block from `(r, c)` to the bottom-right corner:

```
suf[r][c] = mat[r][c] + suf[r+1][c] + suf[r][c+1] - suf[r+1][c+1]
```

The number of 1s in the top strip when cutting the block at `(r, c)` after row `i` is `suf[r][c] - suf[i+1][c]`. So **"the strip has a 1" is a single comparison**: `suf[i+1][c] < suf[r][c]`. The same works for vertical strips with `suf[r][j+1]`.

### (b) Valid Cuts Form a Contiguous Range
As the cut moves further down (larger `i`), the top strip only grows, so once it contains a 1 it keeps containing one. Equivalently, `suf[i+1][c]` never increases with `i`. Therefore the valid horizontal cuts are `i = validRow, validRow + 1, ..., n - 2`: a **suffix of the range** `[r, n-2]`. The same holds for vertical cuts.

This means we do not need to loop over every cut:

1. **Binary search** finds `validRow`, the first valid cut.
2. A **precomputed suffix sum** of the DP values adds up all valid cuts in one lookup.

---

## 3. Approach 1: Brute Force (Plain Recursion)

### Idea
`solve(r, c, p)`: try every horizontal cut and every vertical cut, require the finished strip to contain a 1 (by scanning it), and recurse on the remaining block with `p - 1` pieces.

### Code Logic
```java
private long solve(int[][] mat, int r, int c, int p) {
    if (p == 1) return hasOne(mat, r, c, n, m) ? 1 : 0;

    long ways = 0;
    for (int i = r; i <= n - 2; i++)                 // horizontal cut after row i
        if (hasOne(mat, r, c, i + 1, m))
            ways += solve(mat, i + 1, c, p - 1);
    for (int j = c; j <= m - 2; j++)                 // vertical cut after column j
        if (hasOne(mat, r, c, n, j + 1))
            ways += solve(mat, r, j + 1, p - 1);
    return ways % MOD;
}
```

### Dry Run
`mat = [[0, 0], [1, 1]]`, `k = 2`, start `solve(0, 0, 2)`:

- Horizontal cut after row 0: top strip is `[0, 0]`, no 1, so **invalid**.
- Vertical cut after column 0: left strip is `[[0], [1]]`, has a 1, valid. Continue with `solve(0, 1, 1)`: the block `[[0], [1]]` has a 1, so it returns 1.

Total = **1** ✅

### Complexity
- **Time:** exponential, about `(n + m)^k` calls (each with up to `n + m` branches), plus a scan per check.
- **Space:** O(k) recursion depth.

---

## 4. Middle Ground: Memoized DP, Loop Over Every Cut

Cache `dp[r][c][p]` and use the suffix sum `suf` for the "has a 1" test, but still **loop over every cut position**:

```java
for (int i = r; i < n - 1; i++)  if (suf[r][c] - suf[i+1][c] > 0) ways += dp[i+1][c][p-1];
for (int j = c; j < m - 1; j++)  if (suf[r][c] - suf[r][j+1] > 0) ways += dp[r][j+1][p-1];
```

- **Time:** O(k * n * m * (n + m)). At `n = m = k = 200` that is about **3.2 * 10^9** steps, too slow.
- **Space:** O(n * m * k).

The provided solution replaces the inner loops by a binary search and a lookup, which is the whole optimization.

---

## 5. Approach 2: Optimal (as Provided)

### Step by Step
1. Build `suf`.
2. `dp[r][c][1] = 1` if `suf[r][c] > 0`, else 0.
3. For each `p = 2..k`:
   - Build `sufRow[r][c]` = `dp[r][c][p-1] + dp[r+1][c][p-1] + ... + dp[n-1][c][p-1]` (suffix sums down each **column**).
   - Build `sufCol[r][c]` = `dp[r][c][p-1] + dp[r][c+1][p-1] + ... + dp[r][m-1][p-1]` (suffix sums along each **row**).
   - For every `(r, c)`:
     - **Horizontal:** binary search `validRow` = smallest `i` in `[r, n-2]` with `suf[i+1][c] < suf[r][c]`. If it exists, add `sufRow[validRow + 1][c]`.
     - **Vertical:** binary search `validCol` = smallest `j` in `[c, m-2]` with `suf[r][j+1] < suf[r][c]`. If it exists, add `sufCol[r][validCol + 1]`.
4. Answer = `dp[0][0][k]`.

### Why `sufRow[validRow + 1][c]` Is the Right Sum
Valid cuts are `i = validRow .. n-2`. A cut after row `i` continues with the block starting at row `i + 1`, so we need `dp[i+1][c][p-1]` for `i + 1 = validRow + 1 .. n - 1`. That is exactly the suffix sum starting at row `validRow + 1` in column `c`.

### Dry Run 1: Example 1
`mat = [[1,0,0],[1,1,1],[0,0,0]]`, `k = 3`.

**Suffix sums of ones** (`suf`, the 3 x 3 part):

| | c=0 | c=1 | c=2 |
|---|---|---|---|
| r=0 | 4 | 2 | 1 |
| r=1 | 3 | 2 | 1 |
| r=2 | 0 | 0 | 0 |

**Layer p = 1** (`dp[r][c][1]`, 1 if the block has a 1):

| | c=0 | c=1 | c=2 |
|---|---|---|---|
| r=0 | 1 | 1 | 1 |
| r=1 | 1 | 1 | 1 |
| r=2 | 0 | 0 | 0 |

**Layer p = 2.** Two cells worked out in detail:

- `dp[0][0][2]` (whole matrix, `totalOnes = 4`):
  - Horizontal: cuts `i` in `[0, 1]`. `i = 0`: `suf[1][0] = 3 < 4`, so valid, `validRow = 0`. Add `sufRow[1][0] = dp[1][0][1] + dp[2][0][1] = 1 + 0 = 1`.
  - Vertical: cuts `j` in `[0, 1]`. `j = 0`: `suf[0][1] = 2 < 4`, so valid, `validCol = 0`. Add `sufCol[0][1] = dp[0][1][1] + dp[0][2][1] = 1 + 1 = 2`.
  - Total = **3**.
- `dp[1][0][2]` (rows 1..2, `[[1,1,1],[0,0,0]]`, `totalOnes = 3`):
  - Horizontal: only `i = 1`. `suf[2][0] = 0 < 3`, valid, but the bottom block `[0,0,0]` has no 1: `sufRow[2][0] = dp[2][0][1] = 0`. Adds 0.
  - Vertical: `j` in `[0, 1]`. `j = 0`: `suf[1][1] = 2 < 3`, valid. Add `sufCol[1][1] = dp[1][1][1] + dp[1][2][1] = 2`.
  - Total = **2**.

Full layer p = 2:

| | c=0 | c=1 | c=2 |
|---|---|---|---|
| r=0 | 3 | 1 | 0 |
| r=1 | 2 | 1 | 0 |
| r=2 | 0 | 0 | 0 |

**Layer p = 3**, final cell `dp[0][0][3]` (`totalOnes = 4`):

- Horizontal: `validRow = 0`, add `sufRow[1][0]` over layer 2 = `dp[1][0][2] + dp[2][0][2] = 2 + 0 = 2`.
- Vertical: `validCol = 0`, add `sufCol[0][1]` over layer 2 = `dp[0][1][2] + dp[0][2][2] = 1 + 0 = 1`.
- Total = **3**.

Layer p = 3: `dp[0][0] = 3`, `dp[1][0] = 1`, all other cells 0.

Result: **3** ✅ (matches expected output)

The numbers map directly to the three ways in the problem statement: the **2** from `dp[1][0][2]` are the two "horizontal cut after row 0, then a vertical cut in the bottom part" ways, and the **1** from `dp[0][1][2]` is the "vertical cut after col 0, then a vertical cut after col 1" way.

### Dry Run 2: Example 2
`mat = [[0,0],[1,1]]`, `k = 2`. `suf[0] = [2, 1]`, `suf[1] = [2, 1]`, and `dp[.][.][1] = 1` for all four cells (every block contains a 1).

`dp[0][0][2]`, `totalOnes = 2`:
- Horizontal: range `[0, 0]`. `suf[1][0] = 2 < 2`? **No**, so no valid row. (The top strip `[0, 0]` has no 1.)
- Vertical: range `[0, 0]`. `suf[0][1] = 1 < 2`, valid, `validCol = 0`. Add `sufCol[0][1] = dp[0][1][1] = 1`.
- Total = **1**.

Result: **1** ✅ (matches expected output)

### Dry Run 3: Example 3
`mat = [[1,0],[0,0]]`, `k = 1`. The loop over `p` never runs; the answer is `dp[0][0][1]`, which is 1 because `suf[0][0] = 1 > 0`.

Result: **1** ✅ (matches expected output)

### Complexity
- **Time:** O(k * n * m * (log n + log m)). For `n = m = k = 200` that is about **1.2 * 10^8** steps.
- **Space:** O(n * m * k) for `dp` (about 8 million ints, roughly 32 MB at the maximum), plus O(n * m) for the helper arrays that are rebuilt for each `p`. Since layer `p` only needs layer `p - 1`, this can be reduced to O(n * m) by keeping two layers.

---

## 6. Comparing the Approaches

| Aspect | Brute Force | Memoized, loop over cuts | Optimal (as provided) |
|---|---|---|---|
| Time | Exponential | O(k * n * m * (n + m)), about 3.2 * 10^9 | O(k * n * m * (log n + log m)), about 1.2 * 10^8 |
| Space | O(k) | O(n * m * k) | O(n * m * k) |
| Max input (200 x 200, k = 200) | Impossible | Too slow | Fast enough |

---

## 7. Implementation Notes on the Provided Code

- **A comment nuance:** the original comment says "first row `i` where `suf[i][c] < totalOnes`", but the code actually tests `suf[mid + 1][c]`, which is the correct condition (the number of 1s *below* the cut). The code is right; the comment is just loosely worded.
- **Empty ranges:** when `r = n - 1`, the range `[r, n-2]` is empty, the loop does not run, and `validRow` stays `-1`, so no horizontal cuts are counted. The same applies to columns.
- **`sufRow` has `n + 1` rows** so that `sufRow[validRow + 1][c]` is always in range (`validRow + 1 <= n - 1`), and index `n` is a harmless zero row.
- **Modular arithmetic:** values are reduced after every addition, `dp` entries stay below `10^9 + 7` so they fit in `int`, and `long` is used for the running sums to avoid overflow.
- **Layer order:** `p` is the outer loop, so everything needed for layer `p` (layer `p - 1`) is complete before it is read.

---

## 8. Edge Cases to Consider

1. **`k = 1`:** the answer is 1 if the matrix contains any 1, otherwise 0 (Example 3).
2. **All zeros:** every `suf` is 0, so every `dp` is 0 and the answer is 0.
3. **`k` larger than the number of 1s:** each piece needs its own 1, so the answer is 0; the DP produces this naturally.
4. **`k` larger than the number of possible pieces** (for example a 1 x 1 matrix with `k = 2`): no cut positions exist, so the answer is 0.
5. **Single row or single column:** only one cut direction is possible, and the other binary search range is empty.
6. **Large counts:** the number of ways can be huge, which is why everything is reduced modulo `10^9 + 7`.
7. **A cut that would leave a 1-free piece:** excluded by the `suf[...] < totalOnes` test for the finished strip and by `dp = 0` for a 1-free remaining block.

---

## 9. Related Concepts / Follow-Ups

- **Number of Ways of Cutting a Pizza** (LeetCode 1444): this problem under another name.
- **2D prefix / suffix sums:** the "number of 1s in any rectangle in O(1)" tool used throughout matrix DP.
- **Binary search on a monotone condition:** the same "valid positions form a suffix" idea as in Koko Eating Bananas and Aggressive Cows, but applied inside a DP transition.
- **Range-sum optimization of DP transitions:** replacing "sum over a range of earlier states" with a prefix or suffix sum, as in many counting DPs.

---

## 10. Key Takeaways

- The "only the bottom or right part can be cut" rule collapses the state to a top-left corner plus a piece count.
- A 2D suffix sum answers "does this strip contain a 1?" in O(1).
- The valid cut positions form a contiguous range, so one binary search plus one precomputed suffix sum replaces a loop over all cuts, bringing 3.2 * 10^9 steps down to about 1.2 * 10^8.
- Reduce modulo after every addition and keep sums in `long`.
