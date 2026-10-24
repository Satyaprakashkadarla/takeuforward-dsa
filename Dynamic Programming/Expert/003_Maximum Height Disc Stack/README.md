# Maximum Height Disc Stack

**Difficulty:** Expert (as rated)
**Tags:** Dynamic Programming, Sorting, Fenwick Tree (Binary Indexed Tree), Weighted LIS in 2D

## Problem Statement

Given arrays `r[]` and `h[]` of size `n`, where `r[i]` and `h[i]` are the radius and height of the `i`th circular disc, find the maximum possible height of a stack.

- A disc can be placed above another only if **both** its radius and its height are **strictly smaller** than those of the disc below it.
- Each disc can be used **at most once**.

## Examples

### Example 1
```
Input:  r = [5, 7, 3], h = [6, 5, 4]
Output: 10
Explanation: The discs (radius 3, height 4) and (radius 5, height 6) form a valid stack: 4 + 6 = 10.
```

### Example 2
```
Input:  r = [3, 7], h = [7, 4]
Output: 7
Explanation: Neither disc can sit on the other (one is wider but shorter, the other narrower but taller), so the best is a single disc of height 7.
```

## Constraints

- `1 <= r.size(), h.size() <= 10^5`
- `1 <= r[i], h[i] <= 1000`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Sort by radius, then O(n^2) DP over all earlier discs |
| `Optimal.java` | Sort by radius + Fenwick tree (max) over heights, O(n log n), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (O(n^2) DP) | O(n^2) | O(n) |
| Optimal (Sort + Fenwick Tree) | O(n log n) | O(n + H), H = 1000 |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **weighted Longest Increasing Subsequence in two dimensions**. Sort the discs by radius so that one dimension is handled by the order. What remains is: for each disc, find the best stack whose top disc has a **strictly smaller height**, which is a "maximum over a prefix of heights" query. A **Fenwick tree that stores maximums** answers it in O(log H). The one trap is **discs with equal radius**: they cannot stack, so a whole group of equal-radius discs must be computed first and added to the tree only afterwards.
