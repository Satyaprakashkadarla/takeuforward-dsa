# Strings with More Distinct Subsequences

**Difficulty:** Expert (as rated)
**Tags:** Dynamic Programming, DP on Strings, Subsequences, Counting

## Problem Statement

Given two strings `s1` and `s2` of equal length, find which of the two has **more distinct subsequences**. If both have the same number, return `s1`.

A subsequence keeps the original order but need not be contiguous. "Distinct" means that equal strings count once, even if they can be formed from different positions.

## Examples

### Example 1
```
Input:  s1 = "gfg", s2 = "ggg"
Output: "gfg"
Explanation: "gfg" has 6 distinct subsequences, "ggg" has 3.
```

### Example 2
```
Input:  s1 = "a", s2 = "b"
Output: "a"
Explanation: Each string has exactly 1 distinct subsequence (itself), so the tie goes to s1.
```

## Constraints

- `1 <= n <= 30`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Generate every subsequence into a set, O(2^n * n) |
| `Optimal.java` | Linear DP that counts distinct subsequences using the last occurrence of each character, as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (enumerate all subsets, use a set) | O(2^n * n) | O(2^n * n) |
| Optimal (DP with last-occurrence correction) | O(n) per string | O(n + alphabet) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Let `dp[i]` be the number of distinct subsequences (counting the empty one) of the first `i` characters. Adding a new character lets every existing subsequence either **skip** it or **take** it, so `dp[i] = 2 * dp[i-1]`. But if the same character appeared before, some of the "take" results are strings we already counted: exactly the ones formed by appending the character to subsequences of the prefix that came **before its previous occurrence**. There are `dp[previous position - 1]` of those, so we subtract them. Run this for both strings, drop the empty subsequence, and compare. A string with many repeated characters has far fewer distinct subsequences than one with the same length and few repeats.
