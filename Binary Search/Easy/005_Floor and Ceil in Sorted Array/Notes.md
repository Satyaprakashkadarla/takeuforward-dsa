# Notes: Floor and Ceil in Sorted Array

## 1. Problem Recap

Given a **sorted** array `nums` and an integer `x`, find:

- **Floor(x)** = the **largest** element in `nums` that is `<= x`.
- **Ceil(x)** = the **smallest** element in `nums` that is `>= x`.

If either doesn't exist (e.g., `x` is smaller than every element, so no floor exists; or `x` is larger than every element, so no ceil exists), return `-1` for that value.

### Note on Terminology
Don't confuse this with **lower bound** / **upper bound** — those problems return an **index**, while this problem returns the **value** (element) itself. However, the underlying binary search technique is closely related.

- `Ceil(x)` is essentially `nums[lowerBound(x)]` (the value at the lower-bound index, if that index is valid).
- `Floor(x)` is the element just before the ceiling's position (if the exact value isn't found) or the exact value itself if `x` is present.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
A single left-to-right scan can find both floor and ceil at once:

- **Floor**: Every time we see an element `<= x`, update `floor` to that element. Since the array is sorted ascending, by the time we finish scanning (or find something `> x`), the *last* element we recorded is the largest one `<= x`.
- **Ceil**: The moment we see the *first* element `>= x`, that's our ceiling — record it once and never overwrite it (since anything after would be even larger).

### Code Logic
```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] <= x) {
        floor = nums[i];
    }
    if (nums[i] >= x && ceil == -1) {
        ceil = nums[i];
    }
}
```

### Dry Run
`nums = [3, 4, 4, 7, 8, 10]`, `x = 5`

| i | nums[i] | nums[i] <= 5? (floor update) | nums[i] >= 5? (ceil update, first time only) |
|---|---|---|---|
| 0 | 3 | floor = 3 | No |
| 1 | 4 | floor = 4 | No |
| 2 | 4 | floor = 4 | No |
| 3 | 7 | No | ceil = 7 (first time) |
| 4 | 8 | No | already set |
| 5 | 10 | No | already set |

Result: **floor = 4, ceil = 7** ✅

### Complexity
- **Time:** O(n) — one pass through the array.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Single-Pass Binary Search)

### Idea
Run a standard binary search over `[low, high] = [0, n-1]`. At each step, `nums[mid]` tells us something useful regardless of whether it equals `x`:

- **`nums[mid] == x`**: We found `x` exactly in the array. In this case, `x` is *both* its own floor and ceiling — return `{x, x}` immediately. No need to keep searching.
- **`nums[mid] < x`**: `nums[mid]` is a valid **floor candidate** (it satisfies `<= x`). We record it, but there might be an even larger valid floor further right (closer to `x`), so we continue searching the right half: `low = mid + 1`.
- **`nums[mid] > x`**: `nums[mid]` is a valid **ceiling candidate** (it satisfies `>= x`). We record it, but there might be a smaller valid ceiling further left (closer to `x`), so we continue searching the left half: `high = mid - 1`.

Each time we update `floor` or `ceil`, we're recording a *better* (tighter) candidate than any previous one on that side, because we're always moving the search window closer to `x`. By the time `low > high`, whatever values are stored in `floor` and `ceil` are guaranteed to be optimal.

### Code Logic
```java
int floor = -1, ceil = -1;
int low = 0, high = nums.length - 1;

while (low <= high) {
    int mid = low + (high - low) / 2;

    if (nums[mid] == x) {
        return new int[]{x, x};
    } else if (nums[mid] < x) {
        floor = nums[mid];
        low = mid + 1;
    } else {
        ceil = nums[mid];
        high = mid - 1;
    }
}

return new int[]{floor, ceil};
```

### Dry Run 1
`nums = [3, 4, 4, 7, 8, 10]`, `x = 5`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 4 | 4 < 5 | floor = 4, low = 3 |
| 2 | 3 | 5 | 4 | 8 | 8 > 5 | ceil = 8, high = 3 |
| 3 | 3 | 3 | 3 | 7 | 7 > 5 | ceil = 7, high = 2 |
| — | 3 | 2 | — | — | low > high | **loop ends** |

Result: **{floor=4, ceil=7}** ✅ (matches expected output)

Notice how `ceil` was first set to `8`, then improved to `7` once the search narrowed further — this is the "tighter candidate" behavior in action.

### Dry Run 2 — Exact Match
`nums = [3, 4, 4, 7, 8, 10]`, `x = 8`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 4 | 4 < 8 | floor = 4, low = 3 |
| 2 | 3 | 5 | 4 | 8 | 8 == 8 | **return {8, 8}** |

Result: **{8, 8}** ✅ (matches expected output)

### Dry Run 3 — No Floor Exists
`nums = [2, 4, 6, 8, 10, 12, 14]`, `x = 1`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 8 | 8 > 1 | ceil = 8, high = 2 |
| 2 | 0 | 2 | 1 | 4 | 4 > 1 | ceil = 4, high = 0 |
| 3 | 0 | 0 | 0 | 2 | 2 > 1 | ceil = 2, high = -1 |
| — | 0 | -1 | — | — | low > high | **loop ends** |

Result: **{floor=-1, ceil=2}** ✅ (matches expected output `[-1, 2]`) — `floor` was never updated since no element is ever `< 1` (or `<= 1`), so it stays at its initial value `-1`.

### Dry Run 4 — No Ceil Exists
`nums = [2, 4, 6, 8, 10, 12, 14]`, `x = 20`

| Step | low | high | mid | nums[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 8 | 8 < 20 | floor = 8, low = 4 |
| 2 | 4 | 6 | 5 | 12 | 12 < 20 | floor = 12, low = 6 |
| 3 | 6 | 6 | 6 | 14 | 14 < 20 | floor = 14, low = 7 |
| — | 7 | 6 | — | — | low > high | **loop ends** |

Result: **{floor=14, ceil=-1}** ✅ — every element is `< 20`, so `ceil` never gets set.

### Complexity
- **Time:** O(log n) — single binary search pass, not two separate searches.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Number of passes | 1 (combined floor+ceil scan) | 1 (combined floor+ceil binary search) |
| Uses sorted property? | Partially (relies on order for correctness) | Fully (true binary search) |

---

## 5. Why Track Both floor and ceil in ONE Pass?

A naive optimal approach might be tempted to run two *separate* binary searches:
1. One binary search to find the floor (largest element `<= x`).
2. Another binary search to find the ceiling (smallest element `>= x`).

That would still be O(log n) overall (since 2 × O(log n) is still O(log n)), but it's wasteful — we're re-scanning largely the same search space twice. The single-pass approach shown in `Optimal.java` is elegant because **every comparison result tells us something about BOTH floor and ceil simultaneously**: if `nums[mid] < x`, we immediately know `nums[mid]` can't be a ceiling candidate (so we only update floor), and vice versa. This lets us compute both answers while only ever halving the search space once.

---

## 6. Edge Cases to Consider

1. **x smaller than every element** — e.g., `nums = [2,4,6], x = 1` → floor = -1, ceil = 2.
2. **x larger than every element** — e.g., `nums = [2,4,6], x = 10` → floor = 6, ceil = -1.
3. **x exactly matches an element** — floor and ceil both equal `x` (see Dry Run 2).
4. **x matches a duplicate value** — e.g., `nums = [3,4,4,7,8,10], x = 4` → since `nums[mid] == x` triggers an immediate return, duplicates don't cause any issue; we simply return `{4, 4}` as soon as we land on any `4`.
5. **Single-element array**:
   - `nums = [5], x = 5` → `{5, 5}`
   - `nums = [5], x = 3` → `{-1, 5}`
   - `nums = [5], x = 7` → `{5, -1}`
6. **x falls strictly between two consecutive elements** — e.g., `nums = [3,4,4,7,8,10], x = 5` (see Dry Run 1) — floor and ceil are the two "neighboring" values.

---

## 7. Related Concepts / Follow-Ups

- **Lower Bound / Upper Bound**: Floor and ceil can each be derived from these index-based searches:
  - `ceil = (lowerBoundIndex < n) ? nums[lowerBoundIndex] : -1`
  - `floor = (lowerBoundIndex > 0 && nums[lowerBoundIndex - 1] <= x) ? nums[lowerBoundIndex - 1] : ...` (a bit more involved — the single-pass approach shown here is cleaner).
- **Search Insert Position**: Closely related — the insertion index is essentially where the ceiling "would go" if it doesn't already exist.
- **Closest Element to a Target in a Sorted Array**: A natural follow-up — given floor and ceil, the closest element is whichever of the two has a smaller absolute difference from `x`.

---

## 8. Key Takeaways

- Floor and ceil are **value-based** results (not index-based), but the search technique is still classic binary search.
- A single binary search pass can compute both floor and ceil simultaneously by updating the appropriate variable based on whether `nums[mid]` is less than, greater than, or equal to `x`.
- An exact match (`nums[mid] == x`) short-circuits the search — `x` is trivially both its own floor and ceiling.
- Always initialize `floor` and `ceil` to `-1` so that "no valid floor/ceil found" is handled automatically without extra logic.
