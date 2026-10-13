# Notes: Two Robots Collecting Chocolates (Cherry Pickup II)

## 1. Problem Recap

Two robots start at the top-left and top-right of a grid and walk down one row per step, each moving to the cell directly below, below-left or below-right. They collect all chocolates on the cells they visit, but a cell visited by **both** counts once. Maximize the total.

```
grid = [[4, 1, 2],
        [3, 6, 1],
        [1, 6, 6],
        [3, 1, 2]]

Robot 1: 4 + 3 + 6 + 3 = 16   (columns 0, 0, 1, 0)
Robot 2: 2 + 6 + 6 + 2 = 16   (columns 2, 1, 2, 2)
Total = 32
```

### Why Not Solve Each Robot Separately?
Choosing the best path for robot 1 and then the best for robot 2 does not work. If both want the same rich cell, the chocolates there are only collected once, so the robots' choices interact. The two paths have to be optimized **together**.

### The Key Insight: Lockstep Movement
Both robots move down exactly one row per step. After `i` steps both are in row `i`, so the full state is just:

```
(row, column of robot 1, column of robot 2)
```

This is a 3D DP: `dp[row][c1][c2]`.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
`solve(row, c1, c2)` returns the best total from `row` downward. Collect this row's chocolates, then try all 9 combinations of next-row moves and keep the best.

### Code Logic
```java
private int solve(int[][] grid, int row, int c1, int c2) {
    int collected = grid[row][c1] + (c1 == c2 ? 0 : grid[row][c2]);
    if (row == n - 1) return collected;

    int best = 0;
    for (int d1 = -1; d1 <= 1; d1++)
        for (int d2 = -1; d2 <= 1; d2++) {
            int n1 = c1 + d1, n2 = c2 + d2;
            if (inBounds(n1) && inBounds(n2))
                best = Math.max(best, solve(grid, row + 1, n1, n2));
        }
    return collected + best;
}
// answer = solve(grid, 0, 0, m - 1)
```

### Dry Run (Last Two Rows)
Suppose both robots are in row 2 of the example at columns `(0, 2)`, with row 3 being `[3, 1, 2]`.

- Row 2 collects `grid[2][0] + grid[2][2] = 1 + 6 = 7`.
- Robot 1 can go to column 0 or 1; robot 2 can go to column 1 or 2. The best next row is columns `(0, 2)` giving `3 + 2 = 5`.
- So `solve(2, 0, 2) = 7 + 5 = 12`.

### Complexity
- **Time:** O(9^n): up to 9 branches per row across `n` rows, with no caching.
- **Space:** O(n) for the recursion stack.

Same-state repeats are everywhere: different move sequences land on the same `(row, c1, c2)`, and each time the whole sub-problem is solved again. That is the signal that DP applies.

---

## 3. Approach 2: Optimal (3D DP, as Provided)

### Idea
Fill the table row by row. `dp[i][c1][c2]` is the best total collected through row `i` with the robots at columns `c1` and `c2`.

**Unreachable states:** everything starts at `-1_000_000`. Real totals are never negative, so a value above the sentinel means the state is reachable. At row 0 only `(0, m-1)` is reachable.

**Transition:**
```java
maxPrev = max of dp[i-1][pc1][pc2] over pc1 in [c1-1, c1+1], pc2 in [c2-1, c2+1]
if (maxPrev > -1_000_000)
    dp[i][c1][c2] = maxPrev + grid[i][c1] + (c1 == c2 ? 0 : grid[i][c2]);
```

Looking **backwards** (which previous columns could lead here) is equivalent to looking forwards, because the move set is symmetric (`-1, 0, +1`).

**Answer:** the maximum of `dp[n-1][c1][c2]` over all `(c1, c2)`.

### Dry Run: The Example
Reachable states per row (shown as `(c1, c2): value`):

**Row 0** (robots fixed at `(0, 2)`): `(0,2): 4 + 2 = 6`

**Row 1** (`grid[1] = [3, 6, 1]`):

| State | Calculation | Value |
|---|---|---|
| (0,1) | 6 + 3 + 6 | 15 |
| (0,2) | 6 + 3 + 1 | 10 |
| (1,1) | 6 + 6 (same cell, counted once) | 12 |
| (1,2) | 6 + 6 + 1 | 13 |

**Row 2** (`grid[2] = [1, 6, 6]`), selected states:

| State | Best previous state | Value |
|---|---|---|
| (1,2) | from (0,1)=15 | 15 + 6 + 6 = 27 |
| (2,1) | from (1,1)=12 or (1,2)=13, best 13 | 13 + 6 + 6 = 25 |
| (0,2) | from (0,1)=15 | 15 + 1 + 6 = 22 |

**Row 3** (`grid[3] = [3, 1, 2]`), selected states:

| State | Best previous state | Value |
|---|---|---|
| (0,2) | from (0,1)=22, (0,2)=22, (1,1)=21, (1,2)=27 → best 27 | 27 + 3 + 2 = **32** |
| (0,1) | best previous 27 | 27 + 3 + 1 = 31 |

Largest value in the last row: **32**, at `(0, 2)`.

Result: **32** ✅ (matches expected output)

The full set of reachable values in the last row is: (0,0)=25, (0,1)=31, (0,2)=32, (1,0)=29, (1,1)=28, (1,2)=30, (2,0)=30, (2,1)=30, (2,2)=29.

### Complexity
- **Time:** O(n * m^2 * 9), which is O(n * m^2). For `n = m = 70` that is about `70 * 4900 * 9 ≈ 3.1 million` operations.
- **Space:** O(n * m^2) for the 3D table, about `70 * 70 * 70 = 343,000` ints.

---

## 4. Space Optimization (Optional)

Row `i` only reads row `i - 1`, so two `m x m` layers are enough:

```java
int[][] prev = new int[m][m];   // layer for row i-1
int[][] cur  = new int[m][m];   // layer for row i
// fill cur from prev, then swap prev and cur
```

This reduces space from O(n * m^2) to O(m^2). It is the approach used in the earlier "Ninja and his Friends" solution, which is the same problem.

---

## 5. Comparing the Approaches

| Aspect | Brute Force | 3D DP (Optimal) | 3D DP, two layers |
|---|---|---|---|
| Time | O(9^n) | O(n * m^2) | O(n * m^2) |
| Space | O(n) | O(n * m^2) | O(m^2) |
| n = m = 70 | Impossible | Fast | Fast |

---

## 6. Implementation Notes on the Provided Code

- **The sentinel check `maxPrev > -1_000_000`:** skips states that cannot be reached from the start. Without it, adding chocolates to the sentinel could make an unreachable state look reachable.
- **Why not `Integer.MIN_VALUE`:** adding to `Integer.MIN_VALUE` would overflow. `-1_000_000` is safely below any real total (at most `70 * 100 * 2 = 14,000`) while staying far from overflow.
- **`ans = 0` initial value:** safe, because chocolates are non-negative and at least one last-row state is always reachable.
- **Crossing paths:** the DP allows states where robot 1 is to the right of robot 2 (`c1 > c2`). That is fine: the robots are interchangeable, so these states are symmetric duplicates and never produce an answer that a non-crossing arrangement couldn't.
- **`m >= 2`:** guaranteed by the constraints, so the two starting cells `(0, 0)` and `(0, m-1)` are different and both are counted at the start.

---

## 7. Edge Cases to Consider

1. **`m = 2`:** each robot has only two columns to move between; the robots start adjacent and often overlap.
2. **`n = 2`:** a single transition from the start row to the bottom row.
3. **All zeros:** the answer is 0.
4. **One very rich cell everyone wants:** both robots may land on it, but it is counted once.
5. **A high-value cell that only one robot can reach:** the DP naturally sends that robot there while the other takes a different good route.
6. **Narrow grids:** boundary checks (`Math.max(0, c - 1)` and `Math.min(m - 1, c + 1)`) keep the previous-column loops inside the grid.

---

## 8. Related Concepts / Follow-Ups

- **Ninja and his Friends:** the exact same problem. The solution there already uses the two-layer version.
- **Cherry Pickup II** (LeetCode 1463): this problem verbatim.
- **Cherry Pickup** (LeetCode 741): a harder cousin where one robot goes down and back up; it can be modelled as two robots moving together from the top-left, using a `(step, r1, r2)` state.
- **Minimum Falling Path Sum and Triangle:** the single-walker versions of "move down, with up to 3 choices per row".

---

## 9. Key Takeaways

- When two walkers move in lockstep, the state is `(step, position1, position2)`. This turns an interacting pair of paths into a single DP.
- The "count a shared cell once" rule is a one-line `c1 == c2` check when adding a row's chocolates.
- A large negative sentinel safely marks unreachable states, as long as it is nowhere near `Integer.MIN_VALUE` so additions cannot overflow.
- Because each row depends only on the row above, the 3D table can be shrunk to two `m x m` layers.
