# Notes: Word Wrap

## 1. Problem Recap

Break a sequence of words into lines of width `k`. The cost of a line is the square of its unused space; the **last line is free**. Minimize the total cost.

```
arr = [3, 2, 2, 5], k = 6

Layout A:  [3]  [2 2]  [5]
           extra 3, extra 1, last line free  ->  9 + 1 + 0 = 10   (optimal)

Layout B:  [3 2]  [2]  [5]
           extra 0, extra 4, last line free  ->  0 + 16 + 0 = 16
```

### How Line Width Is Computed
A line holding words `i..j` uses

```
used = arr[i] + ... + arr[j] + (j - i)
```

characters: the word lengths plus one space between each consecutive pair (`j - i` spaces). The line is valid only if `used <= k`, and its extra spaces are `k - used`.

### Why Greedy Fails
Packing as many words as possible into each line (Layout B above) minimizes the number of lines but is **not** optimal. Because the cost is squared, one very empty line (4 extra spaces, cost 16) hurts much more than two moderately empty lines (3 and 1 extra, cost 9 + 1). The optimum needs a global view, which is what DP provides.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
`solve(i)` returns the minimum cost to lay out words `i..n-1`, starting a new line at word `i`. Try every possible end word `j` that still fits, pay that line's cost, and recurse on the rest.

### Code Logic
```java
private int solve(int[] arr, int k, int i) {
    int n = arr.length;
    if (i == n) return 0;

    int best = Integer.MAX_VALUE;
    int lineLength = 0;

    for (int j = i; j < n; j++) {
        lineLength += arr[j];
        int used = lineLength + (j - i);
        if (used > k) break;

        int cost = (j == n - 1) ? 0 : (k - used) * (k - used);
        best = Math.min(best, cost + solve(arr, k, j + 1));
    }
    return best;
}
```

### Dry Run
`arr = [3, 2, 2, 5]`, `k = 6`

```
solve(0): line starts at word 0
  j=0: used=3        cost 9  + solve(1)
  j=1: used=3+2+1=6  cost 0  + solve(2)
  j=2: used=3+2+2+2=9 > 6 -> break

solve(1): line starts at word 1
  j=1: used=2        cost 16 + solve(2)
  j=2: used=2+2+1=5  cost 1  + solve(3)
  j=3: used=2+2+5+2=11 > 6 -> break

solve(2): line starts at word 2
  j=2: used=2        cost 16 + solve(3)
  j=3: used=2+5+1=8 > 6 -> break

solve(3): line starts at word 3
  j=3: used=5, last line, cost 0 + solve(4) = 0

solve(2) = 16 + 0 = 16
solve(1) = min(16 + 16, 1 + 0) = 1
solve(0) = min(9 + 1, 0 + 16) = 10
```

Result: **10** ✅

Notice `solve(2)` and `solve(3)` are each called from several places. With more words, such repeats multiply exponentially.

### Complexity
- **Time:** O(2^n). Each of the `n - 1` gaps between words can be a break or not, and nothing is cached.
- **Space:** O(n) recursion stack.

---

## 3. Approach 2: Optimal (Bottom-Up DP)

### Idea
The suffix results `solve(i)` depend only on `i`, so store them. Fill `dp[i]` from `i = n - 1` down to `0`, so every `dp[j + 1]` needed is already computed.

```
dp[n] = 0
dp[i] = min over fitting j of ( cost(i, j) + dp[j + 1] )
```

### Code Logic
```java
int[] dp = new int[n + 1];
dp[n] = 0;

for (int i = n - 1; i >= 0; i--) {
    int lineLength = 0;
    dp[i] = Integer.MAX_VALUE;

    for (int j = i; j < n; j++) {
        lineLength += arr[j];
        int used = lineLength + (j - i);
        if (used > k) break;

        int cost = (j == n - 1) ? 0 : (k - used) * (k - used);
        dp[i] = Math.min(dp[i], cost + dp[j + 1]);
    }
}
return dp[0];
```

### The "Last Line Is Free" Rule
`j == n - 1` means this line contains the final word, so it is the last line and costs 0. It is checked by position in the text, not by anything about the line itself, which is why it is a single condition.

### Dry Run 1: Example 1
`arr = [3, 2, 2, 5]`, `k = 6`, `n = 4`

| i | j | lineLength | used | Last line? | cost | cost + dp[j+1] | dp[i] so far |
|---|---|---|---|---|---|---|---|
| 4 | | | | | | | dp[4] = 0 |
| 3 | 3 | 5 | 5 | yes | 0 | 0 + dp[4] = 0 | 0 |
| 2 | 2 | 2 | 2 | no | 16 | 16 + dp[3] = 16 | 16 |
| 2 | 3 | 7 | 8 | | | break (8 > 6) | **dp[2] = 16** |
| 1 | 1 | 2 | 2 | no | 16 | 16 + dp[2] = 32 | 32 |
| 1 | 2 | 4 | 5 | no | 1 | 1 + dp[3] = 1 | 1 |
| 1 | 3 | 9 | 11 | | | break | **dp[1] = 1** |
| 0 | 0 | 3 | 3 | no | 9 | 9 + dp[1] = 10 | 10 |
| 0 | 1 | 5 | 6 | no | 0 | 0 + dp[2] = 16 | 10 |
| 0 | 2 | 7 | 9 | | | break | **dp[0] = 10** |

Result: **10** ✅ (matches expected output)

### Dry Run 2: Example 2
`arr = [3, 2, 2]`, `k = 4`, `n = 3`

| i | j | lineLength | used | Last line? | cost | cost + dp[j+1] | dp[i] so far |
|---|---|---|---|---|---|---|---|
| 3 | | | | | | | dp[3] = 0 |
| 2 | 2 | 2 | 2 | yes | 0 | 0 + dp[3] = 0 | **dp[2] = 0** |
| 1 | 1 | 2 | 2 | no | 4 | 4 + dp[2] = 4 | 4 |
| 1 | 2 | 4 | 5 | | | break (5 > 4) | **dp[1] = 4** |
| 0 | 0 | 3 | 3 | no | 1 | 1 + dp[1] = 5 | 5 |
| 0 | 1 | 5 | 6 | | | break | **dp[0] = 5** |

Result: **5** ✅ (matches expected output)

### Complexity
- **Time:** For each start word `i`, the inner loop only runs while the line fits, so it takes at most `w` steps where `w` is the maximum number of words that fit on a line. Total O(n * w), and `w <= n`, so at most O(n²). For `n = 500` that is at most 250,000 steps.
- **Space:** O(n) for the `dp` array.

---

## 4. Comparing the Approaches

| Aspect | Brute Force | Optimal (DP) |
|---|---|---|
| Time | O(2^n) | O(n²) worst case |
| Space | O(n) | O(n) |
| Practical for n = 500? | No | Yes |

---

## 5. Implementation Notes on the Provided Code

- **Direction of the DP:** filling from the end means "`dp[i]` is the cost of everything from word `i` onward". This makes the last-line rule natural, because the base case `dp[n] = 0` sits right after the final word.
- **Why `break` is safe:** word lengths are positive, so `used` only increases as `j` grows. Once a line is too long, every longer line is too.
- **`Integer.MAX_VALUE` as the starting value:** it is only used as a "nothing found yet" marker and never added to. The values added are `cost + dp[j + 1]`, and `dp[j + 1]` is always a real number because every word fits alone (`max(arr[i]) <= k`), so at least the one-word line is always available.
- **`int` is large enough:** the cost of the optimal layout stays well inside the `int` range. The largest optimal cost I could construct under the constraints was 499,000,000, from 500 words of length 1000 with `k = 2000`, where each word has to sit alone with 1000 extra spaces.
- **The cost is based on spaces, not just word lengths:** the `(j - i)` term is easy to forget and would make lines look shorter than they are.

---

## 6. Edge Cases to Consider

1. **Single word:** it is the last line, so the cost is 0.
2. **Everything fits on one line:** the one line is the last line, so the total cost is 0.
3. **Every word needs its own line** (for example, each word is longer than `k / 2`): the cost is the sum of `(k - arr[i])²` for all words except the last.
4. **A word exactly as long as `k`:** it fills its line with 0 extra spaces, so that line costs 0.
5. **Lines that fit exactly:** `used == k` gives cost 0 and is allowed (`used > k` is the only invalid case).
6. **Last line is long:** since it is free, the optimal layout often pushes more words into the last line.

---

## 7. Related Concepts / Follow-Ups

- **Text Justification** (LeetCode 68): the actual formatting problem, where you build the output lines instead of minimizing a cost.
- **Partition DP in general** (Palindrome Partitioning II, Minimum Cost to Cut a Stick, Matrix Chain Multiplication): the same idea of choosing where to split a sequence and combining the cost of a piece with the best cost of the rest.
- **Knuth-Plass line breaking:** the algorithm used by TeX, a more sophisticated version of this same DP.
- **Optimization:** the cost function here is convex, so the DP can be sped up beyond O(n²) with techniques like the monotone-queue optimization. This is not needed for `n <= 500`.

---

## 8. Key Takeaways

- A partition DP asks: "where does the current piece end?" Try every valid end, pay for the piece, and add the best cost of the remainder.
- Squared costs punish unevenness, so greedy packing is not optimal and a DP is required.
- Special rules (here, the free last line) are best expressed by position (`j == n - 1`) inside the DP rather than by post-processing.
- Break out of the inner loop as soon as the line stops fitting; it turns a potentially O(n³) solution into O(n * w).
