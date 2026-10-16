# Max Min Height

**Difficulty:** Hard
**Tags:** Binary Search on the Answer, Greedy, Sliding Window / Difference Array

## Problem Statement

A garden has `n` flowers in a row, where `arr[i]` is the height of the `i`th flower. You water them for `k` days. In one day you water `w` **continuous** flowers, and each watered flower grows by 1 unit. Maximize the **minimum** height among all flowers after `k` days.

## Examples

### Example 1
```
Input:  arr = [2, 3, 4, 5, 1], k = 2, w = 2
Output: 2
Explanation:
  Day 1: water the last two flowers -> [2, 3, 4, 6, 2]
  Day 2: water the last two flowers -> [2, 3, 4, 7, 3]
  Minimum height = 2
```

### Example 2
```
Input:  arr = [5, 8], k = 5, w = 1
Output: 9
Explanation:
  Day 1-4: water the first flower  -> [9, 8]
  Day 5:   water the second flower -> [9, 9]
  Minimum height = 9
```

## Constraints

- `1 <= arr.size() <= 10^5`
- `1 <= w <= arr.size()`
- `1 <= k <= 10^5`
- `1 <= arr[i] <= 10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Linear scan over the answer, with explicit window updates |
| `Optimal.java` | Binary search on the answer + greedy check with a difference array, as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (scan answer upward, explicit window updates) | O(k * n * w) | O(n) |
| Optimal (binary search + difference array) | O(n log(k + max(arr))) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **"maximize the minimum"** problem, so think **binary search on the answer** (like Aggressive Cows). Pick a target height `T` and ask: "can I make every flower at least `T` using at most `k` waterings?" That question is **monotonic**: if `T` is reachable, so is every smaller target. The check is greedy: sweep left to right, and the moment flower `i` is below `T`, water the window that **starts at `i`** exactly as many times as needed, because that window reaches furthest to the right and so helps the most flowers still to come. A **difference array** (sliding window of active waterings) makes each check O(n).
