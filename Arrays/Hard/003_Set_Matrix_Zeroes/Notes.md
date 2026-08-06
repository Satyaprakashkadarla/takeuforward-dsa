# Notes

## Problem Summary

Given a matrix, if any element is `0`, make its **entire row and column** equal to `0`.

The operation must be performed **in-place**.

---

## Brute Force Approach

### Idea

Maintain two arrays:

- `rows[]`
- `cols[]`

First pass:

- Record which rows and columns contain a zero.

Second pass:

- Set every marked row and column to zero.

### Complexity

- **Time Complexity:** O(m × n)
- **Space Complexity:** O(m + n)

### Drawback

Uses additional memory proportional to the number of rows and columns.

---

## Optimal Approach

### Idea

Avoid extra arrays by using:

- First row as column markers.
- First column as row markers.

Use two boolean variables:

- `firstRowZero`
- `firstColZero`

Steps:

1. Check whether the first row contains a zero.
2. Check whether the first column contains a zero.
3. Mark rows and columns using the first row and first column.
4. Update the remaining cells.
5. Finally update the first row and first column if required.

---

## Complexity

- **Time Complexity:** O(m × n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Zero in first row
- Zero in first column
- Multiple zeros
- Entire matrix already zero
- Single row matrix
- Single column matrix

---

## Key Concepts

- Matrix Traversal
- In-place Modification
- Marker Technique
- Constant Space Optimization

---

## Interview Tip

A common follow-up is:

> "Can you solve it without using extra arrays?"

The expected answer is to use the **first row and first column as marker arrays**, reducing the extra space from **O(m+n)** to **O(1)**.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Extra Row & Column Arrays | O(m × n) | O(m + n) |
| First Row & Column Markers | O(m × n) | O(1) |

---

## Takeaway

The optimal solution cleverly reuses the first row and first column to store marker information, eliminating the need for additional arrays while preserving **O(m × n)** time complexity and achieving **O(1)** extra space.
