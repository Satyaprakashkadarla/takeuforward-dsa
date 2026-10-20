# Notes: Maximum Score Using Neighbor Products (Burst Balloons)

## 1. Problem Recap

Remove elements one at a time. Removing an element scores `left * element * right` using its **current** neighbors (missing neighbors count as 1). Maximize the total score over all removal orders.

```
arr = [5, 10]
Order A: remove 5 then 10  ->  (1*5*10) + (1*10*1) = 50 + 10 = 60   (best)
Order B: remove 10 then 5  ->  (5*10*1) + (1*5*1)  = 50 + 5  = 55
```

The order matters because each removal changes who the neighbors are.

---

## 2. Why "Which Element Goes First?" Doesn't Work

Suppose you decide to remove element `k` first from an interval. Its neighbors are `k-1` and `k+1`. Afterwards, the elements on its left and right become **adjacent**, so what happens on the left now depends on what happens on the right. The two halves are tangled together, and there is no clean sub-problem.

### The Fix: Think About the LAST Removal
In an interval `[l, r]`, pick `k` as the element removed **last**:

- When `k` is finally removed, everything else in `[l, r]` is gone. Its neighbors are therefore the elements just **outside** the interval: `a[l-1]` and `a[r+1]`. So its gain is `a[l-1] * a[k] * a[r+1]`, a fixed number.
- Before that, `k` sits in the middle the whole time, acting as a **wall**. The left part `[l, k-1]` has neighbors `a[l-1]` and `a[k]` (both fixed), and the right part `[k+1, r]` has neighbors `a[k]` and `a[r+1]` (both fixed). The two sides are now **independent**.

That gives the recurrence:

```
dp[l][r] = max over k in [l, r] of
           ( a[l-1] * a[k] * a[r+1] + dp[l][k-1] + dp[k+1][r] )
```

with `a[0] = a[n+1] = 1` as padding, and `dp` of an empty interval equal to 0.

---

## 3. Approach 1: Brute Force (Try Every Removal Order)

### Idea
Follow the process literally: from the current list, try removing each element, add its gain, and recurse on what is left.

### Code Logic
```java
private int solve(List<Integer> list) {
    if (list.isEmpty()) return 0;
    int best = 0;
    for (int i = 0; i < list.size(); i++) {
        int left  = (i > 0) ? list.get(i - 1) : 1;
        int right = (i < list.size() - 1) ? list.get(i + 1) : 1;
        List<Integer> next = new ArrayList<>(list);
        next.remove(i);
        best = Math.max(best, left * list.get(i) * right + solve(next));
    }
    return best;
}
```

### Dry Run
`arr = [5, 10]`

```
solve([5, 10]):
  remove index 0 (value 5):  left=1, right=10 -> gain 50, then solve([10])
      solve([10]): remove 10 -> 1*10*1 = 10 -> 10
      total = 50 + 10 = 60
  remove index 1 (value 10): left=5, right=1  -> gain 50, then solve([5])
      solve([5]): remove 5 -> 1*5*1 = 5 -> 5
      total = 50 + 5 = 55
solve([5, 10]) = max(60, 55) = 60
```

Result: **60** ✅

### Complexity
- **Time:** O(n!). There are `n` choices for the first removal, `n - 1` for the second, and so on.
- **Space:** O(n^2) because each recursion level holds a copy of the list.

For `n = 100` this is impossible, but it is a perfect correctness baseline for tiny inputs.

---

## 4. Middle Ground: Top-Down Interval DP (Memoization)

The same recurrence written recursively with a cache:

```java
int solve(int l, int r) {
    if (l > r) return 0;
    if (memo[l][r] != -1) return memo[l][r];
    int best = 0;
    for (int k = l; k <= r; k++)
        best = Math.max(best, a[l-1]*a[k]*a[r+1] + solve(l, k-1) + solve(k+1, r));
    return memo[l][r] = best;
}
```

Same O(n^3) time and O(n^2) space as the bottom-up version. Recursion depth is at most `n = 100`, so stack overflow is not a concern here.

---

## 5. Approach 2: Optimal (Bottom-Up Interval DP)

### Idea
Compute `dp[l][r]` for all intervals in order of increasing length, so the smaller intervals needed by the recurrence are always ready.

### Code Logic
```java
int[] a = new int[n + 2];
a[0] = a[n + 1] = 1;
System.arraycopy(arr, 0, a, 1, n);

int[][] dp = new int[n + 2][n + 2];

for (int len = 1; len <= n; len++) {
    for (int l = 1; l + len - 1 <= n; l++) {
        int r = l + len - 1;
        for (int k = l; k <= r; k++) {
            int score = a[l-1] * a[k] * a[r+1] + dp[l][k-1] + dp[k+1][r];
            dp[l][r] = Math.max(dp[l][r], score);
        }
    }
}
return dp[1][n];
```

### Why the Edge Indices Are Safe
- `dp[l][k-1]` with `k = l` is `dp[l][l-1]`, an empty interval, left at its default value 0.
- `dp[k+1][r]` with `k = r` is `dp[r+1][r]`, also empty, also 0.
- The table is `(n + 2) x (n + 2)`, so indices up to `n + 1` are valid.

### Dry Run 1: Example 1
`arr = [5, 10]`, so `a = [1, 5, 10, 1]`.

**Length 1:**
- `dp[1][1]` = `a[0] * a[1] * a[2]` = 1 * 5 * 10 = **50**
- `dp[2][2]` = `a[1] * a[2] * a[3]` = 5 * 10 * 1 = **50**

**Length 2:** `dp[1][2]`, interval `[1, 2]`, with outside neighbors `a[0] = 1` and `a[3] = 1`:

| k (last removed) | Gain a[0]*a[k]*a[3] | + dp[1][k-1] | + dp[k+1][2] | Total |
|---|---|---|---|---|
| 1 (the 5) | 1 * 5 * 1 = 5 | dp[1][0] = 0 | dp[2][2] = 50 | 55 |
| 2 (the 10) | 1 * 10 * 1 = 10 | dp[1][1] = 50 | dp[3][2] = 0 | **60** |

`dp[1][2] = 60`

Result: **60** ✅ (matches expected output). The winning choice `k = 2` means the 10 is removed **last**, exactly as in the example (remove 5 first, then 10).

### Dry Run 2: Example 2
`arr = [1, 2, 3, 4, 5]`, so `a = [1, 1, 2, 3, 4, 5, 1]`.

Full DP table (row `l`, column `r`):

| | r=1 | r=2 | r=3 | r=4 | r=5 |
|---|---|---|---|---|---|
| **l=1** | 2 | 9 | 36 | 105 | **110** |
| **l=2** | | 6 | 32 | 100 | 105 |
| **l=3** | | | 24 | 90 | 100 |
| **l=4** | | | | 60 | 75 |
| **l=5** | | | | | 20 |

A few cells worked out by hand:

- `dp[3][3]` = `a[2] * a[3] * a[4]` = 2 * 3 * 4 = **24**
- `dp[4][4]` = `a[3] * a[4] * a[5]` = 3 * 4 * 5 = **60**
- `dp[3][4]` (elements 3, 4 with outside neighbors `a[2] = 2` and `a[5] = 5`):
  - `k = 3`: 2 * 3 * 5 = 30, plus `dp[3][2] = 0`, plus `dp[4][4] = 60`, total **90**
  - `k = 4`: 2 * 4 * 5 = 40, plus `dp[3][3] = 24`, plus `dp[5][4] = 0`, total 64
  - max = **90**

The final cell `dp[1][5]` (outside neighbors `a[0] = 1` and `a[6] = 1`):

| k (last removed) | Gain a[0]*a[k]*a[6] | + dp[1][k-1] | + dp[k+1][5] | Total |
|---|---|---|---|---|
| 1 | 1 * 1 * 1 = 1 | 0 | dp[2][5] = 105 | 106 |
| 2 | 1 * 2 * 1 = 2 | dp[1][1] = 2 | dp[3][5] = 100 | 104 |
| 3 | 1 * 3 * 1 = 3 | dp[1][2] = 9 | dp[4][5] = 75 | 87 |
| 4 | 1 * 4 * 1 = 4 | dp[1][3] = 36 | dp[5][5] = 20 | 60 |
| 5 | 1 * 5 * 1 = 5 | dp[1][4] = 105 | 0 | **110** |

`dp[1][5] = 110`

Result: **110** ✅ (matches expected output). The winning choice is `k = 5`: the element 5 is removed **last**, which matches the example's sequence (4, 3, 2, 1, then 5).

### Complexity
- **Time:** O(n^3). There are about `n^2 / 2` intervals, each trying up to `n` values of `k`. For `n = 100` that is roughly 170,000 steps.
- **Space:** O(n^2) for the table (about 10,000 ints).

---

## 6. Comparing the Approaches

| Aspect | Brute Force | Top-Down Memo | Bottom-Up (Optimal) |
|---|---|---|---|
| Time | O(n!) | O(n^3) | O(n^3) |
| Space | O(n^2) | O(n^2) + stack | O(n^2) |
| Handles n = 100? | No | Yes | Yes |

---

## 7. Implementation Notes on the Provided Code

- **Padding with 1s** makes the "missing neighbor counts as 1" rule disappear: edge elements simply see `a[0]` or `a[n+1]`, both equal to 1.
- **Loop order matters:** by looping over `len` first, every interval used on the right-hand side of the recurrence is shorter than `[l, r]` and therefore already computed.
- **Overflow:** a single gain is at most `100 * 100 * 100 = 10^6` and there are at most 100 removals, so the total is at most about 10^8. The largest score for the constraints is `98,010,100`, which fits in `int` (limit about 2.1 x 10^9).
- **`dp[l][r]` starts at 0:** all gains are positive (elements are at least 1), so starting from 0 is safe as the "max so far".

---

## 8. Edge Cases to Consider

1. **Single element `[x]`:** the answer is `1 * x * 1 = x`.
2. **All elements equal to 1:** every removal gains 1, so the score is `n`.
3. **Large element at the end of the array:** the padded neighbor of 1 matters, and the DP handles it through `a[0]` and `a[n+1]`.
4. **Increasing or decreasing arrays:** the best order is not obvious by intuition, which is why the DP tries all choices of the last element.
5. **`n = 100` with all 100s:** the score is `98,010,100`, still inside `int`.

---

## 9. Related Concepts / Follow-Ups

- **Burst Balloons** (LeetCode 312): this exact problem, with balloons instead of array elements.
- **Matrix Chain Multiplication:** the same interval DP shape, trying a split point `k` for each interval.
- **Minimum Cost to Cut a Stick, Minimum Score Triangulation of a Polygon, Palindrome Partitioning:** other interval DPs where you pick the **last** action in an interval so that the two sides become independent.
- **Removing Boxes** (LeetCode 546): a harder interval DP with an extra state.

---

## 10. Key Takeaways

- When removing items changes who is adjacent to whom, think about the **last** removal in an interval instead of the first. The two sides then become independent.
- Padding the array with sentinel values removes special cases for the ends.
- Interval DP fills the table by increasing length, so every smaller sub-interval is ready when needed.
- Many interval DP problems share the same skeleton: `dp[l][r] = max/min over k of (cost at k + dp[l][k-1] + dp[k+1][r])`.
