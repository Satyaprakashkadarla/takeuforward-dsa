# Distinct Palindromic Substrings

**Difficulty:** Expert (as rated)
**Tags:** Strings, Palindromes, Expand Around Center, Hashing, Palindromic Tree (Eertree)

## Problem Statement

Given a string `s` of lowercase English letters, find all the **distinct** continuous palindromic substrings of `s`. They may be returned in any order (the driver prints them sorted).

## Examples

### Example 1
```
Input:  s = "abaaa"
Output: ["a", "aa", "aaa", "aba", "b"]
Explanation: All 5 distinct continuous palindromic substrings.
```

### Example 2
```
Input:  s = "geek"
Output: ["e", "ee", "g", "k"]
Explanation: All 4 distinct continuous palindromic substrings.
```

## Constraints

- `1 <= s.length() <= 10^4`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Check every substring for being a palindrome, O(n^3) |
| `Optimal.java` | Expand around every center and collect into a `HashSet`, as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, worst case, and the true linear-time alternative |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (check every substring) | O(n^3) | O(total length of distinct palindromes) |
| Expand Around Center + HashSet (as provided) | O(n^2) expansion steps (one per palindromic occurrence), plus the cost of building and hashing each substring (worst case O(n^3) character work) | O(total length of distinct palindromes) |
| Bonus: Palindromic Tree (Eertree) | O(n) to find all distinct palindromes | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Every palindrome has a **center**: a single character (odd length) or the gap between two characters (even length). From each of the `2n - 1` centers, expand outward while the two end characters match; every step of the expansion is a palindrome, so add it to a `HashSet` to remove duplicates. A useful fact: a string of length `n` has **at most `n` distinct palindromic substrings**, so the set stays small even when the number of palindromic *occurrences* is huge. The main cost to watch is not the expansion itself but **creating and hashing a `String` for every occurrence**, which gets expensive on strings like `"aaaa...a"`.
