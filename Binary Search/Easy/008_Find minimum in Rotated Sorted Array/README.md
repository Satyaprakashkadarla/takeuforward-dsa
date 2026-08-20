# Find Minimum in Rotated Sorted Array

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `nums` of size `N`, sorted in ascending order with **distinct** values, and then **rotated** an unknown number of times (between `1` and `N`), find the **minimum element** in the array.

## Examples

### Example 1
```
Input:  nums = [4,5,6,7,0,1,2,3]
Output: 0
Explanation: The element 0 is the minimum element in the array.
```

### Example 2
```
Input:  nums = [3,4,5,1,2]
Output: 1
Explanation: The element 1 is the minimum element in the array.
```

### Your Turn
```
Input:  nums = [4,5,6,7,-7,1,2,3]
Output: -7
```

## Constraints

- `n == nums.length`
- `1 <= n <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- All the integers of `nums` are unique.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | Provided solution (`Collections.min`) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases, and the true O(log n) binary search alternative |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Search) | O(n) | O(1) |
| Optimal (as provided, `Collections.min`) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

`Collections.min` finds the minimum by scanning every element, so it's O(n) under the hood — it doesn't exploit the fact that the array is a **rotated sorted array**. Because the array has this special structure (two sorted segments), the minimum can actually be found in **O(log n)** using a modified binary search. See `NOTES.md` for that approach.
