# Notes: Ninja's Training

## 1. Problem Recap

Choose one of 3 activities each day, maximizing total points, with the constraint that the **same activity can't be repeated on consecutive days**.

```
matrix = [[20,10,10],[20,10,10],[20,30,10]]
Best plan: Day1=run(20), Day2=fight(10), Day3=stealth(30) -> total = 60
```

### Why This Is "2D DP"
Unlike Climbing Stairs or House Robber (where the state was just "which day/index"), here the state needs **two** dimensions: which day, AND which activity was done the day before (since that determines what's allowed today). This is the first genuinely 2D DP problem in the series — `dp[day][lastActivity]`.

---

## 2. Approach 1: Brute Force (Plain Recursion)

### Idea
Recursively try every valid activity for each day (excluding whatever was chosen the previous day), tracking the "last activity used" as part of the recursive state.

### Code Logic
```java
private int solve(int[][] matrix, int day, int last) {
    if (day == 0) {
        int best = 0;
        for (int act = 0; act < 3; act++) {
            if (act != last) best = Math.max(best, matrix[0][act]);
        }
        return best;
    }
    int best = 0;
    for (int act = 0; act < 3; act++) {
        if (act != last) {
            best = Math.max(best, matrix[day][act] + solve(matrix, day - 1, act));
        }
    }
    return best;
}
```

Called initially with `last = 3` (a sentinel meaning "no restriction on day 0").

### Dry Run (Conceptual)
`matrix = [[10,40,70],[20,50,80],[30,60,90]]`, finding `solve(2, 3)`

```
solve(2,3): try act=0,1,2 (none excluded since last=3)
  act=2 (fight, 90): 90 + solve(1, 2)
    solve(1,2): try act=0,1 (act=2 excluded)
      act=1 (stealth,50): 50 + solve(0,1)
        solve(0,1): max of matrix[0][0],matrix[0][2] (act=1 excluded) = max(10,70)=70
      -> 50+70=120
      act=0 (run,20): 20 + solve(0,0)
        solve(0,0): max(matrix[0][1],matrix[0][2])=max(40,70)=70
      -> 20+70=90
    solve(1,2) = max(120,90) = 120
  -> 90+120 = 210
  (other act=0,1 branches at day 2 would give less - omitted for brevity)
solve(2,3) = 210
```

Result: **210** ✅ (matches Example 1's expected output)

### Complexity
- **Time:** O(3^n) — up to 3 branches per level, with the (day, lastActivity) state recomputed repeatedly across different paths.
- **Space:** O(n) — recursion stack depth.

---

## 3. Approach 2: Optimal (Iterative DP, Space-Optimized)

### Idea
Since `dp[day][activity]` only depends on `dp[day-1][...]`, track a rolling array of size 3 (`prev[0..2]`) representing the best score achievable up through the previous day, ending on each of the 3 activities.

### Code Logic
```java
int[] prev = matrix[0].clone();

for (int i = 1; i < n; i++) {
    int run     = matrix[i][0] + Math.max(prev[1], prev[2]);
    int stealth = matrix[i][1] + Math.max(prev[0], prev[2]);
    int fight   = matrix[i][2] + Math.max(prev[0], prev[1]);
    prev[0] = run; prev[1] = stealth; prev[2] = fight;
}

return Math.max(prev[0], Math.max(prev[1], prev[2]));
```

### Dry Run 1
`matrix = [[10,40,70],[20,50,80],[30,60,90]]`

`prev = [10, 40, 70]` (day 0)

**Day 1** (`matrix[1] = [20,50,80]`):
- run = 20 + max(40,70) = 20+70 = 90
- stealth = 50 + max(10,70) = 50+70 = 120
- fight = 80 + max(10,40) = 80+40 = 120

`prev = [90, 120, 120]`

**Day 2** (`matrix[2] = [30,60,90]`):
- run = 30 + max(120,120) = 30+120 = 150
- stealth = 60 + max(90,120) = 60+120 = 180
- fight = 90 + max(90,120) = 90+120 = 210

`prev = [150, 180, 210]`

Final answer: `max(150,180,210) = 210`

Result: **210** ✅ (matches expected output)

### Dry Run 2
`matrix = [[70,40,10],[180,20,5],[200,60,30]]`

`prev = [70, 40, 10]` (day 0)

**Day 1** (`[180,20,5]`):
- run = 180 + max(40,10) = 180+40 = 220
- stealth = 20 + max(70,10) = 20+70 = 90
- fight = 5 + max(70,40) = 5+70 = 75

`prev = [220, 90, 75]`

**Day 2** (`[200,60,30]`):
- run = 200 + max(90,75) = 200+90 = 290
- stealth = 60 + max(220,75) = 60+220 = 280
- fight = 30 + max(220,90) = 30+220 = 250

`prev = [290, 280, 250]`

Final answer: `max(290,280,250) = 290`

Result: **290** ✅ (matches expected output)

### Dry Run 3 — "Your Turn" Case
`matrix = [[20,10,10],[20,10,10],[20,30,10]]`

`prev = [20, 10, 10]` (day 0)

**Day 1** (`[20,10,10]`):
- run = 20 + max(10,10) = 20+10 = 30
- stealth = 10 + max(20,10) = 10+20 = 30
- fight = 10 + max(20,10) = 10+20 = 30

`prev = [30, 30, 30]`

**Day 2** (`[20,30,10]`):
- run = 20 + max(30,30) = 20+30 = 50
- stealth = 30 + max(30,30) = 30+30 = 60
- fight = 10 + max(30,30) = 10+30 = 40

`prev = [50, 60, 40]`

Final answer: `max(50,60,40) = 60`

**Result: 60** ✅

So for the quiz options `70, 50, 40, 60`, the correct answer is **60**.

### Complexity
- **Time:** O(n) — single pass through the days.
- **Space:** O(1) — a fixed-size array of 3 values.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Iterative DP) |
|---|---|---|
| Time Complexity | O(3^n) | O(n) |
| Space Complexity | O(n) | O(1) |
| Practical for n up to 10^4? | No — exponential blowup | Yes — trivially fast |

---

## 5. Why This Is "2D DP" Even Though the Optimal Solution Uses O(1) Space

It's worth clarifying: the *problem* is inherently 2D in nature (state = day × last-activity), which is why the brute force recursion needs both `day` and `last` as parameters. However, because each day's computation only ever depends on the *immediately previous* day's three values (not any earlier days), we can collapse the "day" dimension down to a rolling window of size 1, leaving only the "3 activities" dimension to actually track — hence the `int[3] prev` array instead of a full `int[n][3]` table. This is the same space-optimization principle as Climbing Stairs and Frog Jump, just applied to a state with an extra dimension.

---

## 6. Edge Cases to Consider

1. **n = 1** — no adjacency constraint at all (nothing "before" day 0); the answer is simply the max of the 3 activities on that single day.
2. **All activities have equal points every day** — e.g., every entry is the same value `v` → the ninja must still alternate activities, but every valid alternation yields the same total (`n * v`), since no activity is ever favored.
3. **One activity always dominates** — e.g., running always has by far the highest points — the DP naturally forces alternating away from running every other day, since it can't be repeated, correctly balancing against the second-best available choice.
4. **Large n (up to 10^4)** — confirms the O(n) iterative approach scales comfortably, unlike the exponential brute force.

---

## 7. Related Concepts / Follow-Ups

- **Climbing Stairs / Frog Jump / House Robber**: All share the general DP theme of "look back at limited previous state(s) and combine," though this problem is the first to require tracking multiple values per state (one per activity) rather than a single scalar.
- **DP on Grids**: The natural next topic in this series — problems involving 2D grids with paths, often requiring genuinely 2D (not space-reducible to O(1)) DP tables, or reducible to O(width) or O(height) rather than full O(1).
- **Paint House** (a well-known interview problem): Nearly identical structure — choose one of several paint colors per house, minimizing (or here, maximizing) cost, with the constraint that adjacent houses can't share the same color/activity.

---

## 8. Key Takeaways

- Ninja's Training introduces genuinely 2D DP state (day × last-activity), a step up in complexity from the single-dimension state seen in Climbing Stairs, Frog Jump, and House Robber.
- Despite the 2D nature of the *state*, the *space complexity* can still be optimized to O(1), because each day only depends on the immediately preceding day — the same "rolling window" principle as before, just applied to a small fixed-size array (3 activities) instead of a single scalar.
- The core recurrence computes, for each activity, the best possible score if that activity is chosen today: today's points for that activity, plus the best score from yesterday among the *other two* activities.
- This pattern — "choose one option per step, can't repeat the previous step's choice, maximize/minimize total" — generalizes to other well-known problems like Paint House.
