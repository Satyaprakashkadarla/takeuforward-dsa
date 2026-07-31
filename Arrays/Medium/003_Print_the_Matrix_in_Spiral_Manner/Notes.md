# Notes

## Problem Summary

Print all matrix elements in **clockwise spiral order**.

The traversal starts from the top-left corner and continues layer by layer until every element is visited.

---

## Brute Force Approach

### Idea

Use a separate `visited` matrix.

Move in one direction until the next cell is either out of bounds or already visited.

Change direction in the sequence:

- Right
- Down
- Left
- Up

Repeat until all elements are visited.

### Complexity

- **Time Complexity:** O(m × n)
- **Space Complexity:** O(m × n)

### Drawback

Requires an additional visited matrix, increasing memory usage.

---

## Optimal Approach

### Idea

Maintain four boundaries:

- Top
- Bottom
- Left
- Right

Traverse the outer layer of the matrix and shrink the boundaries after completing each side.

Repeat until all layers are processed.

### Complexity

- **Time Complexity:** O(m × n)
- **Space Complexity:** O(1) *(excluding output list)*

---

## Edge Cases

- Single row matrix
- Single column matrix
- Square matrix
- Rectangular matrix
- Matrix with one element

---

## Key Concepts

- Matrix Traversal
- Boundary Traversal
- Simulation
- Spiral Order
- Layer-by-Layer Processing

---

## Interview Tip

Whenever a matrix traversal follows a **spiral**, **boundary**, or **layer-by-layer** pattern, think about maintaining four pointers:

- Top
- Bottom
- Left
- Right

This approach avoids using an extra visited matrix and achieves constant auxiliary space.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Visited Matrix | O(m × n) | O(m × n) |
| Boundary Traversal | O(m × n) | O(1) |

---

## Takeaway

The boundary traversal technique is the preferred solution because it visits every element exactly once while using only constant extra space.
