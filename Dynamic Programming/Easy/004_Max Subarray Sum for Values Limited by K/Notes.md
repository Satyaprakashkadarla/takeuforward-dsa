# Notes: Max Subarray Sum for Values Limited by K

## 1. Problem Recap

Find the maximum sum of a contiguous subarray where **every** element is `<= k`.

```
arr = [3, 2, 2, 3, 1, 1, 1, 3], k = 2
Valid segments: [2,2] (sum 4), [1,1,1] (sum 3)
Answer: 4
```

### The Key Insight — Elements > k Are Hard Breaks
Any element greater than `k` can **never** appear in a valid subarray. Since a subarray must be contiguous, such an element effectively **splits the array** into independent chunks — each chunk being a maximal run of elements that are all `<= k`.

```
arr = [3, 2, 2, 3, 1, 1, 1, 3]
         ^-------^  ^-------^
         invalid    chunk1=[2,2]   invalid   chunk2=[1,1,1]  invalid
         (3)                       (3)                        (3)
```

Within each chunk, **every** element already satisfies the `<= k` constraint — there's no further filtering needed. Since all values are non-negative (`0 <= arr[i]`), the best possible subarray sum within a chunk is simply the **entire chunk's sum** (dropping any element from within a valid chunk can only reduce the total, never help, since nothing is negative).

So the problem reduces to: **split the array at every element `> k`, and find the maximum chunk-sum among the resulting pieces.**

---

## 2. Approach 1: Brute Force (Check Every Subarray)

### Idea
Try every possible subarray `(i, j)`, verifying along the way that no element exceeds `k`, and track the best valid sum found.

### Code Logic
```java
for (int i = 0; i < n; i++) {
    int sum = 0;
    for (int j = i; j < n; j++) {
        if (arr[j] > k) break;  // can't extend past an invalid element
        sum += arr[j];
        best = Math.max(best, sum);
    }
}
```

### Dry Run
`arr = [3,2,2,3,1,1,1,3]`, `k = 1`

Starting at `i=0`: `arr[0]=3 > 1` → inner loop breaks immediately (sum stays 0).
Starting at `i=1`: `arr[1]=2 > 1` → breaks immediately.
Starting at `i=2`: `arr[2]=2 > 1` → breaks immediately.
Starting at `i=3`: `arr[3]=3 > 1` → breaks immediately.
Starting at `i=4`: `arr[4]=1 <= 1` → sum=1, best=1. `arr[5]=1<=1` → sum=2,best=2. `arr[6]=1<=1` → sum=3,best=3. `arr[7]=3>1` → break.
Starting at `i=5,6,7`: shorter sub-runs of the same segment, can't beat 3.

Result: **3** ✅ (matches expected output)

### Complexity
- **Time:** O(n^2) — for each start index, an O(n) scan extending the end index.
- **Space:** O(1)

### Why It's Not Optimal
This approach re-examines overlapping ranges repeatedly (e.g., the subarray `[1,1]` starting at index 4 is checked as part of exploring starts at both index 4 and index 5). Given the insight that each "chunk" between invalid elements just needs its OWN total summed once, this repeated work is unnecessary.

---

## 3. Approach 2: Optimal (Single-Pass Running Sum)

### Idea
Walk through the array once. Maintain a `currentSum` representing the running total of the current valid chunk. Whenever an element exceeds `k`, the current chunk ends — reset `currentSum` to 0 to start fresh. Track the best `currentSum` ever seen as `maxSum`.

### Code Logic
```java
int currentSum = 0, maxSum = 0;
for (int num : arr) {
    if (num <= k) {
        currentSum += num;
        maxSum = Math.max(maxSum, currentSum);
    } else {
        currentSum = 0;
    }
}
return maxSum;
```

### Dry Run 1
`arr = [3,2,2,3,1,1,1,3]`, `k = 1`

| num | num<=1? | Action | currentSum | maxSum |
|---|---|---|---|---|
| 3 | No | reset | 0 | 0 |
| 2 | No | reset | 0 | 0 |
| 2 | No | reset | 0 | 0 |
| 3 | No | reset | 0 | 0 |
| 1 | Yes | add | 1 | 1 |
| 1 | Yes | add | 2 | 2 |
| 1 | Yes | add | 3 | 3 |
| 3 | No | reset | 0 | 3 |

Result: **3** ✅ (matches expected output)

### Dry Run 2
`arr = [3,2,2,3,1,1,1,3]`, `k = 2`

| num | num<=2? | Action | currentSum | maxSum |
|---|---|---|---|---|
| 3 | No | reset | 0 | 0 |
| 2 | Yes | add | 2 | 2 |
| 2 | Yes | add | 4 | 4 |
| 3 | No | reset | 0 | 4 |
| 1 | Yes | add | 1 | 4 |
| 1 | Yes | add | 2 | 4 |
| 1 | Yes | add | 3 | 4 |
| 3 | No | reset | 0 | 4 |

Result: **4** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — a single pass through the array.
- **Space:** O(1) — two variables.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Running Sum) |
|---|---|---|
| Time Complexity | O(n^2) | O(n) |
| Space Complexity | O(1) | O(1) |
| Scales for n up to 10^5? | No — up to 10^10 operations worst case | Yes — at most 10^5 operations |

---

## 5. Why Non-Negativity of `arr[i]` Matters

This greedy "just sum the whole chunk" approach relies critically on `arr[i] >= 0` (stated in the constraints). If negative values were allowed, the best subarray within a valid chunk might NOT be the entire chunk — it could be beneficial to exclude a very negative element from the middle or either end, turning this into a more general "maximum subarray sum" problem (solved via Kadane's algorithm) applied independently within each chunk. Since the constraints guarantee non-negative values here, the simpler "just accumulate and never reset except at a hard break" logic is sufficient and correct.

---

## 6. Edge Cases to Consider

1. **k >= max(arr)** — every element is valid, so the entire array is one giant chunk; the answer is the sum of the whole array.
2. **k < min(arr)** — no element is ever valid; `currentSum` resets every single iteration, and `maxSum` stays `0`.
3. **All elements equal to k** — the entire array is one valid chunk (no resets at all).
4. **Single-element array** — trivially either `arr[0]` (if `arr[0] <= k`) or `0` (otherwise).
5. **Alternating valid/invalid elements** — e.g., `[1,5,1,5,1]` with `k=1` → each valid element is isolated (chunks of size 1), so `maxSum` ends up being just the largest single valid element (here, `1`).
6. **k = 0 with zeros in the array** — zeros are valid (`0 <= 0`), so chunks of zeros contribute a sum of `0` each, which is fine since `maxSum` starts at `0` anyway.

---

## 7. Related Concepts / Follow-Ups

- **Maximum Subarray Sum (Kadane's Algorithm)**: The classic, more general problem — this problem's optimal solution is essentially "Kadane's algorithm simplified," since the non-negativity of elements removes the need to ever consider dropping elements from within a valid segment.
- **Longest Subarray with Elements <= K**: A close sibling — instead of maximizing the SUM, maximize the LENGTH of a valid segment, solvable with the exact same running/reset pattern (track length instead of sum).
- **Sliding Window Problems**: While this problem doesn't need an explicit sliding window (no need to track a window's start/end pointers), the "reset on invalid element" pattern is conceptually related to window-based techniques for constrained subarray problems.

---

## 8. Key Takeaways

- Elements exceeding `k` act as hard delimiters, splitting the array into independent valid chunks — recognizing this reframes the problem from "search all subarrays" to "sum each chunk and take the max."
- Because array values are non-negative, the best subarray within any valid chunk is simply the entire chunk — no need for a more general max-subarray algorithm like Kadane's.
- The provided single-pass solution (accumulate while valid, reset on invalid) is both correct and optimal here, achieving O(n) time with O(1) space.
- Always check constraints like "non-negative values" carefully — they often justify simpler algorithms than the fully general version of a problem would otherwise require.
