# Notes: Split Array - Largest Sum

## 1. Problem Recap

Split array `a` into `k` contiguous, non-empty subarrays such that the **largest sum among the subarrays is as small as possible**. Return that minimized largest sum.

```
a = [1, 2, 3, 4, 5], k = 3
Split [1,2,3] | [4] | [5]: sums = 6, 4, 5 -> largest = 6
This is the minimum possible largest sum across all valid 3-way splits.
```

### This Problem Is a Triplet with Two Others You've Already Solved
This exact structure appears under three different names:

| This Problem | Ship Packages | Book Allocation |
|---|---|---|
| array elements | packages | books |
| element values | weights | pages |
| k (number of subarrays) | days | students |
| largest subarray sum | ship capacity | max pages per student |

All three are "minimize the maximum" binary-search-on-the-answer problems, using the identical greedy feasibility check pattern: pack as much as fits into the current group, start a new group when it would overflow.

### The Key Insight — Monotonicity
As the candidate maximum sum **increases**, fewer subarrays (groups) are needed to keep every group's sum under that limit. This monotonic relationship is what enables binary search on the answer.

---

## 2. Approach 1: Brute Force (Linear Scan Over Candidate Sums)

### Idea
Try every candidate "largest sum" starting from the largest single element (a group must be able to hold that element alone) up to the sum of the entire array (a single group holds everything). For each candidate, greedily simulate grouping and count how many groups are needed.

### Code Logic
```java
int maxElement = max(a);
int totalSum = sum(a);

for (int candidate = maxElement; candidate <= totalSum; candidate++) {
    if (isValid(a, k, candidate)) return candidate;
}
```

### The Feasibility Check (`isValid`)
```java
int subarrays = 1, currentSum = 0;
for (int num : a) {
    if (currentSum + num <= maxSum) {
        currentSum += num;
    } else {
        subarrays++;
        currentSum = num;
    }
}
return subarrays <= k;
```

Greedily pile elements into the current subarray as long as the running sum doesn't exceed `maxSum`. The moment adding the next element would overflow, start a new subarray. If we ever need more than `k` subarrays, this candidate sum is infeasible.

### Dry Run
`a = [1, 2, 3, 4, 5]`, `k = 2`, checking `candidate = 9`

| num | currentSum + num | <=9? | Action | subarrays |
|---|---|---|---|---|
| 1 | 0+1=1 | Yes | currentSum=1 | 1 |
| 2 | 1+2=3 | Yes | currentSum=3 | 1 |
| 3 | 3+3=6 | Yes | currentSum=6 | 1 |
| 4 | 6+4=10 | No | new subarray, currentSum=4 | 2 |
| 5 | 4+5=9 | Yes | currentSum=9 | 2 |

`subarrays = 2 <= k = 2` → feasible at candidate 9.

(Checking candidates 5 through 8 would all require 3 or more subarrays.)

Result: **9** ✅

### Complexity
- **Time:** O(sum(a) × n) — up to `sum(a)` candidate sums, each with an O(n) simulation.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search the candidate sum range `[max(a), sum(a)]`. For each candidate `mid`, run the same greedy `isValid` check:

- If feasible, `mid` is a valid candidate — record it as the best answer so far (`ans = mid`), and try an even smaller candidate: `high = mid - 1`.
- If not feasible, `mid` is too small — need a larger candidate: `low = mid + 1`.

### Code Logic
```java
int low = max(a), high = sum(a);
int ans = high;

while (low <= high) {
    int mid = low + (high - low) / 2;
    if (isValid(a, k, mid)) {
        ans = mid;
        high = mid - 1;
    } else {
        low = mid + 1;
    }
}
return ans;
```

### Dry Run 1
`a = [1, 2, 3, 4, 5]`, `k = 3`

Search range: `low = 5` (max), `high = 15` (sum)

| Step | low | high | mid | isValid? | Action |
|---|---|---|---|---|---|
| 1 | 5 | 15 | 10 | 1+2+3+4=10,+5=15>10→s2,c5. s=2<=3. Yes | ans=10, high=9 |
| 2 | 5 | 9 | 7 | 1+2+3=6,+4=10>7→s2,c4,+5=9>4→s3,c5. s=3<=3. Yes | ans=7, high=6 |
| 3 | 5 | 6 | 5 | 1+2=3,+3=6,+4=10>6→s2,c4,+5=9>4→s3,c5. s=3<=3. Yes | ans=5, high=4 |
| 4 | 5 | 4 | — | — | low>high, **loop ends -> return ans=5**? |

Wait — let's re-check: at mid=5: 1+2=3,+3=6<=5? 6>5, so actually re-simulate: currentSum=0; num=1:0+1=1<=5,c=1; num=2:1+2=3<=5,c=3; num=3:3+3=6>5→s2,c=3; num=4:3+4=7>3... wait c was reset to 3 (the num itself)? Let's redo: when overflow, currentSum=num (the element itself), not cumulative. So after num=3 overflow: subarrays=2, currentSum=3. num=4: 3+4=7>5→s3,currentSum=4. num=5:4+5=9>4→s4,currentSum=5. Total subarrays=4>3. So isValid(5)=false, not true as I wrote. Let me redo dry run 1 correctly.

| Step | low | high | mid | isValid? | Action |
|---|---|---|---|---|---|
| 1 | 5 | 15 | 10 | subarrays=2<=3 → Yes | ans=10, high=9 |
| 2 | 5 | 9 | 7 | subarrays=3<=3 → Yes | ans=7, high=6 |
| 3 | 5 | 6 | 5 | subarrays=4<=3? No | low=6 |
| 4 | 6 | 6 | 6 | 1+2+3=6,+4=10>6→s2,c4,+5=9>4→s3,c5. subarrays=3<=3 → Yes | ans=6, high=5 |
| — | 6 | 5 | — | — | low>high, **loop ends -> return ans=6** |

Result: **6** ✅ (matches expected output — corrected dry run)

### Dry Run 2
`a = [3, 5, 1]`, `k = 3`

Search range: `low = 5` (max), `high = 9` (sum)

| Step | low | high | mid | isValid? | Action |
|---|---|---|---|---|---|
| 1 | 5 | 9 | 7 | 3<=7,+5=8>3→s2,c5,+1=6>5→s3,c1. subarrays=3<=3. Yes | ans=7, high=6 |
| 2 | 5 | 6 | 5 | 3<=5,+5=8>3→s2,c5,+1=6>5→s3,c1. subarrays=3<=3. Yes | ans=5, high=4 |
| — | 5 | 4 | — | — | low>high, **loop ends -> return ans=5** |

Result: **5** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`a = [1, 2, 3, 4, 5]`, `k = 2`

Search range: `low = 5` (max), `high = 15` (sum)

| Step | low | high | mid | isValid? | Action |
|---|---|---|---|---|---|
| 1 | 5 | 15 | 10 | subarrays=2<=2 → Yes (see brute-force dry run logic) | ans=10, high=9 |
| 2 | 5 | 9 | 7 | 1+2+3=6,+4=10>6→s2,c4,+5=9>4→s3,c5. subarrays=3<=2? No | low=8 |
| 3 | 8 | 9 | 8 | 1+2+3=6,+4=10>6→s2,c4,+5=9>4→s3,c5. subarrays=3<=2? No | low=9 |
| 4 | 9 | 9 | 9 | 1+2+3=6,+4=10>6→s2,c4,+5=9<=4? 4+5=9<=9 → c=9. subarrays=2<=2. Yes | ans=9, high=8 |
| — | 9 | 8 | — | — | low>high, **loop ends -> return ans=9** |

**Result: 9** ✅

So for the quiz options `9, 7, 10, 8`, the correct answer is **9**.

### Complexity
- **Time:** O(n log(sum(a)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(sum(a) × n) | O(n log(sum(a))) |
| Space Complexity | O(1) | O(1) |
| Scales for n up to 10^4, a[i] up to 10^4? | No - sum could be up to 10^8, far too slow | Yes - roughly n * log(10^8) ≈ 10^4 * 27 ≈ 2.7*10^5 |

---

## 5. Edge Cases to Consider

1. **k = 1** — the entire array is one subarray, so the answer is simply `sum(a)`.
2. **k = n** — every element is its own subarray, so the answer is `max(a)` (the single largest element determines the minimum viable "largest sum").
3. **Single-element array** — e.g., `a = [7], k = 1` → answer is `7`.
4. **All elements equal** — e.g., `a = [4,4,4,4], k = 2` → answer is `8` (2 elements per group).
5. **k close to n but not equal** — e.g., `a = [1,2,3,4,5], k = 4` → likely forces most groups to be single elements except one pair, testing the greedy grouping logic under tight constraints.
6. **Large sum values** — with `n` up to `10^4` and elements up to `10^4`, `sum(a)` could reach `10^8`, which still fits comfortably within `int` range, but is good to keep in mind when reasoning about the search space size.

---

## 6. Related Concepts / Follow-Ups

- **Capacity to Ship Packages Within D Days**: Structurally identical — recognizing this equivalence means solving one is equivalent to solving both.
- **Book Allocation Problem**: Also structurally identical — same "minimize the maximum, binary search + greedy grouping" pattern, just with books/pages/students instead of array elements/sums/k.
- **Koko Eating Bananas / Find Smallest Divisor**: Related "minimize X" binary-search-on-the-answer problems, though their feasibility checks are arithmetic (ceiling division sums) rather than greedy grouping — still the same overarching pattern.
- **Aggressive Cows**: The "maximize the minimum" mirror-image pattern, useful to contrast against this "minimize the maximum" family.

---

## 7. Key Takeaways

- Split Array Largest Sum, Capacity to Ship Packages Within D Days, and Book Allocation Problem are the **same problem** wearing three different costumes — recognizing this pattern is one of the most valuable skills for tackling binary-search-on-the-answer problems efficiently.
- The search range `[max(a), sum(a)]` reflects the two natural extremes: a group must hold the largest single element, and never needs to exceed the total sum.
- The greedy "pack as much as fits, then start a new group" feasibility check is optimal for a fixed maximum sum, making the O(n) check both correct and efficient.
- Always double-check greedy simulation dry runs carefully step by step — it's easy to make small arithmetic slips (as shown in the corrected Dry Run 1 above) when tracing through `currentSum` resets after each overflow.
