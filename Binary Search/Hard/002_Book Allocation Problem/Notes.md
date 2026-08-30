# Notes: Book Allocation Problem

## 1. Problem Recap

Allocate books (contiguous groups) to `m` students so that every student gets at least one book, and the **maximum number of pages any single student receives is minimized**.

```
nums = [12, 34, 67, 90], m = 2
Allocation [12,34,67] | [90]: student totals = 113, 90 -> max = 113
This is the minimum possible maximum across all valid contiguous allocations.
```

### This Problem Is a Twin of "Capacity to Ship Packages Within D Days"
The structure is **identical**, just with different names:

| Ship Packages | Book Allocation |
|---|---|
| packages | books |
| weights | pages |
| days | students |
| ship capacity | max pages per student |

Both are "minimize the maximum" binary-search-on-the-answer problems, using the exact same greedy feasibility check pattern (pack as much as fits into the current group, then start a new group).

### The Key Insight — Monotonicity
As the candidate page limit **increases**, fewer students are needed to allocate all the books (since each student can absorb more pages before needing to split off). This monotonic relationship is what enables binary search on the answer.

---

## 2. Approach 1: Brute Force (Linear Scan Over Page Limits)

### Idea
Try every candidate page limit starting from the largest single book's page count (a student must be able to handle the biggest book alone) up to the sum of all pages (one student takes everything). For each candidate, greedily simulate allocating books and count how many students are needed.

### Code Logic
```java
if (m > n) return -1;

int maxBook = max(nums);
int totalPages = sum(nums);

for (int limit = maxBook; limit <= totalPages; limit++) {
    if (canAllocate(nums, m, limit)) return limit;
}
```

### The Feasibility Check (`canAllocate`)
```java
int students = 1, pages = 0;
for (int book : nums) {
    if (pages + book <= maxPages) {
        pages += book;
    } else {
        students++;
        pages = book;
        if (students > m) return false;
    }
}
return true;
```

Greedily pile books onto the current student's stack as long as it doesn't exceed `maxPages`. The moment adding the next book would overflow, start a new student. If we ever need more than `m` students, this limit is infeasible.

### Dry Run
`nums = [15, 17, 20]`, `m = 2`, checking `limit = 32`

| book | pages + book | <=32? | Action | students |
|---|---|---|---|---|
| 15 | 0+15=15 | Yes | pages=15 | 1 |
| 17 | 15+17=32 | Yes | pages=32 | 1 |
| 20 | 32+20=52 | No | new student, pages=20 | 2 |

`students = 2 <= m = 2` → feasible at limit 32.

(Checking limits `20` through `31` would all require 3 or more students.)

Result: **32** ✅

### Complexity
- **Time:** O(sum(nums) × n) — up to `sum(nums)` candidate limits, each with an O(n) simulation.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search the candidate limit range `[max(nums), sum(nums)]`. For each candidate `mid`, run the same greedy `canAllocate` check:

- If feasible, `mid` is a valid limit — try an even smaller one: `high = mid`.
- If not feasible, `mid` is too small — need a bigger limit: `low = mid + 1`.

### Code Logic
```java
if (m > n) return -1;

int low = max(nums), high = sum(nums);

while (low < high) {
    int mid = low + (high - low) / 2;
    if (canAllocate(nums, m, mid)) {
        high = mid;
    } else {
        low = mid + 1;
    }
}
return low;
```

### Dry Run 1
`nums = [12, 34, 67, 90]`, `m = 2`

Search range: `low = 90` (max), `high = 203` (sum)

| Step | low | high | mid | canAllocate? | Action |
|---|---|---|---|---|---|
| 1 | 90 | 203 | 146 | 12+34=46,+67=113,+90=203>146→students2,pages=90. students=2<=2. Yes | high=146 |
| 2 | 90 | 146 | 118 | 12+34+67=113,+90=203>118→students2,pages=90. 2<=2. Yes | high=118 |
| 3 | 90 | 118 | 104 | 12+34=46,+67=113>104→students2,pages=67,+90=157>104→students3>2. No | low=105 |
| 4 | 105 | 118 | 111 | 12+34=46,+67=113>111→students2,pages=67,+90=157>111→students3>2. No | low=112 |
| 5 | 112 | 118 | 115 | 12+34+67=113,+90=203>115→students2,pages=90. 2<=2. Yes | high=115 |
| 6 | 112 | 115 | 113 | 12+34+67=113,+90=203>113→students2,pages=90. 2<=2. Yes | high=113 |
| 7 | 112 | 113 | 112 | 12+34=46,+67=113>112→students2,pages=67,+90=157>112→students3>2. No | low=113 |
| — | 113 | 113 | — | — | **low==high -> return 113** |

Result: **113** ✅ (matches expected output)

### Dry Run 2
`nums = [25, 46, 28, 49, 24]`, `m = 4`

Search range: `low = 49` (max), `high = 172` (sum)

| Step | low | high | mid | canAllocate? | Action |
|---|---|---|---|---|---|
| 1 | 49 | 172 | 110 | 25+46=71,+28=99,+49=148>110→s2,p49,+24=73. s=2<=4. Yes | high=110 |
| 2 | 49 | 110 | 79 | 25+46=71,+28=99>79→s2,p28,+49=77,+24=101>77→s3,p24. s=3<=4. Yes | high=79 |
| 3 | 49 | 79 | 64 | 25+46=71>64→s2,p46,+28=74>46... let's compute carefully: p=46(after student2 starts with46? wait 46>64? no 46<=64 so p=46; +28=74>64→s3,p28,+49=77>28→s4,p49,+24=73>49→s5>4. No | low=65 |
| 4 | 65 | 79 | 72 | 25+46=71<=72,+28=99>72→s2,p28,+49=77>28→s3,p49,+24=73>49→s4,p24. s=4<=4. Yes | high=72 |
| 5 | 65 | 72 | 68 | 25+46=71>68→s2,p46,+28=74>46→s3,p28,+49=77>28→s4,p49,+24=73>49→s5>4. No | low=69 |
| 6 | 69 | 72 | 70 | 25+46=71>70→s2,p46,+28=74>46→s3,p28,+49=77>28→s4,p49,+24=73>49→s5>4. No | low=71 |
| 7 | 71 | 72 | 71 | 25+46=71<=71,+28=99>71→s2,p28,+49=77>28→s3,p49,+24=73>49→s4,p24. s=4<=4. Yes | high=71 |
| — | 71 | 71 | — | — | **low==high -> return 71** |

Result: **71** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`nums = [15, 17, 20]`, `m = 2`

Search range: `low = 20` (max), `high = 52` (sum)

| Step | low | high | mid | canAllocate? | Action |
|---|---|---|---|---|---|
| 1 | 20 | 52 | 36 | 15+17=32,+20=52>36→s2,p20. s=2<=2. Yes | high=36 |
| 2 | 20 | 36 | 28 | 15<=28,+17=32>28→s2,p17,+20=37>17→s3>2. No | low=29 |
| 3 | 29 | 36 | 32 | 15+17=32<=32,+20=52>32→s2,p20. s=2<=2. Yes | high=32 |
| 4 | 29 | 32 | 30 | 15<=30,+17=32>30→s2,p17,+20=37>17→s3>2. No | low=31 |
| 5 | 31 | 32 | 31 | 15<=31,+17=32>31→s2,p17,+20=37>17→s3>2. No | low=32 |
| — | 32 | 32 | — | — | **low==high -> return 32** |

**Result: 32** ✅

So for the quiz options `-1, 32, 31, 33`, the correct answer is **32**.

### Complexity
- **Time:** O(n log(sum(nums)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(sum(nums) × n) | O(n log(sum(nums))) |
| Space Complexity | O(1) | O(1) |
| Scales for n, m up to 10^4, nums[i] up to 10^5? | No - sum could be up to 10^9, far too slow | Yes - roughly n * log(10^9) ≈ 10^4 * 30 ≈ 3*10^5 |

---

## 5. When Is the Answer `-1`?

The only way allocation is impossible is if there are **more students than books** (`m > n`) — since each student must receive at least one book, and books can't be split between students, having more students than books guarantees at least one student would be left with nothing. This check is done upfront, before any binary search is attempted, since it's a fast O(1) determination that avoids wasted computation.

---

## 6. Edge Cases to Consider

1. **m > n** — impossible, return `-1` immediately.
2. **m = n** — each student gets exactly one book; answer is `max(nums)` (the single largest book determines the limit, since every student handles exactly one book).
3. **m = 1** — a single student takes all the books; answer is `sum(nums)`.
4. **All books have equal pages** — e.g., `nums = [10,10,10,10], m=2` → answer is `20` (2 books per student).
5. **Single book, single student** — `nums = [50], m = 1` → answer is `50`.
6. **Large arrays with pages near the constraint maximum (10^5)** — sum could reach `10^4 * 10^5 = 10^9`, which fits within `int` range but is worth noting when reasoning about the search bounds.

---

## 7. Related Concepts / Follow-Ups

- **Capacity to Ship Packages Within D Days**: The exact same problem structurally — recognizing this equivalence means you've essentially already solved this problem once you've solved that one (or vice versa).
- **Koko Eating Bananas**: Another close relative in the "minimize the maximum, binary search on the answer with a greedy/arithmetic feasibility check" family.
- **Split Array Largest Sum** (LeetCode 410): Yet another restatement of this same core problem — split an array into `m` contiguous parts to minimize the largest part's sum.
- **Aggressive Cows**: The "maximize the minimum" mirror-image pattern, worth contrasting against this "minimize the maximum" pattern to build a complete mental model of binary search on the answer.

---

## 8. Key Takeaways

- Book Allocation is structurally identical to Capacity to Ship Packages Within D Days and Split Array Largest Sum — recognizing shared structure across differently-worded problems is a major time-saver.
- The upfront `m > n` check handles the only true impossibility case cleanly, before any search begins.
- The greedy "pack as much as fits, then move to the next student" feasibility check is optimal for a fixed page limit, making the O(n) check both correct and efficient.
- The binary search range `[max(nums), sum(nums)]` reflects the two natural extremes: a student must handle the biggest book alone, and never needs to handle more than the total page count.
