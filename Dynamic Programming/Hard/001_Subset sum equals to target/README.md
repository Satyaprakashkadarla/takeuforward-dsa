# Subset Sum Equals to Target

**Difficulty:** Hard
**Tags:** Dynamic Programming, DP on Subsequences
**Companies:** (add as applicable)

## Problem Statement

Given an array `arr` of `n` integers and an integer `target`, determine if there exists a subset of `arr` whose sum equals `target`.

## Examples

### Example 1
```
Input:  arr = [1,2,7,3], target = 6
Output: True
Explanation: Subset {1,2,3} sums to 6.
```

### Example 2
```
Input:  arr = [2,3,5], target = 6
Output: False
```

### Your Turn
```
Input:  arr = [7,54,4,12,15,5], target = 9
Output: True
Explanation: Subset {4,5} sums to 9.
```

## Constraints

- `1 <= n <= 100`
- `1 <= arr[i] <= 100`
- `0 <= target <= 5*10^3`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n) plain recursion (include/exclude) solution |
| `Optimal.java` | O(n * target) 1D DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Include/Exclude Recursion) | O(2^n) | O(n) (recursion stack) |
| Optimal (1D DP, Space-Optimized) | O(n * target) | O(target) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is the foundational **"DP on Subsequences"** problem: `dp[sum]` tracks whether `sum` is achievable using some subset of elements processed so far. For each element, traverse the sum array **backwards** (from `target` down to the element's value) so each element is only ever used once per subset — this backward traversal is the classic **0/1 knapsack** space-optimization trick, avoiding the need for a full 2D `dp[i][sum]` table.
