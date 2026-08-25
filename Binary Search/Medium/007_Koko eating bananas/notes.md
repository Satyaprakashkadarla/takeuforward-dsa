# Notes: Koko Eating Bananas

## 1. Problem Recap

Koko eats bananas at a fixed speed `k` bananas/hour, one pile at a time. For a pile of size `p`, it takes Koko `ceil(p / k)` hours to finish that pile (since if there are leftover bananas fewer than `k`, she still spends a full hour on them). We need the **minimum** speed `k` such that the total hours across all piles is `<= h`.

```
nums = [7, 15, 6, 3], h = 8, speed k = 5
  pile 7:  ceil(7/5)  = 2 hours
  pile 15: ceil(15/5) = 3 hours
  pile 6:  ceil(6/5)  = 2 hours
  pile 3:  ceil(3/5)  = 1 hour
  total = 2+3+2+1 = 8 hours <= h  ✓
```

### The Key Insight — Monotonicity
As the eating speed `k` **increases**, the total hours needed to finish all piles **decreases** (or stays the same — it never increases). This monotonic relationship is exactly what makes **binary search on the answer** applicable: we can binary search over candidate speeds and use "is this speed fast enough?" as our deciding condition.

---

## 2. Approach 1: Brute Force (Linear Scan Over Speeds)

### Idea
Try every candidate speed starting from `1` upward. For each speed, compute the total hours needed. The first speed for which total hours `<= h` is the answer, since increasing speed only ever helps (or has no effect), so scanning upward guarantees we find the *minimum* valid speed first.

### Code Logic
```java
int maxPile = max(nums);
for (int k = 1; k <= maxPile; k++) {
    long hours = 0;
    for (int num : nums) {
        hours += (num + k - 1) / k;  // ceil(num / k)
    }
    if (hours <= h) return k;
}
```

### Why the Search Range Is `[1, max(nums)]`
- Speed `1` is the slowest possible (must be at least 1 banana/hour).
- Speed `max(nums)` guarantees every pile finishes in exactly 1 hour (since no pile is larger than this), so it's never useful to consider speeds beyond this — going faster doesn't reduce the "1 hour per pile" floor.

### Dry Run
`nums = [7, 15, 6, 3]`, `h = 8`

| k | ceil(7/k) | ceil(15/k) | ceil(6/k) | ceil(3/k) | total hours | <= 8? |
|---|---|---|---|---|---|---|
| 1 | 7 | 15 | 6 | 3 | 31 | No |
| 2 | 4 | 8 | 3 | 2 | 17 | No |
| 3 | 3 | 5 | 2 | 1 | 11 | No |
| 4 | 2 | 4 | 2 | 1 | 9 | No |
| 5 | 2 | 3 | 2 | 1 | 8 | **Yes -> return 5** |

Result: **5** ✅

### Complexity
- **Time:** O(max(nums) × n) — for each of up to `max(nums)` candidate speeds, we do an O(n) pass over the piles.
- **Space:** O(1)

### Why It's Not Optimal
Given `nums[i]` can be up to `10^9`, trying every single speed from `1` to `10^9` one at a time would be far too slow — this is exactly the kind of scale where binary search on the answer becomes essential rather than just a nice-to-have optimization.

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search over the candidate speed range `[1, max(nums)]`. For a candidate `mid`, compute the total hours needed at that speed:

- If `hours <= h`, `mid` is a valid (fast enough) speed — but there might be an even slower speed that's still valid, so search left: `right = mid`.
- If `hours > h`, `mid` is too slow — search right (faster speeds): `left = mid + 1`.

When `left == right`, that's the minimum valid speed.

### Code Logic
```java
int left = 1, right = max(nums);

while (left < right) {
    int mid = left + (right - left) / 2;

    long hours = 0;
    for (int num : nums) {
        hours += (num + mid - 1) / mid;
        if (hours > h) break;   // early exit optimization
    }

    if (hours <= h) {
        right = mid;
    } else {
        left = mid + 1;
    }
}

return left;
```

### About the Early-Exit Optimization
Inside the hours-computation loop, `if (hours > h) break;` stops summing the moment we already know the running total has exceeded `h` — there's no need to keep adding more piles once we already know this candidate speed is too slow. This doesn't change the worst-case complexity (a "just barely valid" or "just barely invalid" speed might still require summing all `n` piles), but it noticeably helps in the average case, especially for very slow candidate speeds early in the search where hours accumulate quickly.

### Dry Run 1
`nums = [7, 15, 6, 3]`, `h = 8`

Search range: `[1, 15]` (max pile = 15)

| Step | left | right | mid | hours computation | hours | <=8? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 15 | 8 | ceil(7/8)+ceil(15/8)+ceil(6/8)+ceil(3/8) = 1+2+1+1 | 5 | Yes | right = 8 |
| 2 | 1 | 8 | 4 | ceil(7/4)+ceil(15/4)+ceil(6/4)+ceil(3/4) = 2+4+2+1 | 9 | No | left = 5 |
| 3 | 5 | 8 | 6 | ceil(7/6)+ceil(15/6)+ceil(6/6)+ceil(3/6) = 2+3+1+1 | 7 | Yes | right = 6 |
| 4 | 5 | 6 | 5 | ceil(7/5)+ceil(15/5)+ceil(6/5)+ceil(3/5) = 2+3+2+1 | 8 | Yes | right = 5 |
| — | 5 | 5 | — | — | — | — | **left == right -> return 5** |

Result: **5** ✅ (matches expected output)

### Dry Run 2
`nums = [25, 12, 8, 14, 19]`, `h = 5`

Search range: `[1, 25]` (max pile = 25)

| Step | left | right | mid | hours | <=5? | Action |
|---|---|---|---|---|---|---|
| 1 | 1 | 25 | 13 | ceil(25/13)+ceil(12/13)+ceil(8/13)+ceil(14/13)+ceil(19/13) = 2+1+1+2+2 = 8 | No | left = 14 |
| 2 | 14 | 25 | 19 | ceil(25/19)+ceil(12/19)+ceil(8/19)+ceil(14/19)+ceil(19/19) = 2+1+1+1+1 = 6 | No | left = 20 |
| 3 | 20 | 25 | 22 | ceil(25/22)+1+1+1+1 = 2+1+1+1+1 = 6 | No | left = 23 |
| 4 | 23 | 25 | 24 | ceil(25/24)+1+1+1+1 = 2+1+1+1+1 = 6 | No | left = 25 |
| — | 25 | 25 | — | — | — | **left == right -> return 25** |

Result: **25** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [3, 7, 6, 11]`, `h = 8`

Search range: `[1, 11]` (max pile = 11)

| Step | left | right | mid | hours computation | hours | <=8? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 11 | 6 | ceil(3/6)+ceil(7/6)+ceil(6/6)+ceil(11/6) = 1+2+1+2 | 6 | Yes | right = 6 |
| 2 | 1 | 6 | 3 | ceil(3/3)+ceil(7/3)+ceil(6/3)+ceil(11/3) = 1+3+2+4 | 10 | No | left = 4 |
| 3 | 4 | 6 | 5 | ceil(3/5)+ceil(7/5)+ceil(6/5)+ceil(11/5) = 1+2+2+3 | 8 | Yes | right = 5 |
| 4 | 4 | 5 | 4 | ceil(3/4)+ceil(7/4)+ceil(6/4)+ceil(11/4) = 1+2+2+3 | 8 | Yes | right = 4 |
| — | 4 | 4 | — | — | — | — | **left == right -> return 4** |

**Result: 4** ✅

So for the quiz options `3, 5, 2, 4`, the correct answer is **4**.

### Complexity
- **Time:** O(n log(max(nums))) — binary search does O(log(max(nums))) iterations, each requiring an O(n) pass to compute total hours.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(max(nums) × n) | O(n log(max(nums))) |
| Space Complexity | O(1) | O(1) |
| Scales for nums[i] up to 10^9? | No - far too slow | Yes - very fast |

For `max(nums) = 10^9` and `n = 10^4`: brute force could need up to `10^9 × 10^4 = 10^13` operations in the absolute worst case — completely infeasible. Binary search needs roughly `log2(10^9) ≈ 30` iterations, each doing `10^4` work, totaling about `3 × 10^5` operations — extremely fast by comparison.

---

## 5. Why `(num + mid - 1) / mid` Computes `ceil(num / mid)`

This is a standard integer-division trick for ceiling division without using floating point. Adding `mid - 1` before dividing "pushes" any remainder up over the next integer boundary:

- If `num` divides evenly by `mid` (no remainder), adding `mid - 1` isn't enough to push it into the next multiple, so the result is unchanged: `ceil(num/mid) = num/mid`.
- If there's a remainder, adding `mid - 1` guarantees the division rounds up to the next integer.

Example: `ceil(7/5)` should be `2`. Using the trick: `(7 + 5 - 1) / 5 = 11 / 5 = 2` (integer division truncates) ✅.

---

## 6. Edge Cases to Consider

1. **Single pile** — e.g., `nums = [10], h = 3` → need speed such that `ceil(10/k) <= 3`, so `k = 4` (`ceil(10/4) = 3`).
2. **h exactly equals n (minimum possible h)** — this forces Koko to eat each pile in exactly 1 hour, so the answer is `max(nums)` (the largest pile determines the minimum viable speed).
3. **h is very large (h >> n)** — allows a very slow speed; the answer approaches `1` (the slowest possible), assuming `h` is large enough to accommodate eating everything at speed 1.
4. **All piles equal** — e.g., `nums = [5,5,5,5], h = 4` → the answer is exactly `5` (must finish each pile in 1 hour with h=n).
5. **Large pile sizes near 10^9** — ensures the `long` type is used for the running `hours` total, since summing many large ceil-divisions could otherwise overflow a plain `int`.

---

## 7. Related Concepts / Follow-Ups

- **Find Square Root of a Number / Find Nth Root**: Both are earlier examples of "binary search on the answer" — recognizing this problem as belonging to the same family (monotonic predicate over a range of candidate answers) is the key conceptual leap.
- **Capacity to Ship Packages Within D Days**: A very similar problem — find the minimum ship capacity such that all packages can be shipped within D days, using the exact same binary-search-on-the-answer structure.
- **Minimum Number of Days to Make M Bouquets**: Another classic binary-search-on-the-answer problem with a similar "find the minimum/maximum value satisfying a monotonic condition" structure.
- **Aggressive Cows**: A "maximize the minimum distance" variant of the same technique, showing the pattern also applies to maximization problems (not just minimization).

---

## 8. Key Takeaways

- Koko Eating Bananas is a canonical **"binary search on the answer"** problem: the relationship between candidate speed and total hours needed is monotonic (strictly non-increasing as speed increases), which is exactly the property binary search needs.
- The search range `[1, max(nums)]` is chosen because speeds below 1 aren't valid, and speeds above `max(nums)` provide no additional benefit.
- `(num + mid - 1) / mid` is the standard trick for computing ceiling division using only integer arithmetic.
- The early-exit `if (hours > h) break;` inside the hours-computation loop is a nice practical optimization, though it doesn't change the theoretical worst-case complexity.
- Recognizing this problem's structural similarity to "Find Square Root," "Find Nth Root," and other binary-search-on-the-answer problems makes the pattern much easier to spot in future problems.
