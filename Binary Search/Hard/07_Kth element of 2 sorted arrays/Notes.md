# Notes: Kth Element of 2 Sorted Arrays

## 1. Problem Recap

Given two sorted arrays, find the `k`th smallest element (1-indexed) across their combined, logically merged sequence — without necessarily merging them.

```
a = [2, 3, 6], b = [7, 9], k = 4
Merged: [2, 3, 6, 7, 9]
4th element (1-indexed) = 7
```

### This Problem Generalizes "Median of 2 Sorted Arrays"
"Median of 2 Sorted Arrays" is really just this problem with `k` fixed at (roughly) the halfway point of the combined length. Here, `k` can be **any** value from `1` to `m+n`, making this the more general version. If you understand the partition-based binary search for the median problem, this problem uses the *exact same technique*, just parameterized by `k` instead of a fixed half-point.

---

## 2. Approach 1: Brute Force (Merge Two Sorted Arrays, Stop Early)

### Idea
Merge the arrays using the standard two-pointer technique, but stop the moment we've produced `k` elements — no need to merge further.

### Code Logic
```java
int i = 0, j = 0, count = 0;
while (i < m && j < n) {
    int current = (a[i] <= b[j]) ? a[i++] : b[j++];
    count++;
    if (count == k) return current;
}
// handle any remaining leftover elements from whichever array isn't exhausted
```

### Dry Run
`a = [2, 3, 6]`, `b = [7, 9]`, `k = 4`

| Step | i | j | Compare | current | count |
|---|---|---|---|---|---|
| 1 | 0 | 0 | 2 vs 7 → 2 | 2 | 1 |
| 2 | 1 | 0 | 3 vs 7 → 3 | 3 | 2 |
| 3 | 2 | 0 | 6 vs 7 → 6 | 6 | 3 |
| 4 | 3 | 0 | i exhausted (a has no more), take from b | 7 | 4 → **count==k, return 7** |

Result: **7** ✅

### Complexity
- **Time:** O(k) in the best case (stopping early once k elements are produced), bounded overall by O(m + n) in the worst case (if k equals m+n).
- **Space:** O(1) — using pointers and a counter, no need to store a full merged array.

### Why It's Not Optimal
While this brute force is already quite efficient (especially with the early-stop optimization), it can still take up to O(m+n) time when `k` is large. The optimal partition-based approach achieves O(log(min(m,n))) regardless of how large `k` is, which is a meaningfully better complexity class, especially as array sizes grow (up to 10^4 each per constraints).

---

## 3. Approach 2: Optimal (Binary Search on the Partition Point)

### The Core Idea
Find a way to split arrays `a` and `b` such that the **combined left side has exactly `k` elements**, and every element on the left is `<=` every element on the right. Once found, the `k`th element is simply the **largest value among those k left-side elements**.

```
a:  [ ... leftA | rightA ... ]
b:  [ ... leftB | rightB ... ]

If leftA <= rightB AND leftB <= rightA, and (cutA + cutB) = k,
then max(leftA, leftB) is exactly the kth smallest element overall.
```

### Why This Works
If the partition is "balanced" (every left element `<=` every right element), then the `k` elements collected on the combined left side are *exactly* the `k` smallest elements across both arrays — and the largest among them is, by definition, the `k`th smallest element overall.

### Tighter Search Bounds Than the Median Problem
Unlike the median problem (which searches `cutA` over the full range `[0, m]`), this problem tightens the bounds based on `k`:

```java
int low = Math.max(0, k - n);   // can't take fewer from `a` than would force
                                  // taking MORE than n from `b` (b only has n total)
int high = Math.min(k, m);      // can't take more from `a` than k allows, or
                                  // more than `a`'s own length
```

This tightening is a nice optimization specific to the "find the kth element" framing — it narrows the search space right from the start, rather than relying purely on the partition-validity check to rule out invalid ranges.

### Code Logic
```java
if (m > n) return kthElement(b, a, k);  // always search the smaller array

int low = Math.max(0, k - n), high = Math.min(k, m);

while (low <= high) {
    int cutA = low + (high - low) / 2;
    int cutB = k - cutA;

    int leftA  = (cutA == 0) ? MIN : a[cutA-1];
    int rightA = (cutA == m) ? MAX : a[cutA];
    int leftB  = (cutB == 0) ? MIN : b[cutB-1];
    int rightB = (cutB == n) ? MAX : b[cutB];

    if (leftA <= rightB && leftB <= rightA) {
        return Math.max(leftA, leftB);
    }
    if (leftA > rightB) high = cutA - 1;
    else low = cutA + 1;
}
```

### Dry Run 1
`a = [2,3,6,7,9]`, `b = [1,4,8,10]`, `k = 5` — `m=5, n=4`. Since `m > n`, **swap**: search over `a=[1,4,8,10]` (smaller, now called `a` internally), `b=[2,3,6,7,9]`.

`low = max(0, 5-5) = 0`, `high = min(5, 4) = 4`

| Step | low | high | cutA | cutB | leftA | rightA | leftB | rightB | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 3 | a[1]=4 | a[2]=8 | b[2]=6 | b[3]=7 | 4<=7 & 6<=8 → Valid! | — |

`max(leftA, leftB) = max(4, 6) = 6`

Result: **6** ✅ (matches expected output)

### Dry Run 2
`a = [100,112,256,349,770]`, `b = [72,86,113,119,265,445,892]`, `k = 7` — `m=5, n=7`. `m < n`, no swap needed.

`low = max(0, 7-7) = 0`, `high = min(7, 5) = 5`

| Step | low | high | cutA | cutB | leftA | rightA | leftB | rightB | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 5 | a[1]=112 | a[2]=256 | b[4]=265 | b[5]=445 | 112<=445 & 265<=256? No (265>256) | leftB>rightA → low=3 |
| 2 | 3 | 5 | 4 | 3 | a[3]=349 | a[4]=770 | b[2]=113 | b[3]=119 | 349<=119? No | leftA>rightB → high=3 |
| 3 | 3 | 3 | 3 | 4 | a[2]=256 | a[3]=349 | b[3]=119 | b[4]=265 | 256<=265 & 119<=349 → Valid! | — |

`max(leftA, leftB) = max(256, 119) = 256`

Result: **256** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`a = [2,3,6]`, `b = [7,9]`, `k = 4` — `m=3, n=2`. `m > n`, so **swap**: search over `a=[7,9]` (smaller), `b=[2,3,6]`.

`low = max(0, 4-3) = 1`, `high = min(4, 2) = 2`

| Step | low | high | cutA | cutB | leftA | rightA | leftB | rightB | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 1 | 2 | 1 | 3 | a[0]=7 | a[1]=9 | b[2]=6 | +∞ | 7<=+∞ & 6<=9 → Valid! | — |

`max(leftA, leftB) = max(7, 6) = 7`

**Result: 7** ✅

So for the quiz options `7, 6, 9, 1`, the correct answer is **7**.

### Complexity
- **Time:** O(log(min(m, n)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (Merge, Early Stop) | Optimal (Partition Binary Search) |
|---|---|---|
| Time Complexity | O(min(k, m+n)) | O(log(min(m, n))) |
| Space Complexity | O(1) | O(1) |
| Performance when k is large (close to m+n)? | Approaches O(m+n) | Still O(log(min(m,n))) - unaffected by k's size |

The optimal approach's real advantage shines when `k` is large — the brute force degrades toward O(m+n) as `k` approaches the combined array length, while the binary search approach's complexity is entirely independent of `k`'s specific value (only depending on the smaller array's length).

---

## 5. Comparing This Problem to "Median of 2 Sorted Arrays"

| | Median of 2 Sorted Arrays | Kth Element of 2 Sorted Arrays |
|---|---|---|
| Target split size | Fixed at `(m+n+1)/2` (or handles even/odd specially) | Exactly `k` (any value from 1 to m+n) |
| Search bounds for cutA | `[0, m]` | `[max(0, k-n), min(k, m)]` (tighter, using k) |
| Result formula | `max(left1,left2)` (odd) or average with `min(right1,right2)` (even) | Always just `max(leftA, leftB)` |
| Relationship | A special case of this problem | The general form |

Recognizing that the median problem is just this problem with `k` fixed at the halfway point is a great "aha" moment — once you understand one, the other follows almost immediately.

---

## 6. Edge Cases to Consider

1. **k = 1** — the smallest element overall; the algorithm correctly handles this via the tightened bounds.
2. **k = m + n** — the largest element overall.
3. **One array much smaller than the other** — e.g., `a` has 1 element, `b` has 10,000 — binary search still converges in O(log(1)) ≈ O(1) steps since we always search the smaller array.
4. **k falls entirely within one array's range** — e.g., `a = [100, 200], b = [1, 2, 3], k = 5` → the 5th element could be entirely determined by `a`'s contribution; the tightened bounds (`low = max(0, k-n)`) help the search converge efficiently even in such skewed cases.
5. **Duplicate values across arrays** — the partition logic works correctly regardless of duplicates, relying only on `<=` comparisons.

---

## 7. Related Concepts / Follow-Ups

- **Median of 2 Sorted Arrays**: The specific case of this problem where `k` is fixed at the halfway point(s) of the combined length — see that problem's notes for the median-specific formulas.
- **Kth Smallest Element in a Sorted Matrix**: A related but distinct problem involving 2D sorted structure, often solved with a different binary-search-on-value technique rather than this partition-based approach.
- **Merge K Sorted Arrays/Lists**: A generalization to more than 2 arrays, typically solved with a min-heap rather than this partition technique (which is specific to exactly 2 arrays).

---

## 8. Key Takeaways

- This problem is the general form of "Median of 2 Sorted Arrays" — once you master the partition-based binary search technique here, the median problem becomes a simple specialization.
- Tightening the search bounds using `k` (`low = max(0, k-n)`, `high = min(k, m)`) is a nice problem-specific optimization beyond the naive `[0, m]` range, narrowing the search space immediately based on what's mathematically possible.
- The result is always simply `max(leftA, leftB)` once a valid partition is found — no odd/even branching needed here, unlike the median problem, since we're directly asking for a specific kth element rather than an average of two middle values.
- Always searching the smaller array remains essential for both correctness (keeping the paired cut in bounds) and efficiency (minimizing the O(log(min(m,n))) search space).
