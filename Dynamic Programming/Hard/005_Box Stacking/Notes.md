# Notes: Box Stacking

## 1. Problem Recap

Stack boxes (rotation allowed, unlimited copies of each) to maximize total height. A box may sit on another only if **both** base dimensions are strictly smaller than the box below.

```
height = [1, 4, 3], width = [2, 5, 4], length = [3, 6, 1]
Best stack (bottom to top): 4 + 6 + 1 + 1 + 3 = 15
```

---

## 2. Step 1: Expand Every Box Into 3 Orientations

A box with dimensions `x, y, z` can stand on three different faces:

| Height | Base |
|---|---|
| x | (y, z) |
| y | (x, z) |
| z | (x, y) |

Each base is stored **sorted** as `(a, b)` with `a <= b`. Normalizing is valid because a box on top can always be turned in the horizontal plane, so the only thing that matters is the sorted pair. The fit rule then becomes simple:

```
top fits on bottom  <=>  a_top < a_bottom  AND  b_top < b_bottom
```

Since multiple instances of a box are allowed, the 3n orientations are independent candidates.

For Example 2 the boxes are (1,2,3), (4,5,6), (3,4,1), which expand to 9 orientations:

| Label | Height | Base (a, b) | Area |
|---|---|---|---|
| A | 1 | (2, 3) | 6 |
| B | 2 | (1, 3) | 3 |
| C | 3 | (1, 2) | 2 |
| D | 4 | (5, 6) | 30 |
| E | 5 | (4, 6) | 24 |
| F | 6 | (4, 5) | 20 |
| G | 3 | (1, 4) | 4 |
| H | 4 | (1, 3) | 3 |
| I | 1 | (3, 4) | 12 |

---

## 3. Approach 1: Brute Force (Plain Recursion)

### Idea
`solve(prevA, prevB)` = best height of a stack built on top of a base `(prevA, prevB)`. Try every orientation that fits and take the best:

```java
private int solve(int prevA, int prevB) {
    int best = 0;
    for (int i = 0; i < hs.length; i++) {
        if (as[i] < prevA && bs[i] < prevB) {
            best = Math.max(best, hs[i] + solve(as[i], bs[i]));
        }
    }
    return best;
}
// start: solve(INF, INF)
```

Bases strictly shrink each step, so recursion terminates and no orientation is reused.

### Complexity
- **Time:** Exponential. The same base is recomputed through many different stacks.
- **Space:** O(n) recursion depth (at most 3n levels).

---

## 4. Approach 2: Optimal (Sort + Weighted LIS)

### Why Sort by Area (Descending)?
If X can sit on Y, X's base is strictly smaller in both dimensions, which forces `area(X) < area(Y)`. So after sorting by area descending, **every box that could be underneath another comes earlier in the list**. That is exactly the order a DP needs. Areas can reach `10^6 * 10^6 = 10^12`, so they are compared as `long`.

### The DP
```
dp[i] = height[i] + max( dp[j] )   for j < i with a[j] > a[i] and b[j] > b[i]
        (or height[i] alone if no such j)
answer = max over i of dp[i]
```
`dp[i]` is the tallest stack whose **top** box is orientation `i`. This is the Longest Increasing Subsequence pattern, with heights as weights and a 2D "fits inside" test instead of `<`.

### Dry Run: Example 2

Sorted by area descending: D(30), E(24), F(20), I(12), A(6), G(4), B(3), H(3), C(2).

| Orientation | Height | Base | Can sit on (earlier boxes whose base strictly contains it) | dp |
|---|---|---|---|---|
| D | 4 | (5,6) | none | 4 |
| E | 5 | (4,6) | none (D has b=6, not > 6) | 5 |
| F | 6 | (4,5) | D | 4 + 6 = 10 |
| I | 1 | (3,4) | D, E, F | 10 + 1 = 11 |
| A | 1 | (2,3) | D, E, F, I | 11 + 1 = 12 |
| G | 3 | (1,4) | D, E, F | 10 + 3 = 13 |
| B | 2 | (1,3) | D, E, F, I (not A, since 3 is not < 3; not G, since 1 is not < 1) | 11 + 2 = 13 |
| H | 4 | (1,3) | D, E, F, I | 11 + 4 = 15 |
| C | 3 | (1,2) | D, E, F, I, A | 12 + 3 = 15 |

Maximum `dp` = **15**.

Result: **15** ✅ (matches expected output). The stack for `C` is D(4) -> F(6) -> I(1) -> A(1) -> C(3), which is exactly the arrangement given in the problem.

### Example 1 (60)
The best stack uses the 10 x 12 x 32 box twice (height 10 on a 12 x 32 base, then height 32 on a 10 x 12 base), then smaller orientations of the other boxes: `10 + 32 + 4 + 4 + 6 + 1 + 3 = 60`. This was confirmed by running the algorithm and cross-checking it against the brute force.

### Complexity
- **Time:** O(n^2). Sorting 3n items is O(n log n), and the DP compares every pair of the 3n orientations (about 9n^2 / 2 checks). With `n <= 100` that is at most around 45,000 checks.
- **Space:** O(n) for the orientation list and `dp` array.

---

## 5. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Sort + DP) |
|---|---|---|
| Time | Exponential | O(n^2) |
| Space | O(n) | O(n) |
| Practical for n = 100? | No | Yes |

---

## 6. Edge Cases to Consider

1. **A cube (all three dimensions equal):** all 3 orientations are identical, and no two of them can stack (bases are equal, not strictly smaller). Only one counts.
2. **Boxes with two equal dimensions:** some orientations duplicate each other, which is harmless.
3. **Equal bases cannot stack:** the check is strictly smaller in both dimensions, so a box never sits on an identical base. Using `>` rather than `>=` in the DP is essential.
4. **Single box:** the answer is the stack made of its own orientations, for example `height[i]` plus any smaller-base orientations that fit.
5. **Overflow:** the base area can reach 10^12, so it must be compared as `long`. The stack height itself is at most `3n * 10^6 = 3 * 10^8`, which fits in `int`.
6. **Sorting by area is only a processing order:** a larger area does not mean a box fits, which is why the DP still checks both dimensions explicitly.

---

## 7. Related Concepts / Follow-Ups

- **Longest Increasing Subsequence (LIS):** this is the weighted, 2D version of it.
- **Russian Doll Envelopes** (LeetCode 354): the same "strictly smaller in both dimensions" nesting rule, but without rotation and with each item used once, which can be solved in O(n log n).
- **Maximum Sum Increasing Subsequence:** the same "sum the weights along the chain" DP in one dimension.
- **Maximum Height by Stacking Cuboids** (LeetCode 1691): a close relative where the nesting condition is `<=` instead of `<`.

---

## 8. Key Takeaways

- Rotation is handled by generating 3 orientations per box, and "multiple instances allowed" means all 3n orientations are independent.
- Sorting each base as `(a, b)` with `a <= b` turns the rotation-in-the-plane question into a simple pair of comparisons.
- Sorting by base area (descending) gives a topological order, so a single left-to-right DP pass is enough.
- Strict inequality in both dimensions is what prevents equal bases from stacking.
