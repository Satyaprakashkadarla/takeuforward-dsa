# Notes: Minimize Max Distance to Gas Station

## 1. Problem Recap

Given sorted gas station positions and `k` new stations to add anywhere (including non-integer positions), minimize the **maximum distance between any two adjacent stations** after placing the new ones.

```
arr = [3, 6, 12, 19, 33, 44, 67, 72, 89, 95], k = 2
Gaps: 3, 6, 7, 14, 11, 23, 5, 17, 6
```

### The Twist: Binary Search on REAL Numbers
Every prior binary-search-on-the-answer problem searched over **integers**. This problem is different: the answer can be **any real number** (stations can be placed at non-integer positions). This changes how the binary search loop must be structured — there's no natural integer convergence point (`low == high`), so instead we run the loop for a **fixed number of iterations**, which is a standard technique for real-valued binary search.

---

## 2. The Core Feasibility Formula

For a single gap of length `gap`, splitting it into `p` equal pieces (by adding `p - 1` new stations inside it) means each piece has length `gap / p`. We want the smallest `p` such that `gap / p <= maxDist`, i.e.:

```
p = ceil(gap / maxDist)
stations needed for this gap = p - 1
```

Summing this across all gaps gives the total number of new stations required to guarantee no gap exceeds `maxDist`. If this total exceeds `k`, `maxDist` is infeasible.

### Why This Is Monotonic
As `maxDist` increases, `ceil(gap / maxDist)` only decreases (or stays the same) for every gap, so the total required stations only decreases (or stays the same). This monotonic relationship is exactly what makes binary search on the answer applicable — just now over real numbers instead of integers.

---

## 3. Approach 1: Brute Force (Fixed-Step Linear Scan)

### Idea
Since we can't literally check "every real number," a naive brute force approach scans candidate distances in small fixed decrements (e.g., `0.01`) starting from the largest existing gap downward, checking feasibility at each step.

### Code Logic
```java
double maxGap = max gap in arr;
double step = 0.01;

for (double candidate = maxGap; candidate > 0; candidate -= step) {
    if (!canPlace(arr, k, candidate)) {
        return candidate + step; // last feasible value found
    }
}
```

### Why This Is a Poor Approach in Practice
- **Precision vs. speed tradeoff is brutal**: to achieve the required `1e-6` precision, the step size would need to be `0.000001` or smaller, requiring potentially **millions to billions** of iterations depending on the gap sizes involved (recall gaps can be up to `10^9`). This is completely impractical.
- It's included here purely to illustrate *why* binary search (with its exponential precision gain per iteration) is essential for real-valued search problems — a linear scan simply cannot compete.

### Dry Run (Conceptual, Coarse Precision)
`arr = [3,6,12,19,33,44,67,72,89,95]`, `k = 2`, using `step = 0.01` (impractically coarse for real problems, but illustrative)

Scanning downward from `maxGap = 23`, feasibility holds down to somewhere around `14.00`, and fails just below that (e.g., at `13.99`), so the brute force would return approximately `14.00` — matching the true answer, but only with a coarse approximation and after a large number of iterations.

### Complexity
- **Time:** O(range / step) — impractically large for the required precision (potentially billions of iterations for large gaps and tight precision requirements).
- **Space:** O(1)

---

## 4. Approach 2: Optimal (Binary Search on Real Numbers, Fixed Iterations)

### Idea
Binary search the range `[0, maxGap]`, but since there's no clean integer stopping condition, run the loop for a **fixed number of iterations** (100 in the given solution) instead of a `while (low < high)` style loop.

### Why 100 Iterations Is More Than Enough
Each iteration halves the search interval. Starting from an interval of size up to `10^9` (max possible gap per constraints):

```
Iteration 1:  interval ≈ 10^9 / 2
Iteration 2:  interval ≈ 10^9 / 4
...
Iteration k:  interval ≈ 10^9 / 2^k
```

To shrink an interval of size `10^9` down to `1e-6`, we need `2^k >= 10^9 / 10^-6 = 10^15`, i.e., `k >= log2(10^15) ≈ 50`. Using **100 iterations** gives an enormous safety margin beyond what's strictly necessary — the final interval width after 100 halvings is astronomically smaller than `1e-6`, ensuring the required precision is met (and then some).

### Code Logic
```java
double low = 0.0, high = maxGap;

for (int iteration = 0; iteration < 100; iteration++) {
    double mid = low + (high - low) / 2.0;
    if (canPlace(arr, k, mid)) {
        high = mid;
    } else {
        low = mid;
    }
}

return high;
```

### Dry Run 1 (Conceptual)
`arr = [1,2,...,10]`, `k = 10`

All gaps are `1`. With 10 new stations and 9 gaps, we can afford roughly 1 station per gap (some gaps get 1, matching the 10 stations across 9 gaps — actually with 10 stations and 9 gaps of size 1, we can place enough to halve every gap and have 1 extra). Each gap of size 1, split by 1 station, becomes two pieces of `0.5` each. Binary search converges toward `maxDist ≈ 0.5`.

Result: **≈0.50000** ✅ (matches expected output)

### Dry Run 2 (Conceptual)
`arr = [1,2,...,10]`, `k = 1`

With only 1 new station and 9 gaps of size 1 each, we can only split ONE gap in half (making it `0.5`), but the other 8 gaps remain at size `1`. The MAXIMUM gap overall is still `1` (dominated by the unsplit gaps), so no matter where we place the single new station, the answer can't go below `1`.

Result: **≈1.00000** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case (Verified by Direct Calculation)
`arr = [3, 6, 12, 19, 33, 44, 67, 72, 89, 95]`, `k = 2`

Gaps: `[3, 6, 7, 14, 11, 23, 5, 17, 6]`

Checking `maxDist = 14`:
| gap | ceil(gap/14) | stations needed (p-1) |
|---|---|---|
| 3 | 1 | 0 |
| 6 | 1 | 0 |
| 7 | 1 | 0 |
| 14 | 1 | 0 |
| 11 | 1 | 0 |
| 23 | 2 | 1 |
| 5 | 1 | 0 |
| 17 | 2 | 1 |
| 6 | 1 | 0 |

Total required = `1 + 1 = 2 <= k = 2` → **feasible at 14**.

Checking `maxDist = 13.9` (just below 14):
| gap | ceil(gap/13.9) | stations needed |
|---|---|---|
| 14 | ceil(1.007) = 2 | 1 |
| 23 | ceil(1.654) = 2 | 1 |
| 17 | ceil(1.223) = 2 | 1 |

Total required = `1+1+1 = 3 > k = 2` → **infeasible just below 14**.

This confirms the true minimum feasible `maxDist` is exactly **14.0** — the binary search converges to this value after its 100 iterations.

**Result: 14.00000** ✅

So for the quiz options `12.00000, 15.00000, 13.00000, 14.00000`, the correct answer is **14.00000**.

### Complexity
- **Time:** O(n × 100), which is effectively O(n log(1/precision)) — the fixed 100 plays the same role that `log(range/precision)` would in a variable-iteration-count version.
- **Space:** O(1)

---

## 5. Comparing Both Approaches

| Aspect | Brute Force (Fixed-Step Scan) | Optimal (Binary Search, Fixed Iterations) |
|---|---|---|
| Time Complexity | O(range / step) — impractically large | O(n × 100) — fast and precision-independent of range |
| Space Complexity | O(1) | O(1) |
| Achieves 1e-6 precision practically? | No — would require an infeasible number of steps | Yes — 100 iterations comfortably exceed what's needed |

---

## 6. Why `canPlace` Uses `(int) Math.ceil(gap / maxDist) - 1`

This is the same "how many pieces do I need to keep each piece under a limit" logic seen in problems like Koko Eating Bananas and Ship Capacity, just adapted for continuous (real-valued) distances instead of discrete quantities:

- `gap / maxDist` tells us, in a continuous sense, how many "maxDist-sized" pieces fit into the gap.
- `Math.ceil(...)` rounds this up to the smallest whole number of pieces needed (since a gap can only be split into a whole number of segments).
- Subtracting `1` converts "number of pieces" into "number of NEW stations needed" (since splitting into `p` pieces requires `p - 1` internal dividing points).

---

## 7. Edge Cases to Consider

1. **k = 0** — no new stations can be added; the answer is simply the largest existing gap (the initial `high` value, achieved trivially since `canPlace` with `maxDist = maxGap` requires 0 additional stations).
2. **Very large k** — with enough stations, the max distance can be pushed arbitrarily close to 0 (limited only by the precision requirement, not by any theoretical floor).
3. **All gaps equal** — e.g., `arr = [0,5,10,15], k = 3` → stations distribute evenly, likely splitting gaps as evenly as possible.
4. **Single very large gap dominating all others** — the binary search naturally focuses stations on the largest gap(s) first, since the greedy feasibility check inherently prioritizes wherever splitting has the most impact on the maximum.
5. **k = 1 with many equal gaps** (see Dry Run 2) — the single new station can only reduce the max gap if it's the *unique* largest gap; with ties, the max remains unchanged regardless of placement.

---

## 8. Related Concepts / Follow-Ups

- **Aggressive Cows**: The discrete (integer-position) cousin of this problem — same "maximize/minimize a spacing" concept, but searching over integers with a clean `low == high` stopping condition instead of real numbers with fixed iterations.
- **Binary Search on Real Numbers (General Technique)**: This fixed-iteration-count binary search pattern generalizes to any problem requiring a real-valued answer within some precision tolerance — common in computational geometry and optimization problems.
- **Koko Eating Bananas / Ship Capacity**: Share the same `ceil(x / candidate)` style feasibility check, just applied to distances instead of speeds or capacities.

---

## 9. Key Takeaways

- This is the first "binary search on the answer" problem in real-valued (floating-point) territory rather than integers — the key structural change is using a **fixed iteration count** instead of an integer `low == high` convergence condition.
- 100 iterations is a generous, standard choice that comfortably exceeds the precision needed even for very large search ranges (up to `10^9` here), since each iteration halves the interval exponentially.
- The feasibility formula `ceil(gap / maxDist) - 1` computes exactly how many new stations are needed to ensure no piece of a given gap exceeds `maxDist` — a continuous adaptation of the "ceiling division" trick seen in earlier binary-search-on-the-answer problems.
- Always verify by direct hand calculation (as done in Dry Run 3) when floating-point dry runs get complex — tracing through binary search convergence for real numbers isn't as clean to table out as integer binary search, so a direct feasibility check at the candidate boundary values is often clearer.
