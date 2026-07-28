# Notes

## Problem Summary

Check whether the given array is sorted in **non-decreasing order**.

---

## Brute Force Approach

### Idea

Compare each element with its next element.

If an element is greater than the next one, the array is not sorted.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Drawback

There is no drawback. This is already the most efficient approach.

---

## Optimal Approach

### Idea

Traverse the array only once and compare adjacent elements.

Return `false` immediately when the order is violated.

Otherwise return `true`.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Single element array
- Duplicate elements
- Already sorted array
- Reverse sorted array

---

## Key Concepts

- Array Traversal
- Adjacent Element Comparison
- Early Exit

---

## Interview Tip

Never sort the array to check whether it is sorted.

Sorting changes the original order and increases the time complexity unnecessarily.

Simply compare adjacent elements in one traversal.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Adjacent Comparison | O(n) | O(1) |
| Single Traversal | O(n) | O(1) |

---

## Takeaway

Checking adjacent elements in a single traversal is the simplest and most efficient solution.
