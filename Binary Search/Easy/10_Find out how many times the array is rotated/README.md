# Find Out How Many Times the Array Is Rotated

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `nums` of size `n`, sorted in ascending order with **distinct** values. The array has been **right rotated** an unknown number of times, between `0` and `n-1` (inclusive). Determine the number of rotations performed on the array.

## Examples

### Example 1
```
Input:  nums = [4,5,6,7,0,1,2,3]
Output: 4
Explanation: The original array should be [0,1,2,3,4,5,6,7]. The array has been rotated 4 times.
```

### Example 2
```
Input:  nums = [3,4,5,1,2]
Output: 3
Explanation: The original array should be [1,2,3,4,5]. The array has been rotated 3 times.
```

### Your Turn
```
Input:  nums = [4,5,1,2]
Output: 2
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

The **number of rotations equals the index of the minimum element** in the rotated array. This problem is really "Find Minimum in Rotated Sorted Array" in disguise — once you find where the minimum sits, that index *is* the answer.
