# Notes

## Problem Summary

Search for a target element in a matrix where:

- Every row is sorted.
- The first element of each row is greater than the last element of the previous row.

---

## Brute Force Approach

### Idea

Visit every element in the matrix.

Compare each element with the target.

Return `true` immediately if the target is found.

### Complexity

- **Time Complexity:** O(m × n)
- **Space Complexity:** O(1)

### Drawback

Every element may need to be checked, making it inefficient for large matrices.

---

## Optimal Approach

### Idea

Since the matrix is globally sorted, imagine it as one long sorted array.

Apply Binary Search.

Convert the 1D index into matrix coordinates:

- `row = mid / numberOfColumns`
- `col = mid % numberOfColumns`

### Complexity

- **Time Complexity:** O(log(m × n))
- **Space Complexity:** O(1)

---

## Edge Cases

- Matrix with one row
- Matrix with one column
- Target is the first element
- Target is the last element
- Target does not exist
- Negative numbers

---

## Key Concepts

- Binary Search
- Matrix Index Mapping
- Row and Column Conversion
- Time Complexity Optimization

---

## Interview Tip

Whenever a matrix satisfies:

- Rows are sorted.
- Each row starts with a value greater than the previous row's last value.

You can treat the matrix as a single sorted array and apply Binary Search.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Linear Traversal | O(m × n) | O(1) |
| Binary Search | O(log(m × n)) | O(1) |

---

## Takeaway

Binary Search is the preferred solution because it leverages the sorted property of the matrix, reducing the search time from **O(m × n)** to **O(log(m × n))**.
