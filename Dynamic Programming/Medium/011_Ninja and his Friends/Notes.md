# Notes: Ninja and his Friends

## 1. Problem Recap

Alice starts at `(0,0)`, Bob starts at `(0,C-1)`. Both move one row down at a time, each choosing to go straight down, down-left, or down-right. Collect all chocolates along both paths, counting shared cells only once. Maximize the total.

```
grid = [[10, 1, 10],
        [ 1, 1,  1],
        [ 1, 1,  1]]

Best total: 24
```

### The State Space: A Genuine 3D DP
The state here needs **three** dimensions: the current row, Alice's column, and Bob's column. This is a step up from "Ninja's Training" (which needed 2D state: day × last-activity) — here we need row × AliceCol × BobCol.

```
dp[row][j1][j2] = maximum chocolates collectible from row 0 through `row`,
                  given Alice ends up at column j1 and Bob at column j2
```

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively explore all combinations of Alice's and Bob's moves (9 combinations per row: 3 choices each), collecting chocolates along the way and avoiding double-counting shared cells.

### Code Logic
```java
private int solve(int[][] g, int row, int j1, int j2) {
    int chocolates = g[row][j1];
    if (j1 != j2) chocolates += g[row][j2];

    if (row == r - 1) return chocolates;

    int best = 0;
    for (int d1 = -1; d1 <= 1; d1++) {
        for (int d2 = -1; d2 <= 1; d2++) {
            int nj1 = j1+d1, nj2 = j2+d2;
            if (out of bounds) continue;
            best = Math.max(best, solve(g, row+1, nj1, nj2));
        }
    }
    return chocolates + best;
}
```

### Dry Run (Conceptual)
`grid = [[1,2],[3,4]]`, starting `solve(0, 0, 1)`

```
solve(0,0,1): chocolates = g[0][0]+g[0][1] = 1+2 = 3 (j1≠j2)
  row=0 is not last row (r=2), so explore next row
  Alice from col0: can go to col -1(invalid), 0, 1
  Bob from col1: can go to col 0, 1, 2(invalid)
  Valid (nj1,nj2) combos: (0,0),(0,1),(1,0),(1,1)

  solve(1,0,0): chocolates = g[1][0] (j1==j2, counted once) = 3. Last row -> return 3
  solve(1,0,1): chocolates = g[1][0]+g[1][1] = 3+4 = 7. Last row -> return 7
  solve(1,1,0): chocolates = g[1][1]+g[1][0] = 4+3 = 7. Last row -> return 7
  solve(1,1,1): chocolates = g[1][1] (same cell) = 4. Last row -> return 4

  best = max(3,7,7,4) = 7
solve(0,0,1) = 3 + 7 = 10
```

Result: **10** ✅ (matches expected output)

### Complexity
- **Time:** O(3^(2R)) — 9 branches per row (3 for Alice × 3 for Bob), repeated across R rows with no caching.
- **Space:** O(R) — recursion stack depth.

---

## 3. Approach 2: Optimal (Row-by-Row DP, 3D State Collapsed to 2D Layers)

### Idea
Since row `i`'s state only depends on row `i-1`'s state, we don't need to store all R layers simultaneously — just the current layer (a `C x C` table) and the next layer being built. This collapses what's conceptually a 3D DP table down to a rolling pair of 2D tables.

### Initialization
```java
dp[0][c-1] = g[0][0] + (c > 1 ? g[0][c-1] : 0);
// everything else in dp is UNREACHABLE (Integer.MIN_VALUE)
```
This reflects that Alice and Bob have FIXED starting columns (0 and c-1) — no other (j1,j2) pair is possible for row 0.

### Transition
For each row, and each candidate (j1, j2) pair, check all 9 possible "where did Alice and Bob come from" combinations in the previous row's table, take the best reachable value, then add this row's chocolates (avoiding double-count if j1==j2).

### Dry Run 1
`grid = [[10,1,10],[1,1,1],[1,1,1]]`, `c=3`

**Row 0:** `dp[0][2] = 10 + 10 = 20`. Everything else unreachable.

**Row 1** (`g[1] = [1,1,1]`):

Valid (j1,j2) targets need at least one reachable (p1,p2) with p1=j1+d1, p2=j2+d2 landing on `(0,2)` (the only reachable row-0 state).

- j1=0,j2=1: reachable via p1=0(d1=0),p2=2(d2=1) → best=20. choc=1+1=2 (j1≠j2). next[0][1]=22
- j1=0,j2=2: p1=0,p2=2(d2=0) → best=20. choc=1+1=2. next[0][2]=22
- j1=1,j2=1: p1=0(d1=-1),p2=2(d2=-1) → best=20. choc=1 (same cell). next[1][1]=21
- j1=1,j2=2: p1=0(d1=-1),p2=2(d2=0) → best=20. choc=1+1=2. next[1][2]=22

All other (j1,j2) unreachable (no valid path from (0,2)).

`dp (row1) = {(0,1):22, (0,2):22, (1,1):21, (1,2):22}` (rest MIN)

**Row 2** (`g[2] = [1,1,1]`):

Checking target (j1=0, j2=2): need p1∈{-1,0,1}→valid{0,1}, p2∈{1,2,3}→valid{1,2}. Sources available: (0,1)=22, (0,2)=22, (1,1)=21, (1,2)=22 — all satisfy p1∈{0,1}, p2∈{1,2}. best=22. choc=1+1=2 (j1≠j2). **next[0][2] = 22+2 = 24**

Checking target (j1=1, j2=2): p1∈{0,1,2}→valid{0,1}, p2∈{1,2,3}→valid{1,2}. Same sources qualify. best=22. choc=2. next[1][2]=24.

Checking target (j1=0, j2=1): p1∈{0,1}, p2∈{0,1,2}. Sources (0,1),(0,2),(1,1),(1,2) all qualify. best=22. choc=2. next[0][1]=24.

Final max across all (j1,j2): **24**

**Result: 24** ✅

So for the quiz options `24, 45, 34, 12`, the correct answer is **24**.

### Complexity
- **Time:** O(R × C^2) — for each row, C^2 target pairs, each checking a fixed 9 source combinations.
- **Space:** O(C^2) — two `C x C` tables (current and next layer).

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Row-by-Row DP) |
|---|---|---|
| Time Complexity | O(3^(2R)) | O(R × C^2) |
| Space Complexity | O(R) | O(C^2) |
| Practical for R,C up to 50? | No — astronomically slow | Yes — at most 50×50×50×9 ≈ 1.1M operations |

---

## 5. Why Only Add `g[0][c-1]` "If c > 1"

This upfront check in the initialization (`if (c > 1) dp[0][c-1] += g[0][c-1];`) handles a subtle edge case: if the grid has only **1 column** (`c == 1`), then Alice's starting position `(0,0)` and Bob's starting position `(0, c-1) = (0,0)` are the SAME cell. Without this check, `g[0][0]` would get added twice (once as `g[0][0]` and again as `g[0][c-1]`, which is the same value), violating the "count shared cells once" rule right from the very first row.

---

## 6. Edge Cases to Consider

1. **c = 1 (single column)** — Alice and Bob start on the same cell; handled by the `if (c > 1)` check to avoid double-counting.
2. **c = 2 (minimum per constraints)** — Alice and Bob start immediately adjacent; still handled correctly by the general transition logic.
3. **All chocolates are 0** — the answer is trivially `0` regardless of paths chosen.
4. **R = 2 (minimum rows)** — only one transition step needed; the DP still correctly explores all valid next-row combinations.
5. **Alice and Bob forced to converge or stay separate due to grid width** — the boundary checks (`p1/p2` within `[0, c-1]`) correctly prevent invalid off-grid moves at the edges.

---

## 7. Related Concepts / Follow-Ups

- **Ninja's Training**: The simpler 2D-state predecessor (day × last-activity) — this problem extends that idea to a 3D state (row × two independent positions).
- **Cherry Pickup** (LeetCode 1463/741): Extremely similar problems (in fact, LeetCode 1463 "Cherry Pickup II" is essentially this exact problem) — two agents traversing a grid simultaneously, collecting values, with shared-cell deduplication.
- **DP on Grids with Multiple Agents**: A broader pattern where multiple simultaneous "walkers" on a grid, each contributing to a shared objective, naturally leads to a DP state that multiplies the position-space by the number of agents.

---

## 8. Key Takeaways

- This problem's state genuinely needs 3 dimensions (row, AliceCol, BobCol), making it the first true "3D DP" problem in this series — a step beyond Ninja's Training's 2D state.
- Despite the 3D nature of the full problem, space can still be reduced to O(C^2) by only keeping the current and next row's layers, since each row only depends on the row directly above.
- The "count shared cells once" rule is handled with a simple `if (j1 != j2)` check whenever chocolates are added for the current row.
- This problem is essentially identical to the well-known "Cherry Pickup II" — recognizing that connection means any resources or intuition built for one transfers directly to the other.
