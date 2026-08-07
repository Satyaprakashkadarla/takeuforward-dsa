# Merge Two Sorted Arrays Without Extra Space

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

Given two sorted integer arrays `nums1` and `nums2`, merge them into a single sorted array in-place.

- `nums1` has a size of `m + n`.
- The first `m` elements are valid.
- The last `n` elements are empty (0 placeholders).
- `nums2` contains `n` sorted elements.

The final sorted array should be stored inside `nums1`.

---

## Examples

### Example 1

**Input**

```text
nums1 = [-5,-2,4,5]
nums2 = [-3,1,8]
```

**Output**

```text
[-5,-3,-2,1,4,5,8]
```

---

### Example 2

**Input**

```text
nums1 = [0,2,7,8]
nums2 = [-7,-3,-1]
```

**Output**

```text
[-7,-3,-1,0,2,7,8]
```

---

### Example 3

**Input**

```text
nums1 = [1,3,5]
nums2 = [2,4,6,7]
```

**Output**

```text
[1,2,3,4,5,6,7]
```

---

## Constraints

- `0 <= m,n <= 1000`
- Arrays are sorted in non-decreasing order.

---

# Brute Force Approach

## Idea

Create an extra array.

Merge both arrays using two pointers.

Copy the merged array back into `nums1`.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m+n) |
| Space Complexity | O(m+n) |

---

# Optimal Approach

## Idea

Start filling `nums1` from the last index.

Maintain three pointers:

- `i` → Last valid element of nums1
- `j` → Last element of nums2
- `k` → Last position of nums1

Always place the larger element at index `k`.

Continue until all elements are merged.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(m+n) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
