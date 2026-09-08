# Climbing Stairs

**Difficulty:** Medium
**Tags:** Dynamic Programming, Math, Recursion
**Companies:** (add as applicable)

## Problem Statement

Given an integer `n`, there is a staircase with `n` steps, starting from the 0th step. Determine the number of unique ways to reach the `n`th step, where each move can be either 1 or 2 steps at a time.

## Examples

### Example 1
```
Input:  n = 2
Output: 2
Explanation: (1+1), (2)
```

### Example 2
```
Input:  n = 3
Output: 3
Explanation: (1+1+1), (2+1), (1+2)
```

### Your Turn
```
Input:  n = 1
Output: 1
Explanation: Only one way: a single step of size 1.
```

## Constraints

- `1 <= n <= 45`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^n) plain recursion solution |
| `Optimal.java` | O(n) iterative dynamic programming (space-optimized) solution |
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

This is the **Fibonacci sequence in disguise**: the number of ways to reach step `n` equals the number of ways to reach step `n-1` plus the number of ways to reach step `n-2` (since your very last move was either a 1-step or a 2-step). Recognizing this recurrence turns an exponential brute-force recursion into a simple O(n) iterative computation — no need for even an array, since only the last two values are ever needed at once.
