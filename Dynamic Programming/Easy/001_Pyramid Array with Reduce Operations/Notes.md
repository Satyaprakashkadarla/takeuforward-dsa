# Notes: Form Pyramid (Minimum Cost to Build a Pyramid of Stones)

## 1. Problem Recap

Given stone heights, carve out a contiguous **pyramid** (heights `1,2,...,x-1,x,x-1,...,2,1`) by only reducing heights (cost = total units reduced), zeroing everything outside the pyramid. Minimize total cost.

```
arr = [1,2,3,4,2,1]
Best pyramid: [1,2,3,2,1,0], apex height 3
Cost = (4-3) + (2-1) + (1-0) = 1+1+1 = wait let's recompute per problem's own explanation: reduce 4→2 (cost2), 2→1(cost1), 1→0(cost1) = total 4
```

### The Key Insight — Pyramid Sum Is Always `x^2`
A pyramid with apex height `x` has values `1,2,...,x-1,x,x-1,...,2,1`. Summing this:
```
sum = 2*(1+2+...+(x-1)) + x = 2 * (x-1)*x/2 + x = (x-1)*x + x = x^2 - x + x = x^2
```
So a pyramid of apex height `x` always sums to exactly `x^2`, regardless of *where* it sits in the array.

### Reframing: Minimize Cost = Maximize Kept Sum
Since every unit removed costs 1 and contributes nothing to the final pyramid, minimizing total cost is the same as **maximizing** the sum of the pyramid we end up keeping (everything else becomes 0 anyway). Since pyramid sum = `x^2`, this further reduces to: **find the largest apex height `x` achievable anywhere in the array**.

```
minimum cost = total_sum(arr) - (max_achievable_x)^2
```

---

## 2. Approach 1: Brute Force (Try Every Apex Position and Height)

### Idea
For every possible apex position `i` and every possible apex height `x` (from 1 up to some reasonable bound), check whether a pyramid of that height centered at `i` actually FITS within the array (i.e., every required "ramp" position has enough height available). Track the best (largest) achievable `x`.

### Code Logic (Conceptual)
```java
int maxHeight = 1;
for (int i = 0; i < n; i++) {
    for (int x = 1; x <= n; x++) {
        if (fits(arr, i, x)) {
            maxHeight = Math.max(maxHeight, x);
        }
    }
}
return total - maxHeight * maxHeight;
```
Where `fits(arr, i, x)` checks that positions `i-x+1` through `i+x-1` are all within bounds and each has `arr[pos] >= requiredHeightAtPos`.

### Complexity
- **Time:** O(n^3) in a naive implementation — O(n) apex positions × O(n) candidate heights × O(n) to verify each candidate fits.
- **Space:** O(1) (beyond the input array itself).

This is far too slow for `n` up to `10^5`, but is useful as a correctness baseline / sanity check for the optimal approach.

---

## 3. Approach 2: Optimal (Two-Pass Precomputation)

### Idea
Instead of checking every (position, height) pair from scratch, precompute for every position `i`:
- `left[i]` = the tallest a strictly-increasing-by-1 "ramp" COULD be, ending at `i`, limited by both the actual stone height at `i` and continuity from the left neighbor.
- `right[i]` = the symmetric version, computed from the right.

```
left[i]  = min(arr[i], left[i-1] + 1)
right[i] = min(arr[i], right[i+1] + 1)
```

Then, for each position `i` treated as a potential APEX, the tallest pyramid centered there is `min(left[i], right[i])` — limited by whichever side (up-ramp or down-ramp) is more restrictive.

### Code Logic
```java
long total = sum(arr);

int[] left = new int[n];
left[0] = 1;
for (i=1; i<n; i++) left[i] = Math.min(arr[i], left[i-1]+1);

int[] right = new int[n];
right[n-1] = 1;
for (i=n-2; i>=0; i--) right[i] = Math.min(arr[i], right[i+1]+1);

int maxHeight = 1;
for (i=0; i<n; i++) maxHeight = Math.max(maxHeight, Math.min(left[i], right[i]));

return total - maxHeight*maxHeight;
```

### Dry Run 1
`arr = [1,2,3,4,2,1]`, `total = 13`

**Computing `left[]`:**
| i | arr[i] | left[i-1]+1 | left[i] |
|---|---|---|---|
| 0 | 1 | — | 1 |
| 1 | 2 | 1+1=2 | min(2,2)=2 |
| 2 | 3 | 2+1=3 | min(3,3)=3 |
| 3 | 4 | 3+1=4 | min(4,4)=4 |
| 4 | 2 | 4+1=5 | min(2,5)=2 |
| 5 | 1 | 2+1=3 | min(1,3)=1 |

`left = [1,2,3,4,2,1]`

**Computing `right[]`:**
| i | arr[i] | right[i+1]+1 | right[i] |
|---|---|---|---|
| 5 | 1 | — | 1 |
| 4 | 2 | 1+1=2 | min(2,2)=2 |
| 3 | 4 | 2+1=3 | min(4,3)=3 |
| 2 | 3 | 3+1=4 | min(3,4)=3 |
| 1 | 2 | 3+1=4 | min(2,4)=2 |
| 0 | 1 | 2+1=3 | min(1,3)=1 |

`right = [1,2,3,3,2,1]`

**Computing `min(left[i], right[i])` for each i:**
| i | left[i] | right[i] | min |
|---|---|---|---|
| 0 | 1 | 1 | 1 |
| 1 | 2 | 2 | 2 |
| 2 | 3 | 3 | **3** |
| 3 | 4 | 3 | 3 |
| 4 | 2 | 2 | 2 |
| 5 | 1 | 1 | 1 |

`maxHeight = 3` (achieved at index 2 or 3)

**Answer:** `total - maxHeight^2 = 13 - 9 = 4`

Result: **4** ✅ (matches expected output)

### Dry Run 2
`arr = [1,2,1]`, `total = 4`

**left:** left[0]=1; left[1]=min(2,1+1)=2; left[2]=min(1,2+1)=1. `left=[1,2,1]`

**right:** right[2]=1; right[1]=min(2,1+1)=2; right[0]=min(1,2+1)=1. `right=[1,2,1]`

**min(left,right):** [1,2,1] → `maxHeight = 2` (at index 1)

**Answer:** `total - maxHeight^2 = 4 - 4 = 0`

Result: **0** ✅ (matches expected output — the array is already a valid pyramid)

### Complexity
- **Time:** O(n) — three linear passes (sum, left, right) plus a final linear scan.
- **Space:** O(n) — two auxiliary arrays.

---

## 4. Comparing Both Approaches

| Aspect | Brute Force | Optimal (Two-Pass Precomputation) |
|---|---|---|
| Time Complexity | O(n^3) | O(n) |
| Space Complexity | O(1) | O(n) |
| Practical for n up to 10^5? | No — far too slow | Yes — a few hundred thousand operations |

---

## 5. Why `left[i] = min(arr[i], left[i-1] + 1)` Correctly Represents the Best Possible Ramp

Two constraints limit how tall a "ramp" ending at position `i` can be:
1. **The physical stone height**: we can never have a ramp value taller than `arr[i]` itself, since we can only reduce, never increase.
2. **Continuity from the left**: for the ramp to increase by exactly 1 at each step (as required by the pyramid shape `1,2,3,...`), the ramp height at `i` can be **at most** one more than the ramp height achieved at `i-1`.

Taking the `min` of these two constraints gives the tightest (and correct) upper bound on the ramp height achievable at `i`. The symmetric logic applies to `right[i]` from the other direction.

---

## 6. Edge Cases to Consider

1. **Single-element array** — the only possible pyramid is height 1 (just `[1]`, or the element itself reduced to 1... actually with n=1, left[0]=right[0]=1, so maxHeight=1, cost = arr[0]-1). Wait, but constraint says arr[i]>=1, so this is consistent.
2. **Already a perfect pyramid** — e.g., `[1,2,1]` → cost 0 (see Dry Run 2).
3. **All elements very large and equal** — e.g., `[100,100,100,100,100]` → the achievable pyramid height is limited by how far the ramp can extend given the array length, not by the stone heights themselves (since they're all "tall enough").
4. **Strictly increasing then no decrease** — e.g., `[1,2,3,4,5]` → the pyramid must still be symmetric, so the achievable apex height is constrained by the SHORTER of the possible up-ramp and down-ramp at each candidate position.
5. **Minimum apex height of 1** — `maxHeight` is initialized to 1, guaranteeing every array (per the problem's guarantee that a valid pyramid always exists) has at least a trivial single-stone "pyramid" of height 1 as a fallback.

---

## 7. Related Concepts / Follow-Ups

- **Largest Rectangle in Histogram**: A conceptually similar "how far can I extend in each direction, limited by a local constraint" precomputation technique, though solved with a monotonic stack rather than this specific ramp-based approach.
- **Maximum Square/Rectangle in Binary Matrix**: Another family of problems using similar "precompute how far you can extend, then combine" DP techniques.
- **Trapping Rain Water**: Uses a similar left-max/right-max precomputation idea, though for a different geometric quantity (water trapped, not pyramid height).

---

## 8. Key Takeaways

- The critical mathematical insight — a pyramid of apex height `x` always sums to exactly `x^2` — transforms "minimize the cost of removed height" into "maximize the achievable apex height," a much more tractable optimization target.
- `left[i]` and `right[i]` are computed via simple O(n) linear scans, each capturing "how tall could a ramp be here, given both the physical stone limit and the continuity constraint from the previous position."
- The tallest pyramid centered at any position `i` is bounded by the SMALLER of its two ramps (`min(left[i], right[i])`) — a pyramid can only be as tall as its weaker side allows.
- This problem is a nice example of turning a seemingly complex construction problem (build a specific shape at minimum cost) into a simple precomputation + single formula, once the right mathematical reframing (sum = x^2) is spotted.
