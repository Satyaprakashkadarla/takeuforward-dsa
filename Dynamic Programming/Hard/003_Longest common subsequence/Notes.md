# Notes: Longest Common Subsequence

## 1. Problem Recap

Find the length of the longest subsequence common to both strings — characters must appear in the same relative order in both, but don't need to be contiguous.

```
str1 = "abc", str2 = "dafb"
LCS = "ab": 'a' is str1[0] and str2[1]; 'b' is str1[1] and str2[3].
Both appear in the same relative order in each string -> length 2.
```

### The Core Recurrence
```
dp[i][j] = length of LCS between str1[0..i-1] and str2[0..j-1]

if str1[i-1] == str2[j-1]:
    dp[i][j] = 1 + dp[i-1][j-1]     (extend the LCS using this matching character)
else:
    dp[i][j] = max(dp[i-1][j], dp[i][j-1])   (skip a char from either string)
```

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively compare characters from the end of both strings: if they match, count it and recurse on both remaining prefixes; otherwise, try skipping a character from either string and take the better result.

### Code Logic
```java
private int solve(String s1, String s2, int i, int j) {
    if (i < 0 || j < 0) return 0;
    if (s1.charAt(i) == s2.charAt(j)) return 1 + solve(s1, s2, i-1, j-1);
    return Math.max(solve(s1, s2, i-1, j), solve(s1, s2, i, j-1));
}
```

### Dry Run
`str1 = "abc"`, `str2 = "dafb"`, finding `solve(2, 3)` (last chars: 'c' vs 'b')

```
solve(2,3): s1[2]='c', s2[3]='b' -> no match
  = max(solve(1,3), solve(2,2))

solve(1,3): s1[1]='b', s2[3]='b' -> MATCH!
  = 1 + solve(0,2)
  solve(0,2): s1[0]='a', s2[2]='f' -> no match
    = max(solve(-1,2), solve(0,1))
    solve(-1,2) = 0 (base case)
    solve(0,1): s1[0]='a', s2[1]='a' -> MATCH!
      = 1 + solve(-1,0) = 1 + 0 = 1
    solve(0,2) = max(0, 1) = 1
  solve(1,3) = 1 + 1 = 2

solve(2,2): s1[2]='c', s2[2]='f' -> no match
  = max(solve(1,2), solve(2,1))
  solve(1,2): s1[1]='b', s2[2]='f' -> no match
    = max(solve(0,2), solve(1,1))
    solve(0,2) = 1 (computed above)
    solve(1,1): s1[1]='b', s2[1]='a' -> no match
      = max(solve(0,1), solve(1,0))
      solve(0,1) = 1 (computed above)
      solve(1,0): s1[1]='b',s2[0]='d' -> no match = max(solve(0,0),solve(1,-1))
        solve(0,0): s1[0]='a',s2[0]='d'->no match=max(solve(-1,0),solve(0,-1))=max(0,0)=0
        solve(1,-1)=0
      solve(1,0)=0
    solve(1,1) = max(1,0) = 1
  solve(1,2) = max(1,1) = 1
  solve(2,1): s1[2]='c',s2[1]='a'->no match = max(solve(1,1),solve(2,0))
    solve(1,1)=1 (above)
    solve(2,0): s1[2]='c',s2[0]='d'->no match=max(solve(1,0),solve(2,-1))=max(0,0)=0
  solve(2,1)=max(1,0)=1
solve(2,2) = max(1,1) = 1

solve(2,3) = max(solve(1,3), solve(2,2)) = max(2,1) = 2
```

Result: **2** ✅ (matches expected output — LCS "ab")

### Complexity
- **Time:** O(2^(n+m)) — exponential branching, massive repeated (i,j) overlap.
- **Space:** O(n+m) — recursion stack depth.

---

## 3. Approach 2: Optimal (1D DP, Space-Optimized)

### Idea
Instead of a full `(n+1) x (m+1)` 2D table, use a single 1D array of size `m+1`, updated row by row (over `str1`'s characters). The trick is tracking a `diagonal` variable to remember `dp[i-1][j-1]` before it gets overwritten by the current row's computation.

### Why a `diagonal` Variable Is Needed
In the 2D version, computing `dp[i][j]` (on a match) needs `dp[i-1][j-1]` — a value from the row *above*, one column to the *left*. But if we're reusing a single 1D array across rows, by the time we're computing column `j`, `dp[j-1]` has *already* been overwritten with the current row's value (since we process columns left to right) — so `dp[i-1][j-1]` is lost unless we save it separately.

The `diagonal` variable solves this: right before overwriting `dp[j]`, we save its OLD value (which is `dp[i-1][j]`, from the row above) into `above`. Then, `diagonal` (which was set from the *previous* column's `above`) correctly holds `dp[i-1][j-1]` for the current column's match-case computation. After processing column `j`, we update `diagonal = above` so it's ready for column `j+1`.

### Code Logic
```java
int m = str2.length();  // the SHORTER string, after the length-based swap
int[] dp = new int[m + 1];

for (int i = 1; i <= str1.length(); i++) {
    int diagonal = 0;
    for (int j = 1; j <= m; j++) {
        int above = dp[j];   // save dp[i-1][j] before overwriting

        if (str1.charAt(i-1) == str2.charAt(j-1)) {
            dp[j] = diagonal + 1;
        } else {
            dp[j] = Math.max(dp[j], dp[j-1]);
        }

        diagonal = above;    // prepare dp[i-1][j] as the diagonal for next column
    }
}
return dp[m];
```

### Dry Run 1
`str1 = "bdefg"`, `str2 = "bfg"` (str1 is longer, so no swap needed; m=3)

`dp = [0,0,0,0]` initially (indices 0-3, representing "" vs each prefix of str2)

**i=1 (str1[0]='b'):** diagonal starts at 0
- j=1 (str2[0]='b'): above=dp[1]=0; match! dp[1]=diagonal+1=0+1=1; diagonal=above=0
- j=2 (str2[1]='f'): above=dp[2]=0; no match; dp[2]=max(dp[2],dp[1])=max(0,1)=1; diagonal=0
- j=3 (str2[2]='g'): above=dp[3]=0; no match; dp[3]=max(0,1)=1; diagonal=0

`dp = [0,1,1,1]`

**i=2 (str1[1]='d'):** diagonal=0
- j=1 ('b'): above=1; no match ('d'≠'b'); dp[1]=max(dp[1]=1,dp[0]=0)=1; diagonal=1
- j=2 ('f'): above=1; no match; dp[2]=max(dp[2]=1,dp[1]=1)=1; diagonal=1
- j=3 ('g'): above=1; no match; dp[3]=max(1,1)=1; diagonal=1

`dp = [0,1,1,1]` (unchanged)

**i=3 (str1[2]='e'):** similar — 'e' doesn't match 'b','f','g'; dp stays `[0,1,1,1]`

**i=4 (str1[3]='f'):** diagonal=0
- j=1 ('b'): above=1; no match; dp[1]=max(1,0)=1; diagonal=1
- j=2 ('f'): above=1; MATCH; dp[2]=diagonal+1=1+1=2; diagonal=1
- j=3 ('g'): above=1; no match; dp[3]=max(dp[3]=1,dp[2]=2)=2; diagonal=1

`dp = [0,1,2,2]`

**i=5 (str1[4]='g'):** diagonal=0
- j=1 ('b'): above=1; no match; dp[1]=max(1,0)=1; diagonal=1
- j=2 ('f'): above=2; no match; dp[2]=max(2,1)=2; diagonal=2
- j=3 ('g'): above=2; MATCH; dp[3]=diagonal+1=2+1=3; diagonal=2

`dp = [0,1,2,3]`

Return `dp[3] = 3`.

Result: **3** ✅ (matches expected output — LCS "bfg")

### Dry Run 2 — "Your Turn" Case
`str1 = "abc"`, `str2 = "dafb"` — str2 is LONGER (length 4 vs 3), so **swap**: internally, `str1` becomes `"dafb"`, `str2` becomes `"abc"`. Now `m = 3`.

`dp = [0,0,0,0]`

**i=1 (new str1[0]='d'):** diagonal=0
- j=1 ('a'): above=0; no match; dp[1]=max(0,0)=0; diagonal=0
- j=2 ('b'): above=0; no match; dp[2]=max(0,0)=0; diagonal=0
- j=3 ('c'): above=0; no match; dp[3]=max(0,0)=0; diagonal=0

`dp = [0,0,0,0]`

**i=2 (new str1[1]='a'):** diagonal=0
- j=1 ('a'): above=0; MATCH; dp[1]=0+1=1; diagonal=0
- j=2 ('b'): above=0; no match; dp[2]=max(0,1)=1; diagonal=0
- j=3 ('c'): above=0; no match; dp[3]=max(0,1)=1; diagonal=0

`dp = [0,1,1,1]`

**i=3 (new str1[2]='f'):** diagonal=0
- j=1 ('a'): above=1; no match; dp[1]=max(1,0)=1; diagonal=1
- j=2 ('b'): above=1; no match; dp[2]=max(1,1)=1; diagonal=1
- j=3 ('c'): above=1; no match; dp[3]=max(1,1)=1; diagonal=1

`dp = [0,1,1,1]`

**i=4 (new str1[3]='b'):** diagonal=0
- j=1 ('a'): above=1; no match; dp[1]=max(1,0)=1; diagonal=1
- j=2 ('b'): above=1; MATCH; dp[2]=diagonal+1=1+1=2; diagonal=1
- j=3 ('c'): above=1; no match; dp[3]=max(dp[3]=1,dp[2]=2)=2; diagonal=1

`dp = [0,1,2,2]`

Return `dp[3] = 2`.

**Result: 2** ✅

So for the quiz (with garbled options "2013", likely meaning `2, 0, 1, 3`), the correct answer is **2**.

### Complexity
- **Time:** O(n × m)
- **Space:** O(min(n,m)) — the array is sized to the shorter string, thanks to the length-based swap.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (1D DP) |
|---|---|---|
| Time Complexity | O(2^(n+m)) | O(n × m) |
| Space Complexity | O(n+m) | O(min(n,m)) |
| Practical for n,m up to 1000? | No — astronomically slow | Yes — at most 1,000,000 operations |

---

## 5. Why Swap to Use the Shorter String for the DP Array?

The DP array's size is always `m+1`, where `m` is the length of whichever string is treated as "str2" internally. By swapping so that `str2` always refers to the *shorter* of the two input strings, the array size — and thus the space complexity — is minimized to `O(min(n,m))` rather than being at the mercy of whichever string happened to be passed second. This is a nice, easy space optimization that costs nothing in correctness (LCS is symmetric — swapping the two strings doesn't change the answer).

---

## 6. Edge Cases to Consider

1. **No common characters at all** — e.g., `str1="abc", str2="xyz"` → LCS length `0`.
2. **One string is a subsequence of the other** — e.g., `str1="abc", str2="axbxc"` → LCS = "abc", length 3 (the entirety of the shorter string).
3. **Identical strings** — LCS is the entire string itself.
4. **Single-character strings** — e.g., `str1="a", str2="a"` → LCS length 1; `str1="a", str2="b"` → LCS length 0.
5. **Repeated characters** — e.g., `str1="aabba", str2="ababa"` — the algorithm correctly handles repeated characters since it's tracking positions, not just character identity.

---

## 7. Related Concepts / Follow-Ups

- **Longest Common Substring**: A DIFFERENT (and often confused) problem — requires the common sequence to be CONTIGUOUS in both strings, using a different DP recurrence (reset to 0 on mismatch, rather than carrying forward the max).
- **Edit Distance**: A closely related "DP on Strings" problem — instead of finding a common subsequence, it computes the minimum number of insertions/deletions/substitutions to transform one string into another, using a very similar 2D recurrence structure.
- **Longest Palindromic Subsequence**: Solvable by finding the LCS between a string and its own reverse — a clever application of this exact algorithm to a different-looking problem.
- **Shortest Common Supersequence**: Builds directly on LCS — the shortest string containing both input strings as subsequences has length `n + m - LCS(str1, str2)`.

---

## 8. Key Takeaways

- LCS is the foundational "DP on Strings" problem — its recurrence (match → extend diagonal; mismatch → max of skipping either string) appears, in some variation, throughout this entire problem family.
- Space-optimizing from a full 2D table to a 1D array requires careful handling of the "diagonal" dependency (`dp[i-1][j-1]`), since a naive 1D reuse would otherwise lose that value before it's needed.
- Swapping to always use the shorter string for the DP array's sizing is a simple, free space optimization.
- LCS underlies several other well-known string problems (Longest Palindromic Subsequence, Shortest Common Supersequence, Edit Distance) — mastering this recurrence pays dividends across the whole "DP on Strings" topic.
