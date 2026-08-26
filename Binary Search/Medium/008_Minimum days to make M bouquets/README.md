# Minimum Days to Make M Bouquets

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given `n` roses and an array `nums` where `nums[i]` denotes the day on which the `i`th rose blooms. Only **adjacent** bloomed roses can be picked to make a bouquet, and exactly `k` adjacent bloomed roses are required for a single bouquet.

Find the **minimum number of days** required to make at least `m` bouquets, each containing `k` roses. Return `-1` if it's not possible.

## Examples

### Example 1
```
Input:  n = 8, nums = [7,7,7,7,13,11,12,7], m = 2, k = 3
Output: 12
Explanation: By day 12, the first 4 flowers and the last 3 flowers have bloomed.
We can make 2 bouquets: one from the first 3, another from the last 3.
```

### Example 2
```
Input:  n = 5, nums = [1,10,3,10,2], m = 3, k = 2
Output: -1
Explanation: 3 bouquets of 2 flowers each need at least 6 flowers, but only 5 are available.
```

### Your Turn
```
Input:  n = 5, nums = [1,10,3,10,2], m = 3, k = 1
Output: 3
```

## Constraints

- `1 <= n <= 10^5`
- `1 <= nums[i] <= 10^9`
- `1 <= m <= 10^6`
- `1 <= k <= n`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(max(nums) * n) linear scan solution |
| `Optimal.java` | O(n log(max(nums))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over days) | O(max(nums) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(max(nums))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Another **"binary search on the answer"** problem: as the number of days increases, the number of bouquets that CAN be made only increases (or stays the same) — never decreases. This monotonic relationship lets us binary search over candidate day counts and use a helper function to check "can we make at least `m` bouquets by this day?"
