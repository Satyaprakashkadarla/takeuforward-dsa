# Notes: Search X in Sorted Array

## 1. Problem Recap

We are given a **sorted** (ascending), **0-indexed** array of integers `nums`, and a `target` value. We need to return the index of `target` if it exists in `nums`, otherwise return `-1`.

The key detail that shapes our approach is: **the array is sorted**. Whenever you see "sorted array" + "find element/index", binary search should be the first technique that comes to mind.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Walk through the array one element at a time and compare it to the target.

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == target) return i;
}
return -1;
```

### Dry Run
`nums = [-1, 0, 3, 5, 9, 12]`, `target = 9`

| i | nums[i] | nums[i] == target? |
|---|---|---|
| 0 | -1 | No |
| 1 | 0 | No |
| 2 | 3 | No |
| 3 | 5 | No |
| 4 | 9 | **Yes -> return 4** |

### Complexity
- **Time:** O(n) — in the worst case (target is the last element or absent), we check every element.
- **Space:** O(1) — no extra memory used.

### Why It's Not Optimal
This approach completely ignores the fact that the array is sorted. We're doing unnecessary comparisons that a smarter algorithm (binary search) can skip entirely.

---

## 3. Approach 2: Optimal (Binary Search)

### Idea
Because the array is sorted, at any point we can look at the middle element and immediately know which half of the array the target could be in (if it exists at all):

- If `nums[mid] == target`, we're done.
- If `nums[mid] < target`, the target (if present) must be to the **right** of `mid`, so we discard the left half.
- If `nums[mid] > target`, the target (if present) must be to the **left** of `mid`, so we discard the right half.

We repeat this, shrinking the search window (`left` to `right`) until either we find the target or the window becomes invalid (`left > right`), meaning the target isn't in the array.

### Code Logic
```java
int left = 0, right = nums.length - 1;

while (left <= right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] == target) return mid;
    else if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}

return -1;
```

### Why `mid = left + (right - left) / 2` instead of `(left + right) / 2`?
If `left` and `right` are both large (close to `Integer.MAX_VALUE`), `left + right` could **overflow** a 32-bit int. Using `left + (right - left) / 2` avoids that overflow risk. This is a standard defensive-coding habit in binary search implementations, even though it rarely matters for small constraint ranges like this problem's (`10^5`).

### Dry Run 1 — Target Found
`nums = [-1, 0, 3, 5, 9, 12]`, `target = 9`

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 3 | 3 < 9 | left = mid+1 = 3 |
| 2 | 3 | 5 | 4 | 9 | 9 == 9 | **return 4** |

### Dry Run 2 — Target Not Found
`nums = [-1, 0, 3, 5, 9, 12]`, `target = 2`

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 3 | 3 > 2 | right = mid-1 = 1 |
| 2 | 0 | 1 | 0 | -1 | -1 < 2 | left = mid+1 = 1 |
| 3 | 1 | 1 | 1 | 0 | 0 < 2 | left = mid+1 = 2 |
| — | 2 | 1 | — | — | left > right | **loop ends -> return -1** |

### Dry Run 3 — Target at Index 0
`nums = [-1, 0, 3, 5, 9, 12]`, `target = -1`

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 3 | 3 > -1 | right = mid-1 = 1 |
| 2 | 0 | 1 | 0 | -1 | -1 == -1 | **return 0** |

### Complexity
- **Time:** O(log n) — the search space halves on every iteration.
- **Space:** O(1) — iterative approach uses only a few extra variables (no recursion stack).

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses sorted property? | No | Yes |
| Suitable for large `n` (up to 10^5)? | Works, but slower | Much faster |
| Implementation complexity | Very simple | Slightly more involved |

For `n = 10^5`:
- Linear search could take up to ~100,000 comparisons in the worst case.
- Binary search takes at most ~17 comparisons (`log2(100000) ≈ 16.6`).

This is a massive efficiency gain, and it's why binary search is the expected/optimal solution for this problem in interviews.

---

## 5. Edge Cases to Consider

1. **Target at the first index** (`target = nums[0]`) — handled correctly since binary search checks all valid indices, not just the "middle" region.
2. **Target at the last index** (`target = nums[nums.length - 1]`).
3. **Target smaller than every element** (`target < nums[0]`) — should return -1.
4. **Target larger than every element** (`target > nums[nums.length - 1]`) — should return -1.
5. **Single-element array** (`nums.length == 1`) — make sure `left <= right` loop condition still behaves correctly when `left == right == 0`.
6. **Target not present but within value range of array** (a "gap" value like `2` in `[-1,0,3,5,9,12]`).
7. Since constraints guarantee **all elements are unique** and the array **is sorted**, we don't need to worry about duplicate handling or unsorted input validation.

---

## 6. Common Follow-Up Variations (Good to Know)

These aren't required for this exact problem, but interviewers often extend it:

- **Find first/last occurrence** of a target in a sorted array *with duplicates* (this problem states uniqueness, so not needed here, but a common follow-up).
- **Search in a rotated sorted array** (LeetCode 33) — a very common follow-up to this exact problem.
- **Find insertion position** — if the target isn't found, return the index where it *would* be inserted to keep the array sorted (LeetCode 35, "Search Insert Position"). This is a small tweak: instead of returning -1, return `left` at the end of the loop.
- **Find peak element / search in bitonic array** — other binary search variants worth practicing after mastering this one.

---

## 7. Key Takeaways

- Whenever the array is **sorted**, always consider **binary search** before defaulting to a linear scan — it's a strong signal in problem statements.
- Binary search reduces the time complexity from **O(n)** to **O(log n)**, which is a huge win for large inputs.
- Always be mindful of integer overflow when computing `mid`; prefer `left + (right - left) / 2` over `(left + right) / 2`.
- Practice tracing through the pointer movements (`left`, `right`, `mid`) by hand — it builds the intuition needed to adapt binary search to trickier variants (rotated arrays, first/last occurrence, etc.).
