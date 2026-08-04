# Pascal's Triangle I

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given two integers `r` and `c` (1-indexed), return the value present at the `rᵗʰ` row and `cᵗʰ` column of Pascal's Triangle.

In Pascal's Triangle:

- The first row contains only `1`.
- Every row starts and ends with `1`.
- Each interior element is the sum of the two elements directly above it.

Mathematically,

Pascal[r][c] = Pascal[r-1][c-1] + Pascal[r-1][c]

---

## Examples

### Example 1

**Input**

```text
r = 4, c = 2
```

**Output**

```text
3
```

---

### Example 2

**Input**

```text
r = 5, c = 3
```

**Output**

```text
6
```

---

## Constraints

- `1 <= r, c <= 30`
- `c <= r`

---

# Brute Force Approach

## Idea

Construct Pascal's Triangle row by row until the required row.

Return the element at column `c`.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(r²) |
| Space Complexity | O(r²) |

---

# Optimal Approach

## Idea

The required value is simply:

```text
C(r-1, c-1)
```

where

```text
C(n,k)=n!/(k!(n-k)!)
```

Instead of factorials, compute the combination iteratively to avoid overflow.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(min(c,r-c)) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
