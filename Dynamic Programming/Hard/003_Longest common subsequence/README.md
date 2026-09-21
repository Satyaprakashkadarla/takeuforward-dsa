# Longest Common Subsequence

**Difficulty:** Medium/Hard
**Tags:** Dynamic Programming, DP on Strings
**Companies:** (add as applicable)

## Problem Statement

Given two strings `str1` and `str2`, find the length of their **longest common subsequence** (LCS) — a sequence that appears in both strings in the same relative order, but not necessarily contiguously.

## Examples

### Example 1
```
Input:  str1 = "bdefg", str2 = "bfg"
Output: 3
Explanation: LCS = "bfg"
```

### Example 2
```
Input:  str1 = "mnop", str2 = "mnq"
Output: 2
Explanation: LCS = "mn"
```

### Your Turn
```
Input:  str1 = "abc", str2 = "dafb"
Output: 2
Explanation: LCS = "ab" ('a' at str1[0]/str2[1], 'b' at str1[1]/str2[3], in matching relative order).
```

## Constraints

- `n = str1.length`, `m = str2.length`
- `1 <= n, m <= 10^3`
- Both strings are lowercase alphabetic

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(2^(n+m)) plain recursion solution |
| `Optimal.java` | O(n * m) 1D DP (space-optimized) solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^(n+m)) | O(n+m) (recursion stack) |
| Optimal (1D DP, Space-Optimized) | O(n * m) | O(min(n,m)) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is the foundational **"DP on Strings"** problem. At each pair of positions `(i,j)`: if the characters match, extend the LCS found so far by 1 (`dp[i-1][j-1] + 1`); otherwise, take the best of skipping a character from either string (`max(dp[i-1][j], dp[i][j-1])`). Since each row only depends on the row above (and the diagonal), a full 2D table can be collapsed to a single 1D array using a clever "diagonal" tracking variable.
