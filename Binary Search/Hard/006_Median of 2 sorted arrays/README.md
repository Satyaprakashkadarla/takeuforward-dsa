# Median of 2 Sorted Arrays

**Difficulty:** Hard
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given two sorted arrays `arr1` and `arr2` of size `m` and `n` respectively, return the **median** of the two sorted arrays.

The median is the middle value of a sorted list. If the length of the combined list is even, the median is the average of the two middle elements.

## Examples

### Example 1
```
Input:  arr1 = [2,4,6], arr2 = [1,3,5]
Output: 3.5
Explanation: Merged = [1,2,3,4,5,6] (even length). Median = avg(3,4) = 3.5.
```

### Example 2
```
Input:  arr1 = [2,4,6], arr2 = [1,3]
Output: 3.0
Explanation: Merged = [1,2,3,4,6] (odd length). Median = 3.
```

### Your Turn
```
Input:  arr1 = [2,4,5], arr2 = [1,6]
Output: 4.0
Explanation: Merged = [1,2,4,5,6] (odd length, 5 elements). Median = middle element = 4.
```

## Constraints

- `0 <= m <= 1000`
- `0 <= n <= 1000`
- `1 <= m + n <= 2000`
- `-10^6 <= arr1[i], arr2[i] <= 10^6`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(m + n) merge-based solution |
| `Optimal.java` | O(log(min(m, n))) partition-based binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Merge Two Sorted Arrays) | O(m + n) | O(m + n) or O(1) with two pointers |
| Optimal (Binary Search on Partition Point) | O(log(min(m, n))) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a fundamentally different kind of binary search than the earlier "binary search on the answer" problems: here we binary search for the correct **partition point** that splits the combined array into a left half and right half of the correct sizes, such that every element on the left is `<=` every element on the right — without ever actually merging the arrays.
