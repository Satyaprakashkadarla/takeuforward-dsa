# Notes: Max Min Height

## 1. Problem Recap

You have `k` waterings. Each watering raises `w` consecutive flowers by 1. Make the **shortest** flower as tall as possible.

```
arr = [2, 3, 4, 5, 1], k = 2, w = 2
Water flowers 4 and 5 twice -> [2, 3, 4, 7, 3]   minimum = 2
```

The answer is bounded:

- **At least `min(arr)`:** do nothing.
- **At most `min(arr) + k`:** even if every watering hits the shortest flower, it grows by only `k`.

So the answer lies in a range of size at most `k + 1`, and there is a clean yes/no question we can ask about any value in it.

---

## 2. The Feasibility Question

`canAchieve(T)`: *can every flower reach height at least `T` using at most `k` waterings?*

This is **monotonic**: if `T` is achievable, every smaller target is too. That lets us search for the largest achievable `T`, either by scanning upward (brute force) or by binary search (optimal).

### The Greedy Check
Sweep left to right. At flower `i`, suppose it is still below `T` after the waterings already applied. We must water some window that covers `i`. Among all windows covering `i`, the one that **starts at `i`** is the best: every flower to the left is already tall enough, so the window should reach as far right as possible, helping the most flowers still to come. Water that window exactly as many times as flower `i` needs.

Counting those waterings against `k` tells us whether `T` is reachable.

---

## 3. Approach 1: Brute Force (Linear Scan Over the Answer)

### Idea
Start from `target = min(arr)`. While `target + 1` is achievable, increase `target`. Test each candidate with the greedy sweep, applying each watering explicitly to every flower in its window.

### Code Logic
```java
int target = min(arr);
while (canAchieve(arr, target + 1, k, w)) target++;
return target;

// canAchieve: copy the array, then
for (int i = 0; i < n; i++) {
    if (a[i] < target) {
        long needed = target - a[i];
        ops += needed;
        if (ops > k) return false;
        for (int j = i; j < Math.min(n, i + w); j++) a[j] += needed;
    }
}
return true;
```

### Dry Run
`arr = [2, 3, 4, 5, 1]`, `k = 2`, `w = 2`. Start at `target = 1` (the minimum).

| Test target | Greedy sweep | ops | Achievable? |
|---|---|---|---|
| 2 | Only the last flower (height 1) is short: water it once (its window runs off the end, so only it is affected in the sweep; a real window would be the last two flowers) | 1 | yes, so target = 2 |
| 3 | Flower 1 (height 2) short: 1 watering. Flower 5 (height 1) needs 2 more: ops reaches 3 | 3 > 2 | **no**, stop |

Result: **2** ✅

### Complexity
- **Time:** O(k * n * w): up to `k + 1` candidate targets, each check does O(n) steps with up to `w` updates.
- **Space:** O(n) for the working copy.

With `n` and `k` up to 10^5 this is hopeless, but it is a good correctness baseline.

---

## 4. Approach 2: Optimal (Binary Search + Difference Array)

### Idea
Two upgrades over brute force:

1. **Binary search the target** instead of scanning it, since `canAchieve` is monotonic.
2. **Make each check O(n)** by tracking window effects with a difference array instead of updating `w` flowers each time.

### The Difference-Array Check
```java
long[] extra = new long[n + 1];
long ops = 0, curr = 0;
for (int i = 0; i < n; i++) {
    curr += extra[i];                         // drop waterings that just expired
    long needed = target - arr[i] - curr;     // how short is flower i right now?
    if (needed > 0) {
        ops += needed;
        if (ops > k) return false;
        curr += needed;                       // these waterings now cover i
        if (i + w < n) extra[i + w] -= needed; // ...and stop covering at i + w
    }
}
return true;
```

- `curr` is the number of waterings currently covering flower `i`.
- A watering started at `i` covers flowers `i .. i + w - 1`. Writing `extra[i + w] -= needed` makes `curr` drop by `needed` when the sweep reaches flower `i + w`.

### Binary Search Details
```java
int lo = min(arr), hi = max(arr) + k + 1;
while (lo < hi) {
    int mid = lo + (hi - lo + 1) / 2;   // round UP
    if (canAchieve(arr, mid, k, w)) lo = mid;
    else hi = mid - 1;
}
return lo;
```

- **Why round up?** On success the code sets `lo = mid`. If `mid` rounded down, then with `hi = lo + 1` we would get `mid = lo` and loop forever.
- **Why is `lo = min(arr)` safe?** It is always achievable (water nothing).
- **Why `hi = max(arr) + k + 1`?** It is a loose but safe upper bound. The true answer is at most `min(arr) + k`.

### Dry Run 1: Example 1
`arr = [2, 3, 4, 5, 1]`, `k = 2`, `w = 2`. Initial `lo = 1`, `hi = 5 + 2 + 1 = 8`.

| lo | hi | mid | canAchieve(mid)? | Why | New range |
|---|---|---|---|---|---|
| 1 | 8 | 5 | no | flower 1 needs 3 waterings, more than k = 2 | hi = 4 |
| 1 | 4 | 3 | no | flower 1 needs 1; flower 5 then needs 2 more, ops = 3 > 2 | hi = 2 |
| 1 | 2 | 2 | yes | only flower 5 needs 1 watering, ops = 1 | lo = 2 |
| 2 | 2 | | | loop ends | answer **2** |

Detailed check for `mid = 3`:

| i | arr[i] | curr (after expiry) | needed = 3 - arr[i] - curr | ops | notes |
|---|---|---|---|---|---|
| 0 | 2 | 0 | 1 | 1 | water window [0,1] once; `extra[2] -= 1` |
| 1 | 3 | 1 | -1 | 1 | already tall enough |
| 2 | 4 | 0 (expired) | -1 | 1 | |
| 3 | 5 | 0 | -2 | 1 | |
| 4 | 1 | 0 | 2 | 3 | ops = 3 > k = 2, **fail** |

Result: **2** ✅ (matches expected output)

### Dry Run 2: Example 2
`arr = [5, 8]`, `k = 5`, `w = 1`. Initial `lo = 5`, `hi = 8 + 5 + 1 = 14`.

| lo | hi | mid | canAchieve(mid)? | Why | New range |
|---|---|---|---|---|---|
| 5 | 14 | 10 | no | flower 1 needs 5, flower 2 needs 2 more: ops = 7 > 5 | hi = 9 |
| 5 | 9 | 7 | yes | flower 1 needs 2, flower 2 is already 8 | lo = 7 |
| 7 | 9 | 8 | yes | flower 1 needs 3 | lo = 8 |
| 8 | 9 | 9 | yes | flower 1 needs 4, flower 2 needs 1: ops = 5 | lo = 9 |
| 9 | 9 | | | loop ends | answer **9** |

Result: **9** ✅ (matches expected output)

### Complexity
- **Time:** O(n * log(k + max(arr))). The search range is up to about 10^9 + 10^5, so around 30 rounds, each an O(n) check. For `n = 10^5` that is about 3 million basic steps.
- **Space:** O(n) for the difference array.

---

## 5. Comparing the Approaches

| Aspect | Brute Force | Optimal |
|---|---|---|
| Time | O(k * n * w) | O(n log(k + max)) |
| Space | O(n) | O(n) |
| Handles n = k = 10^5? | No | Yes |

---

## 6. Implementation Notes on the Provided Code

- **`long` for `ops`, `curr` and `extra`:** `needed` can be close to 10^9, and several values are added together, so `int` could overflow.
- **`int n` in `maxMinHeight` is unused** (the check recomputes it). It is harmless.
- **Windows near the right edge:** when `i + w > n` the greedy still "starts a window at `i`", which would stick out of the garden. This does not change the answer: a real window can be shifted left to end at `n - 1`, covering at least the same flowers on the right and more on the left, which only helps. Conversely, any real solution is also valid for the relaxed check. So feasibility is identical.
- **`if (i + w < n)`:** when the window runs to the end of the array, there is no later index where it expires, so nothing needs to be recorded.
- **Early exit on `ops > k`** keeps each check cheap and avoids long overflow.

---

## 7. Edge Cases to Consider

1. **`w = 1`:** each watering is a single flower, so the problem reduces to leveling up the shortest flowers (Example 2).
2. **`w = n`:** every watering raises all flowers, so the answer is `min(arr) + k`.
3. **All flowers equal to `v`:** the answer depends on `w`. For `w = 1` it is `v + k / n` (integer division), because waterings must be spread over all `n` flowers; for `w = n` it is `v + k`. Values in between are found by the same check, with no special case needed.
4. **Huge heights (`arr[i]` near 10^9):** the search range is wide, but binary search takes only about 30 rounds, and `long` prevents overflow.
5. **A single flower (`n = 1`, `w = 1`):** the answer is `arr[0] + k`.
6. **`k` smaller than the number of short flowers:** the answer may stay at `min(arr)` or rise only slightly.

---

## 8. Related Concepts / Follow-Ups

- **Maximize the Minimum Powered City** (LeetCode 2528): the same structure, with power stations instead of watering.
- **Aggressive Cows, Koko Eating Bananas, Capacity to Ship Packages:** other binary-search-on-the-answer problems.
- **Difference arrays:** also used in Range Addition, Corporate Flight Bookings and Car Pooling.
- **Sliding-window greedy:** "fix the leftmost unsatisfied element with the window that starts there" appears in Bulb Switcher, Minimum Number of K Consecutive Bit Flips and Minimum Number of Operations to Make Array Zero.

---

## 9. Key Takeaways

- "Maximize the minimum" suggests binary search on the answer: guess a target and test whether it is reachable.
- The feasibility check is greedy: fix the leftmost flower that is too short using the window that starts at it.
- A difference array (or a running `curr` plus expiry marks) turns range updates into O(1) work per flower.
- With an upward-rounding midpoint, `lo = mid` on success and `hi = mid - 1` on failure is the standard pattern for finding the **largest** feasible value.
