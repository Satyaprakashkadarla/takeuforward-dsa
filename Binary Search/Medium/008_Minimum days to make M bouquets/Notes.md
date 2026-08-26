# Notes: Minimum Days to Make M Bouquets

## 1. Problem Recap

We have `n` roses, and rose `i` blooms on day `nums[i]`. We want to make `m` bouquets, each requiring exactly `k` **adjacent** (consecutive index) bloomed roses. Find the minimum number of days needed to have enough bloomed roses arranged correctly to form `m` bouquets, or `-1` if it's never possible.

### Two Distinct Ways This Can Be Impossible
1. **Not enough total roses**: If `m * k > n`, we simply don't have enough roses to ever form `m` bouquets of `k` each, regardless of how long we wait.
2. **Roses exist but can't form enough adjacent groups**: Even with enough total roses, the *positions* of bloom days might never align to create `m` separate groups of `k` consecutive bloomed roses (this is checked implicitly by the feasibility function, even after waiting until every rose has bloomed).

### The Key Insight — Monotonicity
As the number of days we wait **increases**, more roses bloom, so the number of bouquets we're able to form only **increases or stays the same** — it never decreases. This is the monotonic property that makes **binary search on the answer** applicable.

---

## 2. Approach 1: Brute Force (Linear Scan Over Days)

### Idea
Try every candidate day count from `1` up to `max(nums)` (waiting past the last bloom day doesn't unlock anything new). For each day, check feasibility using a helper function. The first day that works is the answer, since feasibility only improves as days increase.

### Code Logic
```java
if ((long) m * k > n) return -1;

int maxDay = max(nums);
for (int day = 1; day <= maxDay; day++) {
    if (canMakeBouquets(nums, k, m, day)) return day;
}
return -1;
```

### The Feasibility Check (`canMakeBouquets`)
```java
int bouquets = 0, consecutive = 0;
for (int bloomDay : nums) {
    if (bloomDay <= days) {
        consecutive++;
        if (consecutive == k) {
            bouquets++;
            consecutive = 0;
        }
    } else {
        consecutive = 0;   // break in adjacency - bloomed roses must be consecutive
    }
}
return bouquets >= m;
```

This walks through the roses in array order, tracking a run of consecutive bloomed roses. The moment a run reaches exactly `k`, that's one complete bouquet — the counter resets and we look for the next run. If we ever hit an unbloomed rose, the current run breaks entirely (bouquets require *adjacent* bloomed roses, so a gap ruins the current attempt).

### Dry Run
`nums = [7, 7, 7, 7, 13, 11, 12, 7]`, `k = 3`, `m = 2`

Checking `day = 12`:

| index | bloomDay | <=12? | consecutive | bouquets |
|---|---|---|---|---|
| 0 | 7 | Yes | 1 | 0 |
| 1 | 7 | Yes | 2 | 0 |
| 2 | 7 | Yes | 3 -> bouquet! | 1 (consecutive reset to 0) |
| 3 | 7 | Yes | 1 | 1 |
| 4 | 13 | No | 0 (reset) | 1 |
| 5 | 11 | Yes | 1 | 1 |
| 6 | 12 | Yes | 2 | 1 |
| 7 | 7 | Yes | 3 -> bouquet! | 2 |

`bouquets = 2 >= m = 2` → feasible at day 12.

(Checking days 1–11 would all fail — either not enough bloomed roses yet, or they don't align into two full groups of 3 consecutive.)

Result: **12** ✅

### Complexity
- **Time:** O(max(nums) × n) — up to `max(nums)` candidate days, each with an O(n) feasibility check.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search the candidate day range `[1, max(nums)]`. For each candidate `mid`, use the same `canMakeBouquets` feasibility check:

- If feasible at `mid`, record it as our current best answer, then search for an even earlier day: `right = mid - 1`.
- If not feasible, we need to wait longer: `left = mid + 1`.

### Early Feasibility Check
```java
if (m * k > n) return -1;
```
This upfront check avoids running the entire binary search when it's mathematically impossible from the start (not enough total roses exist, period).

### Code Logic
```java
int left = 1, right = max(nums);
int result = -1;

while (left <= right) {
    int mid = left + (right - left) / 2;

    if (canMakeBouquets(nums, k, m, mid)) {
        result = mid;
        right = mid - 1;
    } else {
        left = mid + 1;
    }
}

return result;
```

Note: this version uses `left <= right` (inclusive) combined with tracking `result` separately, rather than the `left < right` style with `right = mid` seen in some other binary-search-on-the-answer problems. Both styles are valid; this one explicitly stores the best-known answer as it narrows the search.

### Dry Run 1
`nums = [7, 7, 7, 7, 13, 11, 12, 7]`, `k = 3`, `m = 2`

Feasibility check: `m*k = 6 <= n = 8` ✓. Search range: `[1, 13]`.

| Step | left | right | mid | Feasible? | Action |
|---|---|---|---|---|---|
| 1 | 1 | 13 | 7 | Check day 7: consecutive runs of 7's... [7,7,7,7]→bouquet at idx2(3), 1 leftover; 13>7 breaks; 11>7 breaks; 12>7 breaks; 7≤7 →1. Total bouquets=1 < 2. No | left = 8 |
| 2 | 8 | 13 | 10 | Day 10: [7,7,7,7]→bouquet(1), leftover 1; 13>10 break; 11>10 break;12>10 break;7≤10→1. bouquets=1 <2. No | left = 11 |
| 3 | 11 | 13 | 12 | Day 12: as shown in brute force dry run → bouquets=2 ≥2. Yes | result=12, right=11 |
| 4 | 11 | 11 | 11 | Day 11: [7,7,7,7]→bouquet(1); 13>11 break; 11≤11→1; 12>11 break; 7≤11→1. bouquets=1 <2. No | left = 12 |
| — | 12 | 11 | — | left > right | **loop ends -> return result = 12** |

Result: **12** ✅ (matches expected output)

### Dry Run 2
`nums = [1, 10, 3, 10, 2]`, `k = 2`, `m = 3`

Feasibility check: `m*k = 6 > n = 5` → **immediately return -1**, no binary search needed.

Result: **-1** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [1, 10, 3, 10, 2]`, `k = 1`, `m = 3`

Feasibility check: `m*k = 3 <= n = 5` ✓. Search range: `[1, 10]`.

With `k = 1`, every individual bloomed rose is its own complete "bouquet" (a run of length 1 immediately equals `k`), so `canMakeBouquets` effectively just counts how many roses have bloomed by day `days`, and checks if that count is `>= m`.

| Step | left | right | mid | Bloomed roses by day mid (bloomDay <= mid) | bouquets | >=3? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 10 | 5 | 1,3,2 bloom by day 5 (10,10 don't) -> 3 roses | 3 | Yes | result=5, right=4 |
| 2 | 1 | 4 | 2 | 1,2 bloom by day 2 -> 2 roses | 2 | No | left = 3 |
| 3 | 3 | 4 | 3 | 1,3,2 bloom by day 3 -> 3 roses | 3 | Yes | result=3, right=2 |
| — | 3 | 2 | — | — | — | left > right | **loop ends -> return result = 3** |

**Result: 3** ✅

So for the quiz options `10, 1, 2, 3`, the correct answer is **3**.

### Complexity
- **Time:** O(n log(max(nums)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(max(nums) × n) | O(n log(max(nums))) |
| Space Complexity | O(1) | O(1) |
| Scales for nums[i] up to 10^9? | No - far too slow | Yes |
| Early impossibility check? | Implicitly via loop bound | Explicit early return before searching |

---

## 5. Edge Cases to Consider

1. **Not enough total roses** — e.g., `m*k > n` → immediately return `-1` (see Dry Run 2).
2. **k = 1** — every bloomed rose is trivially its own bouquet; the problem reduces to "find the minimum day by which at least m roses have bloomed" (see Dry Run 3).
3. **m = 1** — just need to find the earliest day a single group of k consecutive roses has all bloomed.
4. **All roses bloom on the same day** — e.g., `nums = [5,5,5,5,5,5], k=2, m=3` → answer is `5` (all bloom simultaneously, forming exactly 3 pairs).
5. **Roses bloom on wildly different days, breaking adjacency** — e.g., `nums = [1,100,1,100,1], k=2` → even though roses at indices 0,2,4 bloom early, they're not *adjacent* to each other (indices 1,3 bloom very late), so no bouquet of 2 can form until day 100.
6. **k = n and m = 1** — need every single rose to have bloomed, so the answer is `max(nums)`.

---

## 6. Related Concepts / Follow-Ups

- **Koko Eating Bananas / Find Square Root / Find Nth Root**: All are binary-search-on-the-answer problems sharing the same core pattern — monotonic feasibility as the candidate answer changes.
- **Capacity to Ship Packages Within D Days**: Another close relative, using the same "binary search on the answer, verify with an O(n) feasibility scan" template.
- **Adjacency requirement** distinguishes this problem from simpler "count how many satisfy a condition" problems — the feasibility check here must track *consecutive* runs, not just totals, which is what makes the helper function a bit more involved than in some other binary-search-on-the-answer problems.

---

## 7. Key Takeaways

- This problem combines **binary search on the answer** with an **adjacency-aware feasibility check** — bouquets require *consecutive* bloomed roses, not just any k bloomed roses total.
- An early impossibility check (`m*k > n`) short-circuits the entire search when there simply aren't enough roses, regardless of timing.
- The feasibility function resets its "consecutive bloomed" counter whenever it hits an unbloomed rose, correctly modeling the adjacency requirement.
- When `k = 1`, the problem degenerates into a simpler "count total bloomed roses by day X" check, since every bloomed rose trivially satisfies a run of length 1.
