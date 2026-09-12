# Notes: House Robber (Circular)

## 1. Problem Recap

Houses are arranged in a **circle**: house `0` and house `n-1` are considered adjacent, in addition to normal consecutive adjacency. Maximize the total loot such that no two adjacent houses (including the wraparound pair) are both robbed.

```
money = [9, 4, 1, 8]
House 0 (9) and House 3 (8) are circularly adjacent -> can't take both.
```

### The Key Insight — Reduce to Two Linear Subproblems
Since house `0` and house `n-1` can never both be robbed, **every valid plan falls into (at least) one of two categories**:

1. Plans that **don't rob house `n-1`** — these are unrestricted by the circular constraint and reduce to the plain **linear** "Maximum Sum of Non-Adjacent Elements" problem over houses `[0, n-2]`.
2. Plans that **don't rob house `0`** — similarly reduce to the linear problem over houses `[1, n-1]`.

The optimal circular answer is the max of these two linear sub-results, because any valid circular plan is captured by (at least) one of these two categories.

This is a beautiful example of **reducing a harder problem to two applications of an easier, already-solved problem** — you don't need any new DP recurrence at all, just the "Maximum Sum of Non-Adjacent Elements" logic run twice over different ranges.

---

## 2. Approach 1: Brute Force (Plain Recursion, Twice Over)

### Idea
Run the same plain recursive solution used for the linear version of this problem, once over `[0, n-2]` and once over `[1, n-1]`, then take the max.

### Code Logic
```java
public int houseRobber(int[] money) {
    int n = money.length;
    if (n == 1) return money[0];
    return Math.max(robLinear(money, 0, n-2), robLinear(money, 1, n-1));
}

private int solve(int[] a, int l, int r, int i) {
    if (i < l) return 0;
    if (i == l) return a[l];
    int skip = solve(a, l, r, i - 1);
    int take = a[i] + solve(a, l, r, i - 2);
    return Math.max(skip, take);
}
```

### Dry Run
`money = [9, 4, 1, 8]`

**Case (a): rob range [0, 2] = [9, 4, 1]**
```
solve(2) = max(solve(1), 1+solve(0))
solve(1) = max(solve(0), 4+solve(-1)) = max(9, 4+0) = 9
solve(0) = 9
solve(2) = max(9, 1+9) = max(9,10) = 10
```

**Case (b): rob range [1, 3] = [4, 1, 8]**
```
solve(3) = max(solve(2), 8+solve(1))
solve(2) = max(solve(1), 1+solve(0)) 
solve(1) = 4 (base case, l=1)
solve(0)... wait i=0 < l=1, so solve(0)=0
solve(2) = max(4, 1+0) = 4
solve(3) = max(4, 8+4) = max(4,12) = 12
```

Overall: `max(10, 12) = 12`

Result: **12** ✅ (matches expected output)

### Complexity
- **Time:** O(2^n) — each of the two linear sub-recursions is independently exponential without memoization; running it twice doesn't change the complexity class.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (Iterative DP, Reduced to Two Linear Passes)

### Idea
Use the same iterative, space-optimized linear DP from "Maximum Sum of Non-Adjacent Elements" (the `rob` helper), applied once to `[0, n-2]` and once to `[1, n-1]`.

### Code Logic
```java
public int houseRobber(int[] money) {
    int n = money.length;
    if (n == 1) return money[0];
    return Math.max(rob(money, 0, n-2), rob(money, 1, n-1));
}

private int rob(int[] a, int l, int r) {
    int prev2 = 0, prev1 = 0;
    for (int i = l; i <= r; i++) {
        int cur = Math.max(prev1, prev2 + a[i]);
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

### Dry Run 1
`money = [2, 1, 4, 9]`

**rob(0, 2) = [2, 1, 4]:**

| i | prev2 | prev1 | cur = max(prev1, prev2+a[i]) |
|---|---|---|---|
| 0 | 0 | 0 | max(0, 0+2)=2 |
| 1 | 0 | 2 | max(2, 0+1)=2 |
| 2 | 2 | 2 | max(2, 2+4)=6 |

`rob(0,2) = 6`

**rob(1, 3) = [1, 4, 9]:**

| i | prev2 | prev1 | cur |
|---|---|---|---|
| 1 | 0 | 0 | max(0,0+1)=1 |
| 2 | 0 | 1 | max(1,0+4)=4 |
| 3 | 1 | 4 | max(4,1+9)=10 |

`rob(1,3) = 10`

Overall: `max(6, 10) = 10`

Result: **10** ✅ (matches expected output)

### Dry Run 2
`money = [1, 5, 2, 1, 6]`

**rob(0, 3) = [1, 5, 2, 1]:**

| i | prev2 | prev1 | cur |
|---|---|---|---|
| 0 | 0 | 0 | 1 |
| 1 | 0 | 1 | max(1,0+5)=5 |
| 2 | 1 | 5 | max(5,1+2)=5 |
| 3 | 5 | 5 | max(5,5+1)=6 |

`rob(0,3) = 6`

**rob(1, 4) = [5, 2, 1, 6]:**

| i | prev2 | prev1 | cur |
|---|---|---|---|
| 1 | 0 | 0 | 5 |
| 2 | 0 | 5 | max(5,0+2)=5 |
| 3 | 5 | 5 | max(5,5+1)=6 |
| 4 | 5 | 6 | max(6,5+6)=11 |

`rob(1,4) = 11`

Overall: `max(6, 11) = 11`

Result: **11** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`money = [9, 4, 1, 8]`

**rob(0, 2) = [9, 4, 1]:**

| i | prev2 | prev1 | cur |
|---|---|---|---|
| 0 | 0 | 0 | 9 |
| 1 | 0 | 9 | max(9,0+4)=9 |
| 2 | 9 | 9 | max(9,9+1)=10 |

`rob(0,2) = 10`

**rob(1, 3) = [4, 1, 8]:**

| i | prev2 | prev1 | cur |
|---|---|---|---|
| 1 | 0 | 0 | 4 |
| 2 | 0 | 4 | max(4,0+1)=4 |
| 3 | 4 | 4 | max(4,4+8)=12 |

`rob(1,3) = 12`

Overall: `max(10, 12) = 12`

**Result: 12** ✅

So for the quiz options `17, 13, 12, 9`, the correct answer is **12**.

### Complexity
- **Time:** O(n) — two linear passes, still O(n) overall.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical for n up to 10^5? | No — far too slow | Yes — trivially fast |

---

## 5. Why This Reduction Is Valid (and Complete)

A natural worry: does splitting into "exclude house n-1" and "exclude house 0" actually cover *every* possible optimal plan? Yes — because in any valid circular robbery plan, house 0 and house n-1 cannot both be robbed (they're adjacent). So every valid plan excludes **at least one** of these two houses. That means:

- If a plan excludes house `n-1`, it's captured by the `rob(0, n-2)` computation (since that computation considers all valid linear plans over that range, including ones that also happen to skip other houses).
- If a plan excludes house `0`, it's captured by `rob(1, n-1)`.

Taking the max of these two guarantees we've found the best plan overall, since every valid circular plan falls into at least one of these two linear sub-cases.

---

## 6. Edge Cases to Consider

1. **n = 1** — a single house has no adjacency concern (can't be adjacent to itself); the answer is simply that house's value. Handled by an explicit early check.
2. **n = 2** — houses 0 and 1 are adjacent to each other in BOTH the normal sense and the circular sense (since with only 2 houses, "house 0 adjacent to house n-1" and "house 0 adjacent to house 1" are the same pair) — so only one house can ever be robbed; the answer is `max(money[0], money[1])`.
3. **All houses have equal value** — e.g., `money = [5,5,5,5,5]` (odd count) → alternating selection still respects the circular constraint; the DP correctly finds the best alternating pattern.
4. **Two adjacent houses with very high values, rest low** — verifies the DP correctly identifies that taking both isn't allowed and picks appropriately.
5. **All houses have value 0** — trivially, the answer is `0`.

---

## 7. Related Concepts / Follow-Ups

- **Maximum Sum of Non-Adjacent Elements**: The linear (non-circular) version of this exact problem — this circular version is solved by literally reusing that solution twice over different ranges.
- **House Robber III** (LeetCode 337): A tree-structured variant where houses form a binary tree instead of a line or circle, requiring a different (tree-DP) approach, though the core "rob or skip" decision logic remains conceptually similar.
- **General Pattern: Reducing Circular Problems to Linear Ones**: This "exclude one endpoint, solve linearly, then exclude the other endpoint, solve linearly, take the best" technique is a broadly reusable trick whenever a problem's only circular constraint involves the first and last elements being adjacent — worth recognizing as a pattern beyond just this specific problem.

---

## 8. Key Takeaways

- House Robber (Circular) doesn't need a new DP recurrence — it's solved by recognizing that the circular constraint only affects the house-0/house-(n-1) pair, and reducing to two applications of the already-known linear "Maximum Sum of Non-Adjacent Elements" solution.
- The reduction is provably complete: every valid circular plan excludes at least one of house 0 or house (n-1), so checking both linear sub-cases and taking the max is guaranteed to find the true optimum.
- This is a great example of a broader problem-solving technique: when a circular constraint only involves the endpoints, splitting into two linear sub-problems (each excluding one endpoint) often cleanly resolves the added complexity.
- The space-optimized `rob` helper is identical in structure to the linear House Robber / Maximum Sum of Non-Adjacent Elements solution — no new algorithmic idea is needed, just careful application of an existing one.
