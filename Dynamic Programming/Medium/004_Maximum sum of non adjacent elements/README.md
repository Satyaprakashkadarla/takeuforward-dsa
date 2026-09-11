# Maximum Sum of Non-Adjacent Elements

**Difficulty:** Medium
**Tags:** Dynamic Programming
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `nums` of size `n`, return the maximum sum possible using elements of `nums` such that no two chosen elements are adjacent in `nums`.

## Examples

### Example 1
```
Input:  nums = [1,2,4]
Output: 5
Explanation: Pick 1 and 4 (not adjacent) -> sum = 5.
```

### Example 2
```
Input:  nums = [2,1,4,9]
Output: 11
Explanation: Pick 2 and 9 -> sum = 11.
```

### Your Turn
```
Input:  nums = [1,7,16,8]
Output: 17
Explanation: Pick 1 and 16 -> sum = 17.
```

## Constraints

- `n == nums.length`
- `1 <= n <= 10^5`
- `0 <= nums[i] <= 1000`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n) plain recursion solution |
| `Optimal.java` | O(n) iterative DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^n) | O(n) (recursion stack) |
| Optimal (Iterative DP, O(1) space) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is the classic **"House Robber"** pattern: for each element, either **take it** (add its value to the best result 2 positions back, since the previous element must be skipped) or **skip it** (keep the best result 1 position back). `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`. Same "look back 1 or 2" DP shape as Climbing Stairs and Frog Jump, just with `max` instead of `min`/`sum`.
