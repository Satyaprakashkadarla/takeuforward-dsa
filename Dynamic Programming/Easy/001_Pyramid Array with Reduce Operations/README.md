# Form Pyramid (Minimum Cost to Build a Pyramid of Stones)

**Difficulty:** Medium/Hard
**Tags:** Dynamic Programming, Greedy, Prefix/Suffix DP
**Companies:** (add as applicable)

## Problem Statement

Given `arr[]` representing stone heights, transform it into a **pyramid** by only *reducing* heights (cost = 1 per unit reduced; stones can't grow or move). A valid pyramid is a contiguous subarray shaped like `1, 2, ..., x-1, x, x-1, ..., 2, 1`, with every stone outside it at height `0`. Find the minimum total cost.

## Examples

### Example 1
```
Input:  arr = [1, 2, 3, 4, 2, 1]
Output: 4
Explanation: Resulting array [1,2,3,2,1,0], total reduction = 4.
```

### Example 2
```
Input:  arr = [1, 2, 1]
Output: 0
Explanation: Already a valid pyramid.
```

## Constraints

- `1 <= arr.size(), arr[i] <= 10^5`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n^2) try-every-center-and-height solution |
| `Optimal.java` | O(n) prefix/suffix DP solution |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (Try Every Center & Height) | O(n^2) | O(1) extra |
| Optimal (Prefix/Suffix DP) | O(n) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

A pyramid of peak height `h` always has a fixed total sum: `1+2+...+h+...+2+1 = h²` (a classic identity). So minimizing cost is equivalent to **maximizing h²**, i.e., maximizing the achievable peak height `h`. For each position `i`, the tallest pyramid centered there is limited by how far heights can "ramp up" to the left (`left[i]`) and "ramp down" to the right (`right[i]`), each capped by the actual stone heights available. The best achievable height overall is `max over i of min(left[i], right[i])`.
