# Notes: Kth Missing Positive Number

## 1. Problem Recap

Given a strictly increasing array of unique positive integers, find the `k`th positive integer missing from the full sequence `1, 2, 3, ...` that isn't present in `arr`.

```
arr = [2, 3, 7, 11, 15]
Full sequence:  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 ...
In arr?         .  ✓  ✓  .  .  .  ✓  .  .  .  ✓  .  .  .  ✓
Missing:        1        4  5  6     8  9  10    12 13 14
```

Missing sequence: `1, 4, 5, 6, 8, 9, 10, 12, 13, 14, ...`. The 5th missing number is `8`.

---

## 2. The Core Formula: `missing = arr[i] - (i + 1)`

This is the key insight that makes binary search possible here. Think about it this way: if the array had **no** missing numbers at all, then `arr[i]` (0-indexed) would simply equal `i + 1` — the array would just be `[1, 2, 3, 4, 5, ...]`.

Any time `arr[i]` is *larger* than `i + 1`, the difference tells us exactly how many positive integers have been "skipped" (are missing) somewhere before or at this point in the array.

```
arr = [2, 3, 7, 11, 15]
i=0: arr[0]=2, expected=1, missing = 2-1 = 1   (number 1 is missing)
i=1: arr[1]=3, expected=2, missing = 3-2 = 1   (still just 1 missing so far)
i=2: arr[2]=7, expected=3, missing = 7-3 = 4   (1,4,5,6 are missing so far)
i=3: arr[3]=11, expected=4, missing = 11-4 = 7 (1,4,5,6,8,9,10 missing so far)
i=4: arr[4]=15, expected=5, missing = 15-5 = 10 (adds 12,13,14 too)
```

### Why This Is Monotonic
As `i` increases, `arr[i]` grows (strictly increasing array) while `i + 1` grows by exactly 1 each step. Since gaps in the array can only add up (never "un-skip" a number), `missing` is **monotonically non-decreasing** as `i` increases. This monotonicity is exactly what makes binary search valid.

---

## 3. Approach 1: Brute Force (Linear Scan)

### Idea
Walk through the natural number sequence `1, 2, 3, ...` alongside the array. At each step, check if the current number matches the array's current element:
- If it matches, that number isn't missing — advance both pointers.
- If it doesn't match, that number is missing — increment a missing-count, and if we've now found the kth missing number, return it.

### Code Logic
```java
int missingCount = 0, current = 1, i = 0;

while (missingCount < k) {
    if (i < arr.length && arr[i] == current) {
        i++;
    } else {
        missingCount++;
        if (missingCount == k) return current;
    }
    current++;
}
```

### Dry Run
`arr = [2, 3, 7, 11, 15]`, `k = 5`

| current | arr[i] (if valid) | Match? | missingCount | Action |
|---|---|---|---|---|
| 1 | 2 | No | 1 | not k yet |
| 2 | 2 | Yes | 1 | i++ |
| 3 | 3 | Yes | 1 | i++ |
| 4 | 7 | No | 2 | not k yet |
| 5 | 7 | No | 3 | not k yet |
| 6 | 7 | No | 4 | not k yet |
| 7 | 7 | Yes | 4 | i++ |
| 8 | 11 | No | 5 | **missingCount == k -> return 8** |

Result: **8** ✅

### Complexity
- **Time:** O(n) in the worst case (bounded by array length and/or k, whichever triggers termination first — since both are capped at 1000 per constraints, this is efficient enough for this problem's scale).
- **Space:** O(1)

---

## 4. Approach 2: Optimal (Binary Search)

### Idea
Since `missing = arr[i] - (i+1)` is monotonic, binary search for the **smallest index** where `missing >= k`. That index tells us exactly how many array elements come *before* the target missing number, which lets us compute the answer directly.

### Code Logic
```java
int left = 0, right = arr.length - 1;

while (left <= right) {
    int mid = left + (right - left) / 2;
    int missing = arr[mid] - (mid + 1);

    if (missing < k) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}

return left + k;
```

### Why `return left + k` Gives the Correct Answer
When the loop ends, `left` equals the number of array elements that appear **before** the kth missing number in the overall sequence. Since those `left` elements "occupy" `left` of the positions in the natural number sequence `1, 2, 3, ...`, the kth missing number must be `k` positions further along than where it would be without any array elements in the way — specifically, at position `left + k`.

Another way to see it: `left` is also the count of missing numbers accounted for by the point the loop settles (specifically, right before crossing the k threshold), and adding `k` shifts us to exactly the target missing number.

### Dry Run 1
`arr = [3, 5, 7, 10]`, `k = 6`

| Step | left | right | mid | arr[mid] | missing = arr[mid]-(mid+1) | <k=6? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 3 | 1 | 5 | 5-2=3 | 3<6 Yes | left=2 |
| 2 | 2 | 3 | 2 | 7 | 7-3=4 | 4<6 Yes | left=3 |
| 3 | 3 | 3 | 3 | 10 | 10-4=6 | 6<6? No | right=2 |
| — | 3 | 2 | — | — | — | left>right | **loop ends** |

`return left + k = 3 + 6 = 9` ✅ (matches expected output)

### Dry Run 2
`arr = [1, 4, 6, 8, 9]`, `k = 3`

| Step | left | right | mid | arr[mid] | missing | <k=3? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 6 | 6-3=3 | 3<3? No | right=1 |
| 2 | 0 | 1 | 0 | 1 | 1-1=0 | 0<3 Yes | left=1 |
| 3 | 1 | 1 | 1 | 4 | 4-2=2 | 2<3 Yes | left=2 |
| — | 2 | 1 | — | — | — | left>right | **loop ends** |

`return left + k = 2 + 3 = 5` ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`arr = [2, 3, 7, 11, 15]`, `k = 5`

| Step | left | right | mid | arr[mid] | missing | <k=5? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 7 | 7-3=4 | 4<5 Yes | left=3 |
| 2 | 3 | 4 | 3 | 11 | 11-4=7 | 7<5? No | right=2 |
| — | 3 | 2 | — | — | — | left>right | **loop ends** |

**`return left + k = 3 + 5 = 8`** ✅

So for the quiz options `9, 10, 5, 8`, the correct answer is **8**.

### Complexity
- **Time:** O(log n)
- **Space:** O(1)

---

## 5. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Uses sorted/monotonic property? | Not really | Yes (core to the algorithm) |

Given the constraints (`arr.length <= 1000`, `k <= 1000`), the difference in practice is negligible — but the binary search approach demonstrates a much more scalable technique that would matter significantly for larger inputs.

---

## 6. Edge Cases to Consider

1. **All missing numbers come before the array even starts** — e.g., `arr = [10, 11, 12], k = 3` → missing numbers `1,2,3,...` → answer `3` (since none of the array elements interfere with numbers 1-9).
2. **k is larger than any gap within the array, requiring numbers past the last array element** — e.g., `arr = [1,2,3], k = 2` → missing numbers are `4, 5, 6, ...` → answer `5`.
3. **Single-element array** — e.g., `arr = [5], k = 3` → missing numbers `1,2,3,4,6,...` → 3rd missing is `3`.
4. **k = 1** — find just the very first missing number.
5. **arr starts at 1** — e.g., `arr = [1,4,6,8,9], k=3` (see Dry Run 2) → the algorithm correctly handles the case where the array's first several elements ARE part of the natural sequence with no gap yet.

---

## 7. Related Concepts / Follow-Ups

- **Missing Number (Simple Version)**: A simpler classic problem — given an array containing `n` distinct numbers from `0` to `n`, find the missing one. Usually solved with sum/XOR tricks rather than binary search, since there's only ONE missing number to find, not the kth.
- **Find All Missing Numbers**: A related problem asking for ALL missing numbers in a range, often solved with an O(n) marking technique rather than binary search.
- **Binary Search on a Derived Monotonic Quantity**: This problem is a great example of binary searching not on the array's raw values, but on a *computed* quantity (`arr[i] - (i+1)`) that happens to be monotonic — a pattern worth recognizing beyond just this specific problem.

---

## 8. Key Takeaways

- The formula `missing = arr[i] - (i + 1)` elegantly captures "how many positive integers have been skipped by this point in the array" — and crucially, it's monotonically non-decreasing, enabling binary search.
- Binary search here isn't searching for a specific value in the array — it's searching for the boundary index where the "missing count so far" first reaches `k`.
- The final answer `left + k` requires understanding *why* `left` represents "array elements before the target" — a bit of a leap that's worth internalizing through the dry runs above.
- This problem is a nice example of binary searching on a **derived quantity** rather than directly on array values — a broader pattern applicable to many array problems beyond this one.
