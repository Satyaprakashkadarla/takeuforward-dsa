# Maximum Sum Bitonic Subsequence

**Difficulty:** Easy (rated, though the optimal solution here is genuinely advanced)
**Tags:** Dynamic Programming, DP on Subsequences, Fenwick Tree / BIT, Coordinate Compression

## Problem Statement

Given an array `arr[]`, a subsequence is **bitonic** if it first strictly increases, then strictly decreases. Find the maximum sum achievable by any bitonic subsequence.

## Examples

### Example 1
```
Input:  arr = [80,60,30,40,20,10]
Output: 210
Explanation: The whole array is strictly decreasing (a valid bitonic sequence with an empty "increasing" part), sum = 80+60+30+40... 
Actually here the decreasing run [80,60,40,20,10] etc. — sum = 210 as stated.
```

### Example 2
```
Input:  arr = [1,15,51,45,33,100,12,18,9]
Output: 194
Explanation: Best bitonic subsequence: {1,15,51,100,18,9} = 194
```

### Example 3
```
Input:  arr = [10,10,10]
Output: 10
Explanation: Since the sequence must be STRICTLY increasing then STRICTLY decreasing, equal elements can't both be included — best is a single 10.
```

## Constraints

- `1 <= arr.size() <= 10^5`
- `1 <= arr[i] <= 10^6`

## Files in this Repo

| File | Description |
|---|---|
| `Bruteforce.java` | O(n^2) standard two-array DP solution |
| `Optimal.java` | O(n log n) DP with Fenwick Tree (BIT) + coordinate compression (as provided) |
| `NOTES.md` | Detailed explanation, dry runs, complexity analysis, edge cases |

## Approaches Summary

| Approach | Time Complexity | Space Complexity |
|---|---|---|
| Brute Force (O(n^2) DP) | O(n^2) | O(n) |
| Optimal (Fenwick Tree DP) | O(n log n) | O(n) |

## How to Run

```bash
javac Optimal.java
java Optimal
```

(Each file contains a `main` method with the example test cases for quick verification.)

## Key Takeaway

For each index `i`, compute `inc[i]` = max sum of a strictly increasing subsequence **ending** at `i`, and `dec[i]` = max sum of a strictly decreasing subsequence **starting** at `i`. Treating `i` as the "peak" of a bitonic sequence, the answer is `max(inc[i] + dec[i] - arr[i])` (subtracting `arr[i]` once since it's counted in both halves). The O(n^2) DP computes `inc`/`dec` by scanning all previous/later elements per index; the O(n log n) version replaces that inner scan with a **Fenwick Tree** that answers "max sum among all values strictly smaller than `arr[i]` seen so far" in O(log n), using **coordinate compression** to map values to compact ranks for indexing the tree.
