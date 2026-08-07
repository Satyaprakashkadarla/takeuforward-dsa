# Notes

## Problem Summary

Merge two already sorted arrays into `nums1` without using any extra space.

---

## Brute Force Approach

### Idea

- Create a temporary array.
- Merge both arrays using two pointers.
- Copy the merged result back into `nums1`.

### Complexity

- **Time Complexity:** O(m+n)
- **Space Complexity:** O(m+n)

### Drawback

Uses an additional array, violating the in-place requirement.

---

## Optimal Approach

### Idea

Since `nums1` has extra space at the end, start filling from the back.

Maintain three pointers:

- `i` → Last valid element in `nums1`
- `j` → Last element in `nums2`
- `k` → Last position in `nums1`

Compare `nums1[i]` and `nums2[j]`.

Place the larger element at `nums1[k]`.

Move the corresponding pointer backward.

Continue until `nums2` is exhausted.

---

## Why Merge from the End?

If merging starts from the beginning, valid elements in `nums1` may be overwritten before they are compared.

By filling from the end, all unprocessed values remain intact.

---

## Complexity

- **Time Complexity:** O(m+n)
- **Space Complexity:** O(1)

---

## Edge Cases

- `nums2` is empty
- `nums1` is empty
- All elements of `nums2` are smaller
- All elements of `nums2` are larger
- Duplicate values
- Negative numbers

---

## Key Concepts

- Two Pointers
- In-place Merge
- Reverse Traversal
- Array Manipulation

---

## Interview Tip

This problem is commonly asked in interviews because it tests your understanding of in-place algorithms.

Instead of shifting elements repeatedly, utilize the unused space at the end of `nums1` and merge from right to left.

---

## Comparison

| Approach | Time | Space |
|----------|------|-------|
| Temporary Array | O(m+n) | O(m+n) |
| In-place Three Pointers | O(m+n) | O(1) |

---

## Takeaway

The optimal solution merges both sorted arrays from the end using three pointers. This prevents overwriting elements in `nums1` and achieves the required **O(1)** extra space while maintaining **O(m+n)** time complexity.
