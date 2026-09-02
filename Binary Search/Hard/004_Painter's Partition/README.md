# Painter's Partition

**Difficulty:** Medium
**Tags:** Array, Binary Search, Greedy

## Problem Statement

You are given `A` painters and an array `C` of `N` integers where `C[i]` denotes the length of the `i`th board. Each painter takes `B` units of time to paint 1 unit of board. Assign boards to painters such that:

- Each painter paints only **contiguous** segments of boards.
- No board can be split between painters.
- The goal is to **minimize the time** to paint all boards.

Return the minimum time required, modulo `10000003`.

## Examples

### Example 1
```
Input:  A = 2, B = 5, C = [1,10]
Output: 50
Explanation: Painter 1 paints board 0 (time=5), Painter 2 paints board 1 (time=50). Max time = 50.
```

### Example 2
```
Input:  A = 10, B = 1, C = [1,8,11,3]
Output: 11
Explanation: Each board to a different painter. Max time = max(1,8,11,3) = 11.
```

### Your Turn
```
Input:  A = 3, B = 2, C = [5,10,30,20]
Output: 60
Explanation: Best 3-way split: [5,10] | [30] | [20] → sums 15, 30, 20 → max sum = 30 → time = 30 * 2 = 60.
```

## Constraints

- `1 <= A <= 1000`
- `1 <= B <= 10^6`
- `1 <= N <= 10^5`
- `1 <= C[i] <= 10^6`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(sum(C) * N) linear scan solution |
| `Optimal.java` | O(N log(sum(C))) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan over board-length sums) | O(sum(C) * N) | O(1) |
| Optimal (Binary Search on the Answer) | O(N log(sum(C))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Painter's Partition is **the same "minimize the maximum" structure** as Split Array Largest Sum, Ship Packages Within D Days, and Book Allocation — just with one extra twist: the actual "time" answer is the minimized maximum **board-length sum**, multiplied by `B` (time per unit length), then taken modulo `10000003`.
