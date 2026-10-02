# Notes: Minimum Operations To Reach 1

## 1. Problem Recap

Reduce `n` to `1` using the fewest operations, where each operation subtracts a divisor `x` of the current value (with `0 < x < n`), i.e. `n -> n - x`.

```
n = 8 -> 4 -> 2 -> 1  (3 operations: subtract 4, then 2, then 1)
```

### Why Subtracting the Largest Possible Divisor Isn't Always Optimal
A greedy instinct might be: "always subtract the largest valid divisor, to shrink `n` as fast as possible." But this doesn't always give the minimum number of steps. For `n=8`: the largest proper divisor is 4, giving `8 -> 4 -> 2 -> 1` (3 steps) — which happens to be optimal here. But in general, being greedy about *shrinking fast* isn't the same as minimizing the *number of steps*, since landing on a number with especially good divisor options later can sometimes beat naive greedy shrinking. This is exactly why a DP approach (trying all options and taking the best) is the safe, correct strategy.

---

## 2. Approach 1: Brute Force (Plain Recursion, Checking Every Divisor)

### Idea
Recursively try every valid divisor `x` of the current value, moving to `i - x`, and take whichever path leads to the fewest total operations.

### Code Logic
```java
private int solve(int i) {
    if (i == 1) return 0;
    int best = Integer.MAX_VALUE;
    for (int x = 1; x < i; x++) {
        if (i % x == 0) {
            best = Math.min(best, 1 + solve(i - x));
        }
    }
    return best;
}
```

### Dry Run
`n = 5`

```
solve(5): divisors of 5 (x<5): only x=1
  = 1 + solve(4)

solve(4): divisors of 4 (x<4): x=1, x=2
  option x=1: 1 + solve(3)
  option x=2: 1 + solve(2)

solve(3): divisors (x<3): x=1
  = 1 + solve(2)

solve(2): divisors (x<2): x=1
  = 1 + solve(1) = 1 + 0 = 1

solve(3) = 1 + 1 = 2
solve(4) option x=1: 1 + 2 = 3
solve(4) option x=2: 1 + 1 = 2
solve(4) = min(3, 2) = 2

solve(5) = 1 + 2 = 3
```

Result: **3** ✅ (matches expected output — path 5→4→2→1)

### Complexity
- **Time:** Exponential-ish — branches over every divisor at each level, with no caching, and even the divisor-finding itself is a naive O(i) scan per call (checking every `x` from 1 to i-1, rather than just up to `sqrt(i)`).
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (Bottom-Up DP with Trial Division up to sqrt(i))

### Idea
Build `dp[i]` iteratively from `i=2` up to `n`. For each `i`, start with the guaranteed-valid "subtract 1" move as a baseline, then look for better options using all of `i`'s divisors — found efficiently via trial division up to `sqrt(i)`.

### The sqrt(i) Divisor-Finding Trick
Divisors of a number always come in **pairs** that multiply to that number: if `x` divides `i`, then `i/x` also divides `i`, and one of the pair is always `<= sqrt(i)` while the other is `>= sqrt(i)`. So checking `x` only up to `sqrt(i)` is enough to discover **every** divisor pair — for each `x` found, both `x` and its partner `i/x` are recorded as candidate moves, without needing to separately check values of `x` beyond `sqrt(i)`.

```
i = 12, sqrt(12) ≈ 3.46
x=2: 12%2==0 -> divisors 2 and 6
x=3: 12%3==0 -> divisors 3 and 4
(x=4 would exceed sqrt(12), and we've already found all 4 divisor pairs: (2,6) and (3,4))
```

### Code Logic
```java
int[] dp = new int[n + 1];
for (int i = 2; i <= n; i++) {
    dp[i] = dp[i - 1] + 1;  // baseline: subtract 1
    for (int x = 2; x * x <= i; x++) {
        if (i % x == 0) {
            dp[i] = Math.min(dp[i], 1 + dp[i - x]);
            int d = i / x;
            if (d < i) dp[i] = Math.min(dp[i], 1 + dp[i - d]);
        }
    }
}
return dp[n];
```

### Dry Run 1
`n = 8`

| i | dp[i-1]+1 (baseline) | divisor checks (x from 2, x*x<=i) | best | dp[i] |
|---|---|---|---|---|
| 1 | — | — | — | 0 (default) |
| 2 | dp[1]+1=1 | x=2: 2*2=4>2, none | — | 1 |
| 3 | dp[2]+1=2 | x=2: 4>3, none | — | 2 |
| 4 | dp[3]+1=3 | x=2: 4<=4, 4%2=0 → dp[4]=min(3,1+dp[2]=2)=2; d=2<4 → dp[4]=min(2,1+dp[2]=2)=2 | | 2 |
| 5 | dp[4]+1=3 | x=2: 4<=5, 5%2≠0, none | — | 3 |
| 6 | dp[5]+1=4 | x=2: 4<=6, 6%2=0 → dp[6]=min(4,1+dp[4]=3)=3; d=3<6 → dp[6]=min(3,1+dp[3]=3)=3 | | 3 |
| 7 | dp[6]+1=4 | x=2: 4<=7, 7%2≠0, none | — | 4 |
| 8 | dp[7]+1=5 | x=2: 4<=8, 8%2=0 → dp[8]=min(5,1+dp[6]=4)=4; d=4<8 → dp[8]=min(4,1+dp[4]=3)=3 | | 3 |

Return `dp[8] = 3`.

Result: **3** ✅ (matches expected output — matches path 8→4→2→1)

### Dry Run 2 — n = 5
Continuing the table above (computed as part of building up to 8): `dp[5] = 3`.

Result: **3** ✅ (matches expected output — path 5→4→2→1)

### Complexity
- **Time:** O(n × sqrt(n)) — for each of `n` numbers, trial division up to `sqrt(i)`.
- **Space:** O(n) — the `dp` array.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Bottom-Up DP) |
|---|---|---|
| Time Complexity | Exponential (with O(i) divisor scan per call) | O(n × sqrt(n)) |
| Space Complexity | O(n) (recursion stack) | O(n) (dp array) |
| Practical for n up to 1000? | No — far too slow | Yes — at most ~31,600 divisor checks total |

---

## 5. Why the `d < i` Guard Exists

When `x` ranges from `2` upward, the paired divisor `d = i/x` is always strictly less than `i` (since `x >= 2` implies `i/x <= i/2 < i`). So in practice, given the loop starts at `x=2`, the `d < i` check will always be true and never actually filters anything out. It's included as a defensive correctness guard — protecting against a hypothetical case where `d` could equal `i` (which would only happen if `x=1`, a value the loop never reaches) — rather than a condition that changes behavior for this specific implementation. It's good defensive practice even though it's not strictly necessary given the loop's starting value.

---

## 6. Edge Cases to Consider

1. **n = 1** — already at the target; `dp[1]` stays `0` by default (Java initializes int arrays to 0), and the loop starts at `i=2`, so `n=1` is correctly handled without even entering the loop.
2. **n = 2** — only divisor is 1 (subtract 1); `dp[2] = 1`.
3. **Prime n** — the only valid first move is subtracting 1 (since a prime's only divisors less than itself are 1); e.g., `n=5` (see Example 2) reduces to `dp[prime] = 1 + dp[prime - 1]`.
4. **Powers of 2** — tend to have efficient halving paths (e.g., 8→4→2→1), since dividing by 2 repeatedly is a strong option.
5. **n = 1000 (maximum)** — confirms the O(n√n) approach handles the upper bound comfortably (about 31,600 total divisor checks across all i from 2 to 1000).

---

## 7. Related Concepts / Follow-Ups

- **Integer Replacement** (LeetCode 397): A similar "minimum operations to reduce n to 1" problem, but using different allowed operations (divide by 2 if even, or add/subtract 1) — same DP spirit, different move set.
- **Trial Division for Prime Factorization**: The `sqrt(i)` divisor-finding technique used here is the same fundamental trick used in primality testing and prime factorization — recognizing and reusing this trick across different number-theory problems is valuable.
- **Coin Change**: Conceptually similar "minimum steps/coins to reach a target" DP shape, though the "moves available" (coin denominations vs. divisors) differ significantly in structure.

---

## 8. Key Takeaways

- This problem is a DP over "minimum operations to reach a target," where the set of valid moves from each state (all divisors of the current value) changes per state — unlike simpler DP problems with a fixed, small set of moves (like Climbing Stairs).
- The `sqrt(i)` trial-division technique finds all divisors of `i` in O(sqrt(i)) time instead of O(i), by exploiting the fact that divisors always pair up around the square root.
- Starting with the "subtract 1" baseline move guarantees `dp[i]` is always well-defined (since 1 is always a valid divisor), with better options only ever improving on that baseline.
- Recognizing the `sqrt(n)` divisor-pairing trick is broadly useful across many number-theory-flavored problems, not just this one.
