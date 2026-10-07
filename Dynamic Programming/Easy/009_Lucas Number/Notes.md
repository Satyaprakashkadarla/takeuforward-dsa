# Notes: Lucas Number

## 1. Problem Recap

Lucas numbers follow `Ln = Ln-1 + Ln-2`, with `L0 = 2` and `L1 = 1`. Find `Ln mod (10^9+7)`.

```
L0=2, L1=1, L2=3, L3=4, L4=7, L5=11, L6=18, L7=29, ...
```

### Lucas Numbers Are Fibonacci's Twin
This is structurally **identical** to the Fibonacci recurrence — the only difference is the starting values. Fibonacci starts `F0=0, F1=1`; Lucas starts `L0=2, L1=1`. Every technique that applies to computing Fibonacci numbers efficiently (iterative O(1)-space DP, matrix exponentiation for very large n, etc.) applies directly here too.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Directly recurse using the Lucas recurrence, with base cases `L0=2`, `L1=1`.

### Code Logic
```java
public long lucas(int n) {
    if (n == 0) return 2;
    if (n == 1) return 1;
    return (lucas(n - 1) + lucas(n - 2)) % MOD;
}
```

### Dry Run
`lucas(5)`

```
lucas(5) = lucas(4) + lucas(3)
lucas(4) = lucas(3) + lucas(2)
lucas(3) = lucas(2) + lucas(1) = lucas(2) + 1
lucas(2) = lucas(1) + lucas(0) = 1 + 2 = 3
lucas(3) = 3 + 1 = 4
lucas(4) = 4 + 3 = 7
lucas(5) = 7 + 4 = 11
```

Result: **11** ✅ (matches expected output)

### Complexity
- **Time:** O(2^n) — identical exponential blowup to naive Fibonacci recursion, from massively repeated overlapping subproblems.
- **Space:** O(n) — recursion stack depth.

### Why It's Catastrophically Bad Here
With `n` up to `10^6`, this isn't just "slow" — it's completely infeasible (exponential time makes even `n=50` or so impractical, let alone `10^6`). The modulus operation prevents individual values from overflowing, but does nothing to address the exponential NUMBER of recursive calls.

---

## 3. Approach 2: Optimal (Iterative DP, O(1) Space)

### Idea
Build the sequence iteratively using two rolling variables, exactly like the standard Fibonacci iterative solution, just seeded with Lucas's starting values.

### Code Logic
```java
long a = 2; // L0
long b = 1; // L1

for (int i = 2; i <= n; i++) {
    long c = (a + b) % MOD;
    a = b;
    b = c;
}
return b;
```

### Dry Run 1
`n = 5`

| i | a | b | c=(a+b)%MOD | after shift (a,b) |
|---|---|---|---|---|
| start | 2 | 1 | — | (2,1) |
| 2 | 2 | 1 | 3 | (1,3) |
| 3 | 1 | 3 | 4 | (3,4) |
| 4 | 3 | 4 | 7 | (4,7) |
| 5 | 4 | 7 | 11 | (7,11) |

Return `b=11`.

Result: **11** ✅ (matches expected output)

### Dry Run 2 — Continuing to n=7
| i | a | b | c | after shift |
|---|---|---|---|---|
| 6 | 7 | 11 | 18 | (11,18) |
| 7 | 11 | 18 | 29 | (18,29) |

Return `b=29`.

Result: **29** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — single pass.
- **Space:** O(1) — two variables.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical for n up to 10^6? | Absolutely not | Yes — a single pass of up to a million iterations |

---

## 5. Why the Modulus Is Essential Here (Unlike Some Earlier Problems)

Unlike, say, Climbing Stairs (where `n` was capped at 45 and plain `int` sufficed), Lucas numbers grow exponentially and `n` can be as large as `10^6` — the true value of `L(10^6)` would have hundreds of thousands of digits, far beyond any fixed-size numeric type. Applying `% MOD` at every addition step keeps every intermediate value within `long` range, while still correctly computing the final answer modulo `10^9+7` (modular arithmetic is compatible with addition: `(a+b) mod m = ((a mod m) + (b mod m)) mod m`).

---

## 6. Edge Cases to Consider

1. **n = 0** — returns `2` directly via the explicit base case.
2. **n = 1** — returns `1` directly via the explicit base case.
3. **n = 2** — smallest case requiring the loop to run once; `L2 = L1+L0 = 1+2 = 3`.
4. **n = 10^6 (maximum)** — confirms the O(n) loop completes quickly (a single pass of a million simple iterations) and the modulus keeps every value bounded throughout.
5. **Values right at the modulus boundary** — the modulus operation correctly wraps values that would otherwise exceed `10^9+7`, and using `long` (rather than `int`) for intermediate sums avoids overflow during the addition itself (since two values just under `10^9+7` could sum to just under `2*10^9+14`, which would overflow a 32-bit `int`).

---

## 7. Related Concepts / Follow-Ups

- **Fibonacci Number**: The direct structural twin — identical recurrence, different seed values. Any Fibonacci-solving technique (iterative DP, matrix exponentiation for O(log n) computation, Binet's formula) transfers directly to Lucas numbers.
- **Matrix Exponentiation for Fibonacci-like Recurrences**: For even larger `n` (e.g., up to 10^18), an O(log n) matrix-power technique would be needed instead of this O(n) loop — worth knowing as the next level of optimization beyond what this problem's constraints require.
- **Modular Arithmetic in Recurrence Relations**: The pattern of applying `% MOD` at every addition (rather than just once at the end) is a standard technique whenever a recurrence's values would otherwise grow unboundedly large.

---

## 8. Key Takeaways

- Lucas numbers are Fibonacci numbers with different starting values — recognizing this immediately tells you which techniques apply.
- The same O(1)-space, two-rolling-variable iterative technique used for Fibonacci/Climbing Stairs/Frog Jump applies directly here.
- Given `n` up to `10^6` and exponential growth, the modulus must be applied at every step (not just at the end) to keep intermediate values from overflowing even a 64-bit `long`.
- Recognizing a "new" problem as a disguised version of one you already know (Lucas = Fibonacci's twin) is one of the most efficient problem-solving shortcuts available.
