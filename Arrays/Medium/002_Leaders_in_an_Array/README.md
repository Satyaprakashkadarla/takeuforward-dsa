# Leaders in an Array

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an integer array `nums`, return a list of all the **leaders** in the array.

A leader is an element that is **strictly greater than every element to its right**.

The rightmost element is always a leader.

The leaders should appear in the same order as they appear in the original array.

---

## Examples

### Example 1

**Input**

```text
nums = [1, 2, 5, 3, 1, 2]
```

**Output**

```text
[5, 3, 2]
```

---

### Example 2

**Input**

```text
nums = [-3, 4, 5, 1, -4, -5]
```

**Output**

```text
[5, 1, -4, -5]
```

---

### Example 3

**Input**

```text
nums = [-3, 4, 5, 1, -30, -10]
```

**Output**

```text
[5, 1, -10]
```

---

## Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

# Brute Force Approach

## Idea

For every element, check all elements to its right.

If no greater element exists, it is a leader.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) *(excluding output list)* |

---

# Optimal Approach

## Idea

Traverse the array from right to left.

Maintain the maximum element seen so far.

If the current element is greater than the maximum, it is a leader.

Reverse the answer list at the end.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n) |
| Space Complexity | O(n) *(for output list)* |

---

## Files

- `BruteForce.java`
- `Optimal.java`
- `Notes.md`
