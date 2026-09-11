# Frog Jump

**Difficulty:** Medium
**Tags:** Dynamic Programming
**Companies:** (add as applicable)

## Problem Statement

A frog wants to climb a staircase with `n` steps. Given an integer array `heights`, where `heights[i]` is the height of the `i`th step, the frog can jump from step `i` to step `i+1` or step `i+2` (if it exists), at a cost of `abs(heights[i] - heights[j])`. Return the minimum total energy required to go from step `0` to step `n-1`.

## Examples

### Example 1
```
Input:  heights = [2,1,3,5,4]
Output: 2
Explanation: 0→2 (cost 1), 2→4 (cost 1). Total = 2.
```

### Example 2
```
Input:  heights = [7,5,1,2,6]
Output: 9
Explanation: 0→1 (cost 2), 1→3 (cost 3), 3→4 (cost 4). Total = 9.
```

### Your Turn
```
Input:  heights = [3,10,3,11,3]
Output: 0
Explanation: 0→2 (cost |3-3|=0), 2→4 (cost |3-3|=0). Total = 0.
```

## Constraints

- `1 <= n <= 10^4`
- `0 <= heights[i] <= 10^4`

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

`dp[i]` = minimum energy to reach step `i`. Since the frog can only arrive at step `i` from step `i-1` or step `i-2`, `dp[i] = min(dp[i-1] + cost(i-1,i), dp[i-2] + cost(i-2,i))`. Because each state only depends on the previous two, we can track just two rolling variables instead of a full array — the same space-optimization pattern as Climbing Stairs.
