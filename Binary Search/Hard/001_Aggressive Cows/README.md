# Aggressive Cows

**Difficulty:** Hard
**Tags:** Array, Binary Search, Greedy
**Companies:** (add as applicable)

## Problem Statement

Given an array `nums` of size `n` denoting the positions of stalls, and an integer `k` denoting the number of aggressive cows, assign stalls to `k` cows such that the **minimum distance between any two cows is maximized**. Find that maximum possible minimum distance.

## Examples

### Example 1
```
Input:  n = 6, k = 4, nums = [0,3,4,7,10,9]
Output: 3
Explanation: Placing 4 cows at [0,3,7,10] gives gaps of 3, 4, 3 — minimum gap 3, which is the largest achievable minimum.
```

### Example 2
```
Input:  n = 5, k = 2, nums = [4,2,1,3,6]
Output: 5
Explanation: Placing 2 cows at [1,6] gives a gap of 5, the maximum possible.
```

### Your Turn
```
Input:  n = 5, k = 3, nums = [10,1,2,7,5]
Output: 4
Explanation: Sorted positions [1,2,5,7,10]. Placing cows at [1,5,10] gives gaps of 4 and 5 — minimum gap 4, the largest achievable.
```

## Constraints

- `2 <= n <= 10^5`
- `2 <= k <= n`
- `0 <= nums[i] <= 10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(max(nums) * n) linear scan solution |
| `Optimal.java` | O(n log(max(nums))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over distances) | O(max(nums) * n) | O(1) (excluding sort) |
| Optimal (Binary Search on the Answer) | O(n log(max(nums))) | O(1) (excluding sort) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **"maximize the minimum"** variant of binary search on the answer — as the candidate minimum distance increases, it becomes *harder* to fit `k` cows (fewer cows can be placed), so feasibility flips from `true` to `false` monotonically as distance increases. We binary search for the largest distance that still allows placing all `k` cows using a greedy placement check.
