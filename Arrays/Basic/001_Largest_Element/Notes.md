# Notes

## Problem Summary

Find the largest element present in the given array.

---

## Brute Force Approach

### Idea

Sort the entire array in ascending order. After sorting, the last element will be the largest.

### Why it Works

Sorting arranges all elements from smallest to largest, making the last element the maximum.

### Complexity

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1) *(Ignoring sorting recursion stack)*

### Drawback

Sorting the entire array is unnecessary because we only need the maximum element.

---

## Optimal Approach

### Idea

Traverse the array once while maintaining the maximum element found so far.

### Why it Works

Compare every element with the current maximum.
If a larger element is found, update the maximum.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Array contains only one element.
- Array contains duplicate values.
- Array contains all negative numbers.
- Array is already sorted.
- Array is sorted in descending order.

---

## Key Concepts

- Array Traversal
- Maximum Element
- Linear Search
- Time Complexity Optimization

---

## Interview Tip

Whenever the problem asks for **maximum**, **minimum**, **sum**, or **count**, think about solving it with a **single traversal (O(n))** instead of sorting the array.

Sorting should only be used if the relative order of elements is required.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Sorting | O(n log n) | O(1) |
| Linear Traversal | O(n) | O(1) |

---

## Takeaway

The linear traversal approach is the preferred solution because it visits each element exactly once, making it the most efficient method for finding the largest element.
