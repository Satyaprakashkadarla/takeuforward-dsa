# Find the Duplicate Number

## Problem Information

- **Topic:** Arrays
- **Difficulty:** Medium
- **Status:** ✅ Solved

---

## Problem Statement

Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive, there is only one repeated number in `nums` (but it may appear more than once).

Return the duplicate number.

**Constraints:**

- The array must not be modified.
- Use only constant extra space.
- The solution should run in less than `O(n²)` time.

---

## Examples

### Example 1

**Input**

```text
nums = [1,3,4,2,2]
```

**Output**

```text
2
```

---

### Example 2

**Input**

```text
nums = [3,1,3,4,2]
```

**Output**

```text
3
```

---

### Example 3

**Input**

```text
nums = [1,1]
```

**Output**

```text
1
```

---

## Constraints

- `1 <= n <= 10^5`
- `nums.length == n + 1`
- `1 <= nums[i] <= n`
- Only one duplicate number exists, but it may appear multiple times.

---

# Brute Force Approach

## Idea

For every element, compare it with every other element.

If two different indices contain the same value, return that value.

---

## Complexity Analysis

| Complexity | Value |
|------------|-------|
| Time Complexity | O(n²) |
| Space Complexity | O(1) |

---

# Optimal Approach

## Idea

Treat the array as a linked list.

Use **Floyd's Tortoise and Hare (Cycle Detection)** algorithm.

- Phase 1: Find the meeting point inside the cycle.
- Phase 2: Reset one pointer to the beginning.
- Move both pointers one step at a time.
- The meeting point is the duplicate number.

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
