# Grid Unique Paths

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given two integers `m` and `n`, representing the number of rows and columns of a grid, determine the number of unique paths from the **top-left corner** to the **bottom-right corner**.

You can only move:

- Right
- Down

Return the total number of unique paths.

---

## Examples

### Example 1

**Input**

```text
m = 3, n = 2
```

**Output**

```text
3
```

**Explanation**

Possible paths:

1. Right → Down → Down
2. Down → Right → Down
3. Down → Down → Right

---

### Example 2

**Input**

```text
m = 2, n = 4
```

**Output**

```text
4
```

---

### Example 3

**Input**

```text
m = 3, n = 3
```

**Output**

```text
6
```

---

## Constraints

- `1 <= m, n <= 100`
- The answer will not exceed `10^9`

---

# Brute Force Approach

## Idea

Use recursion.

From every cell:

- Move right.
- Move down.

If the destination is reached, count one valid path.

The total number of paths is the sum of all possible recursive calls.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(2^(m+n)) |
| Space Complexity | O(m+n) |

---

# Optimal Approach

## Idea

A path consists of:

- `(m - 1)` downward moves
- `(n - 1)` rightward moves

Total moves:

```text
N = m + n - 2
```

We need to choose where the downward (or rightward) moves occur.

Using combinations:

```text
C(N, m-1)
```

Compute the combination efficiently without calculating factorials.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(min(m,n)) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
