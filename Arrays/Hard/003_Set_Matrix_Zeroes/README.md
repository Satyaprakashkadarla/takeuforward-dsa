# Set Matrix Zeroes

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an `m × n` integer matrix, if an element is `0`, set its entire row and column to `0`.

The operation must be performed **in-place**.

---

## Examples

### Example 1

**Input**

```text
matrix = [[1,1,1],
          [1,0,1],
          [1,1,1]]
```

**Output**

```text
[[1,0,1],
 [0,0,0],
 [1,0,1]]
```

---

### Example 2

**Input**

```text
matrix = [[0,1,2,0],
          [3,4,5,2],
          [1,3,1,5]]
```

**Output**

```text
[[0,0,0,0],
 [0,4,5,0],
 [0,3,1,0]]
```

---

### Example 3

**Input**

```text
matrix = [[1,2,3,4],
          [5,6,0,8],
          [9,10,11,12]]
```

**Output**

```text
[[1,2,0,4],
 [0,0,0,0],
 [9,10,0,12]]
```

---

## Constraints

- `1 <= m, n <= 200`
- `-2³¹ <= matrix[i][j] <= 2³¹ - 1`

---

# Brute Force Approach

## Idea

Use two extra arrays:

- One to mark rows containing zero.
- One to mark columns containing zero.

Traverse the matrix twice:

1. Record rows and columns to be zeroed.
2. Update the matrix based on the recorded information.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(m + n) |

---

# Optimal Approach

## Idea

Instead of using extra arrays:

- Use the **first row** as column markers.
- Use the **first column** as row markers.

Store whether the first row or first column originally contained a zero using two boolean variables.

After marking:

- Zero all required cells.
- Finally update the first row and first column.

This satisfies the **constant extra space** requirement.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
