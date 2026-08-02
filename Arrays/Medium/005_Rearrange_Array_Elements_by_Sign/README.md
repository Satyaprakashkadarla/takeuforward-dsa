# Rearrange Array Elements by Sign

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums` of even length containing an equal number of positive and negative integers, rearrange the array such that:

- Every consecutive pair has opposite signs.
- The relative order of positive numbers is preserved.
- The relative order of negative numbers is preserved.
- The rearranged array starts with a positive number.

Return the rearranged array.

---

## Examples

### Example 1

**Input**

```text
nums = [2, 4, 5, -1, -3, -4]
```

**Output**

```text
[2, -1, 4, -3, 5, -4]
```

---

### Example 2

**Input**

```text
nums = [1, -1, -3, -4, 2, 3]
```

**Output**

```text
[1, -1, 2, -3, 3, -4]
```

---

### Example 3

**Input**

```text
nums = [-4, 4, -4, 4, -4, 4]
```

**Output**

```text
[4, -4, 4, -4, 4, -4]
```

---

## Constraints

- `2 <= nums.length <= 10^5`
- `1 <= |nums[i]| <= 10^4`
- `nums.length` is even.
- Number of positive and negative integers are equal.

---

# Brute Force Approach

## Idea

Store all positive numbers in one list and all negative numbers in another list.

Then, alternately place elements from both lists into the answer array.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(n) |

---

# Optimal Approach

## Idea

Create the answer array.

Maintain:

- `pos = 0` → index for positive numbers
- `neg = 1` → index for negative numbers

Traverse the original array once.

- Place positive numbers at even indices.
- Place negative numbers at odd indices.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(n) |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
