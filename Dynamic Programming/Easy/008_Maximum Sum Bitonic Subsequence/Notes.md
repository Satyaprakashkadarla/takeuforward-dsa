# Notes: Maximum Sum Bitonic Subsequence

## 1. Problem Recap

A subsequence is **bitonic** if it strictly increases, then strictly decreases (either half may be empty/length 1, allowing purely increasing or purely decreasing sequences too). Find the maximum possible sum of such a subsequence.

```
arr = [1, 15, 51, 45, 33, 100, 12, 18, 9]
Best: {1, 15, 51, 100, 18, 9} -> sum = 194
                      ^peak (100)
```

### The Core Decomposition
Every bitonic subsequence has a single **peak** element — the point where it switches from increasing to decreasing. If we know, for every index `i`:
- `inc[i]` = the max sum of a strictly increasing subsequence **ending** at `i`
- `dec[i]` = the max sum of a strictly decreasing subsequence **starting** at `i`

...then treating `i` as the peak, the best bitonic sum with that specific peak is `inc[i] + dec[i] - arr[i]` (subtracting `arr[i]` once since it's counted in both halves). The overall answer is the max of this over all `i`.

---

## 2. Approach 1: Brute Force / Standard DP — O(n^2)

### Idea
Compute `inc[]` and `dec[]` using the classic "Longest Increasing Subsequence"-style DP, but tracking **maximum sum** instead of maximum length.

### Code Logic
```java
// inc[i] = max sum strictly increasing subsequence ending at i
for (int i = 0; i < n; i++) {
    inc[i] = arr[i];
    for (int j = 0; j < i; j++) {
        if (arr[j] < arr[i]) inc[i] = Math.max(inc[i], inc[j] + arr[i]);
    }
}

// dec[i] = max sum strictly decreasing subsequence starting at i
for (int i = n-1; i >= 0; i--) {
    dec[i] = arr[i];
    for (int j = i+1; j < n; j++) {
        if (arr[j] < arr[i]) dec[i] = Math.max(dec[i], dec[j] + arr[i]);
    }
}

int ans = 0;
for (int i = 0; i < n; i++) ans = Math.max(ans, inc[i] + dec[i] - arr[i]);
```

### Dry Run
`arr = [1, 15, 51, 45, 33, 100, 12, 18, 9]`

**Computing `inc[]`:**

| i | arr[i] | best predecessor sum | inc[i] |
|---|---|---|---|
| 0 | 1 | — | 1 |
| 1 | 15 | from idx0 (1): 1 | 16 |
| 2 | 51 | from idx1 (16): best | 67 |
| 3 | 45 | from idx1 (16): best (51 not < 45) | 61 |
| 4 | 33 | from idx1 (16): best | 49 |
| 5 | 100 | from idx2 (67): best | 167 |
| 6 | 12 | from idx0 (1): only option | 13 |
| 7 | 18 | from idx1 (16): best (12 gives 13, 16 wins) | 34 |
| 8 | 9 | from idx0 (1): only option | 10 |

`inc = [1, 16, 67, 61, 49, 167, 13, 34, 10]`

**Computing `dec[]`** (scanning right to left, looking for smaller values AFTER each index):

| i | arr[i] | best successor sum | dec[i] |
|---|---|---|---|
| 8 | 9 | — | 9 |
| 7 | 18 | from idx8 (9): 9 | 27 |
| 6 | 12 | from idx8 (9): 9 | 21 |
| 5 | 100 | from idx7 (27): best | 127 |
| 4 | 33 | from idx7 (27): best (idx6=21 gives 54, idx7=27 gives 60, wins) | 60 |
| 3 | 45 | from idx4 (60): best | 105 |
| 2 | 51 | from idx3 (105): best | 156 |
| 1 | 15 | from idx6 (21): best (12<15) | 36 |
| 0 | 1 | none (all later values >= 9 > 1) | 1 |

`dec = [1, 36, 156, 105, 60, 127, 21, 27, 9]`

**Computing `inc[i] + dec[i] - arr[i]` for each i:**

| i | inc[i] | dec[i] | arr[i] | sum |
|---|---|---|---|---|
| 0 | 1 | 1 | 1 | 1 |
| 1 | 16 | 36 | 15 | 37 |
| 2 | 67 | 156 | 51 | 172 |
| 3 | 61 | 105 | 45 | 121 |
| 4 | 49 | 60 | 33 | 76 |
| 5 | 167 | 127 | 100 | **194** |
| 6 | 13 | 21 | 12 | 22 |
| 7 | 34 | 27 | 18 | 43 |
| 8 | 10 | 9 | 9 | 10 |

Max = **194** at `i=5` (peak value 100).

Result: **194** ✅ (matches expected output — bitonic subsequence {1,15,51,100,18,9})

### Complexity
- **Time:** O(n^2) — for each index, an O(n) scan to find the best smaller predecessor/successor.
- **Space:** O(n) — the `inc[]` and `dec[]` arrays.

### Why It's Not Optimal for Large n
With `n` up to `10^5`, O(n^2) is up to `10^10` operations — far too slow. This is exactly why the provided solution upgrades the "find best smaller predecessor" step from an O(n) linear scan to an O(log n) Fenwick Tree query.

---

## 3. Approach 2: Optimal (as Provided) — Fenwick Tree (Max-BIT) + Coordinate Compression

### The Core Upgrade
The slow part of the O(n^2) approach is: *"among all previous elements with a smaller value, what's the best `inc[]` sum?"* This is precisely the kind of query a **Fenwick Tree (Binary Indexed Tree)** configured to track **maximums** can answer in O(log n), instead of an O(n) linear scan.

### Why Coordinate Compression Is Needed
A Fenwick Tree is indexed by position (1 to size), and we want to index it by **value** (so "query everything with value < x" becomes "query positions 1 to rank(x)-1"). But `arr[i]` can be as large as `10^6`, while there are at most `10^5` elements — building a tree of size `10^6` would waste memory and isn't necessary. **Coordinate compression** maps each distinct value to a compact rank (1, 2, 3, ... up to the number of *distinct* values), so the Fenwick Tree only needs to be as large as the number of distinct values actually present.

```java
Arrays.sort(sorted);
int m = 0;
for (int x : sorted) {
    if (m == 0 || sorted[m-1] != x) sorted[m++] = x;  // de-duplicate
}
```

### The Two Passes
**Forward pass (computing `inc[]`):** process left to right. For `arr[i]`, find its compressed rank, then query the Fenwick Tree for the best `inc` sum among all STRICTLY SMALLER values already processed (ranks `1` to `rank-1`). Add `arr[i]` to get `inc[i]`, then update the tree at this rank.

**Backward pass (computing `dec[]`):** symmetric, processing right to left with a fresh Fenwick Tree.

```java
int rank = lowerBound(sorted, m, a[i]) + 1;  // 1-indexed rank
inc[i] = a[i] + bit.query(rank - 1);          // best sum from strictly smaller values
bit.update(rank, inc[i]);                      // record this new best at this rank
```

### Why `bit.query(rank - 1)` (Not `rank`)?
We need values **strictly smaller** than `arr[i]`, not including `arr[i]`'s own rank. Since `rank` is `arr[i]`'s own position, querying up to `rank - 1` correctly excludes any value equal to `arr[i]` itself (enforcing the "strictly increasing/decreasing" requirement) while including everything smaller.

### The Fenwick Tree (Max Variant)
Unlike the more common "sum" Fenwick Tree, this one tracks **running maximums**:
```java
void update(int index, int value) {
    while (index < tree.length) {
        tree[index] = Math.max(tree[index], value);
        index += index & -index;  // move to next responsible node
    }
}
int query(int index) {
    int result = 0;
    while (index > 0) {
        result = Math.max(result, tree[index]);
        index -= index & -index;  // move to parent range
    }
    return result;
}
```
This works because `Math.max` is associative and the Fenwick Tree's structure correctly partitions the "prefix up to index" into O(log n) non-overlapping ranges, regardless of whether you're combining with `+` (sum) or `Math.max`.

### Verifying the Example (via the Same inc[]/dec[] Values)
The Fenwick-Tree-based computation produces the IDENTICAL `inc[]` and `dec[]` arrays as the O(n^2) brute force shown above (since it's computing the exact same quantities, just faster) — so the same dry run table applies: `inc=[1,16,67,61,49,167,13,34,10]`, `dec=[1,36,156,105,60,127,21,27,9]`, and the final answer is **194**, matching Example 2 exactly.

### Complexity
- **Time:** O(n log n) — O(n log n) for sorting during compression, plus O(n log n) for each of the two Fenwick-Tree passes (n updates/queries, each O(log n)).
- **Space:** O(n) — the compressed value array, `inc[]`/`dec[]` arrays, and the Fenwick Tree.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (O(n^2) DP) | Optimal (Fenwick Tree DP) |
|---|---|---|
| Time Complexity | O(n^2) | O(n log n) |
| Space Complexity | O(n) | O(n) |
| Scales for n up to 10^5? | No — up to 10^10 operations | Yes — roughly 10^5 * 17 ≈ 1.7*10^6 operations |

---

## 5. Edge Cases to Consider

1. **Strictly decreasing array** (Example 1) — the "increasing" half of the bitonic sequence is trivially just the first/peak element alone; the whole array can serve as the "decreasing" half. `inc[i]=arr[i]` for all i (no smaller predecessor), and the peak ends up being the very first element.
2. **All elements equal** (Example 3) — since the subsequence must be STRICTLY increasing/decreasing, no two equal elements can both be included; the best achievable is just a single element.
3. **Strictly increasing array** — symmetric to Example 1; the "decreasing" half is trivial, and the whole array forms the increasing half.
4. **Single-element array** — trivially, the answer is that single element's value.
5. **Large value range relative to array size** (`arr[i]` up to 10^6, `n` up to 10^5) — this is exactly why coordinate compression matters; without it, the Fenwick Tree would need to be sized to the maximum VALUE rather than the number of distinct values, wasting memory and time.

---

## 6. Related Concepts / Follow-Ups

- **Longest Increasing Subsequence (LIS)**: The length-based cousin of `inc[]`/`dec[]` here — this problem swaps "maximize length" for "maximize sum," but uses the exact same underlying DP shape and the exact same Fenwick-Tree-with-coordinate-compression optimization technique.
- **Maximum Sum Increasing Subsequence**: The standalone version of just the `inc[]` computation here — a well-known problem in its own right.
- **Fenwick Tree / Binary Indexed Tree**: A fundamental data structure for efficient prefix queries and point updates — while most commonly used for prefix SUMS, it generalizes cleanly to any associative, order-preserving operation like `max` or `min`, as shown here.
- **Coordinate Compression**: A broadly useful technique whenever you need to index a data structure (Fenwick Tree, segment tree, etc.) by VALUE, but the value range is much larger than the number of actual data points.

---

## 7. Key Takeaways

- Every bitonic subsequence has a unique peak; computing `inc[]` (best increasing-ending-here sum) and `dec[]` (best decreasing-starting-here sum) for every index, then trying each as a potential peak, solves the whole problem.
- The O(n^2) version computes `inc[]`/`dec[]` via linear scans; the O(n log n) version replaces those scans with Fenwick Tree queries, exploiting the fact that "best sum among all smaller values" is exactly the kind of prefix-aggregate query a Fenwick Tree answers efficiently.
- Coordinate compression is essential whenever array values could be much larger than the array's length, to keep the Fenwick Tree's size proportional to the number of distinct values rather than the raw value range.
- A Fenwick Tree isn't limited to sums — it works for any associative combining operation, including `max`, as demonstrated by this problem's "Fenwick Tree of maximums" variant.
