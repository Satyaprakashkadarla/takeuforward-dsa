# Box Stacking

**Difficulty:** Hard
**Tags:** Dynamic Programming, Sorting, Longest Increasing Subsequence (LIS) variant

## Problem Statement

Given arrays `height[]`, `width[]` and `length[]` of size `n`, where the `i`th box has dimensions `height[i] x width[i] x length[i]`, find the maximum possible height of a stack of boxes.

- A box can be **rotated** so that any of its dimensions becomes its height.
- A box can be placed on top of another only if **both base dimensions are strictly smaller** than those of the box below.
- **Multiple instances** of the same box can be used.

## Examples

### Example 1
```
Input:  height = [4,1,4,10], width = [6,2,5,12], length = [7,3,6,32]
Output: 60
Explanation: 10 + 32 + 4 + 4 + 6 + 1 + 3 = 60
```

### Example 2
```
Input:  height = [1,4,3], width = [2,5,4], length = [3,6,1]
Output: 15
Explanation: 4 + 6 + 1 + 1 + 3 = 15
```

## Constraints

- `1 <= n <= 100`
- `1 <= height[i], width[i], length[i] <= 10^6`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Plain recursion over all orientations (exponential) |
| `Optimal.java` | O(n^2) sort + LIS-style DP over the 3n orientations |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | Exponential | O(n) recursion stack |
| Optimal (Sort + LIS-style DP) | O(n^2) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Turn every box into **3 orientations** (each dimension takes a turn as the height). Each orientation is a "box" with a fixed height and a base `(a, b)` where `a <= b`. Because a box that fits on top must be strictly smaller in both base dimensions, it must also have a strictly smaller base **area**, so sorting orientations by base area in descending order guarantees every possible "box below" appears earlier in the list. After that the problem is a **Longest Increasing Subsequence with weights**: `dp[i] = height[i] + max(dp[j])` over earlier `j` whose base strictly contains base `i`.

Note: the `Optimal.java` here was written for this problem, since the code snippet supplied with the problem statement was for a different problem (merging two sorted parts of an array).
