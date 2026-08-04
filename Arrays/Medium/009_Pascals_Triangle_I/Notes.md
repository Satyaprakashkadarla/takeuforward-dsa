# Notes

## Problem Summary

Find the value located at the `rᵗʰ` row and `cᵗʰ` column of Pascal's Triangle.

Rows and columns are **1-indexed**.

---

## Brute Force Approach

### Idea

Construct Pascal's Triangle row by row until the required row.

Return the value at column `c`.

### Complexity

- **Time Complexity:** O(r²)
- **Space Complexity:** O(r²)

### Drawback

Builds the entire triangle even though only one element is required.

---

## Optimal Approach

### Idea

Observe that every element of Pascal's Triangle is a **binomial coefficient**.

Formula:

```text
Value = C(r-1,c-1)
```

where

```text
C(n,k)=n!/(k!(n-k)!)
```

Instead of factorials, compute the result iteratively.

Also use symmetry:

```text
C(n,k)=C(n,n-k)
```

to reduce the number of iterations.

### Complexity

- **Time Complexity:** O(min(c,r-c))
- **Space Complexity:** O(1)

---

## Edge Cases

- First row
- First column
- Last column
- Middle element
- Large row values

---

## Key Concepts

- Pascal's Triangle
- Binomial Coefficient
- Combinatorics
- Mathematical Optimization

---

## Interview Tip

For Pascal's Triangle problems:

- **Pascal's Triangle I** → Find one element → **Combination Formula**
- **Pascal's Triangle II** → Generate one row
- **Pascal's Triangle III** → Generate the complete triangle

Recognizing which variation is asked helps in selecting the most efficient solution.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Build Triangle | O(r²) | O(r²) |
| Binomial Coefficient | O(min(c,r-c)) | O(1) |

---

## Takeaway

Each value in Pascal's Triangle is a **binomial coefficient**. Using the combination formula computes the required element directly without generating the entire triangle, making it the optimal solution.
