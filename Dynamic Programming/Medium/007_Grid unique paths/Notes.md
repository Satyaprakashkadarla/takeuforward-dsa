# Comprehensive Notes: Grid Unique Paths

## 1. Problem Intuition
To go from `(0, 0)` to `(m-1, n-1)` in an `m x n` grid:
- You must take exactly `(m - 1)` **Down** steps.
- You must take exactly `(n - 1)` **Right** steps.
- Total steps taken will always be `N = (m - 1) + (n - 1) = m + n - 2`.

Since you must choose `(m - 1)` Down steps (or `(n - 1)` Right steps) out of `totalSteps`, the problem reduces to finding combinations:

$$\text{Unique Paths} = \binom{m + n - 2}{m - 1} = \binom{N}{r}$$

---

## 2. Approach Comparison

### Approach 1: Recursion (Brute Force)
- Explores every possible decision tree (Down vs Right).
- **Time Complexity:** $\mathcal{O}(2^{m+n})$
- **Space Complexity:** $\mathcal{O}(m + n)$ (Stack depth)

### Approach 2: Dynamic Programming (Tabulation)
- `dp[i][j]` stores unique paths to cell `(i, j)`.
- Recurrence: `dp[i][j] = dp[i-1][j] + dp[i][j-1]`
- **Time Complexity:** $\mathcal{O}(m \times n)$
- **Space Complexity:** $\mathcal{O}(m \times n)$ (or $\mathcal{O}(n)$ space-optimized)

### Approach 3: Combinatorics / Mathematics (Optimal)
- Directly compute $\binom{N}{r} = \frac{N \times (N-1) \times \dots \times (N-r+1)}{1 \times 2 \times \dots \times r}$.
- Use iterative multiplication and division to prevent integer overflow.
- **Time Complexity:** $\mathcal{O}(\min(m, n))$
- **Space Complexity:** $\mathcal{O}(1)$

---

## 3. Mathematical Formula Breakdown
To calculate $\binom{N}{r}$ efficiently without overflow:
```java
long res = 1;
for (int i = 1; i <= r; i++) {
    res = res * (N - r + i) / i;
}
