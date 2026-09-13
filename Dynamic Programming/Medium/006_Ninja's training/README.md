# Ninja's Training

**Difficulty:** Medium
**Tags:** Dynamic Programming, 2D DP
**Companies:** (add as applicable)

## Problem Statement

A ninja trains for `n` days, choosing one of 3 activities each day (running, stealth, fighting). The same activity **cannot** be done on two consecutive days. Given `matrix[i][0..2]` as the merit points for each activity on day `i`, return the maximum total merit points achievable.

## Examples

### Example 1
```
Input:  matrix = [[10,40,70],[20,50,80],[30,60,90]]
Output: 210
Explanation: Day1=fighting(70), Day2=stealth(50), Day3=fighting(90). Total=210.
```

### Example 2
```
Input:  matrix = [[70,40,10],[180,20,5],[200,60,30]]
Output: 290
Explanation: Day1=running(70), Day2=stealth(20), Day3=running(200). Total=290.
```

### Your Turn
```
Input:  matrix = [[20,10,10],[20,10,10],[20,30,10]]
Output: 60
Explanation: Day1=running(20), Day2=fighting(10)... best combo sums to 60 (e.g. running,fighting,stealth = 20+10+30=60).
```

## Constraints

- `1 <= n <= 10^4`
- `n` = number of rows, `3` = number of columns
- `0 <= matrix[i][j] <= 1000`

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

This is the first **2D DP** problem in this series: the state isn't just "which day" but also "which activity was done that day." `dp[i][activity] = matrix[i][activity] + max(dp[i-1][other two activities])`. Since day `i` only depends on day `i-1`, the DP array can be space-optimized down to just 3 rolling values (one per activity) instead of a full `n x 3` table.
