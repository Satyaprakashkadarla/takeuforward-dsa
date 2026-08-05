# Notes

## Problem Summary

Given an array representing a permutation, rearrange it into the **next lexicographically greater permutation**.

If no greater permutation exists, rearrange it into the **smallest possible permutation**.

---

## Brute Force Approach

### Idea

- Generate all possible permutations.
- Sort them lexicographically.
- Find the current permutation.
- Return the next permutation.

### Complexity

- **Time Complexity:** O(n! × n)
- **Space Complexity:** O(n!)

### Drawback

Generating every permutation is computationally expensive and impractical even for moderate values of `n`.

---

## Optimal Approach

### Step 1

Traverse from the end and find the first index `i` such that:

```text
nums[i] < nums[i+1]
```

This element is called the **pivot**.

---

### Step 2

Again traverse from the end and find the first element greater than the pivot.

Swap them.

---

### Step 3

Reverse the suffix after the pivot.

The suffix is originally in decreasing order, so reversing it produces the smallest possible arrangement.

---

## Example

```text
1 2 7 4 3 1

Pivot = 2

Swap with 3

1 3 7 4 2 1

Reverse suffix

1 3 1 2 4 7
```

---

## Complexity

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

---

## Edge Cases

- Already highest permutation
- Already lowest permutation
- Duplicate elements
- Single element array
- Two element array

---

## Key Concepts

- Lexicographical Order
- Two Pointer Technique
- In-place Array Manipulation
- Greedy Algorithm

---

## Interview Tip

The key observation is that the suffix after the pivot is always in **descending order**.

By swapping the pivot with the next larger element and reversing the suffix, we obtain the smallest lexicographically greater permutation in linear time.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Generate All Permutations | O(n! × n) | O(n!) |
| Next Permutation Algorithm | O(n) | O(1) |

---

## Takeaway

The Next Permutation algorithm efficiently computes the next lexicographical arrangement by identifying a pivot, swapping it with the next larger element, and reversing the suffix. It satisfies the in-place and constant-space requirements with **O(n)** time complexity.
```
