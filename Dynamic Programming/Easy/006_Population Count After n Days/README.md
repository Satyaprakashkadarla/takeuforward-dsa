# Population Count After N Days

**Difficulty:** Easy
**Tags:** Dynamic Programming, Simulation

## Problem Statement

Population starts with 1 member on day 1. Each living member produces 2 new members every day. Each member has a lifespan of 6 days (a member born on day `d` is removed before reproducing on day `d + 6`). Find the total population on day `n`.

## Examples

### Example 1
```
Input:  n = 2
Output: 3
Explanation: Day 1: 1, Day 2: 1 + 2 = 3
```

### Example 2
```
Input:  n = 3
Output: 9
Explanation: Day 1: 1, Day 2: 3, Day 3: 3 + 3*2 = 9
```

### Example 3
```
Input:  n = 7
Output: 726
```

## Constraints

- `1 <= n <= 20`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n) day-by-day simulation using a full birth-history array |
| `Optimal.java` | O(n) simulation using a size-6 circular "born" array (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Full Birth-History Array) | O(n) | O(n) |
| Optimal (Circular Buffer of Size 6) | O(n) | O(1) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Since members only live for exactly 6 days, you never need to remember births from more than 6 days ago — only the most recent 6 days' worth of births matter at any point (anything older has either already died or is irrelevant going forward). This lets a tiny fixed-size circular array (indexed by `day % 6`) replace what would otherwise be a growing, unbounded history, reducing space from O(n) to O(1).
