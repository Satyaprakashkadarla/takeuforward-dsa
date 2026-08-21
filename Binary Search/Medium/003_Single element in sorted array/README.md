# Single Element in Sorted Array

**Difficulty:** Medium
**Tags:** Array, Binary Search, Bit Manipulation
**Companies:** (add as applicable)

## Problem Statement

Given an array `nums` sorted in **non-decreasing** order. Every number in the array except **one** appears **twice**. Find the single number in the array.

## Examples

### Example 1
```
Input:  nums = [1,1,2,2,3,3,4,5,5,6,6]
Output: 4
Explanation: Only the number 4 appears once in the array.
```

### Example 2
```
Input:  nums = [1,1,3,5,5]
Output: 3
Explanation: Only the number 3 appears once in the array.
```

### Your Turn
```
Input:  nums = [1,1,2,2,3,3,4,4,5,5,6,6,7]
Output: 7
```

## Constraints

- `n == nums.length`
- `1 <= n <= 10^4`
- `-10^4 <= nums[i] <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) XOR / linear scan solution |
| `Optimal.java` | O(log n) binary search solution (using even/odd index parity) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (XOR / Linear Scan) | O(n) | O(1) |
| Optimal (Binary Search) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Before the single element, every pair `(nums[i], nums[i+1])` starts at an **even index** (i.e., `nums[2k] == nums[2k+1]`). After the single element, this pattern shifts — pairs start at **odd indices** instead. Binary search can detect exactly where this shift happens, pinpointing the single element in O(log n).
