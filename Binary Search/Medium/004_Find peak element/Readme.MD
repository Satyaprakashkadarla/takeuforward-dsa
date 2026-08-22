# Find Peak Element

**Difficulty:** Medium
**Tags:** Array, Binary Search
**Companies:** (add as applicable)

## Problem Statement

Given an array `arr` of integers. A **peak element** is defined as an element greater than both of its neighbors.

Formally, if `arr[i]` is the peak element, `arr[i-1] < arr[i]` and `arr[i+1] < arr[i]`.

Find the index (0-based) of a peak element in the array. If there are multiple peak elements, return the index of **any** one of them.

**Note:**
- `arr[0]`'s left neighbor is considered `-∞`.
- `arr[n-1]`'s right neighbor is considered `-∞`.

## Examples

### Example 1
```
Input:  arr = [1,2,3,4,5,6,7,8,5,1]
Output: 7
Explanation: There is only one peak, at index 7 (value 8).
```

### Example 2
```
Input:  arr = [1,2,1,3,5,6,4]
Output: 1
Explanation: There are 2 peaks, at indices 1 and 5. Either is a valid answer.
```

### Your Turn
```
Input:  arr = [-2,-1,3,4,5]
Output: 5 (peak value at index 4, since the right boundary is -∞)
```

## Constraints

- `1 <= arr.length <= 1000`
- `-2^31 <= arr[i] <= 2^31 - 1`
- `arr[i] != arr[i+1]` for all valid `i`.
- `arr[0]`'s left neighbor is `-∞`.
- `arr[n-1]`'s right neighbor is `-∞`.

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) linear scan solution |
| `Optimal.java` | O(log n) binary search solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Linear Scan) | O(n) | O(1) |
| Optimal (Binary Search) | O(log n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Binary search works here even though the array isn't sorted, because we're not searching for a specific value — we're searching for a **local maximum**. At any `mid`, if the slope is going up (`arr[mid] < arr[mid+1]`), a peak is guaranteed to exist somewhere to the right; if going down, a peak is guaranteed to exist at `mid` or to its left. This "always a peak exists in the direction of ascent" property is what makes binary search applicable to an unsorted array.
