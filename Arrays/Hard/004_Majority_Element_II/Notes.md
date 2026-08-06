# Notes

## Problem Summary

Find all elements that appear **more than n/3 times** in the array.

Unlike Majority Element-I, there can be **at most two majority elements**.

---

## Brute Force Approach

### Idea

For every element:

- Count its occurrences.
- If its frequency exceeds `n/3`, include it in the answer.
- Skip duplicates already added.

### Complexity

- **Time Complexity:** O(n²)
- **Space Complexity:** O(1)

### Drawback

Repeatedly counts frequencies, making it inefficient for large arrays.

---

## Optimal Approach (Extended Boyer-Moore Voting Algorithm)

### Key Observation

An array can contain **at most two** elements occurring more than `n/3` times.

Maintain:

- Candidate 1
- Candidate 2
- Count 1
- Count 2

### Phase 1

Identify two potential candidates by increasing, decreasing, and resetting counts.

### Phase 2

Traverse the array again to verify that each candidate actually appears more than `n/3` times.

Return the valid candidates in ascending order.

---

## Why At Most Two?

Suppose there were three different elements each occurring more than `n/3` times.

Their total occurrences would exceed `n`, which is impossible.

Therefore, only **two candidates** are sufficient.

---

## Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Only one majority element
- Two majority elements
- Negative numbers
- All elements identical
- Minimum array size

---

## Key Concepts

- Extended Boyer-Moore Voting Algorithm
- Candidate Selection
- Frequency Verification
- Majority Element

---

## Interview Tip

Remember the relationship between the threshold and the number of possible majority elements:

| Threshold | Maximum Possible Majority Elements |
|-----------|------------------------------------|
| > n/2 | 1 |
| > n/3 | 2 |
| > n/4 | 3 |

The generalized Boyer-Moore algorithm maintains **k − 1** candidates for elements appearing more than **n/k** times.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Nested Loops | O(n²) | O(1) |
| Extended Boyer-Moore | O(n) | O(1) |

---

## Takeaway

The Extended Boyer-Moore Voting Algorithm efficiently identifies all elements appearing more than `n/3` times by maintaining two candidates and verifying their frequencies in a second pass. It achieves the optimal **O(n)** time and **O(1)** extra space.
