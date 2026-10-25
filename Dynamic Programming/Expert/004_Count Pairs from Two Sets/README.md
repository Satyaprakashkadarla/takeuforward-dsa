# Count Pairs from Two Sets

**Difficulty:** Expert (as rated)
**Tags:** Graph, Union-Find (Disjoint Set Union), Connected Components, Counting

## Problem Statement

There are `n` students numbered `0` to `n - 1`, and `p` pairs `(u, v)`, where each pair says that students `u` and `v` belong to the **same branch**. The same-branch relation is **transitive**. Find the total number of pairs of students that belong to **different** branches.

## Examples

### Example 1
```
Input:  n = 5, p = 3, pairs = [[0,1], [2,3], [0,4]]
Output: 6
Explanation: Branch 1 = {0, 1, 4}, Branch 2 = {2, 3}.
             Cross pairs: [0,2], [0,3], [1,2], [1,3], [4,2], [4,3] -> 6
```

### Example 2
```
Input:  n = 6, p = 4, pairs = [[0,1], [1,2], [2,3], [4,5]]
Output: 8
Explanation: Branch 1 = {0, 1, 2, 3}, Branch 2 = {4, 5}. 4 * 2 = 8 cross pairs.
```

## Constraints

- `1 <= n <= 5 * 10^4`
- `0 <= p, pairs.size() <= 5 * 10^4`
- Each pair `(u, v)` has `u != v` and refers to valid students (`0 <= u, v < n`)

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | BFS to label branches, then check all O(n^2) pairs of students |
| `Optimal.java` | Union-Find (by size, with path compression) and a size-based count, as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (label branches, check every pair) | O(n^2 + n + p) | O(n + p) |
| Components + formula (BFS/DFS) | O(n + p) | O(n + p) |
| Optimal (Union-Find, as provided) | O((n + p) * alpha(n)), effectively O(n + p) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Because "same branch" is transitive, the students split into **connected components** (branches). Two students are in different branches exactly when they lie in different components, so there is no need to look at individual pairs of students: if the component sizes are `s1, s2, ..., sk`, the answer is the number of ways to pick two students from different components, i.e. `sum over i < j of si * sj`. Union-Find builds the components and their sizes quickly, and the counting can be done in one sweep: for each component, multiply its size by the number of students **not yet counted**.
