# Notes: Find Nth Root of a Number

## 1. Problem Recap

Given `N` and `M`, find an integer `X` such that `X^N == M`. If no such integer exists, return `-1`.

```
N = 3, M = 27  ->  X = 3, since 3^3 = 27
N = 4, M = 69  ->  no integer X satisfies X^4 = 69  ->  -1
```

This generalizes the "Find Square Root" problem (`N = 2`) to any power `N`.

---

## 2. Approach 1: Brute Force (Linear Scan)

### Idea
Try candidates `X = 1, 2, 3, ...` and compute `X^N`, comparing it to `M`. Since `X^N` grows monotonically with `X`, the moment `X^N` exceeds `M`, we know no larger `X` will work either — so we can stop early.

### Code Logic
```java
for (int x = 1; x <= M; x++) {
    long power = computePower(x, N, M);
    if (power == M) return x;
    else if (power > M) return -1;   // grows monotonically, no point continuing
}
return -1;
```

The helper `computePower` multiplies one factor at a time and bails out early if the running product exceeds `M`, to avoid unnecessary work and overflow.

### Dry Run
`N = 3, M = 27`

| x | x^3 | Comparison | Action |
|---|---|---|---|
| 1 | 1 | 1 < 27 | continue |
| 2 | 8 | 8 < 27 | continue |
| 3 | 27 | 27 == 27 | **return 3** |

Result: **3** ✅

### Complexity
- **Time:** O(M) in the absolute worst case (if we had to check every candidate up to M), but in practice it terminates much sooner since `x^N` grows fast — effectively closer to O(M^(1/N) · N).
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### The Core Idea
Just like "Find Square Root," this is a **binary search on the answer**: we search the range of possible integer roots `[1, M]`, using the monotonic fact that `X^N` increases as `X` increases. This monotonicity lets us discard half the candidates at each step.

### Algorithm
1. Binary search `mid` in `[low, high] = [1, M]`.
2. Use `comparePower(mid, N, M)` to compare `mid^N` to `M` without ever fully computing a potentially astronomical value:
   - Returns `1` if `mid^N > M` (computed incrementally, stopping the moment the running product exceeds `M`).
   - Returns `0` if `mid^N == M` exactly.
   - Returns `-1` if `mid^N < M`.
3. Based on the result:
   - `cmp == 0`: found the exact Nth root — return `mid`.
   - `cmp < 0`: `mid` is too small — search right (`low = mid + 1`).
   - `cmp > 0`: `mid` is too large — search left (`high = mid - 1`).
4. If the loop ends without an exact match, return `-1`.

### Why `comparePower` Multiplies One Factor at a Time (Instead of `Math.pow`)
- **Avoids overflow**: With `N` up to 30 and `mid` potentially in the hundreds or thousands, `mid^30` could be an astronomically large number — far beyond even `long` range. By checking `if (result > m) return 1;` after **every single multiplication**, we stop as soon as we know the answer is "too big," never needing to compute the full (potentially overflowing) value.
- **Avoids floating-point risk**: `Math.pow()` operates on `double`s and can introduce rounding errors, which is dangerous for a problem that needs an *exact* integer match. The incremental integer multiplication approach sidesteps this entirely.

### Dry Run 1
`N = 3, M = 27`

| Step | low | high | mid | comparePower(mid, 3, 27) | Action |
|---|---|---|---|---|---|
| 1 | 1 | 27 | 14 | 14^3 = 2744 > 27 → returns 1 | high = 13 |
| 2 | 1 | 13 | 7 | 7^3 = 343 > 27 → returns 1 | high = 6 |
| 3 | 1 | 6 | 3 | 3^3 = 27 == 27 → returns 0 | **return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 2
`N = 4, M = 69`

| Step | low | high | mid | comparePower(mid, 4, 69) | Action |
|---|---|---|---|---|---|
| 1 | 1 | 69 | 35 | 35^4 is huge, > 69 → returns 1 | high = 34 |
| 2 | 1 | 34 | 17 | 17^4 huge, > 69 → returns 1 | high = 16 |
| 3 | 1 | 16 | 8 | 8^4 = 4096 > 69 → returns 1 | high = 7 |
| 4 | 1 | 7 | 4 | 4^4 = 256 > 69 → returns 1 | high = 3 |
| 5 | 1 | 3 | 2 | 2^4 = 16 < 69 → returns -1 | low = 3 |
| 6 | 3 | 3 | 3 | 3^4 = 81 > 69 → returns 1 | high = 2 |
| — | 3 | 2 | — | — | low > high, **loop ends -> return -1** |

Result: **-1** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`N = 4, M = 81`

| Step | low | high | mid | comparePower(mid, 4, 81) | Action |
|---|---|---|---|---|---|
| 1 | 1 | 81 | 41 | 41^4 huge, > 81 → returns 1 | high = 40 |
| 2 | 1 | 40 | 20 | 20^4 huge, > 81 → returns 1 | high = 19 |
| 3 | 1 | 19 | 10 | 10^4 = 10000 > 81 → returns 1 | high = 9 |
| 4 | 1 | 9 | 5 | 5^4 = 625 > 81 → returns 1 | high = 4 |
| 5 | 1 | 4 | 2 | 2^4 = 16 < 81 → returns -1 | low = 3 |
| 6 | 3 | 4 | 3 | 3^4 = 81 == 81 → returns 0 | **return 3** |

**Result: 3** ✅

So for the quiz options `3, 4, 9, -1`, the correct answer is **3** (since 3^4 = 81).

### Complexity
- **Time:** O(N log M) — binary search does O(log M) iterations, and each `comparePower` call does up to O(N) multiplications in the worst case.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(M) worst case | O(N log M) |
| Space Complexity | O(1) | O(1) |
| Overflow-safe? | Yes (early bailout) | Yes (early bailout in comparePower) |
| Scales well for M up to 10^9? | Slower, though early termination helps a lot in practice | Much faster and more predictable |

For `M = 10^9` and `N = 2` (worst case for large M with small N), binary search does about `log2(10^9) ≈ 30` iterations, each doing up to 2 multiplications — extremely fast. Brute force would, in the worst case, need to check candidates up to `sqrt(10^9) ≈ 31,623` before finding an answer or concluding none exists — still fast for small `N`, but binary search's guarantee is more consistent across the full range of `N` (1 to 30) and `M` (1 to 10^9).

---

## 5. Why `Long.compare(result, m)` at the End of `comparePower`?

After the loop finishes computing all `n` multiplications without the running result exceeding `m`, we still need to determine whether the *final* result is less than, equal to, or greater than `m`. `Long.compare(result, m)` returns `-1`, `0`, or `1` accordingly — a clean, safe way to express this three-way comparison without manually writing `if/else if/else` for it.

---

## 6. Edge Cases to Consider

1. **N = 1** — the Nth root of any number is the number itself: `X^1 = M` means `X = M`. The algorithm handles this correctly since `comparePower` reduces to a single multiplication.
2. **M = 1** — the Nth root of 1 is always 1, for any N (`1^N = 1`).
3. **Exact match at the boundary** — e.g., `N = 2, M = 1` → answer `1`.
4. **No integer root exists** — e.g., `N = 4, M = 69` (see Dry Run 2) → `-1`.
5. **Large N (up to 30) with small M** — e.g., `N = 30, M = 2` → likely no integer root exists except trivial cases (`X=1` gives `1^30=1`, `X=2` gives `2^30` which is over a billion) — the early bailout in `comparePower` is essential here to avoid wasted computation.
6. **Large M (up to 10^9) with small N** — e.g., `N = 2, M = 10^9` → tests binary search's efficiency at scale.

---

## 7. Related Concepts / Follow-Ups

- **Find Square Root of a Number**: The `N = 2` special case of this exact problem — same binary-search-on-the-answer technique, just simpler since only one multiplication (`mid * mid`) is needed per check instead of a loop of `N` multiplications.
- **Binary Search on the Answer**: This is another strong example of the broader pattern — searching over a range of candidate answers using a monotonic predicate, rather than searching within a data structure.
- **Fast Exponentiation (Binary Exponentiation)**: An alternative way to compute `x^n` in O(log n) time instead of O(n), which could further optimize the `comparePower` helper if `N` were much larger than the current constraint (30) allows.

---

## 8. Key Takeaways

- This problem generalizes "Find Square Root" to arbitrary powers, using the same "binary search on the answer" technique.
- The critical implementation detail is `comparePower`'s early bailout: multiplying one factor at a time and stopping the moment the running product exceeds `M` avoids both wasted computation and dangerous overflow, especially with `N` up to 30.
- Avoiding `Math.pow()` (which uses floating-point and can introduce rounding errors) in favor of manual integer multiplication is essential for correctness when an *exact* integer match is required.
- The overall time complexity, O(N log M), reflects the two nested costs: O(log M) for the binary search itself, and O(N) for each power comparison.
