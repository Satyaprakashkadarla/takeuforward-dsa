# Notes: Subset Sum Equals to Target

## 1. Problem Recap

Given an array and a target sum, determine whether **any subset** of the array's elements sums exactly to the target.

```
arr = [7, 54, 4, 12, 15, 5], target = 9
Subset {4, 5} sums to 9 -> True
```

### Introducing "DP on Subsequences"
This is the foundational problem in the **DP on Subsequences** family — a huge category of problems (subset sum, partition equal subset sum, count subsets with a given sum, minimum subset sum difference, target sum, coin change, and more) that all share the same core recurrence: at each element, decide whether to **include** it or **exclude** it from the subset being built, tracking a running sum (or count, or difference) as the state.

---

## 2. Approach 1: Brute Force (Include/Exclude Recursion)

### Idea
At each index, recursively try both excluding and including the current element (if it fits within the remaining target), and check if either path leads to a valid subset.

### Code Logic
```java
private boolean solve(int[] arr, int i, int remaining) {
    if (remaining == 0) return true;
    if (i < 0) return false;
    boolean exclude = solve(arr, i-1, remaining);
    boolean include = (arr[i] <= remaining) && solve(arr, i-1, remaining - arr[i]);
    return exclude || include;
}
```

### Dry Run
`arr = [1, 2, 7, 3]`, `target = 6`, finding `solve(3, 6)` (i=3 is index of value 3)

```
solve(3,6) [arr[3]=3]:
  exclude: solve(2,6)
  include: 3<=6, solve(2,3)

solve(2,6) [arr[2]=7]: 7>6, so include impossible
  exclude: solve(1,6)
  = solve(1,6)

solve(1,6) [arr[1]=2]:
  exclude: solve(0,6)
  include: 2<=6, solve(0,4)

solve(0,6) [arr[0]=1]:
  exclude: solve(-1,6) = false (i<0, remaining≠0)
  include: 1<=6, solve(-1,5) = false
  = false

solve(0,4) [arr[0]=1]:
  exclude: solve(-1,4) = false
  include: 1<=4, solve(-1,3) = false
  = false

solve(1,6) = false || false = false
solve(2,6) = false (from above)

solve(2,3) [arr[2]=7]: 7>3, include impossible
  exclude: solve(1,3)

solve(1,3) [arr[1]=2]:
  exclude: solve(0,3)
  include: 2<=3, solve(0,1)

solve(0,3) [arr[0]=1]:
  exclude: solve(-1,3)=false
  include: 1<=3, solve(-1,2)=false
  = false

solve(0,1) [arr[0]=1]:
  exclude: solve(-1,1)=false
  include: 1<=1, solve(-1,0) = TRUE (remaining==0!)
  = true

solve(1,3) = false || true = true
solve(2,3) = true
solve(3,6) = false || true = true
```

Result: **true** ✅ (matches expected output — subset {1,2,3} sums to 6)

### Complexity
- **Time:** O(2^n) — exponential branching, massively overlapping (i, remaining) subproblems.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (1D DP, Space-Optimized 0/1 Knapsack Style)

### Idea
Track `dp[sum]` = "is `sum` achievable using some subset of the elements processed so far?" Process elements one at a time, and for each, update the `dp` array to reflect that this element is now available.

### The Critical Detail: Why Traverse Backwards?
```java
for (int num : arr) {
    for (int sum = target; sum >= num; sum--) {
        dp[sum] = dp[sum] || dp[sum - num];
    }
}
```

If we traversed **forwards** (sum increasing from `num` to `target`), we'd risk using the SAME element multiple times within a single pass. For example, if `num = 3` and we're computing `dp[6]`, a forward pass might have already updated `dp[3]` to `true` earlier in this same pass (using this very `num=3`), and then `dp[6] = dp[6] || dp[3]` would incorrectly imply "using 3 twice to reach 6."

By traversing **backwards** (from `target` down to `num`), we guarantee that `dp[sum - num]` still reflects the state from *before* this element was considered in this pass — correctly modeling "each element can be used at most once" (the defining trait of the 0/1 knapsack pattern, as opposed to unbounded knapsack where reuse is allowed).

### Dry Run 1
`arr = [1, 2, 7, 3]`, `target = 6`

`dp = [T, F, F, F, F, F, F]` (indices 0-6, only dp[0]=true initially)

**Process num=1:** scan sum=6 down to 1
- dp[1] = dp[1] || dp[0] = F || T = T
- (sums 2-6 unaffected since dp[1..5] were all False before dp[0] gets used, and only dp[1] changes here since dp[sum-1] for sum=2 is dp[1] which was False at the time we check it — wait let's be careful, we process sum descending: sum=6:dp[5]=F; sum=5:dp[4]=F;sum=4:dp[3]=F;sum=3:dp[2]=F;sum=2:dp[1]=F(not yet updated, since we haven't reached sum=1 yet);sum=1:dp[0]=T→dp[1]=T)

After num=1: `dp = [T, T, F, F, F, F, F]`

**Process num=2:** scan sum=6 down to 2
- sum=6: dp[6]||dp[4]=F||F=F
- sum=5: dp[5]||dp[3]=F||F=F
- sum=4: dp[4]||dp[2]=F||F=F
- sum=3: dp[3]||dp[1]=F||T=T
- sum=2: dp[2]||dp[0]=F||T=T

After num=2: `dp = [T, T, T, T, F, F, F]`

**Process num=7:** 7 > target(6), inner loop doesn't execute at all (sum starts at 6 < 7)

After num=7: `dp` unchanged: `[T, T, T, T, F, F, F]`

**Process num=3:** scan sum=6 down to 3
- sum=6: dp[6]||dp[3]=F||T=T
- sum=5: dp[5]||dp[2]=F||T=T
- sum=4: dp[4]||dp[1]=F||T=T
- sum=3: dp[3]||dp[0]=T||T=T

After num=3: `dp = [T, T, T, T, T, T, T]`

Return `dp[6] = T`.

Result: **True** ✅ (matches expected output)

### Dry Run 2
`arr = [2, 3, 5]`, `target = 6`

`dp = [T, F, F, F, F, F, F]`

**num=2:** sum=6..2
- sum=2: dp[2]||dp[0]=T. Others stay F (dp[0..1] unaffected at higher sums since those indices are still False)

`dp = [T, F, T, F, F, F, F]`

**num=3:** sum=6..3
- sum=6: dp[6]||dp[3]=F||F=F
- sum=5: dp[5]||dp[2]=F||T=T
- sum=4: dp[4]||dp[1]=F||F=F
- sum=3: dp[3]||dp[0]=F||T=T

`dp = [T, F, T, T, F, T, F]`

**num=5:** sum=6..5
- sum=6: dp[6]||dp[1]=F||F=F
- sum=5: dp[5]||dp[0]=T||T=T

`dp = [T, F, T, T, F, T, F]`

Return `dp[6] = F`.

Result: **False** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`arr = [7, 54, 4, 12, 15, 5]`, `target = 9`

`dp = [T, F, F, F, F, F, F, F, F, F]` (indices 0-9)

**num=7:** sum=9..7
- sum=9: dp[9]||dp[2]=F
- sum=8: dp[8]||dp[1]=F
- sum=7: dp[7]||dp[0]=F||T=T

`dp[7]=T`, rest unchanged.

**num=54:** 54>9, loop doesn't execute at all.

**num=4:** sum=9..4
- sum=9: dp[9]||dp[5]=F||F=F
- sum=8: dp[8]||dp[4]=F||F=F
- sum=7: dp[7]||dp[3]=T||F=T (unchanged)
- sum=6: dp[6]||dp[2]=F||F=F
- sum=5: dp[5]||dp[1]=F||F=F
- sum=4: dp[4]||dp[0]=F||T=T

`dp[4]=T` now too.

**num=12:** 12>9, loop doesn't execute.

**num=15:** 15>9, loop doesn't execute.

**num=5:** sum=9..5
- sum=9: dp[9]||dp[4]=F||T=**T**
- sum=8: dp[8]||dp[3]=F||F=F
- sum=7: dp[7]||dp[2]=T||F=T (unchanged)
- sum=6: dp[6]||dp[1]=F||F=F
- sum=5: dp[5]||dp[0]=F||T=T

`dp[9] = T` ✅ (achieved via 4+5)

Return `dp[9] = True`.

**Result: True** ✅

So for the quiz options `True, False`, the correct answer is **True**.

### Complexity
- **Time:** O(n × target)
- **Space:** O(target)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (1D DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n × target) |
| Space Complexity | O(n) | O(target) |
| Practical for n=100, target up to 5000? | No — astronomically slow | Yes — at most 500,000 operations |

---

## 5. Edge Cases to Consider

1. **target = 0** — always achievable via the empty subset; `dp[0] = true` from initialization, regardless of the array's contents.
2. **A single element equals target exactly** — trivially achievable.
3. **All elements larger than target** — no subset (other than the empty one) can possibly reach a positive target; `dp[target]` stays `false` unless `target=0`.
4. **Duplicate values in the array** — handled naturally; the backward-traversal trick doesn't care about duplicate *values*, only about not reusing the same *array position* twice within one pass.
5. **target larger than the sum of all elements** — impossible to achieve; `dp[target]` correctly stays `false` (array size is `target+1`, so this case is naturally handled as long as target is within the array's allocated bounds).

---

## 6. Related Concepts / Follow-Ups

- **Partition Equal Subset Sum**: A direct application — check if the array can be split into two subsets with equal sums, which reduces to "is `totalSum/2` achievable as a subset sum?" (using this exact algorithm).
- **Count Subsets with Given Sum**: A close variant that counts *how many* subsets achieve the target, rather than just checking existence — replace the boolean `dp` array with an integer count array, and use `+=` instead of `||`.
- **Minimum Subset Sum Difference**: Uses the full `dp` array (not just `dp[target]`) to find the achievable sum closest to `totalSum/2`, minimizing the difference between the two partition sums.
- **0/1 Knapsack**: The general pattern this problem is a special case of — instead of a boolean "achievable or not," full knapsack tracks a maximized value subject to a weight constraint, using the same backward-traversal space optimization.
- **Coin Change**: A related but distinct family (unbounded knapsack, since coins can be reused) — importantly, that variant traverses the sum array **forwards**, not backwards, since reuse is allowed there.

---

## 7. Key Takeaways

- Subset Sum is the foundational "DP on Subsequences" problem — the include/exclude recursive structure it introduces underlies a huge family of related problems.
- The 1D space-optimized DP array, updated by traversing sums **backwards** for each element, is the standard 0/1 knapsack technique — crucial for ensuring each element is used at most once per subset.
- Contrast this backward traversal with problems like Coin Change (unbounded knapsack), which traverse **forwards** specifically because element reuse IS allowed there — recognizing which direction to traverse is one of the most important details to get right in this entire family of DP problems.
- This algorithm directly generalizes to counting subsets (replace boolean OR with integer addition) and to related partition/difference problems, making it one of the most reusable DP templates to master.
