# Notes: Painter's Partition

## 1. Problem Recap

`A` painters must paint `N` boards, where board `i` has length `C[i]`. Each painter paints a **contiguous** segment of boards, and takes `B` units of time per unit of board length. Minimize the total time needed (i.e., the maximum time any single painter spends), then return that value modulo `10000003`.

```
A = 3, B = 2, C = [5, 10, 30, 20]
Best split: [5,10] | [30] | [20] -> length sums: 15, 30, 20
Max sum = 30 -> time = 30 * B = 30 * 2 = 60
```

### The Twist: This Is Split Array Largest Sum + One Extra Step
The core sub-problem — "split the array into `A` contiguous groups to minimize the maximum group sum" — is **exactly** Split Array Largest Sum (and, by extension, Ship Packages Within D Days and Book Allocation). Painter's Partition adds two small wrinkles on top:

1. **Multiply the minimized max sum by `B`** to convert "board length" into actual "painting time."
2. **Apply a modulo (`10000003`)** to the final result, as specified by the problem.

Recognizing this equivalence means you've essentially already solved the hard part if you've seen any of those other problems before.

---

## 2. Approach 1: Brute Force (Linear Scan Over Candidate Sums)

### Idea
Same greedy-feasibility structure as the "twin" problems: try every candidate max-length-sum from `max(C)` up to `sum(C)`, using a greedy check to see how many painters would be needed. The first candidate that works with `<= A` painters gives the minimized max sum — then multiply by `B` and apply the modulo.

### Code Logic
```java
long maxBoard = max(C);
long totalLength = sum(C);
A = Math.min(A, C.length);  // extra painters are unused

for (long candidate = maxBoard; candidate <= totalLength; candidate++) {
    if (canPaint(C, A, candidate)) {
        return (int) ((candidate % MOD) * (B % MOD) % MOD);
    }
}
```

### Why `A = Math.min(A, C.length)`?
If there are more painters than boards, the extra painters simply have nothing to do — you can never usefully assign more than one painter per board (since a board can't be split). Clamping `A` down to `C.length` ensures the feasibility check behaves sensibly (asking for more "groups" than there are boards would be meaningless).

### Dry Run
`A = 3, B = 2, C = [5, 10, 30, 20]`, checking `candidate = 30`

| len | current + len | <=30? | Action | used |
|---|---|---|---|---|
| 5 | 0+5=5 | Yes | current=5 | 1 |
| 10 | 5+10=15 | Yes | current=15 | 1 |
| 30 | 15+30=45 | No | new painter, current=30 | 2 |
| 20 | 30+20=50 | No | new painter, current=20 | 3 |

`used = 3 <= A = 3` → feasible at candidate 30.

(Checking candidates from `max(C)=30`... wait, 30 IS the max board, so this is actually the very first candidate checked — no smaller candidate is even possible since a painter must handle the 30-length board alone at minimum.)

Minimized max sum = **30**. Time = `30 * 2 = 60`. `60 % 10000003 = 60`.

Result: **60** ✅

### Complexity
- **Time:** O(sum(C) × N) — up to `sum(C)` candidate sums, each with an O(N) simulation.
- **Space:** O(1)

---

## 3. Approach 2: Optimal (Binary Search on the Answer)

### Idea
Binary search the candidate max-length-sum range `[max(C), sum(C)]`, using the identical `canPaint` greedy feasibility check as the brute force. Once the minimized max sum is found, multiply by `B` and take the modulo.

### Code Logic
```java
long low = max(C), high = sum(C);
A = Math.min(A, C.length);

while (low < high) {
    long mid = low + (high - low) / 2;
    if (canPaint(C, A, mid)) {
        high = mid;
    } else {
        low = mid + 1;
    }
}

return (int) ((low % MOD) * (B % MOD) % MOD);
```

### Dry Run 1
`A = 2, B = 5, C = [1, 10]`

Search range: `low = 10` (max), `high = 11` (sum). `A = min(2, 2) = 2`.

| Step | low | high | mid | canPaint? | Action |
|---|---|---|---|---|---|
| 1 | 10 | 11 | 10 | 1<=10,+10=11>1→painter2,current=10. used=2<=2. Yes | high=10 |
| — | 10 | 10 | — | — | **low==high -> minimized sum = 10** |

Time = `10 * 5 = 50`. `50 % 10000003 = 50`.

Result: **50** ✅ (matches expected output)

### Dry Run 2
`A = 10, B = 1, C = [1, 8, 11, 3]`

Search range: `low = 11` (max), `high = 23` (sum). `A = min(10, 4) = 4`.

| Step | low | high | mid | canPaint? | Action |
|---|---|---|---|---|---|
| 1 | 11 | 23 | 17 | 1<=17,+8=9,+11=20>9→p2,c11,+3=14<=11? 11+3=14>11→p3,c3. used=3<=4. Yes | high=17 |
| 2 | 11 | 17 | 14 | 1<=14,+8=9,+11=20>9→p2,c11,+3=14<=11? 14>11→p3,c3. used=3<=4. Yes | high=14 |
| 3 | 11 | 14 | 12 | 1<=12,+8=9,+11=20>9→p2,c11,+3=14>11→p3,c3. used=3<=4. Yes | high=12 |
| 4 | 11 | 12 | 11 | 1<=11,+8=9,+11=20>9→p2,c11,+3=14>11→p3,c3. used=3<=4. Yes | high=11 |
| — | 11 | 11 | — | — | **low==high -> minimized sum = 11** |

Time = `11 * 1 = 11`. `11 % 10000003 = 11`.

Result: **11** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`A = 3, B = 2, C = [5, 10, 30, 20]`

Search range: `low = 30` (max), `high = 65` (sum). `A = min(3, 4) = 3`.

| Step | low | high | mid | canPaint? | Action |
|---|---|---|---|---|---|
| 1 | 30 | 65 | 47 | 5+10=15,+30=45<=47,+20=65>47→p2,c20. used=2<=3. Yes | high=47 |
| 2 | 30 | 47 | 38 | 5+10=15,+30=45>15→p2,c30,+20=50>30→p3,c20. used=3<=3. Yes | high=38 |
| 3 | 30 | 38 | 34 | 5+10=15,+30=45>15→p2,c30,+20=50>30→p3,c20. used=3<=3. Yes | high=34 |
| 4 | 30 | 34 | 32 | 5+10=15,+30=45>15→p2,c30,+20=50>30→p3,c20. used=3<=3. Yes | high=32 |
| 5 | 30 | 32 | 31 | 5+10=15,+30=45>15→p2,c30,+20=50>30→p3,c20. used=3<=3. Yes | high=31 |
| 6 | 30 | 31 | 30 | 5+10=15,+30=45>15→p2,c30,+20=50>30→p3,c20. used=3<=3. Yes | high=30 |
| — | 30 | 30 | — | — | **low==high -> minimized sum = 30** |

Time = `30 * 2 = 60`. `60 % 10000003 = 60`.

**Result: 60** ✅

So for the quiz options `60, 80, 90, 120`, the correct answer is **60**.

### Complexity
- **Time:** O(N log(sum(C)))
- **Space:** O(1)

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Binary Search) |
|---|---|---|
| Time Complexity | O(sum(C) × N) | O(N log(sum(C))) |
| Space Complexity | O(1) | O(1) |
| Scales for N up to 10^5, C[i] up to 10^6? | No - sum could be up to 10^11, extremely slow | Yes - roughly N * log(10^11) ≈ 10^5 * 37 ≈ 3.7*10^6 |

---

## 5. Why Everything Uses `long` (and Why the Modulo Matters)

With `N` up to `10^5` and `C[i]` up to `10^6`, `sum(C)` could reach up to `10^11` — far beyond the range of a 32-bit `int` (`int` max is about `2.1 * 10^9`). This is why `low`, `high`, and `mid` are all declared as `long` in the provided solution. Additionally, the final time is `minimizedSum * B`, where `B` can be up to `10^6` — multiplying two large `long` values could still be enormous, which is exactly why the problem asks for the result **modulo `10000003`**, and why the modulo is applied carefully (`(low % MOD) * (B % MOD) % MOD`) to avoid overflow even during the multiplication step itself.

---

## 6. Edge Cases to Consider

1. **A >= N (more or equal painters than boards)** — every board can get its own painter; the minimized max sum is `max(C)`, and `A` gets clamped down to `C.length` internally.
2. **A = 1** — a single painter handles everything; minimized max sum is `sum(C)`.
3. **Single board** — e.g., `C = [7], A = 1, B = 3` → time = `7 * 3 = 21`.
4. **All boards equal length** — e.g., `C = [4,4,4,4], A = 2` → minimized max sum = `8` (2 boards per painter).
5. **Very large B** — verifies the modulo arithmetic correctly prevents overflow even when `B` is at its constraint maximum (`10^6`).
6. **Result that's smaller than MOD** — most typical cases (as in all three examples) will have a result well below `10000003`, so the modulo often has no visible effect except as a safety net for larger inputs.

---

## 7. Related Concepts / Follow-Ups

- **Split Array Largest Sum**: The exact same core sub-problem (minimize the maximum contiguous group sum), without the extra multiply-by-B and modulo steps.
- **Capacity to Ship Packages Within D Days / Book Allocation Problem**: Two more restatements of the identical underlying structure.
- **Modular Arithmetic in Competitive Programming**: The `(a % MOD) * (b % MOD) % MOD` pattern used here is a standard idiom for safely computing `(a * b) % MOD` without intermediate overflow, worth recognizing as a reusable technique beyond just this problem.

---

## 8. Key Takeaways

- Painter's Partition is Split Array Largest Sum with two extra steps bolted on: multiply the minimized sum by `B`, then apply a modulo — recognizing the shared core structure saves significant problem-solving effort.
- Using `long` throughout (rather than `int`) is essential given how large `sum(C)` and the final time value can get, based on the problem's constraints.
- Clamping `A = Math.min(A, C.length)` handles the edge case of having more painters than boards, ensuring the feasibility check behaves correctly.
- The modular arithmetic pattern `(a % MOD) * (b % MOD) % MOD` safely computes the final result without overflow, even for large intermediate products.
