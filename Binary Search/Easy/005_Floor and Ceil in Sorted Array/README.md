# Floor and Ceil in Sorted Array

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given a sorted array `nums` and an integer `x`, find the **floor** and **ceil** of `x` in `nums`.

- The **floor** of `x` is the **largest element** in the array which is **smaller than or equal to** `x`.
- The **ceiling** of `x` is the **smallest element** in the array **greater than or equal to** `x`.

If no floor or ceil exists, output `-1` for that value.

## Examples

### Example 1
```
Input:  nums = [3,4,4,7,8,10], x = 5
Output: 4 7
Explanation: The floor of 5 in the array is 4, and the ceiling of 5 in the array is 7.
```

### Example 2
```
Input:  nums = [3,4,4,7,8,10], x = 8
Output: 8 8
Explanation: The floor of 8 in the array is 8, and the ceiling of 8 in the array is also 8.
```

### Example 3
```
Input:  nums = [2,4,6,8,10,12,14], x = 1
Output: [-1, 2]
Explanation: No element is <= 1, so floor is -1. The smallest element >= 1 is 2, so ceil is 2.
```

## Constraints

- `1 <= nums.length <= 10^5`
- `0 < nums[i], x < 10^5`
- `nums` is sorted in ascending order.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | O(log n) single-pass binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Search) | O(n) | O(1) |
| Optimal (Binary Search, single pass) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Floor and ceil can each be computed independently using the lower-bound/upper-bound patterns, but the optimal solution here finds **both in a single binary search pass** by tracking `floor` and `ceil` as we narrow the search space — avoiding two separate O(log n) searches.
