# Print the Matrix in Spiral Manner

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an `M × N` matrix, return the elements of the matrix in **clockwise spiral order**.

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
[1,2,3,6,9,8,7,4,5]
```

---

### Example 2

**Input**

```text
matrix = [
 [1,2,3,4],
 [5,6,7,8]
]
```

**Output**

```text
[1,2,3,4,8,7,6,5]
```

---

### Example 3

**Input**

```text
matrix = [
 [1,2],
 [3,4],
 [5,6],
 [7,8]
]
```

**Output**

```text
[1,2,4,6,8,7,5,3]
```

---

## Constraints

- `1 <= m, n <= 100`
- `-100 <= matrix[i][j] <= 100`

---

# Brute Force Approach

## Idea

Maintain a separate `visited` matrix.

Move in the current direction until the next cell is invalid or already visited.

Change direction in the order:

- Right
- Down
- Left
- Up

Continue until all elements are visited.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(m × n) |

---

# Optimal Approach

## Idea

Maintain four boundaries:

- Top
- Bottom
- Left
- Right

Traverse the matrix layer by layer:

1. Left → Right
2. Top → Bottom
3. Right → Left
4. Bottom → Top

Shrink the boundaries after each traversal until all elements are processed.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(1) *(excluding output list)* |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
