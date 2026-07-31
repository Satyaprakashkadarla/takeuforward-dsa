# Notes

## Problem Summary

Find all **leaders** in the array.

A leader is an element that is **strictly greater than every element to its right**.

The rightmost element is always considered a leader.

---

## Brute Force Approach

### Idea

For every element:

- Traverse all elements to its right.
- If a greater or equal element exists, it is not a leader.
- Otherwise, add it to the answer.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1) *(excluding output list)*

### Drawback

Each element may compare with every element to its right, making it inefficient for large arrays.

---

## Optimal Approach

### Idea

Traverse from the right side.

Maintain the maximum element encountered so far.

If the current element is greater than the maximum, it is a leader.

Finally, reverse the result to restore the original order.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

---

## Edge Cases

- Single element array
- All elements equal
- Strictly increasing array
- Strictly decreasing array
- Negative numbers

---

## Key Concepts

- Reverse Traversal
- Maximum Tracking
- Array Traversal
- Greedy Observation

---

## Interview Tip

Whenever a problem asks about the **greatest element on the right**, think about traversing the array **from right to left** while maintaining the maximum seen so far.

This reduces the time complexity from **O(n²)** to **O(n)**.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Loops | O(n²) | O(1) |
| Reverse Traversal | O(n) | O(n) |

---

## Takeaway

Scanning the array from right to left while tracking the maximum element efficiently identifies all leaders in a single traversal.
