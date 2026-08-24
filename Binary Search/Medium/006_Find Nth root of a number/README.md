# Find Nth Root of a Number

**Difficulty:** Medium
**Tags:** Binary Search, Math
**Companies:** (add as applicable)

## Problem Statement

Given two numbers `N` and `M`, find the **Nth root** of `M`. The Nth root of a number `M` is defined as a number `X` such that `X` raised to the power `N` equals `M`. If the Nth root is not an integer, return `-1`.

## Examples

### Example 1
```
Input:  N = 3, M = 27
Output: 3
Explanation: The cube root of 27 is 3 (3^3 = 27).
```

### Example 2
```
Input:  N = 4, M = 69
Output: -1
Explanation: The 4th root of 69 does not exist as an integer.
```

### Your Turn
```
Input:  N = 4, M = 81
Output: 3
Explanation: 3^4 = 81.
```

## Constraints

- `1 <= N <= 30`
- `1 <= M <= 10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(M) linear scan solution |
| `Optimal.java` | O(N log M) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan) | O(M) | O(1) |
| Optimal (Binary Search on the Answer) | O(N log M) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is another **"binary search on the answer"** problem: instead of computing `Math.pow()` (which involves floating-point rounding risk), we binary search over candidate integers `X` from `1` to `M`, and for each candidate, compute `X^N` manually — bailing out early the moment it exceeds `M`, to avoid unnecessary work and overflow.
