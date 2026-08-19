# Search in Rotated Sorted Array - II

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an integer array `nums`, sorted in ascending order (**may contain duplicate values**), and a target value `k`. The array is **rotated at some unknown pivot point**. Return `true` if `k` is present, otherwise return `false`.

## Examples

### Example 1
```
Input:  nums = [7,8,1,2,3,3,3,4,5,6], k = 3
Output: True
Explanation: The element 3 is present in the array.
```

### Example 2
```
Input:  nums = [7,8,1,2,3,3,3,4,5,6], k = 10
Output: False
Explanation: The element 10 is not present in the array.
```

### Your Turn
```
Input:  nums = [7,8,1,2,3,3,3,4,5,6], k = 7
Output: True
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is guaranteed to be rotated at some pivot.
- `-10^4 <= k <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | Provided solution (linear scan) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases, and notes on the true O(log n) average-case binary search alternative |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Search) | O(n) | O(1) |
| Optimal (as provided) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Unlike **Search in Rotated Sorted Array - I**, this version allows **duplicates**, which breaks the "identify the sorted half" trick used there in the worst case (e.g., `nums[left] == nums[mid] == nums[right]` gives no information about which side is sorted). Because of this, the worst-case time complexity for any correct approach degrades to **O(n)** — see `NOTES.md` for a full discussion, including the modified binary search that is O(log n) on average but still O(n) in the worst case.
