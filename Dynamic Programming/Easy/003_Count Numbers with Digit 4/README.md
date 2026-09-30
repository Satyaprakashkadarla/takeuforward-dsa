# Count Numbers with Digit 4

**Difficulty:** Easy
**Tags:** Math, Digit Manipulation, Digit DP

## Problem Statement

Given a number `n`, return the count of total numbers from `1` to `n` that contain the digit `4` somewhere in them.

## Examples

### Example 1
```
Input:  n = 9
Output: 1
Explanation: Only 4 contains the digit 4.
```

### Example 2
```
Input:  n = 44
Output: 9
Explanation: 4, 14, 24, 34, 40, 41, 42, 43, 44
```

## Constraints

- `1 <= n <= 10^5`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n * d) solution converting each number to a string and scanning it |
| `Optimal.java` | O(n * d) digit-checking solution using `% 10` / `/ 10` (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases, and a true sub-linear "Digit DP" alternative |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (String Conversion) | O(n * d) | O(d) per number (string allocation) |
| Optimal (as provided, digit extraction via `% 10`) | O(n * d) | O(1) |
| Bonus: Digit DP | O(d) | O(d) |

*(d = number of digits in n, at most 6 for n up to 10^5)*

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

Both the brute force and the provided "optimal" solution check every number from 1 to n individually — they only differ in *how* they inspect each number's digits (string scan vs. arithmetic `% 10`/`/ 10`). Since `n` is capped at `10^5`, this O(n * d) approach is fast enough in practice. A true asymptotic improvement (down to O(d), independent of n) is possible using **Digit DP**, discussed as a bonus in `NOTES.md`.
