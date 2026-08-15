# Upper Bound

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given a sorted array `nums` and an integer `x`, find the **upper bound** of `x`.

The upper bound is the **smallest index** `i` such that `nums[i] > x` (strictly greater than).

If no such index exists (i.e., every element is `<= x`), return the **size of the array**.

## Examples

### Example 1
```
Input:  n = 4, nums = [1,2,2,3], x = 2
Output: 3
Explanation: Index 3 is the smallest index such that nums[3] > x.
```

### Example 2
```
Input:  n = 5, nums = [3,5,8,15,19], x = 9
Output: 3
Explanation: Index 3 is the smallest index such that nums[3] > x.
```

### Example 3
```
Input:  n = 5, nums = [3,5,8,15,19], x = 3
Output: 1
```
Explanation: `nums[0] = 3` is not `> 3`, but `nums[1] = 5 > 3`. So index 1 is the smallest index satisfying the condition.

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^5 < nums[i], x < 10^5`
- `nums` is sorted in ascending order.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | O(log n) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Search) | O(n) | O(1) |
| Optimal (Binary Search) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Upper bound is the sibling of **lower bound** — the only difference is the comparison operator (`>` instead of `>=`). Together, `upperBound(x) - lowerBound(x)` gives the **count of occurrences of x** in the array, and `lowerBound(x)` / `upperBound(x) - 1` give the first/last occurrence of x, respectively.
