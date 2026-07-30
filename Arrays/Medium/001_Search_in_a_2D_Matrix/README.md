# Search in a 2D Matrix

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given a 2-D array `mat` where:

- Each row is sorted in non-decreasing order.
- The first element of each row is greater than the last element of the previous row.

Given an integer `target`, determine whether the target exists in the matrix.

Return `true` if found, otherwise return `false`.

---

## Examples

### Example 1

**Input**

```text
mat = [
 [1, 2, 3, 4],
 [5, 6, 7, 8],
 [9, 10, 11, 12]
]

target = 8
```

**Output**

```text
true
```

---

### Example 2

**Input**

```text
mat = [
 [1, 2, 4],
 [6, 7, 8],
 [9, 10, 34]
]

target = 78
```

**Output**

```text
false
```

---

### Example 3

**Input**

```text
mat = [
 [1, 2, 4],
 [6, 7, 8],
 [9, 10, 34]
]

target = 7
```

**Output**

```text
true
```

---

## Constraints

- `1 <= m, n <= 100`
- `-10^4 <= mat[i][j], target <= 10^4`

---

# Brute Force Approach

## Idea

Traverse every element of the matrix one by one.

If any element equals the target, return `true`.

Otherwise return `false`.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m × n) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Treat the entire matrix as a single sorted array.

Apply Binary Search.

Convert the 1D index into 2D coordinates using:

- `row = mid / n`
- `col = mid % n`

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(log(m × n)) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
