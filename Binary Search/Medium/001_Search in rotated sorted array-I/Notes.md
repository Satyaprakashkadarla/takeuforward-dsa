# Notes: Search in Rotated Sorted Array - I

## 1. Problem Recap

We're given an array that was originally sorted in ascending order, but has been **rotated** at some unknown pivot point. For example:

```
Original:  [0, 1, 2, 4, 5, 6, 7]
Rotated:   [4, 5, 6, 7, 0, 1, 2]     <- rotated at index 4
```

We need to find the index of `k` in this rotated array, or return `-1` if it doesn't exist. All values are **distinct**, which turns out to be important (see the follow-up section).

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Since we just need to find whether `k` exists and at what index, a plain linear scan works regardless of rotation — the array being rotated doesn't break correctness, it only means we can't apply a *normal* binary search directly.

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == k) return i;
}
return -1;
```

### Dry Run
`nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 0`

| i | nums[i] | Match? |
|---|---|---|
| 0 | 4 | No |
| 1 | 5 | No |
| 2 | 6 | No |
| 3 | 7 | No |
| 4 | 0 | **Yes -> return 4** |

### Complexity
- **Time:** O(n)
- **Space:** O(1)

### Why It's Not Optimal
It completely ignores the fact that the array, despite being rotated, still consists of **two internally sorted segments**. That structure can be exploited for a faster search.

---

## 3. Approach 2: Optimal (Modified Binary Search)

### The Core Insight
Pick any `mid` index in a rotated sorted array. **At least one of the two halves — `[left, mid]` or `[mid, right]` — is guaranteed to be properly sorted** (non-rotated), even though the array as a whole isn't.

```
nums = [4, 5, 6, 7, 0, 1, 2]
              mid=3 (value 7)
        [4,5,6,7]  |  [0,1,2]
        sorted!         sorted too, in this case
```

Even in trickier splits, one side is always clean:

```
nums = [6, 7, 0, 1, 2, 4, 5]
              mid=3 (value 1)
        [6,7,0,1]  |  [2,4,5]
        NOT sorted     sorted!
```

### Algorithm
1. Compute `mid`. If `nums[mid] == k`, return `mid`.
2. Determine which half is sorted:
   - If `nums[left] <= nums[mid]`, the **left half** `[left..mid]` is sorted.
   - Otherwise, the **right half** `[mid..right]` is sorted.
3. Check whether `k` falls within the sorted half's value range:
   - If it does, discard the other half and search inside the sorted half.
   - If it doesn't, `k` must be in the other (rotated/unsorted) half — search there instead.
4. Repeat until found or the search space is exhausted.

### Why Comparing Value Ranges Works
Once we know a half is sorted, checking `k >= nums[left] && k < nums[mid]` (or the mirrored version for the right half) is just a normal "is this value within this sorted range" check — exactly like checking membership in a regular sorted subarray.

### Dry Run 1 — Target Found in Rotated (Right) Half
`nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 0`

| Step | left | right | mid | nums[mid] | Left half sorted? | Check | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 7 | nums[0]=4 <= nums[3]=7 → Yes | Is 0 in [4,7)? No | left = mid+1 = 4 |
| 2 | 4 | 6 | 5 | 1 | nums[4]=0 <= nums[5]=1 → Yes | Is 0 in [0,1)? Yes | right = mid-1 = 4 |
| 3 | 4 | 4 | 4 | 0 | match! | — | **return 4** |

Result: **4** ✅ (matches expected output)

### Dry Run 2 — Target Not Present
`nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 3`

| Step | left | right | mid | nums[mid] | Left half sorted? | Check | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 7 | Yes ([4,7]) | Is 3 in [4,7)? No | left = 4 |
| 2 | 4 | 6 | 5 | 1 | nums[4]=0 <= nums[5]=1 → Yes ([0,1]) | Is 3 in [0,1)? No | left = 6 |
| 3 | 6 | 6 | 6 | 2 | nums[6]=2 <= nums[6]=2 → Yes ([2,2]) | Is 3 in [2,2)? No | left = 7 |
| — | 7 | 6 | — | — | — | — | left > right, **loop ends -> return -1** |

Result: **-1** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 5`

| Step | left | right | mid | nums[mid] | Left half sorted? | Check | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 7 | nums[0]=4 <= nums[3]=7 → Yes | Is 5 in [4,7)? Yes | right = mid-1 = 2 |
| 2 | 0 | 2 | 1 | 5 | match! | — | **return 1** |

**Result: 1** ✅

So for the "pick your answer" quiz: of the given options `-1, 2, 1, 0`, **the correct answer is `1`**.

### Complexity
- **Time:** O(log n) — search space halves every iteration, just like standard binary search.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Modified Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Exploits sorted structure? | No | Yes (identifies sorted half each step) |

---

## 5. Why `nums[left] <= nums[mid]` (Not `<`)?

Using `<=` correctly handles the case where the left half has only **one element** (`left == mid`), in which case `nums[left]` trivially equals `nums[mid]`, and that single-element "half" is (trivially) sorted. Using strict `<` would still usually work due to how the branches are structured, but `<=` is the safer, more standard choice and avoids subtle off-by-one edge cases.

---

## 6. Edge Cases to Consider

1. **Array not rotated at all** (rotation point at index 0) — e.g., `nums = [0,1,2,4,5,6,7]`. The algorithm still works correctly; the "left half" will simply always be the sorted one.
2. **Array rotated by exactly one position** — e.g., `nums = [7,0,1,2,4,5,6]`.
3. **Single-element array** — `nums = [5]`:
   - `k = 5` → index 0
   - `k = 3` → -1
4. **Two-element array** — e.g., `nums = [3,1], k = 1` → index 1.
5. **Target equals nums[left] or nums[right] exactly** — verify the boundary comparisons (`>=`, `<=`) are inclusive where needed.
6. **Target not in the array at all** — must correctly return -1 without infinite looping (see Dry Run 2).

---

## 7. Important Follow-Up: What If There Are Duplicates?

This problem states "distinct values" specifically because that guarantee is what makes the sorted-half identification reliable. If duplicates were allowed, we could have `nums[left] == nums[mid] == nums[right]`, making it **impossible to tell which half is sorted** just from that one comparison (both could look "flat"). For example:

```
nums = [3, 1, 3, 3, 3], target = 1
```

Here `nums[left] == nums[mid] == nums[right] == 3`, but the array is still rotated with `1` hidden inside. The standard fix (see "Search in Rotated Sorted Array II") is: when `nums[left] == nums[mid] == nums[right]`, you can't determine the sorted half, so you shrink the search space conservatively (`left++; right--;`) and continue — this degrades the worst-case time complexity to O(n), but is still correct.

---

## 8. Related Concepts / Follow-Ups

- **Search in Rotated Sorted Array II** — the version of this problem allowing duplicate values, requiring the extra handling described above.
- **Find Minimum in Rotated Sorted Array** — a closely related problem that finds the "pivot point" itself using a similar sorted-half-identification strategy.
- **Find the Rotation Count** — equivalent to finding the index of the minimum element, which tells you how many times the array was rotated.

---

## 9. Key Takeaways

- A rotated sorted array always has **at least one sorted half** relative to any midpoint — this is the key structural property that keeps binary search applicable.
- The algorithm at each step: (1) check for exact match, (2) identify the sorted half, (3) check if target lies in that half's range, (4) discard the appropriate half.
- This approach preserves O(log n) time complexity even though the array isn't fully sorted.
- The "distinct values" constraint is essential — with duplicates, you can't always reliably identify the sorted half in O(1), and the algorithm needs a fallback (see Search in Rotated Sorted Array II).
