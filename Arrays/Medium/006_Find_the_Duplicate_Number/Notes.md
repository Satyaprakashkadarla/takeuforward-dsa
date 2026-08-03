# Notes

## Problem Summary

Find the only duplicate number in the array without:

- Modifying the original array.
- Using extra space.
- Exceeding linear time complexity.

---

## Brute Force Approach

### Idea

Compare every element with all remaining elements.

If two elements are equal, return the duplicate.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Performs unnecessary comparisons and is too slow for large inputs.

---

## Optimal Approach

### Idea

Think of the array as a linked list.

Each value points to the next index.

Since one value repeats, a cycle is formed.

Use **Floyd's Tortoise and Hare Algorithm** to detect the cycle.

### Phase 1

Move:

- Tortoise → one step
- Hare → two steps

They eventually meet inside the cycle.

### Phase 2

Reset the tortoise to the first element.

Move both pointers one step at a time.

The point where they meet again is the duplicate number.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Duplicate appears exactly twice
- Duplicate appears multiple times
- Smallest valid array
- Duplicate at the beginning
- Duplicate at the end

---

## Key Concepts

- Floyd's Cycle Detection
- Slow and Fast Pointers
- Linked List Representation
- Cycle Detection

---

## Interview Tip

When a problem involves:

- Numbers in the range `1...n`
- Array size `n+1`
- Exactly one duplicate
- Constant extra space

Think of the array as a **linked list** and apply **Floyd's Cycle Detection Algorithm** instead of using hashing or sorting.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Loops | O(n²) | O(1) |
| Floyd's Cycle Detection | O(n) | O(1) |

---

## Takeaway

Floyd's Tortoise and Hare algorithm is the optimal solution because it finds the duplicate in **linear time** using **constant extra space** without modifying the input array, satisfying all problem constraints.
```
