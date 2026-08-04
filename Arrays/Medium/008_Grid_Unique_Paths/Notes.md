# Notes

## Problem Summary

Find the total number of unique paths from the **top-left** corner to the **bottom-right** corner of a grid.

Allowed movements:

- Right
- Down

---

## Brute Force Approach

### Idea

From every cell:

- Move down.
- Move right.

If the destination is reached, count one valid path.

Return the total number of possible paths.

### Complexity

- **Time Complexity:** O(2^(m+n))
- **Space Complexity:** O(m+n)

### Drawback

Many subproblems are solved repeatedly, making recursion inefficient for larger grids.

---

## Optimal Approach

### Idea

Every path contains:

```text
(m−1) Down moves
(n−1) Right moves
```

Total moves:

```text
m+n−2
```

The problem becomes choosing the positions of either:

- Down moves

or

- Right moves

Formula:

```text
C(m+n−2, m−1)
```

Instead of computing factorials, calculate the combination iteratively to avoid overflow and improve efficiency.

### Complexity

- **Time Complexity:** O(min(m,n))
- **Space Complexity:** O(1)

---

## Edge Cases

- Single row grid
- Single column grid
- Square grid
- Rectangular grid
- Large values of `m` and `n`

---

## Key Concepts

- Recursion
- Combinatorics
- Binomial Coefficient
- Mathematical Optimization

---

## Interview Tip

This problem has multiple solutions:

- Recursion
- Memoization
- Dynamic Programming
- Space Optimized DP
- Combinatorics

If the interviewer asks for the **most optimal solution**, use the **combinatorial formula** because it avoids recursion, dynamic programming, and extra memory while achieving excellent performance.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Recursion | O(2^(m+n)) | O(m+n) |
| Combinatorial Formula | O(min(m,n)) | O(1) |

---

## Takeaway

A path is simply a sequence of **right** and **down** moves. Using the binomial coefficient allows us to count all possible arrangements directly, making the combinatorial solution the most efficient approach for this problem.
```
