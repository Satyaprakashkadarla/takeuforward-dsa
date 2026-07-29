# Notes

## Problem Summary

Find the **first occurrence** of the target element in the array.

Return its index if found; otherwise return `-1`.

---

## Brute Force Approach

### Idea

Traverse the array from the first element to the last.

Compare each element with the target.

Return the index immediately when a match is found.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Drawback

There is no faster approach for an unsorted array without additional data structures.

---

## Optimal Approach

### Idea

Linear Search itself is the optimal solution for an unsorted array.

It checks each element until the target is found or the array ends.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Target is the first element.
- Target is the last element.
- Target is not present.
- Array contains duplicate elements.
- Array contains negative numbers.

---

## Key Concepts

- Array Traversal
- Sequential Search
- Early Return

---

## Interview Tip

Linear Search is preferred for **unsorted arrays**.

If the array is sorted and multiple searches are required, consider using **Binary Search**, which runs in **O(log n)** time.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Linear Search | O(n) | O(1) |

---

## Takeaway

Linear Search is simple, easy to implement, and efficient for small or unsorted datasets. It stops immediately after finding the first occurrence, avoiding unnecessary comparisons.
