# Notes: Lower Bound

## 1. Problem Recap

Given a **sorted** array `nums` and an integer `x`, find the **smallest index** `i` such that `nums[i] >= x`.

If every element in the array is smaller than `x`, no such index exists — in that case, return `nums.length` (conceptually, "the position right after the last element," i.e., where `x` would be inserted to keep the array sorted).

### What "Lower Bound" Really Means
Think of it as answering: *"What is the first position in this sorted array where I could insert or find a value that is not less than x?"*

This is a fundamental building block used in the C++ STL (`std::lower_bound`) and Java's `Collections.binarySearch` family of utilities — it's a very common primitive in competitive programming and interviews.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan the array from left to right. The moment we find an element `>= x`, that index is our answer (since the array is sorted, this is guaranteed to be the *first* such index). If we finish the loop without finding one, return `nums.length`.

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] >= x) return i;
}
return nums.length;
```

### Dry Run
`nums = [3, 5, 8, 15, 19]`, `x = 9`

| i | nums[i] | nums[i] >= x? |
|---|---|---|
| 0 | 3 | No |
| 1 | 5 | No |
| 2 | 8 | No |
| 3 | 15 | **Yes -> return 3** |

### Complexity
- **Time:** O(n) — worst case (x larger than every element) scans the whole array.
- **Space:** O(1)

### Why It's Not Optimal
Same reasoning as always: we're not exploiting the sorted property. A binary search can find this boundary in logarithmic time instead.

---

## 3. Approach 2: Optimal (Binary Search)

### Idea — "Binary Search on the Boundary"
Unlike a classic binary search that looks for an *exact match*, here we're looking for a **boundary/transition point** in the array:

```
[values < x]  |  [values >= x]
              ^
      this is what we want (lower bound)
```

Because the array is sorted, all elements before this boundary are `< x`, and all elements from this boundary onward are `>= x`. Binary search can efficiently locate this transition point.

### Search Space Setup
```java
int low = 0, high = nums.length;   // note: high = nums.length, not nums.length - 1
```

We use `high = nums.length` (an exclusive upper bound / "one past the end") because the answer might legitimately be `nums.length` itself — if `x` is larger than every element, the lower bound is a position that doesn't correspond to any actual array index.

### Loop Logic
```java
while (low < high) {
    int mid = low + (high - low) / 2;

    if (nums[mid] >= x) {
        high = mid;      // mid could be the answer; look for something smaller to the left
    } else {
        low = mid + 1;   // mid is too small; the answer must be to the right
    }
}
return low;
```

- When `nums[mid] >= x`: `mid` **satisfies** our condition, so it's a *candidate* answer. But there could be an even smaller index that also satisfies it, so we don't discard `mid` — we set `high = mid` (keep `mid` in the search space) and keep looking to the left.
- When `nums[mid] < x`: `mid` does **not** satisfy the condition, so it can never be the answer, and neither can anything to its left (since the array is sorted, everything left of `mid` is even smaller). We move `low = mid + 1`.
- The loop ends when `low == high` — at this point, `low` (equivalently `high`) is exactly the lower bound.

### Dry Run 1
`nums = [1, 2, 2, 3]`, `x = 2`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 2 | 2 >= 2 | high = mid = 2 |
| 2 | 0 | 2 | 1 | 2 | 2 >= 2 | high = mid = 1 |
| 3 | 0 | 1 | 0 | 1 | 1 < 2 | low = mid+1 = 1 |
| — | 1 | 1 | — | — | low == high | **loop ends -> return 1** |

Result: **1** ✅ (matches expected output)

### Dry Run 2
`nums = [3, 5, 8, 15, 19]`, `x = 9`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 < 9 | low = mid+1 = 3 |
| 2 | 3 | 5 | 4 | 19 | 19 >= 9 | high = mid = 4 |
| 3 | 3 | 4 | 3 | 15 | 15 >= 9 | high = mid = 3 |
| — | 3 | 3 | — | — | low == high | **loop ends -> return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 3
`nums = [3, 5, 8, 15, 19]`, `x = 3`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 >= 3 | high = mid = 2 |
| 2 | 0 | 2 | 1 | 5 | 5 >= 3 | high = mid = 1 |
| 3 | 0 | 1 | 0 | 3 | 3 >= 3 | high = mid = 0 |
| — | 0 | 0 | — | — | low == high | **loop ends -> return 0** |

Result: **0** ✅ (matches expected output)

### Dry Run 4 — x Larger Than Every Element
`nums = [3, 5, 8, 15, 19]`, `x = 20`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 < 20 | low = mid+1 = 3 |
| 2 | 3 | 5 | 4 | 19 | 19 < 20 | low = mid+1 = 5 |
| — | 5 | 5 | — | — | low == high | **loop ends -> return 5** |

Result: **5** (which equals `nums.length`) ✅

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses sorted property? | No | Yes |
| High/low search bounds | N/A (simple loop) | `[0, nums.length]` (note the exclusive upper bound) |

---

## 5. Important Implementation Detail: Why `high = nums.length` and NOT `nums.length - 1`?

This is the single most common mistake when implementing lower bound. If you initialize `high = nums.length - 1` (like a standard "find exact element" binary search), you can never return `nums.length` as an answer — but that's a completely valid output when `x` is greater than every element in the array!

By starting with `high = nums.length`, we treat the search space as **half-open**: `[low, high)`. This means `high` is a valid *possible answer* (representing "one past the last real index"), while `low` through `high - 1` are valid array indices we're actually comparing.

---

## 6. Edge Cases to Consider

1. **x smaller than every element** — e.g., `nums = [3,5,8], x = 1` → answer should be `0` (insert at the very beginning).
2. **x larger than every element** — e.g., `nums = [3,5,8], x = 100` → answer should be `nums.length` (3), meaning "insert at the end."
3. **x equal to an element that appears multiple times** — e.g., `nums = [1,2,2,2,3], x = 2` → should return the index of the **first** occurrence of 2 (index 1), not any later duplicate.
4. **Single-element array** — `nums = [5]`:
   - `x = 5` → answer `0`
   - `x = 6` → answer `1`
   - `x = 4` → answer `0`
5. **x exactly matches the first or last element** — verify the boundary logic handles both ends correctly (see Dry Run 3 above for the first-element case).

---

## 7. Related Concepts / Follow-Ups

- **Upper Bound**: Find the first index where `nums[index] > x` (strictly greater, not `>=`). Nearly identical code — just change the condition from `nums[mid] >= x` to `nums[mid] > x`.
- **Search Insert Position** (LeetCode 35): This is *literally* the lower bound problem — "find the index where target would be inserted to keep the array sorted" is exactly what lower bound computes.
- **Count Occurrences of an Element in a Sorted Array**: Can be computed as `upperBound(x) - lowerBound(x)`.
- **First and Last Occurrence of an Element**: `lowerBound(x)` gives the first occurrence (if `nums[lowerBound(x)] == x`), and `upperBound(x) - 1` gives the last occurrence.
- This "search for a boundary" binary search template (using a half-open `[low, high)` range) is one of the most versatile binary search patterns — it generalizes to many "find the boundary where a condition flips from false to true" problems, including binary search on answer problems (e.g., "minimum capacity to ship packages," "koko eating bananas," etc.).

---

## 8. Key Takeaways

- Lower bound isn't about finding an *exact match* — it's about finding a **boundary/transition point** in a sorted array.
- Always initialize `high = nums.length` (not `nums.length - 1`) for lower bound / upper bound style problems, because the valid answer can be one-past-the-end.
- The condition `nums[mid] >= x` means "this could be the answer, but keep looking left for something smaller" → `high = mid` (keep mid in play).
- The condition `nums[mid] < x` means "this can never be the answer" → `low = mid + 1` (discard mid).
- Mastering this pattern unlocks a huge family of related problems (upper bound, search insert position, counting occurrences, binary search on answer).
