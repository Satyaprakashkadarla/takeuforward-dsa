# Word Wrap

**Difficulty:** Hard
**Tags:** Dynamic Programming, Partition DP, Strings

## Problem Statement

Given an array `arr[]` where `arr[i]` is the number of characters in the `i`th word, and a line width `k`, insert line breaks so the text is printed neatly. Each line holds words separated by single spaces and must not exceed `k` characters. Every word is shorter than or equal to the line width.

- The **extra spaces** in a line are the unused positions up to width `k` (this includes trailing spaces at the end of every line **except the last**).
- **Cost of a line** = (number of extra spaces)².
- **The last line is free** (cost 0).

Minimize the total cost over all lines.

## Examples

### Example 1
```
Input:  arr = [3, 2, 2, 5], k = 6
Output: 10
Explanation:
  Line 1: word 1             -> extra = 6 - 3 = 3         -> cost 9
  Line 2: words 2 and 3      -> extra = 6 - 2 - 2 - 1 = 1 -> cost 1
  Line 3: word 4 (last line) -> cost 0
  Total = 9 + 1 + 0 = 10
```

### Example 2
```
Input:  arr = [3, 2, 2], k = 4
Output: 5
Explanation:
  Line 1: word 1             -> extra = 1 -> cost 1
  Line 2: word 2             -> extra = 2 -> cost 4
  Line 3: word 3 (last line) -> cost 0
  Total = 5
```

## Constraints

- `1 <= arr.size() <= 500`
- `1 <= arr[i] <= 1000`
- `max(arr[i]) <= k <= 2000`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | Plain recursion over every possible line break, exponential |
| `Optimal.java` | Bottom-up DP, O(n * w) where w is words per line (at most O(n^2)), as provided |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Plain Recursion) | O(2^n) | O(n) recursion stack |
| Optimal (Bottom-Up DP) | O(n^2) worst case | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

This is a **partition DP**: decide where each line ends. Let `dp[i]` be the minimum cost to lay out words `i..n-1` on lines. For a line starting at word `i`, try every end word `j` that still fits, pay that line's cost, and add the best cost for the rest: `dp[i] = min over j of (cost(i, j) + dp[j + 1])`. The twist is that the **last line costs nothing**, which is handled with a single `j == n - 1` check. A greedy "pack as many words as possible" strategy is not optimal, because squaring the extra spaces rewards balanced lines.
