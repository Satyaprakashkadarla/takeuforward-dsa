# Count Occurrences in a Sorted Array

**Difficulty:** Easy
**Tags:** Array, Binary Search

## Problem Statement

Given a sorted array of integers `arr` and an integer `target`, determine **how many times** `target` appears in `arr`.

Return the count of occurrences of `target` in the array.

## Examples

### Example 1
```
Input:  arr = [0,0,1,1,1,2,3], target = 1
Output: 3
Explanation: The number 1 appears 3 times in the array.
```

### Example 2
```
Input:  arr = [5,5,5,5,5,5], target = 5
Output: 6
Explanation: All elements in the array are 5, so the target appears 6 times.
```

### Example 3
```
Input:  arr = [2,4,6,8,10], target = 3
Output: 0
```

## Constraints

- `1 <= arr.length <= 10^6`
- `1 <= arr[i] <= 10^6`
- `1 <= target <= 10^6`

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

Once you know how to find the **first** and **last occurrence** of a value in a sorted array (via binary search), counting occurrences is just `last - first + 1`. This problem is a direct extension of "First and Last Occurrence."
