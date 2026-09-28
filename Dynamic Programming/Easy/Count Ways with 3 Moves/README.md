# Count Ways to Reach the Nth Stair (1, 2 or 3 Steps)

**Difficulty:** Easy/Medium
**Tags:** Dynamic Programming, Recursion, Math
**Companies:** (add as applicable)

## Problem Statement

A child is running up a staircase with `n` steps and can hop **1, 2, or 3 steps** at a time. Return the count of how many possible ways the child can run up the stairs.

## Examples

### Example 1
```
Input:  n = 3
Output: 4
Explanation:
1+1+1, 1+2, 2+1, 3
```

### Example 2
```
Input:  n = 4
Output: 7
Explanation:
1+1+1+1, 1+2+1, 2+1+1, 1+1+2, 2+2, 3+1, 1+3
```

### Example 3
```
Input:  n = 1
Output: 1
```

## Constraints

- `1 <= n <= 30`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(3^n) plain recursion solution |
| `Optimal.java` | O(n) iterative DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(3^n) | O(n) (recursion stack) |
| Optimal (Iterative DP, O(1) space) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is **Climbing Stairs generalized from 2 allowed step sizes to 3**. Your last hop was either 1, 2, or 3 steps, so `ways(n) = ways(n-1) + ways(n-2) + ways(n-3)` — the **Tribonacci** recurrence. Since each value depends only on the previous three, three rolling variables replace the full DP array.
