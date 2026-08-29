# Notes: Aggressive Cows

## 1. Problem Recap

Given stall positions and `k` cows, place all `k` cows in stalls such that the **minimum distance between any two adjacent cows is as large as possible**. Find that maximum possible minimum distance.

```
nums = [0, 3, 4, 7, 10, 9] sorted -> [0, 3, 4, 7, 9, 10], k = 4
Placing cows at [0, 3, 7, 10]: gaps = 3, 4, 3 -> minimum gap = 3
This is the best (largest) achievable minimum gap.
```

### The Key Insight — "Maximize the Minimum"
This is a **different flavor** of binary search on the answer compared to problems like Koko Eating Bananas or Ship Capacity. Those problems **minimize a maximum** (find the smallest speed/capacity that still works). This problem **maximizes a minimum** (find the largest spacing that's still achievable).

The monotonic relationship still holds, just flipped:
- As the candidate distance **increases**, it becomes **harder** to fit all `k` cows (each cow "takes up more room"), so fewer cows can be placed.
- As the candidate distance **decreases**, it becomes **easier** to fit more cows.

This means feasibility ("can we place k cows with at least this much distance apart?") flips from `true` to `false` exactly once as distance increases — the mirror image of the "minimize the maximum" pattern.

---

## 2. Approach 1: Brute Force (Linear Scan Over Distances, Descending)

### Idea
Sort the stalls first. Then try candidate distances starting from the **largest possible** (the full span `max - min`) and decreasing down to `1`. The first (largest) distance for which we can successfully place all `k` cows is the answer.

### Code Logic
```java
Arrays.sort(nums);
int maxDistance = nums[n-1] - nums[0];

for (int distance = maxDistance; distance >= 1; distance--) {
    if (canPlace(nums, k, distance)) return distance;
}
```

### The Greedy Feasibility Check (`canPlace`)
```java
int count = 1, lastPosition = nums[0];  // always place the first cow at the first stall
for (int i = 1; i < nums.length; i++) {
    if (nums[i] - lastPosition >= distance) {
        count++;
        lastPosition = nums[i];
        if (count >= k) return true;
    }
}
return false;
```

This greedily places the first cow at the very first (smallest) stall, then scans forward, placing the next cow at the **first available stall** that's at least `distance` away from the last placed cow. This greedy strategy is provably optimal: always taking the earliest valid stall leaves the most room for future cows, which never does worse than any other valid placement strategy.

### Dry Run
`nums = [10, 1, 2, 7, 5]` → sorted: `[1, 2, 5, 7, 10]`, `k = 3`

Checking `distance = 4` (after checking 9, 8, 7, 6, 5 all fail — omitted for brevity):

| i | nums[i] | nums[i] - lastPosition | >= 4? | Action | count |
|---|---|---|---|---|---|
| — | (init: lastPosition=1) | — | — | — | 1 |
| 1 | 2 | 2-1=1 | No | skip | 1 |
| 2 | 5 | 5-1=4 | Yes | place, lastPosition=5 | 2 |
| 3 | 7 | 7-5=2 | No | skip | 2 |
| 4 | 10 | 10-5=5 | Yes | place, lastPosition=10 | 3 -> **count>=k, return true** |

Feasible at distance 4. (Distance 5 would fail — checked below in the optimal dry run.)

Result: **4** ✅

### Complexity
- **Time:** O(max(nums) × n) — up to `max(nums) - min(nums)` candidate distances, each with an O(n) greedy check. Plus O(n log n) for the initial sort.
- **Space:** O(1) extra

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search the candidate distance range `[0, max-min]`. For each candidate `mid`, run the same greedy `canPlace` feasibility check:

- If feasible, `mid` is achievable — record it as the best answer so far (`ans = mid`), and try an even **larger** distance: `low = mid + 1` (since we're maximizing).
- If not feasible, `mid` is too large — try a **smaller** distance: `high = mid - 1`.

### Code Logic
```java
Arrays.sort(nums);
int low = 0, high = nums[n-1] - nums[0];
int ans = 0;

while (low <= high) {
    int mid = low + (high - low) / 2;
    if (canPlace(nums, k, mid)) {
        ans = mid;
        low = mid + 1;
    } else {
        high = mid - 1;
    }
}
return ans;
```

### Dry Run 1
`nums = [0, 3, 4, 7, 10, 9]` → sorted: `[0, 3, 4, 7, 9, 10]`, `k = 4`

Search range: `[0, 10]` (span = 10 - 0)

| Step | low | high | mid | canPlace? | Action |
|---|---|---|---|---|---|
| 1 | 0 | 10 | 5 | Place at 0; next>=5→7(2); next>=12→none. count=2<4. No | high=4 |
| 2 | 0 | 4 | 2 | Place at 0; next>=2→3(2); next>=5→7(3); next>=9→9(4)→true | ans=2, low=3 |
| 3 | 3 | 4 | 3 | Place at 0; next>=3→3(2); next>=6→7(3); next>=10→10(4)→true | ans=3, low=4 |
| 4 | 4 | 4 | 4 | Place at 0; next>=4→4(2); next>=8→9(3); next>=13→none. count=3<4. No | high=3 |
| — | 4 | 3 | — | — | low>high, **loop ends -> return ans=3** |

Result: **3** ✅ (matches expected output)

### Dry Run 2
`nums = [4, 2, 1, 3, 6]` → sorted: `[1, 2, 3, 4, 6]`, `k = 2`

Search range: `[0, 5]` (span = 6 - 1)

| Step | low | high | mid | canPlace? | Action |
|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | Place at 1; next>=3→3(2)→true | ans=2, low=3 |
| 2 | 3 | 5 | 4 | Place at 1; next>=5→6(2)→true | ans=4, low=5 |
| 3 | 5 | 5 | 5 | Place at 1; next>=6→6(2)→true | ans=5, low=6 |
| — | 6 | 5 | — | — | low>high, **loop ends -> return ans=5** |

Result: **5** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [10, 1, 2, 7, 5]` → sorted: `[1, 2, 5, 7, 10]`, `k = 3`

Search range: `[0, 9]` (span = 10 - 1)

| Step | low | high | mid | canPlace? | Action |
|---|---|---|---|---|---|
| 1 | 0 | 9 | 4 | (see brute-force dry run above) → true | ans=4, low=5 |
| 2 | 5 | 9 | 7 | Place at 1; next>=8→10(2); next... only 1 more stall left, count=2<3. No | high=6 |
| 3 | 5 | 6 | 5 | Place at 1; next>=6→7(2); next>=12→none. count=2<3. No | high=4 |
| — | 5 | 4 | — | — | low>high, **loop ends -> return ans=4** |

**Result: 4** ✅

So for the quiz options `4, 2, 5, 3`, the correct answer is **4**.

### Complexity
- **Time:** O(n log n) for sorting, plus O(n log(max(nums))) for the binary search itself.
- **Space:** O(1) extra (excluding the sort)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(max(nums) × n) | O(n log n + n log(max(nums))) |
| Space Complexity | O(1) extra | O(1) extra |
| Search direction | Descending from max distance | Binary search, maximizing feasible answer |
| Scales for nums[i] up to 10^9, n up to 10^5? | No - far too slow | Yes |

For `max(nums) = 10^9`, brute force could need up to a billion iterations in the worst case — completely infeasible. Binary search needs roughly `log2(10^9) ≈ 30` iterations, each with an O(n) check, making it dramatically faster.

---

## 5. Why Sorting Is Required First

The greedy placement strategy (`canPlace`) only works correctly if stalls are processed in increasing order of position — otherwise "the first stall at least `distance` away from the last placed cow" doesn't make sense. Sorting is a mandatory O(n log n) preprocessing step that both the brute force and optimal solutions rely on.

---

## 6. Why the Greedy `canPlace` Check Is Correct

Placing the first cow at the very first (leftmost) stall, then always advancing to the *next available* stall that satisfies the minimum distance, is a well-known greedy strategy that is provably optimal for this type of "maximize minimum spacing" problem. Intuitively: placing a cow as early as possible (without violating the distance constraint) always leaves at least as much room for subsequent cows as any other valid choice would — so this greedy approach never does worse than any alternative valid placement.

---

## 7. Edge Cases to Consider

1. **k = 2 (minimum allowed)** — reduces to simply finding the maximum possible distance between two stalls, which is always `max(nums) - min(nums)` (place cows at the two extreme ends).
2. **k = n (as many cows as stalls)** — every stall must get exactly one cow, so the answer is the smallest gap between any two *consecutive* stalls (in sorted order) — placement has no flexibility.
3. **All stalls evenly spaced** — e.g., `nums = [0,2,4,6,8], k=3` → answer should evenly divide the span, likely `4` (cows at 0,4,8).
4. **Duplicate-adjacent stalls very close together** — verifies the greedy check correctly skips stalls that are too close to the last placed cow.
5. **Large span with sparse stalls** — verifies the binary search range `[0, max-min]` and iteration count scale correctly for very large position values (up to 10^9).

---

## 8. Related Concepts / Follow-Ups

- **Koko Eating Bananas / Ship Capacity / Smallest Divisor**: All "minimize the maximum" binary-search-on-the-answer problems — the mirror image of this "maximize the minimum" pattern. Comparing the two patterns side by side is a great way to solidify understanding of binary search on the answer generally.
- **Book Allocation Problem**: Another classic "minimize the maximum" problem structurally similar to Ship Capacity, often taught alongside Aggressive Cows as companion problems in the binary-search-on-the-answer family.
- **Magnetic Force Between Two Balls** (LeetCode 1552): Essentially the *exact same problem* as Aggressive Cows, just renamed (balls in baskets instead of cows in stalls).

---

## 9. Key Takeaways

- Aggressive Cows is the canonical **"maximize the minimum"** binary-search-on-the-answer problem, contrasting with the more common "minimize the maximum" pattern seen in problems like Koko Eating Bananas.
- The direction of pointer movement flips accordingly: on a successful feasibility check, we search for a LARGER answer (`low = mid + 1`) rather than a smaller one, since we're trying to maximize.
- Sorting the input is a mandatory first step, since the greedy placement check depends on processing stalls in increasing order.
- The greedy "place as early as possible" placement strategy is what makes the O(n) feasibility check both correct and efficient.
- Recognizing this problem's equivalence to "Magnetic Force Between Two Balls" and its mirror relationship to "minimize the maximum" problems rounds out a strong mental model of the entire binary-search-on-the-answer family.
