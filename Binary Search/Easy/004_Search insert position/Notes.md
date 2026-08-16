# Notes: Search Insert Position

## 1. Problem Recap

Given a **sorted** array `nums` of **distinct** integers and a `target`:

- If `target` exists in the array, return its index.
- If `target` does not exist, return the index where it **would be inserted** to keep the array sorted.

### The Key Insight
This problem is **exactly** the Lower Bound problem, just phrased differently. Think about it:

> "The index where target would be inserted to keep the array sorted"

is the same as:

> "The smallest index `i` such that `nums[i] >= target`"

Why? Because inserting `target` at that index pushes everything `>= target` one step to the right, which is precisely what keeps the array sorted. And if `nums[i] == target` exactly, that's the index we return directly (found or not found are handled by the *same* logic — no special-casing needed).

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan the array from left to right. The first index where `nums[i] >= target` is our answer — whether that's an exact match or the correct insertion point. If we scan the whole array without finding such an index, `target` is larger than everything, so it belongs at `nums.length`.

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] >= target) return i;
}
return nums.length;
```

### Dry Run
`nums = [1, 3, 5, 6]`, `target = 2`

| i | nums[i] | nums[i] >= target? |
|---|---|---|
| 0 | 1 | No |
| 1 | 3 | **Yes -> return 1** |

Result: **1** ✅ — 2 isn't in the array, but inserting it at index 1 gives `[1, 2, 3, 5, 6]`, which stays sorted.

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search / Lower Bound)

### Idea
Since the array is sorted, we binary search for the **boundary** where the condition `nums[mid] >= target` first becomes true — this is the lower bound pattern.

### Search Space Setup
```java
int low = 0, high = nums.length;   // half-open range [low, high)
```

We use `high = nums.length` (not `nums.length - 1`) because target could be larger than every element, in which case the correct answer is a position *past* the last valid index.

### Loop Logic
```java
while (low < high) {
    int mid = low + (high - low) / 2;

    if (nums[mid] >= target) {
        high = mid;      // candidate answer; look left for something smaller
    } else {
        low = mid + 1;   // mid is too small; look right
    }
}
return low;
```

### Dry Run 1
`nums = [1, 3, 5, 6]`, `target = 5`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 5 | 5 >= 5 | high = mid = 2 |
| 2 | 0 | 2 | 1 | 3 | 3 >= 5? No | low = mid+1 = 2 |
| — | 2 | 2 | — | — | low == high | **loop ends -> return 2** |

Result: **2** ✅ (matches expected output — target found exactly)

### Dry Run 2
`nums = [1, 3, 5, 6]`, `target = 2`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 5 | 5 >= 2 | high = mid = 2 |
| 2 | 0 | 2 | 1 | 3 | 3 >= 2 | high = mid = 1 |
| 3 | 0 | 1 | 0 | 1 | 1 >= 2? No | low = mid+1 = 1 |
| — | 1 | 1 | — | — | low == high | **loop ends -> return 1** |

Result: **1** ✅ (matches expected output — target not found, correct insertion index)

### Dry Run 3
`nums = [1, 3, 5, 6]`, `target = 7`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 5 | 5 >= 7? No | low = mid+1 = 3 |
| 2 | 3 | 4 | 3 | 6 | 6 >= 7? No | low = mid+1 = 4 |
| — | 4 | 4 | — | — | low == high | **loop ends -> return 4** |

Result: **4** ✅ (matches expected output — target larger than everything, insert at the end)

### Dry Run 4 — Target Smaller Than Everything
`nums = [1, 3, 5, 6]`, `target = 0`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 5 | 5 >= 0 | high = mid = 2 |
| 2 | 0 | 2 | 1 | 3 | 3 >= 0 | high = mid = 1 |
| 3 | 0 | 1 | 0 | 1 | 1 >= 0 | high = mid = 0 |
| — | 0 | 0 | — | — | low == high | **loop ends -> return 0** |

Result: **0** ✅ — target belongs at the very start.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. About the Given `Arrays.binarySearch` Solution

The version using Java's built-in `Arrays.binarySearch()` is a clever shortcut worth understanding:

```java
int index = Arrays.binarySearch(nums, target);
return index >= 0 ? index : -index - 1;
```

- If `target` **is found**, `Arrays.binarySearch` returns its index directly (a non-negative number).
- If `target` **is not found**, it returns `-(insertion point) - 1` (a negative number) — this encoding lets you recover the insertion point via `-index - 1`.

This works and is O(log n), but it relies on knowing this specific quirk of the Java standard library API. Understanding and implementing the manual lower-bound binary search (as in `Optimal.java`) is more valuable for interviews, since it builds the underlying skill and transfers directly to problems where a built-in method doesn't exist or doesn't fit (e.g., custom comparators, non-Java languages, or more complex binary-search-on-answer problems).

---

## 5. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses sorted property? | No | Yes |
| Equivalent to | — | Lower Bound of `target` |

---

## 6. Edge Cases to Consider

1. **Target smaller than every element** — e.g., `nums = [1,3,5,6], target = 0` → answer `0`.
2. **Target larger than every element** — e.g., `nums = [1,3,5,6], target = 7` → answer `nums.length` (4).
3. **Target equal to the first element** — e.g., `nums = [1,3,5,6], target = 1` → answer `0`.
4. **Target equal to the last element** — e.g., `nums = [1,3,5,6], target = 6` → answer `3`.
5. **Single-element array**:
   - `nums = [5], target = 5` → `0`
   - `nums = [5], target = 3` → `0`
   - `nums = [5], target = 7` → `1`
6. **Target falls exactly between two elements** — e.g., `nums = [1,3,5,6], target = 4` → answer `2` (would sit between 3 and 5).

Note: since the problem guarantees **distinct** values, there's no need to worry about duplicate handling — unlike some lower/upper bound variants.

---

## 7. Related Concepts / Follow-Ups

- **Lower Bound**: This problem *is* lower bound, just phrased as an "insertion point" instead of "smallest index with value >= x." Recognizing this equivalence is the whole trick to solving it optimally.
- **Upper Bound**: Not directly relevant here since values are distinct, but useful to contrast — see the Upper Bound notes for the `>` vs `>=` distinction.
- **Floor and Ceiling in a Sorted Array**: The "ceiling" of `target` (smallest element `>= target`) is exactly `nums[searchInsert(nums, target)]` if that index is in bounds.
- **Binary Search on Answer**: The half-open `[low, high)` template used here generalizes to many "find the boundary" problems beyond simple array search.

---

## 8. Key Takeaways

- "Where would this be inserted to keep the array sorted?" is a classic disguise for the **lower bound** pattern — always be on the lookout for this rephrasing.
- The same lower-bound binary search template (`high = nums.length`, shrink on `nums[mid] >= target`) handles both the "found" and "not found" cases without any special-case branching.
- Java's `Arrays.binarySearch` offers a built-in shortcut, but understanding the manual binary search implementation is more broadly useful and interview-relevant.
- Because values are distinct in this problem, there's no ambiguity about "which occurrence" to return — this simplifies things compared to lower/upper bound problems that must handle duplicates.
