# Maximum Score Using Neighbor Products (Burst Balloons)

**Difficulty:** Hard
**Tags:** Dynamic Programming, Interval DP, Matrix Chain Multiplication pattern

## Problem Statement

Given an array `arr[]` of positive integers, repeatedly remove one element until the array is empty. When you remove an element, add to the score the **product of that element and its current left and right neighbors**. If the element is at either end, its missing neighbor counts as `1`. After a removal, the remaining elements become adjacent.

Return the maximum possible total score.

## Examples

### Example 1
```
Input:  arr = [5, 10]
Output: 60
Explanation:
  Remove 5:  1 * 5 * 10 = 50
  Remove 10: 1 * 10 * 1 = 10
  Total = 60
```

### Example 2
```
Input:  arr = [1, 2, 3, 4, 5]
Output: 110
Explanation:
  Remove 4: 3 * 4 * 5 = 60   -> [1, 2, 3, 5]
  Remove 3: 2 * 3 * 5 = 30   -> [1, 2, 5]
  Remove 2: 1 * 2 * 5 = 10   -> [1, 5]
  Remove 1: 1 * 1 * 5 = 5    -> [5]
  Remove 5: 1 * 5 * 1 = 5
  Total = 110
```

## Constraints

- `1 <= arr.size(), arr[i] <= 100`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Try every removal order, O(n!) |
| `Optimal.java` | Bottom-up interval DP, O(n^3), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (all removal orders) | O(n!) | O(n) recursion depth |
| Middle ground (top-down interval DP with memo) | O(n^3) | O(n^2) |
| Optimal (bottom-up interval DP) | O(n^3) | O(n^2) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Thinking about which element to remove **first** is a trap: after one removal the neighbors change, so the sub-problems on the left and right are no longer independent. Flip the question: in an interval, which element is removed **last**? If `k` is the last one removed from the interval `[l, r]`, then at that moment everything else in the interval is already gone, so `k`'s neighbors are exactly the fixed elements just **outside** the interval, `arr[l-1]` and `arr[r+1]`. The left part `[l, k-1]` and right part `[k+1, r]` are then truly independent sub-problems. This gives the recurrence

`dp[l][r] = max over k in [l, r] of ( a[l-1] * a[k] * a[r+1] + dp[l][k-1] + dp[k+1][r] )`

with `1` padded on both ends of the array. This is the classic **Burst Balloons** problem (LeetCode 312).
