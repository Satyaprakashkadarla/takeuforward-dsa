# Notes: Strings with More Distinct Subsequences

## 1. Problem Recap

Given two strings of equal length, decide which has more **distinct** subsequences. On a tie, return `s1`.

```
"gfg": g, f, gf, gg, fg, gfg   -> 6 distinct
"ggg": g, gg, ggg              -> 3 distinct
```

"Distinct" means equal strings count once. In `"ggg"` there are 7 non-empty subsets of positions, but they spell only 3 different strings.

The empty subsequence is excluded from the count. This does not change the comparison (both strings lose the same 1), but the DP naturally includes it, so the code subtracts 1 at the end.

---

## 2. Approach 1: Brute Force (Enumerate Every Subsequence)

### Idea
A string of length `n` has `2^n - 1` non-empty subsets of positions. Build the string for each subset and put it in a `HashSet`; the set size is the number of distinct subsequences.

### Code Logic
```java
for (long mask = 1; mask < (1L << n); mask++) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++)
        if ((mask >> i & 1L) == 1L) sb.append(s.charAt(i));
    seen.add(sb.toString());
}
return seen.size();
```

### Dry Run
`s = "gfg"`, positions 0, 1, 2:

| Subset of positions | String |
|---|---|
| {0} | g |
| {1} | f |
| {2} | g (duplicate of {0}) |
| {0, 1} | gf |
| {0, 2} | gg |
| {1, 2} | fg |
| {0, 1, 2} | gfg |

7 subsets, 6 distinct strings → **6** ✅

`s = "ggg"`: the 7 subsets spell `g` (three times), `gg` (three times) and `ggg` (once) → **3** ✅

Since 6 >= 3, the answer is **"gfg"** ✅

### Complexity
- **Time:** O(2^n * n).
- **Space:** O(2^n * n) for the set.

For `n = 30` that is over a billion subsets, which is why the constraint `n <= 30` is a hint that a smarter DP exists.

---

## 3. Approach 2: Optimal (Linear DP, Provided Code)

### Building the Recurrence
Let `dp[i]` = number of distinct subsequences (including the empty one) of the first `i` characters.

**Step 1: the doubling.** Take all distinct subsequences of the first `i - 1` characters. For each, you can leave out the new character or append it:

```
dp[i] = 2 * dp[i - 1]
```

If the new character `ch` has never appeared before, all these results are different, and this is exact.

**Step 2: the correction for repeats.** If `ch` appeared before, some of the "appended" strings were already produced. Which ones? Suppose the previous `ch` sits at position `p` (0-indexed). When that earlier `ch` was processed, it was appended to every distinct subsequence of the prefix before it, `s[0..p-1]`, and there are `dp[p]` of those. Appending `ch` to those same subsequences again gives the same strings. So exactly `dp[p]` results are duplicates:

```
dp[i] = 2 * dp[i - 1] - dp[last[ch]]
```

The code stores `last[ch] = i - 1` (the 0-indexed position of the current character), so at the next occurrence `dp[last[ch]]` is exactly `dp[p]`.

### Code Logic
```java
long[] dp = new long[n + 1];
dp[0] = 1;                               // only the empty subsequence
int[] last = new int[256];
Arrays.fill(last, -1);

for (int i = 1; i <= n; i++) {
    char ch = s.charAt(i - 1);
    dp[i] = 2 * dp[i - 1];
    if (last[ch] != -1) dp[i] -= dp[last[ch]];
    last[ch] = i - 1;
}
return dp[n] - 1;                        // drop the empty subsequence
```

### Dry Run 1: `"gfg"`

| i | ch | 2 * dp[i-1] | last[ch] before | Subtract | dp[i] | last[ch] after |
|---|---|---|---|---|---|---|
| 0 | | | | | 1 | |
| 1 | g | 2 | -1 | 0 | 2 | 0 |
| 2 | f | 4 | -1 | 0 | 4 | 1 |
| 3 | g | 8 | 0 | dp[0] = 1 | 7 | 2 |

`dp = [1, 2, 4, 7]`, so the count is `7 - 1 =` **6** ✅

Why subtract 1 at `i = 3`? The distinct subsequences of `"gf"` are `"", g, f, gf` (4 of them, `dp[2]`). Skipping the new `g` keeps those four. Appending `g` gives `g, gg, fg, gfg`. But `"g"` is already in the first list, so the union has 7 strings, not 8. The duplicate is exactly the `"g"` formed by appending to the empty subsequence, and `dp[0] = 1` counts it.

### Dry Run 2: `"ggg"`

| i | ch | 2 * dp[i-1] | last[ch] before | Subtract | dp[i] | last[ch] after |
|---|---|---|---|---|---|---|
| 0 | | | | | 1 | |
| 1 | g | 2 | -1 | 0 | 2 | 0 |
| 2 | g | 4 | 0 | dp[0] = 1 | 3 | 1 |
| 3 | g | 6 | 1 | dp[1] = 2 | 4 | 2 |

`dp = [1, 2, 3, 4]`, so the count is `4 - 1 =` **3** ✅

A string made of one repeated letter of length `n` has exactly `n` distinct non-empty subsequences.

### Comparison for Example 1
`6 >= 3`, so `betterString` returns **"gfg"** ✅

### Dry Run 3: Example 2
`"a"` → `dp = [1, 2]` → count 1. `"b"` → count 1. The comparison is `1 >= 1`, which is true, so the tie goes to `s1` = **"a"** ✅

### Complexity
- **Time:** O(n) per string (plus a constant 256 to initialize `last`).
- **Space:** O(n + 256).

---

## 4. Comparing the Approaches

| Aspect | Brute Force | Optimal DP |
|---|---|---|
| Time | O(2^n * n) | O(n) |
| Space | O(2^n * n) | O(n) |
| n = 30 | Over 10^9 subsets | 30 steps |

---

## 5. Why Order Matters, Not Just the Letters

The count depends on **how repeats are spread out**, not only on which letters appear. Both of these use the letters `a, a, b`:

| String | `dp` | Distinct non-empty subsequences |
|---|---|---|
| `"aab"` | `[1, 2, 3, 6]` | 5 (`a, aa, b, ab, aab`) |
| `"aba"` | `[1, 2, 4, 7]` | 6 (`a, b, ab, aa, ba, aba`) |

In `"aba"` the repeated `a` comes after a `b`, so the second `a` creates more new strings. The correction `dp[last[ch]]` captures this: it is smaller when the previous occurrence was early.

---

## 6. Implementation Notes on the Provided Code

- **Return type and comparison:** `count(s1) >= count(s2) ? s1 : s2` implements the tie rule (return `s1` when equal).
- **`long`:** a string of length `n` has at most `2^n - 1` distinct non-empty subsequences. For `n <= 30` that is at most 1,073,741,823, so even `int` would fit, but `long` is a safe choice.
- **`last[256]`:** indexed by the character code, so it works for any character with a code below 256 (all lowercase letters qualify). Characters with codes of 256 or more would cause an `ArrayIndexOutOfBoundsException`; the problem's inputs are plain letters.
- **No negative values:** the subtracted amount `dp[last[ch]]` is always at most half of `2 * dp[i-1]`, so `dp[i]` stays positive.
- **The lengths are equal by the problem statement**, but the code never relies on that.

---

## 7. Edge Cases to Consider

1. **Tie** (for example `"a"` vs `"b"`, or two identical strings): return `s1`.
2. **All letters distinct** (for example `"abc"`): every subset gives a different string, so the count is `2^n - 1`, the maximum possible.
3. **All letters the same** (for example `"ggg"`): the count is exactly `n`, the minimum possible.
4. **Same letters, different order** (`"aab"` vs `"aba"`): the counts differ (5 vs 6), as shown above.
5. **Single character:** the count is 1.
6. **Maximum length (n = 30):** at most `2^30 - 1`, which fits easily.

---

## 8. Related Concepts / Follow-Ups

- **Distinct Subsequences II** (LeetCode 940): count the distinct non-empty subsequences of one string modulo `10^9 + 7`. It is this same recurrence.
- **Distinct Subsequences** (LeetCode 115): a different problem, counting how many times a string `t` occurs as a subsequence of `s`.
- **Subsequence counting DPs in general:** "double, then subtract duplicates caused by the previous occurrence" is the standard technique.
- **Longest Common Subsequence and Edit Distance:** other DP-on-strings problems, but they track pairs of positions instead of a single running count.

---

## 9. Key Takeaways

- Adding a character doubles the number of subsequences (skip or take), but if the character has appeared before, the strings created by "taking" the earlier occurrence are duplicates.
- The number of duplicates is `dp[position of the previous occurrence]`, which is why only the **last** occurrence of each character needs to be remembered.
- The count depends on where repeats occur, not just on how many there are.
- An O(2^n) enumeration becomes an O(n) pass once the duplicate structure is understood.
