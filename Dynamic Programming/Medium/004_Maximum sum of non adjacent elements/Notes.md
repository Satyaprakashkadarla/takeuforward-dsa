# Notes: Maximum Sum of Non-Adjacent Elements

## 1. Problem Recap

Given an array, pick a subset of elements (no two adjacent in the original array) to **maximize the sum**.

```
nums = [1, 7, 16, 8]
Best: pick 1 and 16 (not adjacent) -> sum = 17
(Picking 7 and 8 gives 15; picking just 16 gives 16; 17 is the best.)
```

### The Key Insight — House Robber
This is the classic **"House Robber"** problem, just with generic array elements instead of "houses" with cash. At each index `i`, you face a binary choice:

- **Skip** `nums[i]`: the best sum is whatever the best sum was up through index `i-1` (`dp[i-1]`).
- **Take** `nums[i]`: since you can't also take `nums[i-1]` (adjacency constraint), the best sum is `nums[i] + dp[i-2]`.

```
dp[i] = max( dp[i-1], nums[i] + dp[i-2] )
```

This is the same "look back 1 or 2 states" DP shape as Climbing Stairs and Frog Jump — just using `max` here instead of `sum` (Climbing Stairs) or `min` (Frog Jump).

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Directly recurse using the skip/take choice at each index.

### Code Logic
```java
private int solve(int[] nums, int i) {
    if (i < 0) return 0;
    if (i == 0) return nums[0];
    int skip = solve(nums, i - 1);
    int take = nums[i] + solve(nums, i - 2);
    return Math.max(skip, take);
}
```

### Dry Run
`nums = [1, 7, 16, 8]`, finding `solve(3)`

```
solve(3) = max( solve(2), 8 + solve(1) )
solve(2) = max( solve(1), 16 + solve(0) )
solve(1) = max( solve(0), 7 + solve(-1) ) = max(1, 7+0) = 7
solve(0) = 1
solve(-1) = 0

solve(2) = max(7, 16+1) = max(7, 17) = 17
solve(3) = max(17, 8+7) = max(17, 15) = 17
```

Result: **17** ✅ (matches expected output)

### Complexity
- **Time:** O(2^n) — repeated overlapping subproblems, no caching.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (Iterative DP, O(1) Space)

### Idea
Compute `dp[i]` iteratively using two rolling variables, since each value only depends on the previous two.

### Code Logic
```java
int prev2 = 0, prev1 = nums[0];
for (int i = 1; i < n; i++) {
    int skip = prev1;
    int take = nums[i] + prev2;
    int current = Math.max(skip, take);
    prev2 = prev1;
    prev1 = current;
}
return prev1;
```

### Dry Run 1
`nums = [1, 2, 4]`

| i | prev2 | prev1 | skip | take | current |
|---|---|---|---|---|---|
| start | 0 | 1 | — | — | — |
| 1 | 0 | 1 | 1 | 2+0=2 | 2 |
| 2 | 1 | 2 | 2 | 4+1=5 | 5 |

Return `prev1 = 5`.

Result: **5** ✅ (matches expected output)

### Dry Run 2
`nums = [2, 1, 4, 9]`

| i | prev2 | prev1 | skip | take | current |
|---|---|---|---|---|---|
| start | 0 | 2 | — | — | — |
| 1 | 0 | 2 | 2 | 1+0=1 | 2 |
| 2 | 2 | 2 | 2 | 4+2=6 | 6 |
| 3 | 2 | 6 | 6 | 9+2=11 | 11 |

Return `prev1 = 11`.

Result: **11** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [1, 7, 16, 8]`

| i | prev2 | prev1 | skip | take | current |
|---|---|---|---|---|---|
| start | 0 | 1 | — | — | — |
| 1 | 0 | 1 | 1 | 7+0=7 | 7 |
| 2 | 1 | 7 | 7 | 16+1=17 | 17 |
| 3 | 7 | 17 | 17 | 8+7=15 | 17 |

Return `prev1 = 17`.

**Result: 17** ✅

So for the quiz options `17, 24, 23, 15`, the correct answer is **17**.

### Complexity
- **Time:** O(n)
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(2^n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical for n up to 10^5? | No — far too slow | Yes — trivially fast |

---

## 5. Edge Cases to Consider

1. **n = 1** — must take the only element; answer is `nums[0]`. Handled directly by the base case check.
2. **n = 2** — pick whichever of the two elements is larger (can't take both since they're adjacent).
3. **All elements equal** — e.g., `nums = [5,5,5,5]` → best is to take alternating elements: `5+5=10` (positions 0,2 or 1,3).
4. **All zeros** — answer is `0` regardless of which elements are chosen.
5. **Single large element surrounded by small ones** — e.g., `nums = [1,100,1]` → best is to just take the middle element alone (`100`), skipping both neighbors.
6. **Strictly increasing values** — e.g., `nums = [1,2,3,4,5]` → the DP correctly balances between taking every other large-ish value vs. skipping small ones adjacent to bigger ones.

---

## 6. Related Concepts / Follow-Ups

- **House Robber**: The exact same problem under a different name/story (houses with cash, can't rob two adjacent houses) — recognizing this equivalence means the solution transfers directly.
- **House Robber II**: A circular variant where the first and last elements are also considered "adjacent" (since houses are arranged in a circle) — solved by running this same DP twice (once excluding the first element, once excluding the last) and taking the max of the two results.
- **Climbing Stairs / Frog Jump**: Both share the same "look back 1 or 2 states" DP shape, just with different combining operations (`sum` for counting paths, `min` for minimizing cost, `max` here for maximizing sum).

---

## 7. Key Takeaways

- This is the House Robber pattern: at each index, choose to skip (carry forward the best sum so far) or take (add the current value to the best sum from 2 positions back, since the immediate neighbor must be skipped).
- The recurrence `dp[i] = max(dp[i-1], nums[i]+dp[i-2])` follows the same "look back 1 or 2" DP shape seen in Climbing Stairs and Frog Jump — recognizing this recurring pattern across seemingly different problems is a major time-saver.
- Space can be optimized from O(n) down to O(1) using two rolling variables, since each state only ever depends on the previous two.
- This problem is directly reusable for the "House Robber" and "House Robber II" problems, which are essentially the same core logic with different framing (and a circular-array twist for the second variant).
