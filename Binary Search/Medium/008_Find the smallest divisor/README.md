# Find the Smallest Divisor

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an array of integers `nums` and an integer `limit` (threshold value), find the **smallest positive integer divisor** such that, upon dividing every element of the array by this divisor (taking the **ceiling** of each result), the sum of all division results is `<= limit`.

## Examples

### Example 1
```
Input:  nums = [1,2,3,4,5], limit = 8
Output: 3
Explanation: Dividing by 3 gives [1,1,1,2,2], sum = 7 <= 8. This is the smallest such divisor.
```

### Example 2
```
Input:  nums = [8,4,2,3], limit = 10
Output: 2
Explanation: Dividing by 2 gives [4,2,1,2], sum = 9 <= 10. This is the smallest such divisor.
```

### Your Turn
```
Input:  nums = [8,4,2,3], limit = 4
Output: 8
Explanation: Dividing by 8 gives [1,1,1,1], sum = 4 <= 4. No smaller divisor achieves sum <= 4.
```

## Constraints

- `1 <= nums.length <= 5 * 10^4`
- `1 <= nums[i] <= 10^6`
- `nums.length <= limit <= 10^6`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(max(nums) * n) linear scan solution |
| `Optimal.java` | O(n log(max(nums))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over divisors) | O(max(nums) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(max(nums))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Yet another **"binary search on the answer"** problem: as the divisor increases, the sum of ceiling-divided results strictly *decreases* (or stays the same) — a classic monotonic relationship. Binary search over candidate divisors (`1` to `max(nums)`) using this property to home in on the smallest valid one.
