# Rotate Matrix by 90 Degrees

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an `N × N` 2D integer matrix, rotate the matrix by **90 degrees clockwise**.

The rotation must be performed **in-place**, meaning the original matrix should be modified directly without using another matrix.

---

## Examples

### Example 1

**Input**

```text
matrix = [
 [1,2,3],
 [4,5,6],
 [7,8,9]
]
```

**Output**

```text
[
 [7,4,1],
 [8,5,2],
 [9,6,3]
]
```

---

### Example 2

**Input**

```text
matrix = [
 [0,1,1,2],
 [2,0,3,1],
 [4,5,0,5],
 [5,6,7,0]
]
```

**Output**

```text
[
 [5,4,2,0],
 [6,5,0,1],
 [7,0,3,1],
 [0,5,1,2]
]
```

---

### Example 3

**Input**

```text
matrix = [
 [1,1,2],
 [5,3,1],
 [5,3,5]
]
```

**Output**

```text
[
 [5,5,1],
 [3,3,1],
 [5,1,2]
]
```

---

## Constraints

- `1 <= n <= 100`
- `-10^4 <= matrix[i][j] <= 10^4`

---

# Brute Force Approach

## Idea

Create a temporary matrix.

For every element at position `(i, j)`, place it at position `(j, n - 1 - i)` in the temporary matrix.

Copy the temporary matrix back to the original matrix.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(n²) |

---

# Optimal Approach

## Idea

Perform the rotation in two steps:

1. Transpose the matrix.
2. Reverse every row.

This rotates the matrix 90 degrees clockwise without using extra space.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
