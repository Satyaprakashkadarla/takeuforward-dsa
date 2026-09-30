# Notes: Count Numbers with Digit 4

## 1. Problem Recap

Count how many integers in `[1, n]` contain the digit `4` at least once.

```
n = 44
4, 14, 24, 34, 40, 41, 42, 43, 44 -> 9 numbers
```

Note that 4 only needs to appear **somewhere** in the decimal representation; it doesn't matter which position (units, tens, etc.), and a number can only be counted once even if 4 appears in it multiple times (e.g. 44 has two 4's but is counted once).

---

## 2. Approach 1: Brute Force (String Conversion + Scan)

### Idea
Convert each number to a `String` and check whether `'4'` appears in it using `indexOf`.

### Code Logic
```java
for (int i = 1; i <= n; i++) {
    if (String.valueOf(i).indexOf('4') != -1) {
        count++;
    }
}
```

### Dry Run
`n = 9`

| i | String | contains '4'? |
|---|---|---|
| 1-3 | "1","2","3" | No |
| 4 | "4" | **Yes** |
| 5-9 | "5".."9" | No |

Result: **1** ✅

### Complexity
- **Time:** O(n * d) — n numbers, each converted to a string of length d and scanned.
- **Space:** O(d) per number for the temporary string (garbage collected after use; no lasting extra memory).

### Why It's Not Ideal
Every iteration allocates a new `String` object just to check a single character property. This works fine here (`n` up to `10^5`), but the string allocation is unnecessary overhead compared to checking digits arithmetically.

---

## 3. Approach 2: Optimal (as Provided) — Digit Extraction via Modulo/Division

### Idea
Instead of converting to a string, peel off digits one at a time using `num % 10` (get the last digit) and `num /= 10` (drop it), checking each digit against `4` directly. Break out early the moment a match is found, since further digits don't matter once we know the number qualifies.

### Code Logic
```java
for (int i = 1; i <= n; i++) {
    int num = i;
    while (num > 0) {
        if (num % 10 == 4) {
            count++;
            break;
        }
        num /= 10;
    }
}
```

### Dry Run
`n = 44`, tracing a few representative numbers:

| i | Digit extraction | Contains 4? |
|---|---|---|
| 4 | 4%10=4 -> match | Yes |
| 14 | 14%10=4 -> match | Yes |
| 24 | 24%10=4 -> match | Yes |
| 34 | 34%10=4 -> match | Yes |
| 40 | 40%10=0, 4%10=4 -> match | Yes |
| 41 | 41%10=1, 4%10=4 -> match | Yes |
| 42 | 42%10=2, 4%10=4 -> match | Yes |
| 43 | 43%10=3, 4%10=4 -> match | Yes |
| 44 | 44%10=4 -> match | Yes |
| (all others 1-44) | no digit equals 4 | No |

Count of matches: 4,14,24,34,40,41,42,43,44 → **9**

Result: **9** ✅ (matches expected output)

### Complexity
- **Time:** O(n * d) — same asymptotic complexity as the brute force; the improvement here is purely about avoiding string allocation, not about visiting fewer numbers.
- **Space:** O(1) — pure integer arithmetic, no allocations.

### An Important Clarification
Despite being labeled "optimal," this solution is **not asymptotically faster** than the brute force — both are O(n × d). The real gain here is a constant-factor improvement (avoiding string allocation overhead), which matters in practice but doesn't change the complexity class. Given the constraint `n <= 10^5`, this is already comfortably fast (at most ~600,000 digit checks), so it's a perfectly reasonable "optimal" solution for this problem's actual constraints.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force (String) | "Optimal" (Digit Extraction) |
|---|---|---|
| Time Complexity | O(n * d) | O(n * d) |
| Space Complexity | O(d) per number (temporary) | O(1) |
| Practical difference | String allocation overhead | Pure arithmetic, no allocation |

---

## 5. Bonus: A Truly Sub-Linear Approach — Digit DP

For much larger `n` (say, up to `10^18`), checking every single number individually becomes infeasible. The standard technique for "count numbers up to N satisfying some digit-based property" is **Digit DP** — building the count digit by digit, without ever enumerating individual numbers.

### The Core Idea
Think of constructing an number digit by digit, left to right, up to `n`'s own digit length. At each position, track:
- Whether the digits chosen so far still match `n`'s prefix exactly (a "tight" bound) or have already gone strictly below it (free to pick any digit for the rest).
- Whether a `4` has already appeared among the digits chosen so far.

This is typically implemented as a recursive function with memoization, parameterized by (position, tightBound, hasSeenFour), counting how many complete numbers can be formed from each state.

### Why This Matters
Digit DP computes the answer in roughly O(d) time (d = number of digits in n, e.g. just ~18 for numbers up to 10^18), completely independent of how large `n` itself is — a massive improvement over any approach that iterates through every number from 1 to n. This problem's constraint (`n <= 10^5`) doesn't require this technique, but recognizing when a problem's constraints demand it (e.g., `n` up to `10^9` or `10^18`) is an important skill for competitive programming and technical interviews.

---

## 6. Edge Cases to Consider

1. **n = 1 to 3** — no numbers contain a 4; answer is `0`.
2. **n = 4** — exactly one number (4 itself) contains the digit; answer is `1`.
3. **n containing repeated 4's, like 444** — still counted once per qualifying number, regardless of how many 4's it contains internally.
4. **Numbers like 40-49** — all ten contain a 4 (in the tens place), contributing a "block" of 10 to the count whenever the range includes a full decade starting with 4.
5. **n = 100000 (maximum)** — verifies both provided approaches handle the upper constraint efficiently (at most ~600,000 digit checks for the "optimal" version).

---

## 7. Related Concepts / Follow-Ups

- **Count Numbers Without a Given Digit**: A close variant — count numbers from 1 to n that do NOT contain a specific digit, often solved with complementary counting (total numbers minus those that DO contain the digit) or a similar Digit DP formulation.
- **Digit DP (General Technique)**: A broader and very powerful technique for problems like "count numbers in [L, R] with digit sum equal to k," "count numbers with no two adjacent equal digits," etc. — this problem is a great, simple entry point into recognizing when Digit DP is warranted.
- **Numbers Containing a Specific Substring of Digits**: A generalization where you're checking for a multi-digit pattern rather than a single digit — solvable with a similar digit-extraction or Digit DP approach.

---

## 8. Key Takeaways

- Both provided approaches are O(n × d) — the "optimal" label here refers to avoiding string-allocation overhead, not to an asymptotic complexity improvement.
- Peeling digits via `% 10` and `/ 10` is a standard, allocation-free technique for digit-by-digit number inspection, generally preferable to string conversion when working with pure integer digit properties.
- For much larger constraints on `n`, a true asymptotic improvement requires **Digit DP**, which computes the answer in time proportional to the number of digits in `n`, not to `n` itself.
- Recognizing the difference between "a faster constant factor" (this problem's optimization) and "a genuinely better complexity class" (Digit DP) is an important distinction to keep clear when discussing algorithmic optimization.
