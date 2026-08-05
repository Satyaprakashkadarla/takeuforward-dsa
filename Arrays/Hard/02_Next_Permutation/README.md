# Next Permutation

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Hard
- **Status:** ✅ Solved

---

## Problem Statement

A permutation of an array is an arrangement of its elements in a particular order.

Given an integer array `nums`, rearrange it into the **next lexicographically greater permutation**.

If such a permutation is not possible (i.e., the array is in descending order), rearrange it into the **lowest possible order (ascending order)**.

The rearrangement must be done **in-place** using **constant extra space**.

---

## Examples

### Example 1

**Input**

```text
nums = [1,2,3]
```

**Output**

```text
[1,3,2]
```

---

### Example 2

**Input**

```text
nums = [3,2,1]
```

**Output**

```text
[1,2,3]
```

---

### Example 3

**Input**

```text
nums = [1,1,5]
```

**Output**

```text
[1,5,1]
```

---

## Constraints

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 100`

---

# Brute Force Approach

## Idea

Generate all possible permutations.

Sort them in lexicographical order.

Locate the current permutation and return the next one.

If the current permutation is the last, return the first permutation.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n! × n) |
| Space Complexity | O(n!) |

---

# Optimal Approach

## Idea

1. Traverse from the end to find the first index `i` such that `nums[i] < nums[i+1]`.
2. Traverse again from the end to find the first element greater than `nums[i]`.
3. Swap these two elements.
4. Reverse the suffix starting from `i + 1`.

This produces the next lexicographically greater permutation.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(1) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
