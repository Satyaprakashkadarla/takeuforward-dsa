# Search X in Sorted Array

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given a sorted array of integers `nums` with 0-based indexing, find the index of a specified target integer.

- If the target is found in the array, return its index.
- If the target is not found, return `-1`.

## Examples

### Example 1
```
Input:  nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: The target integer 9 exists in nums and its index is 4
```

### Example 2
```
Input:  nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: The target integer 2 does not exist in nums so return -1
```

### Example 3
```
Input:  nums = [-1,0,3,5,9,12], target = -1
Output: 0
```

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^5 < nums[i], target < 10^5`
- `nums` is sorted in ascending order.
- All elements in `nums` are unique.

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

Since the array is **sorted**, binary search is the ideal technique — it eliminates half the search space on every comparison, making it far more efficient than a linear scan for large inputs.
