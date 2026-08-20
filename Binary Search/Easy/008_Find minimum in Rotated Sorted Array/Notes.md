# Notes: Find Minimum in Rotated Sorted Array

## 1. Problem Recap

Given an array that was originally sorted ascending with **distinct values**, then **rotated** somewhere between 1 and N times, find the minimum element.

```
Original:  [0, 1, 2, 3, 4, 5, 6, 7]
Rotated:   [4, 5, 6, 7, 0, 1, 2, 3]     <- rotated at index 4
```

The minimum element is always the value **right at the rotation point** — the point where the "wrap-around" happens (e.g., `0` above, since it's smaller than the element before it, `7`).

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan through the array once, tracking the smallest value seen so far.

### Code Logic
```java
int min = nums[0];
for (int i = 1; i < nums.length; i++) {
    if (nums[i] < min) min = nums[i];
}
return min;
```

### Dry Run
`nums = [4, 5, 6, 7, 0, 1, 2, 3]`

| i | nums[i] | min so far |
|---|---|---|
| — | (init) | 4 |
| 1 | 5 | 4 |
| 2 | 6 | 4 |
| 3 | 7 | 4 |
| 4 | 0 | 0 |
| 5 | 1 | 0 |
| 6 | 2 | 0 |
| 7 | 3 | 0 |

Result: **0** ✅

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 3. About the Provided "Optimal" Solution: `Collections.min()`

```java
public int findMin(ArrayList<Integer> arr) {
    return Collections.min(arr);
}
```

`Collections.min()` is a built-in Java utility that finds the smallest element in any collection by internally iterating through all of it and comparing elements — essentially doing the exact same thing as the brute force loop above, just hidden inside a library call. It's concise and always correct, but:

- It does **not** know the array is a rotated *sorted* array.
- It treats the input as a completely arbitrary, unordered collection.
- Its time complexity is **O(n)**, same as the manual linear scan.

This is a perfectly valid, simple solution — but it doesn't exploit the special structure of the problem (a rotated sorted array), which is what allows for a genuinely faster **O(log n)** solution. That approach is shown below for completeness.

---

## 4. The True Optimal Approach: Modified Binary Search — O(log n)

### Idea
Because the array is a rotated version of a sorted array, it's made of (at most) two sorted segments. The minimum element is the **only** element in the array that is smaller than the element before it (or, thinking of it differently, it's the one point where the ascending order "breaks").

We can binary search for this break point:

- Maintain `low = 0`, `high = nums.length - 1`.
- At each step, compute `mid`.
- **If `nums[mid] > nums[high]`**: the minimum must be somewhere in the right half (excluding mid, since mid is greater than the rightmost element, meaning the break point is to its right). Set `low = mid + 1`.
- **If `nums[mid] <= nums[high]`**: the right half (from mid to high) is already sorted, meaning the minimum is at `mid` or somewhere to its left. Set `high = mid` (keep mid in the search space, since it could BE the minimum).
- Repeat until `low == high` — at that point, `nums[low]` is the minimum.

### Example Code
```java
public int findMin(int[] nums) {
    int low = 0, high = nums.length - 1;

    while (low < high) {
        int mid = low + (high - low) / 2;

        if (nums[mid] > nums[high]) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return nums[low];
}
```

### Dry Run
`nums = [4, 5, 6, 7, 0, 1, 2, 3]`

| Step | low | high | mid | nums[mid] | nums[high] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 7 | 3 | 7 | 3 | 7 > 3 | low = 4 |
| 2 | 4 | 7 | 5 | 1 | 3 | 1 <= 3 | high = 5 |
| 3 | 4 | 5 | 4 | 0 | 1 | 0 <= 1 | high = 4 |
| — | 4 | 4 | — | — | — | low == high | **loop ends** |

Result: `nums[4] = 0` ✅

### Complexity
- **Time:** O(log n) — search space halves every iteration.
- **Space:** O(1)

---

## 5. Dry Run of "Your Turn" Case (Using the O(log n) Approach)

`nums = [4, 5, 6, 7, -7, 1, 2, 3]`

| Step | low | high | mid | nums[mid] | nums[high] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 7 | 3 | 7 | 3 | 7 > 3 | low = 4 |
| 2 | 4 | 7 | 5 | 1 | 3 | 1 <= 3 | high = 5 |
| 3 | 4 | 5 | 4 | -7 | 1 | -7 <= 1 | high = 4 |
| — | 4 | 4 | — | — | — | low == high | **loop ends** |

Result: `nums[4] = -7` ✅

So for the quiz options `7, -7, 1, 0`, the correct answer is **-7**.

---

## 6. Comparing All Approaches

| Aspect | Brute Force | `Collections.min()` (Provided) | Modified Binary Search |
|---|---|---|---|
| Time Complexity | O(n) | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) | O(1) |
| Exploits rotated-sorted structure? | No | No | Yes |
| Implementation complexity | Very simple | Trivial (built-in call) | Slightly more involved |

---

## 7. Edge Cases to Consider

1. **Array not rotated at all** (or rotated by exactly `n`, which is equivalent to no rotation) — e.g., `nums = [1,2,3,4,5]` → minimum is `nums[0] = 1`. The binary search approach handles this correctly since the "break point" logic still converges to index 0 when the whole array is already sorted.
2. **Array rotated by exactly one position** — e.g., `nums = [5,1,2,3,4]` → minimum is `1`.
3. **Single-element array** — `nums = [5]` → minimum is `5` (loop doesn't even execute since `low == high` immediately).
4. **Two-element array** — e.g., `nums = [2,1]` → minimum is `1`.
5. **Negative numbers present** — as in the "your turn" example, verify the comparisons still work correctly with negative values (they do, since we're just comparing relative order, not sign).

---

## 8. Related Concepts / Follow-Ups

- **Find Minimum in Rotated Sorted Array II** — the version allowing duplicates, which (like "Search in Rotated Sorted Array II") requires an extra fallback (`if (nums[mid] == nums[high]) high--;`) since duplicates can make it ambiguous whether the minimum lies left or right of mid.
- **Find the Rotation Count** — equivalent to finding the *index* of the minimum element rather than its value; the same binary search applies, just return `low` instead of `nums[low]`.
- **Search in Rotated Sorted Array - I / II** — closely related problems that use a similar "identify the sorted half" binary search technique.

---

## 9. Key Takeaways

- `Collections.min()` (and any full-scan approach) is correct but O(n) — it doesn't leverage the fact that the array has a special rotated-sorted structure.
- The true optimal approach uses a modified binary search comparing `nums[mid]` against `nums[high]` to decide which half contains the "break point" (the minimum), achieving O(log n).
- The core comparison logic: if `nums[mid] > nums[high]`, the minimum is to the right of mid; otherwise, mid could be the minimum itself, so keep it in the search space and look left.
- This binary search technique generalizes directly to finding the rotation count and to the "Search in Rotated Sorted Array" family of problems.
