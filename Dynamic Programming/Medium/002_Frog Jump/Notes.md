# Comprehensive Notes: Frog Jump (K = 2)

## 1. Problem Intuition
The Frog Jump problem is a classical 1D Dynamic Programming problem (similar to Climbing Stairs). At any step `i`, the frog could have arrived from either:
- Step `i - 1` (taking a 1-step jump)
- Step `i - 2` (taking a 2-step jump)

The minimum cost to reach step `i` is the minimum of these two choices plus their respective energy jump costs.

---

## 2. Recurrence Relation
Let `dp[i]` be the minimum energy needed to reach step `i` from step `0`.

$$\text{dp}[i] = \min \Big( \text{dp}[i - 1] + |\text{heights}[i] - \text{heights}[i - 1]|, \;\; \text{dp}[i - 2] + |\text{heights}[i] - \text{heights}[i - 2]| \Big)$$

- **Base Case:** `dp[0] = 0` (starting position requires 0 energy)

---

## 3. Optimization Journey

### Approach 1: Recursion (Brute Force)
- Branch factor is 2 at every step ($i-1$ and $i-2$).
- **Time Complexity:** $\mathcal{O}(2^n)$
- **Space Complexity:** $\mathcal{O}(n)$

---

### Approach 2: Tabulation (1D DP Array)
- Use an array `dp` of size `n` where `dp[i]` holds the answer for step `i`.
- **Time Complexity:** $\mathcal{O}(n)$
- **Space Complexity:** $\mathcal{O}(n)$

---

### Approach 3: Space Optimized (Optimal)
- Notice that calculating `dp[i]` only depends on `dp[i - 1]` and `dp[i - 2]`.
- Replace the array with two variables `prev1` and `prev2`.
- Update variables at each iteration:
  - `prev2 = prev1`
  - `prev1 = current`
- **Time Complexity:** $\mathcal{O}(n)$
- **Space Complexity:** $\mathcal{O}(1)$

---

## 4. Edge Cases
1. **$n = 1$:** The frog is already at the target step; return `0`.
2. **Identical Heights:** Costs reduce to `0` whenever matching height steps are picked.
