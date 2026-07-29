# Notes

## Problem Summary

Find the **second largest distinct element** in the given array.

Return `-1` if no such element exists.

---

## Brute Force Approach

### Idea

Sort the array in ascending order.

The last element is the largest.

Traverse backward until a different element is found.

### Complexity

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Drawback

Sorting the entire array is unnecessary when only the second largest element is required.

---

## Optimal Approach

### Idea

Traverse the array once while maintaining two variables:

- `largest`
- `secondLargest`

Whenever a larger element is found:

- Update `secondLargest` with the current `largest`
- Update `largest`

If the current element is between `largest` and `secondLargest`, update `secondLargest`.

---

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Array with one element
- All elements are equal
- Duplicate largest elements
- Negative numbers
- Already sorted array

---

## Key Concepts

- Array Traversal
- Variable Tracking
- Distinct Elements
- Linear Scan

---

## Interview Tip

Maintain two variables during a single traversal instead of sorting.

This reduces the time complexity from **O(n log n)** to **O(n)**.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Sorting | O(n log n) | O(1) |
| Single Traversal | O(n) | O(1) |

---

## Takeaway

A single traversal with two variables efficiently finds the second largest distinct element without modifying the original array.
