# Minimum Operations To Reach 1

**Difficulty:** Easy
**Tags:** Dynamic Programming, Math, Number Theory

## Problem Statement

Given a number `n`, find the minimum number of operations to reduce it to `1`. In each operation, choose `x` such that `0 < x < n` and `n` is exactly divisible by `x`, then reduce `n` to `n - x`.

## Examples

### Example 1
```
Input:  n = 8
Output: 3
Explanation: 8 -> 4 -> 2 -> 1
```

### Example 2
```
Input:  n = 5
Output: 3
Explanation: 5 -> 4 -> 2 -> 1 (5 is prime, so the only valid first move is subtracting 1)
```

## Constraints

- `1 <= n <= 10^3`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n)-ish plain recursion trying every valid divisor move |
| `Optimal.java` | O(n * sqrt(n)) bottom-up DP (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | Exponential (branches over all divisors at each step) | O(n) (recursion stack) |
| Optimal (Bottom-Up DP) | O(n * sqrt(n)) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

`dp[i]` = minimum operations to reduce `i` down to 1. At each `i`, the always-valid move "subtract 1" gives a baseline of `dp[i-1] + 1`. On top of that, every divisor `x` of `i` (found by trial division up to `sqrt(i)`, which also gives the paired divisor `i/x` for free) offers an alternative move `i -> i - x`, potentially reaching 1 faster. Checking only divisors up to `sqrt(i)` (and their paired co-divisors) keeps the per-number work to O(sqrt(i)) instead of checking every possible divisor individually.
