# Max Subarray Sum for Values Limited by K

**Difficulty:** Easy
**Tags:** Array, Sliding Window / Running Sum

## Problem Statement

Given an array `arr[]` and an integer `k`, find the maximum sum of a **contiguous subarray** such that every element in the chosen subarray is `<= k`.

## Examples

### Example 1
```
Input:  k = 1, arr = [3,2,2,3,1,1,1,3]
Output: 3
Explanation: Subarray {1,1,1} — every element <= 1, sum = 3.
```

### Example 2
```
Input:  k = 2, arr = [3,2,2,3,1,1,1,3]
Output: 4
Explanation: Subarray {2,2} — every element <= 2, sum = 4 (beats {1,1,1}'s sum of 3).
```

## Constraints

- `0 <= k <= 10^5` *(likely a typo for `10^5` in the original "1051")*
- `1 <= arr.size() <= 10^5`
- `0 <= arr[i] <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n^2) solution checking every subarray |
| `Optimal.java` | O(n) single-pass running-sum solution (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Check Every Subarray) | O(n^2) | O(1) |
| Optimal (Single-Pass Running Sum) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Any element `> k` acts as a **hard break** — it can never be part of a valid subarray, so it splits the array into independent segments of "all elements `<= k`". Within each such segment, every element is automatically valid, so the best subarray sum in that segment is simply the segment's own total (since all values are non-negative, adding more of the segment never hurts). A single running sum that resets to 0 whenever an invalid element is hit finds the best segment sum in one linear pass.
