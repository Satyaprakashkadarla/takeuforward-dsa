# Notes

## Problem Summary

Given a list of intervals, merge every pair of overlapping intervals and return the resulting non-overlapping intervals.

---

## Brute Force Approach

### Idea

1. Sort the intervals.
2. Compare every interval with the remaining intervals.
3. Merge overlapping intervals.
4. Skip already merged intervals.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(n)

### Drawback

Every interval may be compared with many others, making it inefficient for large inputs.

---

## Optimal Approach

### Idea

First sort all intervals based on their starting point.

Maintain one current interval.

For every next interval:

- If it overlaps with the current interval:

```text
currentStart <= currentEnd
```

Merge them by updating:

```text
end = max(end, currentEnd)
```

Otherwise:

- Store the previous merged interval.
- Start a new interval.

Finally, add the last merged interval.

---

## Complexity

- **Time Complexity:** O(n log n)

  - Sorting: O(n log n)
  - Traversal: O(n)

- **Space Complexity:** O(n)

---

## Edge Cases

- Single interval
- No overlapping intervals
- All intervals overlap
- Already sorted intervals
- Unsorted intervals
- Same start point
- Same end point

---

## Key Concepts

- Interval Problems
- Sorting
- Greedy Algorithm
- Array Traversal

---

## Interview Tip

Almost every interval problem starts with:

> **Sort the intervals by their starting time.**

Once sorted, merging becomes a single linear traversal.

Always remember the overlap condition:

```text
currentStart <= previousEnd
```

If true:

```text
Merge
```

Otherwise:

```text
Store previous interval and start a new one
```

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Comparison | O(n²) | O(n) |
| Sorting + Merge | O(n log n) | O(n) |

---

## Takeaway

Sorting the intervals first allows all overlapping intervals to appear consecutively. A single traversal is then sufficient to merge them, resulting in an efficient **O(n log n)** solution that is widely expected in coding interviews.
