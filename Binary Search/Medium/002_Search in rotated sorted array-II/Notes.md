# Notes: Search in Rotated Sorted Array - II

## 1. Problem Recap

Same setup as "Search in Rotated Sorted Array - I," but with one important twist: **duplicate values are now allowed**. We need to return `true`/`false` for whether `k` exists in the rotated array — we no longer need to return an index.

```
nums = [7, 8, 1, 2, 3, 3, 3, 4, 5, 6]   (rotated, contains duplicate 3's)
```

---

## 2. Why Duplicates Change Everything

In "Search in Rotated Sorted Array - I," the core trick was: **at any midpoint, at least one half of the array is guaranteed to be properly sorted**, and we could tell which half by comparing `nums[left]` and `nums[mid]`.

With duplicates, this comparison can become **uninformative**. Consider:

```
nums = [3, 1, 3, 3, 3],  target = 1
              mid=2 (value 3)
left=0 (value 3), right=4 (value 3)
```

Here `nums[left] == nums[mid] == nums[right] == 3`. We genuinely **cannot tell** from this comparison alone whether the left half `[3,1,3]` or the right half `[3,3,3]` is the "properly sorted, non-rotated" one — both look identical at the boundary. The target `1` is hidden inside the left half, but our comparison gives us no signal to know that.

This is the fundamental reason the worst-case time complexity for this problem **cannot be better than O(n)** in general (this is actually a known, provable lower bound — you can construct adversarial duplicate-heavy inputs that force any correct algorithm toward linear time).

---

## 3. Approach 1 (and the "Optimal" Approach Provided): Linear Scan

### Idea
Given that duplicates can defeat the sorted-half trick in the worst case, a straightforward and always-correct approach is to simply scan the array and check each element against `k`.

### Code Logic
```java
for (int num : nums) {
    if (num == k) return true;
}
return false;
```

### Dry Run 1
`nums = [7, 8, 1, 2, 3, 3, 3, 4, 5, 6]`, `k = 3`

| i | nums[i] | Match? |
|---|---|---|
| 0 | 7 | No |
| 1 | 8 | No |
| 2 | 1 | No |
| 3 | 2 | No |
| 4 | 3 | **Yes -> return true** |

Result: **True** ✅ (matches expected output)

### Dry Run 2
`nums = [7, 8, 1, 2, 3, 3, 3, 4, 5, 6]`, `k = 10`

Scanning through, no element equals `10`, so the loop completes and returns `false`.

Result: **False** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [7, 8, 1, 2, 3, 3, 3, 4, 5, 6]`, `k = 7`

| i | nums[i] | Match? |
|---|---|---|
| 0 | 7 | **Yes -> return true** |

**Result: True** ✅

So for the quiz options `True` / `False`, the correct answer is **True**.

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 4. The "True" Optimal Approach: Modified Binary Search with Duplicate Handling

While the provided solution is a simple and perfectly valid O(n) approach (and is what many interviewers accept, given the O(n) worst-case is unavoidable anyway), it's worth understanding the **modified binary search** version, which behaves like O(log n) on *most* inputs and only degrades to O(n) in the specific adversarial case of many duplicates clustered at the boundaries.

### The Extra Rule
We reuse the same sorted-half logic from Rotated Sorted Array - I, but add one new case at the very top of each loop iteration:

```java
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    // Can't determine which half is sorted - shrink conservatively
    left++;
    right--;
    continue;
}
```

Everything else (checking `nums[mid] == k`, determining the sorted half via `nums[left] <= nums[mid]`, and checking if `k` falls within that half's range) stays exactly the same as in Rotated Sorted Array - I.

### Why `left++; right--;` Is Safe
When `nums[left] == nums[mid] == nums[right]`, we don't know which half is sorted, but we DO know that `nums[left]` and `nums[right]` themselves have already been "accounted for" as duplicate boundary values — shrinking both pointers inward by one step is guaranteed not to skip over the target if it exists elsewhere, and eventually breaks the "all equal" ambiguity.

### Complexity of This Version
- **Average / typical case:** O(log n) — most inputs don't have long runs of duplicate values sitting exactly at the search boundaries.
- **Worst case:** O(n) — e.g., `nums = [3,3,3,3,3,3,3,1,3,3,3]`, where the `left++; right--;` fallback ends up degrading to a near-linear scan.

This is why, for interviews, it's common (and acceptable) to simply state: *"because of duplicates, the worst-case is unavoidably O(n), so a straightforward linear scan is a reasonable solution — but here's a modified binary search that does better on average."*

---

## 5. Comparing the Approaches

| Aspect | Linear Scan (Provided/Optimal) | Modified Binary Search |
|---|---|---|
| Time Complexity (average) | O(n) | O(log n) (typical case) |
| Time Complexity (worst case) | O(n) | O(n) (adversarial duplicates) |
| Space Complexity | O(1) | O(1) |
| Implementation complexity | Very simple | More involved |
| Provable lower bound? | Matches it directly | Matches it in the worst case |

Since **both approaches share the same O(n) worst-case bound**, the linear scan is a legitimate and simple choice for this problem — the modified binary search is a nice-to-know optimization for the *average* case, not a strictly better asymptotic guarantee.

---

## 6. Edge Cases to Consider

1. **All elements identical** — e.g., `nums = [2,2,2,2,2], k = 2` → `true`. `k = 3` → `false`.
2. **Target not present at all** — e.g., `k = 10` (see Dry Run 2).
3. **Target at the very first or last index**.
4. **Array with no rotation** (pivot at index 0) — still a valid rotated array per the problem's guarantee.
5. **Single-element array** — `nums = [5]`:
   - `k = 5` → `true`
   - `k = 3` → `false`
6. **Heavy duplicate clustering around the rotation point** — this is the case that defeats the O(log n) sorted-half trick and forces worst-case O(n) behavior for any correct algorithm.

---

## 7. Related Concepts / Follow-Ups

- **Search in Rotated Sorted Array - I** — the distinct-values version, solvable in guaranteed O(log n) via the sorted-half binary search trick.
- **Find Minimum in Rotated Sorted Array II** — the duplicate-allowed version of finding the pivot/minimum element, which faces the exact same "can't tell which half is sorted" issue and uses the same `left++; right--;` fallback.

---

## 8. Key Takeaways

- Allowing duplicates in a rotated sorted array **fundamentally changes the complexity guarantees** — no algorithm can guarantee better than O(n) in the worst case for this variant.
- A simple linear scan (as provided) is a perfectly valid, easy-to-reason-about O(n) solution given this unavoidable worst case.
- A modified binary search with a `nums[left] == nums[mid] == nums[right]` fallback can do better than O(n) on typical/average inputs, while still degrading to O(n) on adversarial ones.
- Recognizing *why* duplicates break the "identify sorted half" trick (ambiguous boundary values) is the key conceptual leap from Rotated Sorted Array - I to this problem.
