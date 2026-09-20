# Notes: Partition Equal Subset Sum

## 1. Problem Recap

Determine if an array can be split into two subsets with **equal sums**.

```
arr = [2, 2, 1, 1]
Total sum = 6, so each half must sum to 3.
Partition: {2,1} and {2,1} -> both sum to 3 -> True
```

### The Key Insight — This IS Subset Sum
Splitting an array into two equal-sum halves reduces to a single, familiar question:

> **Does some subset of the array sum to exactly `total_sum / 2`?**

If such a subset exists, it forms one half of the partition — and the *remaining* elements (the complement) automatically form the other half, since both halves together must add up to the total sum. This means Partition Equal Subset Sum requires **zero new algorithmic ideas** beyond Subset Sum — it's a direct application with one extra upfront check (odd sums can never be evenly split).

---

## 2. Approach 1: Brute Force (Plain Recursion, Reusing Subset Sum)

### Idea
Check if the sum is even; if so, run the exact same include/exclude Subset Sum recursion with `target = sum/2`.

### Code Logic
```java
int sum = total of arr;
if (sum % 2 != 0) return false;
int target = sum / 2;
return solve(arr, arr.length - 1, target);  // identical to Subset Sum's solve()
```

### Dry Run
`arr = [1, 10, 21, 10]`

`sum = 1+10+21+10 = 42` (even) → `target = 21`

Checking if some subset sums to 21: `{21}` alone works trivially (or `{1,10,10}` also works). The recursion would explore and find this.

Result: **True** ✅ (matches expected output — partition into `[1,10,10]` and `[21]`, both summing to 21)

### Complexity
- **Time:** O(2^n) — same exponential blowup as plain Subset Sum recursion.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (1D DP, Direct Reuse of Subset Sum)

### Idea
Same as the brute force: check for an odd sum upfront (impossible case, instant `false`), then run the exact same 1D space-optimized DP used for Subset Sum, with `target = sum/2`.

### Code Logic
```java
int sum = total of arr;
if ((sum & 1) == 1) return false;   // odd sum check via bitwise AND

int target = sum / 2;
boolean[] dp = new boolean[target + 1];
dp[0] = true;

for (int x : arr) {
    for (int j = target; j >= x; j--) {
        dp[j] = dp[j] || dp[j - x];
    }
}

return dp[target];
```

### Why `(sum & 1) == 1` Instead of `sum % 2 != 0`?
Both check for oddness, but `sum & 1` uses a bitwise AND against `1`, which directly inspects the least significant bit of `sum` — a common micro-optimization in competitive programming (bitwise operations are typically marginally faster than modulo, though modern compilers often optimize `% 2` into the same bitwise check anyway). Functionally, both are equivalent for non-negative integers.

### Dry Run 1
`arr = [1, 10, 21, 10]`

`sum = 42`, even → `target = 21`

`dp` array of size 22, `dp[0] = true`

**Process x=1:** dp[1] = dp[1]||dp[0] = true. `dp = {0,1}` reachable.

**Process x=10:** for j=21..10:
- j=11: dp[11]||dp[1] = true
- j=10: dp[10]||dp[0] = true

`dp = {0,1,10,11}` reachable.

**Process x=21:** for j=21..21:
- j=21: dp[21]||dp[0] = true

`dp[21] = true` ✅ already achieved via the single element 21 itself!

**Process x=10:** (second 10) for j=21..10: would extend further, but dp[21] is already true.

Return `dp[21] = true`.

Result: **True** ✅ (matches expected output)

### Dry Run 2
`arr = [1, 2, 3, 5]`

`sum = 1+2+3+5 = 11`, which is **odd** → immediately return `false` (no DP needed at all).

Result: **False** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`arr = [2, 2, 1, 1]`

`sum = 2+2+1+1 = 6`, even → `target = 3`

`dp` array of size 4, `dp[0] = true`

**Process x=2:** for j=3..2:
- j=3: dp[3]||dp[1] = false||false = false
- j=2: dp[2]||dp[0] = false||true = true

`dp = [T,F,T,F]`

**Process x=2 (second):** for j=3..2:
- j=3: dp[3]||dp[1] = false||false = false
- j=2: dp[2]||dp[0] = true||true = true (unchanged)

`dp = [T,F,T,F]`

**Process x=1:** for j=3..1:
- j=3: dp[3]||dp[2] = false||true = **true**
- j=2: dp[2]||dp[1] = true||false = true (unchanged)
- j=1: dp[1]||dp[0] = false||true = true

`dp = [T,T,T,T]`

**Process x=1 (second):** for j=3..1: all already true, no changes.

Return `dp[3] = true`.

**Result: True** ✅

So for the quiz options `True, False`, the correct answer is **True**.

### Complexity
- **Time:** O(n × sum/2)
- **Space:** O(sum/2)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (1D DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n × sum/2) |
| Space Complexity | O(n) | O(sum/2) |
| Practical given n*sum ≤ 10^5? | No — exponential blowup | Yes — directly bounded by the constraint |

Notably, the constraint `n * sum <= 10^5` is a strong hint pointing directly at this O(n × sum/2) DP approach — the problem setters are telling you exactly what complexity to aim for.

---

## 5. Why an Odd Sum Makes the Answer Trivially `False`

If the total sum is odd, it's mathematically impossible to split it into two equal integer parts (since two equal integers always sum to an even number). This check costs O(n) (to compute the sum) and immediately resolves roughly half of all possible inputs without needing any DP at all — a valuable fast-path optimization.

---

## 6. Edge Cases to Consider

1. **Odd total sum** — immediately `false`, regardless of the specific elements (see Dry Run 2).
2. **Single-element array** — a single element can never be split into two non-empty equal-sum subsets (unless the "other half" is allowed to be empty and sum to 0, but that would require the single element's value to also be 0, which isn't within the stated constraints of `arr[i] >= 1`). This naturally resolves to `false` in practice, since `target = arr[0]/2` would need a subset summing to a fraction of the single available element, which usually isn't achievable exactly (except in specific edge constructions).
3. **All elements equal** — e.g., `arr = [4,4,4,4]` → sum=16, target=8, easily partitioned as `{4,4}` and `{4,4}`.
4. **Sum exactly double the largest element** — e.g., `arr = [10, 3, 3, 4]` (sum=20, target=10) → the element `10` alone forms one half.
5. **Large n with n*sum right at the 10^5 boundary** — confirms the O(n × sum/2) approach stays within reasonable bounds per the problem's own constraint hint.

---

## 7. Related Concepts / Follow-Ups

- **Subset Sum**: The direct parent problem — Partition Equal Subset Sum is simply Subset Sum called once with `target = totalSum/2`, plus an odd-sum fast-path check.
- **Minimum Subset Sum Difference**: A natural generalization — instead of checking for an EXACT 50/50 split, find the split that minimizes the *difference* between the two subset sums (useful when an exact equal partition isn't possible).
- **Count Subsets with Given Sum**: Another Subset Sum variant, counting how many subsets achieve a target rather than just checking existence.

---

## 8. Key Takeaways

- Partition Equal Subset Sum requires no new algorithm — it's Subset Sum with `target = sum/2`, plus a quick odd-sum impossibility check.
- The constraint `n * sum <= 10^5` is a strong signal pointing directly at the intended O(n × sum) DP complexity — recognizing these constraint-based hints is a valuable competitive programming skill.
- If a subset summing to `total/2` exists, its complement automatically forms the other equal half — no need to separately verify the second half.
- This is a great example of how mastering one foundational DP pattern (Subset Sum) unlocks several related problems almost for free, once the underlying equivalence is recognized.
