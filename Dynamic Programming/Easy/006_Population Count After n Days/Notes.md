# Notes: Population Count After N Days

## 1. Problem Recap

Population starts with 1 member on day 1. Every living member produces 2 new members each day. Every member dies after exactly 6 days (born on day `d`, removed before reproducing on day `d+6`). Find the total population on day `n`.

```
Day 1: 1
Day 2: 1 + 1*2 = 3
Day 3: 3 + 3*2 = 9
...
Day 7: 726
```

### The Key Insight — Only the Last 6 Days Matter
Since every member's lifespan is exactly 6 days, at any given moment the only members still alive are those born within the **last 6 days**. Anything born 7+ days ago has already died. This means we never need to remember the *entire* birth history — just a rolling window of the 6 most recent days' worth of births.

---

## 2. Approach 1: Brute Force (Full Birth-History Array)

### Idea
Track births for every single day from 1 to n in a full array. To compute a new day's living population, sum up births from all days still within their 6-day lifespan.

### Code Logic
```java
long[] bornOnDay = new long[n + 1];
bornOnDay[1] = 1;

for (int day = 2; day <= n; day++) {
    long livingPopulation = 0;
    for (int d = 1; d < day; d++) {
        if (d + 6 > day) livingPopulation += bornOnDay[d];
    }
    bornOnDay[day] = 2 * livingPopulation;
}

long total = 0;
for (int d = 1; d <= n; d++) {
    if (d + 6 > n) total += bornOnDay[d];
}
```

### Dry Run
`n = 3`

`bornOnDay[1] = 1`

**day=2:** sum births from d<2 where d+6>2 (always true for d=1) → living=1. `bornOnDay[2] = 2*1 = 2`

**day=3:** sum births from d<3 where d+6>3 → d=1 (1+6=7>3 ✓, contributes 1), d=2 (2+6=8>3 ✓, contributes 2) → living=3. `bornOnDay[3] = 2*3 = 6`

**Final total (n=3):** d=1: 1+6=7>3 ✓ (contributes 1); d=2: 2+6=8>3 ✓ (contributes 2); d=3: 3+6=9>3 ✓ (contributes 6). Total = 1+2+6 = 9.

Result: **9** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — for each day, scanning back is bounded by the fixed 6-day window (O(1) per day in practice, since only the last 6 entries can ever satisfy `d+6>day`), so overall O(n).
- **Space:** O(n) — storing births for every day from 1 to n, even though only the last 6 are ever actually needed at once.

### Why It's Not Ideal
Even though the time complexity matches the optimal approach, this version wastes O(n) space storing a full history array when only 6 values are ever relevant at any given moment.

---

## 3. Approach 2: Optimal (Circular Buffer of Size 6)

### Idea
Replace the full birth-history array with a tiny fixed-size array of length 6, indexed by `day % 6`. Since a slot gets reused every 6 days, and that's exactly when its previous occupants are due to die, the circular indexing naturally handles both "remove the expired members" and "record today's births" using the same slot.

### Code Logic
```java
long[] born = new long[6];
born[0] = 1;
long total = 1;

for (int day = 2; day <= n; day++) {
    int index = (day - 1) % 6;
    total -= born[index];       // remove members born 6 days ago (reusing this slot)
    long newBorn = 2 * total;   // remaining living members reproduce
    born[index] = newBorn;      // record today's births in the now-freed slot
    total += newBorn;
}
return (int) total;
```

### Why `(day - 1) % 6` Lines Up Correctly
Day 1's birth is stored in `born[0]` (set directly, before the loop). Day 2 maps to index `(2-1)%6 = 1`, day 3 to index `2`, ..., day 7 maps to index `(7-1)%6 = 0` — the SAME slot as day 1. This is exactly correct: day 7 is when day 1's members (born on day 1, lifespan 6 days) are due to be removed (since `1 + 6 = 7`), so reusing `born[0]` on day 7 to first subtract out day 1's births, then overwrite with day 7's new births, is precisely the right behavior.

### Dry Run 1
`n = 7` (the most illustrative example, showing a full cycle through all 6 slots)

| day | index | born[index] (before) | total before subtract | total after subtract | newBorn=2*total | born[index] (after) | total after add |
|---|---|---|---|---|---|---|---|
| (init) | — | — | — | — | — | born=[1,0,0,0,0,0] | total=1 |
| 2 | 1 | 0 | 1 | 1 | 2 | born=[1,2,0,0,0,0] | 3 |
| 3 | 2 | 0 | 3 | 3 | 6 | born=[1,2,6,0,0,0] | 9 |
| 4 | 3 | 0 | 9 | 9 | 18 | born=[1,2,6,18,0,0] | 27 |
| 5 | 4 | 0 | 27 | 27 | 54 | born=[1,2,6,18,54,0] | 81 |
| 6 | 5 | 0 | 81 | 81 | 162 | born=[1,2,6,18,54,162] | 243 |
| 7 | 0 | 1 | 243 | 242 | 484 | born=[484,2,6,18,54,162] | 726 |

Return `total = 726`.

Result: **726** ✅ (matches expected output — notice how on day 7, the original member from day 1 is correctly subtracted out via `born[0]=1`, since it has reached the end of its 6-day lifespan)

### Dry Run 2
`n = 2`

`born=[1,0,0,0,0,0]`, `total=1`

**day=2:** index=1. total -= born[1]=0 → total=1. newBorn=2*1=2. born[1]=2. total=1+2=3.

Result: **3** ✅ (matches expected output)

### Dry Run 3
`n = 3`

Continuing from day=2 above: `born=[1,2,0,0,0,0]`, `total=3`

**day=3:** index=2. total -= born[2]=0 → total=3. newBorn=2*3=6. born[2]=6. total=3+6=9.

Result: **9** ✅ (matches expected output)

### Complexity
- **Time:** O(n) — single pass from day 2 to day n.
- **Space:** O(1) — a fixed array of length 6, regardless of how large `n` is.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Circular Buffer) |
|---|---|---|
| Time Complexity | O(n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical difference | Same asymptotic time, but wastes memory on a full history array | Minimal, constant memory footprint |

Since `n` is capped at just `20`, the space difference barely matters in practice here — but the circular-buffer technique is a valuable pattern to recognize for any problem involving a fixed lifespan/window, especially if `n` were allowed to be much larger.

---

## 5. Edge Cases to Consider

1. **n = 1** — handled by an explicit early return of `1` (population is just the single founding member, with no time for any births or deaths yet).
2. **n = 6 or n = 7** — the boundary where the very first member's lifespan expires; day 7 is exactly when `born[0]` (day 1's births) gets correctly subtracted out before being overwritten (see Dry Run 1).
3. **n = 20 (maximum)** — confirms the circular buffer correctly cycles through multiple full 6-day periods without any issues; the values grow quickly (roughly tripling or more each day once the population stabilizes), so using `long` internally (even though the final cast to `int` is safe within these constraints) is a sensible precaution.
4. **Small n values (2 or 3)** — directly verified against the problem's own worked examples (see Dry Runs 2 and 3).

---

## 6. Related Concepts / Follow-Ups

- **Sliding Window Problems**: The core idea here — "only the most recent k entries matter, so use a fixed-size circular buffer instead of unbounded history" — is a recognizable pattern from sliding-window algorithms, even though this problem isn't a classic subarray/substring sliding window.
- **Population/Growth Simulation Problems**: A broader category of problems modeling birth/death cycles with fixed lifespans, often solvable with the same rolling-window bookkeeping technique.
- **Modular Indexing for Circular Buffers**: The `(day - 1) % 6` indexing trick generalizes to any problem needing to cycle through a fixed number of "slots" over time, reusing old slots once their associated data is no longer relevant.

---

## 7. Key Takeaways

- The fixed 6-day lifespan means only the most recent 6 days of birth history are ever relevant — a classic setup for replacing unbounded history storage with a small, fixed-size circular buffer.
- The circular buffer's slot-reuse timing (`day % 6`) elegantly coincides with exactly when that slot's occupants are due to expire, making the "subtract expired, then record new births" logic self-consistent without extra bookkeeping.
- This reduces space complexity from O(n) (storing full history) down to O(1) (a fixed array of size 6), while keeping the same O(n) time complexity.
- Recognizing "only the last k states matter" is a broadly useful pattern for converting unbounded-memory simulations into constant-memory ones.
