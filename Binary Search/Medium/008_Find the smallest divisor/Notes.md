# Notes: Find the Smallest Divisor

## 1. Problem Recap

Given `nums` and a `limit`, find the smallest positive integer `divisor` such that:

```
sum of ceil(nums[i] / divisor) for all i  <=  limit
```

```
nums = [1, 2, 3, 4, 5], limit = 8, divisor = 3
  ceil(1/3)=1, ceil(2/3)=1, ceil(3/3)=1, ceil(4/3)=2, ceil(5/3)=2
  sum = 1+1+1+2+2 = 7 <= 8  ✓
```

### The Key Insight — Monotonicity
As the divisor **increases**, each individual `ceil(num/divisor)` term either decreases or stays the same, so the total sum is **monotonically non-increasing** as the divisor grows. This is exactly the property needed for **binary search on the answer**.

This problem is structurally almost identical to "Koko Eating Bananas" — just with the roles flipped (here we're minimizing the divisor to keep a sum under a limit, vs. minimizing speed to keep hours under a limit). Recognizing this pattern makes the solution much faster to derive.

---

## 2. Approach 1: Brute Force (Linear Scan Over Divisors)

### Idea
Try every candidate divisor starting from `1` upward, calculating the sum of ceiling divisions each time. The first divisor for which the sum is `<= limit` is the answer.

### Code Logic
```java
int maxVal = max(nums);
for (int divisor = 1; divisor <= maxVal; divisor++) {
    long sum = 0;
    for (int num : nums) {
        sum += (num + divisor - 1) / divisor;
    }
    if (sum <= limit) return divisor;
}
```

### Why the Search Range Is `[1, max(nums)]`
- Divisor `1` gives the largest possible sum (equal to the sum of all elements, no reduction at all).
- Divisor `max(nums)` guarantees every `ceil(num/divisor)` term equals `1` (since no element is larger than the divisor), giving the smallest possible sum (`= n`, the count of elements). Going beyond `max(nums)` provides no further benefit.

### Dry Run
`nums = [1, 2, 3, 4, 5]`, `limit = 8`

| divisor | ceil(1/d) | ceil(2/d) | ceil(3/d) | ceil(4/d) | ceil(5/d) | sum | <=8? |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 2 | 3 | 4 | 5 | 15 | No |
| 2 | 1 | 1 | 2 | 2 | 3 | 9 | No |
| 3 | 1 | 1 | 1 | 2 | 2 | 7 | **Yes -> return 3** |

Result: **3** ✅

### Complexity
- **Time:** O(max(nums) × n) — for each of up to `max(nums)` candidate divisors, an O(n) pass over the array.
- **Space:** O(1)

### Why It's Not Optimal
Given `nums[i]` can be up to `10^6` and the array up to `5 * 10^4` elements, trying every divisor one by one is far too slow for large inputs — binary search on the answer is essential here.

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search over the candidate divisor range `[1, max(nums)]`. For a candidate `mid`, compute the sum of ceiling divisions:

- If `sum <= limit`, `mid` is a valid divisor — but a smaller one might also work, so search left: `right = mid - 1` (implemented here as `right = mid`, keeping `mid` in the search space since it's the best-known candidate).
- If `sum > limit`, `mid` is too small (produces too large a sum) — search right (larger divisors): `left = mid + 1`.

### Code Logic
```java
int left = 1, right = max(nums);

while (left < right) {
    int mid = left + (right - left) / 2;

    long sum = 0;
    for (int num : nums) {
        sum += (num + mid - 1) / mid;
        if (sum > limit) break;   // early exit optimization
    }

    if (sum <= limit) {
        right = mid;
    } else {
        left = mid + 1;
    }
}

return left;
```

### Dry Run 1
`nums = [1, 2, 3, 4, 5]`, `limit = 8`

Search range: `[1, 5]` (max = 5)

| Step | left | right | mid | sum computation | sum | <=8? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 5 | 3 | ceil(1/3)+ceil(2/3)+ceil(3/3)+ceil(4/3)+ceil(5/3) = 1+1+1+2+2 | 7 | Yes | right = 3 |
| 2 | 1 | 3 | 2 | ceil(1/2)+ceil(2/2)+ceil(3/2)+ceil(4/2)+ceil(5/2) = 1+1+2+2+3 | 9 | No | left = 3 |
| — | 3 | 3 | — | — | — | — | **left == right -> return 3** |

Result: **3** ✅ (matches expected output)

### Dry Run 2
`nums = [8, 4, 2, 3]`, `limit = 10`

Search range: `[1, 8]` (max = 8)

| Step | left | right | mid | sum computation | sum | <=10? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 8 | 4 | ceil(8/4)+ceil(4/4)+ceil(2/4)+ceil(3/4) = 2+1+1+1 | 5 | Yes | right = 4 |
| 2 | 1 | 4 | 2 | ceil(8/2)+ceil(4/2)+ceil(2/2)+ceil(3/2) = 4+2+1+2 | 9 | Yes | right = 2 |
| 3 | 1 | 2 | 1 | ceil(8/1)+ceil(4/1)+ceil(2/1)+ceil(3/1) = 8+4+2+3 | 17 | No | left = 2 |
| — | 2 | 2 | — | — | — | — | **left == right -> return 2** |

Result: **2** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [8, 4, 2, 3]`, `limit = 4`

Search range: `[1, 8]` (max = 8)

| Step | left | right | mid | sum computation | sum | <=4? | Action |
|---|---|---|---|---|---|---|---|
| 1 | 1 | 8 | 4 | ceil(8/4)+ceil(4/4)+ceil(2/4)+ceil(3/4) = 2+1+1+1 | 5 | No | left = 5 |
| 2 | 5 | 8 | 6 | ceil(8/6)+ceil(4/6)+ceil(2/6)+ceil(3/6) = 2+1+1+1 | 5 | No | left = 7 |
| 3 | 7 | 8 | 7 | ceil(8/7)+ceil(4/7)+ceil(2/7)+ceil(3/7) = 2+1+1+1 | 5 | No | left = 8 |
| — | 8 | 8 | — | — | — | — | **left == right -> return 8** |

**Result: 8** ✅

So for the quiz options `2, 8, 4, 3`, the correct answer is **8**.

### Complexity
- **Time:** O(n log(max(nums)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(max(nums) × n) | O(n log(max(nums))) |
| Space Complexity | O(1) | O(1) |
| Scales for nums[i] up to 10^6, n up to 5*10^4? | No - potentially ~5*10^10 ops worst case | Yes - roughly n * log(10^6) ≈ 5*10^4 * 20 = 10^6 ops |

---

## 5. Edge Cases to Consider

1. **limit equals sum of all elements with divisor 1** — the smallest possible divisor might legitimately be `1` if the array's raw sum already satisfies the limit.
2. **limit equals n (the minimum allowed by constraints)** — this forces the divisor to be at least `max(nums)`, since every `ceil(num/divisor)` must equal exactly `1` for the sum to equal `n`.
3. **Single-element array** — e.g., `nums = [7], limit = 2` → need `ceil(7/divisor) <= 2`, so divisor `>= 4` (since `ceil(7/4)=2`, `ceil(7/3)=3`). Answer: `4`.
4. **All elements identical** — e.g., `nums = [5,5,5,5], limit = 4` → divisor must be `>= 5` (forces each term to `1`), giving sum `= 4`.
5. **Large values near 10^6 with many elements** — verifies the `long` type is used for the running sum, since summing many large ceiling-divided values could otherwise overflow a plain `int` in extreme cases.

---

## 6. Related Concepts / Follow-Ups

- **Koko Eating Bananas**: Nearly identical structure — both use `ceil(x / candidate)` summed over an array, compared against a limit, with a monotonic relationship as the candidate value changes. The only real difference is that Koko minimizes *speed* while this problem minimizes *divisor* — same shape, different variable name.
- **Capacity to Ship Packages Within D Days**: Another close relative in the "binary search on the answer with a summing feasibility check" family.
- **Find Nth Root / Find Square Root**: Simpler binary-search-on-the-answer problems that establish the same core pattern before this problem adds the "sum over an array" twist.

---

## 7. Key Takeaways

- This problem is essentially a twin of "Koko Eating Bananas" — recognizing the shared structure (`ceil(x / candidate)` summed and compared to a limit, monotonic in candidate) makes both problems solvable with the same template.
- `(num + mid - 1) / mid` is the standard trick for ceiling division using integer arithmetic only.
- The early-exit `if (sum > limit) break;` inside the summing loop is a practical optimization, though it doesn't change the theoretical worst-case complexity.
- Always use a wider type (`long`) for the running sum when summing many potentially large values, to avoid overflow — especially given constraints allowing up to `5 * 10^4` elements each up to `10^6`.
