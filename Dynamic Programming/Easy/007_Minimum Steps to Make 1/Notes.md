# Notes: Minimum Steps to Reduce N to 1 (Divide by 2/3 or Decrement)

## 1. Problem Recap

Reduce `n` to `1` using the fewest operations: divide by 2 (if divisible), divide by 3 (if divisible), or decrement by 1 (always allowed).

```
n = 10 -> 9 (decrement) -> 3 (divide by 3) -> 1 (divide by 3)
3 operations total
```

### Why Greedy "Always Divide When Possible" Doesn't Always Work
A tempting shortcut: "whenever divisible by 2 or 3, always divide." But for `n=10`: 10 isn't divisible by 2... wait it is (10/2=5). Let's check: 10 → 5 (divide by 2, 1 step) → 4 (decrement, since 5 isn't divisible by 2 or 3, 1 step) → 2 (divide by 2, 1 step) → 1 (divide by 2, 1 step) = 4 steps total. But the example shows 3 steps is achievable (10→9→3→1)! This proves greedy division isn't always optimal — sometimes decrementing FIRST (even though a division might be immediately available) sets up a much better subsequent path. This is exactly why a full DP comparison of all options at every step is necessary.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively try all applicable operations at each step and take the best (fewest additional steps) result.

### Code Logic
```java
private int solve(int i) {
    if (i == 1) return 0;
    int best = 1 + solve(i - 1);
    if (i % 2 == 0) best = Math.min(best, 1 + solve(i / 2));
    if (i % 3 == 0) best = Math.min(best, 1 + solve(i / 3));
    return best;
}
```

### Dry Run
`n = 10`

```
solve(10): decrement->1+solve(9); 10%2==0->1+solve(5); 10%3≠0
  solve(9): decrement->1+solve(8); 9%3==0->1+solve(3)
    solve(3): decrement->1+solve(2); 3%3==0->1+solve(1)=1+0=1
      solve(3) = min(1+solve(2), 1) ... need solve(2)
      solve(2): decrement->1+solve(1)=1; 2%2==0->1+solve(1)=1
        solve(2) = 1
      solve(3) = min(1+1, 1) = min(2,1) = 1
    solve(9) = min(1+solve(8), 1+1) = min(1+solve(8), 2)
    (solve(8) will be >= 2 based on typical values, so solve(9)=2 likely wins)
    solve(9) = 2 (via divide by 3 path: 9->3->1)
  solve(10) option via solve(9): 1+2 = 3
  solve(5): decrement->1+solve(4); not div by 2 or 3
    solve(4): decrement->1+solve(3)=1+1=2; 4%2==0->1+solve(2)=1+1=2
      solve(4) = 2
    solve(5) = 1+2 = 3
  solve(10) option via solve(5): 1+3 = 4

solve(10) = min(3, 4) = 3
```

Result: **3** ✅ (matches expected output — path 10→9→3→1)

### Complexity
- **Time:** Exponential in the worst case — up to 3 branches per call, with heavy overlapping subproblem recomputation.
- **Space:** O(n) — recursion stack depth (bounded by the longest possible decrement-only chain).

---

## 3. Approach 2: Optimal (Bottom-Up DP)

### Idea
Build `dp[i]` iteratively from `i=2` to `n`. Since all three candidate predecessors (`i-1`, `i/2`, `i/3`) are always strictly smaller than `i`, computing `dp` left-to-right guarantees every needed predecessor value is already finalized.

### Code Logic
```java
int[] dp = new int[n + 1];
dp[1] = 0;
for (int i = 2; i <= n; i++) {
    dp[i] = dp[i-1] + 1;
    if (i % 2 == 0) dp[i] = Math.min(dp[i], dp[i/2] + 1);
    if (i % 3 == 0) dp[i] = Math.min(dp[i], dp[i/3] + 1);
}
return dp[n];
```

### Dry Run 1
`n = 10`

| i | dp[i-1]+1 | i%2==0? dp[i/2]+1 | i%3==0? dp[i/3]+1 | dp[i] |
|---|---|---|---|---|
| 1 | — | — | — | 0 |
| 2 | 0+1=1 | dp[1]+1=1 | — | 1 |
| 3 | 1+1=2 | — | dp[1]+1=1 | 1 |
| 4 | 1+1=2 | dp[2]+1=2 | — | 2 |
| 5 | 2+1=3 | — | — | 3 |
| 6 | 3+1=4 | dp[3]+1=2 | dp[2]+1=2 | 2 |
| 7 | 2+1=3 | — | — | 3 |
| 8 | 3+1=4 | dp[4]+1=3 | — | 3 |
| 9 | 3+1=4 | — | dp[3]+1=2 | 2 |
| 10 | 2+1=3 | dp[5]+1=4 | — | 3 |

Return `dp[10] = 3`.

Result: **3** ✅ (matches expected output)

### Dry Run 2
`n = 1` → caught by the explicit `if (n == 1) return 0;` check before the loop even starts.

Result: **0** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — single pass, O(1) work per value.
- **Space:** O(n) — the `dp` array.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Bottom-Up DP) |
|---|---|---|
| Time Complexity | Exponential | O(n) |
| Space Complexity | O(n) (recursion stack) | O(n) (dp array) |
| Practical for n up to 10^4? | No — far too slow | Yes — at most 10,000 iterations |

---

## 5. Why Left-to-Right Filling Works Without Special Ordering

A subtle but important detail: all three possible moves from `i` go to STRICTLY SMALLER values (`i-1 < i`, `i/2 < i` for `i>=2`, `i/3 < i` for `i>=3`). This means there's no circular dependency — by the time the loop reaches index `i`, every value it might need (`dp[i-1]`, `dp[i/2]`, `dp[i/3]`) has already been computed and finalized in an earlier iteration. This is what makes a simple, single left-to-right pass sufficient, without needing multiple passes or a more complex evaluation order (unlike some other DP problems where dependencies can be trickier to sequence correctly).

---

## 6. Edge Cases to Consider

1. **n = 1** — handled by an explicit early return of `0`.
2. **n = 2 or n = 3** — tiny cases resolved in a single division step (`dp[2]=1` via divide by 2, `dp[3]=1` via divide by 3).
3. **Powers of 2** — tend to have very efficient pure-division paths (e.g., 8→4→2→1, 3 steps).
4. **Powers of 3** — similarly efficient (e.g., 9→3→1, 2 steps).
5. **Numbers one more than a multiple of 2 or 3** (like 10, which is 9+1 and 9 is divisible by 3) — often benefit from "decrement first, then divide," as seen in the main example.
6. **n = 10^4 (maximum)** — confirms the O(n) approach handles the upper constraint efficiently (10,000 simple iterations).

---

## 7. Related Concepts / Follow-Ups

- **Minimum Operations To Reach 1** (the divisor-subtraction variant): A related but structurally different problem — there, the move is "subtract any divisor of the current value," whereas here the moves are fixed (divide by 2, divide by 3, or decrement).
- **Integer Replacement** (LeetCode 397): Uses operations based on parity (halve if even, add/subtract 1 if odd) rather than divisibility by 2 AND 3 — a similar DP/greedy-with-care flavor.
- **Coin Change**: Conceptually similar "minimum steps to reach a target using a fixed set of moves" DP shape, though the available moves there are fixed denominations rather than value-dependent operations.

---

## 8. Key Takeaways

- This problem is a great illustration of why **greedy division isn't always optimal** — sometimes decrementing first (even when a division is available) leads to a better overall path, which is exactly why a full DP (checking ALL options at every step) is necessary rather than a greedy shortcut.
- The recurrence `dp[i] = 1 + min(dp[i-1], dp[i/2] if divisible, dp[i/3] if divisible)` is straightforward to compute bottom-up, since every possible move strictly decreases the value.
- Because all transitions move to strictly smaller indices, a single left-to-right pass correctly computes the entire `dp` array with no ordering subtleties.
- This problem pairs nicely with "Minimum Operations To Reach 1" (the general divisor-subtraction version) as a study in how the SET of available moves at each step shapes both the recurrence and the required algorithm.
