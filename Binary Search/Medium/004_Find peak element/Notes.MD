# Notes: Find Peak Element

## 1. Problem Recap

Given an array `arr`, find the index of any **peak element** — an element strictly greater than both its neighbors. The boundaries are treated as `-∞`, meaning:

- The first element only needs to be greater than its right neighbor to be a peak (or be the only element).
- The last element only needs to be greater than its left neighbor to be a peak.

If multiple peaks exist, any one of them is a valid answer.

### Why This Is Solvable in O(log n) Even Though the Array Isn't Sorted
This is the surprising part of the problem. Binary search doesn't strictly require a sorted array — it requires a property that lets you **safely discard half the search space** at every step, based on a local comparison. Here, that property comes from the guarantee that `arr[0]` and `arr[n-1]` are bounded by `-∞`:

> **If you're climbing (`arr[i] < arr[i+1]`), a peak must exist somewhere ahead of you — worst case, you'll hit the end of the array, which is automatically a peak since its right neighbor is `-∞`.**

This guarantee is what makes binary search valid here, even on an unsorted array.

---

## 2. Approach 1: Brute Force (Linear Scan)

### Idea
Check every element against its left and right neighbor (treating out-of-bounds neighbors as `-∞`), and return the first index that satisfies the peak condition.

### Code Logic
```java
for (int i = 0; i < n; i++) {
    int left = (i == 0) ? Integer.MIN_VALUE : arr[i - 1];
    int right = (i == n - 1) ? Integer.MIN_VALUE : arr[i + 1];
    if (arr[i] > left && arr[i] > right) return i;
}
```

### Dry Run
`arr = [1, 2, 1, 3, 5, 6, 4]`

| i | arr[i] | left | right | Peak? |
|---|---|---|---|---|
| 0 | 1 | -∞ | 2 | 1 > -∞ and 1 > 2? No |
| 1 | 2 | 1 | 1 | 2 > 1 and 2 > 1? **Yes -> return 1** |

Result: **1** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — worst case scans the whole array.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Slope)

### Idea
Binary search over `[low, high] = [0, n-1]`, comparing `arr[mid]` to its right neighbor `arr[mid+1]` to determine which direction guarantees a peak:

- **If `arr[mid] < arr[mid+1]`** (climbing): a peak must exist somewhere to the right — move `low = mid + 1`.
- **If `arr[mid] > arr[mid+1]`** (descending, or `mid` itself is a peak): a peak must exist at `mid` or to its left — move `high = mid` (keep `mid` in the search space, since it might BE the peak).

Since `arr[i] != arr[i+1]` is guaranteed, we never have to handle equality — every comparison is strictly `<` or `>`.

### Code Logic
```java
int low = 0, high = arr.length - 1;

while (low < high) {
    int mid = low + (high - low) / 2;

    if (arr[mid] < arr[mid + 1]) {
        low = mid + 1;
    } else {
        high = mid;
    }
}

return low;
```

### Dry Run 1
`arr = [1, 2, 3, 4, 5, 6, 7, 8, 5, 1]` (length 10, indices 0–9)

| Step | low | high | mid | arr[mid] | arr[mid+1] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 9 | 4 | 5 | 6 | 5 < 6 (climbing) | low = 5 |
| 2 | 5 | 9 | 7 | 8 | 5 | 8 < 5? No (descending) | high = 7 |
| 3 | 5 | 7 | 6 | 7 | 8 | 7 < 8 (climbing) | low = 7 |
| — | 7 | 7 | — | — | — | low == high | **loop ends -> return 7** |

Result: **7** ✅ (matches expected output)

### Dry Run 2
`arr = [1, 2, 1, 3, 5, 6, 4]` (length 7, indices 0–6)

| Step | low | high | mid | arr[mid] | arr[mid+1] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 3 | 5 | 3 < 5 (climbing) | low = 4 |
| 2 | 4 | 6 | 5 | 6 | 4 | 6 < 4? No (descending) | high = 5 |
| 3 | 4 | 5 | 4 | 5 | 6 | 5 < 6 (climbing) | low = 5 |
| — | 5 | 5 | — | — | — | low == high | **loop ends -> return 5** |

Result: **5** ✅ — a different valid peak than the brute force found (index 1), but both are acceptable since the array has two peaks (indices 1 and 5).

### Dry Run 3 — "Your Turn" Case
`arr = [-2, -1, 3, 4, 5]` (length 5, indices 0–4)

| Step | low | high | mid | arr[mid] | arr[mid+1] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 3 | 4 | 3 < 4 (climbing) | low = 3 |
| 2 | 3 | 4 | 3 | 4 | 5 | 4 < 5 (climbing) | low = 4 |
| — | 4 | 4 | — | — | — | low == high | **loop ends -> return 4** |

**Result:** index **4**, value **arr[4] = 5**.

So for the quiz options `-1, 3, 4, 5`, the correct answer (peak value) is **5**. This makes sense: the array is entirely increasing, so the last element is the peak, since its right neighbor is treated as `-∞`.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Works on unsorted array? | Yes (trivially, since it just checks locally) | Yes (exploits the "climb toward a peak" guarantee) |
| Which peak is found (if multiple)? | The first one encountered left-to-right | Depends on the search path — may differ from brute force, but always valid |

---

## 5. Why Comparing to `arr[mid+1]` (Not `arr[mid-1]`) Works

The algorithm only ever looks at `arr[mid]` vs. `arr[mid+1]` — never `arr[mid-1]`. This is intentional and sufficient:

- If `arr[mid] < arr[mid+1]`, we know for certain the array is still rising at `mid`, so *somewhere* to the right (possibly the very last element) must be a peak.
- If `arr[mid] > arr[mid+1]`, we know `mid` is either a peak itself (if `arr[mid] > arr[mid-1]` too) or we're on a "downslope" that started somewhere to the left — either way, a peak is guaranteed to exist at `mid` or earlier.

We never need to check `arr[mid-1]` because the algorithm's correctness only relies on the **existence** of a peak in the chosen direction, not on identifying it immediately — the binary search naturally converges to it.

---

## 6. Edge Cases to Consider

1. **Single-element array** — `arr = [5]` → the only element is trivially a peak (both neighbors are `-∞`); the loop doesn't execute (`low == high` immediately), returns index 0.
2. **Strictly increasing array** — e.g., `arr = [-2,-1,3,4,5]` (see Dry Run 3) → the last element is the peak.
3. **Strictly decreasing array** — e.g., `arr = [5,4,3,2,1]` → the first element is the peak.
4. **Multiple peaks** — e.g., `arr = [1,2,1,3,5,6,4]` (see Dry Run 2) → any valid peak index is acceptable; binary search may find a different one than a linear scan would.
5. **Peak at the very start or very end** — already covered by the strictly increasing/decreasing cases above.
6. **Two-element array** — e.g., `arr = [1,2]` → index 1 is the peak (2 > 1, right neighbor is `-∞`). `arr = [2,1]` → index 0 is the peak.

---

## 7. Related Concepts / Follow-Ups

- **Find Peak Element in a 2D Matrix**: A natural harder follow-up (LeetCode 1901) that applies a similar "always a peak in the direction you're moving" binary search idea, but across two dimensions.
- **Local Maxima / Minima in an Array**: A more general concept — this problem is specifically about finding *a* local maximum in O(log n), which is only possible because of the boundary guarantees (`-∞` at both ends).
- **Bitonic Array Search**: A related family of problems involving arrays that increase then decrease (or vice versa), where finding the "peak" or "valley" is often the first step before searching for a specific target.

---

## 8. Key Takeaways

- Binary search doesn't require the array to be sorted — it only requires a property that guarantees which half of the search space still contains a valid answer. Here, that property is: "if you're climbing, a peak exists ahead of you."
- Comparing `arr[mid]` only to `arr[mid+1]` (never `arr[mid-1]`) is sufficient, because the algorithm relies on the *existence* of a peak in a direction, not on immediately identifying it.
- The `-∞` boundary convention is what guarantees a peak always exists somewhere in the array, and specifically that the very first or very last element can serve as a valid peak in monotonic arrays.
- If multiple peaks exist, binary search may return a different (but equally valid) peak than a linear scan would — both are correct per the problem statement.
