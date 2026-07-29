# Notes

## Problem Summary

Rotate the given array **one position to the left** while modifying the original array.

---

## Brute Force Approach

### Idea

Create a temporary array.

Copy elements from index `1` onward into the temporary array starting at index `0`.

Store the first element at the last position of the temporary array.

Copy all elements back into the original array.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Drawback

Uses an extra array, which increases space usage.

---

## Optimal Approach

### Idea

Store the first element.

Shift every remaining element one position to the left.

Place the stored element at the last index.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Array with one element
- Array with duplicate values
- Array containing negative numbers
- Already rotated array

---

## Key Concepts

- Array Traversal
- In-place Modification
- Array Rotation
- Space Optimization

---

## Interview Tip

Whenever an array rotation must be performed **in-place**, first think about storing only the elements that would otherwise be overwritten. This often reduces the space complexity from **O(n)** to **O(1)**.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Temporary Array | O(n) | O(n) |
| In-place Rotation | O(n) | O(1) |

---

## Takeaway

The in-place approach is the preferred solution because it rotates the array using only one extra variable while maintaining linear time complexity.
