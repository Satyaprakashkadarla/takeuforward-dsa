# Notes: Find Square Root of a Number

## 1. Problem Recap

Given a positive integer `n`, find its square root. If `n` isn't a perfect square, return the **floor** of `sqrt(n)` (the largest integer whose square is `<= n`).

```
n = 28  ->  sqrt(28) ≈ 5.292  ->  floor = 5
```

---

## 2. Approach 1: Brute Force (Linear Scan)

### Idea
Try `1, 2, 3, ...` and check `i * i` against `n`. The moment `i * i` exceeds `n`, the previous value of `i` (i.e., `i - 1`) is the answer.

### Code Logic
```java
if (n == 0) return 0;
int i = 1;
while ((long) i * i <= n) {
    i++;
}
return i - 1;
```

Note: casting to `long` inside the loop condition avoids overflow for large `n`, since `i * i` as a plain `int` could overflow before the loop naturally terminates.

### Dry Run
`n = 28`

| i | i*i | i*i <= 28? |
|---|---|---|
| 1 | 1 | Yes |
| 2 | 4 | Yes |
| 3 | 9 | Yes |
| 4 | 16 | Yes |
| 5 | 25 | Yes |
| 6 | 36 | No -> loop stops |

Result: `i - 1 = 5` ✅

### Complexity
- **Time:** O(sqrt(n)) — we stop as soon as `i` exceeds `sqrt(n)`, not `n` itself. This is already much better than a full O(n) scan, but still not as fast as binary search for very large `n` (up to ~2.1 billion per the constraints).
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### The Core Idea — "Binary Search on the Answer"
This is a different flavor of binary search than searching within an array: instead, we binary search over the **space of possible answers**. Any valid floor-square-root of `n` must be an integer between `1` and `n` (inclusive), so we binary search that range directly.

### The Monotonic Property
The key requirement for binary search to work is a **monotonic condition** — as candidate values increase, the condition `mid * mid <= n` flips from `true` to `false` exactly once (never flip-flops). This lets us discard half the candidates at each step, just like a normal binary search.

```
mid:        1    2    3    4    5    6    7   ...
mid*mid<=28: T    T    T    T    T    F    F   ...
                                      ^
                              the flip point we're searching for
```

### Loop Logic
```java
int left = 1, right = n;

while (left <= right) {
    int mid = left + (right - left) / 2;

    if (mid <= n / mid) {
        left = mid + 1;   // mid is valid; try to find a larger valid one
    } else {
        right = mid - 1;  // mid is too large
    }
}

return right;
```

- If `mid * mid <= n` (checked via `mid <= n / mid`), `mid` is a valid candidate — but there might be a larger valid one, so we search right: `left = mid + 1`.
- If `mid * mid > n`, `mid` is too large — search left: `right = mid - 1`.
- When the loop ends (`left > right`), `right` has been pushed down to exactly the largest valid candidate — the answer.

### Why `mid <= n / mid` Instead of `mid * mid <= n`?
This is a defensive overflow-avoidance technique. `n` can be as large as `2^31 - 1` (~2.1 billion). If `mid` is also close to that value, `mid * mid` could be around `(2^31)^2`, which **massively overflows** a 32-bit `int` (int max is about 2.1 billion, but `mid*mid` could reach into the quintillions). Using integer division (`n / mid`) instead keeps every intermediate value safely within `int` range, since we're comparing `mid` against `n / mid` rather than computing a potentially huge product.

### Dry Run 1
`n = 36`

| Step | left | right | mid | n/mid | mid <= n/mid? | Action |
|---|---|---|---|---|---|---|
| 1 | 1 | 36 | 18 | 36/18=2 | 18 <= 2? No | right = 17 |
| 2 | 1 | 17 | 9 | 36/9=4 | 9 <= 4? No | right = 8 |
| 3 | 1 | 8 | 4 | 36/4=9 | 4 <= 9? Yes | left = 5 |
| 4 | 5 | 8 | 6 | 36/6=6 | 6 <= 6? Yes | left = 7 |
| 5 | 7 | 8 | 7 | 36/7=5 | 7 <= 5? No | right = 6 |
| — | 7 | 6 | — | — | left > right | **loop ends -> return right = 6** |

Result: **6** ✅ (matches expected output)

### Dry Run 2
`n = 28`

| Step | left | right | mid | n/mid | mid <= n/mid? | Action |
|---|---|---|---|---|---|---|
| 1 | 1 | 28 | 14 | 28/14=2 | 14 <= 2? No | right = 13 |
| 2 | 1 | 13 | 7 | 28/7=4 | 7 <= 4? No | right = 6 |
| 3 | 1 | 6 | 3 | 28/3=9 | 3 <= 9? Yes | left = 4 |
| 4 | 4 | 6 | 5 | 28/5=5 | 5 <= 5? Yes | left = 6 |
| 5 | 6 | 6 | 6 | 28/6=4 | 6 <= 4? No | right = 5 |
| — | 6 | 5 | — | — | left > right | **loop ends -> return right = 5** |

Result: **5** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`n = 50`

| Step | left | right | mid | n/mid | mid <= n/mid? | Action |
|---|---|---|---|---|---|---|
| 1 | 1 | 50 | 25 | 50/25=2 | 25 <= 2? No | right = 24 |
| 2 | 1 | 24 | 12 | 50/12=4 | 12 <= 4? No | right = 11 |
| 3 | 1 | 11 | 6 | 50/6=8 | 6 <= 8? Yes | left = 7 |
| 4 | 7 | 11 | 9 | 50/9=5 | 9 <= 5? No | right = 8 |
| 5 | 7 | 8 | 7 | 50/7=7 | 7 <= 7? Yes | left = 8 |
| 6 | 8 | 8 | 8 | 50/8=6 | 8 <= 6? No | right = 7 |
| — | 8 | 7 | — | — | left > right | **loop ends -> return right = 7** |

**Result: 7** ✅

So for the quiz options `50, 8, 7, 1`, the correct answer is **7**.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(sqrt(n)) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Overflow-safe? | Requires a `long` cast | Requires division instead of multiplication |
| Performance at n ≈ 2.1 billion | ~46,000 iterations (sqrt of 2^31) | ~31 iterations (log2 of 2^31) |

For `n` near its maximum constraint (`2^31 - 1 ≈ 2.1 billion`), brute force needs roughly **46,000 iterations** (since `sqrt(2.1 billion) ≈ 46,340`), while binary search needs only about **31 iterations** (`log2(2.1 billion) ≈ 31`) — a dramatic difference at scale.

---

## 5. Why Return `right` (Not `left`)?

This is a subtle but important detail. When the loop terminates, `left` has moved one step **past** the last valid candidate (since `left = mid + 1` happens on a valid match, always probing for something even bigger), while `right` has been pulled back to sit **exactly on** the last valid candidate. So `right` — not `left` — holds the correct floor square root.

You can verify this pattern in the dry runs above: in each case, the final successful comparison (`mid <= n/mid == true`) set `left = mid + 1`, and the *next* iteration's failure (`mid <= n/mid == false`) set `right` to exactly that same previously-successful `mid` value.

---

## 6. Edge Cases to Consider

1. **n = 0** — handled explicitly as a special case, returning `0` immediately (since the general binary search range `[1, n]` would be invalid/empty for `n = 0`).
2. **n = 1** — the only perfect square candidate is `1` itself; answer is `1`.
3. **Perfect square** — e.g., `n = 36` → exact answer `6` (see Dry Run 1).
4. **Non-perfect square** — e.g., `n = 28`, `n = 50` → floor of the true square root (see Dry Runs 2 and 3).
5. **Very large n** — e.g., `n = 2^31 - 1 = 2147483647` → binary search handles this efficiently in ~31 iterations, and the `mid <= n/mid` check prevents overflow that a naive `mid*mid <= n` check (in `int` arithmetic) would suffer from.

---

## 7. Related Concepts / Follow-Ups

- **Binary Search on the Answer**: This is a foundational example of this broader technique — instead of searching a data structure, you search a range of candidate answers using a monotonic predicate. Other classic examples: "Koko Eating Bananas," "Minimum Days to Make Bouquets," "Capacity to Ship Packages Within D Days," "Aggressive Cows."
- **Newton's Method for Square Roots**: A faster (typically converges even quicker than binary search) numerical technique for approximating square roots, often used in low-level math libraries.
- **`Math.sqrt()` in Java**: Uses floating-point arithmetic under the hood; for integer-only floor-square-root with guaranteed precision (no floating-point rounding surprises), the binary search approach shown here is the standard, safe choice in competitive programming and interviews.

---

## 8. Key Takeaways

- This problem is a textbook example of **"binary search on the answer"** — searching over a range of candidate values rather than array indices.
- The monotonic predicate (`mid*mid <= n` becomes false exactly once as `mid` increases) is what makes binary search valid here.
- Using `mid <= n / mid` instead of `mid * mid <= n` is a crucial overflow-avoidance trick given the problem's large constraint range (`n` up to `2^31 - 1`).
- After the loop, `right` (not `left`) holds the correct floor square root — understanding *why* requires tracing through how each pointer moves on success vs. failure.
