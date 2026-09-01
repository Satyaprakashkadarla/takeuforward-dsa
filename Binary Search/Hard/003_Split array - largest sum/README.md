# Split Array - Largest Sum

**Difficulty:** Hard
**Tags:** Array, Binary Search, Greedy
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `a` of size `n` and an integer `k`. Split the array into `k` **non-empty, contiguous** subarrays such that the **largest sum among the subarrays is minimized**. Return that minimized largest sum.

## Examples

### Example 1
```
Input:  a = [1,2,3,4,5], k = 3
Output: 6
Explanation: Split [1,2,3] | [4] | [5] → sums 6, 4, 5 → largest = 6, the minimum achievable.
```

### Example 2
```
Input:  a = [3,5,1], k = 3
Output: 5
Explanation: Only one way to split into 3 parts: [3] | [5] | [1] → largest = 5.
```

### Your Turn
```
Input:  a = [1,2,3,4,5], k = 2
Output: 9
Explanation: Split [1,2,3] | [4,5] → sums 6, 9 → largest = 9, the minimum achievable.
```

## Constraints

- `1 <= n <= 10^4`
- `1 <= k <= n`
- `1 <= a[i] <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(sum(a) * n) linear scan solution |
| `Optimal.java` | O(n log(sum(a))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over sums) | O(sum(a) * n) | O(1) |
| Optimal (Binary Search on the Answer) | O(n log(sum(a))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This problem is **structurally identical** to "Capacity to Ship Packages Within D Days" and "Book Allocation Problem" — just renamed once more (array elements/subarrays/k instead of packages/days or books/students). Same "minimize the maximum" binary-search-on-the-answer pattern, same greedy feasibility check.
