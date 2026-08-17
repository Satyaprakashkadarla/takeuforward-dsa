# First and Last Occurrence

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an array of integers `nums` sorted in **non-decreasing** order, find the **starting and ending position** of a given `target` value.

If the target is not found in the array, return `[-1, -1]`.

## Examples

### Example 1
```
Input:  nums = [5,7,7,8,8,10], target = 8
Output: [3, 4]
Explanation: The target is 8, and it appears in the array at indices 3 and 4.
```

### Example 2
```
Input:  nums = [5,7,7,8,8,10], target = 6
Output: [-1, -1]
Explanation: The target is 6, which is not present in the array.
```

### Example 3
```
Input:  nums = [5,7,7,8,8,10], target = 5
Output: [0, 0]
```

## Constraints

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `nums` is a non-decreasing array.
- `-10^9 <= target <= 10^9`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | O(log n) binary search solution (two passes) |
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

This is a direct application of finding the **first occurrence** and **last occurrence** of a value using two independent binary searches: one that shrinks `right` after a match (to find the leftmost occurrence), and one that pushes `left` forward after a match (to find the rightmost occurrence).
