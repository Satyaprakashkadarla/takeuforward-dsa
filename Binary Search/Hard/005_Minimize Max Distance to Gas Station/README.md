# Minimize Max Distance to Gas Station

**Difficulty:** Hard
**Tags:** Array, Binary Search on Real Numbers, Greedy

## Problem Statement

Given a sorted array `arr` of size `n` containing integer positions of `n` gas stations on the X-axis, and an integer `k`, place `k` new gas stations anywhere on the non-negative X-axis (including non-integer positions).

Let `dist` be the maximum distance between adjacent gas stations after adding the `k` new stations. Find the **minimum possible value of `dist`**.

Answers within `1e-6` of the true value are accepted.

## Examples

### Example 1
```
Input:  n = 10, arr = [1,2,3,4,5,6,7,8,9,10], k = 10
Output: 0.50000
```

### Example 2
```
Input:  n = 10, arr = [1,2,3,4,5,6,7,8,9,10], k = 1
Output: 1.00000
```

### Your Turn
```
Input:  n = 10, arr = [3,6,12,19,33,44,67,72,89,95], k = 2
Output: 14.00000
Explanation: Gaps are [3,6,7,14,11,23,5,17,6]. With 2 new stations, place one
inside the 23-gap and one inside the 17-gap. The 14-gap (between 19 and 33)
is left untouched and becomes the new maximum, since splitting it further
would require a 3rd station. Minimum achievable max distance = 14.
```

## Constraints

- `10 <= n <= 5000`
- `0 <= arr[i] <= 10^9`
- `arr` is sorted in strictly increasing order
- `0 <= k <= 10^5`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Fixed-step linear scan solution over real-valued distances |
| `Optimal.java` | Binary search on real numbers (100 fixed iterations) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Fixed-step Linear Scan) | O(n / precision) | O(1) |
| Optimal (Binary Search on Real Numbers) | O(n * 100) ≈ O(n log(1/precision)) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is **binary search on the answer over real (floating-point) numbers**, not integers. Since the answer can be any real value (not just a whole number), there's no natural "left == right, stop" termination condition — instead, the search runs for a **fixed number of iterations** (e.g., 100), which is more than enough to shrink the search interval well below the required `1e-6` precision.
