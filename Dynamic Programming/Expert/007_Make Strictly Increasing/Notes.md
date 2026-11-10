# Notes: Make Strictly Increasing

## 1. Problem Recap

Change as few elements as possible so that the array becomes strictly increasing. Each change costs **one** operation, whatever the new value is, and values may go negative.

```
arr = [1, 2, 3, 6, 5, 4]
Keep 1, 2, 3 and change 6 -> 4, 5 stays, 4 -> 6   result [1, 2, 3, 4, 5, 6], 2 operations
```

Because the size of a change does not matter, this is not about distances. It is about **how many elements can stay untouched**:

```
answer = n - (largest set of elements that can stay)
```

---

## 2. Which Elements Can Stay? The Shift Trick

Suppose elements at indices `i < j` both stay. Between them there are `j - i - 1` other elements, and they must take integer values that are strictly between `arr[i]` and `arr[j]` and strictly increasing. That is possible exactly when there are enough integers in the gap:

```
arr[j] - arr[i] >= j - i
<=>  arr[j] - j >= arr[i] - i
```

Define **`b[i] = arr[i] - i`**. Then two elements can both stay iff `b[i] <= b[j]`. So the set of untouched elements must be a **non-decreasing subsequence of `b`**.

Is that enough? Yes:
- Between two consecutive kept elements the gap is big enough (shown above): fill it with `arr[i] + 1, arr[i] + 2, ...`.
- Before the first kept element, assign smaller and smaller values (`arr[first] - 1`, `arr[first] - 2`, ...). Negatives are allowed.
- After the last kept element, assign larger and larger values.

So the maximum number of untouched elements is the **length of the longest non-decreasing subsequence (LNDS) of `b`**.

```
arr = [1, 2, 3, 6, 5, 4]
b   = [1, 1, 1, 3, 1, -1]      (b[i] = arr[i] - i)
LNDS = 4  (for example 1, 1, 1, 3  or  1, 1, 1, 1)
answer = 6 - 4 = 2
```

### Strict Versus Non-Decreasing
"Strictly increasing `arr`" becomes "**non-decreasing** `b`", so equal values in `b` are allowed to chain. Mixing this up is the classic mistake: a strictly-increasing LIS on `b` of `[1, 1, 1, 3, 1, -1]` has length only 2 (`1, 3`), which would give 4 instead of the correct 2.

---

## 3. Approach 1: Brute Force / Standard DP (O(n^2))

### Idea
Compute `b`, then the textbook LNDS DP: `dp[i]` = longest non-decreasing subsequence of `b` ending at `i`.

### Code Logic
```java
for (int i = 0; i < n; i++) b[i] = arr[i] - i;

for (int i = 0; i < n; i++) {
    dp[i] = 1;
    for (int j = 0; j < i; j++)
        if (b[j] <= b[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
    longest = Math.max(longest, dp[i]);
}
return n - longest;
```

### Dry Run
`arr = [1, 2, 3, 6, 5, 4]`, so `b = [1, 1, 1, 3, 1, -1]`.

| i | b[i] | Earlier j with b[j] <= b[i] | dp[i] |
|---|---|---|---|
| 0 | 1 | none | 1 |
| 1 | 1 | j=0 (dp 1) | 2 |
| 2 | 1 | j=0, j=1 (best dp 2) | 3 |
| 3 | 3 | j=0, 1, 2 (best dp 3) | 4 |
| 4 | 1 | j=0, 1, 2 (best dp 3) | 4 |
| 5 | -1 | none | 1 |

Longest = 4, so the answer is `6 - 4 =` **2** ✅

`arr = [1, 1, 1, 1]`: `b = [1, 0, -1, -2]`, strictly decreasing, so every `dp[i] = 1` and the answer is `4 - 1 =` **3** ✅

### Complexity
- **Time:** O(n^2): about 5 * 10^9 comparisons at `n = 10^5`, far too slow.
- **Space:** O(n).

---

## 4. Approach 2: Optimal (tails Array + Binary Search)

### Idea
Compute the LNDS in O(n log n) with the classic "patience sorting" array. `tails[k]` is the **smallest possible last value** of a non-decreasing subsequence of length `k + 1` among the elements processed so far. This array is always sorted, so a binary search finds where each new value belongs.

For a new value `val`:
- Find `left` = index of the **first element greater than `val`**.
- Set `tails[left] = val`. If `left == len`, the longest subsequence just grew (`len++`).

The comparison `tails[mid] <= val` sends the search to the right on ties, so an equal value extends a subsequence instead of replacing it. That is exactly what makes it **non-decreasing**.

### Code Logic
```java
int[] tails = new int[n];
int len = 0;

for (int i = 0; i < n; i++) {
    int val = arr[i] - i;

    int left = 0, right = len;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (tails[mid] <= val) left = mid + 1;
        else right = mid;
    }

    tails[left] = val;
    if (left == len) len++;
}
return n - len;
```

### Dry Run 1: Example 1
`arr = [1, 2, 3, 6, 5, 4]`, `b = [1, 1, 1, 3, 1, -1]`

| i | val | tails before (first `len` entries) | first element > val is at | tails after | len |
|---|---|---|---|---|---|
| 0 | 1 | `[]` | 0 (empty) | `[1]` | 1 |
| 1 | 1 | `[1]` | 1 (1 <= 1, move right) | `[1, 1]` | 2 |
| 2 | 1 | `[1, 1]` | 2 | `[1, 1, 1]` | 3 |
| 3 | 3 | `[1, 1, 1]` | 3 | `[1, 1, 1, 3]` | 4 |
| 4 | 1 | `[1, 1, 1, 3]` | 3 (the 3 is > 1) | `[1, 1, 1, 1]` | 4 |
| 5 | -1 | `[1, 1, 1, 1]` | 0 | `[-1, 1, 1, 1]` | 4 |

`len = 4`, so the answer is `6 - 4 =` **2** ✅

Notice what `tails` means: after step 4 it is `[1, 1, 1, 1]`, saying "a non-decreasing subsequence of length 4 can end in value 1". That is valid (`1, 1, 1, 1`). The array is a table of best endings, not the actual subsequence.

### Dry Run 2: Example 2
`arr = [1, 1, 1, 1]`, `b = [1, 0, -1, -2]`

| i | val | first element > val is at | tails after | len |
|---|---|---|---|---|
| 0 | 1 | 0 (empty) | `[1]` | 1 |
| 1 | 0 | 0 (1 > 0) | `[0]` | 1 |
| 2 | -1 | 0 | `[-1]` | 1 |
| 3 | -2 | 0 | `[-2]` | 1 |

`len = 1`, so the answer is `4 - 1 =` **3** ✅ (matches `[-2, -1, 0, 1]` from the problem statement: only one element stays)

### Complexity
- **Time:** O(n log n): `n` iterations, each with a binary search over at most `n` entries. About 1.7 million steps at `n = 10^5`.
- **Space:** O(n) for `tails`.

---

## 5. Comparing the Approaches

| Aspect | Brute Force DP | Optimal |
|---|---|---|
| Time | O(n^2) | O(n log n) |
| Space | O(n) | O(n) |
| n = 10^5 | About 5 * 10^9 comparisons | About 1.7 * 10^6 steps |

---

## 6. Implementation Notes on the Provided Code

- **`val = arr[i] - i` is computed on the fly**, so no separate `b` array is needed.
- **Binary search over `[0, len)` only:** the unused tail of the array is never inspected, so no sentinel values are needed.
- **`tails[left] = val` handles both cases** (extend and replace) with one line; the `if (left == len) len++` finishes the extension.
- **Overflow:** `arr[i] <= 10^9` and `i < 10^5`, so `val` lies roughly in `[-10^5, 10^9]`, safely inside `int`.
- **Midpoint:** `left + (right - left) / 2` avoids the classic `(left + right) / 2` overflow habit.
- **Correctness check:** I compared this method with the O(n^2) DP and with an exact "choose the final value of each position" DP (which does not use the shift trick at all) on 400 random small arrays, and all three agreed.

---

## 7. Edge Cases to Consider

1. **Already strictly increasing** (for example `[1, 2, 3]`): `b = [1, 1, 1]` is constant, so LNDS = n and the answer is 0.
2. **All elements equal** (Example 2): `b` is strictly decreasing, LNDS = 1, answer `n - 1`.
3. **Strictly decreasing array:** `b` decreases even faster, so LNDS = 1 and the answer is `n - 1`.
4. **Single element:** LNDS = 1, answer 0.
5. **Gaps that are too small** (for example `[1, 2, 2]` or `[5, 6, 6]`): the second equal element cannot stay next to its twin, so one of them must change.
6. **Large values (up to 10^9) with small differences:** no overflow, because only `arr[i] - i` is used.
7. **Negative results are fine:** the problem allows them, so elements before the first kept one can always be lowered.

---

## 8. Related Concepts / Follow-Ups

- **Longest Increasing Subsequence** (LeetCode 300): the strict version of the same `tails` technique (use `<` in the search, or `bisect_left`).
- **Minimum Operations to Make the Array Non-decreasing / Longest Non-decreasing Subsequence:** the exact subroutine used here.
- **Make Array Strictly Increasing** (LeetCode 1187): a different problem where replacement values must come from a second array.
- **Minimum Number of Removals to Make Mountain Array:** another "n minus the longest valid subsequence" problem.
- **The `arr[i] - i` shift:** a standard way to turn "strictly increasing by at least the index gap" into a plain ordering condition, also used in problems about increasing arithmetic progressions.

---

## 9. Key Takeaways

- When any change costs one operation regardless of size, minimize changes by **maximizing the elements you keep**.
- Elements `i < j` can both stay iff `arr[j] - arr[i] >= j - i`, which becomes `b[i] <= b[j]` after the shift `b[i] = arr[i] - i`.
- Strictly increasing in `arr` corresponds to **non-decreasing** in `b`, so use the upper-bound binary search (`tails[mid] <= val`).
- The `tails` array gives the length of the longest non-decreasing subsequence in O(n log n); the answer is `n - len`.
