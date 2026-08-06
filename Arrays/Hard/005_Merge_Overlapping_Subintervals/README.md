# Merge Overlapping Subintervals

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of intervals where `intervals[i] = [starti, endi]`, merge all overlapping intervals and return the resulting non-overlapping intervals.

The intervals can be returned in any order.

---

## Examples

### Example 1

**Input**

```text
intervals = [[1,5],[3,6],[8,10],[15,18]]
```

**Output**

```text
[[1,6],[8,10],[15,18]]
```

**Explanation**

Intervals `[1,5]` and `[3,6]` overlap, so they are merged into `[1,6]`.

---

### Example 2

**Input**

```text
intervals = [[5,7],[1,3],[4,6],[8,10]]
```

**Output**

```text
[[1,3],[4,7],[8,10]]
```

---

## Constraints

- `1 <= intervals.length <= 10⁵`
- `0 <= starti <= endi <= 10⁵`

---

# Brute Force Approach

## Idea

Sort the intervals.

For every interval:

- Compare it with the following intervals.
- Merge all overlapping intervals.
- Skip already merged intervals.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(n) |

---

# Optimal Approach

## Idea

1. Sort intervals according to their starting value.
2. Keep the first interval as the current merged interval.
3. If the next interval overlaps, extend the ending point.
4. Otherwise, store the current interval and start a new one.
5. Finally, add the last merged interval.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n log n) |
| Space Complexity | O(n) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
