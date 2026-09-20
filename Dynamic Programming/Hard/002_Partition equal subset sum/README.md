# Partition Equal Subset Sum

**Difficulty:** Hard
**Tags:** Dynamic Programming, DP on Subsequences
**Companies:** (add as applicable)

## Problem Statement

Given an array `arr` of `n` integers, return `true` if the array can be partitioned into two subsets with equal sums, else return `false`.

## Examples

### Example 1
```
Input:  arr = [1,10,21,10]
Output: True
Explanation: Partition into [1,10,10] and [21], both summing to 21.
```

### Example 2
```
Input:  arr = [1,2,3,5]
Output: False
```

### Your Turn
```
Input:  arr = [2,2,1,1]
Output: True
Explanation: Partition into [2,1] and [2,1], both summing to 3.
```

## Constraints

- `1 <= n <= 100`
- `1 <= arr[i] <= 1000`
- `n * sum of elements <= 10^5`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n) plain recursion (include/exclude) solution |
| `Optimal.java` | O(n * sum/2) 1D DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Include/Exclude Recursion) | O(2^n) | O(n) (recursion stack) |
| Optimal (1D DP, Space-Optimized) | O(n * sum/2) | O(sum/2) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **direct application of Subset Sum**: splitting an array into two equal-sum halves is only possible if the total sum is **even**, and even then, it reduces to exactly one question — "can some subset sum to exactly `total/2`?" If yes, that subset and its complement form the two equal halves. No new algorithm is needed — just the Subset Sum solver, called once with `target = sum/2`.
