# House Robber (Circular)

**Difficulty:** Medium
**Tags:** Dynamic Programming
**Companies:** (add as applicable)

## Problem Statement

Houses are arranged in a **circle** (first and last houses are adjacent). Given `money[i]` for each house, return the maximum amount that can be looted such that **no two adjacent houses** (including the first-last wraparound) are both robbed.

## Examples

### Example 1
```
Input:  money = [2,1,4,9]
Output: 10
Explanation: Rob houses 2 (index 1) and 9 (index 3) -> sum = 10.
```

### Example 2
```
Input:  money = [1,5,2,1,6]
Output: 11
Explanation: Rob houses 5, 1... actually 5 (index1) and 6(index4) can't both be taken (not adjacent, that's fine) plus others; best combo sums to 11.
```

### Your Turn
```
Input:  money = [9,4,1,8]
Output: 12
Explanation: Since it's circular, house 0 (9) and house 3 (8) can't both be taken. Best: rob houses 1 and 3 (4 + 8 = 12).
```

## Constraints

- `1 <= money.length <= 10^5`
- `0 <= money[i] <= 1000`

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

The circular constraint (house 0 and house n-1 are adjacent) means they can never *both* be robbed. So the answer is the max of two separate **linear** "Maximum Sum of Non-Adjacent Elements" runs: one excluding the last house (range `[0, n-2]`), and one excluding the first house (range `[1, n-1]`). This cleanly reduces the circular problem to two applications of the already-solved linear problem.
