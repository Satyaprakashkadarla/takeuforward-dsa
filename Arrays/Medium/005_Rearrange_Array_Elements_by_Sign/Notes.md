# Notes

## Problem Summary

Rearrange the array such that:

- Positive and negative numbers alternate.
- The array starts with a positive number.
- The relative order of positive numbers is preserved.
- The relative order of negative numbers is preserved.

---

## Brute Force Approach

### Idea

Store all positive numbers in one list and all negative numbers in another list.

Then, place them alternately into a new array.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Drawback

Requires two additional lists before constructing the answer array.

---

## Optimal Approach

### Idea

Create the answer array directly.

Maintain two indices:

- `pos = 0` for positive numbers (even positions)
- `neg = 1` for negative numbers (odd positions)

Traverse the input array once.

Place each positive number at `pos` and increment `pos` by `2`.

Place each negative number at `neg` and increment `neg` by `2`.

### Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

---

## Edge Cases

- Minimum size array (`n = 2`)
- All positive and negative numbers already alternating
- Positive and negative numbers grouped together
- Duplicate values
- Large input size

---

## Key Concepts

- Array Traversal
- Index Manipulation
- Stable Ordering
- Two Pointer Technique

---

## Interview Tip

When elements must be placed at fixed positions (even/odd indices), maintain separate pointers for each type instead of repeatedly searching for the next available position.

This keeps the solution simple and efficient.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Separate Lists | O(n) | O(n) |
| Direct Placement | O(n) | O(n) |

---

## Takeaway

The direct placement approach preserves the relative order of positive and negative elements while constructing the result in a single traversal, making it clean and efficient.
```
