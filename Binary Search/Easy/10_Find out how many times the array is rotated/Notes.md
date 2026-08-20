# Notes: Find Out How Many Times the Array Is Rotated

## 1. Problem Recap

Given an array originally sorted in ascending order (distinct values), then **right rotated** some unknown number of times (between `0` and `n-1`), determine **how many rotations** were performed.

```
Original:  [0, 1, 2, 3, 4, 5, 6, 7]
Rotated:   [4, 5, 6, 7, 0, 1, 2, 3]     <- rotated right 4 times
```

### The Key Insight
The number of rotations is exactly equal to the **index of the minimum element** in the rotated array. Why? Because right-rotating a sorted array `k` times moves the last `k` elements to the front — and the very first element that "wraps around" to the front is always the smallest one (since the array was ascending to begin with). So the minimum element ends up sitting at index `k`, which is precisely the rotation count.

This means: **this problem is "Find Minimum in Rotated Sorted Array" wearing a different name** — solve for the minimum's index, and you've solved this problem too.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan the array once, tracking both the minimum value seen so far and the index where it occurs.

### Code Logic
```java
int minIndex = 0;
for (int i = 1; i < nums.length; i++) {
    if (nums[i] < nums[minIndex]) {
        minIndex = i;
    }
}
return minIndex;
```

### Dry Run
`nums = [4, 5, 6, 7, 0, 1, 2, 3]`

| i | nums[i] | nums[i] < nums[minIndex]? | minIndex |
|---|---|---|---|
| — | (init) | — | 0 |
| 1 | 5 | 5 < 4? No | 0 |
| 2 | 6 | No | 0 |
| 3 | 7 | No | 0 |
| 4 | 0 | 0 < 4? Yes | 4 |
| 5 | 1 | 1 < 0? No | 4 |
| 6 | 2 | No | 4 |
| 7 | 3 | No | 4 |

Result: **4** ✅

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search)

### Idea
Since finding the rotation count reduces to finding the index of the minimum element, we binary search for the "break point" in the rotated sorted array — the one place where ascending order resets.

### Loop Logic
```java
int left = 0, right = n - 1;

while (left < right) {
    int mid = left + (right - left) / 2;

    if (nums.get(mid) < nums.get(right)) {
        right = mid;       // mid could be the minimum; look left
    } else {
        left = mid + 1;    // break point is further right
    }
}

return left;   // index of minimum = number of rotations
```

### Why Compare `nums[mid]` to `nums[right]` (Not `nums[left]`)?
- If `nums[mid] < nums[right]`, the segment from `mid` to `right` is already in sorted (ascending) order — meaning there's no "break" between them, so the minimum must be at `mid` or to its left. We keep `mid` in the search space (`right = mid`) since it might itself be the minimum.
- If `nums[mid] >= nums[right]`, the segment from `mid` to `right` contains the break point (since it's not fully ascending), so the minimum must be strictly to the right of `mid`. We move `left = mid + 1`.

### Dry Run 1
`nums = [4, 5, 6, 7, 0, 1, 2, 3]`

| Step | left | right | mid | nums[mid] | nums[right] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 7 | 3 | 7 | 3 | 7 < 3? No | left = 4 |
| 2 | 4 | 7 | 5 | 1 | 3 | 1 < 3? Yes | right = 5 |
| 3 | 4 | 5 | 4 | 0 | 1 | 0 < 1? Yes | right = 4 |
| — | 4 | 4 | — | — | — | left == right | **loop ends -> return 4** |

Result: **4** ✅ (matches expected output)

### Dry Run 2
`nums = [3, 4, 5, 1, 2]`

| Step | left | right | mid | nums[mid] | nums[right] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 5 | 2 | 5 < 2? No | left = 3 |
| 2 | 3 | 4 | 3 | 1 | 2 | 1 < 2? Yes | right = 3 |
| — | 3 | 3 | — | — | — | left == right | **loop ends -> return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [4, 5, 1, 2]`

| Step | left | right | mid | nums[mid] | nums[right] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 3 | 1 | 5 | 2 | 5 < 2? No | left = 2 |
| 2 | 2 | 3 | 2 | 1 | 2 | 1 < 2? Yes | right = 2 |
| — | 2 | 2 | — | — | — | left == right | **loop ends -> return 2** |

**Result: 2** ✅

So for the quiz options `2, 3, 0, 1`, the correct answer is **2**.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses rotated-sorted structure? | No | Yes |

---

## 5. Edge Cases to Consider

1. **Array not rotated at all** (0 rotations) — e.g., `nums = [1,2,3,4,5]` → the minimum sits at index 0, so the answer is `0`. The binary search correctly handles this since the whole array is one sorted segment.
2. **Array rotated by exactly 1** — e.g., `nums = [5,1,2,3,4]` → minimum at index 1, answer `1`.
3. **Array rotated by `n-1`** (maximum possible per constraints) — e.g., for `n=5`, `nums = [2,3,4,5,1]` → minimum at index 4, answer `4`.
4. **Single-element array** — `nums = [5]` → loop doesn't execute (`left == right` immediately), answer `0`.
5. **Two-element array** — e.g., `nums = [2,1]` → minimum at index 1, answer `1`.
6. **Negative numbers present** — comparisons work the same regardless of sign, since we're only comparing relative order.

---

## 6. Related Concepts / Follow-Ups

- **Find Minimum in Rotated Sorted Array**: The direct parent problem — this one just asks for the *index* of the minimum (the rotation count) instead of the minimum *value* itself. Same binary search, different return value (`left` instead of `nums[left]`).
- **Search in Rotated Sorted Array - I / II**: Related problems using a similar "identify sorted half" style of binary search, though those search for an arbitrary target rather than the minimum.
- **Find Minimum in Rotated Sorted Array II**: The duplicate-values variant, which needs an extra fallback (`if (nums.get(mid).equals(nums.get(right))) right--;`) since duplicates can make `nums[mid] < nums[right]` uninformative.

---

## 7. Key Takeaways

- The rotation count is exactly the **index** where the minimum element sits — recognizing this equivalence immediately connects this problem to "Find Minimum in Rotated Sorted Array."
- The binary search compares `nums[mid]` to `nums[right]` (not `nums[left]`) to decide whether the break point (minimum) lies within `[left, mid]` or `(mid, right]`.
- `right = mid` (not `mid - 1`) is used when `nums[mid] < nums[right]`, because `mid` itself could be the answer and shouldn't be discarded.
- This is a great example of how recognizing a problem as "secretly the same as" another one you've already solved can save significant effort.
