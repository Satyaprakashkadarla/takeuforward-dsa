# Capacity to Ship Packages Within D Days

**Difficulty:** Medium
**Tags:** Array, Binary Search

## Problem Statement

You are given an array `weights` where `weights[i]` represents the weight of the `i`th package on a conveyor belt. All packages must be shipped **in order** from one port to another within `days` days.

Each day, the ship can carry a **contiguous** sequence of packages, as long as the total weight doesn't exceed its maximum capacity.

Find the **minimum possible capacity** of the ship so that all packages can be shipped within the given number of days.

## Examples

### Example 1
```
Input:  weights = [1,2,3,4,5,6,7,8,9,10], days = 5
Output: 15
Explanation: One valid split: [1+2+3+4+5=15], [6+7=13], [8], [9], [10] — 5 days, max load 15.
```

### Example 2
```
Input:  weights = [3,2,2,4,1,4], days = 3
Output: 6
Explanation: One valid split: [3+2=5], [2+4=6], [1+4=5] — 3 days, max load 6.
```

### Your Turn
```
Input:  weights = [10,50,50,10], days = 2
Output: 60
Explanation: Split [10+50=60], [50+10=60] — 2 days, max load 60. This is the minimum possible.
```

## Constraints

- `1 <= days <= weights.length <= 5 * 10^4`
- `1 <= weights[i] <= 500`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(sum(weights) * n) linear scan solution |
| `Optimal.java` | O(n log(sum(weights))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over capacities) | O(sum(weights) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(sum(weights))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Another **"binary search on the answer"** problem: as ship capacity increases, the number of days needed to ship everything only decreases (or stays the same) — a monotonic relationship. Binary search the candidate capacity range (`max(weights)` to `sum(weights)`) using a greedy day-counting simulation to check feasibility at each candidate.
