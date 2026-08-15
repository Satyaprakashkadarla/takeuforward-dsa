# Notes: Upper Bound

## 1. Problem Recap

Given a **sorted** array `nums` and an integer `x`, find the **smallest index** `i` such that `nums[i] > x` (strictly greater than — note this is different from lower bound's `>=`).

If every element in the array is `<= x`, no such index exists — return `nums.length`.

### Upper Bound vs Lower Bound
This problem is the twin of **Lower Bound**. The only difference is the comparison:

| | Condition | Meaning |
|---|---|---|
| Lower Bound | `nums[i] >= x` | First index where value is **not less than** x |
| Upper Bound | `nums[i] > x` | First index where value is **strictly greater than** x |

If `x` exists in the array (possibly with duplicates), `lowerBound(x)` points to its **first occurrence**, and `upperBound(x)` points to **one past its last occurrence**.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan the array from left to right. The moment we find an element `> x`, that index is our answer. If we finish the loop without finding one, return `nums.length`.

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] > x) return i;
}
return nums.length;
```

### Dry Run
`nums = [3, 5, 8, 15, 19]`, `x = 9`

| i | nums[i] | nums[i] > x? |
|---|---|---|
| 0 | 3 | No |
| 1 | 5 | No |
| 2 | 8 | No |
| 3 | 15 | **Yes -> return 3** |

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search)

### Idea — "Binary Search on the Boundary"
Just like lower bound, we're finding a **transition point** in the sorted array:

```
[values <= x]  |  [values > x]
               ^
       this is what we want (upper bound)
```

### Search Space Setup
```java
int low = 0, high = nums.length;   // half-open range [low, high)
```

### Loop Logic
```java
while (low < high) {
    int mid = low + (high - low) / 2;

    if (nums[mid] > x) {
        high = mid;      // mid could be the answer; look left for something smaller
    } else {
        low = mid + 1;   // mid <= x, so it's not valid; look right
    }
}
return low;
```

- When `nums[mid] > x`: `mid` satisfies our strict condition, so it's a *candidate*. We keep it in the search space (`high = mid`) and look left for an even smaller valid index.
- When `nums[mid] <= x`: `mid` does **not** satisfy `> x`, so it (and everything to its left, since the array is sorted) is discarded: `low = mid + 1`.

### Dry Run 1
`nums = [1, 2, 2, 3]`, `x = 2`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 2 | 2 > 2? No | low = mid+1 = 3 |
| 2 | 3 | 4 | 3 | 3 | 3 > 2? Yes | high = mid = 3 |
| — | 3 | 3 | — | — | low == high | **loop ends -> return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 2
`nums = [3, 5, 8, 15, 19]`, `x = 9`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 > 9? No | low = mid+1 = 3 |
| 2 | 3 | 5 | 4 | 19 | 19 > 9? Yes | high = mid = 4 |
| 3 | 3 | 4 | 3 | 15 | 15 > 9? Yes | high = mid = 3 |
| — | 3 | 3 | — | — | low == high | **loop ends -> return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [3, 5, 8, 15, 19]`, `x = 3`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 > 3? Yes | high = mid = 2 |
| 2 | 0 | 2 | 1 | 5 | 5 > 3? Yes | high = mid = 1 |
| 3 | 0 | 1 | 0 | 3 | 3 > 3? No | low = mid+1 = 1 |
| — | 1 | 1 | — | — | low == high | **loop ends -> return 1** |

**Result: 1** ✅

So for the "pick your answer" quiz in the problem: **the correct choice is `1`**, not `3`, `0`, or `2`. This makes sense because `nums[0] = 3` is not strictly greater than `x = 3`, but `nums[1] = 5` is — so index 1 is the smallest index satisfying `nums[i] > x`.

### Dry Run 4 — x Larger Than Every Element
`nums = [3, 5, 8, 15, 19]`, `x = 20`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 8 | 8 > 20? No | low = mid+1 = 3 |
| 2 | 3 | 5 | 4 | 19 | 19 > 20? No | low = mid+1 = 5 |
| — | 5 | 5 | — | — | low == high | **loop ends -> return 5** |

Result: **5** (equals `nums.length`) ✅

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
| High/low search bounds | N/A | `[0, nums.length]` (exclusive upper bound) |

---

## 5. Lower Bound vs Upper Bound — Side by Side

| | Lower Bound | Upper Bound |
|---|---|---|
| Condition to shrink `high` | `nums[mid] >= x` | `nums[mid] > x` |
| Condition to shrink `low` | `nums[mid] < x` | `nums[mid] <= x` |
| Meaning of result | First index with value `>= x` | First index with value `> x` |
| If x exists in array | Points to first occurrence | Points to one past last occurrence |

**One-line code diff:** Changing `>=` to `>` in the `if` condition is *literally* the only thing that turns a lower bound implementation into an upper bound implementation.

---

## 6. Edge Cases to Consider

1. **x smaller than every element** — e.g., `nums = [3,5,8], x = 1` → answer `0` (everything is `> x`).
2. **x larger than every element** — e.g., `nums = [3,5,8], x = 100` → answer `nums.length` (3).
3. **x equal to an element that appears multiple times** — e.g., `nums = [1,2,2,2,3], x = 2` → upper bound should point **past all** the 2's, i.e., index 4 (where 3 lives).
4. **Single-element array** — `nums = [5]`:
   - `x = 5` → answer `1` (nums.length, since 5 is not `> 5`)
   - `x = 4` → answer `0`
   - `x = 6` → answer `1`
5. **x exactly matches the last element** — e.g., `nums = [3,5,8], x = 8` → answer `3` (nums.length), since no element is strictly greater than 8.

---

## 7. Related Concepts / Follow-Ups

- **Count Occurrences of x in a Sorted Array**: `upperBound(x) - lowerBound(x)`.
- **Last Occurrence of x**: `upperBound(x) - 1` (only valid if x actually exists in the array; otherwise this doesn't give a meaningful "last occurrence").
- **Floor and Ceil of x in a Sorted Array**:
  - `ceil(x)` = the smallest element `>= x` → same as `nums[lowerBound(x)]` (if within bounds).
  - `floor(x)` = the largest element `<= x` → related to `lowerBound(x) - 1` or `upperBound(x) - 1` depending on whether x is present.
- **Search Insert Position** (LeetCode 35): Solved by lower bound, not upper bound — worth comparing the two to see why the choice of `>=` vs `>` matters for that specific problem.

---

## 8. Key Takeaways

- Upper bound finds the first index where the value is **strictly greater** than `x` — contrast this with lower bound's **greater than or equal to**.
- The binary search template is identical to lower bound's, with just the comparison operator flipped (`>` instead of `>=`).
- `high` must start at `nums.length` (not `nums.length - 1`), because the valid answer can be one-past-the-end when no element satisfies the condition.
- Together, lower bound and upper bound let you count occurrences, and find first/last occurrences, of any value in a sorted array — all in O(log n).
