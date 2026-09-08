# Notes: Climbing Stairs

## 1. Problem Recap

You're climbing a staircase with `n` steps, and can move either 1 or 2 steps at a time. Count the number of distinct ways to reach the top.

```
n = 3
Ways: (1+1+1), (2+1), (1+2)  ->  3 ways
```

### The Key Insight — This Is Fibonacci!
To reach step `n`, your **very last move** was either:
- a single step from step `n-1`, or
- a double step from step `n-2`.

So the total number of ways to reach step `n` is the **sum** of the ways to reach step `n-1` and the ways to reach step `n-2`:

```
ways(n) = ways(n-1) + ways(n-2)
```

This is *exactly* the Fibonacci recurrence (just shifted by an index or two, and with different base cases). Recognizing this is the single most important insight for solving this problem efficiently.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Directly translate the recurrence into recursive code, with base cases `ways(1) = 1` and `ways(2) = 2`.

### Code Logic
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    return climbStairs(n - 1) + climbStairs(n - 2);
}
```

### Dry Run
`climbStairs(3)`

```
climbStairs(3)
├── climbStairs(2) = 2   (base case)
└── climbStairs(1) = 1   (base case)
= 2 + 1 = 3
```

Result: **3** ✅

### The Exponential Blowup Problem
For larger `n`, this recursion recomputes the same subproblems over and over. For example, `climbStairs(5)` calls `climbStairs(4)` and `climbStairs(3)`, but `climbStairs(4)` *also* calls `climbStairs(3)` internally — so `climbStairs(3)` gets computed twice. This duplication compounds exponentially as `n` grows:

```
climbStairs(5)
├── climbStairs(4)
│   ├── climbStairs(3)          <- computed here...
│   │   ├── climbStairs(2)
│   │   └── climbStairs(1)
│   └── climbStairs(2)
└── climbStairs(3)              <- ...and AGAIN here (duplicate work!)
    ├── climbStairs(2)
    └── climbStairs(1)
```

### Complexity
- **Time:** O(2^n) — each call branches into 2 more calls, with no caching, leading to exponential blowup.
- **Space:** O(n) — maximum recursion stack depth (the longest chain of nested calls, from `n` down to the base case).

### Why It's Not Optimal
Given the constraint `n` up to `45`, `2^45` is astronomically large (over 35 trillion) — this brute-force approach would be far too slow in practice, even though the problem's constraints seem modest at first glance. This is a classic example of why recognizing repeated subproblems (and either memoizing or converting to iteration) is essential.

---

## 3. Approach 2: Optimal (Iterative DP, O(1) Space)

### Idea
Instead of recursing top-down (and re-computing overlapping subproblems), build the answer **iteratively from the bottom up**. Since each new value only depends on the *previous two* values, we don't need to store an entire array of results — just two rolling variables.

### Code Logic
```java
if (n <= 2) return n;

int a = 1; // ways(1)
int b = 2; // ways(2)

for (int i = 3; i <= n; i++) {
    int c = a + b;  // ways(i) = ways(i-1) + ways(i-2)
    a = b;          // shift window forward
    b = c;
}

return b;
```

### Dry Run 1
`n = 3`

| i | a (ways i-2) | b (ways i-1) | c = a+b (ways i) | after update: a, b |
|---|---|---|---|---|
| start | 1 | 2 | — | a=1, b=2 |
| 3 | 1 | 2 | 3 | a=2, b=3 |

Loop ends (i=3 was the only iteration since n=3). Return `b = 3`.

Result: **3** ✅ (matches expected output)

### Dry Run 2
`n = 2` → since `n <= 2`, return `n` directly = **2** ✅ (matches expected output, no loop needed)

### Dry Run 3 — "Your Turn" Case
`n = 1` → since `n <= 2`, return `n` directly = **1**

**Result: 1** ✅

So for the quiz options `0, 1, 2, 4`, the correct answer is **1**.

### A Slightly Longer Dry Run for Intuition
`n = 5`

| i | a | b | c = a+b | after update |
|---|---|---|---|---|
| start | 1 | 2 | — | a=1, b=2 |
| 3 | 1 | 2 | 3 | a=2, b=3 |
| 4 | 2 | 3 | 5 | a=3, b=5 |
| 5 | 3 | 5 | 8 | a=5, b=8 |

Return `b = 8`. (Sanity check: 1,2,3,5,8 — this is indeed the Fibonacci sequence shifted by one position, confirming the Fibonacci connection.)

### Complexity
- **Time:** O(n) — a single pass from `3` to `n`, with no repeated computation.
- **Space:** O(1) — only two variables (`a`, `b`) are tracked at any time, regardless of how large `n` is.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (Recursion) | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n) |
| Space Complexity | O(n) (recursion stack) | O(1) |
| Repeated subproblem work? | Yes - massive duplication | No - each value computed exactly once |
| Practical for n up to 45? | No - would take an extremely long time | Yes - trivially fast |

---

## 5. A Middle-Ground Approach Worth Knowing: Memoized Recursion

Between the exponential brute force and the fully iterative optimal solution lies **top-down DP with memoization**: keep the same recursive structure, but cache each `climbStairs(i)` result the first time it's computed, so repeated calls return instantly from the cache instead of recomputing:

```java
private Map<Integer, Integer> memo = new HashMap<>();

public int climbStairs(int n) {
    if (n <= 2) return n;
    if (memo.containsKey(n)) return memo.get(n);
    int result = climbStairs(n - 1) + climbStairs(n - 2);
    memo.put(n, result);
    return result;
}
```

This brings the time complexity down to O(n) as well (each subproblem computed once), but still uses O(n) space for both the memo table and the recursion stack — the fully iterative version shown in `Optimal.java` is strictly better on space, which is why it's the preferred "optimal" solution here.

---

## 6. Edge Cases to Consider

1. **n = 1** — only one way (a single 1-step); handled directly by the `n <= 2` base case (see Dry Run 3).
2. **n = 2** — two ways (two 1-steps, or one 2-step); also handled by the base case.
3. **n = 45 (maximum per constraints)** — verifies the iterative approach handles the upper bound efficiently; the result fits within `int` range (Fibonacci-like growth up to n=45 stays well under `Integer.MAX_VALUE`).
4. **Very small staircases** — the recurrence and base cases naturally cover n=1 and n=2 without needing the general loop to execute at all.

---

## 7. Related Concepts / Follow-Ups

- **Fibonacci Number**: The direct mathematical relative of this problem — recognizing "this problem's recurrence looks like Fibonacci" is a valuable pattern-matching skill applicable well beyond just staircase problems.
- **Climbing Stairs with Variable Step Sizes**: A natural generalization where you can move `1, 2, ..., k` steps at a time instead of just 1 or 2 — solvable with the same DP idea, just summing over the last `k` values instead of the last 2.
- **House Robber / Other Linear DP Problems**: Many DP problems share this same "each state depends only on a small fixed number of previous states" structure, making the space-optimized rolling-variable technique broadly reusable.
- **Climbing Stairs with a Cost** (LeetCode 746: Min Cost Climbing Stairs): A close variant that adds a cost to each step and asks for the minimum total cost to reach the top, using a very similar DP recurrence.

---

## 8. Key Takeaways

- Climbing Stairs is the Fibonacci sequence in a different costume — recognizing this immediately suggests both the recurrence relation and the O(n) iterative solution.
- Plain recursion without memoization leads to exponential time complexity due to massively repeated subproblem computation — a critical thing to watch for whenever a recursive solution's call tree branches without caching.
- The space-optimized DP technique (tracking only the last two values in rolling variables, rather than a full array) is a broadly applicable pattern whenever a recurrence only depends on a small, fixed window of previous states.
- Memoized (top-down) recursion is a reasonable middle-ground alternative to full iterative DP, trading a bit of space (O(n) for the memo/stack) for a more naturally structured recursive solution.
