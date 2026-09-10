# Comprehensive Notes: Frog Jump with K Distances

## 1. Problem Intuition
This problem is a extension of the standard 1-D Dynamic Programming **Frog Jump** problem (where $k=2$). Instead of jumping at most $2$ steps, the frog can jump up to $k$ steps forward.

To find the minimum energy to reach step `i`:
1. We can reach step `i` from step `(i - 1)`, `(i - 2)`, ..., or `(i - k)`.
2. The optimal cost to reach step `i` is the minimum cost among all valid previous jump origins, plus the energy needed to take that specific jump.

---

## 2. Recurrence Relation
Let `dp[i]` be the minimum energy needed to reach step `i` from step `0`.

$$\text{dp}[i] = \min_{1 \le j \le k, \, i-j \ge 0} \Big( \text{dp}[i - j] + |\text{heights}[i] - \text{heights}[i - j]| \Big)$$

---

## 3. Step-by-Step Approaches

### Approach 1: Recursive (Brute Force)
- Start from `n - 1` and recursively try jumping back `1` to `k` steps.
- **Drawback:** Overlapping subproblems cause exponential repeated computations.
- **Time Complexity:** $\mathcal{O}(k^n)$
- **Space Complexity:** $\mathcal{O}(n)$

---

### Approach 2: Memoization (Top-Down DP)
- Store recursion results in a 1D array `memo[]` initialized to `-1`.
- Avoid recomputing answers for previously solved indices.
- **Time Complexity:** $\mathcal{O}(n \times k)$
- **Space Complexity:** $\mathcal{O}(n)$ stack space + $\mathcal{O}(n)$ dynamic array space.

---

### Approach 3: Tabulation (Bottom-Up DP)
- Compute costs sequentially from step `0` to `n - 1`.
- Set `dp[0] = 0`.
- Loop through each step `i` from `1` to `n - 1`, updating `dp[i]` using the formula above.
- **Time Complexity:** $\mathcal{O}(n \times k)$
- **Space Complexity:** $\mathcal{O}(n)$

---

## 4. Space Optimization Discussion
- When $k = 2$, space can be optimized to $\mathcal{O}(1)$ using two variables (`prev1`, `prev2`).
- For general $k$, space can be optimized to $\mathcal{O}(k)$ using a fixed-size array or sliding window ring buffer.
- Since $k \le 10$ based on constraints, maintaining an array of size $k$ reduces space overhead. However, standard $\mathcal{O}(n)$ tabulation is usually preferred for code readability and efficiency given $n \le 10^4$.

---

## 5. Edge Cases
1. **$n = 1$:** Already at the final step, cost is `0`.
2. **$k \ge n$:** Frog can directly reach the final step from step `0` in a single jump if optimal.
3. **$k = 1$:** Single path possible ($0 \rightarrow 1 \rightarrow 2 \dots \rightarrow n-1$).
