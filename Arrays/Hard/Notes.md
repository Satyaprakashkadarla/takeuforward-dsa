# Notes

## Problem Summary

Find the element that appears **more than n/2 times** in the array.

It is guaranteed that such an element always exists.

---

## Brute Force Approach

### Idea

For every element:

- Count how many times it appears.
- If the frequency exceeds `n/2`, return it.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

The same frequencies are calculated repeatedly, making the solution inefficient for large arrays.

---

## Optimal Approach (Boyer-Moore Voting Algorithm)

### Idea

Maintain two variables:

- `candidate`
- `count`

Rules:

- If `count == 0`, select the current element as the new candidate.
- If the current element equals the candidate, increment `count`.
- Otherwise, decrement `count`.

After one traversal, the candidate is guaranteed to be the majority element because it appears more than `n/2` times.

### Why It Works

Every occurrence of a non-majority element cancels out one occurrence of the majority element.

Since the majority element appears more than half the time, it cannot be completely canceled out and remains as the final candidate.

---

## Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Single element array
- All elements are the same
- Majority element at the beginning
- Majority element at the end
- Negative numbers

---

## Key Concepts

- Boyer-Moore Voting Algorithm
- Greedy Algorithm
- Frequency Cancellation
- Majority Element

---

## Interview Tip

If the problem states:

- One element appears **more than n/2 times**
- Majority element is guaranteed to exist
- Constant extra space is required

The **Boyer-Moore Voting Algorithm** is the standard optimal solution.

If the majority element is **not guaranteed**, perform a second pass to verify that the candidate actually appears more than `n/2` times.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Loops | O(n²) | O(1) |
| Boyer-Moore Voting | O(n) | O(1) |

---

## Takeaway

The Boyer-Moore Voting Algorithm efficiently identifies the majority element by canceling out occurrences of different elements. It solves the problem in **O(n)** time with **O(1)** extra space, making it the preferred interview solution.
