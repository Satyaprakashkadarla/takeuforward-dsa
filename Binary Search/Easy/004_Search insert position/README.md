# Search Insert Position

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given a sorted array `nums` of **distinct** integers and a `target` value, return the index if the target is found. If not, return the index where it would be **if it were inserted in order**.

## Examples

### Example 1
```
Input:  nums = [1,3,5,6], target = 5
Output: 2
Explanation: The target value 5 is found at index 2 in the sorted array.
```

### Example 2
```
Input:  nums = [1,3,5,6], target = 2
Output: 1
Explanation: The target value 2 is not found in the array. However, it should
be inserted at index 1 to maintain the sorted order of the array.
```

### Example 3
```
Input:  nums = [1,3,5,6], target = 7
Output: 4
Explanation: 7 is larger than every element, so it belongs at the end (index 4).
```

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^5 <= nums[i] <= 10^5`
- `nums` contains distinct values sorted in ascending order.
- `-10^5 <= target <= 10^5`

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

This problem is **exactly** the "Lower Bound" pattern in disguise — the smallest index where `nums[index] >= target`. If the target exists, that index holds it; if not, that's precisely where it should be inserted to keep the array sorted.
