# Lower Bound

**Difficulty:** Easy
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given a sorted array `nums` and an integer `x`, find the **lower bound** of `x`.

The lower bound is the **first and smallest index** in the sorted array where the value at that index is **greater than or equal to** `x`.

If no such index exists (i.e., every element is smaller than `x`), return the **size of the array**.

## Examples

### Example 1
```
Input:  nums = [1,2,2,3], x = 2
Output: 1
Explanation: Index 1 is the smallest index such that nums[1] >= x.
```

### Example 2
```
Input:  nums = [3,5,8,15,19], x = 9
Output: 3
Explanation: Index 3 is the smallest index such that nums[3] >= x.
```

### Example 3
```
Input:  nums = [3,5,8,15,19], x = 3
Output: 0
```

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^5 < nums[i], x < 10^5`
- `nums` is sorted in ascending order.

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

Lower bound is a **binary search on the answer** style problem — instead of searching for an exact match, we search for the boundary where the condition `nums[mid] >= x` first becomes true. This same pattern (search space of `[low, high]` where `high` starts at `nums.length`, not `nums.length - 1`) is the foundation for many other binary search variants like `upper bound`, `search insert position`, and `first/last occurrence`.
