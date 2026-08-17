# Notes: Count Occurrences in a Sorted Array

## 1. Problem Recap

Given a **sorted** array `arr` and an integer `target`, count how many times `target` appears in the array.

### Key Insight
Because the array is sorted, every occurrence of `target` sits in a **single contiguous block**:

```
arr = [0, 0, 1, 1, 1, 2, 3]
              ^  ^  ^
           first  |  last
              (all 1's grouped together)
```

If we know the index of the **first** occurrence and the index of the **last** occurrence, the count is simply:

```
count = last - first + 1
```

This makes "Count Occurrences" a direct extension of the "First and Last Occurrence" problem — solve that, and this one is just one extra line of arithmetic.

---

## 2. Approach 1: Brute Force (Linear Search)

### Idea
Scan the whole array once, incrementing a counter every time we see `target`.

### Code Logic
```java
int count = 0;
for (int i = 0; i < arr.length; i++) {
    if (arr[i] == target) count++;
}
return count;
```

### Dry Run
`arr = [0, 0, 1, 1, 1, 2, 3]`, `target = 1`

| i | arr[i] | Match? | count |
|---|---|---|---|
| 0 | 0 | No | 0 |
| 1 | 0 | No | 0 |
| 2 | 1 | Yes | 1 |
| 3 | 1 | Yes | 2 |
| 4 | 1 | Yes | 3 |
| 5 | 2 | No | 3 |
| 6 | 3 | No | 3 |

Result: **3** ✅

### Complexity
- **Time:** O(n) — scans every element regardless of whether target is found.
- **Space:** O(1)

### Why It's Not Optimal
Given the constraint `arr.length` up to **10^6**, a linear scan means up to a million comparisons per query — fine for a single call, but wasteful if this function is called repeatedly (e.g., counting occurrences of many different targets), and it doesn't exploit the sorted structure at all.

---

## 3. Approach 2: Optimal (Binary Search — Two Passes)

### Idea
Use two binary searches to directly locate the first and last occurrence of `target`, then compute the count arithmetically.

#### Pass 1 — Find First Occurrence
This is structured slightly differently from a typical "first occurrence" search — it's actually a **lower bound** search (`arr[mid] >= target` drives the narrowing), with an extra check to record `first` only when we land on an *exact* match:

```java
int left = 0, right = arr.length - 1;
int first = -1;

while (left <= right) {
    int mid = left + (right - left) / 2;
    if (arr[mid] >= target) {
        if (arr[mid] == target) first = mid;
        right = mid - 1;   // keep narrowing left, looking for earlier match
    } else {
        left = mid + 1;
    }
}
```

If `first` is still `-1` after this loop, `target` doesn't exist in the array at all — return `0` immediately without running the second search.

#### Pass 2 — Find Last Occurrence
Symmetric to Pass 1, but driven by `arr[mid] <= target` and narrowing right:

```java
left = 0; right = arr.length - 1;
int last = -1;

while (left <= right) {
    int mid = left + (right - left) / 2;
    if (arr[mid] <= target) {
        if (arr[mid] == target) last = mid;
        left = mid + 1;    // keep narrowing right, looking for later match
    } else {
        right = mid - 1;
    }
}
```

#### Final Step
```java
return last - first + 1;
```

### Dry Run 1 — Find First Occurrence of 1
`arr = [0, 0, 1, 1, 1, 2, 3]`, `target = 1`

| Step | left | right | mid | arr[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 1 | 1 >= 1 (match, first=3) | right = 2 |
| 2 | 0 | 2 | 1 | 0 | 0 >= 1? No | left = 2 |
| 3 | 2 | 2 | 2 | 1 | 1 >= 1 (match, first=2) | right = 1 |
| — | 2 | 1 | — | — | left > right | **loop ends** |

`first = 2`

### Dry Run 2 — Find Last Occurrence of 1 (continuing same example)

| Step | left | right | mid | arr[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 6 | 3 | 1 | 1 <= 1 (match, last=3) | left = 4 |
| 2 | 4 | 6 | 5 | 2 | 2 <= 1? No | right = 4 |
| 3 | 4 | 4 | 4 | 1 | 1 <= 1 (match, last=4) | left = 5 |
| — | 5 | 4 | — | — | left > right | **loop ends** |

`last = 4`

**Final Result:** `last - first + 1 = 4 - 2 + 1 = 3` ✅ (matches expected output)

### Dry Run 3 — All Elements Equal Target
`arr = [5, 5, 5, 5, 5, 5]`, `target = 5`

First-occurrence search:

| Step | left | right | mid | arr[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 5 | match, first=2 | right = 1 |
| 2 | 0 | 1 | 0 | 5 | match, first=0 | right = -1 |
| — | 0 | -1 | — | — | left > right | **loop ends** |

`first = 0`

Last-occurrence search:

| Step | left | right | mid | arr[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 5 | 2 | 5 | match, last=2 | left = 3 |
| 2 | 3 | 5 | 4 | 5 | match, last=4 | left = 5 |
| 3 | 5 | 5 | 5 | 5 | match, last=5 | left = 6 |
| — | 6 | 5 | — | — | left > right | **loop ends** |

`last = 5`

**Final Result:** `5 - 0 + 1 = 6` ✅ (matches expected output)

### Dry Run 4 — Target Not Found
`arr = [2, 4, 6, 8, 10]`, `target = 3`

First-occurrence search:

| Step | left | right | mid | arr[mid] | Comparison | Action |
|---|---|---|---|---|---|---|
| 1 | 0 | 4 | 2 | 6 | 6 >= 3 (no exact match) | right = 1 |
| 2 | 0 | 1 | 0 | 2 | 2 >= 3? No | left = 1 |
| 3 | 1 | 1 | 1 | 4 | 4 >= 3 (no exact match) | right = 0 |
| — | 1 | 0 | — | — | left > right | **loop ends** |

`first` remains `-1` → **return 0 immediately** (second search skipped)

**Result: 0** ✅ (matches expected output)

### Complexity
- **Time:** O(log n) + O(log n) = O(log n) overall.
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(n) | O(log n) |
| Space Complexity | O(1) | O(1) |
| Scales well for arr.length up to 10^6? | Works, but slower | Much faster |
| Early exit if not found? | No | Yes (skips second search) |

For `n = 10^6`, brute force could take up to a million comparisons, while binary search takes at most about 20 (`log2(10^6) ≈ 19.9`) per pass, or ~40 total for both passes — a massive difference.

---

## 5. A Subtle Detail: Why `arr[mid] >= target` (not `==`) Drives the First-Occurrence Search?

This implementation is written as a true **lower bound** search (shrinking on `arr[mid] >= target`), with the exact-match check as a side effect (`if (arr[mid] == target) first = mid;`). This is a slightly different style from the classic "first occurrence" binary search you might have seen (where you only move `right = mid - 1` when there's an exact match, and otherwise move `left` or `right` based on `<` or `>`).

Both styles arrive at the same correct answer, but this lower-bound-flavored version is worth recognizing: **first occurrence of target = lower bound of target, verified to be an exact match.** If `target` isn't present, the lower bound would point to where it *would* be inserted, which won't be an exact match, so `first` correctly stays `-1`.

---

## 6. Edge Cases to Consider

1. **Target not present at all** — e.g., `target = 3` in `[2,4,6,8,10]` → `0`.
2. **Every element equals target** — e.g., `arr = [5,5,5,5,5,5], target = 5` → `6` (entire array).
3. **Target appears exactly once** — first and last occurrence are the same index, so count = `1`.
4. **Target at the very start or very end of the array**.
5. **Single-element array**:
   - `arr = [5], target = 5` → `1`
   - `arr = [5], target = 3` → `0`
6. **Large array (up to 10^6 elements)** — binary search's O(log n) really shines here; a brute-force scan is still technically fine within typical time limits, but binary search is a much safer, faster choice at this scale, especially if called multiple times.

---

## 7. Related Concepts / Follow-Ups

- **First and Last Occurrence**: This problem is *literally* built on top of that one — see those notes for a deeper breakdown of the two-pass binary search technique.
- **Lower Bound / Upper Bound**: `first occurrence = lowerBound(target)` (verified as an exact match), and similarly `last occurrence` relates to `upperBound(target) - 1`.
- **Count of Elements in a Range [a, b]**: A natural generalization — count how many elements fall between two values, computed as `lowerBound(b+1) - lowerBound(a)` (or similar, depending on inclusive/exclusive bounds).

---

## 8. Key Takeaways

- Counting occurrences in a sorted array reduces to finding the first and last occurrence, then computing `last - first + 1`.
- Both occurrence searches are still O(log n), so the overall solution stays O(log n) — much better than the O(n) brute force, especially at the problem's scale (`arr.length` up to 10^6).
- Returning early (`if (first == -1) return 0;`) avoids a wasted second binary search when the target isn't present at all.
- Recognizing "first occurrence" as a lower-bound search (with an exact-match check) connects this problem back to the broader family of binary search boundary problems.
