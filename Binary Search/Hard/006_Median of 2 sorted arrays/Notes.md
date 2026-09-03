# Notes: Median of 2 Sorted Arrays

## 1. Problem Recap

Given two sorted arrays, find the median of their combined (logically merged) sorted sequence, without necessarily merging them.

```
arr1 = [2, 4, 5], arr2 = [1, 6]
Merged: [1, 2, 4, 5, 6]  (5 elements, odd)
Median = middle element = 4
```

---

## 2. Approach 1: Brute Force (Merge Two Sorted Arrays)

### Idea
Since both arrays are already sorted, merge them using the classic two-pointer merge technique (the same merge step used in merge sort). Once merged, finding the median is trivial — just look at the middle position(s).

### Code Logic
```java
int[] merged = new int[m + n];
int i = 0, j = 0, k = 0;
while (i < m && j < n) {
    merged[k++] = (arr1[i] <= arr2[j]) ? arr1[i++] : arr2[j++];
}
while (i < m) merged[k++] = arr1[i++];
while (j < n) merged[k++] = arr2[j++];

if ((m+n) % 2 == 1) return merged[(m+n)/2];
else return (merged[(m+n)/2 - 1] + merged[(m+n)/2]) / 2.0;
```

### Dry Run
`arr1 = [2, 4, 5]`, `arr2 = [1, 6]`

| Step | i | j | Compare | merged so far |
|---|---|---|---|---|
| 1 | 0 | 0 | 2 vs 1 → 1 smaller | [1] |
| 2 | 0 | 1 | 2 vs 6 → 2 smaller | [1,2] |
| 3 | 1 | 1 | 4 vs 6 → 4 smaller | [1,2,4] |
| 4 | 2 | 1 | 5 vs 6 → 5 smaller | [1,2,4,5] |
| 5 | 3 | 1 | i exhausted, append rest of arr2 | [1,2,4,5,6] |

Total length = 5 (odd) → median = `merged[2] = 4`.

Result: **4.0** ✅

### Complexity
- **Time:** O(m + n) — single merge pass.
- **Space:** O(m + n) for the merged array (can be reduced to O(1) by only tracking the two middle values during the merge, without storing the full array, but a full merge is shown for clarity).

### Why It's Not Optimal
Given `m, n` can each be up to `1000` (so up to 2000 combined), O(m+n) is actually quite fast for this problem's specific constraints — but the *true* optimal solution achieves O(log(min(m,n))), which is a fundamentally faster complexity class, especially relevant if constraints were much larger. This problem is a classic example used to teach binary-search-based partitioning precisely because a "good enough" O(m+n) solution exists, but a much more elegant O(log(min(m,n))) solution is possible with a clever binary search.

---

## 3. Approach 2: Optimal (Binary Search on the Partition Point)

### The Core Idea
We don't need to actually merge the arrays to find the median — we just need to find the correct way to "cut" both arrays such that:

1. **Every element on the combined left side is `<=` every element on the combined right side.**
2. **The combined left side has exactly `half = (m+n+1)/2` elements** (this formula elegantly handles both even and odd total lengths).

Once we find this correct partition, the median is derivable directly from the four boundary elements around the cuts — no merging required.

### Visualizing the Partition
```
arr1:  [ ... left1 | right1 ... ]
arr2:  [ ... left2 | right2 ... ]

If left1 <= right2 AND left2 <= right1, the partition is "balanced":
combined left side = {all of arr1's left1-side elements} U {all of arr2's left2-side elements}
combined right side = the rest
```

### Why Binary Search Over the SMALLER Array
The code always ensures `arr1` is the smaller array (swapping if necessary). This is a deliberate optimization: since `cut2 = half - cut1` is *forced* once `cut1` is chosen, binary searching over the smaller array keeps the search range as small as possible (`O(log(min(m,n)))`) and guarantees `cut2` always stays within valid bounds `[0, n]`.

### The Algorithm
1. Binary search `cut1` in `[0, m]` (m = length of the smaller array).
2. Compute `cut2 = half - cut1`.
3. Get the four boundary values (`left1`, `right1`, `left2`, `right2`), using `±infinity` sentinels when a cut is at an array's edge.
4. Check if the partition is valid: `left1 <= right2 && left2 <= right1`.
   - If valid: compute the median from these four values (see formulas below).
   - If `left1 > right2`: `cut1` is too far right — shrink: `high = cut1 - 1`.
   - Otherwise (`left2 > right1`): `cut1` is too far left — grow: `low = cut1 + 1`.

### Median Formulas Once the Correct Partition Is Found
- **Odd total length**: median = `max(left1, left2)` — the largest element on the combined left side is exactly the middle element (since the left side has one more element than the right side when total is odd, per the `half` formula).
- **Even total length**: median = `(max(left1, left2) + min(right1, right2)) / 2.0` — average of the largest left-side element and the smallest right-side element.

### Dry Run 1
`arr1 = [2, 4, 6]`, `arr2 = [1, 3, 5]` — both same length (3), no swap needed.

`m=3, n=3, half = (3+3+1)/2 = 3`

| Step | low | high | cut1 | cut2 | left1 | right1 | left2 | right2 | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 3 | 1 | 2 | arr1[0]=2 | arr1[1]=4 | arr2[1]=3 | arr2[2]=5 | 2<=5 & 3<=4 → Valid! | — |

Since `(m+n)=6` is even: median = `(max(2,3) + min(4,5)) / 2.0 = (3+4)/2 = 3.5`

Result: **3.5** ✅ (matches expected output)

### Dry Run 2
`arr1 = [2, 4, 6]`, `arr2 = [1, 3]` — arr1 (len 3) > arr2 (len 2), so **swap**: now search over `arr1=[1,3]` (smaller), `arr2=[2,4,6]`.

`m=2, n=3, half = (2+3+1)/2 = 3`

| Step | low | high | cut1 | cut2 | left1 | right1 | left2 | right2 | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | 2 | arr1[0]=1 | arr1[1]=3 | arr2[1]=4 | arr2[2]=6 | 1<=6 & 4<=3? No (4>3) | left2>right1 → low=2 |
| 2 | 2 | 2 | 2 | 1 | arr1[1]=3 | +∞ | arr2[0]=2 | arr2[1]=4 | 3<=4 & 2<=+∞ → Valid! | — |

Since `(m+n)=5` is odd: median = `max(3, 2) = 3`

Result: **3.0** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`arr1 = [2, 4, 5]`, `arr2 = [1, 6]` — arr1 (len 3) > arr2 (len 2), so **swap**: search over `arr1=[1,6]` (smaller), `arr2=[2,4,5]`.

`m=2, n=3, half = (2+3+1)/2 = 3`

| Step | low | high | cut1 | cut2 | left1 | right1 | left2 | right2 | Valid? | Action |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | 0 | 2 | 1 | 2 | arr1[0]=1 | arr1[1]=6 | arr2[1]=4 | arr2[2]=5 | 1<=5 & 4<=6 → Valid! | — |

Since `(m+n)=5` is odd: median = `max(1, 4) = 4`

**Result: 4.0** ✅

So for the quiz options `5.0, 4.0, 6.0, 8.0`, the correct answer is **4.0**.

### Complexity
- **Time:** O(log(min(m, n))) — binary search over the smaller array.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (Merge) | Optimal (Partition Binary Search) |
|---|---|---|
| Time Complexity | O(m + n) | O(log(min(m, n))) |
| Space Complexity | O(m + n) | O(1) |
| Requires actually merging? | Yes | No |

For `m = n = 1000`, brute force does about 2000 operations, while the optimal approach does roughly `log2(1000) ≈ 10` iterations — dramatically fewer operations, and crucially, no extra memory needed for a merged array.

---

## 5. Why `Integer.MIN_VALUE` / `Integer.MAX_VALUE` Sentinels?

When a cut is at the very start of an array (`cut == 0`), there's no element to the left of the cut — using `Integer.MIN_VALUE` as a stand-in ensures the comparison `left <= right2` (or similar) always trivially succeeds on that side, since nothing could be smaller. Similarly, when a cut is at the very end (`cut == length`), there's no element to the right — `Integer.MAX_VALUE` ensures comparisons involving that missing "right" boundary always trivially succeed too. This elegantly handles all the edge cases where a cut lands exactly at an array's boundary, without needing special-case branching.

---

## 6. Why `(long) Math.min(right1, right2)` in the Even-Length Case?

This is a defensive cast to avoid a subtle overflow risk: if `right1` or `right2` happens to be `Integer.MAX_VALUE` (the sentinel value used at an array's edge) and we're about to add it to another large value before dividing, doing the addition in `int` arithmetic could overflow. Casting to `long` before the addition ensures the intermediate sum is computed safely, even in this edge case.

---

## 7. Edge Cases to Consider

1. **One array is empty** — e.g., `arr1 = [], arr2 = [1,2,3]` → since `arr1` is smaller (length 0), the binary search range for `cut1` is just `[0,0]`, trivially finding the correct partition using only `arr2`'s structure.
2. **Arrays of very different sizes** — e.g., `arr1` has 1 element, `arr2` has 999 — binary search still converges in O(log(1)) = O(1) steps in this extreme case, since we always search the smaller array.
3. **All elements in one array are smaller than all elements in the other** — e.g., `arr1 = [1,2,3], arr2 = [10,20,30]` — verifies the partition logic correctly handles a "clean split" scenario.
4. **Duplicate values across both arrays** — the partition logic works correctly regardless of duplicates, since it only relies on `<=` comparisons, not strict ordering.
5. **Total length is odd vs. even** — both cases are explicitly handled via the `(m+n) % 2` check, using different formulas for the final median (see Dry Runs 2 and 3 for odd-length examples, Dry Run 1 for even-length).

---

## 8. Related Concepts / Follow-Ups

- **Kth Element of Two Sorted Arrays**: A generalization of this problem — instead of finding the median (which is really just the (m+n)/2-th or (m+n+1)/2-th element), find the k-th smallest element across two sorted arrays. The same partition-based binary search technique adapts directly.
- **Merge Two Sorted Arrays / Lists**: The brute-force building block used here — a fundamental technique worth mastering on its own (e.g., as the merge step of merge sort).
- **Binary Search on Partition Points (vs. Binary Search on the Answer)**: This problem represents a distinct binary search "shape" compared to the many "binary search on the answer" problems seen elsewhere (Koko Eating Bananas, Aggressive Cows, etc.) — here we're searching for a *position* that satisfies a balance condition, not searching over a range of candidate numeric answers.

---

## 9. Key Takeaways

- This problem showcases binary search used for something structurally different from "search on the answer" problems: here we binary search for the correct **partition index** that balances two arrays into matching left/right halves.
- Always binary searching over the *smaller* array is a crucial optimization, both for correctness (keeping `cut2` within valid bounds) and efficiency (minimizing the search space to `O(log(min(m,n)))`).
- The `±infinity` sentinel trick elegantly avoids special-casing partition points that land at an array's boundary.
- Understanding *why* `max(left1,left2)` gives the median for odd lengths, and why averaging `max(left1,left2)` with `min(right1,right2)` works for even lengths, requires visualizing the four boundary elements around a valid partition — well worth internalizing through the dry runs above, as this technique generalizes to the broader "Kth element across two sorted arrays" problem family.
