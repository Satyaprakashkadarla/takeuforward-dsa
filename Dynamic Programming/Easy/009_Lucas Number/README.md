
# Lucas Number

**Difficulty:** Easy
**Tags:** Dynamic Programming, Math, Recursion

## Problem Statement

A Lucas Number is defined by the recurrence `Ln = Ln-1 + Ln-2` for `n > 1`, with `L0 = 2` and `L1 = 1`. Given `n`, find the `n`th Lucas number, modulo `10^9 + 7`.

## Examples

### Example 1
```
Input:  n = 5
Output: 11
Explanation: L3=4, L4=7, L5=L3+L4=11
```

### Example 2
```
Input:  n = 7
Output: 29
Explanation: L5=11, L6=18, L7=L5+L6=29
```

## Constraints

- `1 <= n <= 10^6`

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

Lucas numbers follow the **exact same recurrence shape as Fibonacci** (`f(n) = f(n-1) + f(n-2)`), just with different starting values (`L0=2, L1=1` instead of `F0=0, F1=1`). This means the same O(1)-space, two-rolling-variable iterative technique used for Fibonacci/Climbing Stairs applies directly here, with the modulo applied at each addition to keep values bounded given `n` up to `10^6`.
