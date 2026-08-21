# Notes: Single Element in Sorted Array

## 1. Problem Recap

Given a **non-decreasing** sorted array where every number appears **exactly twice**, except for **one number that appears exactly once**. Find that single number.

```
nums = [1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6]
                        ^
                   single element (4)
```

---

## 2. Approach 1: Brute Force (XOR of All Elements)

### Idea
XOR has two useful properties:
- `x ^ x = 0` (a number XORed with itself cancels to zero)
- `x ^ 0 = x` (a number XORed with zero stays the same)

If we XOR **every** element in the array together, all the paired numbers cancel each other out (since each pair contributes `x ^ x = 0`), leaving only the single unpaired number in the result.

### Code Logic
```java
int result = 0;
for (int num : nums) {
    result ^= num;
}
return result;
```

### Dry Run
`nums = [1, 1, 3, 5, 5]`

| num | result (running XOR) |
|---|---|
| 1 | 0 ^ 1 = 1 |
| 1 | 1 ^ 1 = 0 |
| 3 | 0 ^ 3 = 3 |
| 5 | 3 ^ 5 = 6 |
| 5 | 6 ^ 5 = 3 |

Result: **3** ✅

### Complexity
- **Time:** O(n) — one pass through the array.
- **Space:** O(1)

### Why It's Not Optimal
This is a clever O(n) trick, but it completely ignores the fact that the array is **sorted**. Since it's sorted, we should be able to do better than O(n) — and indeed, binary search gets us to O(log n).

---

## 3. Approach 2: Optimal (Binary Search — Even/Odd Index Parity)

### The Core Insight
Because the array is sorted, every duplicate pair sits **adjacent to each other**. Before we reach the single element, this creates a very specific pattern:

```
Index:   0  1  2  3  4  5  6  7  8  9  10
nums:  [ 1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6 ]
         \_/    \_/    \_/  |   \_/    \_/
        even   even   even single odd   odd
        start  start  start      start start
```

- **Before** the single element: every pair starts at an **even index** — `nums[0]==nums[1]`, `nums[2]==nums[3]`, `nums[4]==nums[5]`, etc.
- **After** the single element: the pairing shifts by one, so every pair now starts at an **odd index** instead — `nums[7]==nums[8]`, `nums[9]==nums[10]`, etc.

The single element is located exactly at the point where this "pairs start on even index" pattern **breaks** and shifts to "pairs start on odd index."

### Algorithm
Binary search for this break point:

1. Compute `mid`.
2. **If `mid` is even:**
   - If `nums[mid] == nums[mid+1]`, the pairing still holds here (we're still before the single element) → the single element is further right → `left = mid + 2` (skip past this intact pair entirely).
   - Otherwise, the pattern has already broken by this point → the single element is at `mid` or to its left → `right = mid`.
3. **If `mid` is odd:**
   - If `nums[mid] == nums[mid-1]`, the pairing still holds → search right → `left = mid + 1`.
   - Otherwise → search left → `right = mid - 1`.
4. When `left == right`, `nums[left]` is the single element.

### Dry Run 1
`nums = [1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6]` (length 11, indices 0–10)

| Step | left | right | mid | mid parity | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 10 | 5 | odd | 3 | nums[5]=3 == nums[4]=3? Yes | left = 6 |
| 2 | 6 | 10 | 8 | even | 5 | nums[8]=5 == nums[9]=6? No | right = 8 |
| 3 | 6 | 8 | 7 | odd | 5 | nums[7]=5 == nums[6]=4? No | right = 6 |
| — | 6 | 6 | — | — | — | left == right | **loop ends** |

Result: `nums[6] = 4` ✅ (matches expected output)

### Dry Run 2
`nums = [1, 1, 3, 5, 5]` (length 5, indices 0–4)

| Step | left | right | mid | mid parity | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | even | 3 | nums[2]=3 == nums[3]=5? No | right = 2 |
| — | 0 | 2 | — | — | — | left < right, continue | |
| 2 | 0 | 2 | 1 | odd | 1 | nums[1]=1 == nums[0]=1? Yes | left = 2 |
| — | 2 | 2 | — | — | — | left == right | **loop ends** |

Result: `nums[2] = 3` ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7]` (length 13, indices 0–12)

| Step | left | right | mid | mid parity | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 12 | 6 | even | 4 | nums[6]=4 == nums[7]=4? Yes | left = 8 |
| 2 | 8 | 12 | 10 | even | 6 | nums[10]=6 == nums[11]=6? Yes | left = 12 |
| — | 12 | 12 | — | — | — | left == right | **loop ends** |

**Result:** `nums[12] = 7` ✅

So for the quiz options `1, 7, 6, 5`, the correct answer is **7**.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (XOR) | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses sorted property? | No | Yes |
| Technique | Bit manipulation | Index parity + binary search |

Both are O(1) space and both are reasonably elegant, but only the binary search version actually takes advantage of the array being sorted, achieving a better time complexity as a result.

---

## 5. Why `left = mid + 2` (Not `mid + 1`) in the Even Case?

When `mid` is even and `nums[mid] == nums[mid+1]`, we know this ENTIRE pair (`mid` and `mid+1`) is intact and occurs before the single element. There's no need to re-examine `mid+1` — we can safely skip both indices and start searching from `mid + 2`. This is a small but important optimization that keeps the parity logic consistent (since `mid + 2` preserves the even/odd relationship needed for the next iteration's checks).

---

## 6. Edge Cases to Consider

1. **Single element at the very start** — e.g., `nums = [1,2,2,3,3]` → answer `1` (index 0).
2. **Single element at the very end** — e.g., `nums = [1,1,2,2,3]` → answer `3` (last index), as seen in the "your turn" example.
3. **Array of length 1** — `nums = [5]` → trivially, the single element is `5` (loop doesn't execute since `left == right` immediately).
4. **Single element in the middle** — the general case shown in the examples above.
5. **Negative numbers present** — comparisons work identically regardless of sign.

---

## 7. Related Concepts / Follow-Ups

- **Single Number (Unsorted Array)**: The classic LeetCode problem where the array is *not* sorted — here, only the XOR approach applies directly (O(n) time, O(1) space), since there's no ordering to binary search over.
- **Single Number II / III**: Variants involving elements that appear three times (except one), or two elements that each appear once (except all others appearing twice) — these require different bit manipulation tricks beyond simple XOR.
- **Binary Search on Index Parity**: This "even/odd index breaks the pattern" technique is a nice example of binary searching on a property of position (parity) rather than directly on element values — useful to recognize as a general pattern for other structured-array problems.

---

## 8. Key Takeaways

- The brute force XOR trick is a slick O(n) solution that works on **any** array, sorted or not — but it doesn't exploit sortedness, so it can't beat O(n).
- The optimal binary search solution exploits a structural property specific to sorted arrays with paired duplicates: pairs start at even indices before the single element, and at odd indices after it.
- Binary searching on **index parity** (rather than directly on `nums[mid]` vs. `target`) is a distinctive pattern worth recognizing — it shows binary search can be adapted to detect subtler "breaks" in structure, not just value thresholds.
- `left = mid + 2` in the even-match case is a deliberate optimization to skip a confirmed-intact pair and preserve the parity logic for the next iteration.
