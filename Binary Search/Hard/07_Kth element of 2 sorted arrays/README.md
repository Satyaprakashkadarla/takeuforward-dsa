# Kth Element of 2 Sorted Arrays

**Difficulty:** Hard
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given two sorted arrays `a` and `b` of size `m` and `n` respectively, find the `k`th element (1-indexed) of the final merged sorted array.

## Examples

### Example 1
```
Input:  a = [2,3,6,7,9], b = [1,4,8,10], k = 5
Output: 6
Explanation: Merged = [1,2,3,4,6,7,8,9,10]. The 5th element is 6.
```

### Example 2
```
Input:  a = [100,112,256,349,770], b = [72,86,113,119,265,445,892], k = 7
Output: 256
Explanation: Merged = [72,86,100,112,113,119,256,265,349,445,770,892]. The 7th element is 256.
```

### Your Turn
```
Input:  a = [2,3,6], b = [7,9], k = 4
Output: 7
Explanation: Merged = [2,3,6,7,9]. The 4th element is 7.
```

## Constraints

- `1 <= m, n <= 10^4`
- `0 <= a[i], b[i] < 10^9`
- `1 <= k <= m+n`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(m + n) merge-based solution |
| `Optimal.java` | O(log(min(m, n))) partition-based binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Merge Two Sorted Arrays) | O(m + n) | O(m + n) or O(1) with two pointers |
| Optimal (Binary Search on Partition Point) | O(log(min(m, n))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **direct generalization of "Median of 2 Sorted Arrays"** — instead of fixing the split at the halfway point, we fix it at exactly `k` elements on the combined left side, and binary search for the partition of `a` (and correspondingly `b`) that achieves this while keeping every left-side element `<=` every right-side element.
