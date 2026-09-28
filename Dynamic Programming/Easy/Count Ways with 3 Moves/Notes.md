# Notes: Count Ways to Reach the Nth Stair (1, 2 or 3 Steps)

## 1. Problem Recap

A child climbs `n` stairs, hopping 1, 2, or 3 steps at a time. Count the distinct sequences of hops that reach exactly step `n`.

```
n = 4 -> 7 ways
1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 2+2, 1+3, 3+1
```

Order matters: `1+2` and `2+1` count as different ways.

### The Key Insight: Climbing Stairs, Extended
This is the earlier **Climbing Stairs** problem with one more allowed hop size. The child's **last hop** must have been 1, 2, or 3 steps, so:

```
ways(n) = ways(n-1) + ways(n-2) + ways(n-3)
```

This is the **Tribonacci** recurrence (Fibonacci's cousin that sums three terms instead of two).

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Translate the recurrence directly into code with base cases `ways(0) = 1` and `ways(negative) = 0`.

### Code Logic
```java
static int countWays(int n) {
    if (n < 0) return 0;
    if (n == 0) return 1;
    return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
}
```

### Why `ways(0) = 1`?
There is exactly one way to be at the start without hopping at all (the empty sequence). This makes the recurrence work out for small `n` without special-casing:
```
ways(1) = ways(0) + ways(-1) + ways(-2) = 1 + 0 + 0 = 1
ways(2) = ways(1) + ways(0) + ways(-1) = 1 + 1 + 0 = 2
ways(3) = ways(2) + ways(1) + ways(0) = 2 + 1 + 1 = 4
```

### Dry Run
`countWays(4)`

```
countWays(4) = countWays(3) + countWays(2) + countWays(1)
             = 4 + 2 + 1
             = 7
```

Result: **7** ✅

### Complexity
- **Time:** O(3^n): every call branches into three more, with heavy repeated work.
- **Space:** O(n): recursion stack depth.

### Why It's Not Optimal
With `n` up to 30, `3^30` is about 2 x 10^14 calls in the worst case (the real count is somewhat lower, but still far too many). Overlapping subproblems are recomputed again and again.

---

## 3. Approach 2: Optimal (Iterative DP, O(1) Space)

### Idea
Build the answer bottom-up. Each value depends only on the previous three, so three rolling variables replace the whole array.

### Code Logic
```java
if (n <= 2) return n;
if (n == 3) return 4;

int a = 1; // ways(1)
int b = 2; // ways(2)
int c = 4; // ways(3)

for (int i = 4; i <= n; i++) {
    int d = a + b + c;
    a = b;
    b = c;
    c = d;
}
return c;
```

### Dry Run 1
`n = 3` -> caught by the `n == 3` base case, returns **4** ✅

### Dry Run 2
`n = 4`

| i | a | b | c | d = a+b+c | after shift (a, b, c) |
|---|---|---|---|---|---|
| start | 1 | 2 | 4 | — | (1, 2, 4) |
| 4 | 1 | 2 | 4 | 7 | (2, 4, 7) |

Return `c = 7`.

Result: **7** ✅ (matches expected output)

### Dry Run 3
`n = 1` -> caught by `n <= 2`, returns **1** ✅

### A Longer Trace for Intuition
`n = 6`

| i | a | b | c | d = a+b+c | after shift |
|---|---|---|---|---|---|
| start | 1 | 2 | 4 | — | (1, 2, 4) |
| 4 | 1 | 2 | 4 | 7 | (2, 4, 7) |
| 5 | 2 | 4 | 7 | 13 | (4, 7, 13) |
| 6 | 4 | 7 | 13 | 24 | (7, 13, 24) |

Return `c = 24`. The sequence 1, 2, 4, 7, 13, 24 is the Tribonacci-style progression.

### Complexity
- **Time:** O(n): a single pass from 4 to n.
- **Space:** O(1): three variables regardless of `n`.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(3^n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical for n = 30? | No | Yes, about 27 loop iterations |

---

## 5. Overflow Check for n = 30

The constraint caps `n` at 30, and the sequence grows fast:

| n | ways(n) |
|---|---|
| 10 | 274 |
| 20 | 121,415 |
| 25 | 2,555,757 |
| 30 | 53,798,080 |

`ways(30) = 53,798,080` is well below `Integer.MAX_VALUE` (about 2.147 x 10^9), so plain `int` is safe here. If the constraint allowed larger `n`, you would need `long` (around `n = 37` the value passes the `int` limit).

---

## 6. Edge Cases to Consider

1. **n = 1**: only one way (a single 1-step hop), covered by `n <= 2`.
2. **n = 2**: two ways (1+1 or 2), covered by `n <= 2`.
3. **n = 3**: four ways, including the single 3-step hop, handled by the explicit `n == 3` branch.
4. **n = 30 (maximum)**: confirms both the loop and the `int` range hold at the upper bound.
5. **Order sensitivity**: 1+2 and 2+1 are distinct ways, which is exactly why the recurrence adds three terms (each represents a different final hop) rather than counting unordered combinations.

---

## 7. Related Concepts / Follow-Ups

- **Climbing Stairs (1 or 2 steps)**: the direct predecessor. Same idea, two terms instead of three (Fibonacci).
- **Climbing Stairs with up to K steps**: the general form, `ways(n) = ways(n-1) + ... + ways(n-k)`. For large `k` a sliding-window sum keeps it O(n).
- **Frog Jump with K Distances**: a cost-minimizing cousin that also looks back up to `k` states.
- **Tribonacci Number** (LeetCode 1137): the same recurrence with different starting values (0, 1, 1).
- **Coin Change (number of ordered ways)**: this problem is equivalent to counting ordered ways to make `n` using coins of value 1, 2, 3.

---

## 8. Key Takeaways

- Adding a third hop size just adds a third term to the recurrence, so recognizing the "last move" decomposition makes generalizations easy.
- Brute-force recursion is exponential because of overlapping subproblems; iterating bottom-up removes the repetition.
- A recurrence that looks back a fixed number of states (here three) only needs that many rolling variables, giving O(1) space.
- The base cases matter: `ways(0) = 1` is the anchor that makes small values come out right without special handling.
