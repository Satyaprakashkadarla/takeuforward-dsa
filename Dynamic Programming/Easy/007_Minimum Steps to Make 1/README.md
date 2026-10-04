# Minimum Steps to Reduce N to 1 (Divide by 2/3 or Decrement)

**Difficulty:** Easy/Medium
**Tags:** Dynamic Programming, Math

## Problem Statement

Given a number `n`, count the minimum steps to reduce it to `1` using:
- If `n` is divisible by `2`, reduce `n` to `n/2`.
- If `n` is divisible by `3`, reduce `n` to `n/3`.
- Decrement `n` by `1` (always allowed).

## Examples

### Example 1
```
Input:  n = 10
Output: 3
Explanation: 10 -1-> 9 /3-> 3 /3-> 1
```

### Example 2
```
Input:  n = 1
Output: 0
```

## Constraints

- `1 <= n <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(3^n)-ish plain recursion trying all 3 operations |
| `Optimal.java` | O(n) bottom-up DP (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | Exponential | O(n) (recursion stack) |
| Optimal (Bottom-Up DP) | O(n) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

`dp[i]` = minimum steps to reduce `i` to 1. The "decrement by 1" move is always available, giving a guaranteed baseline `dp[i-1] + 1`. On top of that, whenever `i` is divisible by 2 and/or 3, the corresponding division move offers a potentially much faster shortcut (`dp[i/2]+1` or `dp[i/3]+1`). Since all three candidate predecessors (`i-1`, `i/2`, `i/3`) are strictly smaller than `i`, a simple left-to-right bottom-up fill correctly computes every `dp[i]` using already-finalized earlier values.
