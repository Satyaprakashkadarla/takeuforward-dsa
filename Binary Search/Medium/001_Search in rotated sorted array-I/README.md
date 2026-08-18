# Search in Rotated Sorted Array - I

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `nums`, sorted in ascending order (with **distinct** values), and a target value `k`. The array is **rotated at some unknown pivot point**. Find the index at which `k` is present. If `k` is not present, return `-1`.

## Examples

### Example 1
```
Input:  nums = [4,5,6,7,0,1,2], k = 0
Output: 4
Explanation: 0 is present in the rotated sorted array at index 4.
```

### Example 2
```
Input:  nums = [4,5,6,7,0,1,2], k = 3
Output: -1
Explanation: 3 is not present in the array.
```

### Your Turn
```
Input:  nums = [4,5,6,7,0,1,2], k = 5
Output: 1
```
Explanation: 5 is present in the array at index 1. (Of the given options `-1, 2, 1, 0`, the correct answer is **1**.)

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- All values of `nums` are unique.
- `nums` is an ascending array that is possibly rotated.
- `-10^4 <= k <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | O(log n) modified binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Search) | O(n) | O(1) |
| Optimal (Modified Binary Search) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

A rotated sorted array is made of **two sorted halves**. At every step of binary search, **at least one half (left or right of mid) is guaranteed to be sorted**. We identify which half is sorted, check if the target lies within that sorted half's range, and narrow the search accordingly — otherwise, we search the other half.
