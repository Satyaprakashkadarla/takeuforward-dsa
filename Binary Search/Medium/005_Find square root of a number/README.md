# Find Square Root of a Number

**Difficulty:** Medium
**Tags:** Binary Search, Math
**Companies:** (add as applicable)

## Problem Statement

Given a positive integer `n`. Find and return its square root. If `n` is not a perfect square, return the **floor value** of `sqrt(n)`.

## Examples

### Example 1
```
Input:  n = 36
Output: 6
Explanation: 6 is the square root of 36.
```

### Example 2
```
Input:  n = 28
Output: 5
Explanation: The square root of 28 is approximately 5.292. So, the floor value is 5.
```

### Your Turn
```
Input:  n = 50
Output: 7
Explanation: The square root of 50 is approximately 7.071. So, the floor value is 7.
```

## Constraints

- `0 <= n <= 2^31 - 1`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(sqrt(n)) linear scan solution |
| `Optimal.java` | O(log n) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan) | O(sqrt(n)) | O(1) |
| Optimal (Binary Search) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a classic **"binary search on the answer"** problem — instead of searching within an array, we binary search over the *range of possible answers* (`1` to `n`), using the condition `mid * mid <= n` (implemented here as `mid <= n / mid` to avoid overflow) to decide which half to keep.
