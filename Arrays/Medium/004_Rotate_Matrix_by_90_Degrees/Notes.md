# Notes

## Problem Summary

Rotate a square matrix by **90 degrees clockwise** without using additional space.

---

## Brute Force Approach

### Idea

Create a temporary matrix.

For every element `(i, j)`:

```text
temp[j][n - 1 - i] = matrix[i][j]
```

After filling the temporary matrix, copy all values back to the original matrix.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(n²)

### Drawback

Requires an additional `n × n` matrix, increasing memory usage.

---

## Optimal Approach

### Idea

Rotate the matrix in two steps:

### Step 1: Transpose

Swap:

```text
matrix[i][j]
```

with

```text
matrix[j][i]
```

This converts rows into columns.

---

### Step 2: Reverse Each Row

Reverse every row individually.

This completes a **90-degree clockwise rotation**.

---

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

---

## Edge Cases

- Matrix of size `1 × 1`
- Matrix of size `2 × 2`
- Matrix with duplicate values
- Matrix containing negative numbers

---

## Key Concepts

- Matrix Transpose
- In-place Rotation
- Two Pointer Technique
- Matrix Manipulation

---

## Interview Tip

For **clockwise rotation** of a square matrix:

1. Transpose the matrix.
2. Reverse each row.

For **anticlockwise rotation**:

1. Transpose the matrix.
2. Reverse each column.

Remembering this pattern is useful for many matrix-based interview questions.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Temporary Matrix | O(n²) | O(n²) |
| Transpose + Reverse | O(n²) | O(1) |

---

## Takeaway

The transpose-and-reverse technique is the standard in-place solution for rotating a square matrix by **90 degrees clockwise**, achieving constant auxiliary space while maintaining optimal time complexity.
