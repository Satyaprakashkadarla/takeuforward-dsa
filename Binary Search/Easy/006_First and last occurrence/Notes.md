# Notes: First and Last Occurrence

## 1. Problem Recap

Given an array `nums` sorted in **non-decreasing** order (i.e., duplicates are allowed), find the **starting** and **ending** index of a given `target` value.

If `target` doesn't exist in the array, return `[-1, -1]`.

### Why "Non-Decreasing" and Not Just "Sorted"?
The phrase "non-decreasing" is used instead of "strictly increasing" precisely because this problem is *about* duplicates — if the array had no duplicates, "first and last occurrence" would always be the same single index, and the problem would be trivial. The entire challenge here is handling **runs of repeated values**.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
A single scan of the array can find both the first and last occurrence:

- The **first time** we see `target`, record it as the starting index (and never overwrite it again).
- **Every time** we see `target` (including that first time), update the ending index — so by the time the loop finishes, it holds the position of the *last* match.

### Code Logic
```java
int first = -1, last = -1;
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == target) {
        if (first == -1) first = i;
        last = i;
    }
}
return new int[]{first, last};
```

### Dry Run
`nums = [5, 7, 7, 8, 8, 10]`, `target = 8`

| i | nums[i] | Match? | first | last |
|---|---|---|---|---|
| 0 | 5 | No | -1 | -1 |
| 1 | 7 | No | -1 | -1 |
| 2 | 7 | No | -1 | -1 |
| 3 | 8 | Yes | 3 (set) | 3 |
| 4 | 8 | Yes | 3 (unchanged) | 4 |
| 5 | 10 | No | 3 | 4 |

Result: **[3, 4]** ✅

### Complexity
- **Time:** O(n) — worst case, we scan the whole array even if target is never found.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search — Two Passes)

### Idea
Since the array is sorted, all occurrences of `target` form a **contiguous block**. We can locate the two ends of this block with two separate, slightly-modified binary searches:

1. **Find First Occurrence** — a binary search that, upon finding a match, doesn't stop. Instead, it records the match and keeps looking to the **left** (`right = mid - 1`), because there might be an even earlier occurrence of the same value.
2. **Find Last Occurrence** — a binary search that, upon finding a match, records it and keeps looking to the **right** (`left = mid + 1`), because there might be a later occurrence.

If the first search never finds `target` at all, we know immediately that `target` isn't in the array, so we skip the second search entirely and return `{-1, -1}`.

### Code Logic (Pass 1 — First Occurrence)
```java
int left = 0, right = nums.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) {
        result[0] = mid;
        right = mid - 1;   // keep searching left for an earlier match
    } else if (nums[mid] < target) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
```

### Code Logic (Pass 2 — Last Occurrence)
```java
left = 0; right = nums.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) {
        result[1] = mid;
        left = mid + 1;    // keep searching right for a later match
    } else if (nums[mid] < target) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
```

### Dry Run 1 — Find First Occurrence of 8
`nums = [5, 7, 7, 8, 8, 10]`, `target = 8`

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 7 | 7 < 8 | left = 3 |
| 2 | 3 | 5 | 4 | 8 | match! | result[0] = 4, right = 3 |
| 3 | 3 | 3 | 3 | 8 | match! | result[0] = 3, right = 2 |
| — | 3 | 2 | — | — | left > right | **loop ends** |

`result[0] = 3` — note how the recorded index kept improving (4 → 3) as the search narrowed toward the earliest match.

### Dry Run 2 — Find Last Occurrence of 8 (continuing same example)

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 7 | 7 < 8 | left = 3 |
| 2 | 3 | 5 | 4 | 8 | match! | result[1] = 4, left = 5 |
| 3 | 5 | 5 | 5 | 10 | 10 > 8 | right = 4 |
| — | 5 | 4 | — | — | left > right | **loop ends** |

`result[1] = 4`

**Final Result: [3, 4]** ✅ (matches expected output)

### Dry Run 3 — Target Not Found
`nums = [5, 7, 7, 8, 8, 10]`, `target = 6`

First-occurrence search:

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 7 | 7 > 6 | right = 1 |
| 2 | 0 | 1 | 0 | 5 | 5 < 6 | left = 1 |
| 3 | 1 | 1 | 1 | 7 | 7 > 6 | right = 0 |
| — | 1 | 0 | — | — | left > right | **loop ends** |

`result[0]` never got set, so it's still `-1`. Since `result[0] == -1`, we return `{-1, -1}` immediately **without running the second search** — this is a nice small optimization baked into the given solution.

**Result: [-1, -1]** ✅

### Dry Run 4 — Target at Index 0
`nums = [5, 7, 7, 8, 8, 10]`, `target = 5`

First-occurrence search:

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 7 | 7 > 5 | right = 1 |
| 2 | 0 | 1 | 0 | 5 | match! | result[0] = 0, right = -1 |
| — | 0 | -1 | — | — | left > right | **loop ends** |

`result[0] = 0`

Last-occurrence search:

| Step | left | right | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 7 | 7 > 5 | right = 1 |
| 2 | 0 | 1 | 0 | 5 | match! | result[1] = 0, left = 1 |
| 3 | 1 | 1 | 1 | 7 | 7 > 5 | right = 0 |
| — | 1 | 0 | — | — | left > right | **loop ends** |

`result[1] = 0`

**Final Result: [0, 0]** ✅ (matches expected output)

### Complexity
- **Time:** O(log n) + O(log n) = O(log n) — two binary searches, still logarithmic overall.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Number of passes | 1 (combined) | 2 (independent searches) |
| Early exit if not found? | No (still scans whole array) | Yes (skips second search) |

---

## 5. Why Two Separate Binary Searches Instead of One?

You might wonder: could we find both ends in a single pass, like we did for the "Floor and Ceil" problem? The key difference is that floor/ceil are looking for two *different* things (values less than and greater than `x`), which naturally split the search space. Here, both searches are looking for the **same value** `target`, just biased toward finding its leftmost vs. rightmost position. Because both searches share the exact same "is this less than, equal to, or greater than target" comparisons, there isn't a clean way to interleave them into one pass — running them as two clean, independent binary searches is simpler and still O(log n) overall.

This pattern (first occurrence = lower bound of target, last occurrence = upper bound of target minus one) is also directly related to the Lower Bound / Upper Bound techniques:
- `first occurrence = lowerBound(target)` (if `nums[lowerBound(target)] == target`)
- `last occurrence = upperBound(target) - 1` (if `target` exists)

---

## 6. Edge Cases to Consider

1. **Empty array** — `nums = []` → must return `[-1, -1]` without crashing (the loop condition `left <= right` naturally handles this since `right` starts at `-1`).
2. **Target not present at all** — e.g., `target = 6` in `[5,7,7,8,8,10]` → `[-1, -1]`.
3. **Target appears exactly once** — first and last occurrence are the same index (e.g., `target = 5` → `[0, 0]`, or `target = 10` → `[5, 5]`).
4. **Target appears at the very start or very end of the array**.
5. **Entire array consists of the same value** — e.g., `nums = [4,4,4,4], target = 4` → `[0, 3]`.
6. **Single-element array** — `nums = [5]`:
   - `target = 5` → `[0, 0]`
   - `target = 3` → `[-1, -1]`

---

## 7. Related Concepts / Follow-Ups

- **Lower Bound / Upper Bound**: As noted above, first occurrence and last occurrence can be derived directly from these two patterns, if you prefer a unified template.
- **Count Occurrences of an Element in a Sorted Array**: Once you have first and last occurrence, the count is simply `last - first + 1` (or `0` if not found).
- **Search in Rotated Sorted Array with Duplicates**: A trickier follow-up that combines this "find occurrence" idea with rotation handling.

---

## 8. Key Takeaways

- "First and last occurrence" is really about finding the **boundaries of a contiguous block of equal values** in a sorted array — binary search adapts naturally to this by not stopping at the first match, but continuing to narrow toward the correct edge.
- Two independent O(log n) binary searches (one biased left, one biased right) together still give an O(log n) overall solution — multiplying by a constant (2) doesn't change the complexity class.
- A nice optimization: if the first search doesn't find the target, you can skip the second search entirely, since the last occurrence search would also fail.
- This problem is deeply connected to Lower Bound / Upper Bound — recognizing that connection lets you solve it with either the two-pass approach shown here, or a unified lower/upper-bound-based approach.
