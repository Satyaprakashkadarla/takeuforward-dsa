# Koko Eating Bananas

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

A monkey (Koko) is given `n` piles of bananas, where the `i`th pile has `nums[i]` bananas. An integer `h` represents the total time in hours available to eat all the bananas.

Each hour, Koko chooses a non-empty pile and eats `k` bananas from it. If the pile has fewer than `k` bananas, Koko eats all of them and does not eat any more that hour (doesn't move to another pile).

Determine the **minimum** eating speed `k` (bananas per hour) such that Koko can finish all piles within `h` hours.

## Examples

### Example 1
```
Input:  n = 4, nums = [7,15,6,3], h = 8
Output: 5
Explanation: At 5 bananas/hr, Koko takes 2, 3, 2, 1 hours per pile = 8 hours total.
```

### Example 2
```
Input:  n = 5, nums = [25,12,8,14,19], h = 5
Output: 25
Explanation: At 25 bananas/hr, Koko takes 1 hour per pile = 5 hours total.
```

### Your Turn
```
Input:  n = 4, nums = [3,7,6,11], h = 8
Output: 4
```

## Constraints

- `1 <= n <= 10^4`
- `n <= h <= 10^9`
- `1 <= nums[i] <= 10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(max(nums) * n) linear scan solution |
| `Optimal.java` | O(n log(max(nums))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over speeds) | O(max(nums) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(max(nums))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Another classic **"binary search on the answer"** problem: as Koko's eating speed `k` increases, the total hours needed to finish all piles strictly *decreases* (monotonic relationship). This lets us binary search over candidate speeds (`1` to `max(nums)`) and use a helper that computes "hours needed at speed k" to decide which half of the search space to keep.
